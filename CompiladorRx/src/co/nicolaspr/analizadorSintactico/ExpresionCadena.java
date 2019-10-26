package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class ExpresionCadena {
	private Token cadena,mas;
	private Expresion expresion;
	public ExpresionCadena(Token cadena) {
		super();
		this.cadena = cadena;
	}
	public ExpresionCadena(Token cadena, Token mas, Expresion expresion) {
		super();
		this.cadena = cadena;
		this.mas = mas;
		this.expresion = expresion;
	}
	
	

}
