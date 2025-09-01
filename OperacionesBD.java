package socketsAgenda; 
import java.sql.DriverManager; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.PreparedStatement; 
import java.sql.SQLException; 
import java.io.File; 
import java.io.FileReader; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.Scanner; 

public class OperacionesBD {  
	
	// Clase que va a interactuar con la base de datos. 
	
	// Atributos de la clase: 
		private static Connection conexion = null; 
		private ResultSet rs = null; 
		private PreparedStatement ps = null; 
		
	// Constructor de la clase: 
		public OperacionesBD() {// Constructor por defecto.  
			
		} 
		
	// ----------------------------- METODOS PARA INTERACTUAR CON LA BASE DE DATOS: -------------------------
		
		public String[] recuperarDatos() {
			String[]datos = new String[3]; // Se que el array tiene que tener una longitud de 3. 
			final String rutaFichero = "./src/socketsAgenda/datos.txt"; 
			File fichero = new File(rutaFichero);  
			String linea = null; 
			
			try {
				FileReader fr = new FileReader(fichero);  
				BufferedReader br = new BufferedReader(fr);   
				linea = br.readLine(); 
				if(linea != null) {
					datos = linea.split(","); 
				} 
				
				// CERRAR LOS FLUJOS DE LECTURA ABIERTOS:  
					if(br != null && fr != null) {
						br.close(); 
						fr.close(); 
					} 
					return datos; 
			} 
			catch(IOException e) {
				System.out.println("Mensaje de error: " + e.getMessage());  
				datos = null; 
				return datos; 
			}
		}
		
		
		public boolean  connectDB(String url, String usu, String pass) {
			try {
				conexion = DriverManager.getConnection(url, usu, pass);  
				System.out.println("Conectado a la base de datos. "); 
				return true;  
			} 
			catch(SQLException e) { 
				System.out.println("Mensaje de error: " + e.getMessage()); 
				return false; 
				
			}
		}  
		
		public boolean desconexionDB() {
			try {
				if(conexion != null && ps != null && rs != null) { 
					conexion.close(); 
					ps.close(); 
					rs.close(); 	
				} 
				else if(conexion != null && ps != null && rs == null) {
					conexion.close(); 
					ps.close(); 
				} 
				return true; 
			} 
			
			catch(SQLException e) {
				System.out.println("Mensaje de error: " + e.getMessage()); 
				return false; 
			}
		} 
		
		public String mostrarContactos() {
			String infoContactos = ""; 
			StringBuilder sb = new StringBuilder(); 
			final String consultaSql = "SELECT * FROM CONTACTOS ORDER BY ID ASC"; 
			int id = 0; 
			String nombreContacto = ""; 
			String apellidoContacto = ""; 
			String telefonoContacto = ""; 
			String correoContacto = ""; 
			Contacto contacto; 
			ArrayList<Contacto> listaContactos = new ArrayList<Contacto>(); 
			
			try {
				ps = conexion.prepareStatement(consultaSql); 
				rs = ps.executeQuery(); 
				
			// RECORRIDO DEL RESULTSET:  
				while(rs.next()) { 
					id = rs.getInt(1); 
					nombreContacto = rs.getString(2); 
					apellidoContacto = rs.getString(3);  
					telefonoContacto = rs.getString(4); 
					correoContacto = rs.getString(5);  
					contacto = new Contacto(id, nombreContacto, apellidoContacto, telefonoContacto, correoContacto); 
					listaContactos.add(contacto); 		
				} 
				
			// RECORRIDO DE LA LISTA DE CONTACTOS: 
				for(int i = 0; i < listaContactos.size(); i++) { 
					sb.append(listaContactos.get(i).toString());  	
				}  
				
				infoContactos = sb.toString(); 
				return infoContactos; 
			} 
			
			catch(Exception e) { 
				infoContactos = null; 
				return infoContactos; 	
			}	
		} 
		
		public String insertarContacto(String nuevoNombre, String nuevoApellido, String nuevoTelefono, 
										String nuevoCorreo) { 
			// VARIABLES/ CONSTANTES NECESARIAS: 
				String consultaSql = "INSERT INTO CONTACTOS(NOMBRE, APELLIDO, TELEFONO, CORREO) VALUES(?,?,?,?)"; 
				int registrosInsertados = 0; 
				
			try { 
				ps = conexion.prepareStatement(consultaSql); 
				ps.setString(1, nuevoNombre); 
				ps.setString(2, nuevoApellido);  
				ps.setString(3, nuevoTelefono); 
				ps.setString(4, nuevoCorreo);  
				registrosInsertados = ps.executeUpdate(); 
				return "Se ha insertado " + registrosInsertados + " contacto correctamente. "; 		
			} 
			catch(SQLException  e) { 
				e.printStackTrace();
				return "Mensaje de error: " + e.getMessage(); 	
			}
		} 
		
		public String eliminarContacto(int idEliminar) { 
			String consultaSql = "DELETE FROM CONTACTOS WHERE ID = ?";  
			int registrosEliminados = 0;  
			
			try { 
				ps = conexion.prepareStatement(consultaSql); 
				ps.setInt(1, idEliminar); 
				registrosEliminados = ps.executeUpdate(); 
				return "Se ha eliminado " + registrosEliminados + " contacto correctamente. ";  	
			} 
			catch(SQLException e) { 
				return "Mensaje de error: " + e.getMessage();  
				
			}	
		} 
		
		public String actualizarTelefono(int id, String telefonoActualizado) {// Metodo que permite actualizar un num de telefono de un contacto: 
			String consultaSql = "UPDATE CONTACTOS SET TELEFONO=? WHERE ID=?"; 
			int registrosActualizados = 0; 
			
			try {
				ps = conexion.prepareStatement(consultaSql); 
				ps.setString(1, telefonoActualizado);  
				ps.setInt(2, id); 
				registrosActualizados = ps.executeUpdate(); 
				return "Se actualizó el número de teléfono de " + registrosActualizados + " contacto. "; 
			} 
			catch(SQLException e) { 
				return "Mensaje de error: " + e.getMessage();  
				
			}
		}
		
		
}
