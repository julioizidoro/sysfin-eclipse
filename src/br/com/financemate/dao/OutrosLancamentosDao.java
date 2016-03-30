/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.dao;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.util.Formatacao;

import java.sql.SQLException;
import java.util.Date;
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
        manager.close();
        return outroslancamentos;
    }
    
    public List<Outroslancamentos> listaOutrosLancamentos(String sql) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createQuery(sql);
        List<Outroslancamentos> lista = q.getResultList();
        manager.getTransaction().commit();
        manager.close();
        return lista;
    }
    
    
    public void excluir(int idOutrosLancamentos) throws SQLException{
        EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Outroslancamentos outroslancamentos = manager.find(Outroslancamentos.class, idOutrosLancamentos);
        manager.remove(outroslancamentos);
        manager.getTransaction().commit();
        manager.close();
    }
    
    public float gerarSaldoInicial(Date dataInicio)throws SQLException {
    	float saldo= 0.0f;
    	EntityManager manager = ConectionFactory.getConnection();
        manager.getTransaction().begin();
        Query q = manager.createNativeQuery("SELECT distinct sum(valorSaida) as totalsaida FROM outroslancamentos" +
        									" where dataVencimento<'" + Formatacao.ConvercaoDataSql(dataInicio) + "'");
        Double totalsaida = (Double) q.getResultList().get(0);
        q = manager.createNativeQuery("SELECT distinct sum(valorEntrada) as totalentrada FROM outroslancamentos" +
				" where dataVencimento<'" + Formatacao.ConvercaoDataSql(dataInicio) + "'");
        Double totalentrada = (Double) q.getResultList().get(0);
        saldo = totalentrada.floatValue() - totalsaida.floatValue();
    	return saldo;
    }
    
    
}
