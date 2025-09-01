package socketsAgenda; 
import java.net.ServerSocket; 
import java.net.Socket;
import java.util.Arrays;
import java.io.DataInputStream; 
import java.io.DataOutputStream; 

public class Servidor {  
	
	//Este servidor recibe las peticiones del cliente y le envia una respuesta hacia el propio cliente. 
	
	public static void main(String[]args) {  
		//VARIABLES Y CONSTANTES NECESARIAS: 
			final int puerto = 9999; 
			String peticionRecibida = ""; 
			String respuestaEnviada = ""; 
			OperacionesBD op = new OperacionesBD(); 
			String[]datosConexion = null;  
			boolean conectado = false; 
			boolean desconectado = false; 
			String[]datosPeticion = null; 
			String contactoInsertado = "";  
			
		// CREACION DEL SOCKET PARA ESTABLECER COMUNICACION CON EL CLIENTE Y CREACION DE FLUJOS:  
			
		try {
			ServerSocket server = new ServerSocket(puerto);  // Servidor escucha en el puerto definido 9999.
			System.out.println("Esperando conexión entrante ...."); 
			
			Socket miSocket = server.accept();  // Servidor acepta la conexion. 
			System.out.println("Cliente conectado al servidor. "); 
			
			DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());  
			DataOutputStream flujoSalida = new DataOutputStream(miSocket.getOutputStream());   
			
			do { 
				if(datosConexion != null && datosPeticion != null) {
					Arrays.fill(datosConexion, ""); 
					Arrays.fill(datosPeticion, "");
				}
				  
				
				peticionRecibida = flujoEntrada.readUTF(); 
				
				if(peticionRecibida.length() > 1 && peticionRecibida.contains(",")) {
					datosPeticion = peticionRecibida.split(",");
					peticionRecibida = datosPeticion[0]; 
				}
				
				switch(peticionRecibida) { 
					case "1": 
						datosConexion = op.recuperarDatos(); 
						conectado = op.connectDB(datosConexion[0], datosConexion[1], datosConexion[2]);  
						if(conectado) { 
							respuestaEnviada = op.mostrarContactos();  
							flujoSalida.writeUTF(respuestaEnviada);
							desconectado = op.desconexionDB(); 	
						} 
						
						else {
							respuestaEnviada = "Error: El servidor NO pudo establecer conexión con la base de datos";
							flujoSalida.writeUTF(respuestaEnviada);
						}
						break;  
						
					case "2": 
						datosConexion = op.recuperarDatos(); 
						conectado = op.connectDB(datosConexion[0], datosConexion[1], datosConexion[2]); 
						if(conectado) { 
							respuestaEnviada = op.insertarContacto(datosPeticion[1], datosPeticion[2],
												datosPeticion[3], datosPeticion[4]); 
							
							flujoSalida.writeUTF(respuestaEnviada);
							desconectado = op.desconexionDB(); 	
						} 
						else {
							respuestaEnviada = "Error: El servidor NO pudo establecer conexión con la base de datos"; 
							flujoSalida.writeUTF(respuestaEnviada);
						}
						break; 
						
					case "3": 
						datosConexion = op.recuperarDatos(); 
						conectado = op.connectDB(datosConexion[0], datosConexion[1], datosConexion[2]);  
						if(conectado) { 
							respuestaEnviada = op.actualizarTelefono(Integer.parseInt(datosPeticion[1]), datosPeticion[2]);
							flujoSalida.writeUTF(respuestaEnviada);
							op.desconexionDB(); 	
						} 
						else {
							respuestaEnviada = "Error: El servidor no pudo establecer conexión con la base de datos. "; 
							flujoSalida.writeUTF(respuestaEnviada);
						} 
						break; 
						
					case "4": 
						datosConexion = op.recuperarDatos() ; 
						conectado = op.connectDB(datosConexion[0], datosConexion[1], datosConexion[2]); 
						if(conectado) { 
							respuestaEnviada = op.eliminarContacto(Integer.parseInt(datosPeticion[1])); 
							flujoSalida.writeUTF(respuestaEnviada);
							op.desconexionDB(); 	
						} 
						else {
							respuestaEnviada = "Error: El servidor NO pudo establecer conexión con la base de datos. "; 
							flujoSalida.writeUTF(respuestaEnviada);
						}
						break; 
						
					case "5": 
						respuestaEnviada = "Se sale del programa"; 
						flujoSalida.writeUTF(respuestaEnviada); 
						flujoEntrada.close(); 
						flujoSalida.close(); 
						flujoEntrada.close(); 
						miSocket.close(); 
						server.close(); 
						break; 
						
					default: 
						respuestaEnviada = "Opción NO válida. Por favor vuelve a intentarlo..."; 
						flujoSalida.writeUTF(respuestaEnviada); 
						break; 
						
				}
				
			}while(!(peticionRecibida.equals("5"))); 
			
			System.out.println("Medios desconectados en el servidor. "); 
			
		} 
		
		catch(Exception e) { 
			
			System.out.println("Mensaje de error en el servidor: " + e.getMessage());  
			e.printStackTrace();
		}
			 	
	}  
}
