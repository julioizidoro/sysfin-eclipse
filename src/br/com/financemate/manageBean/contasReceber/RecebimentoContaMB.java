package br.com.financemate.manageBean.contasReceber;


import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasReceberFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contasreceber;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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


@Named
@ViewScoped
public class RecebimentoContaMB implements  Serializable{
    
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Cliente> listaCliente;
    private Cliente cliente;
    private List<Banco> listaBanco;
    private Banco banco;
    private Contasreceber contasReceber;
    private Boolean checkRecebimento = false;
    private Boolean disabilitar = true;
    private float valorParcial = 0f;
    private Date dataRecebimentoParcial;
    private List<Contasreceber> listaRecebimentoParial;
    private Float valorPagoParcial;
    
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        contasReceber = (Contasreceber) session.getAttribute("contareceber");
        valorPagoParcial = (Float) session.getAttribute("valorPagoParcial");
        cliente = contasReceber.getCliente();
        gerarListaCliente();
        gerarListaBanco();
        contasReceber.setValorPago(contasReceber.getValorParcela());
        cliente = contasReceber.getCliente();
        banco = contasReceber.getBanco();
		if (valorPagoParcial > 0f) {
			listaRecebimentoParial = new ArrayList<Contasreceber>();
			listaRecebimentoParial = contasReceber.getRecebimentoParcialList();
		}else{
			listaRecebimentoParial = new ArrayList<Contasreceber>();
		}
		
    }
    
    
    

    public Float getValorPagoParcial() {
		return valorPagoParcial;
	}




	public void setValorPagoParcial(Float valorPagoParcial) {
		this.valorPagoParcial = valorPagoParcial;
	}




	public List<Contasreceber> getListaRecebimentoParial() {
		return listaRecebimentoParial;
	}




	public void setListaRecebimentoParial(List<Contasreceber> listaRecebimentoParial) {
		this.listaRecebimentoParial = listaRecebimentoParial;
	}




	public Date getDataRecebimentoParcial() {
		return dataRecebimentoParcial;
	}




	public void setDataRecebimentoParcial(Date dataRecebimentoParcial) {
		this.dataRecebimentoParcial = dataRecebimentoParcial;
	}




	public float getValorParcial() {
		return valorParcial;
	}




	public void setValorParcial(float valorParcial) {
		this.valorParcial = valorParcial;
	}




	public Boolean getDisabilitar() {
		return disabilitar;
	}




	public void setDisabilitar(Boolean disabilitar) {
		this.disabilitar = disabilitar;
	}




	public Boolean getCheckRecebimento() {
		return checkRecebimento;
	}



	public void setCheckRecebimento(Boolean checkRecebimento) {
		this.checkRecebimento = checkRecebimento;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
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

   
      
    public void gerarListaBanco(){
        if (cliente!=null){
            BancoFacade bancoFacade = new BancoFacade();
            String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
            listaBanco = bancoFacade.listar(sql);
            if (listaBanco==null){
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
            Logger.getLogger(ContasReceberMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
     
    public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public String salvar(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        contasReceber.setBanco(banco);
        contasReceber.setCliente(cliente);
        contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasReceber = contasReceberFacade.salvar(contasReceber);
        RequestContext.getCurrentInstance().closeDialog(contasReceber);
        return "consContasReceber";
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        contasReceber.setValorPago(0.0f);
        return "";
    }
    
    public void calcularValoresRecebimento(){
        Float valorReceber = contasReceber.getValorParcela() - contasReceber.getDesagio() + contasReceber.getJuros();
        contasReceber.setValorPago(valorReceber);
    }
    
    

    public Boolean habilitarCampoCheckRecebimento(){
		if (checkRecebimento) {
			disabilitar = false;
			return disabilitar;
		}else{
			 disabilitar = true;
			return disabilitar;
		} 
		
	}
    
    public String salvarRecebimentoParcial(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        contasReceber.setBanco(banco);
        contasReceber.setCliente(cliente);
        contasReceber.setUsuario(usuarioLogadoMB.getUsuario());
        contasReceber.setValorPago(valorPagoParcial + valorParcial);
        contasReceber.setValorParcela(contasReceber.getValorParcela() - valorParcial);
        contasReceber = contasReceberFacade.salvar(contasReceber);
        contasReceber.setDataPagamento(dataRecebimentoParcial);
        listaRecebimentoParial.add(contasReceber);
        contasReceber.setRecebimentoParcialList(listaRecebimentoParial);
        RequestContext.getCurrentInstance().closeDialog(contasReceber);
        return "";
    }
    
    public String recebimentoContaParcial(Contasreceber contasreceber){
		 if (contasreceber!=null){
			 FacesContext fc = FacesContext.getCurrentInstance();
			 HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			 session.setAttribute("contareceber", contasreceber);
			 return "visualizarRecebimentoParcial";
		 }
		 return "";
	 }
}