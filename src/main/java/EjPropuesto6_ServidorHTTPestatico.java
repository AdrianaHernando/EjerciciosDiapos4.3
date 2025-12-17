import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class EjPropuesto6_ServidorHTTPestatico {
	public static void main(String[] args) {
		ServerSocket s;

		//Try to create the socket listening to port 8080
		//Preparo la escucha en un puerto
		try {
			s = new ServerSocket(8080);
		} catch (Exception e) {
			System.out.println("Error: " + e);
			return;
		}

		try {
			// wait for a connection
			Socket cliente = s.accept();
			//Atiendo al cliente: quiero leer la primera linea de la cabecera HTTP y extraer la ruta al recurso solicitado, y servir dicho contenido al cliente.
			InputStream in= cliente.getInputStream();
			BufferedReader i = new BufferedReader(new InputStreamReader(in));
			String str = i.readLine().split("")[1]; //cojo la ruta que me pide
			
				//Abre un flujo de lectura del recurso solicitado (no nos especifican que el contenido sea texto así que lo tratamos como bytes)
			InputStream leerRecurso = new FileInputStream(str);
				//Are un flujo de escritura al cliente que realizó la petición
			OutputStream out = cliente.getOutputStream();
				//Lee el recurso que hay en la ruta indicada, y va escribiendo los bytes que lee al cliente
			int leido = leerRecurso.read(); 
			while (leido!=-1) {
				out.write(leido);
				leido = leerRecurso.read();
			}
			out.flush();
			cliente.close(); //me cierra el in y el out con el cliente.
			leerRecurso.close();
			
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
			
		

	}

}
