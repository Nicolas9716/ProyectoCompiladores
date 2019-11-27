package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorSemantico.Simbolo;
import co.nicolaspr.analizadorSemantico.TablaSimbolos;



/**
 * Esta clase me ayuda a crear una expresion
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
public abstract class Expresion {

	public Expresion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DefaultMutableTreeNode getArbolVisual() {
		return null;
	}

	public abstract String getJavaCode();

	public abstract void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito);


}
