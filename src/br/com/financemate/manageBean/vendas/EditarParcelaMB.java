package br.com.financemate.manageBean.vendas;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class EditarParcelaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Vendas vendas;
	private Contasreceber contasReceber;
	private List<Contasreceber> listarContasreceber;
	private String tipoDocumento;
	private Float valorEditado;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        contasReceber = (Contasreceber) session.getAttribute("contasreceber");
        session.removeAttribute("contasreceber");
        if (contasReceber == null) {
			contasReceber = new Contasreceber();
		}else{
			tipoDocumento = contasReceber.getTipodocumento();
		}
        valorEditado = contasReceber.getValorParcela();
	}

	
	

	public Float getValorEditado() {
		return valorEditado;
	}




	public void setValorEditado(Float valorEditado) {
		this.valorEditado = valorEditado;
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


	public Contasreceber getContasReceber() {
		return contasReceber;
	}


	public void setContasReceber(Contasreceber contasReceber) {
		this.contasReceber = contasReceber;
	}


	public List<Contasreceber> getListarContasreceber() {
		return listarContasreceber;
	}


	public void setListarContasreceber(List<Contasreceber> listarContasreceber) {
		this.listarContasreceber = listarContasreceber;
	}


	public String getTipoDocumento() {
		return tipoDocumento;
	}


	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	public String SalvarParcelaEditada(){
		Float valorDividir;
		Float valorDivido = 0f;
		Integer numeroParcelas;
		Float totalParcela;
		Contasreceber conta = new Contasreceber();
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> listarConta = null;
		try {
			conta = contasReceberFacade.consultar(contasReceber.getIdcontasReceber());
			conta.setValorParcela(valorEditado);
			contasReceberFacade.salvar(conta);
			listarConta = contasReceberFacade.listar("Select c From Contasreceber c where c.venda=" + contasReceber.getVenda() + " and c.valorParcela=" + contasReceber.getValorParcela());
			if (listarConta == null) {
				listarConta = new ArrayList<Contasreceber>();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numeroParcelas = listarConta.size();
		for (int i = 0; i < listarConta.size(); i++) {
			if (contasReceber.getValorParcela() < valorEditado) {
				valorDividir = valorEditado - contasReceber.getValorParcela();
				valorDivido = valorDividir/numeroParcelas;
				totalParcela = contasReceber.getValorParcela() - valorDivido;
				listarConta.get(i).setValorParcela(totalParcela);
			}else{
				valorDividir = contasReceber.getValorParcela() - valorEditado;
				valorDivido = valorDivido/numeroParcelas;
				totalParcela = contasReceber.getValorParcela() + valorDivido;
				listarConta.get(i).setValorParcela(totalParcela);
			}
			
			contasReceberFacade.salvar(listarConta.get(i));
		} 
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", vendas);
		return "gerarParcelas";
	}
	

}
