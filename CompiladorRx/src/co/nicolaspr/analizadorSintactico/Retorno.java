package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class Retorno {
	private Token palabraReservada;
	private Expresion expresion;
	private Token finSentencia;
	public Retorno(Token palabraReservada, Expresion expresion, Token finSentencia) {
		super();
		this.palabraReservada = palabraReservada;
		this.expresion = expresion;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "Retorno [palabraReservada=" + palabraReservada + ", expresion=" + expresion + ", finSentencia="
				+ finSentencia + "]";
	}
	
	


}
