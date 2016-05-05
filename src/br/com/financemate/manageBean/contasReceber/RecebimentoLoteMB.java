package br.com.financemate.manageBean.contasReceber;

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

import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.manageBean.CalculosContasMB;
import br.com.financemate.manageBean.LiberarContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Outroslancamentos;

@Named
@ViewScoped
public class RecebimentoLoteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

    private List<Contasreceber> listaContasSelecionadas;
    private String totalReceberLote;
    private Contasreceber contasReceber;
    private CalculosContasMB calcularContasMB;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Banco banco;
    private Cliente cliente;
    private Date dataPagamento;
    
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContasSelecionadas =  (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
		totalReceberLote = (String) session.getAttribute("totalReceberLote");
		contasReceber = (Contasreceber) session.getAttribute("contasReceber");
		session.removeAttribute("totalReceberLote");
		session.removeAttribute("contasReceber");
		if (contasReceber == null) {
            contasReceber = new Contasreceber();
        }
		dataPagamento = new Date(); 
		if (listaContasSelecionadas == null) {
			listaContasSelecionadas = new  ArrayList<Contasreceber>();
		}
	}
	

	

	public Date getDataPagamento() {
		return dataPagamento;
	}




	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}




	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}




	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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




	public CalculosContasMB getCalcularContasMB() {
		return calcularContasMB;
	}

	public void setCalcularContasMB(CalculosContasMB calcularContasMB) {
		this.calcularContasMB = calcularContasMB;
	}

	public Contasreceber getContasReceber() {
		return contasReceber;
	}



	public void setContasReceber(Contasreceber contasReceber) {
		this.contasReceber = contasReceber;
	}



	public List<Contasreceber> getListaContasSelecionadas() {
		return listaContasSelecionadas;
	}

	public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
		this.listaContasSelecionadas = listaContasSelecionadas;
	}

	public String getTotalReceberLote() {
		return totalReceberLote;
	}

	public void setTotalReceberLote(String totalReceberLote) {
		this.totalReceberLote = totalReceberLote;
	}


	
	public String salvarContasReceberLote() {
		for (int i = 0; i < listaContasSelecionadas.size(); i++) {
			contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
			listaContasSelecionadas.get(i).setUsuario(usuarioLogadoMB.getUsuario());
			listaContasSelecionadas.get(i).setDataPagamento(dataPagamento);
			listaContasSelecionadas.get(i).setValorPago(listaContasSelecionadas.get(i).getValorParcela());
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			contasReceber = contasReceberFacade.salvar(listaContasSelecionadas.get(i));
			lancaOutrosLancamentos(listaContasSelecionadas.get(i));
		}
		RequestContext.getCurrentInstance().closeDialog(contasReceber);
		return "";
	} 


	 public String cancelar() {
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return null;
	 }
	 
	 
	 public void lancaOutrosLancamentos(Contasreceber conta) {
	       Outroslancamentos outroslancamentos = new Outroslancamentos();
	       outroslancamentos.setBanco(conta.getBanco());
	       outroslancamentos.setCliente(conta.getCliente());
	       outroslancamentos.setDataVencimento(conta.getDataVencimento());
	       outroslancamentos.setDataCompensacao(conta.getDataPagamento());
	       outroslancamentos.setDataRegistro(new Date());
	       outroslancamentos.setPlanocontas(conta.getPlanocontas());
	       outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
	       outroslancamentos.setValorEntrada(conta.getValorPago());
	       outroslancamentos.setValorSaida(0f);
	       outroslancamentos.setDataRegistro(new Date());
	       outroslancamentos.setDescricao("Recebimento através do contas a receber de " + conta.getNomeCliente());
	       OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
	       try {
	    	   outroslancamentos.setIdcontasreceber(conta.getIdcontasReceber());
	    	   outroslancamentos = outrosLancamentosFacade.salvar(outroslancamentos);
	       } catch (SQLException ex) {
	           Logger.getLogger(LiberarContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
	           mostrarMensagem(ex, "Erro ao salvar liberação", "Erro");
	       }
	       
	   }
	 
	 public void mostrarMensagem(Exception ex, String erro, String titulo){
	        FacesContext context = FacesContext.getCurrentInstance();
	        erro = erro + " - " + ex;
	        context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	 
	 
	 public String editarBanco(Contasreceber conta){
	    	FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("conta", conta);
			session.setAttribute("totalReceberLote", totalReceberLote);
	    	return "editarBancoRecebimentoLote"; 
 }
	 


}
