package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

public class UnidadDeCompilacion {
	private ArrayList<Funcion> listaFunciones;

	/**
	 * Metodo construtor que debe recibir como parametro una lista de funciones
	 * 
	 * @param listaFunciones
	 */
	public UnidadDeCompilacion(ArrayList<Funcion> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}

}
