package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.OperacaoUsuarioDao;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Operacaousuairo;


public class OperacaoUsuarioFacade {

	OperacaoUsuarioDao operacaoUsuarioDao;
	
	public Operacaousuairo salvar(Operacaousuairo operacaousuairo) throws SQLException{
        operacaoUsuarioDao = new OperacaoUsuarioDao();
        return operacaoUsuarioDao.salvar(operacaousuairo);
    }
	
	public Operacaousuairo consultar(int idConta) throws SQLException{
        operacaoUsuarioDao = new OperacaoUsuarioDao();
        return operacaoUsuarioDao.consultar(idConta);
    }
	
	public Operacaousuairo operacaousuairo(String sql) throws SQLException{
        operacaoUsuarioDao = new OperacaoUsuarioDao();
        return operacaoUsuarioDao.operacaousuairo(sql);
    }
	
	public List<Operacaousuairo> listar(String sql) throws SQLException{
        operacaoUsuarioDao = new OperacaoUsuarioDao();
        return operacaoUsuarioDao.listar(sql);
    }
}
