package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.event.SelectEvent;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;

@Named
@ViewScoped
public class BancoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Banco> listaBanco;
    private Cliente cliente;
    private List<Cliente> listaCliente;
    private Banco banco;
    private String sql;
    private int idcliente;
	
    @PostConstruct
	public void init(){
		gerarListaCliente();
		gerarListaBanco();
	}

    
    
	public int getIdcliente() {
		return idcliente;
	}



	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}



	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
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

	
	 public void gerarListaCliente() {
		 try {
			 ClienteFacade clienteFacade = new ClienteFacade();
			 listaCliente = clienteFacade.listar("");
		 } catch (SQLException ex) {
			 Logger.getLogger(BancoMB.class.getName()).log(Level.SEVERE, null, ex);
		 }
		 
	 }

	 public void mostrarMensagem(Exception ex, String erro, String titulo) {
		 FacesContext context = FacesContext.getCurrentInstance();
		 erro = erro + " - " + ex;
		 context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	 
	 public void gerarListaBanco() {
			 BancoFacade bancoFacade = new BancoFacade();
			 if (usuarioLogadoMB.getUsuario().getCliente() > 0) {
				idcliente = usuarioLogadoMB.getUsuario().getCliente();
				sql = "Select distinct b from Banco b where b.cliente.idcliente=" + idcliente + " order by b.nome";
			 }else{
				 idcliente = 8;
				 sql = "Select distinct b from Banco b  where b.cliente.idcliente=" + idcliente + " order by b.nome";
			 }
			 listaBanco = bancoFacade.listar(sql);
			 if (listaBanco==null){
				 listaBanco = new ArrayList<Banco>();
			 }
	 }
	 
	 public String novoBanco() {
		 FacesContext fc = FacesContext.getCurrentInstance();
		 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 700);
		 session.setAttribute("idcliente", idcliente);
		 RequestContext.getCurrentInstance().openDialog("cadBanco", options, null);
		 return "";
	 }
	 
	 public String editar(Banco banco) {
		 if (banco!= null) {
			 FacesContext fc = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			 session.setAttribute("banco", banco);
			 RequestContext.getCurrentInstance().openDialog("cadBanco");
			 
		 }
		 return "";
	 }

	 public void retornoDialog(SelectEvent event) {
		 gerarListaBanco();
	 }
}
