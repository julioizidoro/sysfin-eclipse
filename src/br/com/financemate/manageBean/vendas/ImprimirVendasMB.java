package br.com.financemate.manageBean.vendas;

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

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ImprimirVendasMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cliente> listaCliente;
	private Cliente cliente;
	private Date dataInicial;
	private Date dataFinal;
	private Boolean habilitarUnidade = false;
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
		if (usuarioLogadoMB.getCliente() != null) {
			cliente = usuarioLogadoMB.getCliente();
		}
		desabilitarUnidade();
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



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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
	
	
	public void gerarListaCliente() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirVendasMB.class.getName()).log(Level.SEVERE, null, ex);
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
	 
	 
	 public String gerarRelatorio() throws SQLException, IOException{
		 ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		 String caminhoRelatorio = "";
		 String nomeRelatorio = null;
		 Map<String, Object> parameters = new HashMap<String, Object>();
		 caminhoRelatorio = "reports/Relatorios/vendas/reportVendas.jasper";
		 nomeRelatorio = "Mapa de Vendas Gerencial";
		 File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
		 BufferedImage logo = ImageIO.read(f);
		 if (cliente != null) {
			 parameters.put("idcliente", cliente.getIdcliente());			
		}
		 parameters.put("sql", gerarSql());
		 parameters.put("sql2", gerarSql2());
		 parameters.put("dataInicial", Formatacao.ConvercaoDataSql(dataInicial));
         parameters.put("dataFinal", Formatacao.ConvercaoDataSql(dataFinal));
		 parameters.put("logo", logo);
		 GerarRelatorio gerarRelatorio = new GerarRelatorio();
		 try{
			 gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, nomeRelatorio, null);
		 } catch (JRException ex) {
			 Logger.getLogger(ImprimirVendasMB.class.getName()).log(Level.SEVERE, null, ex);
		 } catch (IOException ex) {
			 Logger.getLogger(ImprimirVendasMB.class.getName()).log(Level.SEVERE, null, ex);
		 }
		 return "";
	 }
	 
	 public String gerarSql(){
			String sql = "";
				sql = " '" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
	        return sql; 
	    }
	 
	 public String gerarSql2(){
			String sql2 = "";
				sql2 = " '" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
	        return sql2; 
	    }

}
