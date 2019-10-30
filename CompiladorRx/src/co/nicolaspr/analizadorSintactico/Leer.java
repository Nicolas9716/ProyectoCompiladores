package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Leer extends Sentencia{
	private Token palabraReservada;
	private Token id;
	private Token tipoDato;
	private Token finSentencia;
	public Leer(Token palabraReservada, Token id, Token tipoDato, Token finSentencia) {
		super();
		this.palabraReservada = palabraReservada;
		this.id = id;
		this.tipoDato = tipoDato;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "Leer [palabraReservada=" + palabraReservada + ", id=" + id + ", tipoDato=" + tipoDato
				+ ", finSentencia=" + finSentencia + "]";
	}
	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
