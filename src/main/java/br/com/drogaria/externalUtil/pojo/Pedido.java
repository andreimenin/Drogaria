package br.com.drogaria.externalUtil.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {

	private static final long serialVersionUID = -5562977753635651357L;

	private Cliente cliente;
	private List<ItemPedido> listaItensPedido;
	private Date data;
	private String situacao;
	private String identificacao;

	// Métodos getters e setters omitidos
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedido> getListaItensPedido() {
		return listaItensPedido;
	}

	public void setListaItensPedido(List<ItemPedido> listaItensPedido) {
		this.listaItensPedido = listaItensPedido;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}	
	
	public String getIdentificacao() {
		return identificacao;
	}
	
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
}
