package bankEasy.modelo;

public class Movimiento {

	private String fecha;

	private String concepto;

	private double cantidad;

	private String origen;

	private String destino;

	public Movimiento() {
		super();
	}

	public Movimiento(String fecha, String concepto, double cantidad, String origen, String destino) {
		super();
		this.fecha = fecha;
		this.concepto = concepto;
		this.cantidad = cantidad;
		this.origen = origen;
		this.destino = destino;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	@Override
	public String toString() {
		return String.format("Fecha: %s\n" + "Concepto: %s\n" + "Cantidad: %.2f\n" + "Origen: %s\n"
				+ "Destino: %s", fecha, concepto, cantidad, origen, destino);
	}
}
