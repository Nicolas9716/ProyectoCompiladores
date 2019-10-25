package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class Termino {
	private Token termino;

	public Termino(Token termino) {
		super();
		this.termino = termino;
	}

	@Override
	public String toString() {
		return "Termino [termino=" + termino + "]";
	}
	

}
