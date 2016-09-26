package br.com.financemate.facade;

import java.sql.SQLException;

import br.com.financemate.dao.WebServiceDao;
import br.com.financemate.model.Ftpdados;

public class WebServiceFacade {
	
	WebServiceDao webServiceDao;
	
	public Ftpdados getWS()throws SQLException{
        webServiceDao = new WebServiceDao();
        return webServiceDao.getWS();
        
    }

}
