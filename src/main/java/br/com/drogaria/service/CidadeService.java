package br.com.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.drogaria.dao.CidadeDAO;

import br.com.drogaria.domain.Cidade;


//CLASSE PARA LISTAR CIDADES DE ACORDO COM O CÓDIGO DO ESTADO BUSCADO

//http://localhost:8080/drogaria/rest/cidade/{estadoCodigoParam} 
@Path("cidade")
public class CidadeService {

	@GET
	@Path("{estadoCodigo}")
	public String buscarPorEstado(@PathParam("estadoCodigo") Long estadoCodigo) {
		CidadeDAO cidadeDAO = new CidadeDAO();
		
		List<Cidade> cidades = cidadeDAO.buscarPorEstado(estadoCodigo);
		
		
		Gson gson = new Gson();
		String json = gson.toJson(cidades);
		
		
		return json;
	}
	
	
	
}
