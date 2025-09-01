package socketsAgenda; 
import java.util.Scanner; 
import java.net.Socket;  
import java.io.DataInputStream;  
import java.io.DataOutputStream; 

public class Cliente { 
	
	public static void main(String[]args) { 
		// VARIABLES Y CONSTANTES NECESARIAS: 
			final String ip = "localhost";  
			final int puerto = 9999; 
			Scanner entrada = new Scanner(System.in);  
			String opcionEscogida = ""; 
			String respuestaServidor = ""; 
			String datosNuevoContacto = ""; 
			String datosTelefonoActualizado = ""; 
			String datosContactoEliminar = ""; 
			
			
			
	try {
		// CREACION DEL SOCKET Y LOS FLUJOS DE ENTRADA Y DE SALIDA: 
			Socket socket = new Socket(ip, puerto);   
			DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());  
			DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream()); 
			
			do { 
				mostrarMenu(); 
				opcionEscogida = entrada.nextLine(); 
				
				// REALIZAR UNA ACCION EN FUNCION DE LA OPCION ESCOGIDA POR EL USUARIO: 
				switch(opcionEscogida) { 
					case "1":  
						flujoSalida.writeUTF(opcionEscogida);  
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor);  
						break;  
					
					case "2":   
						datosNuevoContacto = datosInsertarContacto(); 
						flujoSalida.writeUTF(opcionEscogida + "," + datosNuevoContacto);   
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor);  
						break; 
						
					case "3": 
						datosTelefonoActualizado = datosActualizarTelefono(); 
						flujoSalida.writeUTF(opcionEscogida + "," + datosTelefonoActualizado);  
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor); 
						break; 
						
					case "4": 
						datosContactoEliminar = datosEliminarContacto(); 
						flujoSalida.writeUTF(opcionEscogida + "," + datosContactoEliminar);  
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor); 
						break; 
						
					case "5": 
						flujoSalida.writeUTF(opcionEscogida);  
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor); 
						if(socket != null && flujoEntrada != null && flujoSalida != null) {
							socket.close(); 
							flujoEntrada.close(); 
							flujoSalida.close(); 
							System.out.println("Medios desconectados en el cliente. "); 
						} 
						break; 
						
					default: 
						flujoSalida.writeUTF(opcionEscogida); 
						respuestaServidor = flujoEntrada.readUTF(); 
						System.out.println(respuestaServidor); 
						break; 
				}
				
			} while(!(opcionEscogida.equals("5")));   
				
	} 
	catch(Exception e) {  
		System.out.println("Mensaje de error en el cliente: " + e.getMessage());  
		e.printStackTrace();
	}
				
} 
	
	public static void mostrarMenu() {
		System.out.println("AGENDA DE CONTACTOS: "); 
		System.out.println("--------------------------------------"); 
		System.out.println("1. MOSTRAR CONTACTOS."); 
		System.out.println("2. CREAR CONTACTO. ");  
		System.out.println("3. ACTUALIZAR TELÉFONO. ");  
		System.out.println("4. ELIMINAR CONTACTO. "); 
		System.out.println("5. SALIR. "); 
		System.out.println("------------------------------------------"); 
		System.out.println("Introduce la opción deseada: "); 	
	} 
	
	public static String datosInsertarContacto() { 
		Scanner entrada = new Scanner(System.in);  
		String nuevoNombre = ""; 
		String nuevoApellido = ""; 
		String nuevoTelefono = ""; 
		String nuevoCorreo = ""; 
		String infoNuevoContacto = "";  
		
		System.out.println("Introduce el nombre del nuevo contacto: "); 
		nuevoNombre = entrada.nextLine(); 
		System.out.println("-----------------------------------------------------"); 
		System.out.println("Introduce el apellido del nuevo contacto: ");  
		nuevoApellido = entrada.nextLine(); 
		System.out.println("-----------------------------------------------------"); 
		System.out.println("Introduce el teléfono del nuevo contacto: ");  
		nuevoTelefono = entrada.nextLine(); 
		System.out.println("-----------------------------------------------------"); 
		System.out.println("Introduce el correo del nuevo contacto: "); 
		nuevoCorreo = entrada.nextLine();  
		System.out.println("-----------------------------------------------------"); 
		infoNuevoContacto = nuevoNombre + "," + nuevoApellido + "," + nuevoTelefono + "," + nuevoCorreo; 
		return infoNuevoContacto; 	
	} 
	
	public static String datosActualizarTelefono() { 
		Scanner entrada = new Scanner(System.in); 
		int idActualizar = 0; 
		String telefonoActualizado = ""; 
		String infoTelefonoActualizado = ""; 
		
		System.out.println("Introduce el ID del contacto que deseas actualizar: ");  
		idActualizar = Integer.parseInt(entrada.nextLine());  
		System.out.println("------------------------------------------------------------------"); 
		System.out.println("Introduce el nuevo número de teléfono: "); 
		telefonoActualizado = entrada.nextLine(); 
		System.out.println("------------------------------------------------------------------"); 
		infoTelefonoActualizado = idActualizar + "," + telefonoActualizado; 
		return infoTelefonoActualizado; 
	} 
	
	public static String datosEliminarContacto() {
		Scanner entrada = new Scanner(System.in);  
		int idEliminar = 0; 
		String infoContactoEliminar = "";  
		
		System.out.println("Introduce el ID del contacto que deseas eliminar: "); 
		idEliminar = Integer.parseInt(entrada.nextLine());  
		System.out.println("-----------------------------------------------------");  
		infoContactoEliminar = String.valueOf(idEliminar);   
		return infoContactoEliminar; 
	}	
}
