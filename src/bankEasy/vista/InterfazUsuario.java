package bankEasy.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bankEasy.controlador.GestionCuentas;
import bankEasy.controlador.SistemaAutenticacionUsuario;
import bankEasy.controlador.ValidarPatrones;
import bankEasy.modelo.Cuenta;
import bankEasy.modelo.TipoCuentaBancaria;
import bankEasy.modelo.Usuario;

public class InterfazUsuario {

	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	SistemaAutenticacionUsuario sau = new SistemaAutenticacionUsuario();
	GestionCuentas gc = new GestionCuentas();
	ValidarPatrones vp = new ValidarPatrones();

	// Códigos de escape ANSI para controlar los colores que se muestran por consola.
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BRIGHT_GREEN = "\u001B[92m";
	public static final String ANSI_BRIGHT_YELLOW = "\u001B[93m";
	public static final String ANSI_BRIGHT_BLUE = "\u001B[94m";
	public static final String ANSI_BRIGHT_MAGENTA = "\u001B[95m";
	public static final String ANSI_BRIGHT_CYAN = "\u001B[96m";

	/**
	 * Método principal que inicia el proceso de la interfaz de usuario.
	 */
	public void start() {
		System.out.println("¡Bienvenido a BankEasy!");
		sau.getUsuarios().add(
				new Usuario("Santiago Rodríguez", "C/ Entreplazas", "67456234", "santi@gmail.com", "1432cbd34", "C"));
		sau.getUsuarios().add(new Usuario("Pedro Gaspar Rodríguez", "C/ Entreplazas", "6902434264", "pedro@gmail.com",
				"1432cbd34", "C"));
		sau.getUsuarios().add(new Usuario("Admin", "Oficina", "666777888", "admin@gmail.com", "1432cbd34", "A"));
		inicioSesionRegistro();
	}

	/**
	 * Muestra el menú principal para iniciar sesión, registrarse o salir de la
	 * aplicación.
	 */
	public void inicioSesionRegistro() {
		String opcion = null;
		do {
			System.out.println(ANSI_BRIGHT_BLUE + "\nMenú principal:\n" + "1. Iniciar sesión\n" + "2. Registrarse\n"
					+ "3. Salir de la aplicación" + ANSI_RESET);
			opcion = introducirInput(ANSI_BRIGHT_CYAN + "Elija una opción:\n> " + ANSI_RESET);

			if (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3")) {
				System.out.println(ANSI_BRIGHT_YELLOW + "ERROR: debe introducir una opción del 1 al 3." + ANSI_RESET);
			}

			switch (opcion) {
			case "1":
				solicitarDatosInicioSesion();
				break;
			case "2":
				solicitarDatosRegistro();
				break;
			}
		} while (!opcion.equals("3"));

		System.out.println(ANSI_BRIGHT_GREEN + "\nSaliendo del programa..." + ANSI_RESET);
	}

