/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.financemate.facade;


import br.com.financemate.dao.OutrosLancamentosDao;
import br.com.financemate.model.Outroslancamentos;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Wolverine
 */
public class OutrosLancamentosFacade {
    
    OutrosLancamentosDao outrosLancamentosDao;
    
    public Outroslancamentos salvar(Outroslancamentos outroslancamentos) throws SQLException{
    	outrosLancamentosDao = new OutrosLancamentosDao();
        return outrosLancamentosDao.salvar(outroslancamentos);
    }
    
    public List<Outroslancamentos> listaOutrosLancamentos(String sql) throws SQLException{
    	outrosLancamentosDao = new OutrosLancamentosDao();
        return outrosLancamentosDao.listaOutrosLancamentos(sql);
    }
    
    public void excluir(int idOutrosLancamentos) throws SQLException{
    	outrosLancamentosDao = new OutrosLancamentosDao();
    	outrosLancamentosDao.excluir(idOutrosLancamentos);
    }
    
    public float saldo(Date dataInicial) throws SQLException{
    	outrosLancamentosDao = new OutrosLancamentosDao();
    	return outrosLancamentosDao.gerarSaldoInicial(dataInicial);
    }
    
    public float saldoInicialTelaConsulta(String sql) throws SQLException{
    	outrosLancamentosDao = new OutrosLancamentosDao();
    	return outrosLancamentosDao.saldoInicialTelaConsulta(sql);
    }
    
}
