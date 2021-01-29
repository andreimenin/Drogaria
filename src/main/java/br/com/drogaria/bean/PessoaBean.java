package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.dao.CidadeDAO;
import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;


//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class PessoaBean implements Serializable {
	private Estado estadoSelecionado;

	private Pessoa pessoa;

	private List<Pessoa> pessoas;

	private List<Estado> estados;

	private List<Cidade> cidades;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	@PostConstruct // como se fosse um construtor, será automaticamente chamado o método listar
	public void listar() {
		try {

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);

			Messages.addGlobalInfo("Pessoa salvo com sucesso: " + pessoa.getNome());

			pessoa = new Pessoa();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");// resetando a escolha no combobox
			pessoas = pessoaDAO.listar("nome");// atualizando a listagem após uma inclusão

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma pessoa.");
			erro.printStackTrace();
		}

	}

	public void novo() {

		try {
			pessoa = new Pessoa();
			estadoSelecionado = new Estado();

			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://localhost:8080/drogaria/rest/estado"); // caminho do servidor
																								// rest
			String json = caminho.request().get(String.class);

			Gson gson = new Gson();
			Estado[] vetor = gson.fromJson(json, Estado[].class);

			estados = Arrays.asList(vetor);

			// Alteração da video aula 304
			// EstadoDAO estadoDAO = new EstadoDAO(); // carregando do banco de dados
			// estados = estadoDAO.listar("nome");

			// cidades = new ArrayList<Cidade>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um nova pessoa.");
		}

	}

	public void excluir(ActionEvent evento) {

		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.excluir(pessoa);
			Messages.addGlobalInfo("Pessoa removido:" + pessoa.getNome());

			pessoas = pessoaDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um pessoa.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionado");

			estadoSelecionado = pessoa.getCidade().getEstado();

			EstadoDAO estadoDAO = new EstadoDAO(); // carregando do banco de dados
			estados = estadoDAO.listar("nome");

			// método popular();
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.buscarPorEstado(estadoSelecionado.getCodigo());

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um pessoa.");
			erro.printStackTrace();
		}

	}

	public void popular() {

		try {
			if (estadoSelecionado != null) {

				// Alteração da video aula 304
				// CidadeDAO cidadeDAO = new CidadeDAO();
				// cidades = cidadeDAO.buscarPorEstado(estadoSelecionado.getCodigo());

				Client cliente = ClientBuilder.newClient();
				WebTarget caminho = cliente.target("http://localhost:8080/drogaria/rest/cidade/{estadoCodigo}")
						.resolveTemplate("estadoCodigo", estadoSelecionado.getCodigo()); // caminho do servidor
				// rest
				String json = caminho.request().get(String.class);

				Gson gson = new Gson();
				Cidade[] vetor = gson.fromJson(json, Cidade[].class);

				cidades = Arrays.asList(vetor);

				System.out.println("Estado selecionado: " + estadoSelecionado.getNome());
				System.out.println("Total de cidades: " + cidades.size());
			} else {
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			System.out.println("Erro ao tentar listar as cidades.");
			erro.printStackTrace();
		}

	}

}
