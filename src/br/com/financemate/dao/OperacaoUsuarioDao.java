package br.com.financemate.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.financemate.connection.ConectionFactory;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Operacaousuairo;

public class OperacaoUsuarioDao {

	
	 public Operacaousuairo salvar(Operacaousuairo operacaousuairo) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 //abrindo uma transação
		 manager.getTransaction().begin();
		 operacaousuairo = manager.merge(operacaousuairo);
		 //fechando uma transação
		 manager.getTransaction().commit();
		 return operacaousuairo;
	 }
	 
	 public Operacaousuairo consultar(int idOperacaousuairo) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 Operacaousuairo operacaousuairo = manager.find(Operacaousuairo.class, idOperacaousuairo);
		 manager.getTransaction().commit();
		 return operacaousuairo;
	 }	
	 
	 public Operacaousuairo operacaousuairo(String sql) throws SQLException{
		 EntityManager manager = ConectionFactory.getConnection();
		 manager.getTransaction().begin();
		 Query q = manager.createQuery(sql);
		 Operacaousuairo operacaousuairoo = (Operacaousuairo) q.getResultList();
		 manager.getTransaction().commit();
		 return operacaousuairoo;
	 }
}
