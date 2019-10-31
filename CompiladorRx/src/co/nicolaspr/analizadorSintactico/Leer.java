package co.nicolaspr.analizadorSintactico;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Leer extends Sentencia {
	private Token palabraReservada;
	private Token id;
	private Token finSentencia;

	public Leer(Token palabraReservada, Token id, Token finSentencia) {
		super();
		this.palabraReservada = palabraReservada;
		this.id = id;
		this.finSentencia = finSentencia;
	}

	@Override
	public String toString() {
		return "Leer [palabraReservada=" + palabraReservada + ", id=" + id + ", finSentencia=" + finSentencia + "]";
	}

	@Override
	public DefaultMutableTreeNode getArbolVisual() {

		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Lectura");

		nodo.add(new DefaultMutableTreeNode("Identificador: " + id.getLexema()));
		return nodo;
	}

}
