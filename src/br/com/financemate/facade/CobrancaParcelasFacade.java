package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.dao.CobrancaParcelasDao;
import br.com.financemate.model.Cobrancaparcelas;

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
}
