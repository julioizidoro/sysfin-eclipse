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

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.TipoPlanoContasFacede;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoplanocontas;

@Named
@ViewScoped
public class ClienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Cliente cliente;
    private String nomeCliente;
    private List<Cliente> listaClientes;
    private String pagina;
    private String idTipoPlanoConta="0";
    private List<Tipoplanocontas> listarTipoPlanoContas;
	
	@PostConstruct
	public void init(){
		cliente = new Cliente();
        getListaClientes();
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

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<Cliente> getListaClientes() {
		if (listaClientes==null){
            gerarListaClientes();
        }
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getIdTipoPlanoConta() {
		return idTipoPlanoConta;
	}

	public void setIdTipoPlanoConta(String idTipoPlanoConta) {
		this.idTipoPlanoConta = idTipoPlanoConta;
	}

	public List<Tipoplanocontas> getListarTipoPlanoContas() {
		return listarTipoPlanoContas;
	}

	public void setListarTipoPlanoContas(List<Tipoplanocontas> listarTipoPlanoContas) {
		this.listarTipoPlanoContas = listarTipoPlanoContas;
	}
	
	
	 public void gerarListaClientes() {
		 ClienteFacade clienteFacade = new ClienteFacade();
		 try {
			 if (nomeCliente == null) {
				 nomeCliente = "";
			 }
			 listaClientes = clienteFacade.listar(nomeCliente);
			 if (listaClientes == null) {
				 listaClientes = new ArrayList<Cliente>();
			 }
			 
		 } catch (SQLException ex) {
			 Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro ao listar o cliente", "Erro");
		 }
	 }
	 
	 public void mostrarMensagem(Exception ex, String erro, String titulo){
		 FacesContext context = FacesContext.getCurrentInstance();
		 erro = erro + " - " + ex;
		 context.addMessage(null, new FacesMessage(titulo, erro));
	 }
	 
	 public String editar(Cliente cliente){
		 if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getAcliente()) {
			 if (cliente != null) {
				 FacesContext fc = FacesContext.getCurrentInstance();
				 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				 session.setAttribute("cliente", cliente);
				 Map<String, Object> options = new HashMap<String, Object>();
				 options.put("contentWidth", 700);
				 options.put("closable", false);
				 RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
			 }
		 } else {
			 FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
			 FacesContext.getCurrentInstance().addMessage(null, mensagem);
			 return "";
		 }
		 return "";
	 }
	 
	 public String novo() {
		 if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getIcliente()) {
			 try {
				 listarTipoPlanoContas();
				 cliente = new Cliente();
				 Map<String, Object> options = new HashMap<String, Object>();
				 options.put("contentWidth", 700);
				 options.put("closable", false);
				 RequestContext.getCurrentInstance().openDialog("cadCliente", options, null);
				 return "";
			 } catch (SQLException ex) {
				 Logger.getLogger(ClienteMB.class.getName()).log(Level.SEVERE, null, ex);
			 }
			 
		 } else {
			 FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
			 FacesContext.getCurrentInstance().addMessage(null, mensagem);
			 return "";
		 } 
		 return null;
	 }
	 
	 public void retornoDialogNovo(SelectEvent event){
		 Cliente cliente = (Cliente) event.getObject();
		 if (cliente.getIdcliente() != null) {
			mensagem mensagem = new mensagem();
			mensagem.saveMessagem();
		}
		 gerarListaClientes();
	 }
	 
	 public String consultarTipoPlanoContas() throws SQLException {
		 FacesContext fc = FacesContext.getCurrentInstance();
		 Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		 int idCliente = Integer.parseInt(params.get("idCliente"));
		 if (idCliente > 0) {
			 ClienteFacade clienteFacade = new ClienteFacade();
			 cliente = clienteFacade.consultar(idCliente);
			 if (cliente != null) {
				 return "cadCliente";
			 }
		 }
		 return null;
	 }
	 
	 public void listarTipoPlanoContas() throws SQLException{
		 TipoPlanoContasFacede tipoPlanoContasFacede = new TipoPlanoContasFacede();
		 listarTipoPlanoContas = tipoPlanoContasFacede.listar();
		 if (listarTipoPlanoContas==null){
			 listarTipoPlanoContas = new ArrayList<Tipoplanocontas>();
		 }
	 }
	

}
