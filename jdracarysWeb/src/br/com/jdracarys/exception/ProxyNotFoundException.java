package br.com.jdracarys.exception;

import org.jsoup.nodes.Document;

/**
 * @author Raneves
 * @jdracarys - projeto com foco em low scalability
 * @date: 04/05/2013
 * @category: ProxyNotFoundException.java exception referente à recuperação de proxys dos repositórios
 */
public class ProxyNotFoundException extends Exception {
	private static final long serialVersionUID = -1038428496017090096L;

	public ProxyNotFoundException(Document doc) {
		super("Nenhum proxy encontrado no documento: " + doc.baseUri());
	}
}
