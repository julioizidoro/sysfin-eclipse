package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Tipoplanocontas;

@Named 
@ViewScoped
public class CadPlanoContaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Inject
	 private UsuarioLogadoMB usuarioLogadoMB;
	 private Planocontas planocontas;
	 private List<Tipoplanocontas> listarTipoPlanoContas;
	 private Tipoplanocontas tipoPlanoContas;
	
	 
	@PostConstruct
	public void init(){
		 FacesContext fc = FacesContext.getCurrentInstance();
		 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		 planocontas = (Planocontas) session.getAttribute("planoconta");
		 session.removeAttribute("planoconta");
		 gerarlistaTipoPlanoContas();
		 if (planocontas == null) {
			 planocontas = new Planocontas(); 
		 }//else{
		//	 tipoPlanoContas = planocontas.get;
		 //}
		 
	}
	
	

	
	public Tipoplanocontas getTipoPlanoContas() {
		return tipoPlanoContas;
	}




	public void setTipoPlanoContas(Tipoplanocontas tipoPlanoContas) {
		this.tipoPlanoContas = tipoPlanoContas;
	}




	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Planocontas getPlanocontas() {
		return planocontas;
	}

	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}

	public List<Tipoplanocontas> getListarTipoPlanoContas() {
		return listarTipoPlanoContas;
	}

	public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
		this.listarTipoPlanoContas = listarTipoPlanoContas;
	}
	
	 public void gerarlistaTipoPlanoContas() {
		 TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
		 try {
			 listarTipoPlanoContas = tipoPlanoContasFacede.listar();
		 } catch (SQLException ex) {
			 Logger.getLogger(CadPlanoContaMB.class.getName()).log(Level.SEVERE, null, ex);
		 }
		 if (listarTipoPlanoContas == null) {
			 listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
		 }
	 }

	    
	 public String salvarPlanoContas() {
		 PlanoContasFacade planoContasFacade = new PlanoContasFacade();
		 try {
			 //planocontas.setTipoplanocontas(tipoPlanoContas);
			 planocontas = planoContasFacade.salvar(planocontas);
			 RequestContext.getCurrentInstance().closeDialog(planocontas);
		 } catch (SQLException ex) {
			 Logger.getLogger(CadPlanoContaMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro  Cadastro Plano de Contas", "Erro");
			 
		 }
		 return ""; 
	 }
	    
	 public void mostrarMensagem(Exception ex, String erro, String titulo) {
		 FacesContext context = FacesContext.getCurrentInstance();
		 erro = erro + " - " + ex;
		 context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	    
	 public String cancelar() {
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	 }

}
