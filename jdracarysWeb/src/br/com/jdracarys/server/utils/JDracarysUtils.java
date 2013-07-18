/**
 * 
 */
package br.com.jdracarys.server.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: JDracarysUtils.java
 */
public class JDracarysUtils {
	private static final int CONNECTION_TIMEOUT = 30000;
	public static final int MAX_CONNECTIONS = 2000;

	/**
	 * @param cm 
	 * @return HttpClient com configura��o default para todos os requests
	 */
	public static HttpClient defaultConfigClient(PoolingClientConnectionManager cm) {
		HttpClient httpClient = new DefaultHttpClient(cm);
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.TCP_NODELAY, true);
		httpClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false);
		return httpClient;
	}

	/**
	 * @return SchemeRegistry contemplando http e https
	 */
	public static SchemeRegistry defaultSchemeRegistry() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		Scheme http = new Scheme("http", 80, PlainSocketFactory.getSocketFactory());
		Scheme https = new Scheme("https", 443, SSLSocketFactory.getSocketFactory());
		schemeRegistry.register(http);
		schemeRegistry.register(https);
		return schemeRegistry;
	}

	/**
	 * @param schemeRegistry
	 * @return
	 */
	public static PoolingClientConnectionManager defaultConfigCM(SchemeRegistry schemeRegistry) {
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(MAX_CONNECTIONS);
		cm.setDefaultMaxPerRoute(MAX_CONNECTIONS);
		return cm;
	}

}
