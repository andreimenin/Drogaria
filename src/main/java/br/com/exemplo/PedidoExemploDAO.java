package br.com.exemplo;

import java.util.ArrayList;
import java.util.List;

public class PedidoExemploDAO {

	private List<Cliente> listarClientes() {
		
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		
		Cliente cliente = new Cliente();
		Cliente cliente1 = new Cliente();
		Cliente cliente2 = new Cliente();
				
		cliente.setEndereco("Endereço do cliente");
		cliente.setNome("Cliente");
		cliente.setUf("SP");
		cliente.setDocumento("12874111");
		
		cliente1.setEndereco("Endereço do cliente 1");
		cliente1.setNome("Cliente 1");
		cliente1.setUf("PR");
		cliente.setDocumento("81749114");
		
		cliente2.setEndereco("Endereço do cliente 2");
		cliente2.setNome("Cliente 2");
		cliente2.setUf("MG");
		cliente.setDocumento("8917187");
		
		
		listaClientes.add(cliente);
		listaClientes.add(cliente1);
		listaClientes.add(cliente2);
		
		return listaClientes;		
	}	
	
	private List<Produto> listarProdutos(){
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		
		Produto produto = new Produto();
		produto.setCodigo(0L);
		produto.setDescricao("Produto 0");
		produto.setValor(10.00);
		
		Produto produto1 = new Produto();
		produto1.setCodigo(1L);
		produto1.setDescricao("Produto 1");
		produto1.setValor(20.00);
		
		Produto produto2 = new Produto();
		produto2.setCodigo(0L);
		produto2.setDescricao("Produto 2");
		produto2.setValor(15.00);
				
		
		return listaProdutos;
	}
	
	
	private List<ItemPedido> listarItensPedido(){
		
		List<Produto> listaProdutos = new ArrayList<Produto>();
		listaProdutos = listarProdutos();		
		
		List<ItemPedido> listaItensPedido = new ArrayList<ItemPedido>();
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setNumeroItem(0);
		itemPedido.setProduto(listaProdutos.get(0));
		itemPedido.setQuantidade(13.00);
		itemPedido.setValorTotalItem(itemPedido.getProduto().getValor() * itemPedido.getQuantidade());
		
		ItemPedido itemPedido1 = new ItemPedido();
		itemPedido1.setNumeroItem(1);
		itemPedido1.setProduto(listaProdutos.get(1));
		itemPedido1.setQuantidade(21.00);
		itemPedido1.setValorTotalItem(itemPedido1.getProduto().getValor() * itemPedido1.getQuantidade());
		
		ItemPedido itemPedido2 = new ItemPedido();
		itemPedido2.setNumeroItem(2);
		itemPedido2.setProduto(listaProdutos.get(2));
		itemPedido2.setQuantidade(13.00);
		itemPedido2.setValorTotalItem(itemPedido2.getProduto().getValor() * itemPedido2.getQuantidade());
		
		listaItensPedido.add(itemPedido);
		listaItensPedido.add(itemPedido1);
		listaItensPedido.add(itemPedido2);		
		
		return listaItensPedido;
	}
	
	
	public List<Pedido> listarPedidos(){
		
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		listaClientes = listarClientes();		
		
		List<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		Pedido pedido = new Pedido();
		pedido.setCliente(listaClientes.get(0));
		pedido.setListaItensPedido(listarItensPedido());
		
		Pedido pedido1 = new Pedido();
		pedido.setCliente(listaClientes.get(1));
		pedido.setListaItensPedido(listarItensPedido());
		
		listaPedidos.add(pedido);
		listaPedidos.add(pedido1);		
		
		return listaPedidos;
		
	}
	
	
	
}
