package Crawler;

import java.util.Iterator;


import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Logger.Logger;
import Scraper.WebScraper.*;

public class Crawler implements Runnable {

	public static void main(String[] args) {
		System.out.println("url");

		new Crawler(new WebScraper() {

			@Override
			public Produto scrap(String url) {
				Produto produto = new Produto(url);

				Element elemento = this.requisitaHTML(url);

				// add informacao extraida da pagina ao produto
				produto.putData("Nome", this.extrairDado(elemento, ".item-title"));
				produto.putData("Preço", this.extrairDado(elemento, "#productInfo > fieldset.item-price"));
				produto.putData("Descrição", this.extrairDado(elemento, ".item-description"));
				produto.putManyData(this.extrairDados(elemento, ".specs-list > li", " > strong", " > span"));

				return produto;
			}

		} , Logger.getINSTANCE(1)).start(new Scanner(System.in).nextLine());
		
	}

	protected String urlRoot;
	
	protected LinkedHashMap<String, Page> paginaMap;
	
	protected LinkedHashSet<String> urlVisited;
	
	protected Thread thread;
	
	protected WebScraper scraper;
	
	protected Logger logger;

	private Crawler() {
		super();
		this.paginaMap = new LinkedHashMap<String, Page>();
		this.urlVisited = new LinkedHashSet<String>();
		thread = new Thread(this);
	}
	
	private Crawler(String nome) {
		this();
		this.scraper = WebScraperFactory.getInstance().getWebScraper(nome);
	}
	
	private Crawler(WebScraper scraper) {
		this();
		this.scraper = scraper;
	}
	
	
	Crawler(String nome , Logger logger){
		this(nome);
		this.logger = logger;
	}
	
	Crawler(WebScraper scraper , Logger logger){
		this(scraper);
		this.logger = logger;
	}





	public void start(String url) {
		if(url == null)
			return;
		this.urlRoot = url;
		this.thread.start();
	}

	public void pausa() {
		this.thread.suspend();
	}

	public void resume() {
		this.thread.resume();
	}

	public boolean isRunning() {
		return this.thread.isAlive();
	}

	@Override
	public void run() {
		try {
			tracking(scraper.requisitaHTML(urlRoot));
		}catch(Exception e) {
			
		}
		//Thread.currentThread().destroy();
	}

	public Element requisitaHTML(String url) {
		
		return this.scraper.requisitaHTML(url);
	}

	private void tracking(Element node) {
		if (node == null && urlVisited.contains(node.baseUri())) {
			return;
		}
		Produto produto = (Produto) scraper.scrap(node.baseUri());
		urlVisited.add(node.baseUri());
		if (produto.temInfo()) {
			paginaMap.put(produto.getUrl(), produto);
			logger.log(produto.toString());
			//System.out.println(produto);
		} else {
			return;
		}

		Elements elementos = node.select("a[href]");
		Iterator<Element> iterator = elementos.iterator();
		while (iterator.hasNext()) {
			Element elemento = iterator.next();
			tracking(scraper.requisitaHTML(elemento.absUrl("href")));
		}

	}
	// GETTERS & SETTERS

	public String getUrl() {
		return urlRoot;
	}

	public void setUrl(String url) {
		this.urlRoot = url;
	}

	public LinkedHashMap<String, Page> getPaginaMap() {
		return paginaMap;
	}

	public void setPaginaMap(LinkedHashMap<String, Page> paginaMap) {
		this.paginaMap = paginaMap;
	}

	public LinkedHashSet<String> getUrlVisited() {
		return urlVisited;
	}

	public void setUrlVisited(LinkedHashSet<String> urlVisited) {
		this.urlVisited = urlVisited;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public WebScraper getScraper() {
		return scraper;
	}

	public void setScraper(WebScraper scraper) {
		this.scraper = scraper;
	}

}