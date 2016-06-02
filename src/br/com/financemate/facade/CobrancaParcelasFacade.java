package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.dao.CobrancaParcelasDao;
import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.NomeArquivoDao;
import br.com.financemate.model.Cobrancaparcelas;
import br.com.financemate.model.Nomearquivo;

public class CobrancaParcelasFacade {
	
	   CobrancaParcelasDao cobrancaParcelasDao;
	    
	   public Cobrancaparcelas salvar(Cobrancaparcelas cobrancaparcelas) {
		   cobrancaParcelasDao = new CobrancaParcelasDao();
	        try {
	            return cobrancaParcelasDao.salvar(cobrancaparcelas);
	        } catch (SQLException ex) {
	            Logger.getLogger(Cobrancaparcelas.class.getName()).log(Level.SEVERE, null, ex);
	            return null;
	        }
	    }
	    
	    public List<Cobrancaparcelas> listar(String sql) throws SQLException{
	        cobrancaParcelasDao = new CobrancaParcelasDao();
	        return cobrancaParcelasDao.listar(sql);  
	    }
	    
	    public List<Cobrancaparcelas> listarCobranca(String sql) throws SQLException{
	        cobrancaParcelasDao = new CobrancaParcelasDao();
	        return cobrancaParcelasDao.listarCobranca(sql);  
	    }
	    
	    public Cobrancaparcelas consultar(int idCobrancaParcelas) throws SQLException{
	    	cobrancaParcelasDao = new CobrancaParcelasDao();
	        return cobrancaParcelasDao.consultar(idCobrancaParcelas);
	    }
	    
	    
	    public Cobrancaparcelas listarCobrancaParcela(int idConta) throws SQLException{
	    	cobrancaParcelasDao = new CobrancaParcelasDao();
	        return cobrancaParcelasDao.listarCobrancaParcela(idConta);
	    }
	    
	    public void excluir(int idCobrancaParcelas) {
	    	cobrancaParcelasDao = new CobrancaParcelasDao();
	        try {
	        	cobrancaParcelasDao.excluir(idCobrancaParcelas);
	        } catch (SQLException ex) {
	            Logger.getLogger(Cobrancaparcelas.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
