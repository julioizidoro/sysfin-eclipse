package br.com.financemate.manageBean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.TipoAcessoFacade;
import br.com.financemate.facade.UsuarioFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Tipoacesso;
import br.com.financemate.model.Usuario;
import br.com.financemate.util.Criptografia;

@Named
@ViewScoped
public class CadUsuarioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tipoacesso tipoacesso;
	private List<Tipoacesso> listaTipoAcesso;
	private Usuario usuario;
	private boolean habilitarSenha;
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private boolean habilitarCliente = false;
	private boolean eCliente;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		usuario = (Usuario) session.getAttribute("usuario");
		session.removeAttribute("usuario");
		if (usuario == null) {
			usuario = new Usuario();
			habilitarSenha = true;
		} else {
			ClienteFacade clienteFacade = new ClienteFacade();
			tipoacesso = usuario.getTipoacesso();
			habilitarSenha = false;
			try {
				if (usuario.getCliente() > 0) {
					cliente = clienteFacade.consultar(usuario.getCliente());
					habilitarCliente = true;
					eCliente = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		gerarListaCliente();
		gerarListaTipoAcesso();
	}

	public Tipoacesso getTipoacesso() {
		return tipoacesso;
	}

	public void setTipoacesso(Tipoacesso tipoacesso) {
		this.tipoacesso = tipoacesso;
	}

	public List<Tipoacesso> getListaTipoAcesso() {
		return listaTipoAcesso;
	}

	public void setListaTipoAcesso(List<Tipoacesso> listaTipoAcesso) {
		this.listaTipoAcesso = listaTipoAcesso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isHabilitarSenha() {
		return habilitarSenha;
	}

	public void setHabilitarSenha(boolean habilitarSenha) {
		this.habilitarSenha = habilitarSenha;
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
	

	public boolean isHabilitarCliente() {
		return habilitarCliente;
	}

	public void setHabilitarCliente(boolean habilitarCliente) {
		this.habilitarCliente = habilitarCliente;
	}
	
	

	public boolean iseCliente() {
		return eCliente;
	}

	public void seteCliente(boolean eCliente) {
		this.eCliente = eCliente;
	}

	public void gerarListaTipoAcesso() {
		TipoAcessoFacade tipoAcessoFacade = new TipoAcessoFacade();
		try {
			listaTipoAcesso = tipoAcessoFacade.listar();
			if (listaTipoAcesso == null || listaTipoAcesso.isEmpty()) {
				listaTipoAcesso = new ArrayList<Tipoacesso>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Usuario());
	}

	public void salvar() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		boolean naoExisteLogin;
		usuario.setTipoacesso(tipoacesso);
		if (eCliente && cliente != null) {
			usuario.setCliente(cliente.getIdcliente());
		}else{
			usuario.setCliente(0);
		}
		String msg = validarDados();
		if (msg.length() < 5) {
			naoExisteLogin = verificacaoLogin();
			if (usuario.getIdusuario() == null) {
				if (naoExisteLogin) {
					try {
						usuario.setSenha(Criptografia.encript(usuario.getSenha()));
						try {
							usuario = usuarioFacade.salvar(usuario);
							RequestContext.getCurrentInstance().closeDialog(usuario);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					mensagem mensagem = new mensagem();
					mensagem.existeLogin();
				}
			} else {
				try {
					usuario = usuarioFacade.salvar(usuario);
					RequestContext.getCurrentInstance().closeDialog(usuario);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			mensagem mensagem = new mensagem();
			mensagem.notificacao(msg);
		}
	}

	public String validarDados() {
		String mensagem = "";
		if (usuario.getNome().equalsIgnoreCase("")) {
			mensagem = mensagem + " Você Não informou seu nome \r\n";
		}
		if (usuario.getLogin().equalsIgnoreCase("")) {
			mensagem = mensagem + " Você não informou seu login \r\n";
		}
		if (usuario.getSenha().equalsIgnoreCase("")) {
			mensagem = mensagem + " Você não informou sua senha \r\n";
		}
		return mensagem;
	}

	public boolean verificacaoLogin() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		try {
			Usuario user = usuarioFacade.verificarLogin(usuario.getLogin());
			if (user == null) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void gerarListaCliente(){
		ClienteFacade clienteFacade = new ClienteFacade();
		try {
			listaCliente = clienteFacade.listar("");
			if (listaCliente == null) {
				listaCliente = new ArrayList<Cliente>();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void habilitarEscolhaCliente(){
		if (eCliente) {
			habilitarCliente = true;
		}else{
			habilitarCliente = false;
		}
	} 

}
