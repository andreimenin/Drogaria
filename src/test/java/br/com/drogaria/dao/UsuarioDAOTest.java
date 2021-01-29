package br.com.drogaria.dao;

import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.drogaria.domain.Usuario;
import br.com.drogaria.enumeracao.TipoUsuario;
import br.com.drogaria.domain.Pessoa;

public class UsuarioDAOTest {

	@Ignore
	@Test
	public void salvar() {

		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		Pessoa pessoa = new Pessoa();
		PessoaDAO pessoaDAO = new PessoaDAO();

		pessoa = pessoaDAO.buscar(4L);

		if (pessoa == null) {
			System.out.println("Pessoa não encontrada.");
		} else {

//			usuario.setTipo('G');
			usuario.setTipoUsuario(TipoUsuario.BALCONISTA);
			usuario.setPessoa(pessoa);
			usuario.setSenhaSemCriptografia("44444444");

			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());

			usuarioDAO.salvar(usuario);
		}
	}

	@Ignore
	@Test
	public void listar() {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		List<Usuario> resultado = usuarioDAO.listar();

		for (Usuario usuario : resultado) {

			System.out.println(usuario.getPessoa().getNome() + " - " + usuario.getSenha());

		}

	}

	@Ignore
	@Test
	public void buscar() {

		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		usuario = usuarioDAO.buscar(1L);

		if (usuario == null) {
			System.out.println("Usuario não encontrado.");
		} else {
			System.out.println(usuario.getPessoa().getNome() + " - " + usuario.getSenha());
		}

	}

	@Ignore
	@Test
	public void excluir() {

		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		usuario = usuarioDAO.buscar(1L);

		if (usuario == null) {
			System.out.println("Usuario não encontrado.");
		} else {
			usuarioDAO.excluir(usuario);
			System.out.println("Usuario excluído" + usuario.getPessoa().getNome() + " - " + usuario.getSenha());
		}

	}

	@Ignore
	@Test
	public void editar() {

		Usuario usuario = new Usuario();
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		usuario = usuarioDAO.buscar(2L);

		Pessoa pessoa = new Pessoa();

		if (usuario == null) {
			System.out.println("Usuario não encontrado.");
		} else {

//			usuario.setTipo('G');
			usuario.setPessoa(pessoa);
			usuario.setSenhaSemCriptografia("111111");

			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());
			usuarioDAO.editar(usuario);
			System.out.println(usuario.getPessoa().getNome() + " - " + usuario.getSenha());
		}

	}

	@Ignore
	@Test
	public void autenticar() {
		try {
			String cpf = "111.111.111-11";
			String senha = "11111111";

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = usuarioDAO.autenticar(cpf, senha);

			if(usuario == null) {
				System.out.println("CPF ou Senha inválidos.");
			}
			else {
				System.out.println(usuario);	
			}
			
			
		} catch (RuntimeException erro) {
			erro.printStackTrace();
		}

	}

}
