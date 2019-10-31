package co.nicolaspr.analizadorSintactico;


import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Argumento {
	
	private Token identificador;
	private Expresion exp;
	private Token separador;
	private Argumento argumentos;

	/**
	 * Metodo constructor de la clase cuando el argumento es un id
	 * 
	 * @param id
	 */
	public Argumento(Token identificador) {
		this.identificador = identificador;
	}

	/**
	 * Metodo constructor de la clase cuando el argumento es una expresion
	 * 
	 * @param Expresion
	 */
	public Argumento(Expresion exp) {
		this.exp = exp;
	}

	
	/**
	 * Metodo constructor de la clase, cuando el argumento tiene id separador y mas
	 * argumentos
	 * 
	 * 
	 */
	public Argumento(Token identificador, Token separador, Argumento argumentos) {
		this.identificador = identificador;
		this.separador = separador;
		this.argumentos = argumentos;
	}

	/**
	 * Metodo constructor de la clase, cuando el argumento tiene expresion,
	 * separador y mas argumentos
	 * 
	 * 
	 */

	public Argumento(Expresion exp, Token separador, Argumento argumentos) {
		this.exp = exp;
		this.separador = separador;
		this.argumentos = argumentos;
	}
	
	/**
	 * Devuelve el arbol visual de la clase Argumento
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Argumento");
		
		nodo.add(new DefaultMutableTreeNode(identificador.getLexema()));

		return nodo;
	}

}
