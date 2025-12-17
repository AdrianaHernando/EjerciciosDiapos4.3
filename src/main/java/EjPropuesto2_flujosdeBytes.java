import java.io.IOException;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.net.URL;

//EJERCICIO 2: Crear un programa que descarga una página Web y la inspecciona para encontrar un encabezado h2 vacío (<h2></h2>) y la modifica para
//incluir dentro del encabezado el texto ‘Hola’.

public class EjPropuesto2_flujosdeBytes {
	
	public static void main (String [] args)  throws IOException {
		InputStream fichBytes = null;
		OutputStream salidaBytes = null;
		try {
			//Obtenemos entrada como texto
			URL unaPagina = new URL("http://web.dit.upm.es/index.html");
			fichBytes = unaPagina.openStream();
			InputStreamReader reader1 = new InputStreamReader(fichBytes);
			BufferedReader fichTxt = new BufferedReader(reader1); //para poder usar readLine y leer linea a linea.
			//Operamos con el texto
			String textoOriginal="",linea;
			while((linea=fichTxt.readLine())!=null) {
				textoOriginal+=linea;
			}
			String textoModif = textoOriginal.replace("<h2></h2>", "<h2>Hola</h2>"); //DUDA
			//Mandamos salida como texto
			salidaBytes = new FileOutputStream("doc1.html");
			PrintWriter salidaTxt=new PrintWriter(salidaBytes);
			salidaTxt.print(textoModif);
			salidaTxt.close();
			
		}catch(Exception e){
        	System.out.println("Ocurrió un error en el proceso: "+e.getMessage());
        }
		finally {
			if(fichBytes!=null)
				fichBytes.close();
			if(salidaBytes!=null)
				salidaBytes.close(); //importante cerrar los streams
		}
		
	}
}
