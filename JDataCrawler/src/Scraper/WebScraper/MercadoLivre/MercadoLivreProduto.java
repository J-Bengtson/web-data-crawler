package Scraper.WebScraper.MercadoLivre;

import Scraper.WebScraper.Produto;

public class MercadoLivreProduto extends Produto{
	
	MercadoLivreProduto(String url){
		super(url);
	}
	
	@Override
	public String toString() {
		return "MercadoLivre"+super.toString();
	}
}
