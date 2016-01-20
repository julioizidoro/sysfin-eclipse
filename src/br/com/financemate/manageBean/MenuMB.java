package br.com.financemate.manageBean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class MenuMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init(){
		
	}

	public String contasPagar(){
		return "consContaPagar";
	}
	
	public String principal(){
		return "principal";
	}
	

}
