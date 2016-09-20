package br.com.financemate.manageBean.outrosLancamentos;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class ConciliacaoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Banco banco;
	private List<Outroslancamentos> listaLacamentos;
	private List<TransacaoBean> listaTransacao;
	private Date dataInicial;
	private Date dataFinal;
	private UploadedFile arquivo;
	private List<ConciliarBean> listaConciliacaoBancaria;
	private String nomeBotaoConciliar = "Conciliar";
	private List<TransacaoBean> listaTransacaoNaoConciliado;
	private List<Outroslancamentos> listaOutrosNaoConciliado;
	private Integer sizeConciliada;
	private TransacaoBean transacaoBean;
	private Outroslancamentos outroslancamentos;
	
	
	
	public String getNomeBotaoConciliar() {
		return nomeBotaoConciliar;
	}
	public void setNomeBotaoConciliar(String nomeBotaoConciliar) {
		this.nomeBotaoConciliar = nomeBotaoConciliar;
	}
	public List<TransacaoBean> getListaTransacaoNaoConciliado() {
		return listaTransacaoNaoConciliado;
	}
	public void setListaTransacaoNaoConciliado(List<TransacaoBean> listaTransacaoNaoConciliado) {
		this.listaTransacaoNaoConciliado = listaTransacaoNaoConciliado;
	}
	public List<Outroslancamentos> getListaOutrosNaoConciliado() {
		return listaOutrosNaoConciliado;
	}
	public void setListaOutrosNaoConciliado(List<Outroslancamentos> listaOutrosNaoConciliado) {
		this.listaOutrosNaoConciliado = listaOutrosNaoConciliado;
	}
	public Integer getSizeConciliada() {
		return sizeConciliada;
	}
	public void setSizeConciliada(Integer sizeConciliada) {
		this.sizeConciliada = sizeConciliada;
	}
	public TransacaoBean getTransacaoBean() {
		return transacaoBean;
	}
	public void setTransacaoBean(TransacaoBean transacaoBean) {
		this.transacaoBean = transacaoBean;
	}
	public Outroslancamentos getOutroslancamentos() {
		return outroslancamentos;
	}
	public void setOutroslancamentos(Outroslancamentos outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}
	public List<ConciliarBean> getListaConciliacaoBancaria() {
		return listaConciliacaoBancaria;
	}
	public void setListaConciliacaoBancaria(List<ConciliarBean> listaConciliacaoBancaria) {
		this.listaConciliacaoBancaria = listaConciliacaoBancaria;
	}
	public UploadedFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	public Banco getBanco() {
		return banco;
	}
	public void setBanco(Banco banco) {
		this.banco = banco;
	}
	public List<Outroslancamentos> getListaLacamentos() {
		return listaLacamentos;
	}
	public void setListaLacamentos(List<Outroslancamentos> listaLacamentos) {
		this.listaLacamentos = listaLacamentos;
	}
	public List<TransacaoBean> getListaTransacao() {
		return listaTransacao;
	}
	public void setListaTransacao(List<TransacaoBean> listaTransacao) {
		this.listaTransacao = listaTransacao;
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
	
	
	public void carregarArquivo(FileUploadEvent e){
		listaConciliacaoBancaria = null;
		arquivo = e.getFile();
		File arq = new File(arquivo.getFileName());
		FileInputStream file = null; 
		try {
			file = (FileInputStream) arquivo.getInputstream();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
   	 	LerOFXBean ler = new LerOFXBean();
   	 	ler.iniciarLeitura(file);
   	 	listaTransacao = ler.getListaTransacao();
   	 	if ((ler.getAgencia()!=null) && (ler.getConta()!=null)){
   	 		banco = consultarBanco(ler.getAgencia(), ler.getConta());
   	 	}
   	 	if (banco!=null){
   	 		dataInicial = listaTransacao.get(0).getData(); 
   	 		dataFinal = listaTransacao.get(listaTransacao.size()-1).getData();
   	 		carregarOutrosLancamentos();
   	 		if (listaLacamentos!=null){
  	 			conciliar();
   	 		}
   	 	}
   	 	
   	 	
   	 	
	}
	
	public Banco consultarBanco(String agencia , String conta){
		BancoFacade bancoFacade = new BancoFacade();
   	 	try {
			Banco banco = bancoFacade.consultarBanco(agencia, conta);
			return banco;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	 	return null;
	} 
	
	public void carregarOutrosLancamentos(){
		OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
		String sql = "SELECT o FROM Outroslancamentos o where o.dataCompensacao>='" +
		Formatacao.ConvercaoDataSql(dataInicial) + "' and o.dataCompensacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' and o.banco.idbanco=" + banco.getIdbanco() + " order by o.dataCompensacao";
		try {
			listaLacamentos = outrosLancamentosFacade.listaOutrosLancamentos(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void conciliar(){ 
		for (int i = 0; i < listaTransacao.size(); i++) {
			verficarLancamentos(listaTransacao.get(i));
		}
		sizeConciliada = listaConciliacaoBancaria.size();
		for (int i = 0; i < listaTransacao.size(); i++) {
			gerarListaTransacaoNaoConciliada(listaTransacao.get(i));
		}
		gerarListaOutrosLancamentosNaoConciliado();
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getOutroslancamentos() == null) {
				listaConciliacaoBancaria.get(i).setOutroslancamentos(new Outroslancamentos());
				listaConciliacaoBancaria.get(i).getOutroslancamentos().setSelecionado(false);
			}
		}
	}
	 
	public void verficarLancamentos(TransacaoBean transacao){
		ConciliarBean cb = new ConciliarBean();
		if (listaConciliacaoBancaria == null) {
			listaConciliacaoBancaria = new ArrayList<ConciliarBean>();
		} 
		for (int j = 0; j < listaLacamentos.size(); j++) {
			Float valor;
			String dataTransacao = Formatacao.ConvercaoDataPadrao(transacao.getData());
			String dataLancamento = Formatacao.ConvercaoDataPadrao(listaLacamentos.get(j).getDataCompensacao());
			if (transacao.getTipo().equalsIgnoreCase("DEBIT")){ 
				valor =  transacao.getValorSaida();
				if ((listaLacamentos.get(j).getValorSaida().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
					listaLacamentos.get(j).setConciliada(true);
					transacao.setConciliada(true);
					cb = new ConciliarBean();
					cb.setOutroslancamentos(listaLacamentos.get(j));
					cb.setTransacao(transacao);
					listaConciliacaoBancaria.add(cb);
				}
			}else if (transacao.getTipo().equalsIgnoreCase("CREDIT")){
				valor =  transacao.getValorEntrada();
				if ((listaLacamentos.get(j).getValorEntrada().floatValue() == valor.floatValue()) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
					listaLacamentos.get(j).setConciliada(true);
					transacao.setConciliada(true);
					cb = new ConciliarBean();
					cb.setOutroslancamentos(listaLacamentos.get(j));
					cb.setTransacao(transacao);
					listaConciliacaoBancaria.add(cb);
				}
			}
			
		}
		
		
	} 
	
	
	public String estiloDaTabelaTransacao(TransacaoBean transacao){
		String color;
		if (transacao.getConciliada() == true) {
			color = "color:black;";
		}else{
			color = "color:red;font-weight:bold;";
		}
		return color;
	}
	
	public String estiloDaTabelaOutrosLancamentos(Outroslancamentos outros){
		String color;
		if (outros.getConciliada() == true) {
			color = "color:gren";
		}else{
			color = "color:red;font-weight:bold;";
		}
		return color;
	}
	
	
	public void gerarListaTransacaoNaoConciliada(TransacaoBean transacao){
		ConciliarBean conciliacaoBean = new ConciliarBean();
		if (listaTransacaoNaoConciliado == null) {
			listaTransacaoNaoConciliado = new ArrayList<TransacaoBean>();
		}
			if (transacao.getConciliada() == false) {
				listaTransacaoNaoConciliado.add(transacao);
				conciliacaoBean.setTransacao(transacao);
				listaConciliacaoBancaria.add(conciliacaoBean);
			}
		
	}
	
	public void gerarListaOutrosLancamentosNaoConciliado(){
		ConciliarBean conciliacaoBean = new ConciliarBean();
		if (listaOutrosNaoConciliado == null) {
			listaOutrosNaoConciliado = new ArrayList<Outroslancamentos>();
		} 
		for (int i = 0; i < listaLacamentos.size(); i++) {
			if (listaLacamentos.get(i).getConciliada() == false) {
				listaOutrosNaoConciliado.add(listaLacamentos.get(i));
				conciliacaoBean.setOutroslancamentos(listaLacamentos.get(i));
				listaConciliacaoBancaria.get(sizeConciliada +i).setOutroslancamentos(conciliacaoBean.getOutroslancamentos());
				
			}
		}
	}
	
	
	public String novaConciliacao(ConciliarBean conciliacao) {
		TransacaoBean transacao = null;
		Outroslancamentos outros = null;
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getTransacao().isSelecionado()) {
				transacao = listaConciliacaoBancaria.get(i).getTransacao();
				i = listaConciliacaoBancaria.size();
			}
		}
		for (int i = 0; i < listaConciliacaoBancaria.size(); i++) {
			if (listaConciliacaoBancaria.get(i).getOutroslancamentos() != null) {
				if (listaConciliacaoBancaria.get(i).getOutroslancamentos().isSelecionado()) {
					outros = listaConciliacaoBancaria.get(i).getOutroslancamentos();
					i = listaConciliacaoBancaria.size();
				}				
			} 
		}
		if(transacao != null && outros != null) {
			OutrosLancamentosFacade outrosFacade = new OutrosLancamentosFacade();
			if (transacao.getValorEntrada() > 0f) {
				outros.setValorEntrada(transacao.getValorEntrada());
				outros.setDataCompensacao(transacao.getData());
				try {
					outrosFacade.salvar(outros);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				outros.setValorSaida(transacao.getValorSaida());
				outros.setDataCompensacao(transacao.getData());
				try {
					outrosFacade.salvar(outros);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			listaConciliacaoBancaria = null;
	        carregarOutrosLancamentos();
	        conciliar();
			return "";
		}else{
			transacaoBean = conciliacao.getTransacao();
			outroslancamentos = conciliacao.getOutroslancamentos();
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("banco", banco);
			session.setAttribute("transacaoBean", transacaoBean);
			session.setAttribute("outroslancamentos", outroslancamentos);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("cadConciliacao"); 
			return "";			
		}
    } 
	
	
	public void retornoDialogConciliado(SelectEvent e) {
        listaConciliacaoBancaria = null;
        carregarOutrosLancamentos();
        conciliar();
	}
	
}
