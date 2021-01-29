package br.com.drogaria.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Venda extends GenericDomain {

	@Temporal(TemporalType.TIME)
	@Column
	private Date horario;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal valorTotal;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "venda") // ligando com o ManyToOne do objeto venda do ItemVenda
	private List<ItemVenda> itensVenda;

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

}
