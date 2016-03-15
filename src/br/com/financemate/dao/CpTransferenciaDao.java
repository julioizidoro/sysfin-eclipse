package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;

public class CpTransferenciaDao {
	
	public Cptransferencia salvar(Cptransferencia cptransferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        cptransferencia = manager.merge(cptransferencia);
        manager.getTransaction().commit();
        manager.close();
        return cptransferencia;
    }
	
	public void excluir(int idcpTranferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar transferencia = manager.find(Contaspagar.class, idcpTranferencia);
        manager.remove(transferencia);
        manager.getTransaction().commit();
        manager.close();
    }
	
	public Cptransferencia consultar(int idTransferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Cptransferencia transferencia = manager.find(Cptransferencia.class, idTransferencia);
        manager.getTransaction().commit();
        manager.close();
        return transferencia;
    }
	

	
	public List<Cptransferencia> listarTransferencias(String sql) throws SQLException{ 
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Cptransferencia> listaTransferenfia = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return listaTransferenfia;
    }
	
	public Cptransferencia transferencias(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        Cptransferencia cptransferencia = (Cptransferencia) q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return cptransferencia;
    }
}
