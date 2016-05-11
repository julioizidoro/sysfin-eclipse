package br.com.financemate.manageBean.outrosLancamentos;

import java.util.Date;

public class TransacaoBean {
	
	private String id;
	private String tipo;
	private Date data;
	private String descricao;
	private Float valorEntrada;
	private Float valorSaida;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Float getValorEntrada() {
		return valorEntrada;
	}
	public void setValorEntrada(Float valorEntrada) {
		this.valorEntrada = valorEntrada;
	}
	public Float getValorSaida() {
		return valorSaida;
	}
	public void setValorSaida(Float valorSaida) {
		this.valorSaida = valorSaida;
	}
	
	
	

}
