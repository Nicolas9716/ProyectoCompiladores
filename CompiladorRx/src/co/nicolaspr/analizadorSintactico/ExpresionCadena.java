package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;
import co.nicolaspr.analizadorSemantico.Simbolo;
import co.nicolaspr.analizadorSemantico.TablaSimbolos;

/**
 * Esta clase nos ayuda a crear una expresion cadena
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
public class ExpresionCadena extends Expresion {
	private Token cadena, mas;
	private Expresion expresion;

	public ExpresionCadena(Token cadena) {
		super();
		this.cadena = cadena;
	}

	public ExpresionCadena(Token cadena, Token mas, Expresion expresion) {
		super();
		this.cadena = cadena;
		this.mas = mas;
		this.expresion = expresion;
	}

	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Cadena");
		return nodo;
	}

	@Override
	public String getJavaCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {
		// TODO Auto-generated method stub

	}

}
