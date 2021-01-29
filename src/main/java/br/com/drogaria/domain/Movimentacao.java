package br.com.drogaria.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//CLASSE QUE SERVIRÁ PARA REGISTRAR AS MOVIMENTAÇÕES (SOMENTE DE ENTRADA)
														//NÃO TERÁ DE SAÍDA


@SuppressWarnings("serial")
@Entity
public class Movimentacao extends GenericDomain{

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date horario;
	
	
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;


	public Date getHorario() {
		return horario;
	}


	public void setHorario(Date horario) {
		this.horario = horario;
	}


	public BigDecimal getValor() {
		return valor;
	}


	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	
	
	
}
