/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Formapagamento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class ContasReceberDao {
    
    private EntityManager manager;
    
    public Contasreceber salvar(Contasreceber conta) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        conta = manager.merge(conta);
        manager.getTransaction().commit();
        manager.close();
        return conta;
    }
    
    public List<Contasreceber> listar(String sql) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Contasreceber> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
    
    public Contasreceber consultar(int idConta) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contasreceber conta = manager.find(Contasreceber.class, idConta);
        manager.getTransaction().commit();
        manager.close();
        return conta;
    }
    
    public void excluir(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contasreceber conta = manager.find(Contasreceber.class, idConta);
        manager.remove(conta);
        manager.getTransaction().commit();
        manager.close();
    }
    
    public Contasreceber consultarVendaFornecedor(int idVenda) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select c from Contasreceber c where c.vendaComissao=" + idVenda);
        Contasreceber conta = null;
        if (q.getResultList().size()>0){
            conta =  (Contasreceber) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        manager.close();
        return conta;
    }
    
    public List<Double> calculaSaldos(String data, int idcliente) throws SQLException {
        Double valor;
        EntityManager manager = ConectionFactory.getConnection();
        Query query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento<'" + data + "' and cliente_idcliente=" + idcliente);
        List<Double> totalContas = new ArrayList<Double>();
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento='" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        
        query = manager.createNativeQuery("Select distinct sum(valorParcela) as valorParcela " +
                "From Contasreceber where dataVencimento>'" + data + "' and cliente_idcliente=" + idcliente);
        if (query.getSingleResult()!=null){
            valor =  (Double) query.getSingleResult();
            totalContas.add(valor.doubleValue());
        }else totalContas.add(0.0);
        manager.close();
        return totalContas;
    }
    
    
    public Contasreceber consultar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Contasreceber contasreceber = null;
        if (q.getResultList().size()>0){
        	contasreceber = (Contasreceber) q.getResultList().get(0);
        }
        return contasreceber;
    }
    
    
    public Float recebimentoPorDia(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Float recebimento = null;
        Double valorRecebimento;
        if (q.getResultList().size()>0){
        	valorRecebimento = (Double) q.getResultList().get(0);
        	if (valorRecebimento == null) {
				recebimento = 0f;
			}else{
				recebimento = valorRecebimento.floatValue();
			}
        }
        manager.close();
        return recebimento;
    } 
}