	/**
	 * Muestra el menú de opciones para el administrador.
	 */
	public void mostrarMenuAdministrador() {
		String opcion = null;
		String nombreDuenyo = null;

		do {
			System.out.println(ANSI_BRIGHT_BLUE + "\n[ADMINISTRADOR]\n" + "1. Crear cuenta bancaria\n"
					+ "2. Borrar cuenta bancaria\n" + "3. Cerrar sesión" + ANSI_RESET);
			opcion = introducirInput(ANSI_BRIGHT_CYAN + "Elija una opción:\n> " + ANSI_RESET);

			switch (opcion) {
			case "1":
				nombreDuenyo = introducirInput(ANSI_BRIGHT_CYAN
						+ "Introduzca el nombre y primer apellido del dueño de la cuenta bancaria que deseas crear:\n> "
						+ ANSI_RESET);
				boolean clienteEncontrado = false;

				for (Usuario cliente : sau.getUsuarios()) {
					if (cliente.getNombre().equals(nombreDuenyo)) {
						clienteEncontrado = true;
						String input = introducirInput(ANSI_BRIGHT_CYAN
								+ "Introduzca el tipo de cuenta (AHORRO o CORRIENTE):\n> " + ANSI_RESET).toUpperCase();
						try {
							TipoCuentaBancaria tipoCuenta = TipoCuentaBancaria.valueOf(input);
							Cuenta cuenta = gc.crearCuenta(cliente.getNombre(), tipoCuenta);
							gc.getCuentas().add(cuenta);
							cliente.anyadirCuenta(cuenta);
							System.out.println(ANSI_BRIGHT_GREEN + "Cuenta creada con éxito." + ANSI_RESET);
						} catch (IllegalArgumentException e) {
							System.out.println(ANSI_BRIGHT_YELLOW
									+ "El número de cuenta no se encuentra en la base de datos." + ANSI_RESET);
						}
						break;
					}
				}

				if (!clienteEncontrado) {
					System.out.println(
							ANSI_BRIGHT_YELLOW + "El cliente no se encuentra en la base de datos." + ANSI_RESET);
				}

				break;
			case "2":
				nombreDuenyo = introducirInput(ANSI_BRIGHT_CYAN
						+ "Introduzca el nombre y primer apellido del dueño de la cuenta que deseas eliminar:\n> "
						+ ANSI_RESET);
				boolean clienteEncontradoEliminar = false;

				for (Usuario cliente : sau.getUsuarios()) {
					if (cliente.getNombre().equals(nombreDuenyo)) {
						clienteEncontradoEliminar = true;
						for (Cuenta cuenta : gc.getCuentas()) {
							if (cuenta.getNombreDuenyo().equals(cliente.getNombre())) {
								System.out.println(cuenta);
							}
						}

						String numeroCuenta = introducirInput(ANSI_BRIGHT_CYAN
								+ "Introduzca el número de la cuenta que desea eliminar:\n> " + ANSI_RESET);
						for (Cuenta cuenta : gc.getCuentas()) {
							if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
								if (!gc.eliminarCuenta(cuenta, gc.getCuentas())) {
									System.out.println(
											ANSI_BRIGHT_YELLOW + "No se ha podido eliminar la cuenta." + ANSI_RESET);
									break;
								}
								cliente.eliminarCuenta(cuenta);
								System.out.println(ANSI_BRIGHT_GREEN + "Cuenta eliminada con éxito." + ANSI_RESET);
								break;
							}
						}

						System.out.println(ANSI_BRIGHT_YELLOW
								+ "El número de cuenta no se encuentra en la base de datos." + ANSI_RESET);
						break;
					}
				}

				if (!clienteEncontradoEliminar) {
					System.out.println(
							ANSI_BRIGHT_YELLOW + "El cliente no se encuentra en la base de datos." + ANSI_RESET);
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
			System.out.println(ANSI_BRIGHT_BLUE + "\n[CLIENTE]\n" + "1. Realizar transferencia\n"
					+ "2. Depositar dinero\n" + "3. Retirar dinero\n" + "4. Mostrar cuentas bancarias\n"
					+ "5. Cerrar sesión" + ANSI_RESET);
			opcion = introducirInput(ANSI_BRIGHT_CYAN + "Elija una opción:\n> " + ANSI_RESET);

			switch (opcion) {
			case "1":
				numeroCuenta = introducirInput(ANSI_BRIGHT_CYAN
						+ "Introduzca el número de la cuenta bancaria a la que deseas hacer una transferencia:\n> "
						+ ANSI_RESET);
				boolean destinoEncontrado = false;

				Cuenta cuentaDestino = null;
				for (Cuenta cuenta : gc.getCuentas()) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)
							&& cuenta.getTipoCuentaBancaria() == TipoCuentaBancaria.CORRIENTE) {
						cuentaDestino = cuenta;
						destinoEncontrado = true;
						break;
					}
				}

				if (!destinoEncontrado) {
					System.out.println(ANSI_BRIGHT_YELLOW
							+ "El número de cuenta introducido no se encuentra en la base de datos o no pertenece a una cuenta corriente."
							+ ANSI_RESET);
					break;
				}

				try {
					double monto = Double.parseDouble(
							introducirInput(ANSI_BRIGHT_CYAN + "Introduzca el monto a transferir:\n> " + ANSI_RESET));
					boolean origenEncontrado = false;

					for (Cuenta cuentaOrigen : gc.getCuentas()) {
						if (cuentaOrigen.getNombreDuenyo().equals(usuario.getNombre())) {
							origenEncontrado = true;

							if (cuentaOrigen.realizarTransferencia(numeroCuenta, monto)) {
								if (cuentaDestino.recibirTransferencia(cuentaOrigen.getNombreDuenyo(), monto)) {
									System.out.println(
											ANSI_BRIGHT_GREEN + "¡Transferencia realizada con éxito!" + ANSI_RESET);
								} else {
									System.out.println(ANSI_BRIGHT_YELLOW + "Transferencia fallida." + ANSI_RESET);
								}
							} else {
								System.out.println(ANSI_BRIGHT_YELLOW + "Transferencia fallida." + ANSI_RESET);
								System.out.println(ANSI_BRIGHT_YELLOW
										+ "Asegúrese de tener suficiente dinero en la cuenta para la transferencia y que la cantidad sea mayor que 0."
										+ ANSI_RESET);
							}

							break;
						}
					}

					if (!origenEncontrado) {
						System.out.println(ANSI_BRIGHT_YELLOW + "Usted no posee una cuenta corriente." + ANSI_RESET);
					}

				} catch (NumberFormatException e) {
					System.out.println(ANSI_BRIGHT_YELLOW
							+ "El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1)."
							+ ANSI_RESET);
				}

				break;
			case "2":
				usuario.mostrarCuentas(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(ANSI_BRIGHT_CYAN
						+ "Introduzca el número de la cuenta en la que quiera depositar dinero:\n> " + ANSI_RESET);
				boolean cuentaEncontrada = false;

				for (Cuenta cuenta : gc.getCuentas()) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;

						try {
							double monto = Double.parseDouble(introducirInput(
									ANSI_BRIGHT_CYAN + "Introduzca el monto a ingresar:\n> " + ANSI_RESET));
							if (!cuenta.depositarDinero(monto)) {
								System.out.println(ANSI_BRIGHT_YELLOW + "Depósito fallido." + ANSI_RESET);
								System.out.println(ANSI_BRIGHT_YELLOW + "La cantidad a depositar debe ser mayor que 0."
										+ ANSI_RESET);
								break;
							}

							System.out.println(ANSI_BRIGHT_GREEN + "¡Dinero depositado con éxito!" + ANSI_RESET);
						} catch (NumberFormatException e) {
							System.out.println(ANSI_BRIGHT_YELLOW
									+ "El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1)."
									+ ANSI_RESET);
						}

						break;
					}
				}

