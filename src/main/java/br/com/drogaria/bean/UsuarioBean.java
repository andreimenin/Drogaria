package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.drogaria.dao.UsuarioDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Usuario;
import br.com.drogaria.domain.Pessoa;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class UsuarioBean implements Serializable {

	private Usuario usuario;

	private List<Usuario> usuarios;
	
	private List<Pessoa> pessoas;
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarios = usuarioDAO.listar("tipoUsuario");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.merge(usuario);				
			
			Messages.addGlobalInfo(Faces.getResourceBundle("msg").getString("usuarioSalvo"));
			
			usuario = new Usuario();
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar("nome");//resetando a escolha no combobox
			usuarios = usuarioDAO.listar();// atualizando a listagem após um inclusão
			
			
																											
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um usuario.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		
		try {
			usuario = new Usuario();
			
			PessoaDAO pessoaDAO = new PessoaDAO(); //carregando do banco de dados
			pessoas = pessoaDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um novo usuario.");
		}
		
		
		
	}

	public void excluir(ActionEvent evento) {

		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);
			Messages.addGlobalInfo("Usuario removido: " + usuario.getPessoa().getNome());

			usuarios = usuarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um usuario.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
						
			PessoaDAO pessoaDAO = new PessoaDAO();				
			pessoas = pessoaDAO.listar("nome");//resetando a escolha no combobox			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um usuario.");
			erro.printStackTrace();
		}

	}

}
