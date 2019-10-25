package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class Impresion {
	private Token palabrareser,parIzq;
	private Expresion expresion;
	private Token parDer,finSentencia;
	public Impresion(Token palabrareser, Token parIzq, Expresion expresion, Token parDer, Token finSentencia) {
		super();
		this.palabrareser = palabrareser;
		this.parIzq = parIzq;
		this.expresion = expresion;
		this.parDer = parDer;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "Impresion [palabrareser=" + palabrareser + ", parIzq=" + parIzq + ", expresion=" + expresion
				+ ", parDer=" + parDer + ", finSentencia=" + finSentencia + "]";
	}
	
	

}
