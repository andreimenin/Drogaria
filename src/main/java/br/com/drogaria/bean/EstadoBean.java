package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.externalUtil.Util;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class EstadoBean implements Serializable {

	private Estado estado;

	private List<Estado> estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
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

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			Messages.addGlobalInfo("Estado salvo com sucesso: " + estado.getNome() + " - " + estado.getSigla());// substituindo
																												// as
																												// linhas
																												// anteriores
																												// com o
																												// OmniFaces
			estado = new Estado();
			estados = estadoDAO.listar();// atualizando a listagem após uma inclusão
			// String msg = "Programação web com java";
			// FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
			// msg);//
			// FacesContext contexto = FacesContext.getCurrentInstance(); // capturando o
			// contexto do JSF//
			// contexto.addMessage(null, mensagem);
			novo();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um estado.");
			erro.printStackTrace();
		}

	}

	public void novo() {
		estado = new Estado();
	}

	public void excluir(ActionEvent evento) throws Exception {

		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			if(Util.isNotNull(estado.getCidades())&&!estado.getCidades().isEmpty()) {				
				Messages.addGlobalWarn(estado.getNome() + " não pode ser removido.");				
			}
			else {
				EstadoDAO estadoDAO = new EstadoDAO();
				estadoDAO.excluir(estado);
				Messages.addGlobalInfo("Estado removido: " + estado.getNome());	
				estados = estadoDAO.listar();
			}
			
		}		
		
		catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um estado.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();			
		
			Messages.addGlobalInfo("Estado editado:" + estado.getNome());

			estados = estadoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um estado.");
			erro.printStackTrace();
		}

	}

}
