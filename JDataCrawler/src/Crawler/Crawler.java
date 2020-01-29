package Crawler;



import java.util.Iterator;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Logger.Logger;
import Scraper.Item;
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
				produto.putData("Preco", this.extrairDado(elemento, "#productInfo > fieldset.item-price"));
				produto.putData("Descricao", this.extrairDado(elemento, ".item-description"));
				produto.putManyData(this.extrairDados(elemento, ".specs-list > li", " > strong", " > span"));

				return produto;
			}

		}, Logger.getINSTANCE()).start(new Scanner(System.in).nextLine());

	}

	protected String urlRoot;

	protected LinkedHashSet<String> urlVisited;

	protected Thread thread;

	protected WebScraper scraper;

	protected Logger logger;

	private Crawler() {
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


	Crawler(WebScraper scraper, Logger logger) {
		this(scraper);
		this.logger = logger;
		
	}

	Crawler(String nome, Logger logger) {
		this(nome);
		this.logger = logger;
	}

	
	Crawler(WebScraper scraper , Logger logger , LinkedHashSet<String> urlVisited){
		this(scraper , logger);
		this.urlVisited = urlVisited;
	}

	public void start(String url , String word) {
		this.word = word.toLowerCase().trim();
		this.start(url);
	}
	public void start(String url) {
		if (url == null)
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
		} catch (Exception e) {

		}
		// Thread.currentThread().destroy();
	}

	public Element requisitaHTML(String url) {
		return this.scraper.requisitaHTML(url);
	}

	private boolean ehInvalido(Element node) {
		return node == null || urlVisited.contains(node.baseUri());
	}

	public String word = new String();

	private boolean temWord() {
		return !word.isEmpty();
	}
	
	
	private boolean validar(Page page) {
				 
		if( page.temDados() && temWord() ) {
			
			for(Map.Entry<String, Object> entry: page.getData().entrySet()) {
				if(((String) entry.getValue()).trim().toLowerCase().contains(this.word)){
					return true;
				}else {
					return false;
				}
			}
		}else if( page.temDados() && !temWord()) {
			return true;
		}
		return false;
	}

	
	
	private String nodeSelector = "a[href]";
	private void trackingBFS(Element node) {

		if (this.ehInvalido(node))
			return; // verificacao inicial antes da procedencia do algoritmo

		Queue<Element> arm = new LinkedList<Element>(); //
		arm.add(node);

		int count = 0;
		do {

			Element head = arm.remove(); // desenfileira
			if (!this.ehInvalido(head)) {

				String url = head.baseUri(); // obtem url
				Page page = scraper.scrap(url); // extrai	
				this.urlVisited.add(url);// add a lista de url visitadas
			 
				boolean pageValida = this.validar(page);
//				System.out.println(pageValida+" "+ page+" "+ url);
				if (this.validar(page)) { //this.validar(page)
					this.logger.log(page.toString());
				}
				Elements elementos = node.select(this.nodeSelector);// captura e indexa elementos da pagina com propriedade "a[href]" 
				Iterator<Element> iterator = elementos.iterator();
				while (iterator.hasNext()) {
					Element elemento = iterator.next();
					arm.add(elemento);
				}
			}
		} while (!arm.isEmpty() && ++count < MAX);// condicao de parada do algoritmo

	}

	int MAX = 100000;

	private void tracking(Element node) {
		if (this.ehInvalido(node)) {
			return;
		}
		String url = node.baseUri();
		Page page = scraper.scrap(url);
		urlVisited.add(url);

		boolean pageValida = this.validar(page);
		//System.out.println(pageValida+" "+ page+" "+ url);
		
		if (this.validar(page)) {
			logger.log(page.toString());
		}
		
		
		
		Elements elementos = node.select(this.nodeSelector);
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