package br.com.drogaria.externalUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.apache.deltaspike.core.api.scope.ViewAccessScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.drogaria.bean.GenericController;
import br.com.drogaria.externalUtil.dao.PedidoDAO;
import br.com.drogaria.externalUtil.dto.ItemPedidoDTO;
import br.com.drogaria.externalUtil.dto.PedidoDTO;
import br.com.drogaria.externalUtil.pojo.ItemPedido;
import br.com.drogaria.externalUtil.pojo.Pedido;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/* 
 * Classe criada para demonstrar a emissão de relatório 
 * diretamente no formato .pdf 
 * 
 */

@Named
@ViewAccessScoped // TIPO DO TEMPO DE VIDA DOS OBJETOS DESSA CLASSE NA VIEW
@ManagedBean
public class ImprimirPDFController extends GenericController {

	private static final long serialVersionUID = -2421979142739758153L;
	private static Logger log = LoggerFactory.getLogger(ImprimirPDFController.class);

	public StreamedContent gerarPDF() throws ApplicationException {
		try {
			// obter o objeto do bd

			PedidoDAO pedidoDAO = new PedidoDAO();

			List<Pedido> listaPedidos = pedidoDAO.listarPedidos();

//			Pedido pedido = pedidos.get(0);			

			return this.emitirRelatorio(listaPedidos);

		} catch (ApplicationException e) {
			throw e;
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] { e.getMessage() });
		}

	}

