/**
 * 
 */
package br.com.jdracarys.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.jdracarys.exception.ProxyNotFoundException;
import br.com.jdracarys.logger.Logger;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: JDracarysProxy.java
 */
public abstract class JDracarysProxy {
	protected Document doc;
	protected List<HttpHost> proxies;

	/**
	 * @param proxies
	 * @param ip
	 * @param port
	 */
	protected void addProxy(String ip, int port) {
		try {
			proxies.add(new HttpHost(ip, port));
		} catch (Exception e) {
			System.out.println("Proxy not added: " + e.getMessage());
		}
	}

	/**
	 * @author Raneves método default para tratamento de proxy vindo de um txt simples
	 * @return proxys tratados
	 */
	protected List<HttpHost> defaultLoadTXT(String url) {
		proxies = new ArrayList<HttpHost>();
		try {
			doc = Jsoup.connect(url).get();
			if (doc != null && doc.hasText()) {
				proxies = loadSimpleTXTFormat(doc);
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}
			System.out.println("Total de Proxies "+ url+": " + proxies.size());
		} catch (IOException e) {
			Logger.onError("Lista de proxy não pôde ser recuperada: " + e.getMessage());
			e.printStackTrace();
		} catch (ProxyNotFoundException e) {
			Logger.onError("Nenhum proxy encontrado no documento: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Logger.onError("Exception Genérica: " + e.getMessage());
		}
		return proxies;
	}

	/**
	 * @author Raneves
	 * @param documento
	 *            contendo proxys em formato texto dentro de uma tag body
	 * @return retorna lista de proxys tratada.
	 */
	protected List<HttpHost> loadSimpleTXTFormat(Document doc) {
		String[] elements;
		elements = doc.text().split(" ");
		for (String element : elements) {
			String ip = element.split(":")[0];
			int port = Integer.parseInt(element.split(":")[1]);
			addProxy(ip, port);
		}
		return proxies;
	}
}
