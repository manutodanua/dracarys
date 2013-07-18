/**
 * 
 */
package br.com.jdracarys.method;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import br.com.jdracarys.server.utils.ProxyUtils;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: JDracarysRequest.java
 */
public abstract class JDracarysRequest extends Thread {
	private static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)";
	private static final String ORIGIN_SERVER = "www.google.com.br";
	private static final String COOKIE = "__utma=122214102.797618267.1367772228.1367772228.1367772228.1; __utmb=122214102.1.10.1367772228; __utmc=122214102; __utmz=122214102.1367772228.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)";
	protected HttpClient httpClient;
	protected HttpRequestBase httpRequestBase;

	protected void configProtocol() {
		HttpParams parameters = httpRequestBase.getParams();
		parameters.setParameter(ConnRoutePNames.DEFAULT_PROXY, ProxyUtils.retrieveNextProxy());
		parameters.setParameter(CoreProtocolPNames.USER_AGENT, USER_AGENT);
		parameters.setParameter(CoreProtocolPNames.ORIGIN_SERVER, ORIGIN_SERVER);
		httpRequestBase.setHeader("cookie", COOKIE);
	}
	
	/**
	 * @param exception levantada durante o request
	 * @description remove proxy inválido(connection refused)
	 */
	protected void removeBadProxy(Exception ex) {
		if (ex.getMessage() != null && ex.getMessage().contains("refused")) {
			HttpHost proxy = (HttpHost) httpRequestBase.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
			if (ProxyUtils.getListProxy().contains(proxy)){
				ProxyUtils.getListProxy().remove(proxy);
			}
		}
	}
	
	/**
	 * @param exception levantada durante o request
	 * @description remove proxy inválido(connection timeout)
	 */
	protected void removeTimeOut(Exception ex) {
		if (ex.getMessage() != null && ex.getMessage().contains("timed out")) {
			HttpHost proxy = (HttpHost) httpRequestBase.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
			if (ProxyUtils.getListProxy().contains(proxy)){
				ProxyUtils.getListProxy().remove(proxy);
			}
		}
	}
	

	/**
	 * @param exception levantada durante o request
	 * @description remove qualquer request com erro
	 */
	protected void supremous() {
		HttpHost proxy = (HttpHost) httpRequestBase.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
		if (ProxyUtils.getListProxy().contains(proxy)) {
			ProxyUtils.getListProxy().remove(proxy);
		}

	}
	
	
	protected void successValitation(String response) {
		if (response != null && response.contains("Forbidden") || response.contains("Forbidden") || response.contains("403")) {
			HttpHost proxy = (HttpHost) httpRequestBase.getParams().getParameter(ConnRoutePNames.DEFAULT_PROXY);
			if (ProxyUtils.getListProxy().contains(proxy)){
				ProxyUtils.getListProxy().remove(proxy);
			}
		}
	}
}
