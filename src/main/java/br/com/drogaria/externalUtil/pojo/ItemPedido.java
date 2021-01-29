package br.com.drogaria.externalUtil.pojo;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2542520507539200085L;
	private Integer numeroItem;  
    private Produto produto; 
    private Integer quantidade;  
    private Double valorTotalItem;
    private String descricao;
    // Métodos getters e setters omitidos
	public Integer getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(Integer numeroItem) {
		this.numeroItem = numeroItem;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValorTotalItem() {
		return valorTotalItem;
	}
	public void setValorTotalItem(Double valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}  
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
  }
