//package br.com.drogaria.externalUtil;
//
//import java.util.List;
//
//import javax.faces.bean.SessionScoped;
//import javax.inject.Named;
//
//import org.primefaces.PrimeFaces;
//import org.primefaces.model.StreamedContent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import br.com.drogaria.bean.GenericController;
//
//@Named
//@SessionScoped
//@ControllerExceptionHandler
//public class ExibirPDF extends GenericController{
//
//	private static final long serialVersionUID = -7308197946016054539L;
//
//	private static Logger log = LoggerFactory.getLogger(ExibirPDF.class);
//	
//	//variáveis do relatório
//	private Pedido pedidoDTO;
//	
//	private String nomeArquivo;
//	
//	private StreamedContent arquivo;	
//	
//	public void carregarDados() throws ApplicationException{	
//		try {
//			//obter o objeto do bd
//			
//			PedidoDAO pedidoDAO = new PedidoDAO();
//			
//			List<Pedido> pedidos = pedidoDAO.listarPedidos();
//			
//			Pedido pedido = pedidos.get(0);			
//		    
//		    gerarPDF(pedido);
//		    
//		   // PrimeFaces.current().ajax().update("nomeFormDaModal");
//				
//		} catch (ApplicationException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			log.error(KEY_ERRO, e);
//			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()});
//		}		
//	}
//	
//	
//	
//	public void gerarPDF(Pedido pedido) throws ApplicationException{
//		try {
//			//setEdicao
//			setPedidoDTO(pedido);
//			
//			setNomeArquivo("Relatório_Exemplo: " + getPedidoDTO().getSituacao());
//			
//			setArquivo(emitirRelatorio(getPedidoDTO()));			
//			
//			PrimeFaces.current().ajax().addCallbackParam("sucesso", Boolean.TRUE);
//			
//		} catch (ApplicationException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			log.error(KEY_ERRO, e);
//			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] {e.getMessage()});
//		}		
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	public void fecharModal() throws ApplicationException{
//		try {
//			setArquivo(null);
//			setNomeArquivo(null);
//		} catch (Exception e) {
//			
//		}
//	}
//	
//
//	public Pedido getPedidoDTO() {
//		return pedidoDTO;
//	}
//
//	public void setPedidoDTO(Pedido pedidoDTO) {
//		this.pedidoDTO = pedidoDTO;
//	}
//
//	public String getNomeArquivo() {
//		return nomeArquivo;
//	}
//
//	public void setNomeArquivo(String nomeArquivo) {
//		this.nomeArquivo = nomeArquivo;
//	}
//
//	public StreamedContent getArquivo() {
//		return arquivo;
//	}
//
//	public void setArquivo(StreamedContent arquivo) {
//		this.arquivo = arquivo;
//	}
//}
//
//
