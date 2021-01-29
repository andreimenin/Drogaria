package br.com.drogaria.dao;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Pessoa;

public class FuncionarioDAOTest {

	@Ignore
	@Test
	public void salvar() {
		
		Funcionario Funcionario = new Funcionario();
		FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
	
		Pessoa pessoa = new Pessoa();
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		pessoa = pessoaDAO.buscar(1L);		
		
		if(pessoa == null) {
			System.out.println("Pessoa não encontrada.");
		}
		else {			
			Funcionario.setDataAdmissao(new Date());
			Funcionario.setCarteiraTrabalho("196417846");
			Funcionario.setPessoa(pessoa);		
			FuncionarioDAO.salvar(Funcionario);			
		}		
	}
	
	
	@Ignore
	@Test
	public void listar() {
		
		FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
		
		List<Funcionario> resultado = FuncionarioDAO.listar();
		
		for(Funcionario Funcionario : resultado) {
			
			System.out.println(Funcionario.getPessoa().getNome() + " - " + Funcionario.getDataAdmissao());
			
		}	
		
	}
	
	
	@Ignore
	@Test
	public void buscar() {
		
		Funcionario Funcionario = new Funcionario();
		FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
		
		Funcionario = FuncionarioDAO.buscar(1L);
		
		if(Funcionario == null) {
			System.out.println("Funcionario não encontrado.");
		}else {
			System.out.println(Funcionario.getPessoa().getNome() + " - " + Funcionario.getDataAdmissao());
		}		
		
	}
	
@Ignore
@Test
public void excluir() {
		
		Funcionario Funcionario = new Funcionario();
		FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
		
		Funcionario = FuncionarioDAO.buscar(1L);
		
		if(Funcionario == null) {
			System.out.println("Funcionario não encontrado.");
		}else {
			FuncionarioDAO.excluir(Funcionario);
			System.out.println("Funcionario excluído" + Funcionario.getPessoa().getNome() + " - " + Funcionario.getDataAdmissao());
		}		
		
	}
	
	
@Ignore
@Test
public void editar() {
	
	Funcionario Funcionario = new Funcionario();
	FuncionarioDAO FuncionarioDAO = new FuncionarioDAO();
	
	Funcionario = FuncionarioDAO.buscar(1L);	
	
	Pessoa pessoa = new Pessoa();	
	
	if(Funcionario == null) {
		System.out.println("Funcionario não encontrado.");
	}else {
		Funcionario.setDataAdmissao(new Date());
		Funcionario.setCarteiraTrabalho("196417846");
		Funcionario.setPessoa(pessoa);		
		FuncionarioDAO.editar(Funcionario);	
		System.out.println(Funcionario.getPessoa().getNome() + " - " + Funcionario.getDataAdmissao());
	}		
	
}
	
	
	
	
	
	
	
}
