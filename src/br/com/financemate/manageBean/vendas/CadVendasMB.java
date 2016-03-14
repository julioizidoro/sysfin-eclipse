package br.com.financemate.manageBean.vendas;

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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.ProdutoFacade;
import br.com.financemate.manageBean.CadContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Formapagamento;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Produto;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class CadVendasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cliente> listaCliente;
	private Cliente cliente;
	private Vendas vendas;
	private Boolean habilitarUnidade;
	private Produto produto;
	private List<Produto> listaProduto;
	private Planocontas planocontas;
	private List<Planocontas> listaPlanocontas;
	private String TipoDocumento;
	private Formapagamento formapagamento;
	private Boolean habilitarCampos = true;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        session.removeAttribute("vendas");
		gerarListaCliente();
		if (vendas == null) {
			vendas = new Vendas();
		}
		gerarListaPlanoContas();
	}
	
	
	
	public Boolean getHabilitarCampos() {
		return habilitarCampos;
	}



	public void setHabilitarCampos(Boolean habilitarCampos) {
		this.habilitarCampos = habilitarCampos;
	}



	public Formapagamento getFormapagamento() {
		return formapagamento;
	}



	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}



	public String getTipoDocumento() {
		return TipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		TipoDocumento = tipoDocumento;
	}



	public Planocontas getPlanocontas() {
		return planocontas;
	}



	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}



	public List<Planocontas> getListaPlanocontas() {
		return listaPlanocontas;
	}



	public void setListaPlanocontas(List<Planocontas> listaPlanocontas) {
		this.listaPlanocontas = listaPlanocontas;
	}



	public Produto getProduto() {
		return produto;
	}



	public void setProduto(Produto produto) {
		this.produto = produto;
	}



	public List<Produto> getListaProduto() {
		return listaProduto;
	}



	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}



	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
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



	public void gerarListaCliente() {
		ClienteFacade clienteFacade = new ClienteFacade();
		try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>(); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
	
	public void gerarListaProduto(){
		if (cliente!=null) {
	        ProdutoFacade produtoFacade = new ProdutoFacade();
	        try {
				listaProduto = produtoFacade.listar(cliente.getIdcliente());
				if (listaProduto ==null){
		        	listaProduto = new ArrayList<Produto>();
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}else {
			listaProduto = new ArrayList<Produto>();
        }
    }
	
	public String backOffice(){
		return "cadBackOffice";
	}
	
	public String dadosVenda(){
		return "cadVendas";
	}
	
	public String adicionarConta(){
		return "adicionarConta";
	}
	
	public String recebimento(){
		return "cadRecebimento";
	}
	
	public void gerarListaPlanoContas() {
        PlanoContasFacade planoContasFacade = new PlanoContasFacade();
        try {
             listaPlanocontas = planoContasFacade.listar();
            if (listaPlanocontas == null) {
            	listaPlanocontas = new ArrayList<Planocontas>();
            }
        } catch (Exception ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
        }
        
    }
	
	public String nomeConta(){
	//	if (vendas.getValorLiquido() > 0) {
//			return "Lançar Conta a Pagar";
	//	}else if (vendas.getValorLiquido() < 0){
	//		return "Lançar Conta a Receber";
	//	}else{
			return "oi";
	//	}
	}
	
	public String voltarCadastro(){
		return "cadBackOffice";
	}
	
	public String formaPagamento(){
		return "lancaFormaPagamento";
	}
	
	public String notaFiscal(){
		return "notaFiscal";
	}
	
	public Boolean desabilitarCampos(){
		if (TipoDocumento.equalsIgnoreCase("Boleto")) {
			return habilitarCampos = false;
		}
		return habilitarCampos;
	}
	

}
