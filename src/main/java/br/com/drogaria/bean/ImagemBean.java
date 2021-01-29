package br.com.drogaria.bean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

//classe para guardar o caminho e os bytes da foto

@ManagedBean
@RequestScoped
public class ImagemBean {

	@ManagedProperty("#{param.caminho}") // fazendo a view reconhecer o caminho
	private String caminho; // guardar o caminho da foto

	private StreamedContent foto; // guardar os bytes da foto

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getFoto() throws IOException {

		if (caminho == null || caminho.isEmpty()) {

			String caminhoRaiz = "/resources/images/";

			String caminho = Faces.getRealPath(caminhoRaiz.concat("branco.png"));

			Path path = Paths.get(caminho);
			InputStream fluxoDeBytes = Files.newInputStream(path);// abrindo a imagem e pegando os bytes dela
			foto = new DefaultStreamedContent(fluxoDeBytes);

		} else {
			Path path = Paths.get(caminho);
			InputStream fluxoDeBytes = Files.newInputStream(path);// abrindo a imagem e pegando os bytes dela
			foto = new DefaultStreamedContent(fluxoDeBytes);
		}

		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
