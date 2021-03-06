package br.com.financemate.manageBean.outrosLancamentos;

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

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.SaldoFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Saldo;

@Named
@ViewScoped
public class CadSaldoInicialMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private Saldo saldo;
	private Banco banco;
	private List<Banco> listaBanco;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cliente = (Cliente) session.getAttribute("cliente");
        if (saldo == null) {
			saldo = new Saldo();
		}
        if (cliente != null) {
        	gerarListaBanco();
		}
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






	public Saldo getSaldo() {
		return saldo;
	}






	public void setSaldo(Saldo saldo) {
		this.saldo = saldo;
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
	
	
	public String salvar(){
		SaldoFacade saldoFacade = new SaldoFacade();
		saldo.setUsuario(usuarioLogadoMB.getUsuario());
		saldo.setBanco(banco);
		try {
			saldo = saldoFacade.salvar(saldo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "consSaldoIncial";
	}
	
	public String cancelar(){
        return "consSaldoIncial";
    }

}
