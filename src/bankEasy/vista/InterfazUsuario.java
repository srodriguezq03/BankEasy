package bankEasy.vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import bankEasy.controlador.GestionCuentas;
import bankEasy.controlador.SistemaAutenticacionUsuario;
import bankEasy.controlador.ValidarPatrones;
import bankEasy.modelo.Cuenta;
import bankEasy.modelo.TipoCuentaBancaria;
import bankEasy.modelo.Usuario;

public class InterfazUsuario {

	ArrayList<Usuario> usuarios = new ArrayList<>();
	ArrayList<Cuenta> cuentas = new ArrayList<>();
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	SistemaAutenticacionUsuario sau = new SistemaAutenticacionUsuario();
	GestionCuentas gc = new GestionCuentas();
	ValidarPatrones vp = new ValidarPatrones();

	/**
	 * Método principal que inicia el proceso de la interfaz de usuario.
	 */
	public void start() {
		System.out.println("¡Bienvenido a BankEasy!");
		usuarios.add(
				new Usuario("Santiago Rodríguez", "C/ Entreplazas", "695070407", "santi@gmail.com", "1830abc42", "C"));
		usuarios.add(new Usuario("Pedro Gaspar Rodríguez", "C/ Entreplazas", "6902434264", "pedro@gmail.com",
				"1830abc42", "C"));
		usuarios.add(new Usuario("Admin", "Oficina", "666777888", "admin@gmail.com", "1830abc42", "A"));
		inicioSesionRegistro();
	}

