package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
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
import br.com.financemate.facade.CobrancaFacade;
import br.com.financemate.facade.HistoricoCobrancaFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Historicocobranca;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class HistoricoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Contasreceber contasReceber;
    private List<Contasreceber> listaContasReceber;
    private Historicocobranca historicaCobranca;
    private Cobranca cobranca;
    
    @PostConstruct
    public void init(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contasReceber");
        cobranca = (Cobranca) session.getAttribute("cobranca");
        historicaCobranca = (Historicocobranca) session.getAttribute("historico");
        session.removeAttribute("historico");
    	gerarListaCliente();
    	if (historicaCobranca == null) {
    		historicaCobranca = new Historicocobranca();
    		historicaCobranca.setCobranca(cobranca);
    		session.removeAttribute("cobranca");
		}
    	
    }
    
    

	public Historicocobranca getHistoricaCobranca() {
		return historicaCobranca;
	}



	public void setHistoricaCobranca(Historicocobranca historicaCobranca) {
		this.historicaCobranca = historicaCobranca;
	}



	public Cobranca getCobranca() {
		return cobranca;
	}



	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contasreceber getContasReceber() {
		return contasReceber;
	}

	public void setContasReceber(Contasreceber contasReceber) {
		this.contasReceber = contasReceber;
	}

	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}
    
	public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public String salvarHitorico(){
        CobrancaFacade cobrancaFacade = new CobrancaFacade();
        if (cobranca.getIdcobranca()==null){
            cobranca = cobrancaFacade.salvar(cobranca);
            
        }
        historicaCobranca.setData(new Date());
        historicaCobranca.setCobranca(cobranca);
        historicaCobranca.setUsuario(usuarioLogadoMB.getUsuario());
        historicaCobranca = cobrancaFacade.salvar(historicaCobranca);
        cobranca.getHistoricocobrancaList().add(historicaCobranca);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Histórico de Cobrança Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "cobranca";
    }
	
	public String cancelar(){
        return "cobranca";
    } 
	
	public String salvarEdicao(){ 
        HistoricoCobrancaFacade historicoCobrancaFacade = new HistoricoCobrancaFacade();
        historicoCobrancaFacade.salvar(historicaCobranca);
        mensagem mensagem = new  mensagem();
        mensagem.historico();
        return "cobranca";
    }

}
