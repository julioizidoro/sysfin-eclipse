package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Formapagamento;
import br.com.financemate.model.Saldo;

public class SaldoDao {
	
	public Saldo salvar(Saldo saldo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        saldo = manager.merge(saldo);
        manager.getTransaction().commit();
        manager.close();
        return saldo;
    }
    
    public List<Saldo> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Saldo> lista = q.getResultList();
        manager.close();
        return lista;
    }
    
    public Banco consultar(int idSaldo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Banco banco = manager.find(Banco.class, idSaldo);
        manager.close();
        return banco;
    }
    public Float consultar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        Float saldo = null;
        if (q.getResultList().size()>0){
        	saldo = (Float) q.getResultList().get(0);
        }else{
        	saldo = 0f;
        }
        return saldo;
    }
    
    
    public void Excluir(int idsaldo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Saldo saldo = manager.find(Saldo.class, idsaldo);
        manager.remove(saldo);
        manager.getTransaction().commit();
        manager.close();
    }
}
