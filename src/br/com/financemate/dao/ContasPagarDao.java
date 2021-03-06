/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Arquivocontaspagar;
import br.com.financemate.model.Contaspagar;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class ContasPagarDao {
    
    public Contaspagar salvar(Contaspagar conta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        conta = manager.merge(conta);
        manager.getTransaction().commit();
        manager.close();
        return conta;
    }
    
    /**
     *
     */
    public List<Contaspagar> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
		List<Contaspagar> listaContas = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return listaContas;
    }

    public List<Contaspagar> listarContas(String sql) throws SQLException{
        sql = "SELECT c FROM Contaspagar c";
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaContas = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return listaContas;
    }

    
    public Contaspagar consultar(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar conta = manager.find(Contaspagar.class, idConta);
        manager.getTransaction().commit();
        return conta;
    }
    
    public void excluir(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar conta = manager.find(Contaspagar.class, idConta);
        manager.remove(conta);
        manager.getTransaction().commit();
        manager.close();
    }
    
    
    
    public Contaspagar consultarVenda(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        Contaspagar conta = null;
        if (q.getResultList().size()>0){
            conta = (Contaspagar) q.getResultList().get(0);
        }
        manager.close();
        return conta;
    }
    
   
    
    public void salvarArquivo(Arquivocontaspagar arquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        //abrindo uma transação
        manager.getTransaction().begin();
        manager.merge(arquivo);
        //fechando uma transação
        manager.getTransaction().commit();
        manager.close();
    }
    
    public Arquivocontaspagar consultarArquivo(int idContasPagar) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select c from  Arquivocontaspagar  c where c.contasPagar=" + idContasPagar);
        Arquivocontaspagar arquivo = null;
        if (q.getResultList().size()>0){
            arquivo =  (Arquivocontaspagar) q.getResultList().get(0);
        }
        manager.close();
        return arquivo;
    }
    
    public void excluirArquivo(int idArquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Arquivocontaspagar arquivo = manager.find(Arquivocontaspagar.class, idArquivo);
        if (arquivo!=null){
            manager.remove(arquivo);
        }
        manager.getTransaction().commit();
        manager.close();
    }
    
    public List<Double> calculaSaldos(String data, int idcliente) throws SQLException {
        Double valor;
        EntityManager manager = ConectionFactory.getConnection();
        Query query = manager.createNativeQuery("Select distinct sum(valor) as valor " +
                "From Contaspagar where dataVencimento<'" + data + "' and cliente_idcliente=" + idcliente);
        List<Double> totalContas = new ArrayList<Double>();
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valor) as valor " +
                "From Contaspagar where dataVencimento='" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valor) as valor " +
                "From Contaspagar where dataVencimento>'" + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        return totalContas;
    }
    
    public Double calculaSaldosRestantes(Date dataAtual, Date dataFinal, int idcliente) throws SQLException {
        Double valor = null;
        EntityManager manager = ConectionFactory.getConnection();
        Query query = manager.createNativeQuery("Select distinct sum(valor) as valor " +
                "From Contaspagar where dataVencimento>'" + dataAtual + "' and dataVencimento<='"+ dataFinal +"' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
        }
        return valor;
    }
    
    
    public Float pagamentoPorDia(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Float pagamento = null;
        Double valorPagamento;
        if (q.getResultList().size()>0){
        	valorPagamento = (Double) q.getResultList().get(0);
        	if (valorPagamento == null) {
        		pagamento = 0f;
			}else{
				pagamento = valorPagamento.floatValue();
			}
        }
        manager.close();
        return pagamento;
    } 
    
    
    public List<Contaspagar> listaFluxo(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaFluxo= q.getResultList();
        manager.getTransaction().commit();
        return listaFluxo;
    }
}
