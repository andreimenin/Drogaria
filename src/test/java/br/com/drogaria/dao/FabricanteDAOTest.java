package br.com.drogaria.dao;

import java.util.List;


import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Fabricante;

public class FabricanteDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		Fabricante Fabricante = new Fabricante();
		Fabricante.setDescricao("Fabricante Z");	
		FabricanteDAO FabricanteDAO = new FabricanteDAO();		
		FabricanteDAO.salvar(Fabricante);
				
	}
	
	@Ignore
	@Test
	public void listar() {
		
		FabricanteDAO FabricanteDAO = new FabricanteDAO();
		List<Fabricante> resultado = FabricanteDAO.listar();
		
		System.out.println("Total de registros encontrados: " + resultado.size());
		for(Fabricante Fabricante : resultado) {
			System.out.println(Fabricante.getDescricao());
		}
		
		
	}
	
	@Ignore
	@Test
	public void buscar() {
		
		FabricanteDAO FabricanteDAO = new FabricanteDAO();		
	    Fabricante Fabricante = FabricanteDAO.buscar(1L);
	    
	    if(Fabricante == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Fabricante encontrado: " + Fabricante.getDescricao());
	    }
	    		
	}
	
	
	@Ignore
	@Test
	public void excluir() {
		
		FabricanteDAO FabricanteDAO = new FabricanteDAO();		
	    Fabricante Fabricante = FabricanteDAO.buscar(1L);
	    
	    if(Fabricante == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Fabricante excluído: " + Fabricante.getDescricao());
	    	FabricanteDAO.excluir(Fabricante);
	    }
	    		
	}
	
	@Ignore
	@Test
	public void editar() {
		
		FabricanteDAO FabricanteDAO = new FabricanteDAO();		
	    Fabricante Fabricante = FabricanteDAO.buscar(2L);
	    
	    
	    if(Fabricante == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Fabricante editado: " + Fabricante.getDescricao());
	    	
	    	
	    	Fabricante.setDescricao("Fabricante Z");	    	
	    	FabricanteDAO.editar(Fabricante);
	    }
	    		
	}
	
	
	
	
	
}
