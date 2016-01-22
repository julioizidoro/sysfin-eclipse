package br.com.financemate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import br.com.financemate.connection.ConectionFactory;
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
}
