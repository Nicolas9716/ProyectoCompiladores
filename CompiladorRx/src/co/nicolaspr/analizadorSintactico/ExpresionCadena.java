package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class ExpresionCadena extends Expresion{
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
	
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Cadena");
		return nodo;
	}
	

}
