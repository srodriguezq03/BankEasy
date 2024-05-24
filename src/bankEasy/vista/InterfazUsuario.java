package bankEasy.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bankEasy.controlador.GestionCuentas;
import bankEasy.controlador.SistemaAutenticacionUsuario;
import bankEasy.modelo.Cuenta;
import bankEasy.modelo.TipoCuentaBancaria;
import bankEasy.modelo.Usuario;

public class InterfazUsuario {

	ArrayList<Usuario> usuarios = new ArrayList<>();
	ArrayList<Cuenta> cuentas = new ArrayList<>();
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	SistemaAutenticacionUsuario sau = new SistemaAutenticacionUsuario();
	GestionCuentas gc = new GestionCuentas();

	/**
	 * Método principal que inicia el proceso de la interfaz de usuario.
	 */
	public void start() {
		System.out.println("¡Bienvenido a BankEasy!");
		inicioSesionRegistro();
	}

	/**
	 * Muestra el menú principal para iniciar sesión, registrarse o salir de la
	 * aplicación.
	 */
	public void inicioSesionRegistro() {
		String opcion = null;
		do {
			do {
				System.out.println("\nMenú principal:\n" + "1. Iniciar sesión\n" + "2. Registrarse\n"
						+ "3. Salir de la aplicación");
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
			}
		} while (!opcion.equals("3"));

		System.out.println("\nSaliendo del programa...");
	}

