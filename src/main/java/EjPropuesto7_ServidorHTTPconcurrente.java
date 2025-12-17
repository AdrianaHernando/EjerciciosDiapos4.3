import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EjPropuesto7_ServidorHTTPconcurrente {
/*
 * Modificar el servidor anterior para que pueda atender múltiples clientes concurrentes.
 * Vamos a hacer que para cada cliente que se concecte, se cree un hilo que lo atienda.
 * Vamos a usar la clase MyThread para crear los threads correspondientes.
 */
	public static void main(String[] args) {
		ServerSocket s;
		//Try to create the socket listening to port 8080
		//Preparo la escucha en un puerto
		try {
			s = new ServerSocket(8080);
			
			//BUCLE PRINCIPAL: espero que se conecte un cliente nuevo
			while(true) {
				// wait for a connection
				Socket connection = s.accept();
				//Cuando se realice una conexión, se la asigno a un nuevo thread
				Thread hiloNuevo = new MyThread(cliente);
				hiloNuevo.start(); //hago que comience su método "run()" en el que atiende al cliente.
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		
		
	
	//CLASE QUE ATIENDE A UN CLIENTE EN CONCRETO
	public class MyThread extends Thread {
		private Socket connection; //atributo
		
		//Constructor
		public MyThread (Socket connection) {
			this.connection = connection;
		}
		
		//Método Run en el que atiendo al cliente
		public void run() {
			try {
				//Atiendo al cliente: quiero leer la primera linea de la cabecera HTTP y extraer la ruta al recurso solicitado, y servir dicho contenido al cliente.
				InputStream in= connection.getInputStream();
				BufferedReader i = new BufferedReader(new InputStreamReader(in));
				String str = i.readLine().split("")[1]; //cojo la ruta que me pide
				
					//Abre un flujo de lectura del recurso solicitado (no nos especifican que el contenido sea texto así que lo tratamos como bytes)
				InputStream leerRecurso = new FileInputStream(str);
					//Are un flujo de escritura al cliente que realizó la petición
				OutputStream out = connection.getOutputStream();
					//Lee el recurso que hay en la ruta indicada, y va escribiendo los bytes que lee al cliente
				int leido = leerRecurso.read(); 
				while (leido!=-1) {
					out.write(leido);
					leido = leerRecurso.read();
				}
				out.flush();
				connection.close(); //me cierra el in y el out con el cliente.
				leerRecurso.close();
			}catch (Exception e) {
				System.out.println("Error: " + e);
			}
		}
	}
	
			
			
		
			
		

	}
}
