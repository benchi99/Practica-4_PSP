package ej1;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 
 * Ej. 1 - FTP - Transferencia de archivos.
 * 
 * Se desea realizar una aplicación que descargue un archivo de
 * un servidor FTP de cualquier servidor utilizando el usuario
 * anónimo.
 * 
 * @author Rubén
 *
 */
public class FTP {
	
	static Scanner teclado = new Scanner(System.in);
	
	public static void main(String[] args) {
		new FTP().run(args);
	}
	
	private void run(String[] args) {
		String ip;
		
		if (args.length != 1) {
			ip = "localhost";
		} else {
			ip = args[0];
		}
		
		FTPClient ftp = new FTPClient();
		try {
			int respuesta;
			ftp.connect(ip);
			ftp.login("usuario", "usuario");
			System.out.println("Conectado a " + ip);
			System.out.println("Mensaje del bienvenida del servidor: \n" + ftp.getReplyString());
			
			//Verificamos el éxito de la conexion.
			respuesta = ftp.getReplyCode();
			
			if(!FTPReply.isPositiveCompletion(respuesta)) {
				ftp.disconnect();
				System.out.println("El servidor ha rechazado tu conexión.");
				System.exit(1);
			}
			
			int opcion;
			
			while(true) {
				dir(obtenListado(ftp), ftp.printWorkingDirectory());
				opcion = menu();
				
				switch (opcion) {
					case 1:
						accederDirectorio(ftp);
						break;
					case 2:
						descargarArchivo(ftp);
						break;
					case 0:
						salir(ftp);
						break;
						
					default:
						System.out.println("Opción no permitida/no existe.");
						break;
				}
				
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	
	}
	
	/**
	 * Método que imprime el directorio actual en el cliente FTP.
	 * 
	 * @param list Lista de archivos y carpetas dentro del directorio actual.
	 * @param directorio El directorio actual.
	 */
	private void dir(ArrayList<FTPFile> list, String directorio) {
		System.out.println("Listado del directorio " + directorio);
		if (!directorio.equals("/"))
			System.out.println("[<== VOLVER]");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).isDirectory()) {
				System.out.println(list.get(i).getName() + " [carpeta]");
			} else {
				System.out.println(list.get(i).getName());
			}
		}
	}
	
	/**
	 * Obtiene el listado de archivos y carpetas dentro del directorio actual.
	 * 
	 * @param ftp Objeto FTP Client en el que se va a realizar la operación.
	 * @return ArrayList de carpetas y archivos en el directorio actual.
	 * @throws IOException Error de E/S.
	 */
	
	private ArrayList<FTPFile> obtenListado(FTPClient ftp) throws IOException {
		
		FTPFile[] dirs = ftp.listDirectories();
		FTPFile[] archivos = ftp.listFiles();
		
		ArrayList<FTPFile> comp = new ArrayList<FTPFile>(Arrays.asList(dirs));
		for (int i = 0; i < archivos.length; i++) {
			if (!archivos[i].isDirectory()) {
				comp.add(archivos[i]);
			}
		}
		return comp;
	}
	
	/**
	 * Imprime el menú y devuelve el comando que el usuario desea hacer.
	 * 
	 * @return Opción del menú deseada.
	 */
	private int menu() {
		System.out.print("Seleccione una acción:\n"
				+ "\t1. Acceder a carpeta.\n"
				+ "\t2. Descargar archivo.\n"
				+ "\t0. Salir de la aplicación.\n"
				+ "ftp>");
		
		return teclado.nextInt();
	}
	
	/**
	 * Accede al directorio especificado por teclado.
	 * 
	 * @param ftp Objeto FTP Client en el que se va a realizar la operación.
	 * @return El nombre del directorio en el que se encuentra.
	 * @throws IOException Error de E/S.
	 */
	
	private void accederDirectorio(FTPClient ftp) throws IOException {
		System.out.print("Nombre de la carpeta a acceder: ");
		String carpetaAEntrar = teclado.next();
		
		if (carpetaAEntrar.toLowerCase().equals("volver")) {
			ftp.changeToParentDirectory();
		} else {
			ftp.changeWorkingDirectory(carpetaAEntrar);
		}
	}
	
	/**
	 * Descarga el archivo especificado por teclado en 
	 * el directorio especificado por teclado.
	 * 
	 * @param ftp Objeto FTP Client en el que se va a realizar la operación.
	 * @throws IOException Error de E/S.
	 */
	private void descargarArchivo(FTPClient ftp) throws IOException {
		System.out.print("Nombre del archivo a descargar: ");
		String archivo = teclado.next();
		System.out.println("¿Dónde quiere descargar el archivo? (Escriba una ruta absoluta)");
		String ruta = teclado.next();
		
		BufferedOutputStream bfout = new BufferedOutputStream(new FileOutputStream(ruta + archivo));
		boolean descargaCorrecta = ftp.retrieveFile(archivo, bfout);
		bfout.close();
		
		if (descargaCorrecta)
			System.out.println("Fichero descargado correctamente.");
		else
			System.out.println("Hubo un error al descargar el fichero.");
	}
	

	/**
	 * Cierra la sesión en el servidor FTP,
	 * y sale de la aplicación.
	 * 
	 * @param ftp
	 * @throws IOException
	 */
	
	private void salir(FTPClient ftp) throws IOException{
		System.out.println("Desconectando...");
		ftp.logout();
		ftp.disconnect();
		System.out.println("Saliendo de la aplicación...");
		System.exit(0);
	}
}
