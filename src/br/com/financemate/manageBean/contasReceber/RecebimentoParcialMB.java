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
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		contasReceber = (Contasreceber) session.getAttribute("contareceber");
		if (contasReceber!=null) {
			listaRecebimentoParcial = contasReceber.getRecebimentoParcialList();
		}else{
			listaRecebimentoParcial = new ArrayList<Contasreceber>();
		}
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
