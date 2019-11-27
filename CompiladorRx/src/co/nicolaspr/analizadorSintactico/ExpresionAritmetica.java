package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Categoria;
import co.nicolaspr.analizadorSemantico.Simbolo;
import co.nicolaspr.analizadorSemantico.TablaSimbolos;

/**
 * Esta clase nos ayuda a crear una expresion aritmetica tradicional en el
 * lenguaje Rx
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
public class ExpresionAritmetica extends Expresion {

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

	@Override
	public String getJavaCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void analizarSemantica(TablaSimbolos tablaSimbolos, ArrayList<String> errores, Simbolo ambito) {

		if (vl != null) {

			if (vl.getTipo().getCategoria() == Categoria.IDENTIFICADOR) {

				Simbolo s = tablaSimbolos.buscarSimboloVariable(vl.getTipo().getLexema(), ambito,
						vl.getTipo().getFila(), vl.getTipo().getColumna());

				if (s == null) {
					errores.add("La variable " + vl.getTipo().getLexema() + " no existe");
				} else {
					if (!(s.getTipo().equals("entero") || s.getTipo().equals("decimal"))) {
						errores.add("La variable " + vl.getTipo().getLexema() + " no es numérica");
					}
				}

			}

		}

		if (eA != null) {
			eA.analizarSemantica(tablaSimbolos, errores, ambito);
		}

		if (eAux != null) {
			eAux.analizarSemantica(tablaSimbolos, errores, ambito);
		}

	}

}