	/**
	 * Muestra el menú principal para iniciar sesión, registrarse o salir de la
	 * aplicación.
	 */
	public void inicioSesionRegistro() {
		String opcion = null;
		do {
			System.out.println(
					"\nMenú principal:\n" + "1. Iniciar sesión\n" + "2. Registrarse\n" + "3. Salir de la aplicación");
			opcion = introducirInput("Elija una opción:\n> ");

			if (!opcion.equals("1") && !opcion.equals("2") && !opcion.equals("3")) {
				System.out.println("ERROR: debe introducir una opción del 1 al 3.");
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
						String input = introducirInput("Introduzca el tipo de cuenta (AHORRO o CORRIENTE):\n> ")
								.toUpperCase();
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
		String nombreDestino = null;

		do {
			System.out.println("\n[CLIENTE]\n" + "1. Realizar transferencia\n" + "2. Depositar dinero\n"
					+ "3. Retirar dinero\n" + "4. Mostrar cuentas bancarias\n" + "5. Volver a la pantalla anterior");
			opcion = introducirInput("Elija una opción:\n> ");

			switch (opcion) {
			case "1":
				nombreDestino = introducirInput(
						"Introduzca el nombre y primer apellido del dueño de la cuenta bancaria a la que deseas hacer una transferencia:\n> ");
				boolean destinoEncontrado = false;

				Cuenta cuentaDestino = null;
				for (Cuenta cuenta : cuentas) {
					if (cuenta.getNombreDuenyo().equals(nombreDestino)
							&& cuenta.getTipoCuentaBancaria() == TipoCuentaBancaria.CORRIENTE) {
						cuentaDestino = cuenta;
						destinoEncontrado = true;
						break;
					}
				}

				if (!destinoEncontrado) {
					System.out.println(
							"El dueño de la cuenta no se encuentra en la base de datos o no posee una cuenta corriente.");
					break;
				}

				try {
					double monto = Double.parseDouble(introducirInput("Introduzca el monto a transferir:\n> "));

					boolean origenEncontrado = false;
					for (Cuenta cuentaOrigen : cuentas) {
						if (cuentaOrigen.getNombreDuenyo().equals(usuario.getNombre())
								&& cuentaOrigen.getTipoCuentaBancaria() == TipoCuentaBancaria.CORRIENTE) {
							origenEncontrado = true;

							if (cuentaOrigen.realizarTransferencia(nombreDestino, monto)) {
								if (cuentaDestino.recibirTransferencia(cuentaOrigen.getNombreDuenyo(), monto)) {
									System.out.println("¡Dinero transferido con éxito!");
								} else {
									System.out.println("Transferencia fallida.");
								}
							} else {
								System.out.println("Transferencia fallida.");
								System.out.println(
										"Asegúrese de tener suficiente dinero en la cuenta para la transferencia y que la cantidad sea mayor que 0.");
							}

							break;
						}
					}

					if (!origenEncontrado) {
						System.out.println("Usted no posee una cuenta corriente.");
					}

				} catch (NumberFormatException e) {
					System.out.println(
							"El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1).");
				}

				break;
			case "2":
				usuario.mostrarCuentas(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(
						"Introduzca el número de la cuenta en la que quiera depositar dinero:\n> ");
				boolean cuentaEncontrada = false;

				for (Cuenta cuenta : cuentas) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;

						try {
							double monto = Double.parseDouble(introducirInput("Introduzca el monto a ingresar:\n> "));
							if (!cuenta.depositarDinero(monto)) {
								System.out.println("Depósito fallido.");
								System.out.println("La cantidad a depositar debe ser mayor que 0.");
								break;
							}

							System.out.println("¡Dinero depositado con éxito!");
						} catch (NumberFormatException e) {
							System.out.println(
									"El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1).");
						}

						break;
					}
				}

				if (!cuentaEncontrada) {
					System.out.println("El número de cuenta no se encuentra en la base de datos.");
				}

				break;
			case "3":
				usuario.mostrarCuentas(); // Muestra las cuentas a nombre del usuario
				numeroCuenta = introducirInput(
						"Introduzca el número de la cuenta de la que quiera retirar dinero:\n> ");
				cuentaEncontrada = false;

				for (Cuenta cuenta : cuentas) {
					if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
						cuentaEncontrada = true;

						try {
							double monto = Double.parseDouble(introducirInput("Introduzca el monto a retirar:\n> "));
							if (!cuenta.retirarDinero(monto)) {
								System.out.println("Retirada fallida.");
								System.out.println(
										"Asegúrese de tener suficiente dinero en la cuenta para la retirada y que la cantidad sea mayor que 0.");
								break;
							}

							System.out.println("¡Dinero retirado con éxito!");
						} catch (NumberFormatException e) {
							System.out.println(
									"El monto introducido no es válido. Por favor, introduzca un número (si es decimal debe llevar el siguiente formato: 56.1).");
						}

						break;
					}
				}

				if (!cuentaEncontrada) {
					System.out.println("El número de cuenta no se encuentra en la base de datos.");
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
		telefono = vp.validarFormatoTelefono(telefono);

		String tipoUsuario = introducirInput("Introduzca el tipo de usuario (administrador (A) o cliente (C)):\n> ")
				.toUpperCase();

		while (!tipoUsuario.equals("A") && !tipoUsuario.equals("C")) {
			System.out.println("El tipo de usuario debe ser administrador (A) o cliente (C).");
			tipoUsuario = introducirInput("Inténtelo de nuevo:\n> ").toUpperCase();
		}

		String correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");

		// Valida el formato del correo electrónico
		correoElectronico = vp.validarFormatoCorreoElectronico(correoElectronico);

		while (!sau.validarCorreo(correoElectronico, usuarios)) {
			correoElectronico = introducirInput("Introduzca su correo electrónico:\n> ");
			correoElectronico = vp.validarFormatoCorreoElectronico(correoElectronico);
		}

		String contrasenya = introducirInput("Introduzca su contraseña:\n> ");

		// Valida el formato de la contraseña
		contrasenya = vp.validarFormatoContrasenya(contrasenya);

		Usuario usuario = new Usuario(nombre, direccion, telefono, correoElectronico, contrasenya, tipoUsuario);
		usuarios.add(usuario);
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
