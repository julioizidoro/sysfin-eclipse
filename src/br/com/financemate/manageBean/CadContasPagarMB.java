package br.com.financemate.manageBean;


import java.io.IOException;
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
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.financemate.facade.BancoFacade;
import br.com.financemate.facade.ClienteFacade;
import br.com.financemate.facade.ContasPagarFacade;
import br.com.financemate.facade.CpTransferenciaFacade;
import br.com.financemate.facade.FtpDadosFacade;
import br.com.financemate.facade.NomeArquivoFacade;
import br.com.financemate.facade.OperacaoUsuarioFacade;
import br.com.financemate.facade.PlanoContasFacade;
import br.com.financemate.model.Banco;
import br.com.financemate.model.Cliente;
import br.com.financemate.model.Contaspagar;
import br.com.financemate.model.Cptransferencia;
import br.com.financemate.model.Ftpdados;
import br.com.financemate.model.Nomearquivo;
import br.com.financemate.model.Operacaousuairo;
import br.com.financemate.model.Planocontas;
import br.com.financemate.util.Ftp;

@Named
@ViewScoped
public class CadContasPagarMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Contaspagar contaPagar;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Planocontas> listaPlanoContas;
    private List<Cliente> listaCliente;
    private Planocontas planoContas;
    private Cliente cliente;
    private Banco banco;
    private List<Banco> listaBanco;
    private UploadedFile file;
    private Cptransferencia cptransferencia;
    Boolean selecionada = false;
	private String nomeAnexo = "Anexar"; 
	private String nomeAquivoFTP;
	private Date dataEnvio;
	private Boolean tipoAcesso;
	private Boolean habilitarUnidade = false;
	private Operacaousuairo operacaousuairo;
	private List<Cptransferencia> listaCptransferencia;
	

	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        file = (UploadedFile) session.getAttribute("file");
        cliente = (Cliente) session.getAttribute("cliente");
        contaPagar = (Contaspagar) session.getAttribute("contapagar");
        cptransferencia =  (Cptransferencia) session.getAttribute("cptransferencia");
        session.removeAttribute("contapagar");
        session.removeAttribute("file");
        session.removeAttribute("cliente");
        gerarListaCliente();
        gerarListaPlanoContas();
        if (contaPagar == null) { 
			contaPagar = new Contaspagar();
			if (usuarioLogadoMB.getCliente() != null) {
				cliente = usuarioLogadoMB.getCliente();
			}else{
				if (cliente == null) {
					cliente = new Cliente();
				}
				
			}
			cptransferencia = new Cptransferencia();
		}else{
			if (cliente == null) {
				cliente = contaPagar.getCliente();
			}
            planoContas = contaPagar.getPlanocontas();
            banco = contaPagar.getBanco();
            gerarListaBanco();
            transferenciaBancaria(); 
            if (contaPagar.getFormaPagamento().equalsIgnoreCase("transferencia")) {
            	CpTransferenciaFacade cpTransferenciaFacade = new CpTransferenciaFacade();
            	try {
            		listaCptransferencia = cpTransferenciaFacade.listarTranferencia("SELECT c FROM Cptransferencia c JOIN Contaspagar co on c.contaspagar.idcontasPagar=co.idcontasPagar WHERE c.contaspagar.idcontasPagar=" + contaPagar.getIdcontasPagar());
            		for (int i = 0; i < listaCptransferencia.size(); i++) {
            			cptransferencia = cpTransferenciaFacade.consultar(listaCptransferencia.get(i).getIdcptransferencia());
					} 
            		if (cptransferencia == null) { 
						cptransferencia = new Cptransferencia();
            		}
				} catch (SQLException e) { 
					e.printStackTrace();
				}
            }
        }
        desabilitarUnidade();
	}
	
	
	
	
	public List<Cptransferencia> getListaCptransferencia() {
		return listaCptransferencia;
	}




	public void setListaCptransferencia(List<Cptransferencia> listaCptransferencia) {
		this.listaCptransferencia = listaCptransferencia;
	}




	public Operacaousuairo getOperacaousuairo() {
		return operacaousuairo;
	}




	public void setOperacaousuairo(Operacaousuairo operacaousuairo) {
		this.operacaousuairo = operacaousuairo;
	}




	public Boolean getHabilitarUnidade() {
		return habilitarUnidade;
	}




	public void setHabilitarUnidade(Boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}




	public Boolean getTipoAcesso() {
		return tipoAcesso;
	}



	public void setTipoAcesso(Boolean tipoAcesso) {
		this.tipoAcesso = tipoAcesso;
	}



	public Date getDataEnvio() {
		return dataEnvio;
	}



	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}



	public String getNomeAquivoFTP() {
		return nomeAquivoFTP;
	}



	public void setNomeAquivoFTP(String nomeAquivoFTP) {
		this.nomeAquivoFTP = nomeAquivoFTP;
	}



	public Boolean getSelecionada() {
		return selecionada;
	}

	

	public String getNomeAnexo() {
		return nomeAnexo;
	}

	public void setNomeAnexo(String nomeAnexo) {
		this.nomeAnexo = nomeAnexo;
	}

	public void setSelecionada(Boolean selecionada) {
		this.selecionada = selecionada;
	}
	

	public Cptransferencia getCptransferencia() {
		return cptransferencia;
	}



	public void setCptransferencia(Cptransferencia cptransferencia) {
		this.cptransferencia = cptransferencia;
	}



	public Contaspagar getContaPagar() {
		return contaPagar;
	}

	public void setContaPagar(Contaspagar contaPagar) {
		this.contaPagar = contaPagar;
	}

	

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public List<Planocontas> getListaPlanoContas() {
		return listaPlanoContas;
	}

	public void setListaPlanoContas(List<Planocontas> listaPlanoContas) {
		this.listaPlanoContas = listaPlanoContas;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Planocontas getPlanoContas() {
		return planoContas;
	}

	public void setPlanoContas(Planocontas planoContas) {
		this.planoContas = planoContas;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
	
	
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
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
	
	public void salvar(){
		Operacaousuairo operacaousuairo = new Operacaousuairo();
		contaPagar.setBanco(banco);
		contaPagar.setCliente(cliente);
		contaPagar.setContaPaga("N");
		if (planoContas != null) {
			contaPagar.setPlanocontas(planoContas);
		}else{
			PlanoContasFacade planoContasFacade = new PlanoContasFacade();
			try {
				planoContas = planoContasFacade.consultar(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			contaPagar.setPlanocontas(planoContas);
		}
		if (contaPagar.getCompetencia() == null) {
			contaPagar.setCompetencia("");
		}
		if (contaPagar.getDataAgendamento() == null) {
			contaPagar.setDataAgendamento(null);
		}
		if (contaPagar.getDataCompensacao() == null) {
			contaPagar.setDataCompensacao(null);
		}
		if (contaPagar.getAutorizarPagamento() == null || contaPagar.getAutorizarPagamento().equalsIgnoreCase("N")) {
			contaPagar.setAutorizarPagamento("N");
		}  
		
		String mensagem = validarDados();
		if (mensagem=="") {
			ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			if (contaPagar.getIdcontasPagar() == null) {
				operacaousuairo.setTipooperacao("Usu·rio Cadastrou");
			}else{
				operacaousuairo.setTipooperacao("Usu·rio Alterou");
			}
			contaPagar = contasPagarFacade.salvar(contaPagar);
			if (contaPagar.getIdcontasPagar() != null) {
				OperacaoUsuarioFacade operacaoUsuarioFacade = new OperacaoUsuarioFacade();
				
				operacaousuairo.setContaspagar(contaPagar);
				operacaousuairo.setData(new Date());
				operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
				try {
					operacaoUsuarioFacade.salvar(operacaousuairo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cptransferencia!=null){
				
		    	salvarTransferencia();
		    }
			if (file != null) {
				nomeArquivo();
				salvarArquivoFTP();
			}
			RequestContext.getCurrentInstance().closeDialog(contaPagar);
			
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
		}
    }

	
	public void salvarRepetir(){
		Operacaousuairo operacaousuairo = new Operacaousuairo();
        contaPagar.setBanco(banco);
        contaPagar.setPlanocontas(planoContas);
        contaPagar.setCliente(cliente);
        contaPagar.setContaPaga("N");
        if (planoContas != null) {
			contaPagar.setPlanocontas(planoContas);
		}else{
			PlanoContasFacade planoContasFacade = new PlanoContasFacade();
			try {
				planoContas = planoContasFacade.consultar(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			contaPagar.setPlanocontas(planoContas);
		}
        if (contaPagar.getCompetencia() == null) {
			contaPagar.setCompetencia("");
		}
		if (contaPagar.getDataAgendamento() == null) {
			contaPagar.setDataAgendamento(null);
		}
		if (contaPagar.getDataCompensacao() == null) {
			contaPagar.setDataCompensacao(null);
		}
		String mensagem = validarDados();
		if (mensagem=="") {
			ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			if (contaPagar == null) {
				operacaousuairo.setTipooperacao("Usu·rio Cadastrou");
			}else{
				operacaousuairo.setTipooperacao("Usu·rio Alterou");
			}
	        contaPagar = contasPagarFacade.salvar(contaPagar);
	        if (contaPagar.getIdcontasPagar() != null) {
				OperacaoUsuarioFacade operacaoUsuarioFacade = new OperacaoUsuarioFacade();
				operacaousuairo.setContaspagar(contaPagar);
				operacaousuairo.setData(new Date());
				operacaousuairo.setUsuario(usuarioLogadoMB.getUsuario());
				try {
					operacaoUsuarioFacade.salvar(operacaousuairo);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	        if (cptransferencia!=null){
	        	salvarTransferencia();
	        	Cptransferencia copiaTranferencia = new Cptransferencia();
	        	copiaTranferencia = cptransferencia;
	        	cptransferencia = new Cptransferencia();
	        	cptransferencia.setBanco(copiaTranferencia.getBanco());
	        	cptransferencia.setAgencia(copiaTranferencia.getAgencia());
	        	cptransferencia.setConta(copiaTranferencia.getConta());
	        	cptransferencia.setBeneficiario(copiaTranferencia.getBeneficiario());
	        	cptransferencia.setCpfcnpj(copiaTranferencia.getCpfcnpj());
		    }
	        if (file != null) {
				salvarArquivoFTP();
			}
	        Contaspagar copia = new Contaspagar();
	        copia = contaPagar;
	        contaPagar = new Contaspagar();
	        contaPagar.setBanco(copia.getBanco());
	        contaPagar.setCliente(copia.getCliente());
	        contaPagar.setPlanocontas(copia.getPlanocontas());
	        contaPagar.setDataEnvio(copia.getDataEnvio());
	        contaPagar.setFormaPagamento(copia.getFormaPagamento());
	        contaPagar.setFornecedor(copia.getFornecedor());
	        contaPagar.setValor(copia.getValor());
	        contaPagar.setDescricao(copia.getDescricao());
	        contaPagar.setDataAgendamento(copia.getDataAgendamento());
	        contaPagar.setDataCompensacao(copia.getDataCompensacao());
	        contaPagar.setCompetencia(copia.getCompetencia());
	        contaPagar.setNumeroDocumento(copia.getNumeroDocumento());
	        contaPagar.setDataVencimento(copia.getDataVencimento());
	        
		}else{
			FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(mensagem, ""));
		}
    }
	
	public void salvarTransferencia(){
		CpTransferenciaFacade cpTransferenciaFacade = new CpTransferenciaFacade();
		cptransferencia.setContaspagar(contaPagar);
		cptransferencia = cpTransferenciaFacade.salvar(cptransferencia);
	}
	
	public String validarDados(){
		String mensagem = "";
		if (contaPagar.getFornecedor() == null) {
			mensagem = mensagem + "Fornecedor n√£o informado \r\n";
		}
		if (contaPagar.getValor().equals(0f)) {
			mensagem = mensagem + "Valor n√£o informado \n";
		}
		if (contaPagar.getDescricao().equalsIgnoreCase("")) {
			mensagem = mensagem + "DescriÁ„o n„o informado \r\n";
		}
		if (contaPagar.getBanco() == null) {
			mensagem = mensagem + "Conta n„o selecionada \r\n";
		}
		if (contaPagar.getDataVencimento() == null) {
			mensagem = mensagem + "Data de Vencimento n„o informada \r\n";
		}
		if (contaPagar.getFormaPagamento() == null) {
			mensagem = mensagem + "Forma de Pagamento n„o selecionada \r\n";
		}
		
		
		
		
		return mensagem;
	}
	
	public void upload(FileUploadEvent event) {
        if(file != null) {
            FacesMessage message = new FacesMessage("Anexado com Sucesso", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
	
	public Boolean transferenciaBancaria(){
		selecionada = false;
		if (contaPagar.getFormaPagamento()!=null){
			if (contaPagar.getFormaPagamento().equalsIgnoreCase("transferencia")){
				selecionada=true;
				if (cptransferencia == null) {
				    cptransferencia = new Cptransferencia();
				}
			}else {
				cptransferencia = null; 
			} 
		}
		return selecionada;
	}
	
	public String nomeAnexo(){
		if (file != null) {
			nomeAnexo = "Anexado";
			return nomeAnexo;
		}
		return nomeAnexo = "Anexar";
	}
	
	public String AdicionarNomeAnexado(){
		if (file !=null) {
			return "" + file;
		}else{ 
			return "Anexar";
		}
	}
	
	public String corIconeClips(){
		if (file != null) {
			return "../../resources/img/iconeClipVerde.png";
		}
		return "../../resources/img/iconeClipsVermelho.png";
	}

	public boolean salvarArquivoFTP(){
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(),dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
        	nomeAquivoFTP = nomeArquivo();
            String msg = ftp.enviarArquivo(file, nomeAquivoFTP);
            if (contaPagar != null && contaPagar.getIdcontasPagar() != null) {
				Nomearquivo nomearquivo = new Nomearquivo();
				NomeArquivoFacade nomeArquivoFacade = new NomeArquivoFacade();
				nomearquivo.setNomearquivo01(nomeAquivoFTP);
				nomearquivo.setContaspagar(contaPagar);
				try {
					nomeArquivoFacade.salvar(nomearquivo);
				} catch (SQLException ex) {
					Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
		            mostrarMensagem(ex, "Erro ao salvar um arquivo", "Erro");
				}
			}
            mostrarMensagem(null, msg, "");
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        }
        try {
           ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
        }
        return false;
    }
	
	
	public boolean salvarArquivoFTP(String nomeArquivoLocal, String nomeArquivoFTP){
        FtpDadosFacade fpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = fpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHost(),dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "Erro");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
            ftp.receberArquivo(""+file, nomeAquivoFTP);
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro Salvar Arquivo", "Erro");
            return false;
        }
        try {
            ftp.desconectar();
        } catch (IOException ex) {
            Logger.getLogger(CadContasPagarMB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
            return false;
        }
        return true;
        
    }
	
	public void baixarFile(){
		nomeArquivo();
		salvarArquivoFTP(""+ file, nomeAquivoFTP);
	}
	
	public String nomeArquivo(){
		nomeAquivoFTP = "" + contaPagar.getIdcontasPagar();
		nomeAquivoFTP = nomeAquivoFTP + file.getFileName().trim();
		return nomeAquivoFTP;
	}
	
	public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		salvarArquivoFTP();
	}
	
	public Date dataEnvio(){
		dataEnvio = new Date();
		contaPagar.setDataEnvio(dataEnvio);
		return dataEnvio;
	}
	
	
	public Boolean tipoAcesso(){
		if (usuarioLogadoMB.getCliente()!=null) {
			cliente.setNomeFantasia(usuarioLogadoMB.getCliente().getNomeFantasia());
			return tipoAcesso = true;
		}
		return tipoAcesso = false;
	}
	
	public void desabilitarUnidade(){
		if (usuarioLogadoMB.getCliente() != null) {
			habilitarUnidade = true;
		}else{
			habilitarUnidade = false;
		}
		 
	}
	
	
	public String anexarArquivo(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("cliente", cliente);
		return "anexarArquivo";
	}

	public String voltarCadContasPagar(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("contapagar", contaPagar);
        session.setAttribute("file", file);
		return "cadContasPagar";
	}

	
}
