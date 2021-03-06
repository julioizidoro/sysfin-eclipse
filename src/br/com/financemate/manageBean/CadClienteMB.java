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

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoplanocontas;

@Named
@ViewScoped
public class CadClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    private Tipoplanocontas tipoplanocontas;
	
    @PostConstruct
	public void init(){  
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        session.removeAttribute("cliente");
        gerarListaTipoPlanoContas();
        if (cliente == null) {
             cliente = new Cliente();
        }else{
        	tipoplanocontas = cliente.getTipoplanocontas();
        }
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



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public List<Tipoplanocontas> getListarTipoPlanoContas() {
		return listarTipoPlanoContas;
	}



	public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
		this.listarTipoPlanoContas = listarTipoPlanoContas;
	}



	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	 public void gerarListaTipoPlanoContas() {
		 TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
		 try {
			 listarTipoPlanoContas = tipoPlanoContasFacede.listar();
			 if (listarTipoPlanoContas == null) {
				 listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
			 }
		 } catch (SQLException ex) {
			 Logger.getLogger(CadClienteMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro ao listar os tipos de contas", "Erro");
		 }
	 }
	 
	 public void mostrarMensagem(Exception ex, String erro, String titulo){
		 FacesContext context = FacesContext.getCurrentInstance();
		 erro = erro + " - " + ex;
		 context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	 
	 public void salvar(){
            ClienteFacade clienteFacade = new ClienteFacade();
                 cliente.setTipoplanocontas(tipoplanocontas);
                 try {
					cliente = clienteFacade.salvar(cliente);
					RequestContext.getCurrentInstance().closeDialog(cliente);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }

}
