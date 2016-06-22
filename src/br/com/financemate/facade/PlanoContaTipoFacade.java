package br.com.financemate.facade;

import java.sql.SQLException;
import java.util.List;

import br.com.financemate.dao.PlanoContaTipoDao;
import br.com.financemate.model.Planocontatipo;


public class PlanoContaTipoFacade {

	 PlanoContaTipoDao planoContaTipoDao;
	    
	    public Planocontatipo salvar(Planocontatipo planotipo) throws SQLException{
	    	planoContaTipoDao = new PlanoContaTipoDao();
	        return planoContaTipoDao.salvar(planotipo);
	    }
	   
	    public Planocontatipo consultar(int idPlanoContas) throws SQLException{
	    	planoContaTipoDao = new PlanoContaTipoDao();
	        return planoContaTipoDao.consultar(idPlanoContas);
	    }
	    
	    public List<Planocontatipo> listar() throws Exception{
	    	planoContaTipoDao = new PlanoContaTipoDao();
	        return planoContaTipoDao.listar();
	        
	    }
	    
	    
	    public List<Planocontatipo> listar(int idTipoPlano) throws Exception{
	    	planoContaTipoDao = new PlanoContaTipoDao();
	        return planoContaTipoDao.listar(idTipoPlano);
	        
	    }
}
