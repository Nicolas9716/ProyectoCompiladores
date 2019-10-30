package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import co.nicolaspr.analizadorLexico.Token;

/*
 * 
 * <Funcion>::=fun<tipoRetorno>identificador"("[<listaParametro>]")""{"[<listaDeSentencia>]"}"
 */
public class Funcion {

	private Token palabraFun, tipoRetorno, identificador, parIzq;
	private ArrayList<Parametro> parametros;
	private Token parDer, llaveIzq;
	private ArrayList<Sentencia> sentencias;
	private Token llaveDer;

	public Funcion(Token palabraFun, Token tipoRetorno, Token identificador, Token parIzq,
			ArrayList<Parametro> parametros, Token parDer, Token llaveIzq, ArrayList<Sentencia> sentencias,
			Token llaveDer) {
		super();
		this.palabraFun = palabraFun;
		this.tipoRetorno = tipoRetorno;
		this.identificador = identificador;
		this.parIzq = parIzq;
		this.parametros = parametros;
		this.parDer = parDer;
		this.llaveIzq = llaveIzq;
		this.sentencias = sentencias;
		this.llaveDer = llaveDer;
	}

	@Override
	public String toString() {
		return "Funcion [palabraFun=" + palabraFun + ", tipoRetorno=" + tipoRetorno + ", identificador=" + identificador
				+ ", parIzq=" + parIzq + ", parametros=" + parametros + ", parDre=" + parDer + ", llaveIzq=" + llaveIzq
				+ ", sentencias=" + sentencias + ", llaveDer=" + llaveDer + "]";
	}

	public DefaultMutableTreeNode getArbolVisual() {

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Funcion");
		raiz.add(new DefaultMutableTreeNode(identificador.getLexema()));

		if(tipoRetorno!=null) {
			raiz.add( new DefaultMutableTreeNode("Tipo de retorno: "+tipoRetorno.getLexema()) );	
		}
		
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
