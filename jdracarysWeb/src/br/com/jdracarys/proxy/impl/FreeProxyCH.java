/**
 * 
 */
package br.com.jdracarys.proxy.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import br.com.jdracarys.exception.ProxyNotFoundException;
import br.com.jdracarys.logger.Logger;
import br.com.jdracarys.proxy.IProxy;
import br.com.jdracarys.proxy.JDracarysProxy;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: FreeProxyCH.java
 */
public class FreeProxyCH extends JDracarysProxy implements IProxy {
	private static final String FREE_PROXY_CH = "http://www.freeproxy.ch/proxy.txt";

	public List<HttpHost> load() {
		proxies = new ArrayList<HttpHost>();
		String body;
		try {
			HttpResponse response = (new DefaultHttpClient()).execute(new HttpGet(FREE_PROXY_CH));

			if (response != null) {
				body = EntityUtils.toString(response.getEntity());
				proxies = loadFreeProxyCH(body);
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}
			System.out.println("Total de Proxies FreeProxy: " + proxies.size());
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
	 * @param string
	 *            contendo a lista de proxys
	 * @return lista de proxys tratada
	 */
	private List<HttpHost> loadFreeProxyCH(String body) {
		String[] elements = body.replaceAll("\\r", "").trim().split("------------------------------------------------------------------------------------------------")[1].split("\\n");
		for (String element : elements) {
			if(!element.isEmpty()){
				String ip = element.split(":")[0];
				int port = Integer.parseInt(element.split(":")[1].split("	")[0]);
				addProxy(ip, port);
			}
		}
		return proxies;
	}
	
}