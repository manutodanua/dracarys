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
 * @category: TubeIncreaser.java
 */
public class TubeIncreaser extends JDracarysProxy implements IProxy {
	private static final String TUBE_INCREASER = "http://www.tubeincreaser.com/proxylist.txt";

	public List<HttpHost> load() {
		return defaultLoadTXT(TUBE_INCREASER);
	}


	

}
