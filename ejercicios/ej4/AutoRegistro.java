package ej4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * Ej4 -- HTTP -- UrlConnection -- Registro automático
 * 
 * En la siguiente dirección:
 * 
 *  https://www.ieslamarisma.net/profesores/santi/login_example/register.php
 * 
 * Hay instalada una aplicación que permite registrar usuarios usando un formulario.
 * 
 * Se desea crear un programa que permite insertar los nuevos usuarios
 * de manera sencilla utilizando tus datos según especificado tus datos
 * siguiendo el formato indicado en la documentación.
 * 
 * @author Rubén
 *
 */

public class AutoRegistro {

	//CAMPOS FORMULARIO PARA ENVIAR POR POST.
	final String CAMPO1 = "name=";
	final String CAMPO2 = "&email=";
	final String CAMPO3 = "&password=";
	final String CAMPO4 = "&cpassword=";
	final String CAMPO5 = "&terminosycond=on";
	final String CAMPO6 = "&signup=Registrar";
	
	public static void main(String[] args) {
		new AutoRegistro().run();
	}

	private void run() {
		String url = "https://www.ieslamarisma.net/profesores/santi/login_example/register.php";
		try {
			for (int i = 65; i <= 85; i++) {
				registraUsuario(url, i);	//
			}
		} catch (IOException ioe) {
		System.err.println("Hubo un error al hacer POST.");
		ioe.printStackTrace();
		}
	}

	/**
	 * Utilizando el método POST, inserta un usuario
	 * dada una iteración y una URL a la que registrarle.
	 * 
	 * @param url URL en donde es registrado.
	 * @param iteracion Iteración del usuario.
	 * @throws IOException Error de E/S.
	 */
	private void registraUsuario(String url, int iteracion) throws IOException {
		URL direccion = new URL(url);
		
		//Se abre una conexión a la web.
		HttpURLConnection http = (HttpURLConnection) direccion.openConnection();
		http.setRequestMethod("POST");			//Se establece el método a realizar.
		http.setRequestProperty("User-Agent", "Mozilla/5.0");
		http.setDoOutput(true);					
		
		OutputStream out = http.getOutputStream();		//Se crea un Stream de salida a la URL.		
		String enviarNombre = CAMPO1 + "RubenBR" + (char) iteracion;
		String enviarEmail = CAMPO2 + "rubenBR" + (char) iteracion + "@dam2.ieslamarisma.net";
		String enviarPass = CAMPO3 + "rubenBR" + (char) iteracion;
		String enviarCPass = CAMPO4 + "rubenBR" + (char) iteracion;
		String POSTParams = enviarNombre + enviarEmail + enviarPass + enviarCPass + CAMPO5 + CAMPO6;
		System.out.println("ENVIANDO POST CON LOS SIGUIENTES PARÁMETROS: " + POSTParams);
		out.write(POSTParams.getBytes());
		out.flush();
		System.out.println("Insertado usuario " + (char) iteracion);
		out.close();	//Llegado a este punto, ya se ha hecho el método POST.
		
		System.out.println("Respuesta del servidor: " + http.getResponseCode());

	}
	
}
