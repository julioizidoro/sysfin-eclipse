package br.com.financemate.manageBean;

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

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ProdutoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;

@Named
@ViewScoped
public class CadProdutoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	

	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produto = (Produto) session.getAttribute("produto");
        session.removeAttribute("produto");
        if (produto == null) {
			produto = new Produto();
		}else{
			cliente = produto.getCliente();
		}
        gerarListaCliente();
	}


	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public List<Cliente> getListaCliente() {
		return listaCliente;
	}


	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
	
	
	public void gerarListaCliente(){
		 ClienteFacade clienteFacade = new ClienteFacade();
		 try {
			listaCliente = clienteFacade.listar("");
			if (listaCliente == null) {
				listaCliente = new ArrayList<Cliente>();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	
	public void salvar(){
		ProdutoFacade produtoFacade = new ProdutoFacade();
		produto.setCliente(cliente);
		try {
			produto = produtoFacade.salvar(produto);
			RequestContext.getCurrentInstance().closeDialog(produto);
		} catch (SQLException e) {
			Logger.getLogger(CadBancoMB.class.getName()).log(Level.SEVERE, null, e);
			 mostrarMensagem(e, "Erro ao Cadastrar Produto", "Erro");
		}
	}
	
	public String cancelar() {
		 RequestContext.getCurrentInstance().closeDialog(produto);
		 return "";
	 }
	
	
	 public void mostrarMensagem(Exception ex, String erro, String titulo) {
	        FacesContext context = FacesContext.getCurrentInstance();
	        erro = erro + " - " + ex;
	        context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	
}
