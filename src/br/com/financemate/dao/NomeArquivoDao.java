/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Nomearquivo;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wolverine
 */
public class NomeArquivoDao {
    
    public Nomearquivo salvar(Nomearquivo nomeArquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        nomeArquivo = manager.merge(nomeArquivo);
        manager.getTransaction().commit();
        manager.close();
        return nomeArquivo;
    }
    
    public Nomearquivo listar(int idConta) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("SELECT n FROM Nomearquivo n where n.contaspagar.idcontasPagar=" + idConta);
        Nomearquivo nomearquivo = null;
        if (q.getResultList().size()>0){
            nomearquivo =  (Nomearquivo) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        manager.close();
        return nomearquivo;
    }
    
    public void excluir(int idNomeArquivo) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Nomearquivo nomearquivo = manager.find(Nomearquivo.class, idNomeArquivo);
        manager.remove(nomearquivo);
        manager.getTransaction().commit();
        manager.close();
    }
    
    
}
