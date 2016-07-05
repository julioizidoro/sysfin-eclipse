package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ProdutoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Produto;

@Named
@ViewScoped
public class ProdutoMB implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produto produto;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private List<Produto> listaProduto;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	@PostConstruct
	public void init(){
		gerarListaProdutos();
		gerarListaCliente();
	}

	
	
	public List<Produto> getListaProduto() {
		return listaProduto;
	}



	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
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
	
	public void gerarListaProdutos(){
		ProdutoFacade produtoFacade = new ProdutoFacade();
		try {
			if (usuarioLogadoMB.getCliente() == null) {
				listaProduto = produtoFacade.listarTodosCliente("");
			}else{
				listaProduto = produtoFacade.listar(usuarioLogadoMB.getCliente().getIdcliente());
			}
			if (listaProduto == null) {
				listaProduto = new ArrayList<Produto>();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public String novoProduto() {
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 700);
		 options.put("closable", false);
		 RequestContext.getCurrentInstance().openDialog("cadProduto", options, null);
		 return "";
	 }
	
	
	public String editar(Produto produto) {
		 if (produto!= null) {
			 FacesContext fc = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			 session.setAttribute("produto", produto);
			 Map<String, Object> options = new HashMap<String, Object>();
			 options.put("contentWidth", 700);
			 options.put("closable", false);
			 RequestContext.getCurrentInstance().openDialog("cadProduto", options, null);
			 
		 }
		 return "";
	 }
	
	public void retornoDialog(SelectEvent event) {
		Produto produto = (Produto) event.getObject();
		 if (produto.getIdproduto() != null) {
			mensagem mensagem = new mensagem();
			mensagem.saveMessagem();
		}
		 gerarListaProdutos();
	 }
}
