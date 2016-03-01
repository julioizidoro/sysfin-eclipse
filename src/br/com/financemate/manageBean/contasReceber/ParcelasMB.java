package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.model.Contasreceber;

@Named
@ViewScoped
public class ParcelasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Contasreceber contasreceber;
	private Float total;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasReceber");
        totalParcela();
	} 
	
	


	public Float getTotal() {
		return total;
	}




	public void setTotal(Float total) {
		this.total = total;
	}




	public Contasreceber getContasreceber() {
		return contasreceber;
	}


	public void setContasreceber(Contasreceber contasreceber) {
		this.contasreceber = contasreceber;
	}
	
	
	public void totalParcela(){
		total = contasreceber.getValorParcela();
	}

	
	public String voltar(){
		return "cadContasReceber";
	}
	
	
}
