package br.com.drogaria.externalUtil;

public enum Modo implements EnumString{
	
	CONSULTAR  ("Consultar", "fa fa-list"),
	EXIBIR     ("Exibir", "fa fa-file-text-o"),
	EDITAR     ("Editar", "fa fa-pencil-square-o"),
	INCLUIR    ("Incluir", "fa fa-file-o"),
	CLONAR     ("Clonar", "fa fa-clone");
	
	private final String descricao;
	private final String icone;
	
	private Modo(String descricao, String icone) {
		this.descricao = descricao;
		this.icone = icone;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public String getIcone() {
		return icone;
	}	
	
	@Override
	public String getId() {
		return getDescricao();
	}
}
