package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.util.Formatacao;

@Named
@SessionScoped
public class CalculosContasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;	
	private String crVencer;
	private String crVencidas;
	private String crVencendo;
	private String crTotal;
	private String cpVencer;
	private String cpVencidas;
	private String cpVencendo;
	private String cpTotal;
	
	@PostConstruct
	public void init(){
		getCrTotal();
		calcularTotalContasPagar();
		calcularTotalContasPagar();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	
	
	public String getCrVencer() {
		return crVencer;
	}

	public void setCrVencer(String crVencer) {
		this.crVencer = crVencer;
	}

	public String getCrVencidas() {
		return crVencidas;
	}

	public void setCrVencidas(String crVencidas) {
		this.crVencidas = crVencidas;
	}

	public String getCrVencendo() {
		return crVencendo;
	}

	public void setCrVencendo(String crVencendo) {
		this.crVencendo = crVencendo;
	}

	public String getCpVencer() {
		return cpVencer;
	}

	public void setCpVencer(String cpVencer) {
		this.cpVencer = cpVencer;
	}

	public String getCpVencidas() {
		return cpVencidas;
	}

	public void setCpVencidas(String cpVencidas) {
		this.cpVencidas = cpVencidas;
	}

	public String getCpVencendo() {
		return cpVencendo;
	}

	public void setCpVencendo(String cpVencendo) {
		this.cpVencendo = cpVencendo;
	}
	
	

	public String getCrTotal() {
		return crTotal;
	}

	public void setCrTotal(String crTotal) {
		this.crTotal = crTotal;
	}

	public String getCpTotal() {
		return cpTotal;
	}

	public void setCpTotal(String cpTotal) {
		this.cpTotal = cpTotal;
	}

	private void calcularTotalContasPagar(){
		Float vencida = 0.0f;
        Float vencendo = 0.0f;
        Float vencer = 0.0f;
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		List<Double> listaTotais = null;
		int idcliente = 0;
		if (usuarioLogadoMB.getUsuario().getCliente()>0){
			idcliente = usuarioLogadoMB.getUsuario().getCliente();
		}else idcliente = 8;
		try {
			listaTotais = contasPagarFacade.calculaSaldos(Formatacao.ConvercaoDataSql(new Date()), idcliente);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (listaTotais!=null){
			vencida = listaTotais.get(0).floatValue();
	        vencendo = listaTotais.get(1).floatValue();
	        vencer = listaTotais.get(2).floatValue();
		}
        setCpVencidas(Formatacao.foramtarFloatString(vencida));
        setCpVencendo(Formatacao.foramtarFloatString(vencendo));
        setCpVencer(Formatacao.foramtarFloatString(vencer));
        setCpTotal(Formatacao.foramtarFloatString(vencida+vencer+vencendo));
    }
	
	
	public void calcularTotaisContasReceber(){
		Float vencida = 0.0f;
		 Float vencendo = 0.0f;
		 Float vencer = 0.0f;
		 ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		 List<Double> listaTotais = null;
		 int idcliente = 0;
			if (usuarioLogadoMB.getUsuario().getCliente()>0){
				idcliente = usuarioLogadoMB.getUsuario().getCliente();
			}else idcliente = 8;
		 try {
			 listaTotais = contasReceberFacade.calculaSaldos(Formatacao.ConvercaoDataSql(new Date()), idcliente);
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		 if (listaTotais!=null){
			 vencida = listaTotais.get(0).floatValue();
			 vencendo = listaTotais.get(1).floatValue();
			 vencer = listaTotais.get(2).floatValue();
		 }
		 setCrVencidas(Formatacao.foramtarFloatString(vencida));
	     setCrVencendo(Formatacao.foramtarFloatString(vencendo));
	     setCrVencer(Formatacao.foramtarFloatString(vencer));
	     setCrTotal(Formatacao.foramtarFloatString(vencida+vencer+vencendo));
	}
}
