package br.com.drogaria.bean;

import java.io.Serializable;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.drogaria.externalUtil.Modo;

public abstract class GenericController implements Serializable{

	private static final long serialVersionUID = -6752771303488051335L;
	
	protected final static String KEY_ERRO = "ERRO:";
	protected final static String KEY_MENSAGEM_DEFAULT ="message.default";	
	protected final static String KEY_MENSAGEM_GENERICA = "message.generica";
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(GenericController.class);
	
	private HttpSessionUtil session;
	//private Credenciais credenciais;
	private Modo modo;
	//private Boolean exibirGrid = Boolean.FALSE;
	
//	public Boolean renderedButton(DeaGrupoSentinela... grupoSentinela) {
//		try {
//			
//			if(grupoSentinela != null) {
//				ArrayList<String> listarGrupoSentinela = new ArrayList<String>();
//				
//				for(DeaGrupoSentinela grupo : grupoSentinela) {
//					listarGrupoSentinela.add(grupo.getNomeGrupoSentinela());	
//				}
//				
//				return credenciais.hasRole(listaGrupoSentinela);
//			}
//			
//		} catch (Exception e) {
//			log.error(KEY_ERRO, e);
//		}
//	}
	
//	public Boolean disabledButton(DeaGrupoSentinela... grupoSentinela) {
//		try {
//		
//			if(grupoSentinela != null) {
//				ArrayList<String> listarGrupoSentinela = new ArrayList<String>();
//				
//				for(DeaGrupoSentinela grupo : grupoSentinela) {
//					listarGrupoSentinela.add(grupo.getNomeGrupoSentinela());	
//				}
//				
//				return !credenciais.hasRole(listaGrupoSentinela);
//			}
//		
//		} catch (Exception e) {
//			log.error(KEY_ERRO, e);
//		}
//	}	
	
	public HttpSessionUtil getSession() {
		return session;
	}
	
	@Inject
	public void setSession(HttpSessionUtil session) {
		this.session = session;
	}
	
//	getCredenciais getCredenciais() {
//		this.credenciais = credenciais;
//	}	
	
	public Modo getModo() {
		return modo;
	}	
	
	public Boolean isExibir() {
		return Modo.EXIBIR == getModo();
	}
	
	public Boolean isEditar() {
		return Modo.EDITAR == getModo();
	}
	
	public Boolean isIncluir() {
		return Modo.INCLUIR == getModo();
	}
}
