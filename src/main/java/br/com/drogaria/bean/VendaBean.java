package br.com.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.drogaria.dao.ClienteDAO;
import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.dao.VendaDAO;
import br.com.drogaria.domain.Cliente;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;
import br.com.drogaria.externalUtil.Util;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class VendaBean implements Serializable {

	private List<Produto> produtos;

	private List<ItemVenda> itensDaVenda;

	private Venda venda;

	private List<Cliente> clientes;

	private List<Funcionario> funcionarios;

	private List<Venda> vendas;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensDaVenda() {
		return itensDaVenda;
	}

	public void setItensDaVenda(List<ItemVenda> itensDaVenda) {
		this.itensDaVenda = itensDaVenda;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Venda> getVendas() {
		return vendas;
	}

	public void setVendas(List<Venda> vendas) {
		this.vendas = vendas;
	}

//	@PostConstruct // como se fosse um construtor, será automaticamente chamado o
	// método listar
	public void novo() {
		try {

			venda = new Venda();
			venda.setValorTotal(new BigDecimal("0.00"));
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();

			itensDaVenda = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;

		for (int posicao = 0; posicao < itensDaVenda.size(); posicao++) {

			if (itensDaVenda.get(posicao).getProduto().equals(produto)) {

				achou = posicao;
			}
		}

		if (achou < 0) {

			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setValorParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(1L);

			itensDaVenda.add(itemVenda);

		} else {
			
			ItemVenda itemVenda = itensDaVenda.get(achou);
			
			if(!itemVenda.getQuantidade().equals(produto.getQuantidade())) {
				itemVenda.setQuantidade(itemVenda.getQuantidade() + 1L);
				itemVenda.setValorParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
			}			
		}

		calcular();
	}

	public void remover(ActionEvent evento) {

		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");
		int achou = -1;

		for (int posicao = 0; posicao < itensDaVenda.size(); posicao++) {

			if (itensDaVenda.get(posicao).getProduto().equals(itemVenda.getProduto()))
				;
			achou = posicao;

		}

		if (achou > -1) {

			itensDaVenda.remove(achou);

		}
		calcular();
	}

	public void calcular() {
		venda.setValorTotal(new BigDecimal("0.00")); // zerando para atualizar preço total

		for (int posicao = 0; posicao < itensDaVenda.size(); posicao++) {

			ItemVenda itemVenda = itensDaVenda.get(posicao); // capturando o item da venda corrente
			venda.setValorTotal(venda.getValorTotal().add(itemVenda.getValorParcial()));
		}

	}
	
	public void atualizaValorAjax(ItemVenda iv) {	
		
		for (int posicao = 0; posicao < itensDaVenda.size(); posicao++) {

			if (itensDaVenda.get(posicao).getProduto().equals(iv.getProduto())) {

				ItemVenda itemVenda = itensDaVenda.get(posicao);
				itemVenda.setValorParcial(itemVenda.getProduto().getPreco().multiply(new BigDecimal(iv.getQuantidade())));				
			}
		}
					
			
		calcular();		
	}

	public void finalizar() {

		try {

			venda.setHorario(new Date());

			venda.setCliente(null);// resetando o cliente escolhido
			venda.setFuncionario(null); // resetando o funcionário escolhido

			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listarOrdenado();

			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listarOrdenado();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar a venda.");
		}
	}

	public void salvar() {

		try {
			if (venda.getValorTotal().signum() == 0) {
				Messages.addGlobalInfo("Informe ao menos um item para a venda.");
				return;// não salva a venda (não faz nada)
			}
			VendaDAO vendaDAO = new VendaDAO();
			vendaDAO.salvar(venda, itensDaVenda);
			Messages.addGlobalInfo("Venda realizada com sucesso.");
			novo();// resetando a tela de finalização
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a venda.");
			erro.printStackTrace();
		}

	}

	public void listar() {

		VendaDAO vendaDAO = new VendaDAO();
		vendas = vendaDAO.listar("horario");

	}
	
	
	
	
	public void corrigirQuant() {
		
		for(ItemVenda itemVenda : this.itensDaVenda) {
			itemVenda.setValorParcial(itemVenda.getProduto().getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));
		}
		this.calcular();
		
	}
	
	public void testeMouseOut() {		
		
		for (int posicao = 0; posicao < itensDaVenda.size(); posicao++) {			
				
				if(Util.isNull(itensDaVenda.get(posicao).getQuantidade())) {
					itensDaVenda.get(posicao).setQuantidade(itensDaVenda.get(posicao).getProduto().getQuantidade());
				}			
				if(itensDaVenda.get(posicao).getQuantidade()>itensDaVenda.get(posicao).getProduto().getQuantidade()) {
					itensDaVenda.get(posicao).setQuantidade(itensDaVenda.get(posicao).getProduto().getQuantidade());
				}
				else if(itensDaVenda.get(posicao).getQuantidade() < 1) {
					itensDaVenda.get(posicao).setQuantidade(1L);
				}			
		}
	}
	
	
	
	
}
