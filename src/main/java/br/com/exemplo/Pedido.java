package br.com.exemplo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Pedido implements Serializable{

	private static final long serialVersionUID = -5562977753635651357L;
	 
	    private Cliente cliente;
	    private List<ItemPedido> listaItensPedido;
	    private Calendar data;
	    private String situacao;
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
		public Calendar getData() {
			return data;
		}
		public void setData(Calendar data) {
			this.data = data;
		}
		public String getSituacao() {
			return situacao;
		}
		public void setSituacao(String situacao) {
			this.situacao = situacao;
		}
}
