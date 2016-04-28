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
import br.com.financemate.facade.OperacaoUsuarioFacade;
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Outroslancamentos;

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
    @Inject
    private CalculosContasMB calculosContasMB;
	
    
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaContasSelecionadas =  (List<Contaspagar>) session.getAttribute("listaContasSelecionadas");
		totalLiberadas = (String) session.getAttribute("totalLiberadas");
		contasPagar = (Contaspagar) session.getAttribute("contasPagar");
		session.removeAttribute("contasPagar");
        if (contasPagar == null) {
            contasPagar = new Contaspagar();
        }
	}
	
	

	public CalculosContasMB getCalculosContasMB() {
		return calculosContasMB;
	}



	public void setCalculosContasMB(CalculosContasMB calculosContasMB) {
		this.calculosContasMB = calculosContasMB;
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
		mensagem msg = new mensagem();
			for (int i = 0; i < listaContasSelecionadas.size(); i++) {
				String mensagem = validarDados(listaContasSelecionadas.get(i));
				if (mensagem == "") {
					if (listaContasSelecionadas.get(i).getAutorizarPagamento().equals("S")) {
				
						salvarContaLiberadasMovimentoBanco(listaContasSelecionadas.get(i));
						if (listaContasSelecionadas.get(i).getIdcontasPagar() != null) {
							OperacaoUsuarioFacade operacaoUsuarioFacade = new OperacaoUsuarioFacade();
							Operacaousuairo operacaousuairo = new Operacaousuairo();
							operacaousuairo.setContaspagar(listaContasSelecionadas.get(i));
							operacaousuairo.setData(new Date());
							operacaousuairo.setTipooperacao("Usuário Liberou");
							operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
							try {
								operacaoUsuarioFacade.salvar(operacaousuairo);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						msg.liberar();
						FacesContext fc = FacesContext.getCurrentInstance();
						HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
						session.removeAttribute("totalLiberadas");
					}else{
						msg.naoLiberar();
						RequestContext.getCurrentInstance().closeDialog(contasPagar); 
						return "";
					}
				}else{
					msg.competencia();
					return ""; 
				}
				calculosContasMB.calcularTotalContasPagar();
			}
			RequestContext.getCurrentInstance().closeDialog(contasPagar);
			return "";
	}
    
    
    public void salvarContaLiberadasMovimentoBanco(Contaspagar conta) {
        conta.setDataLiberacao(dataLiberacao);
        conta.setContaPaga("S");
        Outroslancamentos outroslancamentos = new Outroslancamentos();
        outroslancamentos.setBanco(conta.getBanco());
        outroslancamentos.setCliente(conta.getCliente());
        outroslancamentos.setDataVencimento(conta.getDataVencimento());
        outroslancamentos.setDataRegistro(new Date());
        outroslancamentos.setPlanocontas(conta.getPlanocontas());
        outroslancamentos.setUsuario(usuarioLogadoMB.getUsuario());
        outroslancamentos.setValorEntrada(0.0f);
        outroslancamentos.setValorSaida(conta.getValor());
        outroslancamentos.setDataRegistro(new Date());
        if (conta.getDataCompensacao() == null) {
			outroslancamentos.setDataCompensacao(new Date());
		}else{
			outroslancamentos.setDataCompensacao(conta.getDataCompensacao());
		}
        outroslancamentos.setTipoDocumento(conta.getTipoDocumento());
        outroslancamentos.setDescricao(conta.getDescricao());
        outroslancamentos.setCompentencia(conta.getCompetencia());
        OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        conta = contasPagarFacade.salvar(conta);
        try {
        	outroslancamentos.setIdcontaspagar(conta.getIdcontasPagar());
        	outroslancamentos = outrosLancamentosFacade.salvar(outroslancamentos);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(LiberarContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro ao salvar liberaï¿½ï¿½o", "Erro");
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
	
    public String validarDados(Contaspagar contaspagar){
    	String mensagem = "";
    	if (contaspagar.getCompetencia().equalsIgnoreCase("")){
			mensagem = mensagem + "Competência nãoo informada \r\n";
		}
    	if (contaspagar.getPlanocontas() == null) {
    		mensagem = mensagem + "Plano de contas não informado \r\n";
		}
    	if (contaspagar.getDataCompensacao() == null) {
    		mensagem = mensagem + "Data de Compensação não informado \r\n";
		}
    	if (contaspagar.getDataAgendamento() == null) {
    		mensagem = mensagem + "Data de Agendamento não informado \r\n";
		}
    	return mensagem;
    }
    
    
    public String editarBanco(Contaspagar conta){
	    	FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("conta", conta);
	    	return "editarBanco"; 
    }
}
