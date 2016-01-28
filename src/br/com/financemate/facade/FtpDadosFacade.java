package br.com.financemate.facade;

import java.sql.SQLException;

import br.com.financemate.dao.FtpDadosDao;
import br.com.financemate.model.Ftpdados;

public class FtpDadosFacade {
	
	FtpDadosDao ftpDadosDao;
    
    public Ftpdados getFTPDados()throws SQLException{
        ftpDadosDao = new FtpDadosDao();
        return ftpDadosDao.getFTPDados();
        
    }
    
}

