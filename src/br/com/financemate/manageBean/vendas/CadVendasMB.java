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

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.FormaPagamentoFacade;
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
import br.com.financemate.model.Outroslancamentos;
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
	private List<Formapagamento> listaFormaPagamento;
	private Float valorPagarReceber;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendas = (Vendas) session.getAttribute("vendas");
		cliente = (Cliente) session.getAttribute("cliente");
		produto = (Produto) session.getAttribute("produto");
		listaFormaPagamento = (List<Formapagamento>) session.getAttribute("listaFormaPagamento");
		valorPagarReceber = (Float) session.getAttribute("valorPagarReceber");
		gerarListaCliente(); 
		if (vendas == null) {
			vendas = new Vendas();
		}else{
			if (cliente == null) {
				cliente = vendas.getCliente();
			}
			gerarListaProduto();
			if (produto == null) {
				produto = vendas.getProduto();
			}
			planocontas = vendas.getPlanocontas();
			if (vendas.getIdvendas() != null) {
				ConsultarEmissaoNota();
			}
			gerarListaFormaPagamento();
		}
		if (emissaonota == null) {
			emissaonota = new Emissaonota();
		}
		if (formapagamento == null) {
			formapagamento = new Formapagamento();
		}
		gerarListaPlanoContas();
	}
	
	
	
	
	public Float getValorPagarReceber() {
		return valorPagarReceber;
	}




	public void setValorPagarReceber(Float valorPagarReceber) {
		this.valorPagarReceber = valorPagarReceber;
	}




	public List<Formapagamento> getListaFormaPagamento() {
		return listaFormaPagamento;
	}




	public void setListaFormaPagamento(List<Formapagamento> listaFormaPagamento) {
		this.listaFormaPagamento = listaFormaPagamento;
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
				 Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, e);
		            mostrarMensagem(e, "Erro ao listar o produto:", "Erro");
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
        session.setAttribute("cliente", cliente);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
		return "cadBackOffice";
	}
	
	public String dadosVenda(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
        return "cadVendas";
	}
	
	public String adicionarConta(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("valorPagarReceber", valorPagarReceber);
		return "adicionarConta";
	}
	
	public String recebimento(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        if (listaFormaPagamento == null) {
        	listaFormaPagamento = new ArrayList<Formapagamento>(); 
        }
        session.setAttribute("listaFormaPagamento", listaFormaPagamento);
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
		if (valorPagarReceber > 0) {
			return "Lan�ar Conta a Pagar";
		}else if (valorPagarReceber < 0){
			return "Lan�ar Conta a Receber";
		}else{
			return "Valor Zerado";
		}
	}
	
	public String voltarCadastro(){
		return "cadBackOffice";
	}
	
	public String formaPagamento(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
		return "lancaFormaPagamento";
	}
	
	public String notaFiscal(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        session.setAttribute("planocontas", planocontas);
        session.setAttribute("produto", produto);
        session.setAttribute("cliente", cliente);
		return "notaFiscal";
	}
	
	public Boolean desabilitarCampos(){
		if (TipoDocumento.equalsIgnoreCase("Boleto")) {
			habilitarCampos = false;
			return habilitarCampos;
		}
		habilitarCampos = true;
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
		if (vendas.getDespesasFinanceiras() == null) {
			vendas.setDespesasFinanceiras(0f);
		}
		
		vendas.setLiquidoVendas(vendas.getComissaoLiquidaTotal() - (vendas.getDespesasFinanceiras() + vendas.getComissaoFuncionarios() + vendas.getComissaoTerceiros()));
		valorPagarReceber = vendas.getValorLiquido() - (vendas.getLiquidoVendas() + vendas.getValorPagoFornecedor());
		
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
			contaspagar.setDescricao("Conta gerada pela venda");
			contaspagar.setContaPaga("N");
			contaspagar.setDataVencimento(vendas.getDataVenda());
			contaspagar.setFornecedor(vendas.getNomeFornecedor());
			if (usuarioLogadoMB.getCliente() != null) {
				try {
					banco = bancoFacade.consultar(usuarioLogadoMB.getCliente().getIdcliente(), "Nenhum");
					contaspagar.setBanco(banco);
					contaspagar.setCliente(usuarioLogadoMB.getCliente());
				} catch (SQLException ex) {
					Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
					mostrarMensagem(ex, "Erro ao salvar uma conta com cliente do usuario diferente de null:", "Erro");
				}
			}else{
				try {
					banco = bancoFacade.consultar(8, "Nenhum");
					contaspagar.setBanco(banco);
					cliente = clienteFacade.consultar(8);
					contaspagar.setCliente(cliente);
				} catch (SQLException ex) {
					Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
					mostrarMensagem(ex, "Erro ao salvar uma conta com o cliente do usuario igual a null:", "Erro");
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
			contasreceber.setNumeroParcela(1);
			
			if (usuarioLogadoMB.getCliente() != null) { 
				try {
					banco = bancoFacade.consultar(usuarioLogadoMB.getCliente().getIdcliente(), "Nenhum");
					contasreceber.setBanco(banco);
					contasreceber.setCliente(usuarioLogadoMB.getCliente());
					
				} catch (SQLException ex) {
					Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
					mostrarMensagem(ex, "Erro ao consultar banco e cliente  com cliente do usuario diferente de null:", "Erro");
				}
			}else{
				try {
					banco = bancoFacade.consultar(8, "Nenhum");
					contasreceber.setBanco(banco);
					cliente = clienteFacade.consultar(8);
					contasreceber.setCliente(cliente);
				} catch (SQLException ex) {
					Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
					mostrarMensagem(ex, "Erro ao consultar banco e cliente com o cliente do usuario igual a null:", "Erro");
				}
				
			}
			contasreceber = contasReceberFacade.salvar(contasreceber);
			contasreceber.setNumeroDocumento(""+contasreceber.getIdcontasReceber());
			contasreceber = contasReceberFacade.salvar(contasreceber);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("planocontas", planocontas);
		return "cadBackOffice";
	}
	
	
	public Float saldoRestante(){
		float valorTotalForma = 0f;
		for (int i = 0; i < listaFormaPagamento.size(); i++) {
			valorTotalForma = valorTotalForma + listaFormaPagamento.get(i).getValor();
		}
		if (vendas.getValorLiquido() == null) {
			saldoRestante = 0 - valorTotalForma;
		}else{
			saldoRestante = vendas.getValorLiquido() - valorTotalForma;
		}
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
			} catch (SQLException ex) {
				Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
				mostrarMensagem(ex, "Erro ao consultar um plano de contas:", "Erro");
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
			} catch (SQLException ex) {
				Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
				mostrarMensagem(ex, "Erro ao consultar um cliente:", "Erro");
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
				if (listaFormaPagamento != null) {
					for (int i = 0; i < listaFormaPagamento.size(); i++) {
						listaFormaPagamento.get(i).setVendas(vendas);
						FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
						formaPagamentoFacade.salvar(listaFormaPagamento.get(i));
					}
				}
		        session.removeAttribute("vendas");
		        session.removeAttribute("planocontas");
		        session.removeAttribute("produto");
		        session.removeAttribute("cliente");
		        session.removeAttribute("listaFormaPagamento");
				RequestContext.getCurrentInstance().closeDialog(vendas);
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null, new FacesMessage(mensagem, ""));
			}
			 
		} catch (SQLException ex) {
			Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro ao salvar venda:", "Erro");
		} 
	}
	
	public String validarDados(){
		String mensagem = "";
		if (vendas.getNomeCliente() == null) {
			mensagem = mensagem + "Cliente n�o informado \r\n";
		}
		if (vendas.getProduto().getIdproduto() == null) {
			mensagem = mensagem + " Produto n�o informado \r\n";
		}
		if (vendas.getValorBruto() == null) {
			mensagem = mensagem + " Valor bruto n�o informado \r\n";
		}
		return mensagem;
	}
	
	public String cancelar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("vendas");
        session.removeAttribute("listaFormaPagamento");
        session.removeAttribute("planocontas");
        session.removeAttribute("produto");
        session.removeAttribute("cliente");
        session.removeAttribute("valorPagarReceber");
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	public String excluir(){
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		try {
			formaPagamentoFacade.Excluir(formapagamento.getIdformaPagamento());
			listaFormaPagamento.remove(formapagamento.getIdformaPagamento());
		} catch (SQLException ex) {
			Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro ao excluir forma de pagamento:", "Erro");
		}
        mensagem msg = new mensagem();
        msg.excluiMessagem();
        gerarListaFormaPagamento();
        salvarVendaSemFormaRecebimento();
        return "";
	}
	
	public String SalvarFormaPagamento(){
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		VendasFacade vendasFacade = new VendasFacade();
		Vendas nVenda;
		try {
			nVenda = vendasFacade.consultar(1);
			formapagamento.setTipoDocumento(TipoDocumento);
			formapagamento.setVendas(nVenda);
			formapagamento = formaPagamentoFacade.salvar(formapagamento);
			gerarListaFormaPagamento();
		} catch (SQLException ex) {
			Logger.getLogger(CadVendasMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro ao salvar uma forma de pagamento:", "Erro");
		}
		return "cadRecebimento";
	}
	
	public String voltarRecebimento(){
		return "cadRecebimento";
	}
	
	public void gerarListaFormaPagamento(){
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Vendas nVenda = null;
		try {
        	if ( vendas.getIdvendas() == null) {
    			VendasFacade vendasFacade = new VendasFacade();
    			nVenda = vendasFacade.consultar(1);
    		}else{
    			nVenda = vendas;
    		}
			listaFormaPagamento = formaPagamentoFacade.listar(nVenda.getIdvendas());
			if (listaFormaPagamento == null) {
				listaFormaPagamento = new ArrayList<Formapagamento>();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String salvarVendaSemFormaRecebimento(){
		if (listaFormaPagamento == null || listaFormaPagamento.size() == 0) {
			return "A forma de recebimento ainda n�o foi informada, deseja continuar?";
		}else{
			return "Deseja finalizar o cadastro de uma venda?";
		}
	}
	
	
	public void ConsultarEmissaoNota(){
		VendasFacade vendasFacade = new VendasFacade();
		try {
			emissaonota = vendasFacade.getEmissao(vendas.getIdvendas());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void calcularValorParcelaLancaFormaPagamento(){
		formapagamento.setValorParcela(formapagamento.getValor()/formapagamento.getNumeroParcelas());
	}

}
