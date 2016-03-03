package br.com.financemate.manageBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class mensagem {

    private String messagem="";

    public String getMessagem() {
        return messagem;
    }

    public void setMessagem(String messagem) {
        this.messagem = messagem;
    }

    public void saveMessagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Confirmado com Sucesso", ""));
    }

    public void excluiMessagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Exclu�do com Sucesso", ""));
    }

    public void autorizar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Autorizado com Sucesso", ""));
    }

    public void liberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Liberado com Sucesso", ""));
    }
    
    public void naoLiberar() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Você esta tentando liberar uma conta não autorizada", ""));
    }
    
    public void recebido() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebido com Sucesso", ""));
    }
    
    public void cancelado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cancelado com Sucesso", ""));
    }
    
    public void editado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Editado com Sucesso", ""));
    }
    
    public void acesso() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Acesso Negado", ""));
    }
}