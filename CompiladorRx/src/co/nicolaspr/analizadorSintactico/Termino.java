package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

/**
 * Esta clase nos ayuda a crear un termino
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
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
		nodo.add(new DefaultMutableTreeNode("Valor: " + termino.getLexema()));

		return nodo;
	}

	public String getJavaCode() {
		return termino.getJavaCode();
	}

}