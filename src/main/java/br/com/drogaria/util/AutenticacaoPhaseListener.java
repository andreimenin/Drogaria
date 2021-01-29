package br.com.drogaria.util;

import java.io.IOException;
import java.util.ArrayList;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.drogaria.bean.AutenticacaoBean;
import br.com.drogaria.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoPhaseListener implements PhaseListener {

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("Depois da fase: " + event.getPhaseId());

		String paginaAtual = Faces.getViewId() ;
		System.out.println("Página atual: " + paginaAtual);

		boolean ehPaginaDeAutenticacao = paginaAtual.contains("autenticacao.xhtml");

		
		try {
			
			
			
			if (!ehPaginaDeAutenticacao)// se não for a página de autenticação, quer dizer que está numa página privada
			{

				AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
				System.out.println("AutenticacaoBean:  " + autenticacaoBean);

				/// forçando o usuário para ir para a página de autenticação, caso o usuário
				/// tente acessar uma página restrita sem logar
				if (autenticacaoBean == null) {
					Faces.redirect("./pages/autenticacao.xhtml");
					return;
				}

				Usuario usuario = autenticacaoBean.getUsuarioLogado();

				if (usuario == null) { // se o usuário é nulo, quer dizer que não conseguiu logar
					Faces.redirect("./pages/autenticacao.xhtml");
					return; //não prossegue
				}

				
				//COMANDOS QUE NÃO TÊM NAS VIDEO-AULAS
				ArrayList<String> paginas = new ArrayList<>();

				paginas.add("/pages/cidades.xhtml");
				paginas.add("/pages/clientes.xhtml");
				paginas.add("/pages/estados.xhtml");
				paginas.add("/pages/fabricantes.xhtml");
				paginas.add("/pages/funcionarios.xhtml");
				paginas.add("/pages/produtos.xhtml");
				paginas.add("/pages/pessoas.xhtml");
				paginas.add("/pages/usuarios.xhtml");		

				
				for(int i = 0 ; i< paginas.size() ; i++) {
					if (usuario.getTipoUsuario().toString().equals("Balconista") && paginaAtual.equals(paginas.get(i))) {
						
						Faces.redirect("./pages/autenticacao.xhtml");
						return; /// ********************CONDIÇÃO ADICIONADA PARA RESTRINGIR O ACESSO À PÁGINAS
//								/// RESTRITAS
					}
				}//COMANDOS QUE NÃO TÊM NAS VIDEO-AULAS		

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Antes da fase: " + event.getPhaseId());

	}

	@Override
	public PhaseId getPhaseId() {
		// TODO Auto-generated method stub
		return PhaseId.RESTORE_VIEW;
	}

}
