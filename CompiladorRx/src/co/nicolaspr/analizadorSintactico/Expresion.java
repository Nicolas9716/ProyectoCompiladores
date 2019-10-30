package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

public class Expresion {
	private ExpresionAritmetica expresionAritmetica;
	private ExpresionRelacional expresionRelacional;
	private ExpresionLogica expresionLogica;
	private ExpresionCadena expresionCadena;

	public Expresion(ExpresionAritmetica expresionAritmetica) {
		super();
		this.expresionAritmetica = expresionAritmetica;
	}

	public Expresion(ExpresionRelacional expresionRelacional) {
		super();
		this.expresionRelacional = expresionRelacional;
	}

	public Expresion(ExpresionLogica expresionLogica) {
		super();
		this.expresionLogica = expresionLogica;
	}

	public Expresion(ExpresionCadena expresionCadena) {
		super();
		this.expresionCadena = expresionCadena;
	}
	
	public DefaultMutableTreeNode getArbolVisual() {
		return null;
	}

}
