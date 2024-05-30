package bankEasy.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cuenta {

	private String nombreDuenyo;

	private String numeroCuenta;

	private double saldo;

	private TipoCuentaBancaria tipoCuentaBancaria;

	private ArrayList<Movimiento> movimientos;

	public Cuenta() {
		super();
	}

	public Cuenta(String nombreDuenyo, String numeroCuenta, double saldo, TipoCuentaBancaria tipoCuentaBancaria) {
		super();
		this.nombreDuenyo = nombreDuenyo;
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.tipoCuentaBancaria = tipoCuentaBancaria;
		this.movimientos = new ArrayList<Movimiento>();
	}

	public String getNombreDuenyo() {
		return nombreDuenyo;
	}

	public void setNombreDuenyo(String nombreDuenyo) {
		this.nombreDuenyo = nombreDuenyo;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public TipoCuentaBancaria getTipoCuentaBancaria() {
		return tipoCuentaBancaria;
	}

	public void setTipoCuentaBancaria(TipoCuentaBancaria tipoCuentaBancaria) {
		this.tipoCuentaBancaria = tipoCuentaBancaria;
	}

	public ArrayList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(ArrayList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	public boolean recibirTransferencia(String origen, double monto) {
		if (monto > 0) {
			saldo += monto;

			Movimiento transferencia = new Movimiento(obtenerFechaActual(), "Transferencia realizada a " + origen,
					monto, this.numeroCuenta, origen);
			movimientos.add(transferencia);

			return true;
		} else {
			return false;
		}
	}
	
	public boolean realizarTransferencia(String destino, double monto) {
		if (saldo >= monto && monto > 0) {
			saldo -= monto;

			Movimiento transferencia = new Movimiento(obtenerFechaActual(), "Transferencia realizada a " + destino,
					monto, this.numeroCuenta, destino);
			movimientos.add(transferencia);

			return true;
		} else {
			return false;
		}
	}

	public boolean depositarDinero(double monto) {
		if (monto > 0) {
			saldo += monto;
			
			Movimiento deposito = new Movimiento(obtenerFechaActual(), "Depósito de dinero", monto, this.numeroCuenta, this.numeroCuenta);
			movimientos.add(deposito);
			
			return true;
		}
		
		return false;
	}

	public boolean retirarDinero(double monto) {
		if (saldo >= monto && monto > 0) {
			saldo -= monto;
			
			Movimiento retirada = new Movimiento(obtenerFechaActual(), "Retirada de dinero", monto, this.numeroCuenta, this.numeroCuenta);
			movimientos.add(retirada);
			
			return true;
		}
		
		return false;
	}

	private String obtenerFechaActual() {
		LocalDate fechaActual = LocalDate.now();
		String fechaFormateada = fechaActual.toString();
		return fechaFormateada;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("Cuenta ").append(tipoCuentaBancaria).append("\n");
		stringBuilder.append("Nombre del dueño: ").append(nombreDuenyo).append("\n");
		stringBuilder.append("Saldo: ").append(saldo).append("\n");
		stringBuilder.append("Movimientos:\n");
		for (Movimiento movimiento : movimientos) {
			stringBuilder.append("-------------------------------").append("\n");
			stringBuilder.append(movimiento).append("\n");
		}

		return stringBuilder.toString();
	}
}
