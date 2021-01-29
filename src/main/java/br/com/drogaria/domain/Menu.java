package br.com.drogaria.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@SuppressWarnings("serial")
public class Menu extends GenericDomain{

	
	@Column(nullable = false)
	private String rotulo;
	
	
	@Column(nullable = true, length = 50)
	private String icone;
	
	@Column(nullable = true, length = 50)
	private String rendered;
	
	@Column(nullable = true, length = 50)
	private String action;	
	
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName = "codigo")
	@Fetch(FetchMode.SUBSELECT) //serve para falar que usa cada subitem com um subselect
	private List<Menu> itensDoMenu;
	
	
	@Column(length = 50, nullable = true)
	private String caminho;
	
	
	public String getRotulo() {
		return rotulo;
	}
	
	public void setRotulo(String rotulo) {
		this.rotulo = rotulo;
	}
	
	public List<Menu> getItensDoMenu() {
		return itensDoMenu;
	}
	
	public void setItensDoMenu(List<Menu> itensDoMenu) {
		this.itensDoMenu = itensDoMenu;
	}
	
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public String getIcone() {
		return icone;
	}

	public void setIcone(String icone) {
		this.icone = icone;
	}

	public String getRendered() {
		return rendered;
	}

	public void setRendered(String rendered) {
		this.rendered = rendered;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	////////////////////////////////////////ADICIONADOS FORA DAS VIDEO-AULAS
	
	
	
	
	
	
	
	
	
}
