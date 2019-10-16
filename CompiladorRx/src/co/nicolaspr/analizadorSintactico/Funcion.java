package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class Funcion {

	private Token nombre;
	private ArrayList<Parametro> parametros;
	private Token retorno;
	private ArrayList<Sentencia> sentencias;

	public Funcion(Token nombre, ArrayList<Parametro> parametros, Token retorno, ArrayList<Sentencia> sentencias) {
		super();
		this.nombre = nombre;
		this.parametros = parametros;
		this.retorno = retorno;
		this.sentencias = sentencias;
	}

	@Override
	public String toString() {
		return "Funcion [nombre=" + nombre + ", parametros=" + parametros + ", retorno=" + retorno + ", sentencias="
				+ sentencias + "]";
	}

	public DefaultMutableTreeNode getArbolVisual() {

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Funcion");

		raiz.add(new DefaultMutableTreeNode("Nombre: " + nombre.getLexema()));
		raiz.add(new DefaultMutableTreeNode("retorno: " + retorno.getLexema()));

		if (parametros != null) {
			DefaultMutableTreeNode parametrosNodo = new DefaultMutableTreeNode("Parametros");
			for (Parametro p : parametros) {
				parametrosNodo.add(p.getArbolVisual());
			}

			raiz.add(parametrosNodo);
		}

		if (sentencias != null) {
			DefaultMutableTreeNode sentenciasNodo = new DefaultMutableTreeNode("Sentencias");

			for (Sentencia se : sentencias) {
				sentenciasNodo.add(se.getArbolVisual());
			}
			raiz.add(sentenciasNodo);
		}

		return raiz;
	}

}
