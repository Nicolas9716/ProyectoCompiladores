package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;
import co.nicolaspr.analizadorSemantico.Simbolo;
import co.nicolaspr.analizadorSemantico.TablaSimbolos;

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

	@Override
	protected void crearTablaSimbolo(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJavaCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
