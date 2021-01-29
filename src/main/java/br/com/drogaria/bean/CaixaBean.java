package br.com.drogaria.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import br.com.drogaria.dao.CaixaDAO;
import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.domain.Caixa;
import br.com.drogaria.domain.Funcionario;

@ManagedBean
@ViewScoped
public class CaixaBean {

	private ScheduleModel caixasAbertos; // serve para guardar a agenda (do primeFaces)

	private Caixa caixa;

	private List<Funcionario> funcionarios;

	public ScheduleModel getCaixasAbertos() {
		return caixasAbertos;
	}

	public void setCaixasAbertos(ScheduleModel caixasAbertos) {
		this.caixasAbertos = caixasAbertos;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void listar() {

		caixasAbertos = new DefaultScheduleModel();
	}

	public void novo(SelectEvent evento) {

		caixa = new Caixa();
		caixa.setDataAbertura((Date) evento.getObject());

		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		funcionarios = funcionarioDAO.listar();

	}

	public void salvar() {

		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(caixa.getDataAbertura());
		calendar.add(Calendar.DATE, 1);
		caixa.setDataAbertura(calendar.getTime());
		
		
		CaixaDAO caixaDAO = new CaixaDAO();

		caixaDAO.salvar(caixa);

		Messages.addGlobalInfo("Caixa aberto com sucesso !");

	}

}
