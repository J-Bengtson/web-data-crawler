package Scraper.WebScraper.Netshoes;

import java.io.IOException;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import Scraper.Item;
import Scraper.WebScraper.*;


public class NetshoesWebScraper implements WebScraper
{
	
	
	public static void main( String[] args ) throws IOException 
	{
		//Acesse o diretorio onde se encontra o arquivo NetshoesWebScraper.jar
		// Execute o comando no terminal 'java -jar NetshoesWebScraper.jar (URL)' juntamente com a url da página
		//ou Execute o comando Execute o comando no terminal 'java -jar NetshoesWebScraper.jar' em seguida a url e tecle ENTER
		// exemplo : java -jar NetshoesWebScraper.jar 
		
		//exemplo de URLS : https://www.netshoes.com.br/camiseta-nike-breathe-spring-masculina-grafite-HZM-0689-266
		// https://www.netshoes.com.br/lampada-inteligente-para-barracas-tent-megalite-guepardo-lc0300-branco-221-9120-014	
		// https://www.netshoes.com.br/bola-de-futebol-campo-adidas-starlancer-v-branco-COL-3359-014
		NetshoesWebScraper netshoes_scraper = new NetshoesWebScraper();
		if(args.length == 0)
			System.out.print("Netshoes produto URL : ");
		Page produto = netshoes_scraper.scrap( (args.length > 0 ? args[0].trim() :  new Scanner(System.in).nextLine().trim()));	
		System.out.println();
		System.out.println(produto.toString());
	}
	
	

	// Utiliza-se biblioteca Jsoup e e-commerce escolhido foi Netshoes (www.netshoes.com.br).
	public NetshoesWebScraper(){
		super();
	}

	/*
	 * Metodo extrairInformacoes(url) faz a extração dos dados de uma página HTML atraves da identificação dos elementos pelos selectores CSS.
	 *
	 * Input: (String url) A url da página do produto será passada como input.
	 * Output: (Produto) Classe representa de forma genérica produto do e-commerce.
	 * 
	 */
	@Override
	public Produto scrap(String url) {
		NetshoesProduto produto = new NetshoesProduto(url);

		Element document = requisitaHTML(url); //Realiza requisição e captura da página
		
		
		//extrai dados e add atributo a produto
		produto.putData("Nome:" , extrairDado(document , ".short-description > [itemprop=name]"));
		produto.putData("Ref.:" , extrairDado(document, ".reference > span"));
		produto.putData("Preço:", extrairDado(document , "[itemprop=price]"));
		produto.putData("Descrição:" , extrairDado(document , "[itemprop=description]") );
		
		produto.putData("Gênero:" , extrairDado(document , ".attributes li:nth-child(2)"));
		produto.putData("Indicado para:" , extrairDado(document , ".attributes > li:nth-child(3)"));
		produto.putData("Origem:" , extrairDado(document ,  ".attributes > li:last-child"));			
		produto.putManyData( extrairDados(document , ".attributes > li" , " > strong" , ":not(strong)"));
		
	
		return produto; // retorna produto
	}

	


	
	
}
		
		
		
	
