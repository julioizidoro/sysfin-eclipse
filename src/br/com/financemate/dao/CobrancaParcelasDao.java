package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Cobrancaparcelas;

public class CobrancaParcelasDao {
	
	private EntityManager manager;
    
    public Cobrancaparcelas salvar(Cobrancaparcelas cobrancaparcelas) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        cobrancaparcelas = manager.merge(cobrancaparcelas);
        manager.getTransaction().commit();
        return cobrancaparcelas;
    }
    
    public List<Cobrancaparcelas> listar(String sql) throws SQLException{
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Cobrancaparcelas> lista = q.getResultList();
        return lista;
    }
    
    public Cobrancaparcelas consultar(int idCobrancaParcela) throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Cobrancaparcelas cobrancaparcelas = manager.find(Cobrancaparcelas.class, idCobrancaParcela);
        manager.getTransaction().commit();
        return cobrancaparcelas;
    }
    
    public List<Cobrancaparcelas> listarCobranca(String sql) throws SQLException{
        manager = ConectionFactory.getConnection();
        Query q = manager.createQuery(sql);
        List<Cobrancaparcelas> lista = q.getResultList();
        return lista;
    }
    
    
    
}
