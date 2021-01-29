package br.com.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;
import org.omnifaces.util.Messages;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

import br.com.drogaria.dao.CidadeDAO;
import br.com.drogaria.dao.EstadoDAO;
import br.com.drogaria.dao.PessoaDAO;
import br.com.drogaria.domain.Cidade;
import br.com.drogaria.domain.Estado;
import br.com.drogaria.domain.Pessoa;
import br.com.drogaria.externalUtil.Util;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean 
public class PessoasTabViewBean implements Serializable{
	
	private Pessoa pessoa;		 
	
	private Integer activeIndex = 0;
	
	private TabView tabView;
	
	private List<Estado> estados;

	private List<Cidade> cidades;
	
	private Estado estadoSelecionado;	
	
	private Boolean desativarCidade = Boolean.TRUE;
	
	private String cidadeHidden;
		
	@PostConstruct
	public void novo() {

		try {
			pessoa = new Pessoa();
			
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar um nova pessoa.");
		}

	}	
	
	public void popular() {

		desativarCidade = Boolean.TRUE;
		
		try {
			if (estadoSelecionado != null) {
				
				// Alteração da video aula 304
				 CidadeDAO cidadeDAO = new CidadeDAO();
				 cidades = cidadeDAO.buscarPorEstado(estadoSelecionado.getCodigo());	
				 
				 if(Util.isNotNull(cidades)) {
					 if(!cidades.isEmpty()) {
						 desativarCidade = Boolean.FALSE;
					 }
				 }

				System.out.println("Estado selecionado: " + estadoSelecionado.getNome());
				System.out.println("Total de cidades: " + cidades.size());
			} else {
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			System.out.println("Erro ao tentar listar as cidades.");
			erro.printStackTrace();
		}

	}	
	
	public String voltar(){
		
		String redirectPage = ""; 
		
		if (this.activeIndex == 0 || this.activeIndex == null) {
			redirectPage = "/pages/principal.jsf?faces-redirect=true"; 
		} else {
			this.setActiveIndex(--this.activeIndex);
		}
		
		getPessoa();
		return redirectPage;
	}
	
	
	public String continuar() throws Exception{

		System.out.println("Executou continuar");		
		Boolean isAbaFinal;
		isAbaFinal = Boolean.FALSE;
		try {				
			if(getActiveIndex().equals(2)) {
				isAbaFinal = Boolean.TRUE;
				salvar();				
			}
			
		} catch (Exception e) {			
			throw new Exception(e.getMessage());
		} finally {
			if(!isAbaFinal)
			this.setActiveIndex(++this.activeIndex);
		}	
		
		getPessoa();
		return StringUtils.EMPTY;
		
	}
	
	public void salvar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);

			Messages.addGlobalInfo("Pessoa salvo com sucesso: " + pessoa.getNome());

			pessoa = new Pessoa();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");// resetando a escolha no combobox
			
			//'reseta' o tabview para a primeira aba
			this.setActiveIndex(0);

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma pessoa.");
			erro.printStackTrace();
		}

	}
	
	
	public void controlarAbaSelecionada(TabChangeEvent event) {
	    TabView tv = (TabView) event.getComponent();	    
	    
	    this.activeIndex = tv.getChildren().indexOf(event.getTab());
	    
	    getPessoa();
	    
	}
	
	
	public Integer getActiveIndex() {
		if (this.activeIndex == null) {
			this.activeIndex = 0;
		}
		return activeIndex;
	}

	public void setActiveIndex(Integer activeIndex) {
		this.activeIndex = activeIndex;
	}
	
	public TabView getTabView() {
		return tabView;
	}

	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}	
	
	public Boolean getDesativarCidade() {
		return desativarCidade;
	}
	
	public void setDesativarCidade(Boolean desativarCidade) {
		this.desativarCidade = desativarCidade;
	}
	
	public String getCidadeHidden() {
		return cidadeHidden;
	}
	
	public void setCidadeHidden(String cidadeHidden) {
		this.cidadeHidden = cidadeHidden;
	}
	
}
