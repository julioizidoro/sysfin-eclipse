package br.com.financemate.manageBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.facade.UsuarioFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
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
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasPagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
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
	
	

}
