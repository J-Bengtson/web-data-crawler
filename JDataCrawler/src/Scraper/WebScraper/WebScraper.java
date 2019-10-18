package Scraper.WebScraper;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Scraper.Scrapable;

public interface WebScraper extends Scrapable<Page>{
	
	/*
	 * Metodo extrairInformacoes(url) faz a extração dos dados de uma página HTML
	 * atraves da identificação dos elementos pelos selectores CSS.
	 *
	 * Input: (String url) A url da página do produto será passada como input.
	 * Output: (Produto) Classe representa de forma genérica produto do e-commerce.
	 * 
	 */
	
	default Element requisitaHTML(String url) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			
		}
		return null;
	}
	
	default String extrairDado(Element document, String selector) {
		// Realiza extração do(s) dado(s) referente ao seletor CSS
		Elements elements = document.select(selector);
		return (elements.size() > 0) ? elements.get(0).text() : elements.text();
	}
	
	default Map<String, Object> extrairDados(Element document, String selector, String cssQueryKey , String cssQueryValue) 
	{
		// metodo é uma forma automatizada de captura em grande escala e padronizada. 
		// Atributos : selector é a query css a ser extraida do HTML, cssQueryKey é o completo do selector para capturar chave e cssQueryValue é o complemento do selector para capturar valor
		Map<String, Object> map = new LinkedHashMap<String, Object>(); // Estrutura de dados para alocação das
																		// informações por inserção Chave/Valor
																		// (dicionário)
		// Realiza extração do(s) dado(s) referente ao seletor CSS e armazena-os no map
		Elements elementoRoot = document.select(selector);
		int cout = 0;
		Iterator<Element> iterator = elementoRoot.iterator();
		while(iterator.hasNext()) {
			Element elemento = iterator.next();
			map.put( extrairDado(elemento , cssQueryKey)  , extrairDado(elemento , cssQueryValue));
		}
		return map;
	}

}
