package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;
import co.nicolaspr.analizadorSemantico.Simbolo;
import co.nicolaspr.analizadorSemantico.TablaSimbolos;

/**
 * Esta clase sirve para la creacion de un retorno en el lengiaje Rx
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
public class Retorno extends Sentencia {
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

	@Override
	protected void crearTablaSimbolo(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {

		if (!ambito.getTipo().equals("vacio")) {

			expresion.analizarSemantica(tablaSimbolos, errores, ambito);

			boolean v1 = expresion instanceof ExpresionAritmetica;
			boolean v2 = expresion instanceof ExpresionLogica;
			boolean v3 = expresion instanceof ExpresionRelacional;
			boolean v4 = expresion instanceof ExpresionCadena;

			if (v1 && (!ambito.getTipo().equals("entero") && !ambito.getTipo().equals("decimal"))) {
				errores.add("El tipo de retorno no coincide");
			} else if (v2 && !ambito.getTipo().equals("logico")) {
				errores.add("El tipo de retorno no coincide");
			} else if (v3 && !ambito.getTipo().equals("logico")) {
				errores.add("El tipo de retorno no coincide");
			} else if (v4 && !ambito.getTipo().equals("cadena")) {
				errores.add("El tipo de retorno no coincide");
			}

		}
	}

	@Override
	public String getJavaCode() {
		// TODO Auto-generated method stub
		return null;
	}

}
