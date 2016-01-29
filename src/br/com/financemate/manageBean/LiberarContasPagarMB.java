package br.com.financemate.manageBean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
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

import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.MovimentoBancoFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Movimentobanco;

@Named
@ViewScoped
public class LiberarContasPagarMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Contaspagar> listaContasPagar;
    private Contaspagar contasPagar;
    private String totalLiberadas;
    private List<Contaspagar> listaContasSelecionadas;
    private Date dataLiberacao;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	
    
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContasSelecionadas =  (List<Contaspagar>) session.getAttribute("listaContasSelecionadas");
		totalLiberadas = (String) session.getAttribute("totalLiberadas");
		contasPagar = (Contaspagar) session.getAttribute("contasPagar");
		session.removeAttribute("totalLiberadas");
		session.removeAttribute("listaContasSelecionadas");
		session.removeAttribute("contasPagar");
        if (contasPagar == null) {
            contasPagar = new Contaspagar();
        }
	}

	public List<Contaspagar> getListaContasPagar() {
		return listaContasPagar;
	}

	public void setListaContasPagar(List<Contaspagar> listaContasPagar) {
		this.listaContasPagar = listaContasPagar;
	}

	public Contaspagar getContasPagar() {
		return contasPagar;
	}

	public void setContasPagar(Contaspagar contasPagar) {
		this.contasPagar = contasPagar;
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

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
	
	public Date dataLibercao(){
		dataLiberacao = new Date();
		contasPagar.setDataLiberacao(dataLiberacao);
		return dataLiberacao;
	}
	
	public String salvarContasLiberadas(Contaspagar conta) {
		String mensagem = validarDados();
		if (mensagem != null) {
			for (int i = 0; i < listaContasSelecionadas.size(); i++) {
	            salvarContaLiberadasMovimentoBanco(listaContasSelecionadas.get(i));
	        }
	        RequestContext.getCurrentInstance().closeDialog(contasPagar);
	        return "";
		}
		return "";
    }
    
    
    public void salvarContaLiberadasMovimentoBanco(Contaspagar conta) {
        conta.setDataLiberacao(dataLiberacao);
        conta.setContaPaga("S");
        Movimentobanco movimentoBanco = new Movimentobanco();
        movimentoBanco.setBanco(conta.getBanco());
        movimentoBanco.setCliente(conta.getCliente());
        movimentoBanco.setDataVencimento(conta.getDataVencimento());
        movimentoBanco.setDataRegistro(new Date());
        movimentoBanco.setPlanocontas(conta.getPlanocontas());
        movimentoBanco.setUsuario(usuarioLogadoMB.getUsuario());
        movimentoBanco.setValorEntrada(0.0f);
        movimentoBanco.setValorSaida(conta.getValor());
        movimentoBanco.setDataRegistro(new Date());
        movimentoBanco.setDataCompensacao(conta.getDataCompensacao());
        movimentoBanco.setTipoDocumento(conta.getTipoDocumento());
        movimentoBanco.setDescricao(conta.getDescricao());
        movimentoBanco.setCompentencia(conta.getCompetencia());
        MovimentoBancoFacade movimentoBancoFacade = new MovimentoBancoFacade();
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        conta = contasPagarFacade.salvar(conta);
        try {
            movimentoBanco.setIdcontaspagar(conta.getIdcontasPagar());
            movimentoBanco = movimentoBancoFacade.salvar(movimentoBanco);
        } catch (SQLException ex) {
            Logger.getLogger(LiberarContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao salvar libera��o", "Erro");
        }
        
    }
    
    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }

    
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
    public String validarDados(){
    	String mensagem = "";
    	if (contasPagar.getCompetencia().equalsIgnoreCase("")){
			mensagem = mensagem + "Compet�ncia n�o informada \r\n";
		}
    	return mensagem;
    }
}