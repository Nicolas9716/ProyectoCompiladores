package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class UnidadDeCompilacion {
	private ArrayList<Funcion> listaFunciones;

	/**
	 * Metodo construtor que debe recibir como parametro una lista de funciones
	 * 
	 * @param listaFunciones
	 */
	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		super();
		this.listaFunciones = listaFunciones;
	}

	@Override
	public String toString() {
		return "UnidadDeCompilacion [listaFunciones=" + listaFunciones + "]";
	}

	public DefaultMutableTreeNode getArbolVisual() {

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Unidad de Compilacion");

		for (Funcion funcion : listaFunciones) {
			raiz.add(funcion.getArbolVisual());
		}

		return raiz;

	}

}
