package br.com.financemate.manageBean;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.financemate.util.Criptografia;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.UsuarioFacade;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Usuario;

@Named
@SessionScoped
public class UsuarioLogadoMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
    private Cliente cliente;
    private String novaSenha;
    private String confirmaNovaSenha;
    private String nomeCliente;
    
    
    
    
	public UsuarioLogadoMB() {
		this.usuario = new Usuario();
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getNovaSenha() {
		return novaSenha;
	}
	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}
	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
    
    
	public String validarUsuario() {
        if ((usuario.getLogin() != null) && (usuario.getSenha() == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            String senha = "";
            try {
                senha = Criptografia.encript(usuario.getSenha());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }
            usuario.setSenha(senha);
            UsuarioFacade usuarioFacade = new UsuarioFacade();
            try {
                usuario = usuarioFacade.consultar(usuario.getLogin(), usuario.getSenha());
                if (usuario == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
                } else {
                    if (usuario.getCliente() > 0) {
                        ClienteFacade clienteFacade = new ClienteFacade();
                        cliente = clienteFacade.consultar(usuario.getCliente());
                        nomeCliente = cliente.getNomeFantasia();
                    } else {
                        cliente = null;
                        nomeCliente = "FINANCEMATE - Assessoria Cont�bil & Financeira";
                    }
                    return "principal";
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }

        }
        usuario = new Usuario();
        return "";
    }
    
    
    
    public void erroLogin(String mensagem) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensagem, ""));
    }
    
    public void validarTrocarSenha() {
        if ((usuario.getLogin() != null) && (usuario.getSenha() == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", "Login Invalido."));
        } else {
            UsuarioFacade usuarioFacade = new UsuarioFacade();
            try {
                usuario = usuarioFacade.consultar(usuario.getLogin(), usuario.getSenha());
                if (usuario == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                FacesContext.getCurrentInstance().addMessage(null, mensagem);
            }

        }
    }
     public String confirmaNovaSenha() {
        if ((novaSenha.length() > 0) && (confirmaNovaSenha.length() > 0)) {
            if (novaSenha.equalsIgnoreCase(confirmaNovaSenha)) {
                UsuarioFacade usuarioFacade = new UsuarioFacade();
                usuario.setSenha(novaSenha);
                try {
                    usuario = usuarioFacade.salvar(usuario);
                    novaSenha = "";
                confirmaNovaSenha = "";
                return "principal";
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
                    FacesMessage mensagem = new FacesMessage("Erro: " + ex);
                    FacesContext.getCurrentInstance().addMessage(null, mensagem);
                }
                
            } else {
                novaSenha = "";
                confirmaNovaSenha = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Acesso Negado."));
        }
        return "";
    }
    
     public String cancelarTrocaSenha(){
        usuario = new Usuario();
        novaSenha="";
        confirmaNovaSenha="";
        return "index";
    }
}
