package Scraper.WebScraper.MercadoLivre;

import java.io.IOException;

import java.util.Scanner;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Scraper.WebScraper.*;

public class MercadoLivreWebScraper implements WebScraper{
	
	public static void main(String[]args) 
	{
		MercadoLivreWebScraper mercadolivre_scraper = new MercadoLivreWebScraper();
		if(args.length == 0)
			System.out.print("Mercado Livre produto URL : ");
		Page produto = mercadolivre_scraper.scrap( (args.length > 0 ? args[0].trim() :  new Scanner(System.in).nextLine().trim()));	
		System.out.println();
		System.out.println(produto.toString());	
	}
	
	
	public MercadoLivreWebScraper()
	{
		super();
	}
	


	@Override
	public Page scrap(String url) {
		Produto produto = new MercadoLivreProduto(url);
		
		Element elemento = this.requisitaHTML(url);
		if(elemento == null) return produto;
		// add informacao extraida da pagina ao produto
		produto.putData( "Nome" , this.extrairDado(elemento, ".item-title"));
		produto.putData( "Preço" ,this.extrairDado(elemento, "#productInfo > fieldset.item-price"));
		produto.putData( "Descrição" , this.extrairDado(elemento, ".item-description"));
		produto.putManyData(this.extrairDados(elemento, ".specs-list > li", " > strong" , " > span"));
			
		return produto;
	}


	
}