				if (!cuentaEncontrada) {
					System.out.println(ANSI_BRIGHT_YELLOW + "El número de cuenta no se encuentra en la base de datos."
							+ ANSI_RESET);
				}

				break;
			case "3":
				usuario.mostrarCuentas(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(ANSI_BRIGHT_CYAN
						+ "Introduzca el número de la cuenta de la que quiera retirar dinero:\n> " + ANSI_RESET);
				cuentaEncontrada = false;

				for (Cuenta cuenta : gc.getCuentas()) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;

						try {
							double monto = Double.parseDouble(introducirInput(
									ANSI_BRIGHT_CYAN + "Introduzca el monto a retirar:\n> " + ANSI_RESET));
							if (!cuenta.retirarDinero(monto)) {
								System.out.println(ANSI_BRIGHT_YELLOW + "Retirada fallida." + ANSI_RESET);
								System.out.println(ANSI_BRIGHT_YELLOW
										+ "Asegúrese de tener suficiente dinero en la cuenta para la retirada y que la cantidad sea mayor que 0."
										+ ANSI_RESET);
								break;
							}

							System.out.println(ANSI_BRIGHT_GREEN + "¡Dinero retirado con éxito!" + ANSI_RESET);
						} catch (NumberFormatException e) {
							System.out.println(ANSI_BRIGHT_YELLOW
									+ "El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1)."
									+ ANSI_RESET);
						}

						break;
					}
				}

				if (!cuentaEncontrada) {
					System.out.println(ANSI_BRIGHT_YELLOW + "El número de cuenta no se encuentra en la base de datos."
							+ ANSI_RESET);
				}

				break;
			case "4":
				usuario.mostrarCuentas(); // Muestra las cuentas a nombre del usuario
				break;
			}
		} while (!opcion.equals("5"));
	}

	/**
	 * Solicita al usuario los datos necesarios para iniciar sesión.
	 */
	public void solicitarDatosInicioSesion() {
		String correoElectronico = introducirInput(
				ANSI_BRIGHT_CYAN + "Introduzca su correo electrónico:\n> " + ANSI_RESET);
		String contrasenya = introducirInput(ANSI_BRIGHT_CYAN + "Introduzca su contraseña:\n> " + ANSI_RESET);

		if (sau.autenticarUsuario(correoElectronico, contrasenya, sau.getUsuarios())) {
			for (Usuario usuario : sau.getUsuarios()) {
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
		String nombre = introducirInput(ANSI_BRIGHT_CYAN + "Introduzca su nombre y primer apellido:\n> " + ANSI_RESET);
		String direccion = introducirInput(ANSI_BRIGHT_CYAN + "Introduzca su dirección:\n> " + ANSI_RESET);
		String telefono = introducirInput(ANSI_BRIGHT_CYAN + "Introduzca su teléfono:\n> " + ANSI_RESET);

		// Valida el formato del teléfono
		telefono = vp.validarFormatoTelefono(telefono);

		String tipoUsuario = introducirInput(
				ANSI_BRIGHT_CYAN + "Introduzca el tipo de usuario (administrador (A) o cliente (C)):\n> " + ANSI_RESET)
				.toUpperCase();

		while (!tipoUsuario.equals("A") && !tipoUsuario.equals("C")) {
			System.out.println(
					ANSI_BRIGHT_YELLOW + "El tipo de usuario debe ser administrador (A) o cliente (C)." + ANSI_RESET);
			tipoUsuario = introducirInput(ANSI_BRIGHT_CYAN + "Inténtelo de nuevo:\n> " + ANSI_RESET).toUpperCase();
		}

		String correoElectronico = introducirInput(
				ANSI_BRIGHT_CYAN + "Introduzca su correo electrónico:\n> " + ANSI_RESET);

		// Valida el formato del correo electrónico
		correoElectronico = vp.validarFormatoCorreoElectronico(correoElectronico);

		while (!sau.validarCorreo(correoElectronico, sau.getUsuarios())) {
			correoElectronico = introducirInput(
					ANSI_BRIGHT_CYAN + "Introduzca su correo electrónico:\n> " + ANSI_RESET);
			correoElectronico = vp.validarFormatoCorreoElectronico(correoElectronico);
		}

		String contrasenya = introducirInput(ANSI_BRIGHT_CYAN + "Introduzca su contraseña:\n> " + ANSI_RESET);

		// Valida el formato de la contraseña
		contrasenya = vp.validarFormatoContrasenya(contrasenya);

		Usuario usuario = new Usuario(nombre, direccion, telefono, correoElectronico, contrasenya, tipoUsuario);
		sau.getUsuarios().add(usuario);
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
			System.out.println(ANSI_BRIGHT_YELLOW + "ERROR: algo ha fallado." + ANSI_RESET);
			return null;
		}
	}
}
