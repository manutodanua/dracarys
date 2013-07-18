package br.com.jdracarys.server.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpHost;

import br.com.jdracarys.proxy.impl.BrProxy;
import br.com.jdracarys.proxy.impl.FreeProxyCH;
import br.com.jdracarys.proxy.impl.HideMyAss;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: ProxyUtils.java
 */
public class ProxyUtils {
	private static Set<HttpHost> listProxy = intializeProxies();
	private static Iterator<HttpHost> fastProxies;
	private static ArrayList<HttpHost> tempListIterable;
	
	/**
	 * @return the listProxy
	 */
	public static Set<HttpHost> getListProxy() {
		return listProxy;
	}

	/**
	 * @param listProxy the listProxy to set
	 */
	public static void setListProxy(Set<HttpHost> listProxy) {
		ProxyUtils.listProxy = listProxy;
	}

	/**
	 * @return HashSet<HttpHost>()
	 */
	private static Set<HttpHost> intializeProxies() {
		Set<HttpHost> proxies = new HashSet<HttpHost>();
		/*proxies.addAll(new FreeProxyCH().load());
		proxies.addAll(new HideMyAss().load());
		proxies.addAll(new MultiProxy().load());
		proxies.addAll(new TubeIncreaser().load());*/
		proxies.addAll(new FreeProxyCH().load());
		proxies.addAll(new HideMyAss().load());
		proxies.addAll(new BrProxy().load());
		tempListIterable = new ArrayList<HttpHost>();
		addIterableList(proxies, tempListIterable);
		System.out.println("********Total:" + proxies.size() + "********");
		return proxies;
	}

	public static Set<HttpHost> reload() {
		Set<HttpHost> proxies = new HashSet<HttpHost>();
		proxies.addAll(new HideMyAss().loadSingleProxyList());
		proxies.addAll(listProxy);
		tempListIterable = new ArrayList<HttpHost>();
		addIterableList(proxies, tempListIterable);
		return proxies;
	}
	/**
	 * @param tempListHashSet
	 * @param tempListIterable
	 */
	protected static void addIterableList(Set<HttpHost> listHashSet, List<HttpHost> tempListIterable) {
		tempListIterable.clear();
		tempListIterable.addAll(listHashSet);
		fastProxies = tempListIterable.iterator();
	}

	/**
	 * @return HttpHost proximo proxy da lista
	 */
	public static HttpHost retrieveNextProxy() {
		if (!fastProxies.hasNext())
			addIterableList(listProxy, tempListIterable);
		return fastProxies.next();
	}
}