package br.com.drogaria.bean;


import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class ProdutoBean3 implements Serializable{

	private List<Produto> produtos;
	
	private ProdutoDAO produtoDAO;
	
	private Produto produto;
	private List<Fabricante> fabricantes;
	
	private FabricanteDAO fabricanteDAO;
	
	private Long codigoProduto;
	
	
	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public FabricanteDAO getFabricanteDAO() {
		return fabricanteDAO;
	}

	public void setFabricanteDAO(FabricanteDAO fabricanteDAO) {
		this.fabricanteDAO = fabricanteDAO;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public Long getCodigoProduto() {
		return codigoProduto;
	}
	
	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	
	
	@PostConstruct
	public void iniciar() {
		fabricanteDAO = new FabricanteDAO();
		produtoDAO = new ProdutoDAO();
	}
	
	//1
	public void listar() {
		try {			
			produtos = produtoDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}
	
	//2
	public void carregarEdicao() {
		try {
			produto = produtoDAO.buscar(codigoProduto);			
			
			System.out.println("Código do produto: " + codigoProduto);
			fabricantes = fabricanteDAO.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao carregar os dados para edição.");
		}
	}
	
	//3
	public void salvar() {
		
		
		
		try {
		    produtoDAO.merge(produto);

			

			Messages.addGlobalInfo("Produto salvo com sucesso: " + produto.getDescricao());
			
			System.out.println("Produto após editar: " + codigoProduto);

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um produto.");
			erro.printStackTrace();
		}

	}
	
}
