/**
 * 
 */
package br.com.jdracarys.method;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import br.com.jdracarys.logger.Logger;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 05/05/2013
 * @category: JDracarysGetRequest.java
 */
public class JDracarysGetRequest extends JDracarysRequest {
	private final HttpContext context;

	public JDracarysGetRequest(HttpClient httpClient, String url) {
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpRequestBase = new HttpGet(url);
		//httpRequestBase.setHeader("Cookie", "__utma=85680154.1940597428.1373852448.1373852448.1373852448.1; __utmz=85680154.1373852448.1.1.utmcsr=t.co|utmccn=(referral)|utmcmd=referral|utmcct=/VUY41PoAso; PHPSESSID=71241aeedf22648fe0a5cbbd0f97231e; _ga=GA1.3.1940597428.1373852448");
		//httpRequestBase.setHeader("Host", "www.bhmodels.com.br");
		configProtocol();
	}

	@Override
	public void run() {
		try {
			HttpResponse response = this.httpClient.execute(this.httpRequestBase, this.context);
			String responseLine = response.getStatusLine().toString(); 
			Logger.onSuccess(responseLine);
			EntityUtils.consume(response.getEntity());
			successValitation(responseLine);
		} catch (Exception ex) {
			this.httpRequestBase.abort();
			Logger.onError(ex.getMessage());
			removeBadProxy(ex);
			//removeTimeOut(ex);
		}
	}

	

}
