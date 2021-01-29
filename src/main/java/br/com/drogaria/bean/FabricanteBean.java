package br.com.drogaria.bean;

import java.io.Serializable;
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

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.domain.Fabricante;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class FabricanteBean implements Serializable {

	private Fabricante fabricante;

	private List<Fabricante> fabricantes;

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	@PostConstruct // como se fosse um construtor, será automaticamente chamado o método listar
	public void listar() {
		try {
			// FabricanteDAO fabricanteDAO = new FabricanteDAO();
			// fabricantes = fabricanteDAO.listar();

			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://localhost:8080/drogaria/rest/fabricante"); // caminho do servidor
																									// rest
			String json = caminho.request().get(String.class);

			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

			fabricantes = Arrays.asList(vetor);

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {

			FabricanteDAO fabricanteDAO = new FabricanteDAO();

			fabricanteDAO.merge(fabricante);

			Messages.addGlobalInfo("Fabricante salvo com sucesso: " + fabricante.getDescricao());// substituindo
																									// as
																									// linhas
																									// anteriores
																									// com o
																									// OmniFaces
			fabricante = new Fabricante();
			fabricantes = fabricanteDAO.listar();// atualizando a listagem após uma inclusão

			// String msg = "Programação web com java";
			// FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
			// msg);//
			// FacesContext contexto = FacesContext.getCurrentInstance(); // capturando o
			// contexto do JSF//
			// contexto.addMessage(null, mensagem);

			novo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um fabricante.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		fabricante = new Fabricante();
	}

	public void excluir(ActionEvent evento) {

		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

			Client cliente = ClientBuilder.newClient();
			// método que serve para converter o "{codigo}" por um código representativo de
			// um objeto
			WebTarget caminho = cliente.target("http://127.0.0.1:8080/drogaria/rest/fabricante");
				
			
			WebTarget caminhoExcluir = caminho.path("{codigo}").resolveTemplate("codigo", fabricante.getCodigo())
					.resolveTemplate("{codigo}", fabricante.getCodigo());

			caminhoExcluir.request().delete();
			
			//atualizando a listagem de fabricantes
			String json = caminhoExcluir.request().get(String.class);
			
			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);

			fabricantes = Arrays.asList(vetor);
			
			// FabricanteDAO fabricanteDAO = new FabricanteDAO();
			// fabricanteDAO.excluir(fabricante);
			Messages.addGlobalInfo("Fabricante removido: " + fabricante.getDescricao());

			// fabricantes = fabricanteDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um fabricante.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

			FabricanteDAO fabricanteDAO = new FabricanteDAO();

			Messages.addGlobalInfo("Fabricante editado: " + fabricante.getDescricao());

			fabricantes = fabricanteDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um fabricante.");
			erro.printStackTrace();
		}

	}

}
