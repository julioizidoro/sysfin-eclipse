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
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.manageBean.ImprimirRelatorioMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.model.Planocontas;
import br.com.financemate.util.Formatacao;
import br.com.financemate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
	private Planocontas planocontas;
	private List<Planocontas> listaPlanoContas;
	private String nomeComboPlano = "Selecione";
	private Float saldoInicial;
	private String unidade;
	private String nomeBanco;
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
		if (usuarioLogadoMB.getCliente() != null) {
			cliente = usuarioLogadoMB.getCliente();
			gerarListaBanco();
		}
		gerarListaPlanoContas();
		desabilitarUnidade();
	}
	
	
	
	
	
	public String getUnidade() {
		return unidade;
	}





	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}





	public String getNomeBanco() {
		return nomeBanco;
	}





	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}





	public Float getSaldoInicial() {
		return saldoInicial;
	}





	public void setSaldoInicial(Float saldoInicial) {
		this.saldoInicial = saldoInicial;
	}





	public String getNomeComboPlano() {
		return nomeComboPlano;
	}





	public void setNomeComboPlano(String nomeComboPlano) {
		this.nomeComboPlano = nomeComboPlano;
	}





	public Planocontas getPlanocontas() {
		return planocontas;
	}





	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}





	public List<Planocontas> getListaPlanoContas() {
		return listaPlanoContas;
	}





	public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
		this.listaPlanoContas = listaPlanoContas;
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
		List<ConciliacaoBean> lista = gerarListaConciliacao();
		File f = new File(servletContext.getRealPath("/resources/img/logo.jpg"));
        BufferedImage logo = ImageIO.read(f);
        parameters.put("logo", logo);
        parameters.put("dataInicial", dataIncial);
        parameters.put("dataFinal", dataFinal);
        parameters.put("nomeFantasia", unidade);
        parameters.put("banco", nomeBanco);
        parameters.put("saldoInicial", saldoInicial);
        if (planocontas != null) {
			parameters.put("planocontas", planocontas.getDescricao());
		}else{
			String nome = "Todas";
			parameters.put("planocontas", nome);
		}
        JRDataSource jrds = new JRBeanCollectionDataSource(lista);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try{
			gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeRelatorio);
	 	} catch (JRException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	} catch (IOException ex) {
	 		Logger.getLogger(ImprimirRelatorioMB.class.getName()).log(Level.SEVERE, null, ex);
	 	}
	 		return "";
	}
	
	private List<ConciliacaoBean> gerarListaConciliacao() {
		unidade = "TODAS";
    	nomeBanco = "TODOS";
    	String sql = "Select o From Outroslancamentos o where o.dataCompensacao>='" + Formatacao.ConvercaoDataSql(dataIncial) + "' and" +
    				" o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
    				    				
    	if (cliente!=null){
    		sql =sql + " and o.cliente.idcliente=" + cliente.getIdcliente();
    		unidade = cliente.getNomeFantasia();
    	}
    	if (banco!=null){
    		sql = sql + " and o.banco.idbanco=" + banco.getIdbanco();
    		nomeBanco = banco.getNome();
    	}
    	if (planocontas!=null){
    		sql = sql + " and o.planocontas.idplanoContas=" + planocontas.getIdplanoContas();
    	}
    	sql = sql + " order by o.dataCompensacao";
    	OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
    	List<Outroslancamentos> lista;
		try { 
			lista = outrosLancamentosFacade.listaOutrosLancamentos(sql);
			List<ConciliacaoBean> listaConciliacao = new ArrayList<ConciliacaoBean>();
	    	if (lista!=null){
	    		saldoInicial = geralSqlSaldo();
	    		Float saldoAtual = saldoInicial;
	    		for(int i=0;i<lista.size();i++){
	    			ConciliacaoBean conciliacao = new ConciliacaoBean();
	    			conciliacao.setDataCompensacao(lista.get(i).getDataCompensacao());
	    			conciliacao.setDescricao(lista.get(i).getDescricao());
	    			conciliacao.setPlanoContas(lista.get(i).getPlanocontas().getDescricao());
	    			saldoAtual = saldoAtual  + lista.get(i).getValorEntrada()  - lista.get(i).getValorSaida();
	    			conciliacao.setSaldo(saldoAtual);
	    			conciliacao.setValorEntrada(lista.get(i).getValorEntrada());
	    			conciliacao.setValorSaida(lista.get(i).getValorSaida());
	    			listaConciliacao.add(conciliacao);
	    		}
	    	}
	    	return listaConciliacao;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}


	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	public void gerarListaPlanoContas() {
        PlanoContasFacade planoContasFacade = new PlanoContasFacade();
        try {
            listaPlanoContas = planoContasFacade.listar();
            if (listaPlanoContas == null) {
                listaPlanoContas = new ArrayList<Planocontas>();
            }
        } catch (Exception ex) {
            Logger.getLogger(ImprimirOutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
        }
        
    }
	
	public String nomeComboPlanoConta(){
    	if (listaPlanoContas == null) {
			return nomeComboPlano;
		}else{
			return nomeComboPlano = "Todas";
		}
    }
	
	public float geralSqlSaldo(){
		OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
		try {
			return outrosLancamentosFacade.saldo(dataIncial);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0f;
	}
	
	
	

}
