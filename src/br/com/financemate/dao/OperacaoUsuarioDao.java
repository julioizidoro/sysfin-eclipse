package br.com.financemate.dao;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Operacaousuairo;

public class OperacaoUsuarioDao {

	
	 public Operacaousuairo salvar(Operacaousuairo operacaousuairo) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 operacaousuairo = manager.merge(operacaousuairo);
		 manager.getTransaction().commit();
		 manager.close();
		 return operacaousuairo;
	 }
	 
	 public Operacaousuairo consultar(int idOperacaousuairo) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 Operacaousuairo operacaousuairo = manager.find(Operacaousuairo.class, idOperacaousuairo);
		 manager.getTransaction().commit();
		 manager.close();
		 return operacaousuairo;
	 }	
	 
	 public Operacaousuairo operacaousuairo(String sql) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 Query q = manager.createQuery(sql);
		 Operacaousuairo operacaousuairoo = (Operacaousuairo) q.getResultList();
		 manager.getTransaction().commit();
		 manager.close();
		 return operacaousuairoo;
	 }
	 
	 public List<Operacaousuairo> listar(String sql) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 Query q = manager.createQuery(sql);
		 List<Operacaousuairo> listaContas = q.getResultList();
		 manager.getTransaction().commit();
		 manager.close();
		 return listaContas;
	 }
}
