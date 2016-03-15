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
       manager.close();
       return historicocobranca;
   }
   
   
   public List<Historicocobranca> listar(String sql)throws SQLException{
       manager = ConectionFactory.getConnection();
       manager.getTransaction().begin();
       Query q = manager.createQuery(sql);
       List<Historicocobranca> lista = q.getResultList();
       manager.getTransaction().commit();
       manager.close();
       return lista;
   }
   
   public Historicocobranca consultar(String sql)throws SQLException{
       manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
       Query q = manager.createQuery(sql);
       
       Historicocobranca historicocobranca = null;
       if (q.getResultList().size()>0){
           historicocobranca =(Historicocobranca) q.getSingleResult();
       }
       manager.getTransaction().commit();
       manager.close();
       return historicocobranca;
   }
}