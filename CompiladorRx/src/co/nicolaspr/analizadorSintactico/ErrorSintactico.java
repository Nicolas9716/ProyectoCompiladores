package co.nicolaspr.analizadorSintactico;

/**
 * Esta clase nos ayuda en la creacion de un error sintactico para despues
 * reportarlo
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
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

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the fila
	 */
	public int getFila() {
		return fila;
	}

	/**
	 * @param fila the fila to set
	 */
	public void setFila(int fila) {
		this.fila = fila;
	}

	/**
	 * @return the columna
	 */
	public int getColumna() {
		return columna;
	}

	/**
	 * @param columna the columna to set
	 */
	public void setColumna(int columna) {
		this.columna = columna;
	}

}