	/**
	 * Muestra el menú de opciones para el administrador.
	 */
	public void mostrarMenuAdministrador() {
		String opcion = null;
		String nombreDuenyo = null;

		do {
			System.out.println("\n[ADMINISTRADOR]\n" + "1. Crear cuenta bancaria\n" + "2. Borrar cuenta bancaria\n"
					+ "3. Volver a la pantalla anterior");
			opcion = introducirInput("Elija una opción:\n> ");

			switch (opcion) {
			case "1":
				nombreDuenyo = introducirInput(
						"Introduzca el nombre y primer apellido del dueño de la cuenta bancaria que deseas crear:\n> ");
				boolean clienteEncontrado = false;

				for (Usuario cliente : usuarios) {
					if (cliente.getNombre().equals(nombreDuenyo)) {
						clienteEncontrado = true;
						String input = introducirInput(
								"Introduzca el tipo de cuenta (AHORRO, CORRIENTE, INVERSION):\n> ").toUpperCase();
						try {
							TipoCuentaBancaria tipoCuenta = TipoCuentaBancaria.valueOf(input);
							Cuenta cuenta = gc.crearCuenta(cliente.getNombre(), tipoCuenta);
							cuentas.add(cuenta);
							cliente.anyadirCuenta(cuenta);
							System.out.println("Cuenta creada con éxito.");
						} catch (IllegalArgumentException e) {
							System.out.println("El número de cuenta no se encuentra en la base de datos.");
						}
						break;
					}
				}

				if (!clienteEncontrado) {
					System.out.println("El cliente no se encuentra en la base de datos.");
				}

				break;
			case "2":
				nombreDuenyo = introducirInput(
						"Introduzca el nombre y primer apellido del dueño de la cuenta que deseas eliminar:\n> ");
				boolean clienteEncontradoEliminar = false;

				for (Usuario cliente : usuarios) {
					if (cliente.getNombre().equals(nombreDuenyo)) {
						clienteEncontradoEliminar = true;
						for (Cuenta cuenta : cuentas) {
							if (cuenta.getNombreDuenyo().equals(cliente.getNombre())) {
								System.out.println(cuenta);
							}
						}

						String numeroCuenta = introducirInput(
								"Introduzca el número de la cuenta que desea eliminar:\n> ");
						for (Cuenta cuenta : cuentas) {
							if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
								if (!gc.eliminarCuenta(cuenta, cuentas)) {
									System.out.println("No se ha podido eliminar la cuenta.");
									break;
								}
								cliente.eliminarCuenta(cuenta);
								System.out.println("Cuenta eliminada con éxito.");
								break;
							}
						}

						System.out.println("El número de cuenta no se encuentra en la base de datos.");
						break;
					}
				}

				if (!clienteEncontradoEliminar) {
					System.out.println("El cliente no se encuentra en la base de datos.");
				}

				break;
			}
		} while (!opcion.equals("3"));
	}

	/**
	 * Muestra el menú de opciones para el cliente.
	 * 
	 * @param usuario
	 * @param usuario
	 */
	public void mostrarMenuCliente(Usuario usuario) {
		String opcion = null;
		String numeroCuenta = null;

		do {
			do {
				System.out.println("\n[CLIENTE]\n" + "1. Realizar transferencia\n" + "2. Depositar dinero\n"
						+ "3. Retirar dinero\n" + "4. Volver a la pantalla anterior");
				opcion = introducirInput("Elija una opción:\n> ");
			} while (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3") && !opcion.equals("4"));

			switch (opcion) {
			case "1":
				// Transferencia de dinero
				System.out.println("No implementado aún.");
				break;
			case "2":
				usuario.administraCuenta(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(
						"Introduzca el número de la cuenta en la que quiera depositar dinero:\n> ");
				boolean cuentaEncontrada = false;
				
				for (Cuenta cuenta : cuentas) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;
						try {
							double monto = Double.parseDouble(introducirInput("Introduzca el monto a ingresar:\n> "));
							cuenta.depositarDinero(monto);
						} catch (NumberFormatException e) {
							System.out.println(
									"El monto ingresado no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1).");
						}

						break;
					}
				}
				
				if (!cuentaEncontrada) {
					System.out.println("El número de cuenta no se encuentra en la base de datos.");
				}

				break;
			case "3":
				usuario.administraCuenta(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(
						"Introduzca el número de la cuenta de la que quiera retirar dinero:\n> ");
				cuentaEncontrada = false;

				for (Cuenta cuenta : cuentas) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;
						try {
							double monto = Double.parseDouble(introducirInput("Introduzca el monto a retirar:\n> "));
							cuenta.retirarDinero(monto);
						} catch (NumberFormatException e) {
							System.out.println(
									"El monto ingresado no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1).");
						}

						break;
					}
				}

				if (!cuentaEncontrada) {
					System.out.println("El número de cuenta no se encuentra en la base de datos.");
				}

				break;
			}
		} while (!opcion.equals("4"));
	}

	/**
	 * Solicita al usuario los datos necesarios para iniciar sesión.
	 */
	public void solicitarDatosInicioSesion() {
		String correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");
		String contrasenya = introducirInput("Introduzca su contraseña:\n> ");

		if (sau.autenticarUsuario(correoElectronico, contrasenya, usuarios)) {
			for (Usuario usuario : usuarios) {
				if (usuario.getCorreoElectronico().equals(correoElectronico)) {
					if (usuario.getTipoCuentaUsuario().equalsIgnoreCase("A")) {
						mostrarMenuAdministrador();
					} else {
						mostrarMenuCliente(usuario);
					}
				}
			}
		}
	}

	/**
	 * Solicita al usuario los datos necesarios para registrarse.
	 */
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
	}

	/**
	 * Valida el formato del teléfono introducido.
	 *
	 * @param telefono El teléfono introducido por el usuario.
	 * @return El teléfono validado.
	 */
	private String validarFormatoTelefono(String telefono) {
		while (!telefono.matches("^(\\+34|0034|34)?[6789]\\d{8}$")) {
			System.out.println("El formato del teléfono es incorrecto. Debe contener 9 números enteros del 0 al 9.");
			telefono = introducirInput("Inténtelo de nuevo:\n> ");
		}
		return telefono;
	}

	/**
	 * Valida el formato de la contraseña introducida.
	 *
	 * @param contrasenya La contraseña introducida por el usuario.
	 * @return La contraseña validada.
	 */
	private String validarFormatoContrasenya(String contrasenya) {
		while (!contrasenya.matches("^(?=.*[0-9]).{8,12}$")) {
			System.out.println(
					"El formato de la contraseña es incorrecto. Debe tener de 8 a 12 caracteres y al menos un número.");
			contrasenya = introducirInput("Inténtelo de nuevo:\n> ");
		}
		return contrasenya;
	}

	/**
	 * Valida el formato del correo electrónico introducido.
	 *
	 * @param correoElectronico El correo electrónico introducido por el usuario.
	 * @return El correo electrónico validado.
	 */
	private String validarFormatoCorreoElectronico(String correoElectronico) {
		while (!correoElectronico.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			System.out.println(
					"El formato del correo electrónico es incorrecto. Debe ser similar al siguiente: usuario@example.com");
			correoElectronico = introducirInput("Inténtelo de nuevo:\n> ");
		}

		return correoElectronico;
	}

	/**
	 * Lee y devuelve la entrada del usuario desde la consola.
	 *
	 * @param mensaje El mensaje a mostrar al usuario.
	 * @return La entrada del usuario.
	 */
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
