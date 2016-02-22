package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Tipoplanocontas;

@Named
@ViewScoped
public class TipoPlanoContaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoplanocontas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    
	
	@PostConstruct
	public void init(){
		gerarListaTipoPlanoConta();
	}


	public List<Tipoplanocontas> getListarTipoPlanoContas() {
		return listarTipoPlanoContas;
	}


	public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
		this.listarTipoPlanoContas = listarTipoPlanoContas;
	}


	public Tipoplanocontas getTipoplanocontas() {
		return tipoplanocontas;
	}


	public void setTipoplanocontas(Tipoplanocontas tipoplanocontas) {
		this.tipoplanocontas = tipoplanocontas;
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	
	  public void gerarListaTipoPlanoConta() {
		  TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
		  try {
			  listarTipoPlanoContas = tipoPlanoContasFacede.listar();
			  if (listarTipoPlanoContas == null) {
				  listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
			  }
		  } catch (SQLException ex) {
			  Logger.getLogger(TipoPlanoContaMB.class.getName()).log(Level.SEVERE, null, ex);
			  mostrarMensagem(ex, "Erro ao gerar a lista de tipo de plano de contas", "Erro");
		  }
		  
	  }
	  
	  public void retornoDialogNovo(SelectEvent event){
	        Tipoplanocontas tipoplanocontas = (Tipoplanocontas) event.getObject();
	       listarTipoPlanoContas.add(tipoplanocontas);
	   }
	    
	    public void mostrarMensagem(Exception ex, String erro, String titulo){
	        FacesContext context = FacesContext.getCurrentInstance();
	        erro = erro + " - " + ex;
	        context.addMessage(null, new FacesMessage(titulo, erro));
	    }


	    public String novo() {
	        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()) {
	            tipoplanocontas = new Tipoplanocontas();
	            Map<String, Object> options = new HashMap<String, Object>();
	            options.put("contentWidth", 300);
	            RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta");
	        } else {
	            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
	            FacesContext.getCurrentInstance().addMessage(null, mensagem);
	            return "";
	        }
	        return "";

	    }

	    public String editar(Tipoplanocontas tipoplanocontas) {
	        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getAtipoplanocontas()){
	             if (tipoplanocontas != null) {
	                FacesContext fc = FacesContext.getCurrentInstance();
	                HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	                session.setAttribute("tipoplanocontas", tipoplanocontas);
	                RequestContext.getCurrentInstance().openDialog("cadTipoPlanoConta");
	            }
	            return  "";
	        }else {
	            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
	            FacesContext.getCurrentInstance().addMessage(null, mensagem);
	            return "";
	        }
	         
	    }
}
