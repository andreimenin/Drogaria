package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;


import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;

public class ItemVendaDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		VendaDAO vendaDAO = new VendaDAO();
		Venda venda = vendaDAO.buscar(1L);
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		Produto produto = produtoDAO.buscar(1L);		
		
		ItemVenda itemVenda = new ItemVenda();
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
		
		itemVenda.setProduto(produto);
		itemVenda.setQuantidade(14L);
		itemVenda.setValorParcial(new BigDecimal(617.09));
		itemVenda.setVenda(venda);
		
		itemVendaDAO.salvar(itemVenda);
		
	}
		
	@Ignore
	@Test
	public void listar() {
		
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
		List<ItemVenda> resultado = itemVendaDAO.listar();
		
		System.out.println("Total de registros encontrados: " + resultado.size());
		for(ItemVenda itemVenda : resultado) {
			System.out.println(itemVenda.getVenda() + " - " + itemVenda.getProduto());
		}		
	}
	
	@Ignore
	@Test
	public void buscar() {
		
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();		
	    ItemVenda itemVenda = itemVendaDAO.buscar(1L);
	    
	    if(itemVenda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("ItemVenda encontrada: " +itemVenda.getVenda() + " - " + itemVenda.getProduto());
	    }
	}
	
	
	@Ignore
	@Test
	public void excluir() {
		
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();		
	    ItemVenda itemVenda = itemVendaDAO.buscar(1L);
	    
	    if(itemVenda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	itemVendaDAO.excluir(itemVenda);
	    	System.out.println("ItemVenda excluída: " + itemVenda.getVenda() + " - " + itemVenda.getProduto());
	    }
	    		
	}
	
	@Ignore
	@Test
	public void editar() {
		VendaDAO vendaDAO = new VendaDAO();
		Venda venda = vendaDAO.buscar(1L);
		
		ProdutoDAO produtoDAO = new ProdutoDAO();
		Produto produto = produtoDAO.buscar(1L);		
		
		ItemVendaDAO itemVendaDAO = new ItemVendaDAO();		
	    ItemVenda itemVenda = itemVendaDAO.buscar(2L);	    
	    
	    if(itemVenda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("ItemVenda editada: " + itemVenda.getVenda() + " - " + itemVenda.getProduto());
	    	
	    	
	    	itemVenda.setProduto(produto);
			itemVenda.setQuantidade(14L);
			itemVenda.setValorParcial(new BigDecimal(617.09));
			itemVenda.setVenda(venda);
			itemVendaDAO.editar(itemVenda);	
	    }
	    		
	}
	
	
	
	
	
	
	
	
	
	
}
