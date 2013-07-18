/**
 * 
 */
package br.com.jdracarys.proxy;

import java.util.List;

import org.apache.http.HttpHost;

/**
 * @author Raneves
 *	@jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: IProxy.java define método que todos os proxies precisam ter
 */
public interface IProxy {
	public List<HttpHost> load();
}
