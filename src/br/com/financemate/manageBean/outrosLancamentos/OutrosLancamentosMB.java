package br.com.financemate.manageBean.outrosLancamentos;

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

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.OutrosLancamentosFacade;
import br.com.financemate.manageBean.UsuarioLogadoMB;
import br.com.financemate.manageBean.mensagem;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Outroslancamentos;

@Named
@ViewScoped
public class OutrosLancamentosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private UsuarioLogadoMB usuarioLogadoBean;
    private List<Outroslancamentos> listaOutrosLancamentos;
    private Banco banco;
    private Cliente cliente;
    private List<Cliente> listaClientes;
    private Date dataInical;
    private Date dataFinal;
    private String sql;
    private List<Banco> listaBancos;
    private boolean verCliente=false;
    private Outroslancamentos outrosLancamentos;
	
	@PostConstruct
	public void init(){
		listaOutrosLancamentos = new ArrayList<Outroslancamentos>();
		gerarListaCliente();
		getUsuarioLogadoBean();
		verificarCliente();
	}
	
	

	public Outroslancamentos getOutrosLancamentos() {
		return outrosLancamentos;
	}



	public void setOutrosLancamentos(Outroslancamentos outrosLancamentos) {
		this.outrosLancamentos = outrosLancamentos;
	}



	public UsuarioLogadoMB getUsuarioLogadoBean() {
		return usuarioLogadoBean;
	}

	public void setUsuarioLogadoBean(UsuarioLogadoMB usuarioLogadoBean) {
		this.usuarioLogadoBean = usuarioLogadoBean;
	}

	public List<Outroslancamentos> getListaOutrosLancamentos() {
		return listaOutrosLancamentos;
	}

	public void setListaOutrosLancamentos(List<Outroslancamentos> listaOutrosLancamentos) {
		this.listaOutrosLancamentos = listaOutrosLancamentos;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public Date getDataInical() {
		return dataInical;
	}

	public void setDataInical(Date dataInical) {
		this.dataInical = dataInical;
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

	public List<Banco> getListaBancos() {
		return listaBancos;
	}

	public void setListaBancos(List<Banco> listaBancos) {
		this.listaBancos = listaBancos;
	}

	public boolean isVerCliente() {
		return verCliente;
	}

	public void setVerCliente(boolean verCliente) {
		this.verCliente = verCliente;
	}
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
    
    public void gerarPesquisa() {
        if ((banco != null) && 
                 (cliente != null)) {
            sql = "Select o from Outroslancamentos o where o.banco.idbanco=" + banco.getIdbanco()
                    +" and o.cliente.idcliente=" + cliente.getIdcliente();
            sql = sql + " order by o.dataCompensacao";  
            OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
            try {
                listaOutrosLancamentos = outrosLancamentosFacade.listaOutrosLancamentos(sql);
                if (listaOutrosLancamentos==null){
                    listaOutrosLancamentos = new ArrayList<Outroslancamentos>();
                } 
            } catch (SQLException ex) {
                Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensagem(ex, "Erro Listar Outros Lançamentos", "Erro");
            }
        }else {
            mostrarMensagem(null, "Dados inválidos", "Aviso");
        }
    }
    
    public void gerarListaCliente(){
        ClienteFacade clienteFacade = new ClienteFacade();
        try {
            listaClientes = clienteFacade.listar("");
        } catch (SQLException ex) {
            Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Listar Clientes", "Erro");
        }
    }
    
    public void verificarCliente(){
        if (usuarioLogadoBean.getUsuario().getCliente()>0){
            ClienteFacade clienteFacade = new ClienteFacade();
            try {
                cliente = clienteFacade.consultar(usuarioLogadoBean.getUsuario().getCliente());
                verCliente = true;
                if(cliente==null){
                    verCliente=false;
                    cliente = new Cliente();
                }
            } catch (SQLException ex) {
                Logger.getLogger(OutrosLancamentosMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  
    
    public void gerarListaBanco(){
		if (cliente!=null) {
	        BancoFacade bancoFacade = new BancoFacade();
	        String sql = "Select b from Banco b where b.cliente.idcliente=" + cliente.getIdcliente() + " order by b.nome";
	        listaBancos = bancoFacade.listar(sql);
	        if (listaBancos ==null){ 
	            listaBancos = new ArrayList<Banco>();
	        }
		}else {
			listaBancos = new ArrayList<Banco>();
        } 
    }
    
    public String coresValoresEntrada(Outroslancamentos outroslancamentos){
    	if (outroslancamentos.getValorEntrada() > outroslancamentos.getValorSaida()) {
			return "green";
		}else{
			return "gray";
		}
    }
    
    public String coresValoresSaida(Outroslancamentos outroslancamentos){
    	if (outroslancamentos.getValorSaida() > outroslancamentos.getValorEntrada()) {
			return "red";
		}else{
			return "gray";
		}
    }
    
    public String excluir(){
    	OutrosLancamentosFacade outrosLancamentosFacade = new OutrosLancamentosFacade();
    	try {
			outrosLancamentosFacade.excluir(outrosLancamentos.getIdoutroslancamentos());
			gerarPesquisa();
	    	mensagem mensagem = new mensagem();
	    	mensagem.excluiMessagem();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
     }
    
    public String novoLancamento() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 600);
        RequestContext.getCurrentInstance().openDialog("cadOutrosLancamentos");
        return "";
    }

}
