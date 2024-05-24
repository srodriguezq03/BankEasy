package bankEasy.controlador;

import java.util.ArrayList;
import java.util.Random;

import bankEasy.modelo.Cuenta;
import bankEasy.modelo.TipoCuentaBancaria;

public class GestionCuentas {

	/**
	 * Crea una nueva cuenta bancaria con un número de cuenta generado
	 * aleatoriamente.
	 *
	 * @param nombreDuenyo       El nombre del dueño de la cuenta.
	 * @param tipoCuentaBancaria El tipo de cuenta bancaria.
	 * @return La cuenta creada.
	 */
	public Cuenta crearCuenta(String nombreDuenyo, TipoCuentaBancaria tipoCuentaBancaria) {
		String numeroCuenta = generarNumeroDeCuenta();
		Cuenta cuenta = new Cuenta(nombreDuenyo, numeroCuenta, 0, tipoCuentaBancaria);
		return cuenta;
	}

	/**
	 * Genera un número de cuenta aleatorio de 8 cifras.
	 *
	 * @return Un número de cuenta de 8 cifras.
	 */
	private String generarNumeroDeCuenta() {
		Random random = new Random();
		int numero = 10000000 + random.nextInt(90000000); // Genera un número entre 10000000 y 99999999
		return String.valueOf(numero);
	}

	/**
	 * Elimina una cuenta bancaria.
	 *
	 * @param cuenta La cuenta bancaria que se desea eliminar.
	 * @return Un valor booleano que indica si la eliminación fue exitosa.
	 */
	public boolean eliminarCuenta(Cuenta cuenta, ArrayList<Cuenta> cuentas) {
		if (cuentas.remove(cuenta)) {
			return true;
		}
		
		return false;
	}
}
