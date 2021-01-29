package br.com.exemplo;

import java.io.Serializable;

public class Produto implements Serializable{ 
    /**
	 * 
	 */
	private static final long serialVersionUID = 5858966674450005990L;
	private Long codigo;
    private String descricao; 
    private Double valor;
    // Métodos getters e setters omitidos
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
  }
