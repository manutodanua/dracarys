package br.com.jdracarys.logger;

public class Logger {
	public static boolean log = false;
	
	public static void onSuccess(String msg){
		if(log)
			System.out.println("[INFO] - msg enviada com sucesso, retorno: " + msg);
	}
	
	public static void onError(String msg){
		if(log)
			System.out.println("[ERROR] - Erro ao tentar enviar request: " + msg);
	}
}
