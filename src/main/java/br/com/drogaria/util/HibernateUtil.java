package br.com.drogaria.util;



import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;



public class HibernateUtil {

    private static SessionFactory fabricaDeSessoes = criarFabricaDeSessoes();
    
    public static SessionFactory getFabricaDeSessoes() {
		return fabricaDeSessoes;
	}
    
    public static Connection getConexao(){
    	
    	Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
    	Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
    		
    		@Override //método que será executado para retornar o que você quer
    		public Connection execute(Connection conn) throws SQLException {
    			// TODO Auto-generated method stub
    			return conn;
    		}
    		
    		
		});
    	return conexao;
    }
    
    

    private static SessionFactory criarFabricaDeSessoes() {
        try {
           Configuration configuracao = new Configuration().configure();
           
           ServiceRegistry registro = new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
           
           SessionFactory fabrica = configuracao.buildSessionFactory(registro);
            
           return fabrica;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("A fábrica de sessão não pode ser criada. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

   

}