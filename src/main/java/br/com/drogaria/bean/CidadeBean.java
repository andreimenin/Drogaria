package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.CidadeDAO;
import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class CidadeBean implements Serializable {

	private Cidade cidade;

	private List<Cidade> cidades;
	
	private List<Estado> estados;
	

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public List<Estado> getEstados() {
		return estados;
	}
	
	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}	
	

	@PostConstruct // como se fosse um construtor, será automaticamente chamado o método listar
	public void listar() {
		try {

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidades = cidadeDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.merge(cidade);				
			
			Messages.addGlobalInfo("Cidade salvo com sucesso: " + cidade.getNome() + " - " + cidade.getEstado().getSigla());
			
			cidade = new Cidade();
			EstadoDAO estadoDAO = new EstadoDAO();				
			estados = estadoDAO.listar("nome");//resetando a escolha no combobox
			cidades = cidadeDAO.listar("nome");// atualizando a listagem após uma inclusão
			
			
																											
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma cidade.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		
		try {
			cidade = new Cidade();
			
			EstadoDAO estadoDAO = new EstadoDAO(); //carregando do banco de dados
			estados = estadoDAO.listar("nome");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma nova cidade.");
		}
		
		
		
	}

	public void excluir(ActionEvent evento) {

		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");

			CidadeDAO cidadeDAO = new CidadeDAO();
			cidadeDAO.excluir(cidade);
			Messages.addGlobalInfo("Cidade removida:" + cidade.getNome());

			cidades = cidadeDAO.listar("nome");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir uma cidade.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			cidade = (Cidade) evento.getComponent().getAttributes().get("cidadeSelecionado");
						
			EstadoDAO estadoDAO = new EstadoDAO();				
			estados = estadoDAO.listar("nome");//resetando a escolha no combobox			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar uma cidade.");
			erro.printStackTrace();
		}

	}

}
