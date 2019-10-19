package Scraper;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Item<T>{
	
	protected Map<String,T> data;//estrutura de dados map para alocação de informação estruturada em Chave/Valor
	
	protected Item(){
		super();
		data = new LinkedHashMap<String,T>();
	}
	
	public Map<String,T> getData() {
		return this.data;
	}
	
	public void putManyData(Map<String, T> map) {
		map.keySet().removeAll(data.keySet()); // remove do conjunto map todas as informacoes previamente cadastradas
		data.putAll(map);// insere informacoes
	}
	
	public void putData(String chave, T valor) {
		if (chave == null || valor == null || chave.length() == 0 || valor.equals(""))
			return;
		data.put(chave, valor); // insere informacao
	}
	
	
	public boolean temInfo() {
		return this.data.size() > 0;
	}

	
	public Map<String,T> getInfo(){
		return this.data;
	}


	
}
