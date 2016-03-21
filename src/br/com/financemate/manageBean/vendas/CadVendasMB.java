package br.com.financemate.manageBean.vendas;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.ProdutoFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.manageBean.CadContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Emissaonota;
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
	private Float valorAddConta;
	private String competencia;
	private Banco banco;
	private Float saldoRestante;
	private Emissaonota emissaonota;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
		gerarListaCliente();
		if (vendas == null) {
			vendas = new Vendas();
			
		}
		if (emissaonota == null) {
			emissaonota = new Emissaonota();
		}
		gerarListaPlanoContas();
	}
	
	
	
	
	public Emissaonota getEmissaonota() {
		return emissaonota;
	}




	public void setEmissaonota(Emissaonota emissaonota) {
		this.emissaonota = emissaonota;
	}




	public Float getSaldoRestante() {
		return saldoRestante;
	}




	public void setSaldoRestante(Float saldoRestante) {
		this.saldoRestante = saldoRestante;
	}




	public Banco getBanco() {
		return banco;
	}




	public void setBanco(Banco banco) {
		this.banco = banco;
	}




	public String getCompetencia() {
		return competencia;
	}



	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}



	public Float getValorAddConta() {
		return valorAddConta;
	}



	public void setValorAddConta(Float valorAddConta) {
		this.valorAddConta = valorAddConta;
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
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
		return "cadBackOffice";
	}
	
	public String dadosVenda(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
		return "cadVendas";
	}
	
	public String adicionarConta(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
		return "adicionarConta";
	}
	
	public String recebimento(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
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
		if (vendas.getValorLiquido() > 0) {
			return "Lançar Conta a Pagar";
		}else if (vendas.getValorLiquido() < 0){
			return "Lançar Conta a Receber";
		}else{
			return "Valor Zerado";
		}
	}
	
	public String voltarCadastro(){
		return "cadBackOffice";
	}
	
	public String formaPagamento(){
		return "lancaFormaPagamento";
	}
	
	public String notaFiscal(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
		return "notaFiscal";
	}
	
	public Boolean desabilitarCampos(){
		if (TipoDocumento.equalsIgnoreCase("Boleto")) {
			return habilitarCampos = false;
		}
		return habilitarCampos;
	}
	
	public void calculoTotalVenda(){
		if (vendas.getValorBruto() == null) {
			vendas.setValorBruto(0f);
		}
		if (vendas.getValorDesconto() == null) {
			vendas.setValorDesconto(0f);
		}
		if (vendas.getValorJuros() == null) {
			vendas.setValorJuros(0f);
		}
		vendas.setValorLiquido((vendas.getValorBruto() - vendas.getValorDesconto()) + vendas.getValorJuros());
	}
	
	
	public void calculoValoresBackOffice(){
		if (vendas.getValorBruto() == null) {
			vendas.setValorBruto(0f);
		}
		if (vendas.getComissaoLiquidaTotal() == null) {
			vendas.setComissaoLiquidaTotal(0f);
		}
		if (vendas.getComissaoFuncionarios() == null) {
			vendas.setComissaoFuncionarios(0f);
		}
		if (vendas.getComissaoTerceiros() == null) {
			vendas.setComissaoTerceiros(0f);
		}
		if (vendas.getValorPagoFornecedor() == null) {
			vendas.setValorPagoFornecedor(0f);
		}
		
		vendas.setLiquidoVendas(vendas.getComissaoLiquidaTotal() - (vendas.getDespesasFinanceiras() + vendas.getComissaoFuncionarios() + vendas.getComissaoTerceiros()));
		vendas.setValorLiquido(vendas.getValorBruto() - (vendas.getComissaoLiquidaTotal() + vendas.getValorPagoFornecedor()));
		
	}
	
	
	public String salvarConta(){
		if (vendas.getValorLiquido() > 0) {
			Contaspagar contaspagar = new Contaspagar();
			ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			BancoFacade bancoFacade = new BancoFacade();
			ClienteFacade clienteFacade = new ClienteFacade();
			contaspagar.setDataEnvio(vendas.getDataVenda());
			contaspagar.setValor(vendas.getValorLiquido());
			contaspagar.setPlanocontas(planocontas);
			contaspagar.setTipoDocumento(TipoDocumento);
			contaspagar.setCompetencia(competencia);
			if (usuarioLogadoMB.getCliente() != null) {
				try {
					banco = bancoFacade.consultar(usuarioLogadoMB.getCliente().getIdcliente(), "Nenhum");
					contaspagar.setBanco(banco);
					contaspagar.setCliente(usuarioLogadoMB.getCliente());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					banco = bancoFacade.consultar(8, "Nenhum");
					contaspagar.setBanco(banco);
					cliente = clienteFacade.consultar(8);
					contaspagar.setCliente(cliente);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			contaspagar = contasPagarFacade.salvar(contaspagar);
		}else if(vendas.getValorLiquido() < 0 ){
			Contasreceber contasreceber = new Contasreceber();
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			ClienteFacade clienteFacade = new ClienteFacade();
			BancoFacade bancoFacade = new BancoFacade();
			contasreceber.setDataVencimento(vendas.getDataVenda());
			contasreceber.setValorParcela(vendas.getValorLiquido() * (-1));
			contasreceber.setPlanocontas(planocontas);
			contasreceber.setTipodocumento(TipoDocumento);
			contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
			contasreceber.setNomeCliente(vendas.getNomeCliente());
			contasreceber.setJuros(0f);
			contasreceber.setDesagio(0f);
			contasreceber.setValorPago(0f);
			
			if (usuarioLogadoMB.getCliente() != null) { 
				try {
					banco = bancoFacade.consultar(usuarioLogadoMB.getCliente().getIdcliente(), "Nenhum");
					contasreceber.setBanco(banco);
					contasreceber.setCliente(usuarioLogadoMB.getCliente());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					banco = bancoFacade.consultar(8, "Nenhum");
					contasreceber.setBanco(banco);
					cliente = clienteFacade.consultar(8);
					contasreceber.setCliente(cliente);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			contasreceber = contasReceberFacade.salvar(contasreceber);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("planocontas", planocontas);
		return "cadBackOffice";
	}
	
	
	public Float saldoRestante(){
		//saldoRestante = vendas.getValorLiquido() - 
		return saldoRestante;
	}
	
	public void salvarVenda(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		planocontas = (Planocontas) session.getAttribute("planocontas");
		if (produto == null) { 
			produto = (Produto) session.getAttribute("produto");
		}
		VendasFacade vendasFacade = new VendasFacade();
		ClienteFacade clienteFacade = new ClienteFacade();
		vendas.setUsuario(usuarioLogadoMB.getUsuario());
		vendas.setProduto(produto);
		if (planocontas == null) {
			PlanoContasFacade planoContasFacade = new PlanoContasFacade();
			try {
				planocontas = planoContasFacade.consultar(1);
				vendas.setPlanocontas(planocontas);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			vendas.setPlanocontas(planocontas);
		}
		if (vendas.getValorLiquido() < 0) {
			vendas.setValorLiquido(vendas.getValorLiquido() * (-1));
		}
		if (vendas.getFormapagamentoList() == null) {
			vendas.setSituacao("vermelho");
		}else{
			vendas.setSituacao("amarelo");
		}
		if (usuarioLogadoMB.getCliente() != null) {
			vendas.setCliente(usuarioLogadoMB.getCliente());
		}else{
			try {
				cliente = clienteFacade.consultar(8);
				vendas.setCliente(cliente);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			String mensagem = validarDados();
			if (mensagem == "") {
				vendas = vendasFacade.salvar(vendas);
				if (emissaonota != null) {
					emissaonota.setVendas(vendas);
					emissaonota = vendasFacade.salvar(emissaonota);
				}
		        session.removeAttribute("vendas");
		        session.removeAttribute("planocontas");
		        session.removeAttribute("produto");
				RequestContext.getCurrentInstance().closeDialog(vendas);
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null, new FacesMessage(mensagem, ""));
			}
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public String validarDados(){
		String mensagem = "";
		if (vendas.getNomeCliente() == null) {
			mensagem = mensagem + "Cliente não informado \r\n";
		}
		if (vendas.getProduto().getIdproduto() == null) {
			mensagem = mensagem + " Produto não informado \r\n";
		}
		if (vendas.getValorBruto() == null) {
			mensagem = mensagem + " Valor bruto não informado \r\n";
		}
		return mensagem;
	}
	


}
