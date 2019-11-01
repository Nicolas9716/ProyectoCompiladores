package co.nicolaspr.analizadorSintactico;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

public class ImpresionInv extends Sentencia {
	private Token palabrareser, parIzq;
	private Expresion expresion;
	private Token parDer, finSentencia;

	public ImpresionInv(Token palabrareser, Token parIzq, Expresion expresion, Token parDer, Token finSentencia) {
		super();
		this.palabrareser = palabrareser;
		this.parIzq = parIzq;
		this.expresion = expresion;
		this.parDer = parDer;
		this.finSentencia = finSentencia;
	}

	@Override
	public String toString() {
		return "Impresion [palabrareser=" + palabrareser + ", parIzq=" + parIzq + ", expresion=" + expresion
				+ ", parDer=" + parDer + ", finSentencia=" + finSentencia + "]";
	}

	@Override
	public DefaultMutableTreeNode getArbolVisual() {
		DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Impresion Inversa");
		
		if(expresion != null) {
		nodo.add(expresion.getArbolVisual());
		}else {
			nodo.add(new DefaultMutableTreeNode("Expresion: sin expresion"));
		}

		return nodo;
	}

}
