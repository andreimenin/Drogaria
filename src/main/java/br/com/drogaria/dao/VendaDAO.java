package br.com.drogaria.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.drogaria.domain.ItemVenda;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.domain.Venda;
import br.com.drogaria.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda>{

	
	public void salvar(Venda venda, List<ItemVenda> itensVenda) {
		
			
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;	
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(venda);			
			
				
			
			for(int posicao = 0; posicao < itensVenda.size(); posicao++) {
				ItemVenda itemVenda = itensVenda.get(posicao);//pegando o item da linha corrente
				itemVenda.setVenda(venda);//incluindo item da venda na venda
				
				sessao.save(itemVenda);
				
				Produto produto = itemVenda.getProduto();
				
				Long quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				
				if(quantidade >= 0) {
					produto.setQuantidade(quantidade);
					sessao.update(produto);
				}
				else {
					throw new RuntimeException("Quantidade insuficiente em estoque"); //forçando o erro da camada DAO para a camada BEAN
													//para funcionar o OMNIFACES
				}
				
				
				
			}
			transacao.commit();
			
		} catch (RuntimeException erro) {
			if(transacao != null) {
				transacao.rollback();
			}
			throw erro;
		}
		
		
	}
	
	
}
