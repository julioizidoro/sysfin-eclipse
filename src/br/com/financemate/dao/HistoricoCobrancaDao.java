package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Historicocobranca;

public class HistoricoCobrancaDao {
    
    private EntityManager manager;
   
   public Historicocobranca salvar(Historicocobranca historicocobranca) throws SQLException{
       manager = ConectionFactory.getConnection();
       manager.getTransaction().begin();
       historicocobranca = manager.merge(historicocobranca);
       manager.getTransaction().commit();
       return historicocobranca;
   }
   
   
   public List<Historicocobranca> listar(String sql)throws SQLException{
       manager = ConectionFactory.getConnection();
       manager.getTransaction().begin();
       Query q = manager.createQuery(sql);
       manager.getTransaction().commit();
       return q.getResultList();
   }
   
   public Historicocobranca consultar(String sql)throws SQLException{
       manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
       Query q = manager.createQuery(sql);
         manager.getTransaction().commit();
       if (q.getResultList().size()>0){
           return (Historicocobranca) q.getSingleResult();
       } 
       return null;
   }
}