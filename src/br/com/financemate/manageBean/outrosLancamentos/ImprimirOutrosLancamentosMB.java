package br.com.financemate.manageBean.outrosLancamentos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;

@Named
@ViewScoped
public class ImprimirOutrosLancamentosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Cliente> listaCliente;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cliente cliente;
	private Boolean habilitarUnidade;
	private Banco banco;
	private List<Banco> listaBanco;
	private Date dataIncial;
	private Date dataFinal;
	
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
		if (usuarioLogadoMB.getCliente() != null) {
			cliente = usuarioLogadoMB.getCliente();
			gerarListaBanco();
		}
		desabilitarUnidade();
	}
	
	
	
	
	
	public Date getDataIncial() {
		return dataIncial;
	}





	public void setDataIncial(Date dataIncial) {
		this.dataIncial = dataIncial;
	}





	public Date getDataFinal() {
		return dataFinal;
	}





	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public void gerarListaCliente() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirOutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
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
	
	public String impressaoOutrosLancamentos() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("imprimirOutrosLancamentos");
        return "";
    }

}
