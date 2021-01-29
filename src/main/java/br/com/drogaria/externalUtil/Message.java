package br.com.drogaria.externalUtil;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {
	
	private static ResourceBundle messageResources;
	private static String defaultMessage;
	private static final Logger log = LoggerFactory.getLogger(Message.class);
	
	
	static {
		
		try {
			if(FacesContext.getCurrentInstance() == null) {
				messageResources = ResourceBundle.getBundle("ApplicationMessages");			
			}
			else {
				messageResources = FacesContext.getCurrentInstance().getApplication().getResourceBundle(FacesContext.getCurrentInstance(), "appMessages");
			}
		} catch (Exception e) {
			log.error("Não foi possível acessar arquivo de properties com as mensagens do sistema.", e);
			messageResources = null;
		}
		
		//carrega msg padrão
		defaultMessage = getMessage("message.default");
		if(defaultMessage == null || "".equals(defaultMessage)) {
			defaultMessage = "Ocorreu um erro inesperado. Contacte o administrador do sistema.";
		}
	}
		
		public static void setMessage(String msgKey, String[] args) {
			String mensagem = Message.getMessage(msgKey, args);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		public static void setMessage(ApplicationException ae) {
			String mensagemDetalhada = null;
			if(ae.getRootCause() != null) {
				mensagemDetalhada = ae.getRootCause().toString();
			}
			FacesMessage message = new FacesMessage(ae.getSeverity(), ae.getMessage(), mensagemDetalhada);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		public static String getMessage(String key) {
			
			String msg = null;
			
			try {
				msg = messageResources.getString(key);
			} catch (MissingResourceException mre) {
				msg = defaultMessage;
			}
			catch (Exception e) {
				log.error("Não foi possível acessar arquivo de properties", e);
				messageResources = null;
				msg = defaultMessage;
			}
					
			return (msg == null ? "" : msg);						
		}
		
		public static String getMessage(String key, String[] args) {
			String msg = getMessage(key);
			if("".equals(msg)) {
				return "";
			}
			MessageFormat mf = new MessageFormat(msg);
			msg = mf.format(args);
			return (msg!=null?msg:"");
		}	
		
	}