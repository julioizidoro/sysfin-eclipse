package br.com.financemate.manageBean.contasReceber;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.manageBean.CalculosContasMB;
import br.com.financemate.manageBean.ContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class ContasReceberMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private Date dataInicial;
    private Date dataFinal;
    private String sql;
    private List<Contasreceber> listaContasReceber;
    private Contasreceber contasReceber;
    private boolean recebidas;
    private boolean verCliente=false;
    private int quantidadeTitulos;
    private float totalContasReceber;
    private float totalJurosReceber;
    private float totalDescontosReceber;
    private float valorTotalRecebido;
    private String totalVencer;
    private String totalVencidas;
    private String total;
    private Banco banco;
    private List<Banco> listaBanco;
    private String imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
    private List<Contasreceber> listaSelecionadas;
    private Vendas vendas;
    @Inject
    private CalculosContasMB calculosContasMB;
    private Boolean habilitar = true;
    private List<Contasreceber> listaContasSelecionadas;
    private String totalReceberLote;
    private Date dataRecebimentoLote;
    private String nomeCliente;
    private float valorParcela;
    private Integer nVenda;
	
    @PostConstruct
	public void init(){
		gerarListaCliente();
		getUsuarioLogadoMB();
		verificarCliente();
		criarConsultaContaReceber();
		gerarListaContas();
	}
    
    


	public Integer getnVenda() {
		return nVenda;
	}




	public void setnVenda(Integer nVenda) {
		this.nVenda = nVenda;
	}




	public float getValorParcela() {
		return valorParcela;
	}




	public void setValorParcela(float valorParcela) {
		this.valorParcela = valorParcela;
	}




	public String getNomeCliente() {
		return nomeCliente;
	}




	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}




	public List<Contasreceber> getListaContasSelecionadas() {
		return listaContasSelecionadas;
	}



	public void setListaContasSelecionadas(List<Contasreceber> listaContasSelecionadas) {
		this.listaContasSelecionadas = listaContasSelecionadas;
	}



	public String getTotalReceberLote() {
		return totalReceberLote;
	}





	public void setTotalReceberLote(String totalReceberLote) {
		this.totalReceberLote = totalReceberLote;
	}





	public Date getDataRecebimentoLote() {
		return dataRecebimentoLote;
	}





	public void setDataRecebimentoLote(Date dataRecebimentoLote) {
		this.dataRecebimentoLote = dataRecebimentoLote;
	}





	public Boolean getHabilitar() {
		return habilitar;
	}





	public void setHabilitar(Boolean habilitar) {
		this.habilitar = habilitar;
	}





	public CalculosContasMB getCalculosContasMB() {
		return calculosContasMB;
	}





	public void setCalculosContasMB(CalculosContasMB calculosContasMB) {
		this.calculosContasMB = calculosContasMB;
	}





	public Vendas getVendas() {
		return vendas;
	}





	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}





	public List<Contasreceber> getListaSelecionadas() {
		return listaSelecionadas;
	}





	public void setListaSelecionadas(List<Contasreceber> listaSelecionadas) {
		this.listaSelecionadas = listaSelecionadas;
	}





	public String getImagemFiltro() {
		return imagemFiltro;
	}





	public void setImagemFiltro(String imagemFiltro) {
		this.imagemFiltro = imagemFiltro;
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





	public String getTotal() {
		return total;
	}





	public void setTotal(String total) {
		this.total = total;
	}





	public String getTotalVencer() {
		return totalVencer;
	}





	public void setTotalVencer(String totalVencer) {
		this.totalVencer = totalVencer;
	}





	public String getTotalVencidas() {
		return totalVencidas;
	}





	public void setTotalVencidas(String totalVencidas) {
		this.totalVencidas = totalVencidas;
	}





	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public List<Cliente> getListaCliente() {
		return listaCliente;
	}



	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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



	public String getSql() {
		return sql;
	}



	public void setSql(String sql) {
		this.sql = sql;
	}



	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}



	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}



	public Contasreceber getContasReceber() {
		return contasReceber;
	}



	public void setContasReceber(Contasreceber contasReceber) {
		this.contasReceber = contasReceber;
	}



	public boolean isRecebidas() {
		return recebidas;
	}



	public void setRecebidas(boolean recebidas) {
		this.recebidas = recebidas;
	}



	public boolean isVerCliente() {
		return verCliente;
	}



	public void setVerCliente(boolean verCliente) {
		this.verCliente = verCliente;
	}



	public int getQuantidadeTitulos() {
		return quantidadeTitulos;
	}



	public void setQuantidadeTitulos(int quantidadeTitulos) {
		this.quantidadeTitulos = quantidadeTitulos;
	}



	public float getTotalContasReceber() {
		return totalContasReceber;
	}



	public void setTotalContasReceber(float totalContasReceber) {
		this.totalContasReceber = totalContasReceber;
	}



	public float getTotalJurosReceber() {
		return totalJurosReceber;
	}



	public void setTotalJurosReceber(float totalJurosReceber) {
		this.totalJurosReceber = totalJurosReceber;
	}



	public float getTotalDescontosReceber() {
		return totalDescontosReceber;
	}



	public void setTotalDescontosReceber(float totalDescontosReceber) {
		this.totalDescontosReceber = totalDescontosReceber;
	}



	public float getValorTotalRecebido() {
		return valorTotalRecebido;
	}



	public void setValorTotalRecebido(float valorTotalRecebido) {
		this.valorTotalRecebido = valorTotalRecebido;
	}



	public String novaConta() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadContasReceber");
        return "";
    }
	
	public String novaContaReceberTelaPrincipal() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadContasReceberPrincipal");
        return "";
    }
	
	public void retornoDialogNovoPrincipal(String valor) {
        calculosContasMB.calcularTotaisContasReceber();
        calculosContasMB.habilitarDesabilitarCompraTelaPrincipal(valor);
    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public String novaImpressao() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("imprimirContasReceber"); 
        return "";
    }
	
	public String novaImpressaoContasReceberTelaPrincipal() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("imprimirContasReceberTelaPrincipal"); 
        return "";
    }
	
	 public void criarConsultaContaReceber(){
		 String data = Formatacao.ConvercaoDataPadrao(new Date());
		 String mesString = data.substring(3, 5);
		 String anoString = data.substring(6, 10);
		 int mesInicio = Integer.parseInt(mesString);
		 int anoInicio = Integer.parseInt(anoString);
		 int mescInicio;
		 int mescFinal;
		 int anocInicio = 0;
		 int anocFinal = 0;
		 if (mesInicio==1){
			 mescInicio=12;
			 anocInicio=anoInicio - 1;
		 }else {
			 mescInicio = mesInicio - 1;
			 anocInicio = anoInicio;
		 }
		 if (mesInicio==12){
			 mescFinal=1;
			 anocFinal=anoInicio+1;
		 }else {
			 mescFinal = mesInicio  + 1;
			 anocFinal = anoInicio;
		 }
		 String dataInicial = anocInicio + "-" + Formatacao.retornaDataInicia(mescInicio);
		 String dataFinal = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
		 setDataInicial(Formatacao.ConvercaoStringData(dataInicial));
		 setDataFinal(Formatacao.ConvercaoStringData(dataFinal));
		 if (usuarioLogadoMB.getUsuario().getCliente()>0){
			 sql = " Select v from Contasreceber v where v.dataVencimento<='" + dataFinal + 
					 "' and v.valorPago=0 and v.cliente.idcliente=" + usuarioLogadoMB.getUsuario().getCliente() + 
					 " and v.dataPagamento=null order by v.dataVencimento";
		 }else {
			 sql = " Select v from Contasreceber v where v.cliente.visualizacao='Operacional' and "
					 + "v.dataVencimento<='" + dataFinal + 
					 "' and v.valorPago=0  and v.dataPagamento=null order by v.dataVencimento";
	        }  
		 
	 }
	 
	 public void gerarListaContas() {
		 try {
			 ContasReceberFacade contasReceberFacadece = new ContasReceberFacade();
			 listaContasReceber = contasReceberFacadece.listar(sql);
			 if (listaContasReceber == null) {
				 listaContasReceber = new ArrayList<Contasreceber>();
			 }
		 } catch (SQLException ex) {
			 Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro Listar Contas", "Erro");
		 }
		 //gerarTotalContas();
		 calculosContasMB.calcularTotaisContasReceber();
	 }
	 
	 public void gerarTotalContas(){
		 quantidadeTitulos = listaContasReceber.size();
		 totalContasReceber = 0;
		 totalJurosReceber = 0;
		 totalDescontosReceber = 0;
		 valorTotalRecebido = 0;
		 for (int i = 0; i < listaContasReceber.size(); i++) {
			 totalContasReceber = totalContasReceber + listaContasReceber.get(i).getValorParcela();
			 totalJurosReceber = totalJurosReceber + listaContasReceber.get(i).getJuros();
			 totalDescontosReceber = totalDescontosReceber + listaContasReceber.get(i).getDesagio();
			 valorTotalRecebido = valorTotalRecebido + listaContasReceber.get(i).getValorPago();
		 }
	 }
	 
	 public String verStatus(Contasreceber contasreceber) {
	        Date data = new Date();
	        String diaData = Formatacao.ConvercaoDataPadrao(data);
	        data = Formatacao.ConvercaoStringDataBrasil(diaData);
	        if (contasreceber.getDataVencimento().after(data)) {
	            return "../../resources/img/bolaVerde.png";
	        } else {
	            if (!contasreceber.getDataVencimento().before(data)) {
	                return "../../resources/img/bolaVermelha.png";
	            } else {
	                if (contasreceber.equals(data)) {
	                    return "../../resources/img/bolaAmarela.png";
	                }
	            }
	        }
	        return "../../resources/img/bolaVerde.png";
	    }
	 
	 public void gerarListaCliente(){
	        ClienteFacade clienteFacade = new ClienteFacade();
	        try {
	            listaCliente = clienteFacade.listar("");
	            if (listaCliente == null) {
	                listaCliente = new ArrayList<Cliente>();
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
	            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
	        }
	    }
	 
	 public void verificarCliente(){
	        if (usuarioLogadoMB.getUsuario().getCliente()>0){
	            ClienteFacade clienteFacade = new ClienteFacade();
	            try {
	                cliente = clienteFacade.consultar(usuarioLogadoMB.getUsuario().getCliente());
	                verCliente = true;
	                if(cliente==null){
	                    verCliente=false;
	                    cliente = new Cliente();
	                }
	            } catch (SQLException ex) {
	                Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        }
	    

	 }
	 
	 public void retornoDialogNovo(SelectEvent event) {
		 Contasreceber contasreceber = (Contasreceber) event.getObject();
		 gerarListaContas();
		 calculosContasMB.calcularTotalContasPagar();
	 }
	 
	 public void excluir(){
		 ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		 contasReceberFacade.excluir(contasReceber.getIdcontasReceber());
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
		 gerarListaContas();
	 }
	 
	 public String editar(Contasreceber contasreceber){
		 if (contasreceber!=null){
			 FacesContext fc = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			 session.setAttribute("contareceber", contasreceber);       
			 RequestContext.getCurrentInstance().openDialog("cadContasReceber");
			 
		 }
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
	 
	 public void novoFiltro() {
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 500);
		 RequestContext.getCurrentInstance().openDialog("filtroConsContaReceber");
	 }
	 
	 public void filtrar(){		 
		 sql = "Select v from Contasreceber v where ";
		 if (cliente!=null){
			 sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
		 }else {
			 sql = sql + " v.cliente.visualizacao='Operacional' and ";
		 }
		 
		 if (nomeCliente!="") {
			 sql = sql + " v.nomeCliente=" + nomeCliente + " and ";
		 }
		
		if (valorParcela!=0f) {
			sql = sql + " v.valorParcela=" + valorParcela + " and ";
		}
		
		if (nVenda!=0) {
			sql = sql + " v.vendas_idvendas=" + nVenda + " and ";
		}
		
		if (banco!=null) {
			sql = sql + " v.banco_idbanco=" + banco + " and ";
		}
		 
		 if ((dataInicial!=null) && (dataFinal!=null)){
			 sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + 
					 "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + 
					 "' order by v.dataVencimento";
		 }
		 gerarListaContas();
		 RequestContext.getCurrentInstance().closeDialog(sql);
	 }
	 
	 public String coresFiltrar(){
		 if (imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVerde.ico")) {
			 novoFiltro();
			 imagemFiltro = "../../resources/img/iconefiltrosVermelho.ico";
		 }else if(imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVermelho.ico")){
			 criarConsultaContaReceber();
			 gerarListaContas();
			 imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
		 }
		 return "";
	 } 
	 
	 public String recebimentoConta(Contasreceber contasreceber){
		 if (contasreceber!=null){
			 FacesContext fc = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			 session.setAttribute("contareceber", contasreceber);
			 RequestContext.getCurrentInstance().openDialog("recebimentoConta");
		 }
		 return "";
	 }
	 
	 public String novaCobranca(Contasreceber contasreceber) {
		 if (contasreceber!=null) {
			 Map<String, Object> options = new HashMap<String, Object>();
			 options.put("contentWidth", 600);
			 FacesContext fc = FacesContext.getCurrentInstance();
		     HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		     session.setAttribute("contasReceber", contasReceber);
		     session.setAttribute("vendas", contasReceber.getVendas());
			 RequestContext.getCurrentInstance().openDialog("cobranca");
		}
		 return "";
	 }
	 
	 public void novoHistorico(Contasreceber contasreceber) {
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 500);
		 RequestContext.getCurrentInstance().openDialog("historico");
	 }
	 
	 public void desfazerRecebimento(Contasreceber contasreceber){
		 contasreceber.setDataPagamento(null);
		 contasreceber.setDesagio(0f);
		 contasreceber.setJuros(0f);
		 ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		 contasReceberFacade.salvar(contasreceber);
	 }
	 
	 public Boolean habilitarDesabilitarDesfazer(Contasreceber contasreceber){
		 Boolean desabilitar = true;
		 if (contasreceber.getDataPagamento().equals(null)) {
			desabilitar = false;
			return desabilitar;
		}
		 return desabilitar;
	 }
	 
	 public String novoRecebimentoLote() {
		 float semJuros = 0.0f;
		 float semDesagio = 0.0f;
		 totalReceberLote = "0.00";
		 dataRecebimentoLote = new Date();
		 float valorTotal = 0.0f;
		 listaContasSelecionadas = new ArrayList<Contasreceber>();
		 for (int i = 0; i < listaContasReceber.size(); i++) {
			 if (listaContasReceber.get(i).isSelecionado()) {
				 if (!listaContasReceber.get(i).getJuros().equals(0)) {
					 semJuros = listaContasReceber.get(i).getJuros();
					 listaContasReceber.get(i).setValorParcela(listaContasReceber.get(i).getValorParcela() - semJuros);
				 }
				 if (!listaContasReceber.get(i).getDesagio().equals(0)) {
					 semDesagio = listaContasReceber.get(i).getDesagio();
					 listaContasReceber.get(i).setValorParcela(listaContasReceber.get(i).getValorParcela() - semDesagio);
				 }
				 listaContasSelecionadas.add(listaContasReceber.get(i));
				 valorTotal = valorTotal + listaContasReceber.get(i).getValorParcela();
			 }
	            
		 }
		 totalReceberLote = Formatacao.foramtarFloatString(valorTotal);
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 500);
		 FacesContext fc = FacesContext.getCurrentInstance();
		 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		 session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
		 session.setAttribute("totalReceberLote", totalReceberLote);
		 session.setAttribute("contasReceber", contasReceber);
		 session.setAttribute("cliente", cliente);
		 RequestContext.getCurrentInstance().openDialog("recebimentoLote");
		 return "";
	 }
	 
	 public void retornoDialogRecebimentoLote(SelectEvent event) {
		 gerarListaContas();
		 calculosContasMB.calcularTotalContasPagar();
	 }
	 
	 public void retornoDialogFiltrar(SelectEvent event) {
	        String sql = (String) event.getObject();
	        gerarListaContaas(sql);
	 }
	 
	 public void gerarListaContaas(String sql) {
		 ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		 try {
			 listaContasReceber = contasReceberFacade.listar(sql);
			 if (listaContasReceber == null) {
				 listaContasReceber = new ArrayList<Contasreceber>();
			 }	
		 } catch (SQLException ex) {
			 Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro a listar contas a receber", "Erro");
		 }
	 }
	 
		  
	 public String limparConsulta(){
		 try {
			 ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			 listaContasReceber = contasReceberFacade.listar(sql);
		 } catch (SQLException ex) {
			 Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro Listar Contas", "Erro");
		 }
		 return "";
		 
	 }
	 
}
