package bankEasy.controlador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ValidarPatrones {
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Valida el formato del teléfono introducido.
	 *
	 * @param telefono El teléfono introducido por el usuario.
	 * @return El teléfono validado.
	 */
	public String validarFormatoTelefono(String telefono) {
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
	public String validarFormatoContrasenya(String contrasenya) {
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
	public String validarFormatoCorreoElectronico(String correoElectronico) {
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
