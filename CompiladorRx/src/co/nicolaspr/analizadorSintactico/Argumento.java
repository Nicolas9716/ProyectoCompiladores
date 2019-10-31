package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Argumento {

	private Expresion exp;

	/**
	 * Metodo constructor de la clase, cuando el argumento tiene expresion,
	 * separador y mas argumentos
	 * 
	 * 
	 */

	public Argumento(Expresion exp) {
		this.exp = exp;

	}

	/**
	 * Devuelve el arbol visual de la clase Argumento
	 * 
	 * @return
	 */
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Argumento");

		nodo.add(exp.getArbolVisual());

		return nodo;
	}

}
