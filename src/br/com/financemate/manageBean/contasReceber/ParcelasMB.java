package br.com.financemate.manageBean.contasReceber;

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
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.financemate.facade.ContasReceberFacade;
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
	private List<Contasreceber> listaParcela;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasreceber = (Contasreceber) session.getAttribute("contasReceber");
        listaParcela = (List<Contasreceber>) session.getAttribute("listarParcelas");
        if (contasreceber != null) {
			if (listaParcela == null) {
				gerarListaParcelas();
			}
		}
	} 
	
	
	

	public List<Contasreceber> getListaParcela() {
		return listaParcela;
	}




	public void setListaParcela(List<Contasreceber> listaParcela) {
		this.listaParcela = listaParcela;
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
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contasReceber", contasreceber);
		return "cadContasReceber";
	}
	
	
	public void gerarListaParcelas(){
		String sql = "SELECT c FROM Contasreceber c  WHERE c.numeroDocumento=" + contasreceber.getNumeroDocumento();
		try {
			 ContasReceberFacade contasReceberFacadece = new ContasReceberFacade();
			 
			 listaParcela = contasReceberFacadece.listar(sql);
			 if (listaParcela == null) {
				 listaParcela = new ArrayList<Contasreceber>();
			 }
		 } catch (SQLException ex) {
			 Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro Listar Contas", "Erro");
		 }
		totalParcela();
	}
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	
}
