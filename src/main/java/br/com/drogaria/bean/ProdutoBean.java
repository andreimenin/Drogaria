package br.com.drogaria.bean;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.drogaria.dao.FabricanteDAO;
import br.com.drogaria.dao.ProdutoDAO;
import br.com.drogaria.domain.Fabricante;
import br.com.drogaria.domain.Produto;
import br.com.drogaria.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

//classe java com modelo e controle

@SuppressWarnings("serial")
@ViewScoped// TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean // diz para o Tomcat que essa classe é o controle modelo dessa classe na
				// aplicação
public class ProdutoBean implements Serializable {

	private Produto produto;

	private List<Produto> produtos;

	private List<Fabricante> fabricantes;

	private StreamedContent foto;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	public StreamedContent getFoto() {
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

	@PostConstruct // como se fosse um construtor, será automaticamente chamado o método listar
	public void listar() {
		try {

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar();
			
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar.");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
			if (produto.getCaminhoFotoTemporaria() == null) {
				Messages.addGlobalError("O campo 'Foto' é obrigatório.");
				return;
			}

			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto produtoRetorno = produtoDAO.merge(produto);

			Path origem = Paths.get(produto.getCaminhoFotoTemporaria());
			
			String caminhoRaiz = "/resources/images/";

			String caminho = Faces.getRealPath(caminhoRaiz.concat(produtoRetorno.getCodigo() + ".png"));
						
			Path destino = Paths.get(caminho);
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			Messages.addGlobalInfo("Produto salvo com sucesso: " + produto.getDescricao());

			produto = new Produto();
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();// resetando a escolha no combobox
			produtos = produtoDAO.listar();// atualizando a listagem após uma inclusão
			
			

		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um produto.");
			erro.printStackTrace();
		}

	}

	public void novo() {

		try {
			produto = new Produto();

			FabricanteDAO fabricanteDAO = new FabricanteDAO(); // carregando do banco de dados
			fabricantes = fabricanteDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um nova produto.");
		}

	}

	public void excluir(ActionEvent evento) {

		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtoDAO.excluir(produto);
			
			String caminhoRaiz = "/resources/images/";

			String caminho = Faces.getRealPath(caminhoRaiz.concat(produto.getCodigo() + ".png"));

			Path arquivo = Paths.get(caminho);
			Files.deleteIfExists(arquivo);

			Messages.addGlobalInfo("Produto removido:" + produto.getDescricao());

			produtos = produtoDAO.listar();

		} catch (RuntimeException | IOException erro) {
			Messages.addGlobalError("Erro ao tentar excluir um produto.");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {

		try {
			produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");
			
			String caminhoRaiz = "/resources/images/";

			String caminho = Faces.getRealPath(caminhoRaiz.concat(produto.getCodigo() + ".png"));
			
			produto.setCaminhoFotoTemporaria(caminho);

			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricantes = fabricanteDAO.listar();// resetando a escolha no combobox

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar editar um produto.");
			erro.printStackTrace();
		}

	}

	// upload de fotos
	public void handleFileUpload(FileUploadEvent evento) {

		// aula 227 String nome = evento.getFile().getFileName();
		// String tipo = evento.getFile().getContentType();
		// long tamanho = evento.getFile().getSize();
		//
		// Messages.addGlobalInfo("Nome: " + nome + " Tipo: " + tipo + " Tamanho: " +
		// tamanho);

		try {
			UploadedFile arquivoUpload = evento.getFile();
			Path arquivoTemporario = Files.createTempFile(null, null);

			Files.copy(arquivoUpload.getInputstream(), arquivoTemporario, StandardCopyOption.REPLACE_EXISTING);// pegando
																												// o
			produto.setCaminhoFotoTemporaria(arquivoTemporario.toString()); // fluxo
			// de
			// bytes
			// do
			// arquivo

		} catch (IOException erro) {
			Messages.addGlobalInfo("Ocorreu um erro ao tentar fazer o upload de um arquivo.");
			erro.printStackTrace();
		}

	}

	public void imprimir() {
		try {

			DataTable tabela = (DataTable) Faces.getViewRoot().findComponent("formListagem:tabela");
			Map<String, Object> filtros = tabela.getFilters(); // mapeando filtros

			String proDescricao = (String) filtros.get("descricao"); // capturando filtro
			String fabDescricao = (String) filtros.get("fabricante.descricao"); // capturando filtro

			String caminho = Faces.getRealPath("/reports/jasper/produtosRelatorio.jasper");
			
			String caminhoBanner = Faces.getRealPath("/resources/images/banner.jpg");
			
			Map<String, Object> parametros = new HashMap<>();// mapeando o endereço de memória

			parametros.put("CAMINHO_BANNER", caminhoBanner);
			
			
			if (proDescricao == null) {
				parametros.put("PRODUTO_DESCRICAO", "%%");
			} else {
				parametros.put("PRODUTO_DESCRICAO", "%" + proDescricao + "%");
			}

			if (fabDescricao == null) {
				parametros.put("FABRICANTE_DESCRICAO", "%%");
			} else {
				parametros.put("FABRICANTE_DESCRICAO", "%" + fabDescricao + "%");
			}

			Connection conexao = HibernateUtil.getConexao();

			JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
			//JasperPrintManager.printReport(relatorio, true);
			
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			FacesContext context = FacesContext.getCurrentInstance();			
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			
			JasperExportManager.exportReportToPdfStream(relatorio, baos);
			
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition", "inline; filename=relatorio.pdf"); 
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			
			context.responseComplete();
			
			
			

		} catch (JRException | IOException erro) {
			Messages.addGlobalError("Erro ao tentar gerar o relatório");
			erro.printStackTrace();
		}

	}

	public void download(ActionEvent evento) {

		produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		try {
			// pegando os bytes (foto)	
			String caminhoRaiz = "/resources/images/";
			
			String caminho  = Faces.getRealPath(caminhoRaiz.concat(produto.getCodigo().toString().concat(".png")));
			
			InputStream stream = new FileInputStream(caminho);			

			foto = new DefaultStreamedContent(stream, "image/png", produto.getCodigo() + ".png");			
			
		} catch (FileNotFoundException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar efetuar o download.");

			erro.printStackTrace();
		}
	}

}
