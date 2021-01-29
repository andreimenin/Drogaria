package br.com.drogaria.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.primefaces.model.StreamedContent;

@SuppressWarnings("serial")
@Entity
public class Produto extends GenericDomain{

	@Column(nullable = false)
	private String descricao;
	
	@JoinColumn(nullable = false)
	@ManyToOne
	private Fabricante fabricante;	
	
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private Long quantidade;
	
	@Transient// não gera uma coluna no banco, é apenas para arquivos temporários
	private String caminhoFotoTemporaria;
	
	@Transient
	private StreamedContent foto;
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Fabricante getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}
	
	public String getCaminhoFotoTemporaria() {
		return caminhoFotoTemporaria;
	}
	
	public void setCaminhoFotoTemporaria(String caminhoFotoTemporaria) {
		this.caminhoFotoTemporaria = caminhoFotoTemporaria;
	}
	
	public StreamedContent getFoto() {
		return foto;
	}
	
	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}
	
}
