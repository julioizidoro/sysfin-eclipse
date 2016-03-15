package br.com.financemate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Ftpdados;

public class FtpDadosDao {
	
	private EntityManager manager;
    
    public Ftpdados getFTPDados()throws SQLException{
        manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select f from Ftpdados f");
        Ftpdados ftpDados = (Ftpdados) q.getSingleResult();
        manager.getTransaction().commit();
        manager.close();
        return ftpDados;
    }
    
}

