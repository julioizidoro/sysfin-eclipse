package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.manageBean.CalculosContasMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;

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
    
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContasSelecionadas =  (List<Contasreceber>) session.getAttribute("listaContasSelecionadas");
		totalReceberLote = (String) session.getAttribute("totalReceberLote");
		contasReceber = (Contasreceber) session.getAttribute("contasReceber");
		contasReceber.setDataPagamento(new Date());
		session.removeAttribute("totalReceberLote");
		session.removeAttribute("listaContasSelecionadas");
		session.removeAttribute("contasReceber");
		if (contasReceber == null) {
            contasReceber = new Contasreceber();
        }
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
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			contasReceber = contasReceberFacade.salvar(listaContasSelecionadas.get(i));
		}
		RequestContext.getCurrentInstance().closeDialog(contasReceber);
		return "";
	} 


	 public String cancelar() {
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return null;
	 }
	 
	 


}
