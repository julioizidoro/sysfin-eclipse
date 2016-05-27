package br.com.financemate.manageBean.vendas;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.FormaPagamentoFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Formapagamento;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class GerarParcelaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vendas vendas;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Vendas> listaVendas;
	private Formapagamento formapagamento;
	private List<Formapagamento> listaFormapagamento;
	private Contasreceber contasReceber;
	private Banco banco;
	private Cliente cliente;
	private String tipoDocumento;
	private String vezes;
	private Planocontas planocontas;
	private List<Contasreceber> listarContasreceber;
	private Date dataVencimento;
	private Float valorParcela;
	private List<Formapagamento> listaFormaPagamento;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        vezes = (String) session.getAttribute("vezes");
        tipoDocumento = (String) session.getAttribute("tipoDocumento");
        dataVencimento = (Date) session.getAttribute("dataVencimento");
        valorParcela = (Float) session.getAttribute("valorParcela");
        session.removeAttribute("contasreceber");
        session.removeAttribute("vezes");
        session.removeAttribute("valorParcela");
        session.removeAttribute("dataVencimento");
        session.removeAttribute("tipoDocumento");
        if (contasReceber == null) {
			contasReceber = new Contasreceber();
		}
        //gerarListaFormaPagamento();
        gerarListaParcelas();
	}
	 
	

	public List<Formapagamento> getListaFormaPagamento() {
		return listaFormaPagamento;
	}



	public void setListaFormaPagamento(List<Formapagamento> listaFormaPagamento) {
		this.listaFormaPagamento = listaFormaPagamento;
	}



	public Date getDataVencimento() {
		return dataVencimento;
	}



	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}



	public Float getValorParcela() {
		return valorParcela;
	}



	public void setValorParcela(Float valorParcela) {
		this.valorParcela = valorParcela;
	}



	public List<Contasreceber> getListarContasreceber() {
		return listarContasreceber;
	}



	public void setListarContasreceber(List<Contasreceber> listarContasreceber) {
		this.listarContasreceber = listarContasreceber;
	}



	public Planocontas getPlanocontas() {
		return planocontas;
	}



	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}



	public String getVezes() {
		return vezes;
	}



	public void setVezes(String vezes) {
		this.vezes = vezes;
	}



	public String getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
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



	public Formapagamento getFormapagamento() {
		return formapagamento;
	}



	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}



	public List<Formapagamento> getListaFormapagamento() {
		return listaFormapagamento;
	}



	public void setListaFormapagamento(List<Formapagamento> listaFormapagamento) {
		this.listaFormapagamento = listaFormapagamento;
	}



	public List<Vendas> getListaVendas() {
		return listaVendas;
	}



	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}



	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String editarParcela(Contasreceber contasreceber){
		if (contasreceber != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contasreceber", contasreceber);
            session.setAttribute("vendas", vendas);
		}
		return "editarParcela";
	}
	
	public String CancelarEdicao(){
		return "gerarParcelas";
	}
	
	public String Fechar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.removeAttribute("vendas");
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
	}
	
	public void SalvarParcela(){
		if (vezes != "" && valorParcela != 0f  && dataVencimento != null) {
			if (valorParcela <= saldoTotal()) {
					Integer numerovezes = Integer.parseInt(vezes); 
					Contasreceber contasreceber = new Contasreceber();
					contasreceber.setDataVencimento(dataVencimento);
					for (int i = 0; i < numerovezes; i++) {
						ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
						VendasFacade vendasFacade = new VendasFacade();
						ClienteFacade clienteFacade = new ClienteFacade();
						BancoFacade bancoFacade = new BancoFacade();
						PlanoContasFacade planoContasFacade = new PlanoContasFacade();
						if (valorParcela < 0) {
							contasreceber.setValorParcela(valorParcela/numerovezes * (-1));
						}else{
							contasreceber.setValorParcela(valorParcela/numerovezes);
						}
						contasreceber.setTipodocumento(tipoDocumento);
						contasreceber.setUsuario(usuarioLogadoMB.getUsuario());
						contasreceber.setNomeCliente(vendas.getNomeCliente());
						contasreceber.setJuros(0f); 
						contasreceber.setDesagio(0f);
						contasreceber.setValorPago(0f);
						contasreceber.setNumeroDocumento(""+vendas.getIdvendas());
						contasreceber.setVenda(vendas.getIdvendas());
						contasreceber.setNumeroParcela(i+1);
						if (vendas.getCliente() != null) { 
							try {
								banco = bancoFacade.consultar(vendas.getCliente().getIdcliente(), "Nenhum");
								contasreceber.setBanco(banco);
								contasreceber.setCliente(vendas.getCliente());
								planocontas = planoContasFacade.consultar(1);
								contasreceber.setPlanocontas(planocontas);
								
							} catch (SQLException e) {
								Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
					            mostrarMensagem(e, "Erro ao consultar um banco", "Erro");
							} 
						}else{
							try {
								banco = bancoFacade.consultar(8, "Nenhum");
								contasreceber.setBanco(banco);
								cliente = clienteFacade.consultar(8);
								contasreceber.setCliente(cliente);
								planocontas = planoContasFacade.consultar(1);
								contasreceber.setPlanocontas(planocontas);
							} catch (SQLException e) {
								Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
					            mostrarMensagem(e, "Erro ao consultar um banco", "Erro");
							}
							
						}
						Contasreceber copia = new Contasreceber();
						copia = contasreceber;
						contasreceber = contasReceberFacade.salvar(contasreceber);
						if (contasreceber.getIdcontasReceber() != null) {
							vendas.setSituacao("Parcela Gerada");
							try {
								vendasFacade.salvar(vendas);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						Calendar c = new GregorianCalendar();
						c.setTime(copia.getDataVencimento());
						c.add(Calendar.MONTH, 1);
						Date data = c.getTime();
						copia.setDataVencimento(data);
						if (i < numerovezes) {
							contasreceber = new Contasreceber();
							contasreceber = copia;
						}
					}
					gerarListaParcelas();
					valorParcela = null;
					vezes = null;
					tipoDocumento = null;
					dataVencimento = null;
			}else{
				mensagem mensagem = new mensagem();
				if (valorParcela > saldoTotal()) {	
					mensagem.valorAcimaPermitidoGerarParcela();
				}else{
					mensagem.valorAbaixoPermitidoGerarParcela();
				}
			}
		}else{
			mensagem mensagem = new mensagem();
			mensagem.informacaoNaoPreenchida();
		}
	}
	
	public void SemParcela(){
		VendasFacade vendasFacade = new VendasFacade();
		List<Vendas> listaVendas = null;
		try {
			listaVendas = vendasFacade.listar("Select v from Vendas v where v.idvendas=" + vendas.getIdvendas());
			if (listaVendas == null) {
				listaVendas = new ArrayList<Vendas>();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i < listaVendas.size(); i++) {
			listaVendas.get(i).setSituacao("Sem Parcela");
			try {
				vendasFacade.salvar(listaVendas.get(i));
			} catch (SQLException e) {
				Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
	            mostrarMensagem(e, "Erro ao tentar salvar sem parcela", "Erro");
			}
		} 
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void gerarListaFormaPagamento(){
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		try {
			listaFormapagamento = formaPagamentoFacade.listar(vendas.getIdvendas());
			if (listaFormapagamento == null) {
				listaFormapagamento = new ArrayList<Formapagamento>();
			}
		} catch (SQLException e) {
			Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
            mostrarMensagem(e, "Erro ao listar formas de pagamento", "Erro");
		}
	}
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public Float saldoTotal(){
		Float valortotal = 0f;
		for (int i = 0; i < listarContasreceber.size(); i++) {
			valortotal =  valortotal +  listarContasreceber.get(i).getValorParcela(); 
		}
		Float saldo = 0f;
		saldo = vendas.getValorLiquido() - valortotal;
		return saldo;
	} 
	
	public void gerarListaParcelas(){
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		try {
			listarContasreceber = contasReceberFacade.listar("Select c from Contasreceber c Join Vendas v on c.venda=v.idvendas"
					+ " where c.venda=" + vendas.getIdvendas());
			if (listarContasreceber == null) {
				listarContasreceber = new ArrayList<Contasreceber>();
			}
		} catch (SQLException e) {
			Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
            mostrarMensagem(e, "Erro ao listar Contas a receber", "Erro");
		}
	}
	
	
	public String recebimento(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
        if (listaFormaPagamento == null) {
        	listaFormaPagamento = new ArrayList<Formapagamento>(); 
        }
        session.setAttribute("listaFormaPagamento", listaFormaPagamento);
		return "selecionarForma";
	}
	
	
}
