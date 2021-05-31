package br.com.senior.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.senior.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {
	Integer PrazoDePagto = 7;

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, PrazoDePagto);
		pagto.setDataVencimento(cal.getTime());
	}
	
}
