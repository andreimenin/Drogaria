package br.com.drogaria.externalUtil;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public abstract class StringUtil {

	
	
	public static String removerTracoPontoEspacoBarra(String str) {
		
		String docFormatado = "";
		
		if(StringUtils.isNotBlank(str)) {
			Pattern p = Pattern.compile("[./-]");
			docFormatado = p.matcher(StringUtils.deleteWhitespace(str)).replaceAll("");
			
		}
		return docFormatado;		
	}
	
	public static boolean isSameCharacter(String s) {
		if(s != null && s.length() > 0) {
			char first = s.charAt(0);
			
			for(int i=0; i<s.length(); i++) {
				if(s.charAt(i) != first) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static String removeFormatacao(String s) {
		if(s != null) {
			s = s.replace(".", "");
			s = s.replace("/", "");
			s = s.replace("-", "");
			s = s.replace(" ", "");			
		}
		return s;
	}
	
}
