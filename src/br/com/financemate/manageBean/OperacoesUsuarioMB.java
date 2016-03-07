package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.OperacaoUsuarioFacade;
import br.com.financemate.facade.UsuarioFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Usuario;

@Named
@ViewScoped
public class OperacoesUsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Contaspagar contasPagar;
	private Operacaousuairo operacaousuairo;
	private List<Operacaousuairo> listaOperacaousuairo;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasPagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        
	}
	
	

	public List<Operacaousuairo> getListaOperacaousuairo() {
		return listaOperacaousuairo;
	}



	public void setListaOperacaousuairo(List<Operacaousuairo> listaOperacaousuairo) {
		this.listaOperacaousuairo = listaOperacaousuairo;
	}



	public Operacaousuairo getOperacaousuairo() {
		return operacaousuairo;
	}



	public void setOperacaousuairo(Operacaousuairo operacaousuairo) {
		this.operacaousuairo = operacaousuairo;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Contaspagar getContasPagar() {
		return contasPagar;
	}

	public void setContasPagar(Contaspagar contasPagar) {
		this.contasPagar = contasPagar;
	}

	
	public void inicializar(){
		if (contasPagar != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = new Usuario();
		}
	}
	
	public String voltar(){
		 RequestContext.getCurrentInstance().closeDialog(null);
	     return null;
	}
	
	

}
