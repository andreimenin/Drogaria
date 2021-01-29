package br.com.exemplo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EmissorRelatorioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public EmissorRelatorioServlet() {
      super();
    }
    
    protected void doProcess(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
      PedidoExemploDAO pedidoDAO = new PedidoExemploDAO();
    
      ServletOutputStream outputStream = response.getOutputStream();
       
      // Obtem o caminho para o diretório jasper
      String realPath = request.getSession().getServletContext().getRealPath(
        "/exemploReports/jasper")
        + System.getProperty("file.separator");
      // Caminho do .jasper do relatorio
      String caminhoRelJasper = realPath + "RelatorioPedidos.jasper";
      // Lista com java beans    
      List<Pedido> dadosRelatorio = pedidoDAO.listarPedidos();
       
      // Stream com o .jasper
      @SuppressWarnings("unused")
	InputStream relJasper = EmissorRelatorioServlet.class
        .getResourceAsStream(caminhoRelJasper);
    
      // Cria o DataSource
      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
        dadosRelatorio);
    
      // Parâmetros dos relatórios
      Map<String, Object> parametros = new HashMap<String, Object>();
      // Caminho do diretório onde está o jasper do sub-relatório
      parametros.put("SUBREPORT_DIR", realPath);
      parametros.put("REPORT_RESOURCE_BUNDLE", ResourceBundle
        .getBundle("br.com.exemplo.mensagens"));
      parametros.put("REPORT_LOCALE", request.getLocale());
    
      JasperPrint impressao = null;
      try {
        // Ajusta o response
        response.setHeader("Content-Disposition", "inline; filename=\""
          + "RelatorioPedidos.pdf" + "\"");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/pdf");
         
        // Gera o relatório
        impressao = JasperFillManager
          .fillReport(caminhoRelJasper, parametros, ds);
        // Escreve no OutputStream do response
        JasperExportManager.exportReportToPdfStream(impressao, outputStream);
        outputStream.close();
      }
      catch (JRException e) {
        System.out.println(e.getMessage());
      }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      doProcess(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      doProcess(request, response);
    }
  }
