package br.com.exemplo;

import java.io.Serializable;

public class Cliente implements Serializable{	
    /**
	 * 
	 */
	private static final long serialVersionUID = 82506698507693010L;
	
	
	private String nome;
    private String endereco;
    private String uf;
    private String documento;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	} 
	public String getDocumento() {
		return documento;
	}	
	public void setDocumento(String documento) {
		this.documento = documento;
	}
    
  }
