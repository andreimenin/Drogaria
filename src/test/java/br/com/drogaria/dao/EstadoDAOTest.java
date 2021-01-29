package br.com.drogaria.dao;

import java.util.List;


import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Estado;

public class EstadoDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		Estado estado = new Estado();
		estado.setNome("Rio de Janeiro");
		estado.setSigla("RJ");		
		EstadoDAO estadoDAO = new EstadoDAO();		
		estadoDAO.salvar(estado);
				
	}
	
	@Ignore
	@Test
	public void listar() {
		
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listar();
		
		System.out.println("Total de registros encontrados: " + resultado.size());
		for(Estado estado : resultado) {
			System.out.println(estado.getSigla() + " - " + estado.getNome());
		}
		
		
	}
	
	@Ignore
	@Test
	public void buscar() {
		
		EstadoDAO estadoDAO = new EstadoDAO();		
	    Estado estado = estadoDAO.buscar(1L);
	    
	    if(estado == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Estado encontrado: " + estado.getNome() + " - " + estado.getSigla());
	    }
	    		
	}
	
	
	@Ignore
	@Test
	public void excluir() {
		
		EstadoDAO estadoDAO = new EstadoDAO();		
	    Estado estado = estadoDAO.buscar(1L);
	    
	    if(estado == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Estado excluído: " + estado.getNome() + " - " + estado.getSigla());
	    	estadoDAO.excluir(estado);
	    }
	    		
	}
	
	@Ignore
	@Test
	public void editar() {
		
		EstadoDAO estadoDAO = new EstadoDAO();		
	    Estado estado = estadoDAO.buscar(2L);
	    
	    
	    if(estado == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Estado editado: " + estado.getNome() + " - " + estado.getSigla());
	    	
	    	
	    	estado.setNome("São Paulo");
	    	estado.setSigla("SP");	    	
	    	estadoDAO.editar(estado);
	    }
	    		
	}
	
	
	
	
	
}
