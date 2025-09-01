package socketsAgenda;

public class Contacto {  
	
	// Atributos de la clase: 
	private int id; 
	private String nombre; 
	private String apellido; 
	private String telefono; 
	private String correo;  
	
	public Contacto(int id, String nombre, String apellido, String telefono, String correo) { 
		this.id = id; 
		this.nombre = nombre; 
		this.apellido = apellido; 
		this.telefono = telefono; 
		this.correo = correo; 	
	} 
	
	public int getId() {
		return this.id; 
	} 
	
	public void setId(int nuevoId) { 
		this.id = nuevoId; 	
	} 
	
	public String getNombre() {
		return this.nombre; 	
	} 
	
	public void setNombre(String nuevoNombre) { 
		this.nombre = nuevoNombre; 	
	} 
	
	public String getApellido() {
		return this.apellido; 
	} 
	
	public void setApellido(String nuevoApellido) {
 		this.apellido = nuevoApellido; 
	} 
	
	public String getTelefono() {
		return this.telefono; 
	} 
	
	public void setTelefono(String nuevoTelefono) {
		this.telefono = nuevoTelefono; 
	} 
	
	public String getCorreo() {
		return this.correo; 
	} 
	
	public void setCorreo(String nuevoCorreo) {
		this.correo = nuevoCorreo; 
	} 
	
	public String toString() {
		return 
				"ID: " + this.getId() + "\n" + 
				"NOMBRE: " + this.getNombre() + "\n" + 
				"APELLIDO: " + this.getApellido() + "\n" + 
				"TELÉFONO: " + this.getTelefono() + "\n" + 
				"CORREO: " + this.getCorreo() + "\n" + 
				"-------------------------------------------------------------" + "\n"; 
	}

}
