package Scraper.WebScraper;

import java.util.Date;
import java.util.Map;

import Scraper.Item;

public class Page extends Item<Object>{

	public static void main(String[]args) {
		Page pagina = new Page("www.facebook.com.br");
		pagina.putData("nome", "Facebook");
		pagina.putData("acesso", new Date());
		System.out.println(pagina.toString());
	}
	
	protected String url;
	
	public Page(String url) {
		super();
		this.url = url;
	}

	@Override
	public String toString() {
		return "Pagina [ "+ data +"]";
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

}
