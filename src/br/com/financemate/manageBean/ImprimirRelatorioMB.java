package br.com.financemate.manageBean;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.kohsuke.rngom.digested.DDataPattern;
import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ImprimirRelatorioMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Date dataInicial;
	private Date dataFinal;
	private String relatorio;
	
	@PostConstruct
	public void init(){
	}
	
	

	public String getRelatorio() {
		return relatorio;
	}



	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}



	public Date getDataInicial() {
		return dataInicial;
	}



	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}



	public Date getDataFinal() {
		return dataFinal;
	}



	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
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
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	
	public String gerarRelatorio() throws SQLException, IOException{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoRelatorio = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (relatorio.equalsIgnoreCase("Fluxo de Caixa")) {
			caminhoRelatorio = "reports/Relatórios/reportFluxoCaixa.jasper";
		}else if(relatorio.equalsIgnoreCase("Pagamentos")){
			caminhoRelatorio = "reports/Relatórios/reportPagamento01.jasper";
		}else{
			caminhoRelatorio = "reports/Relatórios/reportPagamentoVencidas.jasper";
		}
        parameters.put("sql",gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("nomeFantasia", cliente.getNomeFantasia());
        parameters.put("logo", logo);
        String periodo = null;
        periodo = "Período : " + Formatacao.ConvercaoDataPadrao(dataInicial) 
                    + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
        parameters.put("periodo", periodo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try{
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fluxocaixa", null);
	 	} catch (JRException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	} catch (IOException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	}
	 		return "";
	}
	
	public String gerarSql(){
		String sql = "";
		if (relatorio.equalsIgnoreCase("Fluxo de caixa")) {
			sql = "Select * from fluxocaixa where cliente_idcliente=" + cliente.getIdcliente() + 
					" and fluxocaixa.data>='" + Formatacao.ConvercaoDataSql(dataInicial) + 
	                "' and fluxocaixa.data<='" + Formatacao.ConvercaoDataSql(dataFinal) + 
	                "' order by fluxocaixa.data";
		}else if(relatorio.equalsIgnoreCase("Pagamentos")){
			 sql = "Select distinct movimentobanco.dataCompensacao, movimentobanco.descricao, ";
			 sql = sql + "movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, ";
			 sql = sql + "planocontas.descricao as planoContas, movimentobanco.planoContas_idplanoContas as idPlanoContas, movimentobanco.compentencia ";
			 sql = sql + "from movimentobanco join cliente on movimentobanco.cliente_idcliente = cliente.idcliente ";
			 sql = sql + "join banco on movimentobanco.banco_idbanco = banco.idbanco ";
			 sql = sql + "join planocontas on movimentobanco.planoContas_idplanoContas = planocontas.idplanoContas ";
			 sql = sql +"where ";
			 if ((dataInicial !=null) && (dataFinal !=null)){
				 sql = sql + "movimentobanco.dataCompensacao>='" +  Formatacao.ConvercaoDataSql(dataInicial) +
						 "' and movimentobanco.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and ";
			 }
			 sql = sql + "cliente.idcliente=" + cliente.getIdcliente();
			 sql = sql + " and movimentobanco.planoContas_idplanoContas<>" + cliente.getContaRecebimento();
			 sql = sql + " and movimentobanco.planoContas_idplanoContas<>" + cliente.getContaReceita();
			 sql = sql + " Group by movimentobanco.planoContas_idplanoContas, movimentobanco.dataCompensacao, movimentobanco.descricao, movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  movimentobanco.compentencia ";
			 sql = sql + " order by movimentobanco.planoContas_idplanoContas, movimentobanco.dataCompensacao, movimentobanco.descricao, movimentobanco.valorEntrada, movimentobanco.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  movimentobanco.compentencia";
		}else{
			sql = "Select distinct contasPagar.dataVencimento, contasPagar.descricao, contasPagar.valor, contasPagar.dataAgendamento,cliente.nomeFantasia, contasPagar.fornecedor, contasPagar.numeroDocumento";
	        sql = sql + " From ";
	        sql = sql + " contasPagar join cliente on contasPagar.cliente_idcliente = cliente.idcliente ";
	        sql = sql +" where ";
	        if ((dataInicial !=null) && (dataFinal !=null)){
	        	sql = sql + "contasPagar.dataVencimento>='" +  Formatacao.ConvercaoDataSql(dataInicial) +
	        			"' and contasPagar.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and ";
	        }
	        sql = sql + " contasPagar.cliente_idcliente=" + cliente.getIdcliente() + " and contasPagar.contaPaga='N'";
	        sql = sql + " order by contasPagar.dataVencimento";
		}
        
        
        return sql;
    }
	
	 public String fechar(){
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	    }

}
