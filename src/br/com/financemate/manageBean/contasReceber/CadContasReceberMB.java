package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.manageBean.ContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Vendas;

@Named
@ViewScoped
public class CadContasReceberMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 	@Inject
	    private UsuarioLogadoMB usuarioLogadoMB;
	    private List<Cliente> listaCliente;
	    private Cliente cliente;
	    private List<Planocontas> listaPlanoContas;
	    private Planocontas planoContas;
	    private List<Banco> listaBanco;
	    private Banco banco;
	    private Contasreceber contasReceber;
	    private Boolean repetir = false;
		private Boolean disabilitar = true;
		private String vezes;
		private String frequencia;
		private Cobranca cobranca;
		private Vendas vendas;
		private Boolean habilitarUnidade = false;
		private List<Contasreceber> listarParcelas;
		private List<Contasreceber> contasNumeroDocumentosIguais;
		
	    @PostConstruct
	    public void init(){
	    	FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        contasReceber = (Contasreceber) session.getAttribute("contareceber");
	        frequencia = (String) session.getAttribute("frequencia");
	        vezes = (String) session.getAttribute("vezes");
	        session.removeAttribute("frequencia");
	        session.removeAttribute("vezes");
	    	gerarListaCliente();
	    	gerarListaPlanoContas();
	    	if (contasReceber == null){
	            contasReceber = new Contasreceber();
	            cobranca = new Cobranca();
	            vendas = new Vendas();
	            if (usuarioLogadoMB.getCliente() != null) {
					cliente = usuarioLogadoMB.getCliente();
					gerarListaBanco(); 
				}else{
					cliente = new Cliente();
				}
	        }else{
	            cliente = contasReceber.getCliente();
	            gerarListaBanco(); 
	            planoContas = contasReceber.getPlanocontas();
	            banco = contasReceber.getBanco();
	        }
	    	desabilitarUnidade();
	    }

	    
	    
	    

		public List<Contasreceber> getContasNumeroDocumentosIguais() {
			return contasNumeroDocumentosIguais;
		}





		public void setContasNumeroDocumentosIguais(List<Contasreceber> contasNumeroDocumentosIguais) {
			this.contasNumeroDocumentosIguais = contasNumeroDocumentosIguais;
		}





		public List<Contasreceber> getListarParcelas() {
			return listarParcelas;
		}





		public void setListarParcelas(List<Contasreceber> listarParcelas) {
			this.listarParcelas = listarParcelas;
		}





		public Boolean getHabilitarUnidade() {
			return habilitarUnidade;
		}





		public void setHabilitarUnidade(Boolean habilitarUnidade) {
			this.habilitarUnidade = habilitarUnidade;
		}





		public Vendas getVendas() {
			return vendas;
		}





		public void setVendas(Vendas vendas) {
			this.vendas = vendas;
		}





		public Cobranca getCobranca() {
			return cobranca;
		}





		public void setCobranca(Cobranca cobranca) {
			this.cobranca = cobranca;
		}





		public String getVezes() {
			return vezes;
		}





		public void setVezes(String vezes) {
			this.vezes = vezes;
		}





		public String getFrequencia() {
			return frequencia;
		}





		public void setFrequencia(String frequencia) {
			this.frequencia = frequencia;
		}





		public Boolean getDisabilitar() {
			return disabilitar;
		}





		public void setDisabilitar(Boolean disabilitar) {
			this.disabilitar = disabilitar;
		}





		public Boolean getRepetir() {
			return repetir;
		}




		public void setRepetir(Boolean repetir) {
			this.repetir = repetir;
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


		public List<Planocontas> getListaPlanoContas() {
			return listaPlanoContas;
		}


		public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
			this.listaPlanoContas = listaPlanoContas;
		}


		public Planocontas getPlanoContas() {
			return planoContas;
		}


		public void setPlanoContas(Planocontas planoContas) {
			this.planoContas = planoContas;
		}


		public List<Banco> getListaBanco() {
			return listaBanco;
		}


		public void setListaBanco(List<Banco> listaBanco) {
			this.listaBanco = listaBanco;
		}


		public Banco getBanco() {
			return banco;
		}


		public void setBanco(Banco banco) {
			this.banco = banco;
		}


		public Contasreceber getContasReceber() {
			return contasReceber;
		}


		public void setContasReceber(Contasreceber contasReceber) {
			this.contasReceber = contasReceber;
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
		
		public void gerarListaPlanoContas() {
	        PlanoContasFacade planoContasFacade = new PlanoContasFacade();
	        try {
	            listaPlanoContas = planoContasFacade.listar();
	        } catch (Exception ex) {
	            Logger.getLogger(CadContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
	            mostrarMensagem(ex, "Erro ao lista Plano de contas", "Erro");
	        }
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
		
		public void mostrarMensagem(Exception ex, String erro, String titulo){
	        FacesContext context = FacesContext.getCurrentInstance();
	        erro = erro + " - " + ex;
	        context.addMessage(null, new FacesMessage(titulo, erro));
	    }
		
		public Boolean habilitarCampoRepetir(){
			if (repetir) {
				disabilitar = false;
				return disabilitar;
			}else{
				 disabilitar = true;
				return disabilitar;
			} 
			
		}
		
		public String salvar(){
			if (vezes != null) {
				ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
				try {
					contasNumeroDocumentosIguais = contasReceberFacade.listar("Select c from Contasreceber c where c.numeroDocumento='"+ contasReceber.getNumeroDocumento() + "'");
					if (contasNumeroDocumentosIguais == null || contasNumeroDocumentosIguais.isEmpty() == true) {
						contasNumeroDocumentosIguais = new ArrayList<Contasreceber>();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (contasNumeroDocumentosIguais == null || contasNumeroDocumentosIguais.isEmpty() == true) {
					int numeroVezes = Integer.parseInt(vezes);
					for (int i = 1; i <= numeroVezes; i++) {
				        contasReceber.setBanco(banco);
				        contasReceber.setPlanocontas(planoContas);
				        contasReceber.setCliente(cliente);
				        contasReceber.setValorPago(0.0f);
				        contasReceber.setDesagio(0.0f);
				        contasReceber.setJuros(0.0f);
				        contasReceber.setNumeroParcela(i);
				        contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
				        String mensagem = validarDados();
				        if (mensagem=="") {
				        	Contasreceber copia = new Contasreceber();
							copia = contasReceber;
				        	contasReceber = contasReceberFacade.salvar(contasReceber);
				        	if (frequencia != null) {
								if (frequencia.equalsIgnoreCase("mensal")) {
									Calendar c = new GregorianCalendar();
									c.setTime(copia.getDataVencimento());
									c.add(Calendar.MONTH, 1);
									Date data = c.getTime();
									copia.setDataVencimento(data);
								}else if (frequencia.equalsIgnoreCase("Diaria")){
									Calendar c = new GregorianCalendar();
									c.setTime(copia.getDataVencimento());
									c.add(Calendar.DAY_OF_MONTH, 1);
									Date data = c.getTime();
									copia.setDataVencimento(data);
								}else if(frequencia.equalsIgnoreCase("anual")){
									Calendar c = new GregorianCalendar();
									c.setTime(copia.getDataVencimento());
									c.add(Calendar.YEAR, 1);
									Date data = c.getTime();
									copia.setDataVencimento(data);
								}else if(frequencia.equalsIgnoreCase("Semanal")){
									Calendar c = new GregorianCalendar();
									c.setTime(copia.getDataVencimento());
									c.add(Calendar.DAY_OF_MONTH, 7);
									Date data = c.getTime();
									copia.setDataVencimento(data);
								}
							}
				        	if (i < numeroVezes) {
								contasReceber = new Contasreceber();
								contasReceber = copia;
							}
						}else{
							FacesContext context = FacesContext.getCurrentInstance();
				            context.addMessage(null, new FacesMessage(mensagem, ""));
						}
					}
					RequestContext.getCurrentInstance().closeDialog(contasReceber);
				}else{
					mensagem mensagem = new mensagem();
					mensagem.numeroDocumentosIguais();
				}
			}else{
				ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
				try {
					contasNumeroDocumentosIguais = contasReceberFacade.listar("Select c from Contasreceber c where c.numeroDocumento='"+ contasReceber.getNumeroDocumento() + "'");
					if (contasNumeroDocumentosIguais == null || contasNumeroDocumentosIguais.isEmpty() == true) {
						contasNumeroDocumentosIguais = new ArrayList<Contasreceber>();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (contasNumeroDocumentosIguais == null || contasNumeroDocumentosIguais.isEmpty() == true) {
					
				
					contasReceber.setBanco(banco);
					contasReceber.setPlanocontas(planoContas);
					contasReceber.setCliente(cliente);
					contasReceber.setValorPago(0.0f);
					contasReceber.setDesagio(0.0f);
					contasReceber.setJuros(0.0f);
					contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
					contasReceber.setNumeroParcela(1);
					String mensagem = validarDados(); 
					if (mensagem=="") {
						contasReceber = contasReceberFacade.salvar(contasReceber);
						RequestContext.getCurrentInstance().closeDialog(contasReceber);
					}else{
						FacesContext context = FacesContext.getCurrentInstance();
			            context.addMessage(null, new FacesMessage(mensagem, ""));
					}
				}else{
					mensagem mensagem = new mensagem();
					mensagem.numeroDocumentosIguais();
				}
			}
			return "";
		}
	    
	    public String cancelar(){
	    	FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.removeAttribute("contareceber");
	        RequestContext.getCurrentInstance().closeDialog(null);
	        return "";
	    }
	    
	    public String validarDados(){
	    	String mensagem = "";
	    	if (contasReceber.getCliente().equals("")) {
				mensagem = mensagem + "Unidade não selecionada \r\n";
			}
	    	if (contasReceber.getNomeCliente().equals("")) {
				mensagem = mensagem + "Nome do Cliente não informado \r\n";
			}
	    	if (contasReceber.getDataVencimento().equals(null)) {
				mensagem = mensagem + "Data de vencimento não informado \r\n";
			}
	    	if (contasReceber.getValorParcela().equals("")) {
				mensagem = mensagem + "Valor não informado \r\n";
			}
	    	if (contasReceber.getBanco().equals(null)) {
				mensagem = mensagem + "Banco não selecionado";
			}
	    	if (contasReceber.getNumeroDocumento().equalsIgnoreCase("")) {
				mensagem = mensagem + "Número de documento não informado \r\n";
			}
	    	return mensagem;
	    }
	    
	    public String abrirParcelamento() {
	    	if (contasReceber.getIdcontasReceber() != null) {
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 500);
				FacesContext fc = FacesContext.getCurrentInstance();
			    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			    session.setAttribute("listarParcelas", listarParcelas);
			    session.setAttribute("contasReceber", contasReceber);
			    session.setAttribute("frequencia", frequencia);
			    session.setAttribute("vezes", vezes);
				return "parcelas";
				
			}else{
				mensagem mensagem = new mensagem();
				mensagem.salvarVisualizarParcela();
				return "";
			}
	    }
	    
	    
	    public void desabilitarUnidade(){
			if (usuarioLogadoMB.getCliente() != null) {
				habilitarUnidade = true;
			}else{
				habilitarUnidade = false;
			}
			 
		}
	    
	    
	   
	    
	    
}
