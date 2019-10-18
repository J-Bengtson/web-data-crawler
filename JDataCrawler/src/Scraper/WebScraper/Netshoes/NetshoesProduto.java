package Scraper.WebScraper.Netshoes;

import Scraper.WebScraper.Produto;

public class NetshoesProduto extends Produto {

	public NetshoesProduto(String url) {
		super(url);
	}
	
	@Override
	public String toString() {
		return "ProdutoNetshoes"+super.toString();
	}

}
