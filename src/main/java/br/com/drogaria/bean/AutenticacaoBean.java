package br.com.drogaria.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.drogaria.dao.UsuarioDAO;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class AutenticacaoBean implements Serializable {

	private Usuario usuario;
	private Usuario usuarioLogado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa()); // instanciando a pessoa
	}

	public void autenticar() {

		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());
			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou SENHA incorretos.");
				return; // para evitar o redirecionamento para a página principal
			}

			Faces.redirect("./pages/principal.xhtml"); // passando a url
			System.out.println(usuarioLogado.getTipoUsuario());
		} catch (IOException erro) {
			Messages.addGlobalError(erro.getMessage());
		}

	}

	public boolean temPermissoes(List<String> permissoes) {

		// for(int i =0; i<permissoes.size(); i++){
		//
		// if(usuarioLogado.getTipoUsuario().toString().equals(permissoes.get(i)) ) {
		// System.out.println(usuarioLogado.getTipoUsuario().toString());
		// return true;
		// }
		// }

		for (String permissao : permissoes) {

			if (usuarioLogado.getTipoUsuario().toString().equals(permissao)) {
				System.out.println(usuarioLogado.getTipoUsuario().toString());
				return true;
			}
		}

		return false;
	}

	public void deslogar() {

		try {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

			Faces.redirect("./pages/autenticacao.xhtml"); // <--- navegar para sua tela de login }
		} catch (Exception erro) {
			Messages.addGlobalError(erro.getMessage());
		}

	}

}