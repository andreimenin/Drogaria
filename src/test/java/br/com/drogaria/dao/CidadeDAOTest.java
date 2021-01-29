package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

public class CidadeDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		Cidade cidade = new Cidade();
		CidadeDAO cidadeDAO = new CidadeDAO();		
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = new Estado();
		
		estado = estadoDAO.buscar(1L);
		
		if(estado == null) {
			System.out.println("Estado não encontrado.");
		}
		else {
			cidade.setEstado(estado);
			cidade.setNome("Bauru");
			cidadeDAO.salvar(cidade);
		}	
		
	}
	
	
	@Ignore
	@Test
	public void listar() {			
		CidadeDAO cidadeDAO = new CidadeDAO();		
		List<Cidade> resultado = cidadeDAO.listar();		
		
		System.out.println("Número de registros encontrados: " + resultado.size());
		
		for(Cidade cidade : resultado) {
			
			System.out.println(cidade.getNome() + " - " + cidade.getEstado().getSigla());			
		}			
	}
	
	
	@Ignore
	@Test
	public void buscar() {
		
		CidadeDAO cidadeDAO = new CidadeDAO();		
	    Cidade cidade = cidadeDAO.buscar(1L);
	    
	    if(cidade == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Cidade encontrada: " + cidade.getNome() + " - " + cidade.getEstado().getSigla());
	    }
	    		
	}
	
	@Ignore
	@Test
	public void excluir() {
		
		CidadeDAO cidadeDAO = new CidadeDAO();		
	    Cidade cidade = cidadeDAO.buscar(1L);
	    
	    if(cidade == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Cidade excluída: " + cidade.getNome() + " - " + cidade.getEstado().getSigla());
	    	cidadeDAO.excluir(cidade);
	    }
	    		
	}
	
	
	@Ignore
	@Test
	public void editar() {
		
		Cidade cidade = new Cidade();
		CidadeDAO cidadeDAO = new CidadeDAO();
		
		Estado estado = new Estado();
		EstadoDAO estadoDAO = new EstadoDAO();		
		
		estado = estadoDAO.buscar(1L);		
		cidade = cidadeDAO.buscar(1L);
		
		
		if((cidade == null)||(estado == null)) {
			System.out.println("Cidade ou Estado não encontrado");
		}
		else {			
			cidade.setEstado(estado);
			cidade.setNome("Rio de Janeiro");
			cidadeDAO.editar(cidade);
			
		}		
		
	}
	
	//@Ignore
	@Test
	public void buscarPorEstado() {			
		
		Long estadoCodigo = 1L;
		
		
		CidadeDAO cidadeDAO = new CidadeDAO();		
		List<Cidade> resultado = cidadeDAO.buscarPorEstado(estadoCodigo);	
		
		System.out.println("Número de registros encontrados: " + resultado.size());
		
		for(Cidade cidade : resultado) {
			
			System.out.println(cidade.getNome() + " - " + cidade.getEstado().getSigla());			
		}			
	}
	
	
	
}
