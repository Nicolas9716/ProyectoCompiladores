package co.nicolaspr.analizadorSintactico;

public class ErrorSintactico {

	private String mensaje;
	private int fila, columna;

	public ErrorSintactico(String mensaje, int fila, int columna) {
		super();
		this.mensaje = mensaje;
		this.fila = fila;
		this.columna = columna;
	}

	@Override
	public String toString() {
		return "ErrorSintactico [mensaje=" + mensaje + ", fila=" + fila + ", columna=" + columna + "]";
	}

}