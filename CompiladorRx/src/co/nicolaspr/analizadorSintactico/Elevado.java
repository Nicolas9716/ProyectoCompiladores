package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Elevado extends Sentencia {
	private Token palabraReservada, parIzq;
	private ExpresionAritmetica aritmetica;
	private Token parDer;

	public Elevado(Token palabraReservada, Token parIzq, ExpresionAritmetica aritmetica, Token parDer) {
		super();
		this.palabraReservada = palabraReservada;
		this.parIzq = parIzq;
		this.aritmetica = aritmetica;
		this.parDer = parDer;
	}

	@Override
	public String toString() {
		return "Elevado [palabraReservada=" + palabraReservada + ", parIzq=" + parIzq + ", aritmetica=" + aritmetica
				+ ", parDer=" + parDer + "]";
	}

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Elevado");
		nodo.add(new DefaultMutableTreeNode("Identificador: " + palabraReservada.getLexema()));
		return nodo;
	}

}
