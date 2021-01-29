package br.com.drogaria.externalUtil.dto;

import java.io.Serializable;

import br.com.drogaria.externalUtil.Util;

public class ItemPedidoDTO implements Serializable{

	private static final long serialVersionUID = -6842052585349110970L;

	private String numeroItem; 
	
    //private Produto produto;
	//Dados do Produto
	private String descricao; 
    private String valor;	
	
    private String quantidade;  
    private String valorTotalItem;
	public String getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(String numeroItem) {
		this.numeroItem = numeroItem;
	}
	public String getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}
	public String getValorTotalItem() {
		return valorTotalItem;
	}
	public void setValorTotalItem(String valorTotalItem) {
		
		String numeroFormatado;
		
		numeroFormatado = Util.formatarNumeroDecimal(Double.parseDouble(valorTotalItem), 2);
				
		this.valorTotalItem = numeroFormatado;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		
		String numeroFormatado;
			
		numeroFormatado = Util.formatarNumeroDecimal(Double.parseDouble(valor), 2);	
		
		this.valor = numeroFormatado;
	}		
}
