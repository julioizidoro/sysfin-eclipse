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

import org.kohsuke.rngom.parse.Parseable;
import org.primefaces.context.RequestContext;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.FormaPagamentoFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.contasReceber.CadContasReceberMB;
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
	private Contasreceber contasreceber;
	private Banco banco;
	private Cliente cliente;
	private String tipoDocumento;
	private String vezes;
	private Planocontas planocontas;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        contasreceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        if (contasreceber == null) {
			contasreceber = new Contasreceber();
		}
        gerarListaFormaPagamento();
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



	public Contasreceber getContasreceber() {
		return contasreceber;
	}



	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
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
		if (vezes != null) {
			Integer numerovezes = Integer.parseInt(vezes);
			for (int i = 0; i < numerovezes; i++) {
				Contasreceber contasreceber = new Contasreceber();
				ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
				ClienteFacade clienteFacade = new ClienteFacade();
				BancoFacade bancoFacade = new BancoFacade();
				PlanoContasFacade planoContasFacade = new PlanoContasFacade();
				contasreceber.setDataVencimento(vendas.getDataVenda());
				contasreceber.setValorParcela(vendas.getValorLiquido() * (-1));
				contasreceber.setTipodocumento(tipoDocumento);
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
		}
	}
	
	public void SemParcela(){
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		for (int i = 0; i < listaFormapagamento.size(); i++) {
			listaFormapagamento.get(i).setParcelaGerada("Sim");
			try {
				formaPagamentoFacade.salvar(listaFormapagamento.get(i));
			} catch (SQLException e) {
				Logger.getLogger(GerarParcelaMB.class.getName()).log(Level.SEVERE, null, e);
	            mostrarMensagem(e, "Erro ao tentar salvar sem parcela", "Erro");
			}
		}
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
		for (int i = 0; i < listaFormapagamento.size(); i++) {
			valortotal =  valortotal + listaFormapagamento.get(i).getValor(); 
		}
		Float saldo = 0f;
		saldo = vendas.getValorLiquido() - valortotal;
		return saldo;
	}
	
	
}
