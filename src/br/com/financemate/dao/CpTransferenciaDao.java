package br.com.financemate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;

public class CpTransferenciaDao {
	
	public Cptransferencia salvar(Cptransferencia cptransferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        //abrindo uma transação
        manager.getTransaction().begin();
        cptransferencia = manager.merge(cptransferencia);
        //fechando uma transação
        manager.getTransaction().commit();
        return cptransferencia;
    }
	
	public void excluir(int idcpTranferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Contaspagar transferencia = manager.find(Contaspagar.class, idcpTranferencia);
        manager.remove(transferencia);
        manager.getTransaction().commit();
    }
	
	public Cptransferencia consultar(int idTransferencia) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Cptransferencia transferencia = manager.find(Cptransferencia.class, idTransferencia);
        manager.getTransaction().commit();
        return transferencia;
    }
}
