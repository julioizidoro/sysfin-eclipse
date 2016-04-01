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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.manageBean.contasReceber.ContasReceberMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;

@Named
@ViewScoped
public class EditarBancoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Contaspagar conta;
	private Banco banco;
	private List<Banco> listaBanco;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Boolean habilitarUnidade = false;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		conta = (Contaspagar) session.getAttribute("conta");
		gerarListaCliente();
		if (conta == null) {
			if (usuarioLogadoMB.getCliente() != null) {
				cliente = usuarioLogadoMB.getCliente();
				gerarListaBanco(); 
			}else{
				cliente = new Cliente();
			}
		}else{
			cliente = conta.getCliente();
            gerarListaBanco(); 
            banco = conta.getBanco();
		}
		desabilitarUnidade();
	}

	

	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
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



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public Contaspagar getConta() {
		return conta;
	}


	public void setConta(Contaspagar conta) {
		this.conta = conta;
	}
	
	
	public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
	}
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public void gerarListaBanco(){
		if (cliente!=null) {
			BancoFacade bancoFacade = new BancoFacade();
			String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
			listaBanco = bancoFacade.listar(sql);
			if (listaBanco ==null){
	            listaBanco = new ArrayList<Banco>();
			}
		}else {
			listaBanco = new ArrayList<Banco>();
		}
	}
	
	public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
	
	public String salvar(){
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		conta.setBanco(banco);
		conta = contasPagarFacade.salvar(conta);
		return "liberarContasPagar";
	}
	
	public String cancelar(){
		return "liberarContasPagar";
	}

}
