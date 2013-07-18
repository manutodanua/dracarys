package br.com.jdracarys.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jdracarys.logger.Logger;
import br.com.jdracarys.proxy.ProxyReloader;
import br.com.jdracarys.server.JDracarysServer;
import br.com.jdracarys.server.utils.JDracarysUtils;
import br.com.jdracarys.server.utils.ProxyUtils;

/**
 * Servlet implementation class JDracarysServlet
 */
@WebServlet("/JDracarysServlet")
public class JDracarysServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger.log = new Boolean(request.getParameter("log"));
		JDracarysServer server = new JDracarysServer();
		server.setNumberConnections(Integer.parseInt(request.getParameter("connections")));
		server.setUrlTarget(request.getParameter("url"));
		ProxyUtils.getListProxy();
		server.setSchemeRegistry(JDracarysUtils.defaultSchemeRegistry());
		server.setCm(JDracarysUtils.defaultConfigCM(server.getSchemeRegistry()));
		server.setHttpClient(JDracarysUtils.defaultConfigClient(server.getCm()));
		
		server.start();
		ProxyReloader reloader = new ProxyReloader(server.getCm());
		reloader.start();
	}

}
