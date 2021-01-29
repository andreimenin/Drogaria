package br.com.drogaria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean2 implements Serializable{

	private Produto produto;
	
	private Boolean exibePainelDeDados;//variável para manipular a exibição da div do panelGroup
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Boolean getExibePainelDeDados() {
		return exibePainelDeDados;
	}
	
	public void setExibePainelDeDados(Boolean exibePainelDeDados) {
		this.exibePainelDeDados = exibePainelDeDados;
	}
	
	
	@PostConstruct
	public void novo() {
		produto = new Produto();
		exibePainelDeDados = false;
	}
	
	public void buscar() {
		try {
			
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());
			if(resultado == null) {
				exibePainelDeDados = false;
				Messages.addGlobalWarn("Não existe produto cadastrado com o código informado.");
			}else {
				exibePainelDeDados = true;
				produto = resultado;
			}
			
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar encontrar o produto.");
			erro.printStackTrace();
		}
	}
	
	
	
}
