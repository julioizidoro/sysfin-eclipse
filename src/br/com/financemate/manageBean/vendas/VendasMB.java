package br.com.financemate.manageBean.vendas;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.manageBean.ClienteMB;
import br.com.financemate.manageBean.ContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;

@Named
@ViewScoped
public class VendasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Inject
	 private UsuarioLogadoMB usuarioLogadoMB;
	 @Inject 
	 private ClienteMB clienteMB;
	 private Vendas venda;
	 private List<Vendas> listaVendas;
	 private String sql;
	 private String order;
	 private String nomeClientePesquisa;
	 private Date dataInicial;
	 private Date dataFinal;
	 private String numeroVenda;
	 private String situacao;
	 private String imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
	 private Cliente cliente;
	 private List<Cliente> listaCliente;
	 private Boolean habilitarUnidade;
	 private String nomeCliente;
	 private Integer nVenda;

	
	@PostConstruct
	public void init(){
		 if (listaVendas==null){
			 gerarDataInicial();
			 gerarListaVendas();
		 }
		 gerarListaCliente();
		 desabilitarUnidade();
	}

	



	public Integer getnVenda() {
		return nVenda;
	}





	public void setnVenda(Integer nVenda) {
		this.nVenda = nVenda;
	}





	public String getNomeCliente() {
		return nomeCliente;
	}





	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}





	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public List<Cliente> getListaCliente() {
		return listaCliente;
	}



	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}


	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}


	public ClienteMB getClienteMB() {
		return clienteMB;
	}


	public void setClienteMB(ClienteMB clienteMB) {
		this.clienteMB = clienteMB;
	}


	public Vendas getVenda() {
		return venda;
	}


	public void setVenda(Vendas venda) {
		this.venda = venda;
	}


	public List<Vendas> getListaVendas() {
		return listaVendas;
	}


	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}


	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public String getOrder() {
		return order;
	}


	public void setOrder(String order) {
		this.order = order;
	}


	public String getNomeClientePesquisa() {
		return nomeClientePesquisa;
	}


	public void setNomeClientePesquisa(String nomeClientePesquisa) {
		this.nomeClientePesquisa = nomeClientePesquisa;
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


	public String getNumeroVenda() {
		return numeroVenda;
	}


	public void setNumeroVenda(String numeroVenda) {
		this.numeroVenda = numeroVenda;
	}


	public String getSituacao() {
		return situacao;
	}


	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	
	
	public String getImagemFiltro() {
		return imagemFiltro;
	}


	public void setImagemFiltro(String imagemFiltro) {
		this.imagemFiltro = imagemFiltro;
	}


	public String novo(){
        if (usuarioLogadoMB.getUsuario().getTipoacesso().getAcesso().getIvendas()){
        	Map<String, Object> options = new HashMap<String, Object>();
            options.put("contentWidth", 600);
            RequestContext.getCurrentInstance().openDialog("cadVendas");
            return "";
        }else {
            FacesMessage mensagem = new FacesMessage("Erro! ", "Acesso Negado");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
    
    public void gerarListaVendas(){
        sql = sql + order;
        VendasFacade vendasFacade = new VendasFacade();
        try {
            listaVendas = vendasFacade.listar(sql);
            if (listaVendas == null) {
                listaVendas = new ArrayList<Vendas>();
        }
        } catch (SQLException ex) {
            Logger.getLogger(VendasMB.class.getName()).log(Level.SEVERE, null, ex);
             FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro!" + ex));
        }
    }
    
    public void gerarDataInicial(){
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
         sql =null;
        if (usuarioLogadoMB.getUsuario().getCliente()>0){
            sql = " Select v from Vendas v where v.dataVenda>='" + dataInicial + 
                "' and v.dataVenda<='" + dataFinal + "' and v.cliente.situacao<>'verde' and v.cliente.idcliente=" + 
                usuarioLogadoMB.getUsuario().getCliente();
            order = " order by v.dataVenda";
        }else {
            sql = " Select v from Vendas v where v.cliente.visualizacao='Operacional' and "
                    + "v.dataVenda>='" + dataInicial + 
                "' and v.dataVenda<='" + dataFinal + "' and v.situacao<>'verde'";
            order = " order by v.dataVenda";
        }
    }
    
     public String selecionarUnidade() {
        clienteMB.setPagina("relVendas");
        return "selecionarUnidade";
    }
     
     
     public String selecionarUnidadePesquisa() {
        clienteMB.setPagina("pesquisarVendas");
        return "selecionarUnidade";
    }
    
     public String pesquisar(){
         return "pesquisarVendas";
     }
     
     public String confirmaPesquisa(){
         gerarSqlPesquisa();
         gerarListaVendas();
         return "consVendas";
     }
     
     public String cancelarPesquisa(){
         return "consVendas";
    }
     
    private void gerarSqlPesquisa(){
        boolean linha = false;
        sql = " Select v from Vendas v where ";
        if ((dataInicial != null) && (dataFinal != null)) {
            if (!linha) {
                sql = sql + "  v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicial)
                        + "' and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
                linha = true;
            }
        }
        if (!situacao.equalsIgnoreCase("Todas")){
            if (!linha){
                sql = sql + " v.situacao='" + situacao + "'";
                linha = true;
            }else{
                sql = sql + " and v.situacao='" + situacao + "'";
            }
        }
        if (clienteMB.getCliente().getIdcliente()!=null){
            if (!linha){
                sql = sql + " v.cliente.idcliente=" + clienteMB.getCliente().getIdcliente();
                linha = true;
            }else {
                sql = sql + " and v.cliente.idcliente=" + clienteMB.getCliente().getIdcliente();
            }
        }else {
            if (!linha){
                sql = sql + " v.cliente.visualizacao='Operacional'";
                linha=true;
            }else {
                sql = sql + " and v.cliente.visualizacao='Operacional'";
            }
        }
        if (numeroVenda.length()>0){
            int numero = Integer.parseInt(numeroVenda);
            if (!linha){
                sql = sql + " v.idvendas=" + numero;
                linha = true;
            }else {
                sql = sql + " and v.idvendas=" + numero;
            }
        }
        if (nomeClientePesquisa.length()>0){
            if (!linha){
                sql = sql + " v.nomeCliente like '%" + nomeClientePesquisa + "%'";
            }else{
                sql = sql + " and v.nomeCliente like '%" + nomeClientePesquisa + "%'";
            }
        }
        order = " order by v.dataVenda";
    }
    
    public String verStatus(Vendas vendas) {
        if (vendas.getSituacao().equalsIgnoreCase("vermelho")) {
            return "../../resources/img/bolaVermelha.png";
        } else if (vendas.getSituacao().equalsIgnoreCase("amarelo")) {
            return "../../resources/img/bolaAmarela.png";
        } else {
            return "../../resources/img/bolaVerde.png";
        } 
    }
    
    public String coresFiltrar(){
		 if (imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVerde.ico")) {
			 filtro();
			 imagemFiltro = "../../resources/img/iconefiltrosVermelho.ico";
		 }else if(imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVermelho.ico")){
			 gerarDataInicial();
			 gerarListaVendas();
			 imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
		 }
		 return "";
	 }
    
    public String novaVenda() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadVendas");
        return "";
    }
    
    public String filtro() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("filtrarVenda");
        return "";
    }
    
    public String gerarParcela() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("gerarParcelas");
        return "";
    }
    
    public void gerarListaCliente() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(VendasMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
    
    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
    
    public void filtrar(){		 
		 sql = "Select v from Vendas v where ";
		 if (cliente!=null){
			 sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
		 }else {
			 sql = sql + " v.cliente.visualizacao='Operacional' and ";
		 }
		 if (nomeCliente!="") {
			 sql = sql + " v.nomeCliente like '%" + nomeCliente + "%' and ";
		 }
		 if (nVenda != null) {
			sql = sql + " v.idvendas="  + nVenda + " and "; 
		 }
		 if (situacao != null) {
			 if (situacao.equalsIgnoreCase("amarelo") || situacao.equalsIgnoreCase("verde") || situacao.equalsIgnoreCase("vermelho")) {
				 sql = sql + " v.situacao='" + situacao + "' and "; 
			}
		}
		
		 if ((dataInicial!=null) && (dataFinal!=null)){
				 sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicial) + 
						 "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + 
						 "' order by v.dataVencimento";
		 } 
		 RequestContext.getCurrentInstance().closeDialog(sql);
	 }
    
    public void retornoDialogFiltrar(SelectEvent event) {
        String sql = (String) event.getObject();
        gerarListaVendaas(sql);
 }
 
    public void gerarListaVendaas(String sql){
        sql = sql + order;
        VendasFacade vendasFacade = new VendasFacade();
        try {
            listaVendas = vendasFacade.listar(sql);
            if (listaVendas == null) {
                listaVendas = new ArrayList<Vendas>();
        }
        } catch (SQLException ex) {
            Logger.getLogger(VendasMB.class.getName()).log(Level.SEVERE, null, ex);
             FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Erro!" + ex));
        }
    }

}
