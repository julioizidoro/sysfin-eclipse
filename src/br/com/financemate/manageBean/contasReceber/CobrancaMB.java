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
import br.com.financemate.facade.CobrancaParcelasFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.HistoricoCobrancaFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Cobrancaparcelas;
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
    private List<Contasreceber> listaContasSelecionadas;
    private Cobrancaparcelas cobrancaParcela;
    
    @PostConstruct
    public void init(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        //listaContasSelecionadas = (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
        if (listaContasSelecionadas == null) {
			listaContasSelecionadas = new ArrayList<Contasreceber>();
			listaContasSelecionadas.add(contasReceber);
		}
    	gerarListaCliente();  
        cliente = contasReceber.getCliente();
        cobranca = (Cobranca) session.getAttribute("cobranca");
        if (cobranca == null) {
			CobrancaFacade cobrancaFacade = new CobrancaFacade();
			cobranca = cobrancaFacade.consultar("Select c From Cobranca c Join Cobrancaparcelas cp on"
					+ " c.idcobranca=cp.cobranca.idcobranca Join Contasreceber co on cp.contasreceber.idcontasReceber=co.idcontasReceber"
					+ " Where cp.contasreceber.idcontasReceber=" + contasReceber.getIdcontasReceber() );
		} 
        if (cobranca == null) {
        	cobranca = new Cobranca();
        	listaHistorico = new ArrayList<Historicocobranca>();
        }else{
        	gerarListaHistorico(); 
        	if (listaHistorico == null) {
				listaHistorico = new ArrayList<Historicocobranca>();
			}
        }
    } 
    
    
    
    
    

	public Cobrancaparcelas getCobrancaParcela() {
		return cobrancaParcela;
	}

	public void setCobrancaParcela(Cobrancaparcelas cobrancaParcela) {
		this.cobrancaParcela = cobrancaParcela;
	}

	public List<Contasreceber> getListaContasSelecionadas() {
		return listaContasSelecionadas;
	}

	public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
		this.listaContasSelecionadas = listaContasSelecionadas;
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
	
	public String novoHistorico() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		FacesContext fc = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	    session.setAttribute("contasReceber", contasReceber);
	    session.setAttribute("cobranca", cobranca);
		return "historico";
    }
	
	public String salvarDadosCobranca(){
        CobrancaFacade cobrancaFacade = new CobrancaFacade();
        cobranca.setVencimentooriginal(contasReceber.getDataVencimento());
        cobranca = cobrancaFacade.salvar(cobranca);
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        contasReceberFacade.salvar(contasReceber);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Dados  salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "";
   }
	
	
	public String novoHistoricos() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		FacesContext fc = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	    session.setAttribute("contasReceber", contasReceber);
	    session.setAttribute("cobranca", cobranca);
		return "historicos";
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
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Historico de Cobran�a Salvo.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return "cobranca";
    }
	
	//Para chamada da tela, na tela principal
	public String editarHistorico(Historicocobranca historicocobranca) { 
        if (historico!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
        }
        return "editarHistorico";
    }
	
	// Para chamada da dialog dentro de algum modulo
	public String editarHistoricos(Historicocobranca historicocobranca) { 
        if (historico!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("historico", historico);
            session.setAttribute("cobranca", cobranca);
        }
        return "editarHistoricos";
    }
	
	public String cancelar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("cobranca");
        session.removeAttribute("listaContasSelecionadas");
        session.removeAttribute("contasReceber");
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }  
	
	//Para chamada da tela, na tela principal
	public String listaContasCobranca(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
		return "listaContaCobranca";
	}
	
	// Para chamada da dialog dentro de algum modulo
	public String listaContasCobrancas(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
		return "listaContaCobrancas";
	}
	
	//Para chamada da tela, na tela principal
	public String cobranca(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.getAttribute("listaHistorico");
		return "cobranca";
	}
	
	// Para chamada da dialog dentro de algum modulo
	public String cobrancas(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.getAttribute("listaHistorico");
		return "cobrancas";
	}
	
	
	//Para chamada da tela, na tela principal
	public String historicoCobranca(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        gerarListaHistorico();
		return "historicoCobranca";
	}
	
	
	// Para chamada da dialog dentro de algum modulo
	public String historicoCobrancas(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cobranca", cobranca);
        gerarListaHistorico();
		return "historicoCobrancas";
	}
	
	public void gerarListaHistorico(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
    	HistoricoCobrancaFacade historicoCobrancaFacade = new HistoricoCobrancaFacade();
    	if (cobranca == null) {
    		cobranca = (Cobranca) session.getAttribute("cobranca");
		}
        listaHistorico = historicoCobrancaFacade.listar("Select h from Historicocobranca h Where h.cobranca.idcobranca=" + cobranca.getIdcobranca());
		if (listaHistorico == null) {
			listaHistorico = new ArrayList<Historicocobranca>();
		}
    }
	
	
	public void salvarCobrancasParcelas(){
		List<Cobrancaparcelas> listaCobrancaParcelas;
		CobrancaParcelasFacade cobrancaParcelasFacade = new CobrancaParcelasFacade();
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cobranca = (Cobranca) session.getAttribute("cobranca");
		contasReceber = (Contasreceber) session.getAttribute("contasreceber");
		try {
			String sql = "Select cp From Cobrancaparcelas cp Join Contasreceber c on  cp.contasreceber.idcontasReceber=c.idcontasReceber";
			sql = sql + " Join Cobranca co on cp.cobranca.idcobranca=co.idcobranca Where cp.contasreceber.idcontasReceber=" + contasReceber.getIdcontasReceber();
			sql = sql + " and cp.cobranca.idcobranca=" + cobranca.getIdcobranca();
			listaCobrancaParcelas = cobrancaParcelasFacade.listarCobranca(sql);
			if (listaCobrancaParcelas.size() == 0) {
				for (int i = 0; i < listaContasSelecionadas.size(); i++) {
					if (cobranca != null && contasReceber != null) {
						cobrancaParcela = new Cobrancaparcelas();
						cobrancaParcela.setCobranca(cobranca);
						cobrancaParcela.setContasreceber(listaContasSelecionadas.get(i));
						cobrancaParcelasFacade.salvar(cobrancaParcela);
						session.removeAttribute("listaContasSelecionadas");
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
