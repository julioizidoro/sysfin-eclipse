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

import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.facade.VendasFacade;
import br.com.financemate.manageBean.ContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Cobranca;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Planocontas;
import br.com.financemate.model.Vendas;
import br.com.financemate.util.Formatacao;

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
		
	    @PostConstruct
	    public void init(){
	    	FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        contasReceber = (Contasreceber) session.getAttribute("contareceber");
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
				int numeroVezes = Integer.parseInt(vezes);
				for (int i = 1; i <= numeroVezes; i++) {
					ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			        contasReceber.setBanco(banco);
			        contasReceber.setPlanocontas(planoContas);
			        contasReceber.setCliente(cliente);
			        contasReceber.setValorPago(0.0f);
			        contasReceber.setDesagio(0.0f);
			        contasReceber.setJuros(0.0f);
			        contasReceber.setNumeroParcela(i/numeroVezes);
			        contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
			        if (contasReceber.getVendas() == null) {
			        	VendasFacade vendasFacade = new VendasFacade();
			        	try {
							vendas = vendasFacade.consultar(1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						contasReceber.setVendas(vendas);
					}
			        String mensagem = validarDados();
			        if (mensagem=="") { 
			        	contasReceber = contasReceberFacade.salvar(contasReceber);
					}else{
						FacesContext context = FacesContext.getCurrentInstance();
			            context.addMessage(null, new FacesMessage(mensagem, ""));
					}
				}
			}else{
				ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
				contasReceber.setBanco(banco);
				contasReceber.setPlanocontas(planoContas);
				contasReceber.setCliente(cliente);
				contasReceber.setValorPago(0.0f);
				contasReceber.setDesagio(0.0f);
				contasReceber.setJuros(0.0f);
				contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
				contasReceber.setNumeroParcela(1/1);
				if (contasReceber.getCobranca()== null) {
					contasReceber.setCobranca(cobranca);
				}
				if (contasReceber.getVendas() == null) {
		        	VendasFacade vendasFacade = new VendasFacade();
		        	try {
						vendas = vendasFacade.consultar(1);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					contasReceber.setVendas(vendas);
				}
				String mensagem = validarDados();
				if (mensagem=="") {
					contasReceber = contasReceberFacade.salvar(contasReceber);
					
				}else{
					FacesContext context = FacesContext.getCurrentInstance();
		            context.addMessage(null, new FacesMessage(mensagem, ""));
				}
			}
			RequestContext.getCurrentInstance().closeDialog(contasReceber);
			return "";
		}
	    
	    public String cancelar(){
	        RequestContext.getCurrentInstance().closeDialog(null);
	        return "";
	    }
	    
	    public String validarDados(){
	    	String mensagem = "";
	    	if (contasReceber.getCliente().equals("")) {
				mensagem = mensagem + "Unidade n�o selecionada \r\n";
			}
	    	if (contasReceber.getNomeCliente().equals("")) {
				mensagem = mensagem + "Nome do Cliente n�o informado \r\n";
			}
	    	if (contasReceber.getDataVencimento().equals(null)) {
				mensagem = mensagem + "Data de vencimento n�o informado \r\n";
			}
	    	if (contasReceber.getValorParcela().equals("")) {
				mensagem = mensagem + "Valor n�o informado \r\n";
			}
	    	if (contasReceber.getBanco().equals(null)) {
				mensagem = mensagem + "Banco n�o selecionado";
			}
	    	return mensagem;
	    }
	    
	    public String abrirParcelamento() {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			FacesContext fc = FacesContext.getCurrentInstance();
		    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		    session.setAttribute("contasReceber", contasReceber);
			return "parcelas";
	    }
	    
	    public void onCellEdit(CellEditEvent event) {
	        Object oldValue = event.getOldValue();
	        Object newValue = event.getNewValue();
	         
	        if(newValue != null && !newValue.equals(oldValue)) {
	            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
	            FacesContext.getCurrentInstance().addMessage(null, msg);
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
