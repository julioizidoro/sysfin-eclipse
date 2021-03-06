package br.com.financemate.manageBean.outrosLancamentos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.model.Planocontas;


@Named
@ViewScoped
public class CadConciliacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planocontas planoconta;
	private List<Planocontas> listaPlanoConta;
	private ConciliacaoBean conciliacaoBean;
	private TransacaoBean transacaoBean;
	private Outroslancamentos outroslancamentos;
	private Banco banco;
	private String campoNomeValor;
	private Float valor;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conciliacaoBean = (ConciliacaoBean) session.getAttribute("conciliacaoBean");
        transacaoBean = (TransacaoBean) session.getAttribute("transacaoBean");
        outroslancamentos = (Outroslancamentos) session.getAttribute("outroslancamentos");
        banco = (Banco) session.getAttribute("banco");
        session.removeAttribute("banco");
        session.removeAttribute("transacaoBean");
        session.removeAttribute("conciliacaoBean");
		carregarPlanoConta();
		if (outroslancamentos == null) {
			outroslancamentos = new Outroslancamentos();
		}
	}
	 
	


	public Float getValor() {
		return valor;
	}



	public void setValor(Float valor) {
		this.valor = valor;
	}



	public String getCampoNomeValor() {
		return campoNomeValor;
	}



	public void setCampoNomeValor(String campoNomeValor) {
		this.campoNomeValor = campoNomeValor;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
	}



	public Outroslancamentos getOutroslancamentos() {
		return outroslancamentos;
	}



	public void setOutroslancamentos(Outroslancamentos outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}



	public TransacaoBean getTransacaoBean() {
		return transacaoBean;
	}



	public void setTransacaoBean(TransacaoBean transacaoBean) {
		this.transacaoBean = transacaoBean;
	}



	public Planocontas getPlanoconta() {
		return planoconta;
	}




	public void setPlanoconta(Planocontas planoconta) {
		this.planoconta = planoconta;
	}




	public List<Planocontas> getListaPlanoConta() {
		return listaPlanoConta;
	}




	public void setListaPlanoConta(List<Planocontas> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}




	public ConciliacaoBean getConciliacaoBean() {
		return conciliacaoBean;
	}




	public void setConciliacaoBean(ConciliacaoBean conciliacaoBean) {
		this.conciliacaoBean = conciliacaoBean;
	}

	


	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}




	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}




	public void carregarPlanoConta(){
        PlanoContasFacade planoContaFacade = new PlanoContasFacade();
        try {
			listaPlanoConta = planoContaFacade.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planocontas>();
        }
    }
	
	
	public String salvarConciliacao(){
		OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
		outroslancamentos.setBanco(banco);
		outroslancamentos.setPlanocontas(planoconta);
		outroslancamentos.setDataCompensacao(transacaoBean.getData());
		outroslancamentos.setDescricao(transacaoBean.getDescricao());
		outroslancamentos.setCliente(banco.getCliente());
		outroslancamentos.setConciliacao("0");
		outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
		if (transacaoBean.getValorEntrada() > 0) {
			outroslancamentos.setValorEntrada(valor);
			outroslancamentos.setValorSaida(0f);
		}else{
			outroslancamentos.setValorSaida(valor);
			outroslancamentos.setValorEntrada(0f);
		}
		try {
			outroslancamentos = outrosLancamentosFacade.salvar(outroslancamentos);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestContext.getCurrentInstance().closeDialog(outroslancamentos);
		return "";
	}
	
	public String nomeCampoValor(){
		if (transacaoBean.getValorEntrada() > 0) {
			campoNomeValor = "Valor Entrada";
			valor = transacaoBean.getValorEntrada();
			return campoNomeValor;
		}else{
			valor = transacaoBean.getValorSaida();
			campoNomeValor = "Valor Saida";
			return campoNomeValor;
		}
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
