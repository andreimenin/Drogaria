package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Fabricante;

public class ProdutoDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		Produto produto = new Produto();
		ProdutoDAO produtoDAO = new ProdutoDAO();		
		
		FabricanteDAO estadoDAO = new FabricanteDAO();
		Fabricante estado = new Fabricante();
		
		estado = estadoDAO.buscar(1L);
		
		if(estado == null) {
			System.out.println("Fabricante não encontrado.");
		}
		else {
			produto.setFabricante(estado);
			produto.setDescricao("Produto ");
			produto.setPreco(new BigDecimal("19.84"));
			produto.setQuantidade(185L);
			
			
			produtoDAO.salvar(produto);
		}	
		
	}
	
	
	@Ignore
	@Test
	public void listar() {			
		ProdutoDAO produtoDAO = new ProdutoDAO();		
		List<Produto> resultado = produtoDAO.listar();		
		
		System.out.println("Número de registros encontrados: " + resultado.size());
		
		for(Produto produto : resultado) {
			
			System.out.println(produto.getDescricao() + " - " + produto.getFabricante().getDescricao());			
		}			
	}
	
	
	@Ignore
	@Test
	public void buscar() {
		
		ProdutoDAO produtoDAO = new ProdutoDAO();		
	    Produto produto = produtoDAO.buscar(1L);
	    
	    if(produto == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println(produto.getDescricao() + " - " + produto.getFabricante().getDescricao());
	    }
	    		
	}
	
	@Ignore
	@Test
	public void excluir() {
		
		ProdutoDAO produtoDAO = new ProdutoDAO();		
	    Produto produto = produtoDAO.buscar(1L);
	    
	    if(produto == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println(produto.getDescricao() + " - " + produto.getFabricante().getDescricao());
	    	produtoDAO.excluir(produto);
	    }
	    		
	}
	
	
	@Ignore
	@Test
	public void editar() {
		
		Produto produto = new Produto();
		ProdutoDAO produtoDAO = new ProdutoDAO();
		
		Fabricante estado = new Fabricante();
		FabricanteDAO estadoDAO = new FabricanteDAO();		
		
		estado = estadoDAO.buscar(1L);		
		produto = produtoDAO.buscar(1L);
		
		
		if((produto == null)||(estado == null)) {
			System.out.println("Produto ou Fabricante não encontrado");
		}
		else {			
			produto.setFabricante(estado);
			produto.setDescricao("Produto ");
			produto.setPreco(new BigDecimal("19.84"));
			produto.setQuantidade(185L);
			
			
			produtoDAO.editar(produto);
			
		}		
		
	}
	
	
	
	
}
