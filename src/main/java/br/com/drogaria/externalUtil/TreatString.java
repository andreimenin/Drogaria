package br.com.drogaria.externalUtil;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("deprecation")
public class TreatString implements Serializable{

	private static final long serialVersionUID = 3446898641870407677L;

	public static final Boolean isSequenceStringExists(String string, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher mat = p.matcher(string);
		if(mat.matches()) {
			return Boolean.TRUE;
		}
		else {
			return Boolean.FALSE;
		}
	}
	
	public static final String formataTelefone(String telefone) {
		
		if(telefone == null) {
			return "";
		}
		if(telefone.length() == 8) {
			return telefone.substring(0, 4) + "-" + telefone.substring(4);
		}
		if(telefone.length() == 9) {
			return telefone.substring(0, 5) + "-" + telefone.substring(5);
		}
		if(telefone.length() == 10) {
			return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + " - " + telefone.substring(6);
		}
		if(telefone.length() == 11) {
			return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + " - " + telefone.substring(7);
		}		
		return telefone;		
	}
	
	
	public static final Boolean isEmailValid(String email) {
		if(email == null) {
			return Boolean.FALSE;
		}
		String regex = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";
		return isSequenceStringExists(email, regex);
	}
	
	
	public static final String filterOnlyNumber(String value) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		return value.replaceAll("\\D", "");
	}
	
	public static String md5(String value) {
		return encryptsValue(value, Algorithm.MD5);
	}
	
	public static String randomId() {
		return UUID.randomUUID().toString();
	}
	
	public static String encryptsValue(String value, Algorithm algorithm) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(algorithm.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		
		md.reset();
		
		byte[] b = md.digest(value.getBytes(StandardCharsets.UTF_8));
		String hexa = "0123456789ABCDEF";
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < b.length; i++) {
			int j = ((int) b[i]) & 0xFF;
			sb.append(hexa.charAt(j / 16));
			sb.append(hexa.charAt(j % 16));
		}
		return sb.toString();
	}
	
	public static final String escapeSequencia(String value) {
		if(StringUtils.isBlank(value)) {
			return value;
		}
		
		String retorno = StringEscapeUtils.unescapeJava(escapeHTML(value));
		
		retorno = retorno.replaceAll("\n", "");
		retorno = retorno.replaceAll("\t", " ");
		retorno = retorno.replaceAll("\r", "");
		
		return retorno;
	}
	
	public static final String escapeHTML(String value) {
		if(value == null) {
			return null;
		}
		
		return value.replaceAll("\\<[^>]*>", "");
	}
	
	public static final Boolean isAnythingIsNull(Object...objects) {
		for(Object object : objects) {
			if(object == null) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public static final String completeZeroToLeft(String value, Integer size) {
		return completeToLeft(value, size, "0");
	}
	
	public static final String completeToLeft(String value, Integer size, String preencherCom) {
		if(StringUtils.isBlank(value)) {
			return null;
		}
		if(size.equals(value.length())) {
			return value;
		}
		StringBuilder sb = new StringBuilder(value);
		while(sb.length() < size) {
			sb.insert(0, preencherCom);
		}
		return sb.toString();
	}
	
	public static String completeToRight(String value, Integer size, String preencherCom) {
		
		if(StringUtils.isBlank(value)) {
			return null;
		}
		if(size.equals(value.length())) {
			return value;
		}
		StringBuilder sb = new StringBuilder(value);
		while(sb.length() < size) {
			sb.append(preencherCom);
		}
		return sb.toString();
	}
	
	public static final StringBuilder replace(String tag, StringBuilder template, Object value) {
		
		if(value == null) {
			value = "";
		}
		Integer fromIndex = null;
		while((fromIndex = template.indexOf(tag)) != -1) {
			template.replace(fromIndex, fromIndex + tag.length(), value.toString());
		}
		return template;		
	}
	
	public static final void checkKey(String algoritmo, String secretKey, String rawpayload, byte[] sig) {
		try {
			SecretKey secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), algoritmo);
			Mac mac = Mac.getInstance(algoritmo);
			mac.init(secretKeySpec);
			byte[] mysig = mac.doFinal(rawpayload.getBytes(StandardCharsets.UTF_8));
			
			if(!Arrays.equals(mysig, sig)) {
				throw new IllegalStateException("Non-matching signature for request");
			}			
			
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Unknown hash algorithm " + algoritmo, e);
		}
		catch(InvalidKeyException e) {
			throw new IllegalStateException("Wrong key for " + algoritmo, e);
		}		
	}
	
	public static String formatString(String str, String mask) {
		if(str == null || str.trim().isEmpty()) {
			return "";
		}
		
		try {
			MaskFormatter mf = new MaskFormatter(mask);
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(str);
		} catch (ParseException e) {
			throw new RuntimeException("Erro ao formatar string." + e.getMessage(), e);
		}
	}
	
	public static String subString(String string, int tamanho) {
		if(string == null) {
			return null;
		}
		if(string.length() > tamanho) {
			return string.substring(0, tamanho);
		}
		return string;
	}
	
	public static Boolean isNameValid(String nomeCompleto) {
		if(StringUtils.isBlank(nomeCompleto)) {
			return Boolean.FALSE;
		}
		String[] partes = nomeCompleto.split(" ");
		int partesIdentificadas = 0;
		for(String parte : partes) {
			if(StringUtils.isBlank(parte)) {
				continue;
			}
			partesIdentificadas++;
		}
		return partesIdentificadas > 1;
		}
	
	public static String traduzirColuna(String nomeColuna) {
		return nomeColuna;
	}
	
	public static String removerAcentos(String string) {
		return Normalizer.normalize(string, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
	}
	
	public static String toUpper(String observacoes) {
		if(StringUtils.isBlank(observacoes)) {
			return null;
		}
		return observacoes.toUpperCase();
	}
	
	public static String treatBlank(String rg) {
		if(rg == null) {
			return "";
		}
		return rg;
	}
	
	public static String geraEspaco(Integer cont) {
		if(cont == null || (cont != null && cont.equals(0))) {
			return "";
		}
		else {
			StringBuilder sb = new StringBuilder();
			for(int i = 1; i <= cont; i++) {
				sb.append(' ');
			}
			return sb.toString();
		}
	}
	
	public static String geraCaracteresHtml(Integer cont, String html) {
		if(cont == null || (cont != null && cont.equals(0))) {
			return "";
		}
		else {
			StringBuilder sb = new StringBuilder();
			for(int i = 1; i <= cont; i++) {
				sb.append(html);
			}
			return sb.toString();
		}
	}
	
	public static String capitalizar(String str) {
		if(!StringUtils.isBlank(str)) {
			return WordUtils.capitalizeFully(str);
		}
		
		return "";
	}
	
	public static String gerarNomeDeMetodo(String str) {
		if(!StringUtils.isBlank(str)) {
			str = TreatString.removerAcentos(str);
			return WordUtils.capitalize(str.replaceAll(" ", ""));
		}
		return "";
	}
	
	public static String convertTextToSlug(String texto) {
		if(StringUtils.isNotBlank(texto)) {
			Pattern NONLATIN = Pattern.compile("[^\\w-]");
			Pattern WHITESPACE = Pattern.compile("[\\s]");
			
			String nowhitespace = WHITESPACE.matcher(texto).replaceAll("_");
			String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
			String slug = NONLATIN.matcher(normalized).replaceAll("");
			return slug.toLowerCase();
		}
		else {
			return "";
		}
	}
	
	public static String replaceHtmlEntityCode(String palavra) {
		if(StringUtils.isNotBlank(palavra)) {
			palavra = palavra.replaceAll("&#8325;", "5").replaceAll("&#8325;", "x");
		}
		return palavra;
	}
	
	public static final StringBuilder replaceFirst(String tag, StringBuilder template, Object value) {
		
		if(value == null) {
			value = "";
		}
		Integer fromIndex = null;
		if((fromIndex = template.indexOf(tag)) != -1) {
			template.replace(fromIndex, fromIndex + tag.length(), value.toString());
		}
		return template;
	}
	
	public static String booleanToString(Boolean valor) {
		if(valor != null) {
			if(valor) {
				return "Sim";
			}
			else {
				return "Não";
			}
		}
		
		return null;
	}
	
	public static boolean isNumeric(String str) {
		
		if(str == null) {
			return false;
		}
		int sz = str.length();
		for(int i = 0; i < sz; i++) {
			if(Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	
	public String capitalizeWord(String texto) {
		if(StringUtils.isBlank(texto)) {
			return null;
		}
		
		String[] arrayTexto = texto.toLowerCase().split(" ");
		
		int index = 0;
		for(String str : arrayTexto) {
			if(str.length() > 3) {
				arrayTexto[index] = StringUtils.capitalize(str);
			}
			index ++;
		}
		return String.join(" ", arrayTexto);
	}
	
	
	private static enum Algorithm {
		MD2, MD5, SHA_1, SHA_256, SHA_384, SHA_512
	}
	
	
	
	
	
	
	
	
	
	
	
}
