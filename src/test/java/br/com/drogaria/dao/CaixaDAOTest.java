package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Caixa;

public class CaixaDAOTest {

	@Ignore
	@Test
	public void salvar() throws ParseException {
		
		Caixa caixa = new Caixa();
		CaixaDAO caixaDAO = new CaixaDAO();
		
		caixa.setDataAbertura(new SimpleDateFormat("dd/MM/yyyy").parse("27/11/2017"));
		caixa.setValorAbertura(new BigDecimal("40.00"));
				
		caixaDAO.salvar(caixa);
		
	}
	
	@Ignore
	@Test
	public void buscar() throws ParseException{
		Caixa caixa = new Caixa();
		CaixaDAO caixaDAO = new CaixaDAO();
		
		caixa = caixaDAO.buscarPorData(new SimpleDateFormat("dd/MM/yyyy").parse("21/11/2017"));
		System.out.println(caixa);
		Assert.assertNull(caixa); //testando a buscar se tem algum resultado nulo
		
	}
	
	
	
	
	
}