/////////////////////////////////////MÉTODO PARA GERAR O RELATÓRIO PDF
	@SuppressWarnings("static-access")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public StreamedContent emitirRelatorio(List<Pedido> listaPedidos) throws ApplicationException {
		String fileNameDownload = "RelatorioNome";

		byte[] arquivo = null;

		try {
			if (Util.isNotNull(listaPedidos)) {

				List<PedidoDTO> listaObjectDTO = this.popularObjetoDTO(listaPedidos);

//					List<Object> listaObjectDTO = new ArrayList<Object>();
//					listaObjectDTO.add(pedido);
				Map<String, Object> parametros = this.popularParametros(listaPedidos);

				arquivo = this.imprimirRelatorio(listaObjectDTO, parametros);
				if (arquivo == null) {
					throw new ApplicationException(KEY_MENSAGEM_DEFAULT,
							new String[] { "Não foi possível gerar o arquivo." });
				}
			}
			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo), "application/pdf",
					fileNameDownload.concat(".pdf"));

		} catch (ApplicationException e) {
			throw e;
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] { e.getMessage() });
		}
	}

	/////////// MÉTODO GENÉRICO

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Map<String, Object> popularParametros(List<Pedido> listaPedidos) throws ApplicationException {

		Map<String, Object> parametros = new HashMap<String, Object>();

		try {

			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String pathServidor = servletContext.getRealPath("");

			// String pathImg =
			// File.separator.concat("resources").concat(File.separator).concat("images").concat(File.separator);

			String pathSubrelatorio = ("reports").concat(File.separator).concat("jasper").concat(File.separator);

			parametros.put("nomeRelatorioJasper", "relatorioPedidos.jasper");

			// parametros.put("nomeImagem",
			// pathServidor.concat(pathImg.concat("Nome_da_imagem.png")));
			parametros.put("pathSubRelatorioItensPedido",
					pathServidor.concat(pathSubrelatorio.concat("subRelatorioItensPedido.jasper")));
//			parametros.put("pathSubRelatorioItensPedido", pathSubrelatorio.concat("subRelatorioItensPedido.jasper"));
			return parametros;

		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] { e.getMessage() });
		}
	}

	/////////// MÉTODO GENÉRICO
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<PedidoDTO> popularObjetoDTO(List<Pedido> listaPedidos) throws ApplicationException {

		List<PedidoDTO> listaPedidoDTO = new ArrayList<PedidoDTO>();

		try {

			if (Util.isNotNull(listaPedidos)) {

				for (Pedido pedidoPOJO : listaPedidos) {
					PedidoDTO pedidoDTO = null;

					pedidoDTO = new PedidoDTO();

					if (Util.isNotNull(pedidoPOJO.getData())) {
						pedidoDTO.setData(Util.formataDataHora(pedidoPOJO.getData()) + " - "
								+ Util.formataData(pedidoPOJO.getData()));
					}
					if (Util.isNotNull(pedidoPOJO.getCliente())) {
						if (Util.isNotNull(pedidoPOJO.getCliente().getDocumento())) {
							pedidoDTO.setDocumento(pedidoPOJO.getCliente().getDocumento());
						}
						if (Util.isNotNull(pedidoPOJO.getCliente().getEndereco())) {
							pedidoDTO.setEndereco(pedidoPOJO.getCliente().getEndereco());
						}
						if (Util.isNotNull(pedidoPOJO.getCliente().getNome())) {
							pedidoDTO.setNome(pedidoPOJO.getCliente().getNome());
						}
						if (Util.isNotNull(pedidoPOJO.getCliente().getUf())) {
							pedidoDTO.setUf(pedidoPOJO.getCliente().getUf());
						}
					}
					if (Util.isNotNull(pedidoPOJO.getSituacao())) {
						pedidoDTO.setSituacao(pedidoPOJO.getSituacao());
					}
					if (Util.isNotNull(pedidoPOJO.getIdentificacao())) {
						pedidoDTO.setIdentificacao(pedidoPOJO.getIdentificacao());
					}
					if (Util.isNotNull(pedidoPOJO.getListaItensPedido())) {
						pedidoDTO.setListaItensPedidoDTO(new ArrayList<ItemPedidoDTO>());

						if (!pedidoPOJO.getListaItensPedido().isEmpty()) {

							List<ItemPedidoDTO> itensPedidoDTO = new ArrayList<ItemPedidoDTO>();

							for (ItemPedido itemPedido : pedidoPOJO.getListaItensPedido()) {
								ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
								if (Util.isNotNull(itemPedido.getDescricao())) {
									itemPedidoDTO.setDescricao(itemPedido.getDescricao());
								}
								if (Util.isNotNull(itemPedido.getNumeroItem())) {
									itemPedidoDTO.setNumeroItem(itemPedido.getNumeroItem().toString());
								}
								if (Util.isNotNull(itemPedido.getQuantidade())) {
									itemPedidoDTO.setQuantidade(itemPedido.getQuantidade().toString());
								}
								if (Util.isNotNull(itemPedido.getValorTotalItem())) {
									itemPedidoDTO.setValorTotalItem(itemPedido.getValorTotalItem().toString());
								}
								itensPedidoDTO.add(itemPedidoDTO);
							}
							pedidoDTO.setListaItensPedidoDTO(itensPedidoDTO);
						}
					}

					listaPedidoDTO.add(pedidoDTO);
				}
			}

			return listaPedidoDTO;

		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] { e.getMessage() });
		}
	}

	// MÉTODO GENÉRICO
	public static <T> byte[] imprimirRelatorio(List<T> lista, Map<String, Object> map) throws IOException {

		InputStream jasper = null;

		try {
			// Gerar o relatório pelo Jasper Reports e exportar em PDF
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(lista);

			String path = File.separator.concat("reports").concat(File.separator).concat("jasper")
					.concat(File.separator);

			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();

			jasper = servletContext.getResourceAsStream(path.concat((String) map.get("nomeRelatorioJasper")));

			// path = Faces.getRealPath(path.concat((String)
			// map.get("nomeRelatorioJasper")));

			JasperPrint print = JasperFillManager.fillReport(jasper, map, beanDS);

			return JasperExportManager.exportReportToPdf(print);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao gerar o relatório", e);
		} finally {
			fechaInputStream(jasper, "Falha ao fechar o jasper");
		}
	}

	public static void fechaInputStream(InputStream i, String mensagemErro) {
		if (mensagemErro == null) {
			mensagemErro = "Falha ao fechar InputStream";
		}
		if (i != null) {
			try {
				i.close();
			} catch (IOException e) {
			}
		}
	}

	///////////////////////////////////// MÉTODO PARA EXIBIR O RELATÓRIO

	@SuppressWarnings("static-access")
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public DefaultStreamedContent emitirRelatorioExibir(List<Pedido> listaPedidos) throws ApplicationException {
		String fileNameDownload = "RelatorioNome";

		byte[] arquivo = null;

		try {
			if (Util.isNotNull(listaPedidos)) {

				List<PedidoDTO> listaObjectDTO = this.popularObjetoDTO(listaPedidos);

//							List<Object> listaObjectDTO = new ArrayList<Object>();
//							listaObjectPOJO.add(pedido);
				Map<String, Object> parametros = this.popularParametros(listaPedidos);

				arquivo = this.imprimirRelatorio(listaObjectDTO, parametros);
				if (arquivo == null) {
					throw new ApplicationException(KEY_MENSAGEM_DEFAULT,
							new String[] { "Não foi possível gerar o arquivo." });
				}
			}
			return new DefaultStreamedContent(new ByteArrayInputStream(arquivo), "application/pdf",
					fileNameDownload.concat(".pdf"));

		} catch (ApplicationException e) {
			throw e;
		} catch (Exception e) {
			log.error(KEY_ERRO, e);
			throw new ApplicationException(KEY_MENSAGEM_DEFAULT, new String[] { e.getMessage() }, e);
		}
	}

}
