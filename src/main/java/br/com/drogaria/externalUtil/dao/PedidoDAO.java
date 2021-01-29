package br.com.drogaria.externalUtil.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.drogaria.externalUtil.Util;
import br.com.drogaria.externalUtil.pojo.Cliente;
import br.com.drogaria.externalUtil.pojo.ItemPedido;
import br.com.drogaria.externalUtil.pojo.Pedido;
import br.com.drogaria.externalUtil.pojo.Produto;

public class PedidoDAO {

	private List<Cliente> listarClientes() {

		List<Cliente> listaClientes = new ArrayList<Cliente>();

		Cliente cliente = new Cliente();
		Cliente cliente1 = new Cliente();
		Cliente cliente2 = new Cliente();

		cliente.setEndereco("Endereço do cliente 1");
		cliente.setNome("José Etevaldo da Silva");
		cliente.setUf("SP");
		cliente.setDocumento("12874111");

		cliente1.setEndereco("Endereço do cliente 2");
		cliente1.setNome("Maria Joaquina Mendes");
		cliente1.setUf("PR");
		cliente1.setDocumento("81749114");

		cliente2.setEndereco("Endereço do cliente 3");
		cliente2.setNome("Silvio Santos");
		cliente2.setUf("MG");
		cliente2.setDocumento("8917187");

		listaClientes.add(cliente);
		listaClientes.add(cliente1);
		listaClientes.add(cliente2);

		return listaClientes;
	}

	private List<Produto> listarProdutos() {

		List<Produto> listaProdutos = new ArrayList<Produto>();

		Produto produto1 = new Produto();
		produto1.setCodigo(1L);
		produto1.setDescricao("Produto A");
		produto1.setValor(10.21);

		Produto produto2 = new Produto();
		produto2.setCodigo(2L);
		produto2.setDescricao("Produto B");
		produto2.setValor(20.34);

		Produto produto3 = new Produto();
		produto3.setCodigo(3L);
		produto3.setDescricao("Produto C");
		produto3.setValor(15.40);

		Produto produto4 = new Produto();
		produto4.setCodigo(4L);
		produto4.setDescricao("Produto D");
		produto4.setValor(17.50);

		Produto produto5 = new Produto();
		produto5.setCodigo(4L);
		produto5.setDescricao("Produto E");
		produto5.setValor(30.60);

		Produto produto6 = new Produto();
		produto6.setCodigo(4L);
		produto6.setDescricao("Produto F");
		produto6.setValor(7.80);

		Produto produto7 = new Produto();
		produto7.setCodigo(4L);
		produto7.setDescricao("Produto G");
		produto7.setValor(4.00);

		listaProdutos.add(produto1);
		listaProdutos.add(produto2);
		listaProdutos.add(produto3);
		listaProdutos.add(produto4);

		return listaProdutos;
	}

	private List<ItemPedido> listarItensPedidoAleatorio() {

		List<Produto> listaProdutos = new ArrayList<Produto>();
		listaProdutos = listarProdutos();

		CopyOnWriteArrayList<ItemPedido> listaItensPedido = new CopyOnWriteArrayList<ItemPedido>();

		List<Integer> numeros = Util.sortearNumeroAleatorio(10, 10);
		
		

		for (int i = 1; i <= numeros.size(); i++) {			
			List<Integer> indicesProdutos = Util.sortearNumeroAleatorio(1, listaProdutos.size());
			
			int indiceProduto = indicesProdutos.get(0);
			
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setProduto(listaProdutos.get(indiceProduto));			
			for(ItemPedido item : listaItensPedido) {
				if(item.getProduto().equals(itemPedido.getProduto())) {
					itemPedido.setQuantidade(item.getQuantidade()+1);
					listaItensPedido.remove(item);
				}
			}
			
			if(Util.isNull(itemPedido.getQuantidade())) {
				itemPedido.setQuantidade(1);
			}			

			itemPedido.setNumeroItem(i);			
			
			itemPedido.setValorTotalItem(itemPedido.getProduto().getValor() * itemPedido.getQuantidade());
			itemPedido.setDescricao(itemPedido.getProduto().getDescricao());

			listaItensPedido.add(itemPedido);
		}

		return listaItensPedido;
	}

	public List<Pedido> listarPedidos() {

		List<Cliente> listaClientes = new ArrayList<Cliente>();
		listaClientes = listarClientes();

		List<ItemPedido> listaItensPedido = new ArrayList<ItemPedido>();
		listaItensPedido = listarItensPedidoAleatorio();

		List<Pedido> listaPedidos = new ArrayList<Pedido>();

		// Numero maximo de sorteios.
		int maxSorteios = Util.sortearNumeroAleatorioUnico(50);
		int numeroMax = 100;
		List<Integer> numeros = Util.sortearNumeroAleatorio(maxSorteios, numeroMax);

		for(int i = 0; i < maxSorteios ; i++) {
			Pedido pedido = new Pedido();
			pedido.setData(new Date());
			pedido.setSituacao("Concluída");
			pedido.setCliente(listaClientes.get(Util.sortearNumeroAleatorioUnico(3)));
			pedido.setListaItensPedido(listaItensPedido);
			pedido.setIdentificacao((numeros.get(i)).toString());
			
			listaItensPedido = new ArrayList<ItemPedido>();
			listaItensPedido = listarItensPedidoAleatorio();
			
			pedido.setListaItensPedido(listaItensPedido);
			
			listaPedidos.add(pedido);
		}
		
		
		return listaPedidos;
	}

}
