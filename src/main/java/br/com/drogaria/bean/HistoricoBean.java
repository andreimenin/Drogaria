package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.HistoricoDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Historico;
import br.com.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable{

	private Produto produto;
	
	private Boolean exibePainelDeDados;//variável para manipular a exibição da div do panelGroup
	
	private Historico historico;
	
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
	
	public Historico getHistorico() {
		return historico;
	}
	
	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	
	
	@PostConstruct
	public void novo() {
		
	    historico = new Historico();
		
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
	
	public void salvar() {
		try {
			historico.setData(new Date()); //pegando o horário do servidor
			historico.setProduto(produto);
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.salvar(historico);
			
			Messages.addGlobalInfo("Histórico salvo com sucesso.");
			historico.setObservacoes(null);
			
			
		} catch (RuntimeException erro) {			
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Histórico.");
			erro.printStackTrace();
		}
	}
	
	
}
