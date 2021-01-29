package br.com.drogaria.enumeracao;

public enum TipoUsuario {
	ADMINISTRADOR, 
	GERENTE,
	BALCONISTA;
	
	
	//MÉTODO USADO PARA FORMATAÇÃO QUANDO FOR EXIBIR O O CAMPO 'TIPO'
	@Override
	public String toString() {

		switch (this) {
		case ADMINISTRADOR:
			return "Administrador";
			
		case GERENTE:
			return "Gerente";
		
		case BALCONISTA:
			return "Balconista";		

		default:
			return null;
		}		
	
	}
	
	
	
}

