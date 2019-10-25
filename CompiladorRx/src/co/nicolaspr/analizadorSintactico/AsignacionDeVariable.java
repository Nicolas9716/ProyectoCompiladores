package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class AsignacionDeVariable {
	private Token identificador, opAsignacion;
	private Termino termino;
	private Token finSentencia;
	public AsignacionDeVariable(Token identificador, Token opAsignacion, Termino termino, Token finSentencia) {
		super();
		this.identificador = identificador;
		this.opAsignacion = opAsignacion;
		this.termino = termino;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "AsignacionDeVariable [identificador=" + identificador + ", opAsignacion=" + opAsignacion + ", termino="
				+ termino + ", finSentencia=" + finSentencia + "]";
	}
	


}
