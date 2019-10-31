package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

public class ExpresionAritmetica extends Expresion{

	private ExpresionAritmetica eA;
	private ExpresionAritmeticaAuxiliar eAux;
	private ValorNumerico vl;

	public ExpresionAritmetica(ExpresionAritmetica eA, ExpresionAritmeticaAuxiliar eAux) {
		super();
		this.eA = eA;
		this.eAux = eAux;
	}

	public ExpresionAritmetica(ValorNumerico vl, ExpresionAritmeticaAuxiliar eAux) {
		super();
		this.vl = vl;
		this.eAux = eAux;
	}

	public ExpresionAritmetica geteA() {
		return eA;
	}

	public void seteA(ExpresionAritmetica eA) {
		this.eA = eA;
	}

	public ExpresionAritmeticaAuxiliar geteAux() {
		return eAux;
	}

	public void seteAux(ExpresionAritmeticaAuxiliar eAux) {
		this.eAux = eAux;
	}

	public ValorNumerico getVl() {
		return vl;
	}

	public void setVl(ValorNumerico vl) {
		this.vl = vl;
	}

	@Override
	public String toString() {
		return "ExpresionAritmetica [eA=" + eA + ", eAux=" + eAux + ", vl=" + vl + "]";
	}

	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Expresion Aritmética");
		return nodo;
	}

}
