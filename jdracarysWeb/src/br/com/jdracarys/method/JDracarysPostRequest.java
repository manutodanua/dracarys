/**
 * 
 */
package br.com.jdracarys.method;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
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
public class JDracarysPostRequest extends JDracarysRequest {
	private final HttpContext context;

	public JDracarysPostRequest(HttpClient httpClient, String url) {
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpRequestBase = new HttpPost(url);
		configProtocol();
	}

	@Override
	public void run() {
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	      	nameValuePairs.add(new BasicNameValuePair("username", "_Born"));
	        nameValuePairs.add(new BasicNameValuePair("password", "asdfdgrewf"));
	        nameValuePairs.add(new BasicNameValuePair("redirect", "index.php"));
	        nameValuePairs.add(new BasicNameValuePair("sid", "2d83703981843cf15ac40170a57daac4"));
	        nameValuePairs.add(new BasicNameValuePair("login", "Entrar"));
	        ((HttpPost)httpRequestBase).setEntity(new UrlEncodedFormEntity(nameValuePairs));
		        
			
			HttpResponse response = this.httpClient.execute(this.httpRequestBase, this.context);
			//System.out.println(EntityUtils.toString(response.getEntity()));
			String responseLine = response.getStatusLine().toString(); 
			Logger.onSuccess(responseLine);
			EntityUtils.consume(response.getEntity());
			//successValitation(responseLine);
		} catch (Exception ex) {
			this.httpRequestBase.abort();
			Logger.onError(ex.getMessage());
			//removeBadProxy(ex);
		}
	}

	

}
