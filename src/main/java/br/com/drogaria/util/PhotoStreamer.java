package br.com.drogaria.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class PhotoStreamer {

	private static final Logger logger = LoggerFactory.getLogger(PhotoStreamer.class);
	private static StreamedContent defaultFileContent;
	private StreamedContent fileContent;

	@SuppressWarnings("finally")
	public StreamedContent getFileContent() throws FileNotFoundException {
		try {
			logger.trace("Entered method getFileContent.");

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String photoId = externalContext.getRequestParameterMap().get("produto_id");
			// String photoId = photoIdParam.toString();
			if (photoId == null || photoId.equals("")) {
				fileContent = defaultFileContent;
				logger.info("Id was null or empty. Retrieved default file content.");
			} else {
				int parsedId = Integer.parseInt(photoId);

				String caminhoRaiz = "/resources/images/";

				String caminho = Faces.getRealPath(caminhoRaiz.concat(photoId.toString().concat(".png")));

				InputStream inputStream = new FileInputStream(caminho);

				fileContent = new DefaultStreamedContent(inputStream, "image/png", photoId.toString().concat(".png"));
				logger.info("Retrieved file content for image {}.", parsedId);
			}
			logger.trace("Exited method getFileContent.");

		} catch (FileNotFoundException e) {
			fileContent = defaultFileContent;
		} finally {
			return fileContent;
		}

	}

	// public StreamedContent getFileContent() throws IOException {
	// // Util is the pojo
	// try {
	//
	// ExternalContext externalContext =
	// FacesContext.getCurrentInstance().getExternalContext();
	// String photoId = externalContext.getRequestParameterMap().get("produto_id");
	// System.out.println("photo_id: " + photoId);
	//
	// if (photoId == null) {
	// defaultFileContent = new
	// DefaultStreamedContent(externalContext.getResourceAsStream("/branco.png"),
	// "image/png");
	//
	// return defaultFileContent;
	// }
	//
	// String caminhoRaiz = "/resources/images/";
	//
	// String caminho =
	// Faces.getRealPath(caminhoRaiz.concat(photoId.toString().concat(".png")));
	//
	// InputStream inputStream = new FileInputStream(caminho);
	//
	// fileContent = new DefaultStreamedContent(inputStream);
	//
	// return fileContent;
	//
	// } catch (FileNotFoundException e) {
	// return defaultFileContent;
	// }
	//
	// }

	public void setFileContent(StreamedContent fileContent) {
		this.fileContent = fileContent;
	}

	static {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = contextClassLoader.getResourceAsStream("resources/images/branco.png");
		defaultFileContent = new DefaultStreamedContent(inputStream, "image/png");
	}

}