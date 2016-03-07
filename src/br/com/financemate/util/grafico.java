package br.com.financemate.util;


	import javax.annotation.PostConstruct;
	import java.io.Serializable;
	import javax.faces.bean.ManagedBean;
	import org.primefaces.model.chart.Axis;
	import org.primefaces.model.chart.AxisType;
	import org.primefaces.model.chart.BarChartModel;
	import org.primefaces.model.chart.LineChartModel;
	import org.primefaces.model.chart.ChartSeries;
	import org.primefaces.model.chart.LineChartSeries;
	 
	@ManagedBean
	public class grafico implements Serializable {
	 
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LineChartModel animatedModel1;
	    private BarChartModel animatedModel2;
	 
	    @PostConstruct
	    public void init() {
	        createAnimatedModels();
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
	        mes.setLabel("Mês, Quantidade");
	        mes.set("Jan", 100);
	        mes.set("Fev", 60);
	        mes.set("Mar", 110);
	        mes.set("Abri", 135);
	        mes.set("Mai", 120);
	        mes.set("Jun", 121);
	        mes.set("Jul", 122);
	        mes.set("Ago", 123);
	        mes.set("Set", 124);
	        mes.set("Out", 125);
	        mes.set("Nov", 126);
	        mes.set("Dez", 127);
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
	    
	}
