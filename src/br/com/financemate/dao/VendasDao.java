/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Emissaonota;
import br.com.financemate.model.Vendas;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 *
 * @author Wolverine
 */
public class VendasDao {
    
    public Vendas salvar(Vendas venda) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        venda = manager.merge(venda);
        manager.getTransaction().commit();
        manager.close();
        return venda;
    }
    
    public List<Vendas> listar(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Vendas> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
    
    public Vendas consultar(int idVenda) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Vendas venda = manager.find(Vendas.class, idVenda);
        manager.getTransaction().commit();
        manager.close();
        return venda;
    }
    
    public void Excluir(int idVendas) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Vendas venda = manager.find(Vendas.class, idVendas);
        if (venda!=null){
            manager.remove(venda);
        }
        manager.getTransaction().commit();
        manager.close();
    }
    
    
    public Emissaonota salvar(Emissaonota emissaonota)throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        emissaonota = manager.merge(emissaonota);
        manager.getTransaction().commit();
        manager.close();
        return emissaonota;
    }
    
    public Emissaonota getEmissao(int idVendas) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select e from Emissaonota e where e.vendas.idvendas=" + idVendas);
        manager.getTransaction().commit();
        Emissaonota emissor = null;
        if (q.getResultList().size()>0){
            emissor = (Emissaonota) q.getResultList().get(0);
        }
        manager.close();
        return emissor;
    }
    
}
