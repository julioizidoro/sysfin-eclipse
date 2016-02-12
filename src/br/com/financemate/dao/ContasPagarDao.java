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
        //abrindo uma transação
        manager.getTransaction().begin();
        conta = manager.merge(conta);
        //fechando uma transação
        manager.getTransaction().commit();
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
        return listaContas;
    }

    public List<Contaspagar> listarContas(String sql) throws SQLException{
        sql = "SELECT c FROM Contaspagar c";
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contaspagar> listaContas = q.getResultList();
        manager.getTransaction().commit();
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
    }
    
    
    
    public Contaspagar consultarVenda(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().commit();
        Query q = manager.createQuery(sql);
        Contaspagar conta = null;
        if (q.getResultList().size()>0){
            conta = (Contaspagar) q.getResultList().get(0);
        }
        manager.getTransaction().begin();
        return conta;
    }
    
   
    
    public void salvarArquivo(Arquivocontaspagar arquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        //abrindo uma transação
        manager.getTransaction().begin();
        manager.merge(arquivo);
        //fechando uma transação
        manager.getTransaction().commit();
    }
    
    public Arquivocontaspagar consultarArquivo(int idContasPagar) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select c from  Arquivocontaspagar  c where c.contasPagar=" + idContasPagar);
        Arquivocontaspagar arquivo = null;
        if (q.getResultList().size()>0){
            arquivo =  (Arquivocontaspagar) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
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
    
    public Double calculaSaldosRestantes(Calendar dataAtual, Calendar dataFinal, int idcliente) throws SQLException {
        Double valor = null;
        EntityManager manager = ConectionFactory.getConnection();
        Query query = manager.createNativeQuery("Select distinct sum(valor) as valor " +
                "From Contaspagar where dataVencimento>'" + dataAtual + "' and dataVencimento<='"+ dataFinal +"' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
        }
        return valor;
    }
}
