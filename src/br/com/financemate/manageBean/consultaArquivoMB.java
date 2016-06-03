package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.NomeArquivoFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Nomearquivo;


@Named
@ViewScoped
public class consultaArquivoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contaspagar contaspagar;
	private Nomearquivo nomeArquivo;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        consultarArquivos();
	}

	public Contaspagar getContaspagar() {
		return contaspagar;
	}

	public void setContaspagar(Contaspagar contaspagar) {
		this.contaspagar = contaspagar;
	}

	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public Nomearquivo getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(Nomearquivo nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public void consultarArquivos(){
		NomeArquivoFacade nomeArquivoFacade = new NomeArquivoFacade();
		try {
			nomeArquivo = nomeArquivoFacade.listar(contaspagar.getIdcontasPagar());
			if (nomeArquivo == null) {
				nomeArquivo = new Nomearquivo();
				nomeArquivo.setNomearquivo01("Não existe arquivo anexado");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String voltar(){
		 nomeArquivo = null;
		 RequestContext.getCurrentInstance().closeDialog(null);
	     return null;
	}
	
	
}
