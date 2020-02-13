package Scraper;

import org.jsoup.nodes.Element;

public interface Scrapable<T extends Item<?>> {
	 abstract T scrap(String url , Element element);
}
   