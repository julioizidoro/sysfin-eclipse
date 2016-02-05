package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.dao.CobrancaDao;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Historicocobranca;

public class CobrancaFacade {
    
    private CobrancaDao cobrancaDao;
    
    public Cobranca salvar(Cobranca cobranca) {
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.salvar(cobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Historicocobranca salvar(Historicocobranca historicocobranca) {
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.salvar(historicocobranca);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Cobranca consultar(String sql){
        cobrancaDao = new CobrancaDao();
        try {
            return cobrancaDao.consultar(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CobrancaFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
}
