/**
 * 
 */
package br.com.jdracarys.proxy;

import static br.com.jdracarys.server.utils.ProxyUtils.getListProxy;
import static br.com.jdracarys.server.utils.ProxyUtils.reload;
import static br.com.jdracarys.server.utils.ProxyUtils.setListProxy;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.PoolingClientConnectionManager;

/**
 * @author Raneves
 *	@jdracarysWeb - projeto com foco em low scalability
 * @date: 07/05/2013
 * @category: ProxyReloader.java 
 */
public class ProxyReloader extends Thread {
	private final int TIME_RELOAD = 60000;//1 minuto
	private PoolingClientConnectionManager cm;
	private final int idleTimeout = 10000;
	public ProxyReloader(PoolingClientConnectionManager cm){
		this.cm = cm;
	}
	
	public void run() {
		while(true){
			try {
				sleep(TIME_RELOAD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			cm.closeExpiredConnections();
			cm.closeIdleConnections(idleTimeout, TimeUnit.SECONDS);
			setListProxy(reload());
			System.out.println("\n\n\n\n\n\n\nAtualizando Proxys: "+ getListProxy().size() +"\n\n\n\n\n\n\n\n");
		}
	}

}
