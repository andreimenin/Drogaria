package br.com.drogaria.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//CLASSE QUE SERVIRÁ PARA CONTROLAR A ABERTURA E O FECHAMENTO DO CAIXA, TAMBÉM
//O VALOR QUE FOI ABERTO


@Entity
@SuppressWarnings("serial")
public class Caixa extends GenericDomain{
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;
	
	@Column(nullable = false, unique = true)
	@Temporal(TemporalType.DATE)
	private Date dataAbertura;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFechamento;
	
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valorAbertura;


	public Date getDataAbertura() {
		return dataAbertura;
	}


	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}


	public Date getDataFechamento() {
		return dataFechamento;
	}


	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}


	public BigDecimal getValorAbertura() {
		return valorAbertura;
	}


	public void setValorAbertura(BigDecimal valorAbertura) {
		this.valorAbertura = valorAbertura;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	
	
	
}
