package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.Pessoa;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class FuncionarioBean implements Serializable {

	private Funcionario funcionario;

	private List<Funcionario> funcionarios;
	
	private List<Pessoa> pessoas;
	

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
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

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar("dataAdmissao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.merge(funcionario);				
			
			Messages.addGlobalInfo("Funcionario salvo com sucesso: " + funcionario.getPessoa().getNome());
			
			funcionario = new Funcionario();
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar();//resetando a escolha no combobox
			funcionarios = funcionarioDAO.listar("dataAdmissao");// atualizando a listagem após um inclusão
			
			
																											
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um funcionario.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		
		try {
			funcionario = new Funcionario();
			
			PessoaDAO pessoaDAO = new PessoaDAO(); //carregando do banco de dados
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo funcionario.");
		}
		
		
		
	}

	public void excluir(ActionEvent evento) {

		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioDAO.excluir(funcionario);
			Messages.addGlobalInfo("Funcionario removido: " + funcionario.getPessoa().getNome());

			funcionarios = funcionarioDAO.listar("dataAdmissao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um funcionario.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			funcionario = (Funcionario) evento.getComponent().getAttributes().get("funcionarioSelecionado");
						
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar();//resetando a escolha no combobox			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um funcionario.");
			erro.printStackTrace();
		}

	}

}
