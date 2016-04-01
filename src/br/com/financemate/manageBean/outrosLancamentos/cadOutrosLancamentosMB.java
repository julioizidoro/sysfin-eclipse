package br.com.financemate.manageBean.outrosLancamentos;

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
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.manageBean.CadContasPagarMB;
import br.com.financemate.manageBean.LiberarContasPagarMB;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Outroslancamentos;
import br.com.financemate.model.Planocontas;

@Named
@ViewScoped
public class cadOutrosLancamentosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Outroslancamentos outrosLancamentos;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private Banco banco;
	private List<Banco> listaBanco;
	private Boolean habilitarUnidade = false;
	private Planocontas planoContas;
	private List<Planocontas> listaPlanoContas;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        outrosLancamentos = (Outroslancamentos) session.getAttribute("outroslancamentos");
        session.removeAttribute("outroslancamentos");
        if (outrosLancamentos == null) {
			outrosLancamentos = new Outroslancamentos();
		}else{
			cliente = outrosLancamentos.getCliente();
            planoContas = outrosLancamentos.getPlanocontas();
            banco = outrosLancamentos.getBanco();
            gerarListaBanco();
		}
		gerarListaCliente();
		gerarListaPlanoContas();
		desabilitarUnidade();
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



	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}



	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Outroslancamentos getOutrosLancamentos() {
		return outrosLancamentos;
	}

	public void setOutrosLancamentos(Outroslancamentos outrosLancamentos) {
		this.outrosLancamentos = outrosLancamentos;
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
	
	public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaCliente = clienteFacade.listar("");
        } catch (SQLException ex) {
            Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	public void salvar() {
        outrosLancamentos.setBanco(banco);
        outrosLancamentos.setCliente(cliente);
        outrosLancamentos.setPlanocontas(planoContas);
        outrosLancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
        try {
        	String mensagem = validarDados();
        	if (mensagem=="") {
        		outrosLancamentos = outrosLancamentosFacade.salvar(outrosLancamentos);
        		mensagem msg = new mensagem();
                msg.saveMessagem();
        		RequestContext.getCurrentInstance().closeDialog(outrosLancamentos);
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null, new FacesMessage(mensagem, ""));
			}
        } catch (SQLException ex) {
            Logger.getLogger(LiberarContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao salvar ", "Erro");
        }
        
       
    }
	
	public void salvarRepetir() {
        outrosLancamentos.setBanco(banco);
        outrosLancamentos.setCliente(cliente);
        outrosLancamentos.setPlanocontas(planoContas);
        outrosLancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
        try {
        	String mensagem = validarDados();
        	if (mensagem=="") {
        		outrosLancamentos = outrosLancamentosFacade.salvar(outrosLancamentos);
        		Outroslancamentos copia = new Outroslancamentos();
        		copia = outrosLancamentos;
        		outrosLancamentos = new Outroslancamentos();
        		outrosLancamentos.setBanco(copia.getBanco());
        		outrosLancamentos.setCliente(copia.getCliente());
        		outrosLancamentos.setCompentencia(copia.getCompentencia());
        		outrosLancamentos.setDataCompensacao(copia.getDataCompensacao());
        		outrosLancamentos.setDataRegistro(copia.getDataCompensacao());
        		outrosLancamentos.setDataVencimento(copia.getDataVencimento());
        		outrosLancamentos.setDescricao(copia.getDescricao());
        		outrosLancamentos.setPlanocontas(copia.getPlanocontas());
        		outrosLancamentos.setSaldo(copia.getSaldo());
        		outrosLancamentos.setTipoDocumento(copia.getTipoDocumento());
        		outrosLancamentos.setUsuario(copia.getUsuario());
        		outrosLancamentos.setValorEntrada(copia.getValorEntrada());
        		outrosLancamentos.setValorSaida(copia.getValorSaida());
        		mensagem msg = new mensagem();
        		msg.saveMessagem();
			}else{
				FacesContext context = FacesContext.getCurrentInstance();
	            context.addMessage(null, new FacesMessage(mensagem, ""));
			}
        } catch (SQLException ex) {
            Logger.getLogger(LiberarContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao salvar ", "Erro");
        }
        
    }
	
	public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
	
	public String validarDados(){
		String mensagem = "";
		if (outrosLancamentos.getCliente() == null) {
			mensagem = mensagem + "Unidade não informada \r\n";
		}
		if (outrosLancamentos.getDataRegistro().equals(null)) {
			mensagem = mensagem + " Data de Registro não informada \n";
		}
		if (outrosLancamentos.getTipoDocumento().equalsIgnoreCase(null)) {
			mensagem = mensagem + "Tipo de documento não informado \r\n";
		}
		if (outrosLancamentos.getBanco().equals(null)) {
			mensagem = mensagem + "Conta não selecionada \r\n";
		}
		if (outrosLancamentos.getDataVencimento().equals(null)) {
			mensagem = mensagem + "Data de Vencimento não informada \r\n";
		}
		if (outrosLancamentos.getValorEntrada() == null) {
			mensagem = mensagem + "Valor de entrada não informada \r\n";
		}
		if (outrosLancamentos.getValorSaida() == null) {
			mensagem = mensagem + "Valor de saida não informada \r\n";
		}
		if (outrosLancamentos.getDescricao().equalsIgnoreCase(null)) {
			mensagem = mensagem + "Descrição não informada \r\n";
		}
		if (outrosLancamentos.getPlanocontas() == null) {
			mensagem = mensagem + "Plano de contas não informada \r\n";
		}
		if (outrosLancamentos.getDataCompensacao() == null) {
			mensagem = mensagem + "Data de compensação não informada \r\n";
		}
		
		return mensagem;
	}
	
	public void gerarListaPlanoContas() {
		PlanoContasFacade planoContasFacade = new PlanoContasFacade();
		try {
			listaPlanoContas = planoContasFacade.listar();
			if (listaPlanoContas == null) {
				listaPlanoContas = new ArrayList<Planocontas>();
			}
		} catch (Exception ex) {
			Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro ao gerar a lista de plano de contas", "Erro");
		}
        
	}
	
	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	
}
