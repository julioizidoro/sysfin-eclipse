package br.com.financemate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.financemate.model.Tipoplanocontas;


@FacesConverter(value="TipoPlanoContasConverter")
public class TipoPlanoContasConverter implements Converter{
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		List<Tipoplanocontas> listaTipoPlanoContas = (List<Tipoplanocontas>) arg1.getAttributes().get("listaTipoPlanoContas");
	    if (listaTipoPlanoContas != null) {
	        for (Tipoplanocontas tipoplanocontas : listaTipoPlanoContas) {
	            if (tipoplanocontas.getDescricao().equalsIgnoreCase(arg2)) {
	                return tipoplanocontas;
	            }
	        }
	    } else {
	    	Tipoplanocontas tipoplanocontas = new Tipoplanocontas();
	        return tipoplanocontas;
	    }
	    Tipoplanocontas tipoplanocontas = new Tipoplanocontas();
	    return tipoplanocontas;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2.toString().equalsIgnoreCase("0")) {
	        return "Selecione";
	    } else {
	    	Tipoplanocontas tipoplanocontas = (Tipoplanocontas) arg2;
	        return tipoplanocontas.getDescricao();
	    }
	}
}
