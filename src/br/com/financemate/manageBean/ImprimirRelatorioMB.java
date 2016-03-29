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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

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
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Date dataInicial;
	private Date dataFinal;
	private String relatorio;
	private Boolean habilitarUnidade = false;
	private String competencia;
	private Boolean desabilitarCompetencia = true;
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
		if (usuarioLogadoMB.getCliente() != null) {
			cliente = usuarioLogadoMB.getCliente();
		}
		desabilitarUnidade();
	}
	
	

	public Boolean getDesabilitarCompetencia() {
		return desabilitarCompetencia;
	}



	public void setDesabilitarCompetencia(Boolean desabilitarCompetencia) {
		this.desabilitarCompetencia = desabilitarCompetencia;
	}



	public String getCompetencia() {
		return competencia;
	}



	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
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
			caminhoRelatorio = "reports/Relatorios/reportFluxoCaixa.jasper";
	        parameters.put("nomeFantasia", cliente.getNomeFantasia());
		}else if(relatorio.equalsIgnoreCase("Pagamentos")){
			caminhoRelatorio = "reports/Relatorios/reportPagamentos01.jasper";
		}else{
			caminhoRelatorio = "reports/Relatorios/reportPagamentoVencidas.jasper";
		}
		parameters.put("sql",gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        String periodo = null;
        if (relatorio.equalsIgnoreCase("Pagamentos")) {
        	if (dataInicial != null && dataFinal != null) {
        		periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicial) 
                + "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
			}else{
				periodo = "Competência : " + competencia;
			}
        	
		}else{
			periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicial) 
			+ "    " + Formatacao.ConvercaoDataPadrao(dataFinal);
			
		}
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
			 sql = "Select distinct outroslancamentos.dataCompensacao, outroslancamentos.descricao, ";
			 sql = sql + "outroslancamentos.valorEntrada, outroslancamentos.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, ";
			 sql = sql + "planocontas.descricao as planoContas, outroslancamentos.planoContas_idplanoContas as idPlanoContas, outroslancamentos.compentencia ";
			 sql = sql + "from outroslancamentos join cliente on outroslancamentos.cliente_idcliente = cliente.idcliente ";
			 sql = sql + "join banco on outroslancamentos.banco_idbanco = banco.idbanco ";
			 sql = sql + "join planocontas on outroslancamentos.planoContas_idplanoContas = planocontas.idplanoContas ";
			 sql = sql +"where ";
			 if ((dataInicial !=null) && (dataFinal !=null)){
				 sql = sql + "outroslancamentos.dataCompensacao>='" +  Formatacao.ConvercaoDataSql(dataInicial) +
						 "' and outroslancamentos.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and ";
			 }else {
		            sql = sql + "outroslancamentos.compentencia='" + competencia + "' and ";
		        }
			 sql = sql + "cliente.idcliente=" + cliente.getIdcliente();
			 sql = sql + " and outroslancamentos.planoContas_idplanoContas<>" + cliente.getContaRecebimento();
			 sql = sql + " and outroslancamentos.planoContas_idplanoContas<>" + cliente.getContaReceita();
			 sql = sql + " Group by outroslancamentos.planoContas_idplanoContas, outroslancamentos.dataCompensacao, outroslancamentos.descricao, outroslancamentos.valorEntrada, outroslancamentos.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  outroslancamentos.compentencia ";
			 sql = sql + " order by outroslancamentos.planoContas_idplanoContas, outroslancamentos.dataCompensacao, outroslancamentos.descricao, outroslancamentos.valorEntrada, outroslancamentos.valorSaida, planocontas.descricao, banco.nome, cliente.nomeFantasia, planocontas.descricao,  outroslancamentos.compentencia";
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
	 
	 public void desabilitarUnidade(){
		 if (usuarioLogadoMB.getCliente() != null) {
			 habilitarUnidade = true;
		 }else{
			 habilitarUnidade = false;
		 }
		 
	 }
	 
	 public Boolean habilitarCompetencia(){
		 if (relatorio.equalsIgnoreCase("Pagamentos")) {
			desabilitarCompetencia = false;
		} 
		 return desabilitarCompetencia;
	 }

}
