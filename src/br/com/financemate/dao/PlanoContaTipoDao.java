package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Planocontatipo;


public class PlanoContaTipoDao {

	public Planocontatipo salvar(Planocontatipo planotipo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        planotipo = manager.merge(planotipo);
        manager.getTransaction().commit();
        manager.close();
        return planotipo;
    }
    
    
     public List<Planocontatipo> listar() throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select p from Planocontatipo p" );
        List<Planocontatipo> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
     
     
     public List<Planocontatipo> listar(int idTipoPlano) throws SQLException{
         EntityManager manager = ConectionFactory.getConnection();
         manager.getTransaction().begin();
         Query q = manager.createQuery("select p from Planocontatipo p where p.tipoplanocontas.idtipoplanocontas="+ idTipoPlano);
         List<Planocontatipo> lista = q.getResultList();
         manager.getTransaction().commit();
         manager.close();
         return lista;
     }

    
    public Planocontatipo consultar(int idPlanoContasTipo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Planocontatipo planotipo = manager.find(Planocontatipo.class, idPlanoContasTipo);
        manager.getTransaction().commit();
        manager.close();
        return planotipo;
    }
}
