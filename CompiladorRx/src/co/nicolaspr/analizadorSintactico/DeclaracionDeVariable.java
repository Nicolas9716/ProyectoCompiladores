package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class DeclaracionDeVariable {
	private Token tipoDato,identificador,finSentencia;

	public DeclaracionDeVariable(Token tipoDato, Token identificador, Token finSentencia) {
		super();
		this.tipoDato = tipoDato;
		this.identificador = identificador;
		this.finSentencia = finSentencia;
	}

	@Override
	public String toString() {
		return "DeclaracionDeVariable [tipoDato=" + tipoDato + ", identificador=" + identificador + ", finSentencia="
				+ finSentencia + "]";
	}
	

}
