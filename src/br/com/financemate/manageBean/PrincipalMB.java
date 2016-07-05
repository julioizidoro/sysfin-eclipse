package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jfree.data.time.Year;

import br.com.financemate.facade.VendasFacade;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class PrincipalMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Vendas> lista10Vendas;
	private Vendas vendas;
	private Float valorMesVendas = 0f;
	private String valorTotalMesVendas = "";
	
	
	@PostConstruct
	public void init(){
		gerarListaUltimasVendas();
		gerarVendasMensais();
		valorTotalMesVendas = Formatacao.foramtarFloatString(valorMesVendas);
	}

	
    
	
 
	public Float getValorMesVendas() {
		return valorMesVendas;
	}





	public void setValorMesVendas(Float valorMesVendas) {
		this.valorMesVendas = valorMesVendas;
	}





	public String getValorTotalMesVendas() {
		return valorTotalMesVendas;
	}





	public void setValorTotalMesVendas(String valorTotalMesVendas) {
		this.valorTotalMesVendas = valorTotalMesVendas;
	}





	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public List<Vendas> getLista10Vendas() {
		return lista10Vendas;
	}


	public void setLista10Vendas(List<Vendas> lista10Vendas){
		this.lista10Vendas = lista10Vendas;
	}


	public Vendas getVendas() {
		return vendas;
	}


	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}
	
	
	
	public void gerarListaUltimasVendas(){
		VendasFacade vendasFacade = new VendasFacade();
		List<Vendas> listaVendas;
		lista10Vendas = new ArrayList<Vendas>();
		try {
			listaVendas = vendasFacade.listar("Select v from Vendas v order by v.idvendas DESC");
			for (int i = 0; i < 10; i++) {
				lista10Vendas.add(listaVendas.get(i));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void gerarVendasMensais(){
    	VendasFacade vendasFacade = new VendasFacade();
    	Calendar cal = GregorianCalendar.getInstance();
    	String messql = "";
    	cal.setTime(new Date());
    	int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    	int mes = (cal.get(Calendar.MONDAY) + 1);
    	if (mes < 10) {
			messql = "0"+mes;
		}
    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-"+  messql  +"-01'"+
    				 " and v.dataVenda<='"+ new Year()+"-"+ messql + "-" + dia +"'";
    	try {
    		
			List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
			if (listaQuantidadeVendas == null || listaQuantidadeVendas.isEmpty()) {
				listaQuantidadeVendas = new ArrayList<Vendas>();
			}
			for (int i = 0; i < listaQuantidadeVendas.size(); i++) {
				valorMesVendas = valorMesVendas + listaQuantidadeVendas.get(i).getValorLiquido();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	
	public String pegarMesAno(){
		String mes = "";
		String ano = "";
		ano = "" + new Year();
		Calendar cal = new GregorianCalendar();
		mes = "" + (cal.get(Calendar.MONDAY) + 1);
		if (mes.equalsIgnoreCase("1")) {
			mes = "JANEIRO";
		}else if(mes.equalsIgnoreCase("2")){
			mes = "FEVEREIRO";
		}else if(mes.equalsIgnoreCase("3")){
			mes = "MARÇO";
		}else if(mes.equalsIgnoreCase("4")){
			mes = "ABRIL";
		}else if(mes.equalsIgnoreCase("5")){
			mes = "MAIO";
		}else if(mes.equalsIgnoreCase("6")){
			mes = "JUNHO";
		}else if(mes.equalsIgnoreCase("7")){
			mes = "JULHO"; 
		}else if(mes.equalsIgnoreCase("8")){
			mes = "AGOSTO";
		}else if(mes.equalsIgnoreCase("9")){
			mes = "SETEMBRO";
		}else if(mes.equalsIgnoreCase("10")){
			mes = "OUTUBRO";
		}else if(mes.equalsIgnoreCase("1")){
			mes = "NOVEMBRO";
		}else if(mes.equalsIgnoreCase("12")){
			mes = "DEZEMBRO";
		}
		return mes + " | " + ano;
	}
	 
}
