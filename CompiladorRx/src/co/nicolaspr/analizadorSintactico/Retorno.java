package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Retorno extends Sentencia{
	private Token palabraReservada;
	private Expresion expresion;
	private Token finSentencia;
	public Retorno(Token palabraReservada, Expresion expresion, Token finSentencia) {
		super();
		this.palabraReservada = palabraReservada;
		this.expresion = expresion;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "Retorno [palabraReservada=" + palabraReservada + ", expresion=" + expresion + ", finSentencia="
				+ finSentencia + "]";
	}
	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Retorno");
		nodo.add(expresion.getArbolVisual());
		
		return nodo;
	}
	
	


}
