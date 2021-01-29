package br.com.drogaria.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.drogaria.domain.Caixa;
import br.com.drogaria.util.HibernateUtil;

public class CaixaDAO extends GenericDAO<Caixa>{

	//verificar se existe um caixa com a data a ser informada
	//(BUSCA POR DATA)
	public Caixa buscarPorData(Date dataAberturaBusca) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			
			Criteria consulta = sessao.createCriteria(Caixa.class);
			consulta.add(Restrictions.eq("dataAbertura", dataAberturaBusca));
			Caixa resultado = (Caixa) consulta.uniqueResult();
			return resultado;
			
			
		} catch (RuntimeException erro) {
			throw erro;// repropagando o erro para o Bean
		}
		finally {
			sessao.close();
		}
		
		
		
	}
	
	
}
