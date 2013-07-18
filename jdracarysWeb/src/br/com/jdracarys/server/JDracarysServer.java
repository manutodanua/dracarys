/**
 * 
 */
package br.com.jdracarys.server;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;

import br.com.jdracarys.method.JDracarysGetRequest;
import br.com.jdracarys.server.utils.JDracarysUtils;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: JDracarysServer.java
 */
public class JDracarysServer extends Thread {
	private static final int TIME_SLEEP_EXCEPTION = 10000;
	private static final int TIME_SLEEP = 1000;
	private HttpClient httpClient;
	private PoolingClientConnectionManager cm;
	private SchemeRegistry schemeRegistry;
	private String urlTarget;
	private int numberConnections;

	/**
	 * @return the urlTarget
	 */
	public String getUrlTarget() {
		return urlTarget;
	}

	/**
	 * @param urlTarget the urlTarget to set
	 */
	public void setUrlTarget(String urlTarget) {
		this.urlTarget = urlTarget;
	}

	/**
	 * @return the numberConnections
	 */
	public int getNumberConnections() {
		return numberConnections;
	}

	/**
	 * @param numberConnections the numberConnections to set
	 */
	public void setNumberConnections(int numberConnections) {
		this.numberConnections = numberConnections;
	}
	
	

	/**
	 * @return the cm
	 */
	public PoolingClientConnectionManager getCm() {
		return cm;
	}

	/**
	 * @param cm the cm to set
	 */
	public void setCm(PoolingClientConnectionManager cm) {
		this.cm = cm;
	}

	/**
	 * @return the httpClient
	 */
	public HttpClient getHttpClient() {
		return httpClient;
	}

	/**
	 * @param httpClient the httpClient to set
	 */
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	/**
	 * @return the schemeRegistry
	 */
	public SchemeRegistry getSchemeRegistry() {
		return schemeRegistry;
	}

	/**
	 * @param schemeRegistry the schemeRegistry to set
	 */
	public void setSchemeRegistry(SchemeRegistry schemeRegistry) {
		this.schemeRegistry = schemeRegistry;
	}

	public void run() {
		while (true) {
			try {
				for (int cont = 0; cont < numberConnections; cont++) {
					new JDracarysGetRequest(httpClient, urlTarget).start();
				}
				System.out.println("\n---------------Send GET " + numberConnections +"---------------\n");
				//System.out.println("getAvailable: " + cm.getTotalStats().getAvailable() + "\nLeased: " + cm.getTotalStats().getLeased() + "\nPending: " + cm.getTotalStats().getPending() + "\nMax" + cm.getTotalStats().getMax());
				sleep(TIME_SLEEP);
				renewServer();
			} catch (OutOfMemoryError m) {
				System.out.println("\n\n\n\n\n\n\n\n\n\nErro cr�tico: " + m.getMessage() + "\n\n\n\n\n\n\n\n\n\n");
				sleep(TIME_SLEEP_EXCEPTION);
				renewServer();

			} catch (Throwable t) {
				System.out.println("\n\n\n\n\n\n\n\n\n\nErro cr�tico!\n");
				t.printStackTrace();
				sleep(TIME_SLEEP_EXCEPTION);
				renewServer();
			}
		}

	}

	/**
	 * 
	 */
	protected void renewServer() {
		if( cm.getTotalStats().getLeased() > (JDracarysUtils.MAX_CONNECTIONS - 50) ){
			cm.shutdown();
			sleep(5000);
			cm = JDracarysUtils.defaultConfigCM(getSchemeRegistry());
			httpClient = JDracarysUtils.defaultConfigClient(cm);
			sleep(5000);
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
