package br.com.drogaria.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Pessoa;

public class PessoaDAOTest {

	@Ignore
	@Test	
	public void salvar() {
		
		Pessoa pessoa = new Pessoa();
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = new Cidade();
		cidade = cidadeDAO.buscar(1L);
		
		if(cidade == null) {
			System.out.println("Cidade não encontrada");
		}
		else {
			pessoa.setBairro("Bairro");
			pessoa.setCelular("997847867");
			pessoa.setCep("18876-379");
			pessoa.setCidade(cidade);
			pessoa.setComplemento("Casa");
			pessoa.setCpf("748-878-876-47");
			pessoa.setEmail("andeurheir@gmail.com");
			pessoa.setNome("Nome");
			pessoa.setNumero("647");
			pessoa.setRua("Rua que nao sei");
			pessoa.setTelefone("74917017");
			
			pessoaDAO.salvar(pessoa);	
		}		
		
	}
	
	@Ignore
	@Test
	public void listar() {
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		List<Pessoa> resultado = pessoaDAO.listar();
		
		for(Pessoa pessoa : resultado) {
			
			System.out.println(pessoa.getNome() + " - " + pessoa.getCpf());
			
		}		
	}
	
	@Ignore
	@Test
	public void buscar() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		
		pessoa = pessoaDAO.buscar(1L);
		
		
		if(pessoa == null) {
			System.out.println("Pessoa não encontrada");
		}
		else {
			System.out.println(pessoa.getNome() + " - " + pessoa.getCpf());
		}		
	}
	
	
	@Ignore
	@Test
	public void excluir() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		
		pessoa = pessoaDAO.buscar(1L);
		
		
		if(pessoa == null) {
			System.out.println("Pessoa não encontrada");
		}
		else {
			System.out.println("Pessoa excluída: "  + pessoa.getNome() + " - " + pessoa.getCpf());
		}		
	}
	
	
	
	//@Ignore
	@Test
	public void editar() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = new Pessoa();
		
		pessoa = pessoaDAO.buscar(1L);
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		Cidade cidade = new Cidade();
		cidade = cidadeDAO.buscar(1L);
		
		
		
		if((pessoa == null)||(cidade == null)) {
			System.out.println("Pessoa ou cidade não encontrada");
		}
		else {
			pessoa.setBairro("Bairro");
			pessoa.setCelular("997847867");
			pessoa.setCep("18876-379");
			pessoa.setCidade(cidade);
			pessoa.setComplemento("Casa");
			pessoa.setCpf("748-878-876-47");
			pessoa.setEmail("andeurheir@gmail.com");
			pessoa.setNome("Andrei");
			pessoa.setNumero("647");
			pessoa.setRua("Rua que nao sei");
			pessoa.setTelefone("74917017");
			
			pessoaDAO.editar(pessoa);		
			
			System.out.println("Pessoa editada: "  + pessoa.getNome() + " - " + pessoa.getCpf());
		}		
	}
	
	
	
	
	
	
	
	
}
