package br.com.exemplo;

import java.io.Serializable;

public class ItemPedido implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2542520507539200085L;
	private Integer numeroItem;  
    private Produto produto; 
    private Double quantidade;  
    private Double valorTotalItem;
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
	public Double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValorTotalItem() {
		return valorTotalItem;
	}
	public void setValorTotalItem(Double valorTotalItem) {
		this.valorTotalItem = valorTotalItem;
	}    
  }
