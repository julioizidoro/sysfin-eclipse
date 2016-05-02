package br.com.financemate.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jfree.data.time.Day;

public class Main {

	public static void main(String[] args) {
		Date dataInicial = new Date();
    	Calendar c = new GregorianCalendar();
		c.setTime(dataInicial);
		c.add(Calendar.DAY_OF_MONTH, 2);
		Date data = c.getTime();
		Date dataFinal = data;
		int numero = dataFinal.getDay();
		System.out.println(numero);
	}

}
