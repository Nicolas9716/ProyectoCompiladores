package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

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

	public DefaultMutableTreeNode getArbolVisual() {

		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Termino");
		nodo.add(new DefaultMutableTreeNode("Nombre Variable: " + termino.getLexema()));

		return nodo;
	}

}
