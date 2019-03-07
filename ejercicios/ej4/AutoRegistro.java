package ej4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * Ej4 -- HTTP -- UrlConnection -- Registro autom�tico
 * 
 * En la siguiente direcci�n:
 * 
 *  https://www.ieslamarisma.net/profesores/santi/login_example/register.php
 * 
 * Hay instalada una aplicaci�n que permite registrar usuarios usando un formulario.
 * 
 * Se desea crear un programa que permite insertar los nuevos usuarios
 * de manera sencilla utilizando tus datos seg�n especificado tus datos
 * siguiendo el formato indicado en la documentaci�n.
 * 
 * @author Rub�n
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
	 * Utilizando el m�todo POST, inserta un usuario
	 * dada una iteraci�n y una URL a la que registrarle.
	 * 
	 * @param url URL en donde es registrado.
	 * @param iteracion Iteraci�n del usuario.
	 * @throws IOException Error de E/S.
	 */
	private void registraUsuario(String url, int iteracion) throws IOException {
		URL direccion = new URL(url);
		
		//Se abre una conexi�n a la web.
		HttpURLConnection http = (HttpURLConnection) direccion.openConnection();
		http.setRequestMethod("POST");			//Se establece el m�todo a realizar.
		http.setRequestProperty("User-Agent", "Mozilla/5.0");
		http.setDoOutput(true);					
		
		OutputStream out = http.getOutputStream();		//Se crea un Stream de salida a la URL.		
		String enviarNombre = CAMPO1 + "RubenBR" + (char) iteracion;
		String enviarEmail = CAMPO2 + "rubenBR" + (char) iteracion + "@dam2.ieslamarisma.net";
		String enviarPass = CAMPO3 + "rubenBR" + (char) iteracion;
		String enviarCPass = CAMPO4 + "rubenBR" + (char) iteracion;
		String POSTParams = enviarNombre + enviarEmail + enviarPass + enviarCPass + CAMPO5 + CAMPO6;
		System.out.println("ENVIANDO POST CON LOS SIGUIENTES PAR�METROS: " + POSTParams);
		out.write(POSTParams.getBytes());
		out.flush();
		System.out.println("Insertado usuario " + (char) iteracion);
		out.close();	//Llegado a este punto, ya se ha hecho el m�todo POST.
		
		System.out.println("Respuesta del servidor: " + http.getResponseCode());

	}
	
}
