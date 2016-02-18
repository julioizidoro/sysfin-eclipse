package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;

@Named
@ViewScoped
public class CadBancoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Banco banco;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("banco");
        if (banco == null) {
            banco = new Banco();
            Cliente cliente = (Cliente) session.getAttribute("cliente");
            banco.setCliente(cliente);
            session.removeAttribute("cliente");
        }
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	
	 public void mostrarMensagem(Exception ex, String erro, String titulo) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        erro = erro + " - " + ex;
	        context.addMessage(null, new FacesMessage(titulo, erro));
	    }

	 public void salvar() {
		 BancoFacade bancoFacade = new BancoFacade();
		 try {
			 banco = bancoFacade.salvar(banco);
			 RequestContext.getCurrentInstance().closeDialog(banco);
		 } catch (SQLException ex) {
			 Logger.getLogger(CadBancoMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro ao Cadastrar Banco", "Erro");
			 
		 }
	 }

	 public String cancelar() {
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	 }
	

}
