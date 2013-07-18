/**
 * 
 */
package br.com.jdracarys.proxy.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.jdracarys.exception.ProxyNotFoundException;
import br.com.jdracarys.logger.Logger;
import br.com.jdracarys.proxy.IProxy;
import br.com.jdracarys.proxy.JDracarysProxy;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: HideMyAss.java proxy do site http://hidemyass.com
 */
public class HideMyAss extends JDracarysProxy implements IProxy {
	private final String HIDE_MY = "http://hidemyass.com/proxy-list/";
	private final String CSS_DISPLAY_STYLE = "{display:none}";

	public List<HttpHost> load() {
		Elements elementsTR = null;
		String url = null;
		proxies = new ArrayList<HttpHost>();
		for (int pagina = 1; pagina <= 26; pagina++) {
			try {
				url = HIDE_MY + pagina;
				doc = Jsoup.connect(url).get();
				if (doc != null && doc.hasText()) {
					elementsTR = doc.getElementsByTag("tr");
					for (Element element : elementsTR) {
						element = cleanElement(element);
						String ip = element.child(1).text();
						if (ip.contains(".")) {
							int port = Integer.parseInt(element.child(2).text());
							addProxy(ip, port);
						}
					}
					if (proxies.isEmpty())
						throw new ProxyNotFoundException(doc);
				}
			} catch (IOException e) {
				Logger.onError("Lista de proxy não pôde ser recuperada: " + e.getMessage());
				e.printStackTrace();
			} catch (ProxyNotFoundException e) {
				Logger.onError("Nenhum proxy encontrado no documento: " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				Logger.onError("Exception Genérica ocorreu na url " + url + ": " + e.getMessage());
			}
		}
		System.out.println("Total de Proxies Hide My: " + proxies.size());
		return proxies;
	}

	/**
	 * @param element
	 * @return retorna apenas elementos html visíveis para o usuário
	 */
	private Element cleanElement(Element element) {
		element.child(1).select("[style*=none]").remove();
		String[] selectors = element.child(1).data().split("\\.");
		for (String selector : selectors) {
			if (selector.contains(CSS_DISPLAY_STYLE)) {
				element.child(1).select("[class*=" + selector.substring(0, selector.indexOf(CSS_DISPLAY_STYLE)) + "]").remove();
			}
		}
		return element;
	}

	/**
	 * @return
	 */
	public Collection<? extends HttpHost> loadSingleProxyList() {
		Elements elementsTR = null;
		String url = null;
		proxies = new ArrayList<HttpHost>();

			try {
				url = HIDE_MY + 1;
				doc = Jsoup.connect(url).get();
				if (doc != null && doc.hasText()) {
					elementsTR = doc.getElementsByTag("tr");
					for (Element element : elementsTR) {
						element = cleanElement(element);
						String ip = element.child(1).text();
						if (ip.contains(".")) {
							int port = Integer.parseInt(element.child(2).text());
							addProxy(ip, port);
						}
					}
					if (proxies.isEmpty())
						throw new ProxyNotFoundException(doc);
				}
			} catch (IOException e) {
				Logger.onError("Lista de proxy não pôde ser recuperada: " + e.getMessage());
				e.printStackTrace();
			} catch (ProxyNotFoundException e) {
				Logger.onError("Nenhum proxy encontrado no documento: " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				Logger.onError("Exception Genérica ocorreu na url " + url + ": " + e.getMessage());
			}

		System.out.println("Total de Proxies Hide My: " + proxies.size());
		return proxies;
	}



}
