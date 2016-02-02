package br.com.financemate.manageBean.contasReceber;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import br.com.financemate.model.Planocontas;

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
	    
	    @PostConstruct
	    public void init(){
	    	FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        contasReceber = (Contasreceber) session.getAttribute("contareceber");
	        session.removeAttribute("contareceber");
	    	gerarListaCliente();
	    	gerarListaPlanoContas();
	    	if (contasReceber == null){
	            contasReceber = new Contasreceber();
	        }else{
	            cliente = contasReceber.getCliente();
	            planoContas = contasReceber.getPlanocontas();
	            banco = contasReceber.getBanco();
	        }
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
	        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
	        contasReceber.setBanco(banco);
	        contasReceber.setPlanocontas(planoContas);
	        contasReceber.setCliente(cliente);
	        contasReceber.setValorPago(0.0f);
	        contasReceber.setDesagio(0.0f);
	        contasReceber.setJuros(0.0f);
	        contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
	        String mensagem = validarDados();
	        if (mensagem!=null) {
	        	contasReceber = contasReceberFacade.salvar(contasReceber);
			}
	        RequestContext.getCurrentInstance().closeDialog(contasReceber);
	        return "consContasReceber";
	    }
	    
	    public String cancelar(){
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
	    	return mensagem;
	    }

}
