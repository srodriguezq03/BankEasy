package bankEasy.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bankEasy.controlador.SistemaAutenticacionUsuario;
import bankEasy.modelo.Usuario;

public class InterfazUsuario {

	ArrayList<Usuario> usuarios = new ArrayList<>();
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	SistemaAutenticacionUsuario sau = new SistemaAutenticacionUsuario();

	public void start() {
		inicioSesionRegistro();
	}

	public void inicioSesionRegistro() {
		String opcion = null;

		do {
			System.out.println("\nMenú principal:\n" + "1. Iniciar sesión\n" + "2. Registrarse\n" + "3. Salir de la aplicación");
			opcion = introducirInput("Elija una opción:\n> ");

			if (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3")) {
				System.out.println("ERROR: debe introducir una opción del 1 al 3.");
			}
		} while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"));

		switch (opcion) {
		case "1":
			solicitarDatosInicioSesion();
			break;
		case "2":
			solicitarDatosRegistro();
			break;
		case "3":
			System.out.println("Saliendo de la aplicación...");
			System.exit(0);
		}
	}

	public void solicitarDatosInicioSesion() {
		String correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");

		String contrasenya = introducirInput("Introduzca su contraseña:\n> ");

		if (sau.autenticarUsuario(correoElectronico, contrasenya, usuarios)) {
			String opcion = null;
			for (Usuario usuario : usuarios) {
				if (usuario.getCorreoElectronico().equals(correoElectronico)) {
					if (usuario.getTipoCuentaUsuario().equalsIgnoreCase("A")) {
						do {
						System.out.println("\n[ADMINISTRADOR]\n" + "1. Crear cuenta bancaria\n"
								+ "2. Borrar cuenta bancaria\n" + "3. Volver a la pantalla anterior");
						opcion = introducirInput("Elija una opción:\n> ");
						} while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3"));
						
						switch (opcion) {
						case "1":
//							crearCuentaBancaria();
							break;
						case "2":
//							borrarCuentaBancaria();
							break;
						case "3":
							inicioSesionRegistro();
							break;
						}
					} else {
						do {
							System.out.println("\n[CLIENTE]\n" + "1. Realizar transferencia\n"
									+ "2. Depositar dinero\n" + "3. Retirar dinero\n" + "4. Volver a la pantalla anterior");
							opcion = introducirInput("Elija una opción:\n> ");
							} while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3") && !opcion.equals("4"));
						
						switch (opcion) {
						case "1":
//							realizarTransferencia();
							break;
						case "2":
//							depositarDinero();
							break;
						case "3":
//							retirarDinero();
							break;
						case "4":
							inicioSesionRegistro();
							break;	
						}
					}
				}
			}
		} else {
			inicioSesionRegistro();
		}

	}

	private void solicitarDatosRegistro() {
		String nombre = introducirInput("Introduzca su nombre y primer apellido:\n> ");
		String direccion = introducirInput("Introduzca su dirección:\n> ");
		String telefono = introducirInput("Introduzca su teléfono:\n> ");

		// Valida el formato del teléfono
		telefono = validarFormatoTelefono(telefono);

		String tipoUsuario = introducirInput("Introduzca el tipo de usuario (administrador (A) o cliente (C)):\n> ")
				.toUpperCase();

		while (!tipoUsuario.equals("A") && !tipoUsuario.equals("C")) {
			System.out.println("El tipo de usuario debe ser administrador (A) o cliente (C).");
			tipoUsuario = introducirInput("Inténtelo de nuevo:\n> ").toUpperCase();
		}

		String correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");

		// Valida el formato del correo electrónico
		correoElectronico = validarFormatoCorreoElectronico(correoElectronico);

		while (!sau.validarCorreo(correoElectronico, usuarios)) {
			correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");
			correoElectronico = validarFormatoCorreoElectronico(correoElectronico);
		}

		String contrasenya = introducirInput("Introduzca su contraseña:\n> ");

		// Valida el formato de la contraseña
		contrasenya = validarFormatoContrasenya(contrasenya);

		Usuario usuario = new Usuario(nombre, direccion, telefono, correoElectronico, contrasenya, tipoUsuario);
		usuarios.add(usuario);

		System.out.println();
		inicioSesionRegistro();
	}

	private String validarFormatoTelefono(String telefono) {
		while (!telefono.matches("^(\\+34|0034|34)?[6789]\\d{8}$")) {
			System.out.println("El formato del teléfono es incorrecto. Debe contener 9 números enteros del 0 al 9.");
			telefono = introducirInput("Inténtelo de nuevo:\n> ");
		}
		return telefono;
	}

	private String validarFormatoContrasenya(String contrasenya) {
		while (!contrasenya.matches("^(?=.*[0-9]).{8,12}$")) {
			System.out.println(
					"El formato de la contraseña es incorrecto. Debe tener de 8 a 12 caracteres y al menos un número.");
			contrasenya = introducirInput("Inténtelo de nuevo:\n> ");
		}
		return contrasenya;
	}

	private String validarFormatoCorreoElectronico(String correoElectronico) {
		while (!correoElectronico.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			System.out.println(
					"El formato del correo electrónico es incorrecto. Debe ser similar al siguiente: usuario@example.com");
			correoElectronico = introducirInput("Inténtelo de nuevo:\n> ");
		}

		return correoElectronico;
	}

	public void solicitarDatosCuenta() {
		
	}

	private String introducirInput(String mensaje) {
		try {
			System.out.print(mensaje);
			return input.readLine();
		} catch (IOException e) {
			System.out.println("ERROR: algo ha fallado.");
			return null;
		}
	}
}
