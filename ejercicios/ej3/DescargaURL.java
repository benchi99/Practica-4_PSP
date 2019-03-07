package ej3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * Ej3. -- HTTP -- UrlConnection
 * 
 *  Utilizando la clase URLConnection, realiza un programa
 *  que permita descargar descargar el contenido HTML de
 *  una URL y escribelo en un fichero en el disco.
 *  
 * @author Rubén
 *
 */

public class DescargaURL {

	public static void main(String[] args) {
		new DescargaURL().run(args);
	}

	private void run(String[] args) {
		String direccion = null;
		String destino = null;
		
		if (args.length != 2) {
			System.out.println("Número de parámetros incorrectos."
					+ "\n"
					+ "\n"
					+ "USO DE LA APLICACIÓN:"
					+ "\n"
					+ "java DescargaURL <URL> <fichero de destino>");
			System.exit(1);
		} else {
			direccion = args[0];
			destino = args[1];
		}
		
		try {
			URL url = new URL(direccion);
			
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("GET");
			http.setRequestProperty("User-Agent", "Mozilla/5.0");
			System.out.println("Código HTTP de respuesta: " + http.getResponseCode());
			if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
				escribeEnDisco(http, destino);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Método que escribe en disco 
	 * desde una URL.
	 * 
	 * @param con Conexión HttpURL
	 * @param destino Archivo de destino.
	 * @throws IOException Error de E/S.
	 */
	private void escribeEnDisco(HttpURLConnection con, String destino) throws IOException {
		BufferedInputStream web = new BufferedInputStream(con.getInputStream());
		BufferedOutputStream bfout = new BufferedOutputStream(new FileOutputStream(destino));
		
		byte[] buffer = new byte[1000];
		int leidos = web.read(buffer);
		while (leidos > 0) {
			bfout.write(buffer, 0, leidos);
			leidos = web.read(buffer);
		}
		web.close();
		bfout.close();
	}
	
}
