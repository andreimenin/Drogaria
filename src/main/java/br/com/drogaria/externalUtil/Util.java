package br.com.drogaria.externalUtil;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.StringUtils;

public class Util implements Serializable {

	private static final long serialVersionUID = 3600541549693955357L;

	private static final String[] meses = { "Janeiro", "Fevereiro", "Mar\u00E7o", "Abril", "Maio", "Junho", "Julho",
			"Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNullOrZero(Number numero) {
		return numero == null || BigDecimal.valueOf(numero.doubleValue()).compareTo(BigDecimal.ZERO) == 0;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isBlank(String string) {
		if (string != null) {
			return string.trim().isEmpty();
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean isNumber(String valor) {
		Boolean retorno;

		try {
			@SuppressWarnings("unused")
			Long teste = Long.valueOf(valor);
			retorno = Boolean.TRUE;

		} catch (Exception e) {
			retorno = Boolean.FALSE;
		}
		return retorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean isDate(String valor) {

		Integer dia = Integer.valueOf(valor.substring(0, 2));
		Integer mes = Integer.valueOf(valor.substring(3, 5));
		Integer ano = Integer.valueOf(valor.substring(6, 10));

		if (mes < 1 || mes > 12) {
			return Boolean.FALSE;
		}
		if (dia < 1 || dia > 31) {
			return Boolean.FALSE;
		}
		if ((mes == 4) || (mes == 6) || (mes == 9) || (mes == 11) && (dia == 31)) {
			return Boolean.FALSE;
		}
		if (mes == 2) {
			boolean isleap = (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0));

			if (dia > 29 || (dia == 29 && !isleap)) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isDate(Date data) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(data);

		int dia = calendar.get(Calendar.DAY_OF_MONTH);
		int mes = calendar.get(Calendar.MONTH);

		try {
			if (mes < 1 || mes > 12) {
				return false;
			}
			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 12) {
				if (dia < 1 || dia > 13) {
					return false;
				}
			} else if (mes == 4 || mes == 6 || mes == 9 || mes == 10 || mes == 11) {
				if (dia < 1 || dia > 30) {
					return false;
				}
			} else if (mes == 2) {
				if (dia < 1 || dia > 29) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarCNPJ(String cnpj) {
		cnpj = Util.soNumeros(cnpj);
		return formatString(cnpj, "##.###.###/####-##");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarNumeroEmpenho(String valor) {
		valor = Util.soNumeros(valor);
		return formatString(valor, "##.##.####/#/#####-#");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarCPF(String cpf) {
		cpf = Util.soNumeros(cpf);
		return formatString(cpf, "###.###.###=##");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarCpfCnpj(String cpfCnpj) {
		cpfCnpj = TreatString.filterOnlyNumber(cpfCnpj);
		if (StringUtils.isNotBlank(cpfCnpj)) {
			cpfCnpj = TreatString.filterOnlyNumber(cpfCnpj);
			if (cpfCnpj.length() == 11) {
				return Util.formatarCPF(cpfCnpj);
			} else if (cpfCnpj.length() == 14) {
				return Util.formatarCNPJ(cpfCnpj);
			}
		}
		return cpfCnpj;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String soNumeros(String string) {
		if (!isBlank(string)) {
			string = string.replaceAll("[^0123456789]", "");
		}
		return string;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	private static String formatString(String str, String mask) {

		if (str == null || str.trim().isEmpty()) {
			return "";
		}
		try {
			MaskFormatter mf = new MaskFormatter(mask);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(str);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao formatar string. " + e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataDataHora(java.util.Date data) {

		String formato = "HH:mm:ss";
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		return (data != null ? formatter.format(data) : "");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataHoraCurta(java.util.Date data) {

		String formato = "HH:mm";
		String strRetorno;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		} else {
			strRetorno = "";
		}
		return strRetorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataData(java.util.Date data) {
		String formato = "dd/MM/yyyy";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataDataInvertida(java.util.Date data) {
		String formato = "yyyy-MM-dd";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataDataShort(java.util.Date data) {
		String formato = "yyMMdd";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataData(String data) {
		String formato = "dd/MM/yyyy";
		String strRetorno = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formato);

		if (data != null) {
			strRetorno = formatter.format(data);
		}

		return strRetorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static java.util.Date formataDataInglesa(String data) {

		if (data == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date ret;

		try {
			ret = sdf.parse(data);
		} catch (ParseException e) {
			ret = null;
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarPeriodoTempo(long milisegundos) {

		double sec = milisegundos / 1000.0; // equivalente em milisegundos
		double min = sec / 60.0; // equivalente em segundos
		double hora = min / 60.0; // equivalente em horas
		double resto = 0.0;

		double intHora = (int) hora; // horas inteiras
		resto = hora - intHora; // resto, em horas

		double intMin = (int) (resto * 60); // minutos inteiros
		resto = resto - (intMin / 60); // resto, em horas

		double intSec = (int) (resto * 60 * 60); // segundos inteiros

		NumberFormat formatter = NumberFormat.getIntegerInstance();

		formatter.setMinimumIntegerDigits(2);
		formatter.setMaximumFractionDigits(0);

		String tempo = formatter.format(intHora) + ":" + formatter.format(intMin) + ":" + formatter.format(intSec);

		return tempo;

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String nomeDoMes(int numMes) {

		if (numMes < 0 || numMes > 11) {
			return "";
		}

		String ret = "";

		switch (numMes) {
		case 0:
			ret = "Janeiro";
			break;
		case 1:
			ret = "Fevereiro";
			break;
		case 2:
			ret = "Mar\u00E7o";
			break;
		case 3:
			ret = "Abril";
			break;
		case 4:
			ret = "Maio";
			break;
		case 5:
			ret = "Junho";
			break;
		case 6:
			ret = "Julho";
			break;
		case 7:
			ret = "Agosto";
			break;
		case 8:
			ret = "Setembro";
			break;
		case 9:
			ret = "Outubro";
			break;
		case 10:
			ret = "Novembro";
			break;
		case 11:
			ret = "Dezembro";
			break;
		default:
			ret = null;
		}
		return ret;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static StringBuilder numeroToString(Long numero, int tamanho) {

		String str = numero.toString();
		StringBuilder sb = new StringBuilder(tamanho + 1);

		int len = str.length();

		for (int i = 0; i < tamanho - len; i++) {
			sb.append('0');
		}

		sb.append(str);

		return sb;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String dataPorExtensao(Date date) {

		GregorianCalendar data = new GregorianCalendar();
		data.setTime(date);
		int mes = data.get(GregorianCalendar.MONTH);

		return data.get(GregorianCalendar.DATE) + " de " + meses[mes] + " de " + data.get(GregorianCalendar.YEAR);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String mascaraGenerica(String str, String mask) {

		if (str == null) {
			return null;
		}

		if (mask == null) {
			return str;
		}

		int i = str.length();
		int j = mask.length();

		Stack<String> pilha = new Stack<String>();

		for (; i > 0 && j > 0; i--, j--) {
			if (mask.substring(j - 1).equals("#")) {

				pilha.push(str.substring(i - 1, i));
			} else {
				pilha.push(mask.substring(j - 1, j));
				i++;
			}
		}
		if (i > j) {
			pilha.push(str.substring(0, i));
		}

		StringBuilder retorno = new StringBuilder();

		while (!pilha.isEmpty()) {
			retorno.append(pilha.pop());
		}

		return retorno.toString();

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String completarComCaracter(String arg, String caracter, int tamanho, boolean esquerda) {

		String retorno = arg;
		int size = tamanho - arg.length();

		for (int i = 1; i <= size; i++) {
			if (esquerda) {
				retorno = caracter.concat(retorno);
			} else {
				retorno = retorno.concat(caracter);
			}
		}
		return retorno;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarNumeroMonetario(BigDecimal number) {

		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		formatter.setMinimumFractionDigits(2);

		return formatter.format(number).replace("R$ ", "");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarNumeroDecimal(double number, int casasDecimais) {

		NumberFormat formatter = new DecimalFormat("#0.0");

		formatter.setMinimumFractionDigits(casasDecimais);

		return formatter.format(number);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Date geraObjDate(Date dia, String hora) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfDataHora = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		return sdfDataHora.parse(sdf.format(dia) + hora);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static BigDecimal formataBigDecimal(String value) throws ApplicationException {

		if (value == null || value.isEmpty()) {
			return null;
		}

		Locale brasil = new Locale("pt", "BR");
		DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));

		df.setParseBigDecimal(true);

		try {

			BigDecimal b1 = (BigDecimal) df.parse(value);

			return b1;

		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isListaNaoVazia(Collection lista) {
		return lista != null && !lista.isEmpty();
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isCodigoValido(String codigo) {
		return codigo != null && !"".equals(codigo.trim()) && !"-1".equals(codigo.trim());
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataCEP(String cep) {
		try {
			return mascaraGenerica(cep, "#####-###");
		} catch (Exception e) {
			return cep;
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataTelefone(String numFone) {

		try {
			if (numFone != null && numFone.length() == 0) {
				return mascaraGenerica(numFone, "####-####");
			} else if (numFone != null) {
				return numFone;
			} else {
				return "";
			}
		} catch (Exception e) {
			return numFone;
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formataTelefone(String ddd, String numero) {

		try {
			String numeroCompleto = "";

			if (ddd != null && !ddd.isEmpty()) {
				numeroCompleto = "(".concat(ddd).concat(") ");
			}
			if (numero != null && !numero.isEmpty()) {
				numeroCompleto = numeroCompleto.concat(mascaraGenerica(numero, "####-####"));
			}

			return numeroCompleto;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarProtocoloAAX(Integer protocolo) {
		String numProtocolo = null;

		if (protocolo != null) {
			numProtocolo = protocolo.toString();
		} else {
			return "00.000.000-0";
		}

		return Util.formatString(numProtocolo, "##.###.###-#");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Double safeDouble(String numero) throws ApplicationException {

		try {
			if (StringUtils.isNotBlank(numero)) {
				return Double.valueOf(numero);
			}

			return null;
		} catch (NumberFormatException ne) {
			throw new ApplicationException(ne);
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean safeBoolean(String value) throws ApplicationException {

		try {
			if (StringUtils.isNotBlank(value)) {
				return Boolean.valueOf(value);
			}

			return null;
		} catch (NumberFormatException ne) {
			throw new ApplicationException(ne);
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isInteger(String str) {

		try {
			if (str.length() > 9) {
				for (int i = 0; i < str.length(); i++) {
					Integer.parseInt(str.substring(i, i + 1));
				}
			} else {
				Integer.parseInt(str);
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Date gerarObjetoDate(String data, String padrao) throws ApplicationException {

		Date objeto = null;

		if (StringUtils.isBlank(data) || StringUtils.isBlank(padrao)) {
			return objeto;
		}

		try {
			objeto = new SimpleDateFormat(padrao).parse(data);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return objeto;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Date gerarObjetoDate(String data) throws ApplicationException {

		Date objeto = null;
		if (StringUtils.isBlank(data)) {
			return objeto;
		}

		try {
			objeto = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return objeto;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static <T> List<T> setToList(Set<T> set) {
		if (set == null || set.isEmpty()) {
			return new ArrayList<T>(0);
		}
		return new ArrayList<T>(set);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static <T> Set<T> listToSet(List<T> list) {

		if (list == null || list.isEmpty()) {
			return new LinkedHashSet<T>(0);
		}
		return new LinkedHashSet<T>(list);
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> getList(T... objects) {
		List<T> lista = new ArrayList<T>(objects.length);

		for (T obj : objects) {
			lista.add(obj);
		}
		return lista;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	@SafeVarargs
	public static <T> Set<T> getSet(T... objects) {

		Set<T> set = new LinkedHashSet<T>(objects.length);

		for (T obj : objects) {
			set.add(obj);
		}

		return set;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String safeToString(Object object) {

		if (object != null) {
			return object.toString();
		}

		return "";
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean dataMaior(Date data1, Date data2) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) > 0;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean dataMenor(Date data1, Date data2) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) < 0;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Boolean dataIgual(Date data1, Date data2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

		return fmt.format(data1).compareTo(fmt.format(data2)) == 0;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
//	@SuppressWarnings("deprecation")
	public static double arredondaValor(double valor, int nrCasasDecimais) {

		BigDecimal bigDecimal = BigDecimal.valueOf(valor);
		bigDecimal = bigDecimal.setScale(nrCasasDecimais, BigDecimal.ROUND_HALF_UP);

		return bigDecimal.doubleValue();

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
//	@SuppressWarnings("deprecation")
	public static double arredondaValor(double valor, int nrCasasDecimais, int roundMod) {

		BigDecimal bigDecimal = BigDecimal.valueOf(valor);
		bigDecimal = bigDecimal.setScale(nrCasasDecimais, roundMod);

		return bigDecimal.doubleValue();

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String getExtensao(String nomeArquivo) {

		int lastIndex = nomeArquivo.lastIndexOf('.');
		int length = nomeArquivo.length();
		lastIndex = lastIndex == -1 ? length : lastIndex;
		String extensao = nomeArquivo.substring(lastIndex, length);

		return extensao;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String formatarBytes(long size) {

		if (size < 1024) {
			return String.valueOf(size + " bytes");
		} else if (size >= 1024 && size < 1048576) {
			return String.valueOf(size / 1024 + " KB");
		} else {
			return String.valueOf(size / 1024 / 1024 + " MB");
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String extrairDddTelefone(String telefone) {

		if (telefone == null || telefone.isEmpty() || telefone.length() < 8) {
			return null;
		}

		int a1 = telefone.indexOf('(');
		int a2 = telefone.indexOf(')');

		String ddd = telefone.substring(a1 + 1, a2);

		return ddd;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String extrairNumeroTelefone(String telefone) {

		if (telefone == null || telefone.isEmpty() || telefone.length() < 8) {
			return null;
		}

		int a1 = telefone.indexOf(')');
		String numero = telefone.substring(a1 + 1).trim();
		numero = StringUtil.removerTracoPontoEspacoBarra(numero);

		return numero;

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String removerZerosEsquerda(String numero) {

		if (numero == null || numero.isEmpty()) {
			return null;
		}

		return numero.replaceAll("^0*", "");
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Double removerFormatacaoMoeda(String valorFormatado) {

		try {
			String valorLimpo = valorFormatado.replace(".", "").replace(',', '.');
			Double valor = Util.safeDouble(valorLimpo);

			return valor;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String stackTraceToString(Throwable e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		e.printStackTrace(pw);

		return sw.toString();

	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String stackTraceToString(ApplicationException e) {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		if (e.getRootCause() != null) {
			e.getRootCause().printStackTrace(pw);
		} else if (e.getCause() != null) {
			e.getCause().printStackTrace(pw);
		} else {
			e.printStackTrace(pw);
		}

		return sw.toString();
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Date removeDias(Integer qtdeDias, Date data) {

		if (data == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) - qtdeDias));

		return calendar.getTime();
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean stringIsNull(String s) {
		return (s == null || "".equals(s.trim()));
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static Date obterUltimoDiaUtil(Date dataAtual) {
		Calendar data = Calendar.getInstance();
		data.setTime(dataAtual);

		do {
			data.set(Calendar.DATE, data.get(Calendar.DATE) - 1);
		} while (data.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| data.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

		return data.getTime();
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String obterSubstring(Integer indiceInicial, Integer indiceFinal, String dados) {

		if (StringUtils.isNotBlank(dados)) {
			if (indiceFinal != null) {
				if (dados.length() >= indiceFinal) {
					return dados.substring(indiceInicial, indiceFinal).trim();

				} else if (dados.length() > indiceInicial) {
					return dados.substring(indiceInicial).trim();
				}
			} else {
				if (dados.length() >= indiceInicial) {
					return dados.substring(indiceInicial).trim();
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static String upperCaseString(String texto) {

		try {
			if (StringUtils.isNotBlank(texto)) {
				return texto.trim().toUpperCase();
			}

			return "";
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isCPFValid(String valor) {

		valor = Util.soNumeros(valor);
		if (Util.isBlank(valor) || valor.length() != 11) {
			return false;
		}

		String c = valor.substring(0, 9);
		String dv = valor.substring(9, 11);
		Integer d1 = 0;
		for (Integer i = 0; i < 9; i++) {
			d1 += Integer.parseInt("" + c.charAt(i)) * (10 - i);
		}

		if (d1 == 0) {
			return false;
		}

		d1 = 11 - (d1 % 11);

		if (d1 > 9) {
			d1 = 0;
		}

		if (Integer.parseInt("" + dv.charAt(0)) != d1) {
			return false;
		}
		d1 *= 2;

		for (Integer i = 0; i < 9; i++) {
			d1 += Integer.parseInt("" + c.charAt(i)) * (11 - i);
		}

		d1 = 11 - (d1 % 11);
		if (d1 > 9) {
			d1 = 0;
		}
		if (Integer.parseInt("" + dv.charAt(1)) != d1 || valor.equals("11111111111") || valor.equals("22222222222")
				|| valor.equals("33333333333") || valor.equals("44444444444") || valor.equals("55555555555")
				|| valor.equals("66666666666") || valor.equals("77777777777") || valor.equals("88888888888")
				|| valor.equals("99999999999")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	public static boolean isCNPJValid(String cnpj) {

		try {
			if (StringUtils.isNotBlank(cnpj)) {

				int soma = 0, dig;

				String unformatedCnpj = StringUtil.removeFormatacao(cnpj);

				if (StringUtil.isSameCharacter(unformatedCnpj)) {
					return false;
				}

				String cnpj_calc = unformatedCnpj.substring(0, 12);

				char[] chr_cnpj = unformatedCnpj.toCharArray();

				/* Primeira Parte */
				for (int i = 0; i < 4; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
					}
				}
				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
						soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
					dig = 11 - (soma % 11);

					cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				}

				/* Segunda Parte */
				soma = 0;
				for (int i = 0; i < 5; i++) {
					if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
						soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
					}
				}

				for (int i = 0; i < 8; i++) {
					if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
						soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
					dig = 11 - (soma % 11);

					cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
				}

				if (unformatedCnpj.equals(cnpj_calc)) {
					return true;
				}
				return false;
			}
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isEmailValid(String email) {

		if ((email == null) || (email.trim().length() == 0)) {
			return false;
		}

		String emailPattern = "\\b(^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";
		;
		Pattern pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	public static <T> Boolean isEmptyArray(T[] array) {
		return (array == null || array.length == 0);
	}

	public static StringBuilder replacePrimerioAndPorWhere(StringBuilder builder) throws ApplicationException {
		if (builder == null) {
			return builder;
		}

		if (builder.toString().contains("AND")) {

			builder = builder.replace(builder.indexOf("AND"), builder.indexOf("AND") + 3, "WHERE");
		} else if (builder.toString().contains("and")) {
			builder = builder.replace(builder.indexOf("and"), builder.indexOf("and") + 3, "WHERE");
		}
		return builder;

	}

	public static boolean validarTelefoneFixo(String telefone) throws ApplicationException {

		String t = soNumeros(telefone);
		if (Util.isBlank(t)) {
			return true;
		}

		if (!validarDDDTelefone(t.substring(0, 2))) {
			throw new ApplicationException("message.generica", new String[] { "O DDD informado não existe." },
					FacesMessage.SEVERITY_ERROR);
		}

		// tamanho invalido
		if (t.length() != 10) {
			throw new ApplicationException("message.generica",
					new String[] { "A quantidade de dígitos do telefone residencial deve ser 8." },
					FacesMessage.SEVERITY_ERROR);
		}

		Integer primeiroDigito = Integer.valueOf(t.charAt(2));
		if (primeiroDigito < 2 || primeiroDigito > 5) {
			throw new ApplicationException("message.generica",
					new String[] { "O primeiro dígito do número do telefone deve ser 2, 3, 4 ou 5." },
					FacesMessage.SEVERITY_ERROR);
		}

		return true;
	}

	public static boolean validarDDDTelefone(String ddd) {
		if (Util.isBlank(ddd)) {
			return false;
		}

		Integer num = Integer.valueOf(ddd);
		if (num < 11 || num > 99) {
			return false;
		}
		return false;
	}

	public static boolean validarTelefoneCelular(String telefone) throws ApplicationException {

		String t = soNumeros(telefone);
		if (Util.isBlank(t)) {
			return true;
		}

		// DDD invalido
		if (!validarDDDTelefone(t.substring(0, 2))) {
			throw new ApplicationException("message.generica", new String[] { "O DDD informado não existe." },
					FacesMessage.SEVERITY_ERROR);
		}
		Integer primeiroDigito = Integer.valueOf(t.charAt(2) + "");
		Integer ddd = Integer.valueOf(t.substring(0, 2));

		// grupo de DDDs que já possuem o nono dígito
		if (((ddd >= 81 && ddd <= 28) || (ddd >= 31 && ddd <= 38) || (ddd >= 71 && ddd <= 79)
				|| (ddd >= 81 && ddd <= 99))) {

			if (t.length() != 11) {
				throw new ApplicationException("message.generica",
						new String[] { "Quantidade de dígitos imcompatível com o DDD informado" },
						FacesMessage.SEVERITY_ERROR);
			}
			if (primeiroDigito != 9) {
				throw new ApplicationException("message.generica",
						new String[] { "O primeiro dígito do número do telefone deve ser 9." },
						FacesMessage.SEVERITY_ERROR);
			}
		}

		// Grupo de DDDs que NÃO possuem o nono dígito
		if (ddd >= 41 && ddd <= 69) {
			if (t.length() != 10) {
				throw new ApplicationException("message.generica",
						new String[] { "Quantidade de dígitos imcompatível com o DDD informado" },
						FacesMessage.SEVERITY_ERROR);
			}
			if ((primeiroDigito > 9 || primeiroDigito < 7)) {
				throw new ApplicationException("message.generica",
						new String[] { "O primeiro dígito do número do telefone deve ser 9, 8 ou 7." },
						FacesMessage.SEVERITY_ERROR);
			}
		}

		return true;
	}

	public static boolean validarData(String data) {
		boolean retorno = true;

		if (StringUtils.isBlank(data)) {
			retorno = false;
		} else {
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			format.setLenient(false);
			try {
				format.parse(data);
			} catch (Exception e) {
				retorno = false;
			}
		}
		return retorno;
	}

	public static Boolean comparar(Object o1, Object o2) {
		if (isNull(o1) && isNull(o2)) {
			return Boolean.TRUE;
		}
		if (isNotNull(o1) && isNotNull(o2)) {
			if (o1 instanceof String && o2 instanceof String) {
				return o1.toString().equalsIgnoreCase(o2.toString());
			}
			return o1.equals(o2);
		}
		return Boolean.FALSE;
	}

	public static BigDecimal converterParaBigDecimal(String value) throws ApplicationException {

		String separador = value.contains(",") ? "," : ".";

		if (value.contains(".") && value.contains(",")) {
			value = value.replace(".", "");
		}

		return Util.converterParaBigDecimal(value, separador);

	}

	public static BigDecimal converterParaBigDecimal(String value, String decimalSeparator)
			throws ApplicationException {

		try {

			String ds = ".".equals(decimalSeparator) || ",".equals(decimalSeparator) ? decimalSeparator : "";
			// Se o valor for nulo ou vazio atribui um valor default
			boolean negativo = value.trim().startsWith("-");
			StringBuilder sbValor = new StringBuilder();
			if (StringUtils.isBlank(value)) {
				sbValor.append("0,00");
			} else {
				if (negativo) {
					sbValor.append(value.trim().replace("-", ""));
				} else {
					sbValor.append(value.trim());
				}
			}
			// Deleta números, ponto e vírgula
			String strValorAux = StringUtils.replaceChars(sbValor.toString(), "0123456789.,", "");

			// Teste se existe algum caracter que não foi apagado
			if (strValorAux.length() > 0) {
				throw new ApplicationException("mensagem.bigdecimal.invalido", new String[] { value });
			}

			if (sbValor.charAt(0) == '.' || sbValor.charAt(0) == '.') {
				sbValor.insert(0, "0");
			}

			if (sbValor.charAt(sbValor.length() - 1) == '.' || sbValor.charAt(sbValor.length() - 1) == ',') {
				sbValor.append("00");
			}
			int i = sbValor.length() - 1;
			char separador;

			String parteInteira = "";
			StringBuilder parteFracionaria = new StringBuilder();

			if (!StringUtils.isEmpty(ds)) {
				// percorre o valor até encontrar uma vírgula ou ponto e assume como
				// separador da parte fracionária
				while (i >= 0) {
					separador = sbValor.charAt(i);
					parteFracionaria.insert(0, separador);

					if (separador == ds.charAt(0)) {
						break;
					}
					i--;
				}
			}
			// Se o comprimento da parte fracionária for menor que o comprimento
			// do valor, então existe um separador

			if (parteFracionaria.length() < sbValor.length()) {

				parteInteira = StringUtils.replaceChars(sbValor.substring(0, i), ",.", "");
				parteFracionaria = new StringBuilder(StringUtils.replaceChars(parteFracionaria.toString(), ",", "."));
			} else {
				parteInteira = sbValor.toString();
				parteFracionaria = new StringBuilder(".00");
			}

			BigDecimal result = new BigDecimal(parteInteira + parteFracionaria);

			if (negativo) {
				result = result.negate();
			}

			return result;
		} catch (Exception e) {
			throw new ApplicationException("mensagem.bigdecimal.invalido", new String[] { value }, e);
		}
	}

	public static UIComponent findComponentPrimefaces(String id) throws ApplicationException {

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			UIViewRoot root = context.getViewRoot();
			final UIComponent[] found = new UIComponent[1];

			root.visitTree(VisitContext.createVisitContext(context), new VisitCallback() {

				@Override
				public VisitResult visit(VisitContext context, UIComponent component) {
					if (component.getId().equals(id)) {
						found[0] = component;
						return VisitResult.COMPLETE;
					}
					return VisitResult.ACCEPT;
				}

			});

			return found[0];
		} catch (Exception e) {
			throw new ApplicationException("message.default", e);
		}
	}

//	@SuppressWarnings("deprecation")
	public static Double diferencaEmHoras(Date dataInicio, Date dataFim) {

		double result = 0;
		long diferenca = dataFim.getTime() - dataInicio.getTime();
		long diferencaEmHoras = (diferenca / 1000) / 60 / 60;
		long minutosRestantes = (diferenca / 1000) / 60 % 60;
		result = new Double(diferencaEmHoras + "." + minutosRestantes);

		return result;
	}

	public static List<Integer> sortearNumeroAleatorio(int maxSorteios, int numeroMax) {

		// Lista para guardar os numeros.
		ArrayList<Integer> numeros = new ArrayList<Integer>(maxSorteios);
		Random random = new Random();
		// Controla se o loop ainda é valido.
		boolean continuarLoop = true;
		while (continuarLoop) {
			// Se a lista estiver do tamanho de maxSorteios termina o loop.
			if (numeros.size() == maxSorteios)
				break;
			// Escolhe um numero entre 0 e numeroMax.
			int sortear = random.nextInt(numeroMax);
			boolean integrar = true;
			// Verifica se o numero já esta na lista.
			for (Integer numero : numeros) {
				if (numero == sortear) {
					integrar = false;
					break;
				}
			}
			
			// Se integrar ainda estiver true adiciona o numero.
			if (integrar) {				
				numeros.add(sortear);
			}
		}		

		return numeros;
	}

	public static Integer sortearNumeroAleatorioUnico(int nuMax) {

		Random random = new Random();
		// Escolhe um numero entre 1 e nuMax.
		int sortear = random.nextInt(nuMax);
		if (sortear == 0) {
			sortear = random.nextInt(nuMax);
		}
		return sortear;
	}

}
