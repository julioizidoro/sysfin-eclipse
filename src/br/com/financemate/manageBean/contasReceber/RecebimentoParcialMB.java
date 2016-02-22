package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;

@Named
@ViewScoped
public class RecebimentoParcialMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private UsuarioLogadoMB usuarioLogadoMB;
	 private Cliente cliente;
	 private Contasreceber contasReceber;
	 private List<Contasreceber> listaRecebimentoParcial;
	 private Float valorPagoParcial;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		contasReceber = (Contasreceber) session.getAttribute("contareceber");
		valorPagoParcial = (Float) session.getAttribute("valorPagoParcial");
		if (valorPagoParcial > 0f) {
			listaRecebimentoParcial = contasReceber.getRecebimentoParcialList();
			listaRecebimentoParcial = new ArrayList<Contasreceber>();
		}else{
			listaRecebimentoParcial = new ArrayList<Contasreceber>();
		}
	}
	
	

	public Float getValorPagoParcial() {
		return valorPagoParcial;
	}



	public void setValorPagoParcial(Float valorPagoParcial) {
		this.valorPagoParcial = valorPagoParcial;
	}



	public List<Contasreceber> getListaRecebimentoParcial() {
		return listaRecebimentoParcial;
	}



	public void setListaRecebimentoParcial(List<Contasreceber> listaRecebimentoParcial) {
		this.listaRecebimentoParcial = listaRecebimentoParcial;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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
	
	public String voltar(){
		return "recebimentoConta";
	}

}
