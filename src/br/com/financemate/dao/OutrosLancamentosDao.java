/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Outroslancamentos;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;



/**
 *
 * @author Wolverine
 */
public class OutrosLancamentosDao {
    
    public Outroslancamentos salvar(Outroslancamentos outroslancamentos) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        outroslancamentos = manager.merge(outroslancamentos);
        manager.getTransaction().commit();
        return outroslancamentos;
    }
    
    public List<Outroslancamentos> listaOutrosLancamentos(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Outroslancamentos> lista = q.getResultList();
        manager.getTransaction().commit();
        return lista;
    }
    
    
    public void excluir(int idOutrosLancamentos) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Outroslancamentos outroslancamentos = manager.find(Outroslancamentos.class, idOutrosLancamentos);
        manager.remove(outroslancamentos);
        manager.getTransaction().commit();
    }
    
    
}
