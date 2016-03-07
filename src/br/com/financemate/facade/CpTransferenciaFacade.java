package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.dao.CpTransferenciaDao;
import br.com.financemate.model.Contaspagar;
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
    
    public void excluir(int idcpTranferencia) {
        cpTransferenciaDao = new CpTransferenciaDao();
        try {
            cpTransferenciaDao.excluir(idcpTranferencia);
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Cptransferencia consultar(int idTransferencia) throws SQLException{
        cpTransferenciaDao = new CpTransferenciaDao();
        return cpTransferenciaDao.consultar(idTransferencia);
    }
 
    
    public List<Cptransferencia> listarTranferencia(String sql) throws SQLException{
        cpTransferenciaDao = new CpTransferenciaDao();
        return cpTransferenciaDao.listarTransferencias(sql);
    }
    
    public Cptransferencia tranferencia(String sql) throws SQLException{
        cpTransferenciaDao = new CpTransferenciaDao();
        return cpTransferenciaDao.transferencias(sql);
    }
}
