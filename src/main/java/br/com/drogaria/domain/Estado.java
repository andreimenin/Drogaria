package br.com.drogaria.domain;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;





@SuppressWarnings("serial")
@Entity //serve para dizer que essa classe é uma entidade do hibernate
public class Estado extends GenericDomain{
	
	@Column(length = 50, nullable = false)
	private String nome;	
	
	@Column(length = 2, nullable = false)
	private String sigla;
		
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "estado") //LAZY PARA A LISTAGEM FICAR CERTA E NÃO REPETIR ESTADOS
	private List<Cidade> cidades;
	
	
	public List<Cidade> getCidades() {
		return cidades;
	}
	
	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	
}
