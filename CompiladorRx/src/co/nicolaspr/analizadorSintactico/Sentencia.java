package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 
 *
 */
public class Sentencia {
	private Condicion condicion;
	private DeclaracionDeVariable declaracionDeVariable;
	private AsignacionDeVariable asignacionDeVariable;
	private Impresion impresion;
	private Retorno retorno;
	private Leer leer;
	private Ciclo ciclo;
	private InvocacionFuncion invocarFuncion;

	public Sentencia(Condicion condicion) {
		this.condicion = condicion;
	}
	
	public Sentencia(DeclaracionDeVariable declaracionDeVariable) {
		this.declaracionDeVariable = declaracionDeVariable;
	}
	public Sentencia(AsignacionDeVariable asignacionDeVariable) {
		this.asignacionDeVariable = asignacionDeVariable;
	}
	public Sentencia(Impresion impresion) {
		this.impresion = impresion;
	}
	public Sentencia(Retorno retorno) {
		this.retorno = retorno;
	}
	public Sentencia(Leer leer) {
		this.leer = leer;
	}
	public Sentencia(Ciclo ciclo) {
		this.ciclo = ciclo;
	}
	public Sentencia(InvocacionFuncion invocacionFuncion) {
		this.invocarFuncion = invocacionFuncion;
	}


	

	public DefaultMutableTreeNode getArbolVisual() {

		return new DefaultMutableTreeNode("hijo se");
	}

}
