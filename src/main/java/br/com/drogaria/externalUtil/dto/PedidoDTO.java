package br.com.drogaria.externalUtil.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.drogaria.externalUtil.Util;

public class PedidoDTO implements Serializable{

	private static final long serialVersionUID = -6980541982267921526L;
	
	 //private Cliente cliente;
	 //Dados do Cliente
	 private String nome;
     private String endereco;
     private String uf;
     private String documento;
     private String identificacao;
	
     //dados do Pedido
	 private List<ItemPedidoDTO> listaItensPedidoDTO;
	 private String data;
	 private String situacao;	 
	 	 
	public List<ItemPedidoDTO> getListaItensPedidoDTO() {	
		if(Util.isNull(this.listaItensPedidoDTO)) {
			setListaItensPedidoDTO(new ArrayList<ItemPedidoDTO>());
		}
	
		return listaItensPedidoDTO;
	}
	public void setListaItensPedidoDTO(List<ItemPedidoDTO> listaItensPedidoDTO) {
		this.listaItensPedidoDTO = listaItensPedidoDTO;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}	
	public String getIdentificacao() {
		return identificacao;
	}
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
}

