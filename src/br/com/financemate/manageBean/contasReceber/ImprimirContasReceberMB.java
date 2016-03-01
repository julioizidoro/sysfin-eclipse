package br.com.financemate.manageBean.contasReceber;

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

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ImprimirContasReceberMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Date dataInicial;
	private Date dataFinal;
	private String relatorio;
	private Boolean contasAberto;
	private Boolean contasRecebidas;
	private Boolean todas;
	private Boolean selecionado = false;

	
	@PostConstruct
	public void init(){
		
	}
	
	
	

	public Boolean getSelecionado() {
		return selecionado;
	}




	public void setSelecionado(Boolean selecionado) {
		this.selecionado = selecionado;
	}




	public Boolean getContasAberto() {
		return contasAberto;
	}




	public void setContasAberto(Boolean contasAberto) {
		this.contasAberto = contasAberto;
	}




	public Boolean getContasRecebidas() {
		return contasRecebidas;
	}




	public void setContasRecebidas(Boolean contasRecebidas) {
		this.contasRecebidas = contasRecebidas;
	}




	public Boolean getTodas() {
		return todas;
	}




	public void setTodas(Boolean todas) {
		this.todas = todas;
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


	public String getRelatorio() {
		return relatorio;
	}


	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
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
	
	
	public String gerarRelatorio() throws SQLException, IOException{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoRelatorio = "";
		String nomeRelatorio = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (relatorio.equalsIgnoreCase("Contas a Receber")) {
			caminhoRelatorio = "reports/Relatorios/contasReceber/reportContasReceber.jasper";
			nomeRelatorio = "Contas a Receber";
		}else if(relatorio.equalsIgnoreCase("Cobranca")){
			caminhoRelatorio =  "reports/Relatorios/contasReceber/frmcobranca.jasper";
			nomeRelatorio = "Relatório Cobranças";
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
        String titulo = null;
        if (contasRecebidas){
            titulo = "RELATÓRIO DE CONTAS RECEBIDAS";
        }else if (contasAberto){
            titulo= "RELATÓRIO DE CONTAS EM ABERTO";
        }else{
            titulo = "RELATÓRIO DE CONTAS A RECEBER";
        }
        if (cliente==null){
            parameters.put("unidade", "TODAS AS UNIDADES");
        }else {
            parameters.put("unidade", cliente.getNomeFantasia());
        }
        parameters.put("titulo", titulo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try{
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio, null);
	 	} catch (JRException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	} catch (IOException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	}
	 		return "";
	}
	
	public String gerarSql(){
		String sql = "";
		if (relatorio.equalsIgnoreCase("Contas a receber")) {
			sql = "SELECT distinct contasreceber.idcontasReceber, contasreceber.numeroDocumento, contasreceber.nomeCliente as nomeCliente,contasreceber.valorParcela, contasreceber.numeroParcela, contasreceber.dataVencimento, contasreceber.juros,contasreceber.desagio, contasreceber.tipodocumento, contasreceber.venda, contasreceber.dataPagamento, contasreceber.valorPago,cliente.nomeFantasia, banco.nome, vendas.dataVenda ";
			sql = sql + "from ";
			sql = sql + "contasreceber join cliente on contasreceber.cliente_idcliente = cliente.idcliente ";
			sql = sql + "join banco on contasreceber.banco_idbanco = banco.idbanco "; 
			sql = sql + "join vendas on contasreceber.vendas_idvendas = vendas.idvendas ";
			sql = sql + "where ";
			String ordem = "";
			if (contasAberto){
				sql = sql + " contasreceber.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
				sql = sql + " and contasreceber.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
				sql = sql + " and valorPago=0 "; 
				ordem = " order by contasReceber.dataVencimento";
			}
			if (contasRecebidas){
				sql = sql + " contasreceber.datapagamento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
				sql = sql + " and contasreceber.dataPagamento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
				sql = sql + " and valorPago>0 "; 
				ordem = " order by contasReceber.dataPagamento";
			}
			if (todas){
				sql = sql + " contasreceber.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
				sql = sql + " and contasreceber.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
				ordem = " order by contasReceber.dataVencimento";
			}
			if (cliente!=null){
				sql = sql  + " and cliente.idcliente="  + cliente.getIdcliente() + "";
			}
			sql = sql + ordem;
		}else if(relatorio.equalsIgnoreCase("Cobranca")){
			sql = "SELECT distinct contasreceber.idcontasreceber, contasreceber.nomeCliente as nomeCliente,contasreceber.valorParcela, contasreceber.numeroParcela, contasreceber.dataVencimento, cobranca.fone1, cobranca.fone2, cobranca.vencimentooriginal, cobranca.alterarvencimento, cobranca.email";
			sql = sql + " from ";
			sql = sql + "contasreceber join cobranca on contasreceber.cobranca_idcobranca = cobranca.idcobranca ";
			sql = sql + "where contasreceber.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
			sql = sql + " and contasreceber.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
			sql = sql + " order by contasReceber.dataVencimento";
		}
        
		return sql;
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public Boolean habilitarTipoRelatorio(){
		selecionado = false;
		if (relatorio.equalsIgnoreCase("Contas a Receber")) {
			selecionado = true;
		}
		return selecionado;
	}
	
}

