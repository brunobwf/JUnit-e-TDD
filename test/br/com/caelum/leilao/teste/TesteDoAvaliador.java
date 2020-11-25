package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.servico.Avaliador;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

@SuppressWarnings("deprecation")
public class TesteDoAvaliador {
	
private Avaliador leiloeiro;
private Usuario joao;
private Usuario jose;
private Usuario maria;
	
	@Before //Ele executa o método criaAvaliador antes do teste. Método precisa ser public para que o JUnit o veja.
	//Utlizando o before o Junit cria automaticamente não sendo necessário chamar o método toda vez, pois ele irá
	//fazer isso toda vez antes de um teste
	
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
	}
	
	
	@Before
	public void criaUsuarios() {
		 this.joao = new Usuario("João");
		 this.jose = new Usuario("José");
		 this.maria = new Usuario("Maria");
	}
	
	
	//Para que o JUNIT entenda o método de teste deve-se retirar o static e os argumentos, @Test é a anotação do JUNIT
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		//parte 1: cenario
		
		//Criamos uma classe que faz o leilão com os lances de modo que evite a repetição deo código e deixando
		//mais facil o entendimento. Classe responsavel por criar cenarios no teste
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao,250)
				.lance(jose,300)
				.lance(maria,400)
				.constroi();
		
		//parte 2: ação
		this.leiloeiro.avalia(leilao);
		
		//parte 3:validação
		double maiorEsperado = 400;
		double menorEsperado = 250;
		
		//Assert equals verifica o esperado e o que foi retornado, o 0,00001 é um delta pois o double tem problema de arredondamento
		//e o Junit vai aceitar essa pequena diferença entra os valores
		//Como double tem limites de precisão, a versão mais nova do JUnit pede para você passar 
		//o "tamanho da precisão aceitável".No caso, estamos passando 0.00001.
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(),0.00001);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(),0.00001);
		//assertThat(leiloeiro.getMenorLance(),equalTo(250)); - > Calculado e esperado -> Para isso é necessário HamCrest

	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {
		
		Leilao leilao = new Leilao("Playstation 3 novo");
		
		leilao.propoe(new Lance(joao,1000.0));
		leilao.propoe(new Lance(jose,2000.0));
		leilao.propoe(new Lance(maria,3000.0));
		
		this.leiloeiro.avalia(leilao);
		
		//Ao importar o static do assert não precisamos mais colocar Assert.assertEquals
		assertEquals(3000, leiloeiro.getMaiorLance(),0.00001);
		assertEquals(1000, leiloeiro.getMenorLance(),0.00001);

	}
	
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		
		Leilao leilao = new Leilao("Playstation 3 novo");
		leilao.propoe(new Lance(joao,1000.0));

		this.leiloeiro.avalia(leilao);
		
		assertEquals(1000, leiloeiro.getMaiorLance(),0.00001);
		assertEquals(1000, leiloeiro.getMenorLance(),0.00001);
	}
	
    @Test
    public void deveEncontrarOsTresMaioresLances() {
    	
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        this.leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(3, maiores.size());
        assertEquals(3, maiores.size());
        assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
        assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
        assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
    }
    
    
    //Só passara no teste se o esperado for ter uma exception da classe RuntimeException, elimina o uso de try catch
    //@Test(expected=IllegalArgumentException.class) -> Só passara no teste se chamar  esta classe que impede valores menores que 1
    @Test(expected = RuntimeException.class)
    public void naoDeveAvaliarSemLances() {
    	Leilao leilao = new CriadorDeLeilao().para("Play 3 ").constroi();
    	this.leiloeiro.avalia(leilao);
    	
    	//Este comando faz o teste falhar. - > Assert.fail();
    	 
    }
    

}
