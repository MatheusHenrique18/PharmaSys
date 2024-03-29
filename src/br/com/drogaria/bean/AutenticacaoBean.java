package br.com.drogaria.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.drogaria.dao.FuncionarioDAO;
import br.com.drogaria.domain.Funcionario;
import br.com.drogaria.util.FacesUtil;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	
	private Funcionario funcionarioLogado;

	public Funcionario getFuncionarioLogado() {
		if(funcionarioLogado == null) {
			funcionarioLogado = new Funcionario();
		}
		return funcionarioLogado;
	}

	public void setFuncionarioLogado(Funcionario funcionarioLogado) {
		this.funcionarioLogado = funcionarioLogado;
	}
	
	public void autenticar() {
		
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarioLogado = funcionarioDAO.autenticar(funcionarioLogado.getCpf(), 
					DigestUtils.md5Hex(funcionarioLogado.getSenha()));
			
			if(funcionarioLogado == null) {
				FacesUtil.addMsgErro("CPF e/ou senha incorretos.");
			}else {
				FacesUtil.addMsgInfo("Funcionário: "+ funcionarioLogado.getNome() +", autenticado com sucesso.");
			}
		}catch (RuntimeException ex) {
			FacesUtil.addMsgErro("Erro ao tentar autenticar no sistema: "+ ex.getMessage());
		}
		
	}
	
	public String sair() {
		funcionarioLogado = null;
		return "/pages/autenticacao.xhtml?faces-redirect=true";
	}
	
}
