package Crawler;

import java.util.LinkedList;


import java.util.List;

import org.jsoup.select.Elements;

import Crawler.Crawler;
import Logger.Logger;
import Scraper.WebScraper.*;

public class WebDataCrawler extends Crawler{
	
	public static void main(String[]args) { // main teste unitario da classe WebDataCrawler
		WebDataCrawler wcrawler = new WebDataCrawler("mercado livre" , Logger.getINSTANCE(2));
		wcrawler.start("https://www.mercadolivre.com.br/");
	}
	
	
	private List<Crawler> crawlerList = new LinkedList<Crawler>();
		// armazenador de bots (spiders)

	WebDataCrawler(String nome , Logger logger){
		super(nome , logger);
	}
	WebDataCrawler(WebScraper scraper , Logger logger){
		super(scraper , logger);
	}
	

	@Override
	public void run() {

		Elements elementos = this.requisitaHTML(super.getUrl()).select("a[href]"); 
		// filtra elementos pelo selector css a[href]

		elementos.forEach(elemento->{ // busca exaustiva a todos os componentes a[href] da pagina
			Crawler crawler = new Crawler(super.scraper , super.logger , super.getPaginaMap()); //cria-se bot 
			crawler.start(requisitaHTML(elemento.absUrl("href")).baseUri()); //realiza requisicao da pagina href e 
																		//começa processo de busca referente ao href da iteração
			crawlerList.add(crawler);//armazena bot
		});	
//		while(!crawlerList.isEmpty()) {
//			System.out.println(".");
//			crawlerList.forEach(e->{
//				if(!e.isRunning()) {
//					this.paginaMap.putAll(e.getPaginaMap());
//					System.out.println("_______________________________________//______________________________-");
//					crawlerList.remove(e);
//				}
//			});
//		}
//		System.out.println(this.paginaMap);
	}
	
}