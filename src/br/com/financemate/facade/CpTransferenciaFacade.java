package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.dao.CpTransferenciaDao;
import br.com.financemate.model.Cptransferencia;


public class CpTransferenciaFacade {

CpTransferenciaDao cpTransferenciaDao;
    
    public Cptransferencia salvar(Cptransferencia cptransferencia) {
        cpTransferenciaDao = new CpTransferenciaDao();
        try {
            return cpTransferenciaDao.salvar(cptransferencia);
        } catch (SQLException ex) {
            Logger.getLogger(ContasPagarFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
