package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.domain.Estado;

//http://localhost:8080/drogaria/rest/estado
@Path("estado")
public class EstadoService {

			@GET
			public String listar() {
				EstadoDAO estadoDAO = new EstadoDAO();
				
				List<Estado> estados = estadoDAO.listar("nome");
				
				
				Gson gson = new Gson();
				String json = gson.toJson(estados);
				
				
				return json;
			}
	
	
	
	
	
}
