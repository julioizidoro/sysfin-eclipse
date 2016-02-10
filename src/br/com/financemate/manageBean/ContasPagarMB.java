package br.com.financemate.manageBean;

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

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.CpTransferenciaFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Planocontas;
import br.com.financemate.util.Formatacao;


@Named
@ViewScoped
public class ContasPagarMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 private Date dataInicio;
	    private Date dataFinal;
	    private String sql;
	    private Cliente cliente;
	    private List<Contaspagar> listaContasPagar;
	    private List<Cliente> listaCliente;
	    private Boolean liberadas;
	    private Boolean autorizadas;
	    private String totalVencidas;
	    private String totalVencer;
	    private String totalVencendo;
	    private String total;
	    private Contaspagar contasPagar;
	    private String linha;
	    private String totalLiberadas;
	    private List<Contaspagar> listaContasSelecionadas;
	    private Date dataLiberacao;
	    @Inject
	    private UsuarioLogadoMB usuarioLogadoMB;
	    private Cptransferencia cpTransferencia;
	    private Planocontas planocontas;
	    private String descricao;
	    private List<Planocontas> listaPlanoContas;
		private String imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
		private List<Cptransferencia> listaTransferencia;
	
	@PostConstruct
	public void init(){
		gerarListaCliente();
		criarConsultaContasPagarInicial();
		gerarListaContas();
		gerarListaPlanoContas();
	}
	
	
	public List<Planocontas> getListaPlanoContas() {
		return listaPlanoContas;
	}

	
	public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
		this.listaPlanoContas = listaPlanoContas;
	}

	


	public List<Cptransferencia> getListaTransferencia() {
		return listaTransferencia;
	}


	public void setListaTransferencia(List<Cptransferencia> listaTransferencia) {
		this.listaTransferencia = listaTransferencia;
	}


	public String getImagemFiltro() {
		return imagemFiltro;
	}


	public void setImagemFiltro(String imagemFiltro) {
		this.imagemFiltro = imagemFiltro;
	}


	public Planocontas getPlanocontas() {
		return planocontas;
	}



	public void setPlanocontas(Planocontas planocontas) {
		this.planocontas = planocontas;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public Cptransferencia getCpTransferencia() {
		return cpTransferencia;
	}



	public void setCpTransferencia(Cptransferencia cpTransferencia) {
		this.cpTransferencia = cpTransferencia;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}




	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	
	
	public Date getDataInicio() {
		return dataInicio;
	}




	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
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




	public Cliente getCliente() {
		return cliente;
	}




	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




	public List<Contaspagar> getListaContasPagar() {
		return listaContasPagar;
	}




	public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
		this.listaContasPagar = listaContasPagar;
	}


	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}




	public Boolean getLiberadas() {
		return liberadas;
	}




	public void setLiberadas(Boolean liberadas) {
		this.liberadas = liberadas;
	}




	public Boolean getAutorizadas() {
		return autorizadas;
	}




	public void setAutorizadas(Boolean autorizadas) {
		this.autorizadas = autorizadas;
	}




	public String getTotalVencidas() {
		return totalVencidas;
	}




	public void setTotalVencidas(String totalVencidas) {
		this.totalVencidas = totalVencidas;
	}




	public String getTotalVencer() {
		return totalVencer;
	}




	public void setTotalVencer(String totalVencer) {
		this.totalVencer = totalVencer;
	}




	public String getTotalVencendo() {
		return totalVencendo;
	}




	public void setTotalVencendo(String totalVencendo) {
		this.totalVencendo = totalVencendo;
	}




	public String getTotal() {
		return total;
	}




	public void setTotal(String total) {
		this.total = total;
	}




	public Contaspagar getContasPagar() {
		return contasPagar;
	}




	public void setContasPagar(Contaspagar contasPagar) {
		this.contasPagar = contasPagar;
	}




	public String getLinha() {
		return linha;
	}




	public void setLinha(String linha) {
		this.linha = linha;
	}




	public String getTotalLiberadas() {
		return totalLiberadas;
	}




	public void setTotalLiberadas(String totalLiberadas) {
		this.totalLiberadas = totalLiberadas;
	}




	public List<Contaspagar> getListaContasSelecionadas() {
		return listaContasSelecionadas;
	}




	public void setListaContasSelecionadas(List<Contaspagar> listaContasSelecionadas) {
		this.listaContasSelecionadas = listaContasSelecionadas;
	}




	public Date getDataLiberacao() {
		return dataLiberacao;
	}




	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}



	public String verStatus(Contaspagar contaspagar) {
        Date data = new Date();
        String diaData = Formatacao.ConvercaoDataPadrao(data);
        data = Formatacao.ConvercaoStringDataBrasil(diaData);
        if (contaspagar.getDataVencimento().after(data)) {
            return "../../resources/img/bolaVerde.png";
        } else {
            if (!contaspagar.getDataVencimento().before(data)) {
                return "../../resources/img/bolaVermelha.png";
            } else {
                if (contaspagar.equals(data)) {
                    return "../../resources/img/bolaAmarela.png";
                }
            }
        }
        return "../../resources/img/bolaVerde.png";
    }
	
	public String imagemAutorizadas(Contaspagar contaspagar){
        if (contaspagar.getAutorizarPagamento() == null) {
            return "../../resources/img/cancel.png";
        } else if (contaspagar.getAutorizarPagamento().equalsIgnoreCase("s")) {
            return "../../resources/img/confirmar.png";
        } else {
            return "../../resources/img/cancel.png";
        }
    }
	
	public void criarConsultaContasPagarInicial(){
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
        String dataTermino = anocFinal + "-" + Formatacao.retornaDataFinal(mescFinal);
        setDataInicio(Formatacao.ConvercaoStringData(dataInicial));
        setDataFinal(Formatacao.ConvercaoStringData(dataTermino));
        sql = " Select v from Contaspagar v where v.dataVencimento>='" + dataInicial + 
                "' and v.dataVencimento<='" + dataTermino + "' and v.contaPaga='N' ";
         if (usuarioLogadoMB.getCliente()!=null){
            sql = sql + " and v.cliente.idcliente=" + usuarioLogadoMB.getCliente().getIdcliente();
        }else {
             sql = sql + "  and v.cliente.visualizacao='Operacional' ";
         }
        sql = sql + " order by v.dataVencimento";
        
    }
	
	public void gerarListaContas() {
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        try {
            listaContasPagar = contasPagarFacade.listar(sql);
            if (listaContasPagar == null) {
                listaContasPagar = new ArrayList<Contaspagar>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro a listar contas a pagar", "Erro");
        }
    }
	
	public void gerarListaCliente() {
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
            if (listaCliente == null) {
                listaCliente = new ArrayList<Cliente>();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao listar o cliente:", "Erro");
        }

    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public String novaConta() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadContasPagar");
        return "";
    }
	
	public void retornoDialogNovo(SelectEvent event) {
        Contaspagar contaspagar = (Contaspagar) event.getObject();
        gerarListaContas();
    }
	
	public String excluir(){
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		if (contasPagar.getFormaPagamento().equalsIgnoreCase("Transferencia")) {
			CpTransferenciaFacade cpTransferenciaFacade = new CpTransferenciaFacade();
			String sql = "";
			try {
				listaTransferencia = cpTransferenciaFacade.listarTranferencia(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < listaTransferencia.size(); i++) {
				cpTransferenciaFacade.excluir(cpTransferencia.getIdcptransferencia());
			}
			
		}
        contasPagarFacade.excluir(contasPagar.getIdcontasPagar());
        gerarListaContas();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
        return "";
     }
	
	public void autorizarPagamento(Contaspagar contaspagar){
        contaspagar.setAutorizarPagamento("S");
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        contaspagar = contasPagarFacade.salvar(contaspagar);
    }
	
	public void novoFiltro() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("filtrarConsContaPagar");
    }
	
	public String editar(Contaspagar contaspagar){
        if (contaspagar!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("contapagar", contaspagar);
            session.setAttribute("cptransferencia", cpTransferencia);
            RequestContext.getCurrentInstance().openDialog("cadContasPagar");
        }
        return "";
    } 
	
	public String novaImpressao() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("imprimir"); 
        return "";
    }
	
	 public String novaLiberacao() {
		 totalLiberadas = "0.00";
		 dataLiberacao = new Date();
		 float valorTotal = 0.0f;
		 listaContasSelecionadas = new ArrayList<Contaspagar>();
		 for (int i = 0; i < listaContasPagar.size(); i++) {
			 if (listaContasPagar.get(i).isSelecionado()) {
				 listaContasSelecionadas.add(listaContasPagar.get(i));
				 valorTotal = valorTotal + listaContasPagar.get(i).getValor();
			 }
	            
		 }
		 totalLiberadas = Formatacao.foramtarFloatString(valorTotal);
		 Map<String, Object> options = new HashMap<String, Object>();
		 options.put("contentWidth", 600);
		 FacesContext fc = FacesContext.getCurrentInstance();
	     HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	     session.setAttribute("listaContasSelecionadas", listaContasSelecionadas);
	     session.setAttribute("totalLiberadas", totalLiberadas);
	     session.setAttribute("contasPagar", contasPagar);
		 RequestContext.getCurrentInstance().openDialog("liberarContasPagar");
		 return "";
	 }
	 
	 public void filtrar(){		 
		 sql = "Select v from Contaspagar v where ";
		 if (liberadas){
			 sql = sql + " v.contaPaga='S' and ";
		 }else sql = sql + " v.contaPaga='N' and ";
		 if (autorizadas){
			 sql = sql + " v.autorizarPagamento='S' and ";
		 }
		 if (cliente!=null){
			 sql = sql + " v.cliente.idcliente=" + cliente.getIdcliente() + " and ";
		 }else {
			 sql = sql + " v.cliente.visualizacao='Operacional' and ";
		 }
		 if (!descricao.equalsIgnoreCase("")) {
			sql =  sql + " v.descricao= '" + descricao + "' and ";
		}
		if (planocontas!=null) {
			sql = sql + " v.planocontas.idplanocontas=" + planocontas.getIdplanoContas() + " and ";
		}
		 if ((dataInicio!=null) && (dataFinal!=null)){
			 if (liberadas){
				 sql = sql + "v.dataLiberacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + 
						 "' and v.dataLiberacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + 
						 "' order by v.dataLiberacao";
			 }else {
				 sql = sql + "v.dataVencimento>='" + Formatacao.ConvercaoDataSql(dataInicio) + 
						 "' and v.dataVencimento<='" + Formatacao.ConvercaoDataSql(dataFinal) + 
						 "' order by v.dataVencimento";
			 } 
		 }
		 gerarListaContas();
		 RequestContext.getCurrentInstance().closeDialog(sql);
	 }
	 
	 public void gerarListaPlanoContas() {
		 PlanoContasFacade planoContasFacade = new PlanoContasFacade();
		 try {
			 listaPlanoContas = planoContasFacade.listar();
			 if (listaPlanoContas == null) {
				 listaPlanoContas = new ArrayList<Planocontas>();
			 }
		 } catch (Exception ex) {
			 Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
		 }
	        
	 }
	 
	 public void retornoDialogFiltrar(SelectEvent event) {
	        String sql = (String) event.getObject();
	        gerarListaContaas(sql);
	 }
	 
	 public void gerarListaContaas(String sql) {
	        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
	        try {
	            listaContasPagar = contasPagarFacade.listar(sql);
	            if (listaContasPagar == null) {
	                listaContasPagar = new ArrayList<Contaspagar>();
	            }
	        } catch (SQLException ex) {
	            Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
	            mostrarMensagem(ex, "Erro a listar contas a pagar", "Erro");
	        }
	    }
	 
	 public String coresFiltrar(){
		if (imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVerde.ico")) {
			novoFiltro();
			imagemFiltro = "../../resources/img/iconefiltrosVermelho.ico";
		}else if(imagemFiltro.equalsIgnoreCase("../../resources/img/iconefiltrosVermelho.ico")){
			limparConsulta();
			criarConsultaContasPagarInicial();
			gerarListaContas();
			imagemFiltro = "../../resources/img/iconefiltrosVerde.ico";
		}
		return "";
	 } 
	  
	 public String limparConsulta(){
		 try {
			 ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			 listaContasPagar = contasPagarFacade.listar(sql);
			 setLiberadas(false);
			 setAutorizadas(false);
		 } catch (SQLException ex) {
			 Logger.getLogger(ContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
			 mostrarMensagem(ex, "Erro Listar Contas", "Erro");
		 }
		 return "";
		 
	 }
	 
	 public void retornoDialogLiberar(SelectEvent event) {
		 gerarListaContas();
	 }
	 
	 
}
