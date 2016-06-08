package br.com.financemate.util;


	import javax.annotation.PostConstruct;
	import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.hibernate.annotations.DiscriminatorFormula;
import org.jfree.data.time.Day;
import org.jfree.data.time.Year;
import org.primefaces.model.chart.Axis;
	import org.primefaces.model.chart.AxisType;
	import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
	import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartSeries;

import com.mchange.v2.c3p0.impl.NewProxyDatabaseMetaData;

import br.com.financemate.dao.ContasPagarDao;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
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
	    private BarChartModel animatedModel3;
	    private Integer nVendasJaneiros;
	    private Date dataInicial;
	    private Date dataFinal;
	    private Integer diaInicio;
	    private Integer diaFinal;
	    private Date dataTerceiroDia;
	    private Date dataQuartoDia;
	    private Date dataQuintoDia;
	    private Float saldoPrimeiroDia = 0f;
	    private Float saldoSegundoDia = 0f;
	    private Float saldoTerceiroDia = 0f;
	    private Float saldoQuartoDia = 0f;
	    private Float saldoQuintaDia = 0f;
	    private Integer numeroDia;
	    private Float valorMaximo = 0f;
	    private Float valorMinimo = 0f;
	 
	    @PostConstruct
	    public void init() {
	        createAnimatedModels();
	    }
	    
	    
	    
	    
		public BarChartModel getAnimatedModel3() {
			return animatedModel3;
		}




		public void setAnimatedModel3(BarChartModel animatedModel3) {
			this.animatedModel3 = animatedModel3;
		}




		public void setAnimatedModel1(LineChartModel animatedModel1) {
			this.animatedModel1 = animatedModel1;
		}




		public void setAnimatedModel2(BarChartModel animatedModel2) {
			this.animatedModel2 = animatedModel2;
		}




		public Float getValorMaximo() {
			return valorMaximo;
		}




		public void setValorMaximo(Float valorMaximo) {
			this.valorMaximo = valorMaximo;
		}




		public Float getValorMinimo() {
			return valorMinimo;
		}




		public void setValorMinimo(Float valorMinimo) {
			this.valorMinimo = valorMinimo;
		}




		public Integer getNumeroDia() {
			return numeroDia;
		}




		public void setNumeroDia(Integer numeroDia) {
			this.numeroDia = numeroDia;
		}




		public Float getSaldoPrimeiroDia() {
			return saldoPrimeiroDia;
		}




		public void setSaldoPrimeiroDia(Float saldoPrimeiroDia) {
			this.saldoPrimeiroDia = saldoPrimeiroDia;
		}




		public Float getSaldoSegundoDia() {
			return saldoSegundoDia;
		}




		public void setSaldoSegundoDia(Float saldoSegundoDia) {
			this.saldoSegundoDia = saldoSegundoDia;
		}




		public Float getSaldoTerceiroDia() {
			return saldoTerceiroDia;
		}




		public void setSaldoTerceiroDia(Float saldoTerceiroDia) {
			this.saldoTerceiroDia = saldoTerceiroDia;
		}




		public Float getSaldoQuartoDia() {
			return saldoQuartoDia;
		}




		public void setSaldoQuartoDia(Float saldoQuartoDia) {
			this.saldoQuartoDia = saldoQuartoDia;
		}




		public Float getSaldoQuintaDia() {
			return saldoQuintaDia;
		}




		public void setSaldoQuintaDia(Float saldoQuintaDia) {
			this.saldoQuintaDia = saldoQuintaDia;
		}




		public Date getDataTerceiroDia() {
			return dataTerceiroDia;
		}




		public void setDataTerceiroDia(Date dataTerceiroDia) {
			this.dataTerceiroDia = dataTerceiroDia;
		}




		public Date getDataQuartoDia() {
			return dataQuartoDia;
		}




		public void setDataQuartoDia(Date dataQuartoDia) {
			this.dataQuartoDia = dataQuartoDia;
		}




		public Date getDataQuintoDia() {
			return dataQuintoDia;
		}




		public void setDataQuintoDia(Date dataQuintoDia) {
			this.dataQuintoDia = dataQuintoDia;
		}




		public Integer getDiaInicio() {
			return diaInicio;
		}




		public void setDiaInicio(Integer diaInicio) {
			this.diaInicio = diaInicio;
		}




		public Integer getDiaFinal() {
			return diaFinal;
		}




		public void setDiaFinal(Integer diaFinal) {
			this.diaFinal = diaFinal;
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
	    	gerarDiasFluxoCaixa();
	        animatedModel1 = initLinearModel();
	        animatedModel1.setTitle("Fluxo de Caixa diário");
	        animatedModel1.setAnimate(true);
	        animatedModel1.setLegendPosition("ne");
	        animatedModel1.setShowPointLabels(true);
	        animatedModel1.getAxes().put(AxisType.X, new CategoryAxis("Dia"));
	        animatedModel1.setLegendPlacement(LegendPlacement . OUTSIDEGRID);
	        animatedModel1.setSeriesColors("66cc66, FE2E2E, A4A4A4");
	        animatedModel1.setZoom(true);
	        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
	        yAxis.setLabel("R$");
	        yAxis.setMin(valorMinimo.floatValue());
	        yAxis.setMax(valorMaximo.floatValue()  + 100);
	        
	         
	        animatedModel2 = initBarModel();
	        
	        animatedModel2.setTitle("Grafico de vendas");
	        animatedModel2.setAnimate(true);
	        animatedModel2.setShowPointLabels(true);
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
	        Calendar cal = Calendar.getInstance();  
            cal.setTime(new Date()); 
            int dia = cal.get(Calendar.DAY_OF_MONTH);
            Date data = cal.getTime();
            int mes = data.getMonth() + 1;
            int ndia = data.getDay() + 8;
	        if (mes == 1) {
				if (ndia > 31) {
					dia = 1;
				}  
			}else if(mes == 2){
				if (ndia > 28) {
					dia = 1;
				}
			}else if(mes == 3){
				if (ndia > 31) {
					dia = 1;
				}
			}else if(mes == 4){
				if (ndia > 30) {
					dia = 1;
				}
			}else if(mes == 5){
				if (ndia > 31) {
					dia = 1;
				}
			}else if(mes == 6){
				if (ndia > 30) {
					dia = 1;
				}
			}else if(mes == 7){
				if (ndia > 31) {
					dia = 1;
				}
			}else if(mes == 8){
				if (ndia > 31) {
					dia = 1;
				}
			}else if(mes == 9){
				if (ndia > 30) {
					dia = 1;
				}
			}else if(mes == 10){
				if (ndia > 31) {
					dia = 1;
				}
			}else if(mes == 11){
				if (ndia > 30) {
					dia = 1;
				}
			}else if(mes == 12){
				if (ndia > 31) {
					dia = 1;
				}
			}
	        LineChartSeries recebimentos = new LineChartSeries();
	        recebimentos.setLabel("Recebimentos");
	        recebimentos.set(dia, recebimentosDia1());
	        recebimentos.set(dia +1, recebimentosDia2());
	        recebimentos.set(dia + 2, recebimentosDia3());
	        recebimentos.set(dia + 3, recebimentosDia4());
	        recebimentos.set(dia + 4, recebimentosDia5()); 
	         
	        LineChartSeries pagamentos = new LineChartSeries();
	        pagamentos.setLabel("Pagamentos");
	        pagamentos.set(dia, pagamentodia1());
	        pagamentos.set(dia +1, pagamentodia2());
	        pagamentos.set(dia +2, pagamentodia3());
	        pagamentos.set(dia +3, pagamentodia4());
	        pagamentos.set(dia +4, pagamentodia5());

	        
	        LineChartSeries saldo = new LineChartSeries();
	        saldo.setLabel("Saldo");
	 
	        saldo.set(dia, saldoPrimeiroDia);
	        saldo.set(dia +1, saldoSegundoDia);
	        saldo.set(dia +2, saldoTerceiroDia);
	        saldo.set(dia +3, saldoQuartoDia);
	        saldo.set(dia +4, saldoQuintaDia);
	        
	        model.addSeries(recebimentos);
	        model.addSeries(pagamentos);
	        model.addSeries(saldo);
	        gerarValorMinimoMaximo(); 
	        return model;
	    }
	    
	    
	    public void gerarValorMinimoMaximo() {
			// TODO Auto-generated method stub
			
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
	    
	    
	    public void gerarDiasFluxoCaixa(){
	    	dataInicial = new Date();
	    	numeroDia = dataInicial.getDay();
	    	Calendar c = new GregorianCalendar();
			c.setTime(dataInicial);
			c.add(Calendar.DAY_OF_MONTH, 5);
			Date data = c.getTime();
			dataFinal = data;
			diaFinal = dataFinal.getDay();
			Calendar c2 = new GregorianCalendar();
			c2.setTime(dataInicial);
			c2.add(Calendar.DAY_OF_MONTH, 1);
			Date data2 = c2.getTime();
			dataInicial = data2;
			diaInicio = dataInicial.getDay();
			Calendar c3 = new GregorianCalendar();
			c3.setTime(dataInicial);
			c3.add(Calendar.DAY_OF_MONTH, 1);
			Date data3 = c3.getTime();
			dataTerceiroDia = data3;
			Calendar c4 = new GregorianCalendar();
			c4.setTime(dataTerceiroDia);
			c4.add(Calendar.DAY_OF_MONTH, 1);
			Date data4 = c4.getTime();
			dataQuartoDia = data4;
			Calendar c5 = new GregorianCalendar();
			c5.setTime(dataQuartoDia);
			c5.add(Calendar.DAY_OF_MONTH, 1);
			Date data5 = c5.getTime();
			dataQuintoDia = data5;
	    }
	    
	    public float recebimentosDia1(){
	    	Float recebimento = null;
	    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	    	try {
	    	 String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(new Date()) + "'";
			 recebimento = contasReceberFacade.recebimentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoPrimeiroDia = saldoPrimeiroDia + recebimento;
	    	if (recebimento >= valorMaximo ) {
				valorMaximo = recebimento;
			}else if(recebimento < valorMinimo){
				valorMinimo = recebimento;
			}
	    	if (saldoPrimeiroDia >= valorMaximo) {
				valorMaximo = saldoPrimeiroDia;
			}else if(saldoPrimeiroDia < valorMinimo){
				valorMinimo = saldoPrimeiroDia;
			}
	    	return recebimento; 
	    }
	    
	    public float recebimentosDia2(){
	    	Float recebimento = null;
	    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	    	try {
	    	 String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataInicial) + "'";
			 recebimento = contasReceberFacade.recebimentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoSegundoDia = saldoSegundoDia + recebimento;
	    	if (recebimento >= valorMaximo ) {
				valorMaximo = recebimento;
			}else if(recebimento < valorMinimo){
				valorMinimo = recebimento;
			}
	    	if (saldoSegundoDia >= valorMaximo) {
				valorMaximo = saldoSegundoDia;
			}else if(saldoSegundoDia < valorMinimo){
				valorMinimo = saldoSegundoDia;
			}
	    	return recebimento; 
	    }
	    
	    public float recebimentosDia3(){
	    	Float recebimento = null;
	    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	    	try {
	    	 String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataTerceiroDia) + "'";
			 recebimento = contasReceberFacade.recebimentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoTerceiroDia = saldoTerceiroDia + recebimento;
	    	if (recebimento >= valorMaximo ) {
				valorMaximo = recebimento;
			}else if(recebimento < valorMinimo){
				valorMinimo = recebimento;
			}
	    	if (saldoTerceiroDia >= valorMaximo) {
				valorMaximo = saldoTerceiroDia;
			}else if(saldoTerceiroDia < valorMinimo){
				valorMinimo = saldoTerceiroDia;
			}
	    	return recebimento; 
	    }
	    public float recebimentosDia4(){
	    	Float recebimento = null;
	    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	    	try {
	    	 String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuartoDia) + "'";
			 recebimento = contasReceberFacade.recebimentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoQuartoDia = saldoQuartoDia + recebimento;
	    	if (recebimento >= valorMaximo ) {
				valorMaximo = recebimento;
			}else if(recebimento < valorMinimo){
				valorMinimo = recebimento;
			}
	    	if (saldoQuartoDia >= valorMaximo) {
				valorMaximo = saldoQuartoDia;
			}else if(saldoQuartoDia < valorMinimo){
				valorMinimo = saldoQuartoDia;
			}
	    	return recebimento; 
	    }
	    public float recebimentosDia5(){
	    	Float recebimento = null;
	    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	    	try {
	    	 String sql = "Select sum(c.valorParcela) from Contasreceber c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuintoDia) + "'";
			 recebimento = contasReceberFacade.recebimentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoQuintaDia = saldoQuintaDia + recebimento;
	    	if (recebimento >= valorMaximo ) {
				valorMaximo = recebimento;
			}else if(recebimento < valorMinimo){
				valorMinimo = recebimento;
			}
	    	if (saldoQuintaDia >= valorMaximo) {
				valorMaximo = saldoQuintaDia;
			}else if(saldoQuintaDia < valorMinimo){
				valorMinimo = saldoQuintaDia;
			}
	    	return recebimento; 
	    }
	    
	    
	    public float pagamentodia1(){
	    	Float pagamento = null;
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	    	try {
	    	 String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(new Date()) + "'";
	    	 pagamento = contasPagarFacade.pagamentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoPrimeiroDia = saldoPrimeiroDia - pagamento;
	    	if (pagamento >= valorMaximo ) {
				valorMaximo = pagamento;
			}else if(pagamento < valorMinimo){
				valorMinimo = pagamento;
			}
	    	if (saldoQuintaDia >= valorMaximo) {
				valorMaximo = saldoPrimeiroDia;
			}else if(saldoPrimeiroDia < valorMinimo){
				valorMinimo = saldoPrimeiroDia;
			}
	    	return pagamento; 
	    }
	    
	    public float pagamentodia2(){
	    	Float pagamento = null;
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	    	try {
	    	 String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataInicial) + "'";
	    	 pagamento = contasPagarFacade.pagamentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoSegundoDia = saldoSegundoDia - pagamento;
	    	if (pagamento >= valorMaximo ) {
				valorMaximo = pagamento;
			}else if(pagamento < valorMinimo){
				valorMinimo = pagamento;
			}
	    	if (saldoSegundoDia >= valorMaximo) {
				valorMaximo = saldoSegundoDia;
			}else if(saldoSegundoDia < valorMinimo){
				valorMinimo = saldoSegundoDia;
			}
	    	return pagamento; 
	    }
	    
	    public float pagamentodia3(){
	    	Float pagamento = null;
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	    	try {
	    	 String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataTerceiroDia) + "'";
	    	 pagamento = contasPagarFacade.pagamentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoTerceiroDia = saldoTerceiroDia - pagamento;
	    	if (pagamento >= valorMaximo ) {
				valorMaximo = pagamento;
			}else if(pagamento < valorMinimo){
				valorMinimo = pagamento;
			}
	    	if (saldoTerceiroDia >= valorMaximo) {
				valorMaximo = saldoTerceiroDia;
			}else if(saldoTerceiroDia < valorMinimo){
				valorMinimo = saldoTerceiroDia;
			}
	    	return pagamento; 
	    }
	    
	    public float pagamentodia4(){
	    	Float pagamento = null;
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	    	try {
	    	 String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuartoDia) + "'";
	    	 pagamento = contasPagarFacade.pagamentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoQuartoDia = saldoQuartoDia - pagamento;
	    	if (pagamento >= valorMaximo ) {
				valorMaximo = pagamento;
			}else if(pagamento < valorMinimo){
				valorMinimo = pagamento;
			}
	    	if (saldoQuartoDia >= valorMaximo) {
				valorMaximo = saldoQuartoDia;
			}else if(saldoQuartoDia < valorMinimo){
				valorMinimo = saldoQuartoDia;
			}
	    	return pagamento; 
	    }
	    
	    public float pagamentodia5(){
	    	Float pagamento = null;
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	    	try {
	    	 String sql = "Select sum(c.valor) from Contaspagar c where c.dataVencimento='" + Formatacao.ConvercaoDataNfe(dataQuintoDia) + "'";
	    	 pagamento = contasPagarFacade.pagamentoPorDia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	saldoQuintaDia = saldoQuintaDia - pagamento;
	    	if (pagamento >= valorMaximo ) {
				valorMaximo = pagamento;
			}else if(pagamento < valorMinimo){
				valorMinimo = pagamento;
			}
	    	if (saldoQuintaDia >= valorMaximo) {
				valorMaximo = saldoQuintaDia;
			}else if(saldoQuintaDia < valorMinimo){
				valorMinimo = saldoQuintaDia;
			}
	    	return pagamento; 
	    }
	}
