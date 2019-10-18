package Scraper.WebScraper;

import Scraper.WebScraper.MercadoLivre.MercadoLivreWebScraper;
import Scraper.WebScraper.Netshoes.NetshoesWebScraper;

public class WebScraperFactory {

	private WebScraperFactory() {}
	
	private static WebScraperFactory INSTANCE = new WebScraperFactory();
	
	public static WebScraperFactory getInstance() {
		return INSTANCE;
	}
	
	public WebScraper getWebScraper(String nome) {
		
		nome = nome.trim().toLowerCase();
		
		if( nome.equals("netshoes"))
			return new NetshoesWebScraper();
		else if( nome.equals("mercado livre"))
			return new MercadoLivreWebScraper();
		
		return null;
		
	}

	
	public static void main(String[]args) {
		System.out.println(WebScraperFactory.getInstance().getWebScraper("Netshoes").getClass());
	}
}
