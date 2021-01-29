package br.com.drogaria.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Venda;

public class VendaDAOTest {

	//@Ignore
	@Test
	public void salvar() {
		
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscar(1L);
		
//		ClienteDAO clienteDAO = new ClienteDAO();
//		Cliente cliente = clienteDAO.buscar(1L);		
		
		Venda venda = new Venda();
		VendaDAO vendaDAO = new VendaDAO();
		
//		venda.setCliente(cliente);
		venda.setFuncionario(funcionario);
		venda.setHorario(new Date());
		venda.setValorTotal(new BigDecimal("748.90"));
		vendaDAO.salvar(venda);		
		
	}
	
	
	
	@Ignore
	@Test
	public void listar() {
		
		VendaDAO vendaDAO = new VendaDAO();
		List<Venda> resultado = vendaDAO.listar();
		
		System.out.println("Total de registros encontrados: " + resultado.size());
		for(Venda venda : resultado) {
			System.out.println(venda.getFuncionario() + " - " + venda.getCliente());
		}
		
		
	}
	
	@Ignore
	@Test
	public void buscar() {
		
		VendaDAO vendaDAO = new VendaDAO();		
	    Venda venda = vendaDAO.buscar(1L);
	    
	    if(venda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Venda encontrada: " + venda.getFuncionario() + " - " + venda.getCliente());
	    }
	    		
	}
	
	
	@Ignore
	@Test
	public void excluir() {
		
		VendaDAO vendaDAO = new VendaDAO();		
	    Venda venda = vendaDAO.buscar(1L);
	    
	    if(venda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	vendaDAO.excluir(venda);
	    	System.out.println("Venda excluída: " + venda.getFuncionario() + " - " + venda.getCliente());
	    }
	    		
	}
	
	@Ignore
	@Test
	public void editar() {
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		Funcionario funcionario = funcionarioDAO.buscar(1L);
		
		ClienteDAO clienteDAO = new ClienteDAO();
		Cliente cliente = clienteDAO.buscar(1L);		
		
		VendaDAO vendaDAO = new VendaDAO();		
	    Venda venda = vendaDAO.buscar(2L);	    
	    
	    if(venda == null) {
	    	System.out.println("Nenhum registro encontrado.");
	    }else {
	    	System.out.println("Venda editada: " + venda.getFuncionario() + " - " + venda.getCliente());
	    	
	    	
	    	venda.setCliente(cliente);
			venda.setFuncionario(funcionario);
			venda.setHorario(new Date());
			venda.setValorTotal(new BigDecimal("748.90"));
			vendaDAO.editar(venda);	
	    }
	    		
	}
	
	
	
	
	
	
	
	
	
	
}
