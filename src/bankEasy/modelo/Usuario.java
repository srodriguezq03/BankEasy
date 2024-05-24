package bankEasy.modelo;

import java.util.ArrayList;

public class Usuario {

	private String nombre;

	private String direccion;

	private String telefono;

	private String correoElectronico;

	private String contrasenya;

	private String tipoCuentaUsuario;
	
	private ArrayList<Cuenta> cuentas;

	public Usuario() {
		super();
	}
	
	public Usuario(String nombre, String direccion, String telefono, String correoElectronico, String contrasenya,
			String tipoCuentaUsuario) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
		this.contrasenya = contrasenya;
		this.tipoCuentaUsuario = tipoCuentaUsuario;
		this.cuentas = new ArrayList<Cuenta>();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getContrasenya() {
		return contrasenya;
	}

	public void setContrasenya(String contrasenya) {
		this.contrasenya = contrasenya;
	}

	public String getTipoCuentaUsuario() {
		return tipoCuentaUsuario;
	}

	public void setTipoCuentaUsuario(String tipoCuentaUsuario) {
		this.tipoCuentaUsuario = tipoCuentaUsuario;
	}
	
	public void anyadirCuenta(Cuenta cuenta) {
		cuentas.add(cuenta);
	}
	
	public void eliminarCuenta(Cuenta cuenta) {
		cuentas.remove(cuenta);
	}
		
	public void administraCuenta() {
	    System.out.println("Administrar Cuentas:");
	    for (Cuenta cuenta : cuentas) {
	        if (cuenta.getNombreDuenyo().equals(this.getNombre())) {
	            System.out.println("Saldo Actual de la Cuenta " + cuenta.getNumeroCuenta() + ": " + cuenta.getSaldo());
	            System.out.println("Detalles de la Cuenta " + cuenta.getNumeroCuenta() + ":");
	            System.out.println(cuenta);
	        }
	    }
	}


	@Override
	public String toString() {
		return String.format(
				"Usuario:\n" + "Nombre: %s\n" + "Dirección: %s\n" + "Teléfono: %s\n" + "Correo Electrónico: %s\n"
						+ "Contraseña: %s\n" + "Tipo de Cuenta: %s",
				nombre, direccion, telefono, correoElectronico, contrasenya, tipoCuentaUsuario);
	}
}
