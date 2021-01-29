package br.com.drogaria.externalUtil;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

/**
 * 
 * @author Andrei
 *
 *
 *Classe utilizada para encapsular exceções de aplicação.
 */

@javax.ejb.ApplicationException(rollback=true)
public class ApplicationException extends Exception{

	/** Serial ID **/
	private static final long serialVersionUID = -5076809288466272210L;

	/** Guarda excecao ocorrida **/
	private Throwable rootCause = null;	
	
	private Severity severity = FacesMessage.SEVERITY_ERROR;	
	
	/**
	 * Constrói e recupera a mensagem a partir da chave informada
	 * 
	 * @param messageKeyLoc chave para busca de mensagem
	 * 
	 */
	public ApplicationException(String messageKeyLoc) {
		super(Message.getMessage(messageKeyLoc));
	}
	
	public ApplicationException(String messageKeyLoc, String[] parameters) {
		super(Message.getMessage(messageKeyLoc, parameters));
	}
	
	public ApplicationException(Throwable causa) {
		super(Message.getMessage("message.default"));
		setRootCause(causa);
	}
	
	public ApplicationException(String messageKeyLoc, Throwable causa, Severity severity) {
		super(Message.getMessage(messageKeyLoc));
		setRootCause(causa);
		this.severity = severity;
	}
	
	public ApplicationException(String messageKeyLoc, String[] parameters, Throwable rootCause) {
		super(Message.getMessage(messageKeyLoc, parameters));
		setRootCause(rootCause);
	}
	
	public ApplicationException(String messageKeyLoc, String[] parameters, Throwable causa, Severity severity) {
		super(Message.getMessage(messageKeyLoc, parameters));
		setRootCause(causa);
		this.severity = severity;
	}
	
	public ApplicationException(String messageKeyLoc, String[] parameters, Severity severity) {
		super(Message.getMessage(messageKeyLoc, parameters));		
		this.severity = severity;
	}	
	
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}
	
	public ApplicationException(String messageKeyLoc, Throwable causa) {		
		setRootCause(causa);		
	}
	
	public Throwable getRootCause() {
		return rootCause;
	}
	
	public Severity getSeverity() {
		return severity;
	}	
}
