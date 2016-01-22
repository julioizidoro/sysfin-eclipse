package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class PesquisaConsContasPagarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sql;
	private Boolean liberadas;
	private Boolean autorizadas;
	private Cliente cliente;
	private Date dataInicio;
	private Date dataFinal;
	private List<Cliente> listaCliente;
	
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
	}
	
	

	
	
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}





	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}





	public String getSql() {
		return sql;
	}




	public void setSql(String sql) {
		this.sql = sql;
	}




	public Boolean getLiberadas() {
		return liberadas;
	}




	public void setLiberadas(Boolean liberadas) {
		this.liberadas = liberadas;
	}




	public Boolean getAutorizadas() {
		return autorizadas;
	}




	public void setAutorizadas(Boolean autorizadas) {
		this.autorizadas = autorizadas;
	}




	public Cliente getCliente() {
		return cliente;
	}




	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




	public Date getDataInicio() {
		return dataInicio;
	}




	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}




	public Date getDataFinal() {
		return dataFinal;
	}




	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public void gerarListaCliente() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }

	public void pesquisar(){
        sql = "Select v from Contaspagar v where ";
        if (liberadas){
            sql = sql + " v.contaPaga='S' and ";
        }else sql = sql + " v.contaPaga='N' and ";
        if (autorizadas){
            sql = sql + " v.autorizarPagamento='S' and ";
        }
        if (cliente!=null){
            sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
        }else {
            sql = sql + " v.cliente.visualizacao='Operacional' and ";
        }
        if (liberadas){
            sql = sql + "v.dataLiberacao>='" + Formatacao.ConvercaoDataSql(getDataInicio()) + 
                "' and v.dataLiberacao<='" + Formatacao.ConvercaoDataSql(getDataFinal()) + 
                "' order by v.dataLiberacao";
        }else {
            sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(getDataInicio()) + 
                "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(getDataFinal()) + 
                "' order by v.dataVencimento";
        }
        
        RequestContext.getCurrentInstance().closeDialog(sql);
    }
}
