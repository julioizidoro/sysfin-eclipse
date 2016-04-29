package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.financemate.dao.FormaPagamentoDao;
import br.com.financemate.dao.SaldoDao;
import br.com.financemate.model.Formapagamento;
import br.com.financemate.model.Saldo;

public class SaldoFacade {

	SaldoDao saldoDao;
	
	public Saldo salvar(Saldo saldo) throws SQLException{
		saldoDao = new SaldoDao();
        return saldoDao.salvar(saldo);
    }
    
    public void Excluir(int idsaldo) throws SQLException{
    	saldoDao = new SaldoDao();
    	saldoDao.Excluir(idsaldo);
    }
    
    public List<Saldo> listar(String sql) throws SQLException{
    	saldoDao = new SaldoDao();
        return saldoDao.listar(sql);
    }
    
    public Float consultar(String sql) throws SQLException{
    	saldoDao = new SaldoDao();
        return saldoDao.consultar(sql);
    }
}
