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
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
    }

    public void excluiMessagem() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com Sucesso", ""));
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
    
    public void recebidoParcial() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Recebimento Parcial com Sucesso", ""));
    }
    
    public void cancelado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cancelado com Sucesso", ""));
    }
    
    public void editado() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Editado com Sucesso", ""));
    }
    
    public void historico() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Histórico Salvo com Sucesso", ""));
    }
    
    public void acesso() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Acesso Negado", ""));
    }
    
    public void competencia() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Competência não informada", ""));
    }
    
    public void excluirConfirmacao() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluido com sucesso", ""));
    }
    
    public void warn() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção!", "Campos em vermelho não preenchido."));
    }
}