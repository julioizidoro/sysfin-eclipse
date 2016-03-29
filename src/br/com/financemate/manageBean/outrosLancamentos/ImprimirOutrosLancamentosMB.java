package br.com.financemate.manageBean.outrosLancamentos;

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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

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
	
	public String gerarRelatorio() throws SQLException, IOException{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String caminhoRelatorio = "";
		String nomeRelatorio = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		caminhoRelatorio = "reports/Relatorios/outroslancamentos/reportConciliacao.jasper";
		nomeRelatorio = "Conciliacao";
		parameters.put("sql",gerarSql());
		File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        parameters.put("dataInicial", dataIncial);
        parameters.put("dataFinal", dataFinal);
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
		sql = sql + "Select distinct outroslancamentos.dataCompensacao, outroslancamentos.descricao, " +
				"outroslancamentos.valorEntrada, outroslancamentos.valorSaida, banco.nome, cliente.nomeFantasia, " +
				"planocontas.descricao as planoContas, outroslancamentos.idoutroslancamentos  from outroslancamentos join" +
				" cliente on outroslancamentos.cliente_idcliente = cliente.idcliente join banco on " +
				"outroslancamentos.banco_idbanco = banco.idbanco join planocontas on outroslancamentos.planoContas_idplanoContas ="+
				" planocontas.idplanoContas where outroslancamentos.dataVencimento>='"+ Formatacao.ConvercaoDataSql(dataIncial) +"' and " +
				"outroslancamentos.dataVencimento<='"+ Formatacao.ConvercaoDataSql(dataFinal) +"' and cliente.idcliente ="+ cliente.getIdcliente() + " and "+
				"banco.idbanco =" + banco.getIdbanco() + " order by outroslancamentos.dataVencimento";
		return sql;
	}
	
	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	

}
