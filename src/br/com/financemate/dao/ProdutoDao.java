/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;




/**
 *
 * @author Wolverine
 */
public class ProdutoDao {
    
    
    public List<Produto> listar(int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select p from Produto p where p.cliente.idcliente=" + idCliente);
        List<Produto> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
    
    
    public List<Produto> listarTodosCLiente(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("Select p from Produto p");
        List<Produto> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
    
    public Produto consultar(int idProduto) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Produto produto = manager.find(Produto.class, idProduto);
        manager.getTransaction().commit();
        manager.close();
        return produto;
    }
    
    public Produto consultarProduto(int idProduto, int idCliente) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery("select c from Produto c where c.codigosystm=" + idProduto + " and c.cliente.idcliente=" + idCliente);
        Produto produto = null;
        if (q.getResultList().size()>0){
        	produto = (Produto) q.getResultList().get(0);
        }
        manager.getTransaction().commit();
        manager.close();
        return produto;
    }
    
    public Produto salvar(Produto produto) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        produto = manager.merge(produto);
        manager.getTransaction().commit();
        manager.close();
        return produto;
    }
    
}
