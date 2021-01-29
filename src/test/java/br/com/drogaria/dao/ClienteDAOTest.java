package br.com.drogaria.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Pessoa;

public class ClienteDAOTest {

	//@Ignore
	@Test
	public void salvar() throws ParseException {

		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		Pessoa pessoa = new Pessoa();
		PessoaDAO pessoaDAO = new PessoaDAO();

		pessoa = pessoaDAO.buscar(2L);

		if (pessoa == null) {
			System.out.println("Pessoa não encontrada.");
		} else {
			cliente.setDataCadastro(new SimpleDateFormat("dd/MM/YYY").parse("10/07/1993"));
			cliente.setLiberado(true);
			cliente.setPessoa(pessoa);
			clienteDAO.salvar(cliente);
		}
	}

	@Ignore
	@Test
	public void listar() {

		ClienteDAO clienteDAO = new ClienteDAO();

		List<Cliente> resultado = clienteDAO.listar();

		for (Cliente cliente : resultado) {

			System.out.println(cliente.getPessoa().getNome() + " - " + cliente.getDataCadastro());

		}

	}

	@Ignore
	@Test
	public void buscar() {

		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		cliente = clienteDAO.buscar(1L);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
		} else {
			System.out.println(cliente.getPessoa().getNome() + " - " + cliente.getDataCadastro());
		}

	}

	@Ignore
	@Test
	public void excluir() {

		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		cliente = clienteDAO.buscar(1L);

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
		} else {
			clienteDAO.excluir(cliente);
			System.out.println("Cliente excluído" + cliente.getPessoa().getNome() + " - " + cliente.getDataCadastro());
		}

	}

	@Ignore
	@Test
	public void editar() {

		Cliente cliente = new Cliente();
		ClienteDAO clienteDAO = new ClienteDAO();

		cliente = clienteDAO.buscar(1L);

		Pessoa pessoa = new Pessoa();

		if (cliente == null) {
			System.out.println("Cliente não encontrado.");
		} else {
			cliente.setDataCadastro(new Date());
			cliente.setLiberado(true);
			cliente.setPessoa(pessoa);
			clienteDAO.editar(cliente);
			System.out.println(cliente.getPessoa().getNome() + " - " + cliente.getDataCadastro());
		}

	}

}
