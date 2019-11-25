package co.nicolaspr.analizadorLexico;

/**
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 *
 */
public class ErrorLexico {
	private String mensaje;
	private int fila, columna;

	public ErrorLexico(String mensaje, int fila, int columna) {
		super();
		this.mensaje = mensaje;
		this.fila = fila;
		this.columna = columna;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	@Override
	public String toString() {
		return "ErrorLexico [mensaje=" + mensaje + ", fila=" + fila + ", columna=" + columna + "]";
	}

}
