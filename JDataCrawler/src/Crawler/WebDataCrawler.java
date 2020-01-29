package Crawler;

import java.util.LinkedList;


import java.util.List;

import javax.swing.JOptionPane;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Crawler.Crawler;
import Logger.Logger;
import Scraper.WebScraper.*;

public class WebDataCrawler extends Crawler{
	
	public static void main(String[]args) { // main teste unitario da classe WebDataCrawler		
		
		WebDataCrawler crawler = new WebDataCrawler(new WebScraper() {

			@Override
			public Page scrap(String url) {
				Page p = new Page(url);
				Element document = this.requisitaHTML(url);
				p.putData("headline", this.extrairDado(document, "#firstHeading"));
				
//				System.out.println(p);
				return p;
			}
			
		} , Logger.getINSTANCE());
		crawler.start("https://pt.wikipedia.org/wiki/Aprendizado_de_m%C3%A1quina" );
		
// 		WebDataCrawler crawler = new WebDataCrawler("mercado livre" , Logger.getINSTANCE());
//		crawler.start("http://www.mercadolivre.com.br");
//		
////		
//		WebDataCrawler crawler = new WebDataCrawler("netshoes" , Logger.getINSTANCE());
//		crawler.start("http://www.netshoes.com.br/");
//		
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
		
		Logger.getINSTANCE().log("starting...");
		if(!this.word.isEmpty())
			Logger.getINSTANCE().log("set word "+this.word);
		
		Element HTML = this.requisitaHTML(super.getUrl());

		if(HTML == null) {
	        JOptionPane.showMessageDialog (null, "Forbidden: You don't have permission to access [directory] on this server\n" + 
	        		"\n" + 
	        		"HTTP Error 403 – solution : Proxy");
	        	        
	        System.exit(1);
		}

		Elements elementos = HTML.select("a[href]"); // filtra elementos pelo selector css a[href]

		elementos.forEach(elemento -> { // busca exaustiva a todos os componentes a[href] da pagina
			try {
				Crawler crawler = new Crawler(super.scraper , super.logger , super.urlVisited); //cria-se bot
				String nextURL = elemento.absUrl("href");
//				System.out.println(nextURL);
				crawler.start(nextURL , this.word); //realiza requisicao da pagina href e 														//come�a processo de busca referente ao href da itera��o
				crawlerList.add(crawler);//armazena bot
			}catch( Exception e) {
				Logger.getINSTANCE().log("Exception " +e.toString());
			}
			
			
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