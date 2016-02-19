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

import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Tipoplanocontas;

@Named
@ViewScoped
public class CadTipoPlanoContaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Tipoplanocontas tipoplanocontas;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Tipoplanocontas> listarTipoPlanoContas;
    
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        tipoplanocontas = (Tipoplanocontas) session.getAttribute("tipoplanocontas");
        session.removeAttribute("tipoplanocontas");
        gerarListaTipoPlanoConta();
        if (tipoplanocontas == null) {
            tipoplanocontas = new Tipoplanocontas();
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


	public List<Tipoplanocontas> getListarTipoPlanoContas() {
		return listarTipoPlanoContas;
	}


	public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
		this.listarTipoPlanoContas = listarTipoPlanoContas;
	}
	
	
	public String salvar() {
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getItipoplanocontas()){
            TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
            try {
                tipoPlanoContasFacede.salvar(tipoplanocontas);
                RequestContext.getCurrentInstance().closeDialog(tipoplanocontas);
            } catch (SQLException ex) {
                Logger.getLogger(CadTipoPlanoContaMB.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensagem(ex, "Erro ao salvar um tipo de plano de conta", "Erro");
            }
                 
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
        return "";
    }
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
   
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
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

}
