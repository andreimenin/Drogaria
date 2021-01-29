package br.com.drogaria.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//classe para carregar o hibernate simultâneamente com o tomcat para que o primeiro usuário não 
//seja penalizado com carregamentos demorados

public class HibernateContexto implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes().close();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes().openSession(); // forçando a inicialização da fábrica de sessões quando o
															// tomcat for ligado

	}

}
