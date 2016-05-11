package br.com.financemate.manageBean.outrosLancamentos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
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
   	 		consultarBanco(ler.getAgencia(), ler.getConta());
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
	
	public void consultarBanco(String agencia , String conta){
		BancoFacade bancoFacade = new BancoFacade();
   	 	String sql = "SELECT b FROM Banco b where b.agencia=" + agencia +
   	 			" and b.conta=" + conta;
   	 	List<Banco> listaBanco = bancoFacade.listar(sql);
   	 	if (listaBanco!=null){
   	 		if (listaBanco.size()>0){
   	 			banco = listaBanco.get(0);
   	 		}
   	 	}
	}
	
	public void carregarOutrosLancamentos(){
		OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
		String sql = "SELECT o FROM Outroslancamentos o where o.dataCompensacao>='" +
		dataInicial + "' and o.dataCompensacao<='" + dataFinal + "' and o.banco.idbanco=" + banco.getIdbanco();
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
	}
	
	public void verficarLancamentos(TransacaoBean transacao){
		for (int j = 0; j < listaLacamentos.size(); j++) {
			Float valor;
			String dataTransacao = Formatacao.ConvercaoDataPadrao(transacao.getData());
			String dataLancamento = Formatacao.ConvercaoDataPadrao(listaLacamentos.get(j).getDataCompensacao());
			if (transacao.getTipo().equalsIgnoreCase("DEBIT")){
				valor =  transacao.getValorSaida();
				if ((listaLacamentos.get(j).getValorSaida()== valor) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
				}
			}else if (transacao.getTipo().equalsIgnoreCase("CREDIT")){
				valor =  transacao.getValorEntrada();
				if ((listaLacamentos.get(j).getValorEntrada()== valor) && (dataTransacao.equals(dataLancamento))){
					listaLacamentos.get(j).setConciliacao(transacao.getId());
				}
			}
		}
	}
}
