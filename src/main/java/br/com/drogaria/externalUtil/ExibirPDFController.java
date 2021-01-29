package br.com.drogaria.externalUtil;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.drogaria.bean.GenericController;
import br.com.drogaria.externalUtil.dao.PedidoDAO;
import br.com.drogaria.externalUtil.pojo.Pedido;

@Named
@SessionScoped
@ControllerExceptionHandler
@ManagedBean
public class ExibirPDFController extends GenericController{

	private static final long serialVersionUID = -594536237468858381L;

	private static Logger log = LoggerFactory.getLogger(ExibirPDFController.class);
	
	private String nomeArquivo;
	
	private StreamedContent arquivo;		
	
	@SuppressWarnings("deprecation")
	public void carregarDados() throws ApplicationException{
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		
		
		
		try {
			//obtendo dados necessários para o relatório
			/////////////
			//obter o objeto do bd
			
			PedidoDAO pedidoDAO = new PedidoDAO();
			
			List<Pedido> listaPedidos = pedidoDAO.listarPedidos();
			
//			Pedido pedido = pedidos.get(0);			
		    
			
			gerarPDF(listaPedidos);
			
			RequestContext.getCurrentInstance().update("modalExibirRelatorio");	
			//PrimeFaces.current().ajax().update("modalExibirRelatorio");
			
		}
		catch (ApplicationException e) {
			throw e;
		}
		catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()});
		}
		
		
		
	}
	
//	public StreamedContent imprimirRelatorio(Pedido pedido) throws ApplicationException{
//		
//		try {
//			//início do processo
//			carregarDados();
//			
//			//criar o map com os parâmetros do relatório
//			Map<String, Object> map = new HashMap<String, Object>();
//			
//			//faz a lista de objetos  ////////////QUANDO TIVER
//			List<Pedido> listaPedidos = new ArrayList<Pedido>();
//			listaPedidos.add(pedido);
//			
//			InputStream inputStream = new ByteArrayInputStream(ImprimirPDFController.imprimirRelatorio(listaPedidos, map));
//			
//			return new DefaultStreamedContent(new BufferedInputStream(inputStream), "application/pdf","Relatório_Pedidos");
//			
//			
//			
//		} catch (IOException ioe) {
//			log.error(KEY_ERRO, ioe);
//			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {ioe.getMessage()}, ioe);
//		}		
//	}
	
	public void gerarPDF(List<Pedido> listaPedidos) throws ApplicationException{
		try {
			
			setNomeArquivo("Relatório_Pedidos".concat(".pdf"));			
			
			ImprimirPDFController bean = new ImprimirPDFController();
			
			setArquivo(bean.emitirRelatorio(listaPedidos));
			
			getArquivo();
			
			PrimeFaces.current().ajax().addCallbackParam("sucesso", Boolean.TRUE);
			
		} 
			catch (ApplicationException e) {
			throw e;
		}
		 catch (Exception e) {
		log.error(KEY_ERRO, e);
		throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()}, e);
	}
	}
	
	
	public void fecharModal()throws ApplicationException{
		try {
			setArquivo(null);
			setNomeArquivo(null);
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()});
		}
	}	
	

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public StreamedContent getArquivo() {
		return arquivo;
	}

	public void setArquivo(StreamedContent arquivo) {
		this.arquivo = arquivo;
	}	
}
