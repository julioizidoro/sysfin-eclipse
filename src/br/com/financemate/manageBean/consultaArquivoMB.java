package br.com.financemate.manageBean;

import java.io.IOException;
import java.io.InputStream;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.financemate.facade.FtpDadosFacade;
import br.com.financemate.facade.NomeArquivoFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Ftpdados;
import br.com.financemate.model.Nomearquivo;
import br.com.financemate.util.Ftp;


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
	private StreamedContent file;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contaspagar = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        consultarArquivos();
	}
	
	

	public StreamedContent getFile() {
		return file;
	}



	public void setFile(StreamedContent file) {
		this.file = file;
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
	
	public boolean salvarArquivoFTP(String nomeArquivoLocal, String nomeArquivoFTP){
        FtpDadosFacade fpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = fpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(),dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "Erro");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
            ftp.receberArquivo(nomeArquivoLocal, nomeArquivoFTP);
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Salvar Arquivo", "Erro");
            return false; 
        }
        try {
            ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
            return false;
        }
        mensagem mensagem = new mensagem();
        mensagem.downloadSucesso();
        return true;
        
    }
	
	public void baixarFile(){
		//salvarArquivoFTP(nomeArquivo.getNomearquivo01(), nomeArquivo.getNomearquivo01());
		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("resources/img/bolaVerde.png");
        file = new DefaultStreamedContent(stream, "/Users/Kamila Rodrigues/Downloads", "downloaded_Arquivo.jpg");
	}
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	
	public boolean desabilitarDownload(){
		if (nomeArquivo.getNomearquivo01().equalsIgnoreCase("Não existe arquivo anexado")) {
			return false;
		}else{
			return true;
		}
	}
	
	
}
