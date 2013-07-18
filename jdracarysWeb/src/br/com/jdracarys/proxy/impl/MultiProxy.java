/**
 * 
 */
package br.com.jdracarys.proxy.impl;

import java.util.List;

import org.apache.http.HttpHost;

import br.com.jdracarys.proxy.IProxy;
import br.com.jdracarys.proxy.JDracarysProxy;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: MultiProxy.java
 */
public class MultiProxy extends JDracarysProxy implements IProxy {
	private static final String MULTI_PROXY = "http://multiproxy.org/txt_anon/proxy.txt";

	public List<HttpHost> load() {
		return defaultLoadTXT(MULTI_PROXY);
	}

}
