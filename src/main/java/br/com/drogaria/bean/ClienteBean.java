package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Pessoa;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class ClienteBean implements Serializable {

	private Cliente cliente;

	private List<Cliente> clientes;
	
	private List<Pessoa> pessoas;
	

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}	
	

	@PostConstruct // como se fosse um construtor, será automaticamente chamado o método listar
	public void listar() {
		try {

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar("dataCadastro");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);				
			
			Messages.addGlobalInfo("Cliente salvo com sucesso: " + cliente.getPessoa().getNome());
			
			cliente = new Cliente();
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar("nome");//resetando a escolha no combobox
			clientes = clienteDAO.listar("dataCadastro");// atualizando a listagem após um inclusão
			
			
																											
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um cliente.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		
		try {
			cliente = new Cliente();
			
			PessoaDAO pessoaDAO = new PessoaDAO(); //carregando do banco de dados
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo cliente.");
		}
		
		
		
	}

	public void excluir(ActionEvent evento) {

		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);
			Messages.addGlobalInfo("Cliente removido: " + cliente.getPessoa().getNome());

			clientes = clienteDAO.listar("dataCadastro");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um cliente.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
						
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar("nome");//resetando a escolha no combobox			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um cliente.");
			erro.printStackTrace();
		}

	}

}
