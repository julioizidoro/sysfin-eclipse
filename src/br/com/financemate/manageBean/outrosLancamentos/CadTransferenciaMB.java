package br.com.financemate.manageBean.outrosLancamentos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;

@Named
@ViewScoped
public class CadTransferenciaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Banco bancoCredito;
	private Banco bancoDebito;
	private List<Banco> listaBancoCredito;
	private List<Banco> listaBancoDebito;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	
	
	@PostConstruct
	public void init(){
		gerarListaBanco();
	}


	public Banco getBancoCredito() {
		return bancoCredito;
	}


	public void setBancoCredito(Banco bancoCredito) {
		this.bancoCredito = bancoCredito;
	}


	public Banco getBancoDebito() {
		return bancoDebito;
	}


	public void setBancoDebito(Banco bancoDebito) {
		this.bancoDebito = bancoDebito;
	}


	public List<Banco> getListaBancoCredito() {
		return listaBancoCredito;
	}


	public void setListaBancoCredito(List<Banco> listaBancoCredito) {
		this.listaBancoCredito = listaBancoCredito;
	}


	public List<Banco> getListaBancoDebito() {
		return listaBancoDebito;
	}


	public void setListaBancoDebito(List<Banco> listaBancoDebito) {
		this.listaBancoDebito = listaBancoDebito;
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
	
	
	public void gerarListaBanco(){
		BancoFacade bancoFacade = new BancoFacade();
		String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente();
		listaBancoCredito = bancoFacade.listar(sql);
		listaBancoDebito = bancoFacade.listar(sql);
		if (listaBancoCredito == null || listaBancoCredito.isEmpty()) {
			listaBancoCredito = new ArrayList<Banco>();
		}
		if (listaBancoDebito == null || listaBancoDebito.isEmpty()) {
			listaBancoDebito = new ArrayList<Banco>();
		}
	}

}
