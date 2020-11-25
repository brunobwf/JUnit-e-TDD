package br.com.caelum.leilao.builder;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;


//Classe responsavel por criar cenário para testes. Essas são chamadas Teste data builders pois diminuem o acoplamento
public class CriadorDeLeilao {
	
	private Leilao leilao;

	public CriadorDeLeilao para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}

	public CriadorDeLeilao lance(Usuario usuario, double valor) {
		leilao.propoe(new Lance(usuario,valor));
		return this;
	}
	
	public Leilao constroi() {
		return leilao; 
	}

}
