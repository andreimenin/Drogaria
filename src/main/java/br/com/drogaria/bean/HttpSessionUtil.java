package br.com.drogaria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.drogaria.externalUtil.ApplicationException;

public class HttpSessionUtil implements Serializable{

	private static final long serialVersionUID = 4145444021456635798L;

	private static Logger log = LoggerFactory.getLogger(HttpSessionUtil.class);
	
	protected final static String KEY_ERRO = "ERRO:";
	protected final static String KEY_MENSAGEM_DEFAULT ="message.default";
	
	@PostConstruct
	public void init() {
		getSession();
	}
	
	public void adicionarAtributoNaSessao(String nomeDoAtributo, Object valorDoAtributo) throws ApplicationException{
		
		try {
			getSession().setAttribute(nomeDoAtributo, valorDoAtributo);
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()}, e);
		}		
	}
	
	public Object obterAtributoNaSessao(String nomeDoAtributo) {
		return getSession().getAttribute(nomeDoAtributo);
	}
	
	public void removerAtributoNaSessao(String nomeDoAtributo) {
		if(this.existeAtributoNaSessao(nomeDoAtributo)) {
			getSession().removeAttribute(nomeDoAtributo);
		}
	}
	
	public boolean existeAtributoNaSessao(String nomeDoAtributo) {
		return getSession().getAttribute(nomeDoAtributo) != null;
	}
	
	public HttpSession getSession() {
		try {
			return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		} catch (Exception e) {
			return null;
		}
	}
	
}
