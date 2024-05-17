package bankEasy.controlador;

import java.util.ArrayList;

import bankEasy.modelo.Usuario;

public class SistemaAutenticacionUsuario {

	public boolean autenticarUsuario(String correoElectronico, String contrasenya, ArrayList<Usuario> usuarios) {
		if (correoElectronico == null || correoElectronico.isEmpty()) {
			System.out.println("El correo electrónico no puede ser nulo o estar vacío.");
			return false;
		}

		if (contrasenya == null || contrasenya.isEmpty()) {
			System.out.println("La contraseña no puede ser nula o estar vacía.");
			return false;
		}

		for (Usuario usuario : usuarios) {
			if (correoElectronico.equals(usuario.getCorreoElectronico())) {
				if (contrasenya.equals(usuario.getContrasenya())) {
					return true;
				} else {
					System.out.println("La contraseña introducida es incorrecta.");
					return false;
				}
			}
		}

		System.out.println("El correo electrónico introducido no se encuentra en la base de datos.");
		return false;
	}

	public boolean validarCorreo(String correoElectronico, ArrayList<Usuario> usuarios) {
		if (correoElectronico == null || correoElectronico.isEmpty()) {
			System.out.println("El correo electrónico no puede ser nulo o estar vacío.");
			return false;
		}

		for (Usuario usuario : usuarios) {
			if (correoElectronico.equals(usuario.getCorreoElectronico())) {
				System.out.println("El correo electrónico introducido ya se encuentra en la base de datos.");
				return false;
			}
		}

		return true;
	}
}
