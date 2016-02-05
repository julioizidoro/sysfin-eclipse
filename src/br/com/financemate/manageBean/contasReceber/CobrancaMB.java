package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.CobrancaFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Historicocobranca;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class CobrancaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Contasreceber contasReceber;
    private Cobranca cobranca;
    private List<Contasreceber> listaContasReceber;
    private List<Historicocobranca> listaHistorico;
    private Historicocobranca historico;
    private Vendas venda;
    
    @PostConstruct
    public void init(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contasReceber");
        venda = (Vendas) session.getAttribute("vendas");
        session.removeAttribute("contasReceber");
    	gerarListaCliente();
        cliente = contasReceber.getCliente();
        if (venda==null){
            venda = new Vendas();
        }
        if (cobranca == null) {
			cobranca = new Cobranca();
			listaHistorico = new ArrayList<Historicocobranca>();
		}else{
			listaHistorico = cobranca.getHistoricocobrancaList();
		}
        historico = new Historicocobranca();
    }
    
    
    

	public Vendas getVenda() {
		return venda;
	}




	public void setVenda(Vendas venda) {
		this.venda = venda;
	}




	public List<Historicocobranca> getListaHistorico() {
		return listaHistorico;
	}




	public void setListaHistorico(List<Historicocobranca> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}




	public Historicocobranca getHistorico() {
		return historico;
	}




	public void setHistorico(Historicocobranca historico) {
		this.historico = historico;
	}




	public Cobranca getCobranca() {
		return cobranca;
	}




	public void setCobranca(Cobranca cobranca) {
		this.cobranca = cobranca;
	}




	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}



	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
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
	
	public void novoHistorico() {
        historico = new Historicocobranca();
        historico.setData(new Date());
    }
	
	public String salvarDadosCobranca(){
        CobrancaFacade cobrancaFacade = new CobrancaFacade();
        cobranca = cobrancaFacade.salvar(cobranca);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Dados  salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "";
   }
	 
	public String salvarHitorico(){
        CobrancaFacade cobrancaFacade = new CobrancaFacade();
        if (cobranca.getIdcobranca()==null){
            cobranca = cobrancaFacade.salvar(cobranca);
            
        }
        historico.setData(new Date());
        historico.setCobranca(cobranca);
        historico.setUsuario(usuarioLogadoMB.getUsuario());
        historico = cobrancaFacade.salvar(historico);
        cobranca.getHistoricocobrancaList().add(historico);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Historico de Cobrança Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "consContasReceber";
    }
	
	public String editarHistorico() { 
        if (historico!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
        }
        return "";
    }

}
