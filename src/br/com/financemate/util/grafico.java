package br.com.financemate.util;


	import javax.annotation.PostConstruct;
	import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.jfree.data.time.Year;
import org.primefaces.model.chart.Axis;
	import org.primefaces.model.chart.AxisType;
	import org.primefaces.model.chart.BarChartModel;
	import org.primefaces.model.chart.LineChartModel;
	import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartSeries;

import br.com.financemate.facade.VendasFacade;
import br.com.financemate.model.Vendas;
	 
	@ManagedBean
	public class grafico implements Serializable {
	 
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LineChartModel animatedModel1;
	    private BarChartModel animatedModel2;
	    private Integer nVendasJaneiros;
	 
	    @PostConstruct
	    public void init() {
	        createAnimatedModels();
	    }
	    
	    
	 
	    public Integer getnVendasJaneiros() {
			return nVendasJaneiros;
		}



		public void setnVendasJaneiros(Integer nVendasJaneiros) {
			this.nVendasJaneiros = nVendasJaneiros;
		}



		public LineChartModel getAnimatedModel1() {
	        return animatedModel1;
	    }
	 
	    public BarChartModel getAnimatedModel2() {
	        return animatedModel2;
	    }
	 
	    private void createAnimatedModels() {
	        animatedModel1 = initLinearModel();
	        animatedModel1.setTitle("Fluxo de Caixa diário");
	        animatedModel1.setAnimate(true);
	        animatedModel1.setLegendPosition("ne");
	        animatedModel1.setLegendPlacement(LegendPlacement . OUTSIDEGRID);
	        animatedModel1.setSeriesColors("66cc66, FE2E2E, A4A4A4"); 
	        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(10);
	        
	         
	        animatedModel2 = initBarModel();
	        
	        animatedModel2.setTitle("Grafico de vendas");
	        animatedModel2.setAnimate(true);
	        animatedModel2.setLegendPosition("ne");
	        yAxis = animatedModel2.getAxis(AxisType.Y);
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	    }
	     
	    private BarChartModel initBarModel() {
	        BarChartModel model = new BarChartModel();
	        ChartSeries mes = new ChartSeries();
	        mes.setLabel("Quantidade");
	        
	        mes.set("Jan", gerarVendasMensaisJaneiro());
	        mes.set("Fev", gerarVendasMensaisFevereiro());
	        mes.set("Mar", gerarVendasMensaisMarco());
	        mes.set("Abri", gerarVendasMensaisAbril());
	        mes.set("Mai", gerarVendasMensaisMaio());
	        mes.set("Jun", gerarVendasMensaisJunho());
	        mes.set("Jul", gerarVendasMensaisJulho());
	        mes.set("Ago", gerarVendasMensaisAgosto());
	        mes.set("Set", gerarVendasMensaisSetembro());
	        mes.set("Out", gerarVendasMensaisOutubro());
	        mes.set("Nov", gerarVendasMensaisNovembro());
	        mes.set("Dez", gerarVendasMensaisDezembro());
	        model.addSeries(mes);
	         
	        return model;
	    }
	    
	    
	     
	    private LineChartModel initLinearModel() {
	        LineChartModel model = new LineChartModel();
	        
	        
	        LineChartSeries recebimentos = new LineChartSeries();
	        recebimentos.setLabel("Recebimentos");
	        recebimentos.set(1, 2);
	        recebimentos.set(2, 1);
	        recebimentos.set(3, 3);
	        recebimentos.set(4, 6);
	        recebimentos.set(5, 10);
	        
	        LineChartSeries pagamentos = new LineChartSeries();
	        pagamentos.setLabel("Pagamentos");
	        pagamentos.set(1, 6);
	        pagamentos.set(2, 3);
	        pagamentos.set(3, 2);
	        pagamentos.set(4, 7);
	        pagamentos.set(5, 9);
	 
	        
	        LineChartSeries saldo = new LineChartSeries();
	        saldo.setLabel("Saldo");
	 
	        saldo.set(1, 7);
	        saldo.set(2, 3);
	        saldo.set(3, 5);
	        saldo.set(4, 6);
	        saldo.set(5, 4);
	        
	        model.addSeries(recebimentos);
	        model.addSeries(pagamentos);
	        model.addSeries(saldo);
	         
	        return model;
	    }
	    
	    
	    public Integer gerarVendasMensaisJaneiro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-01-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-01-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisFevereiro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-02-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-02-28'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisMarco(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-03-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-03-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisAbril(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-04-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-04-30'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisMaio(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-05-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-05-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisJunho(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-06-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-06-30'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    
	    public Integer gerarVendasMensaisJulho(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-07-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-07-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    
	    public Integer gerarVendasMensaisAgosto(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-08-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-08-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisSetembro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-09-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-09-30'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisOutubro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-10-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-10-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisNovembro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-11-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-11-30'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	    
	    public Integer gerarVendasMensaisDezembro(){
	    	VendasFacade vendasFacade = new VendasFacade();
	    	String sql = "Select v From Vendas v where v.dataVenda>='" + new Year()+"-12-01'"+
	    				 " and v.dataVenda<='"+ new Year()+"-12-31'";
	    	try {
				List<Vendas> listaQuantidadeVendas = vendasFacade.listar(sql);
				return listaQuantidadeVendas.size(); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	    }
	   
	    
	}
