package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

/**
 * @author darwi
 *
 */
public class Parametro {

	private Token nombre, tipoDato;

	public Parametro(Token nombre, Token tipoDato) {
		super();
		nombre = nombre;
		this.tipoDato = tipoDato;
	}

	@Override
	public String toString() {
		return "Parametro [nombre=" + nombre + ", tipoDato=" + tipoDato + "]";
	}

	public DefaultMutableTreeNode getArbolVisual() {
		
		 DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Parámetro");

		 nodo.add(new DefaultMutableTreeNode("Nombre: " + nombre.getLexema()));
		 
		 return nodo;
	}

}
