package Scraper;

public interface Scrapable<T extends Item<?>> {
	 abstract T scrap(String url);
}
   