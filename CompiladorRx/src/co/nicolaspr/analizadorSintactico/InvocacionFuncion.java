package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import co.nicolaspr.analizadorLexico.Token;

public class InvocacionFuncion {
	private Token punto,identifi,parIzq;
	private ArrayList<Parametro>parametros;
	private Token parDer,finSentencia;
	public InvocacionFuncion(Token punto, Token identifi, Token parIzq, ArrayList<Parametro> parametros, Token parDer,
			Token finSentencia) {
		super();
		this.punto = punto;
		this.identifi = identifi;
		this.parIzq = parIzq;
		this.parametros = parametros;
		this.parDer = parDer;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "InvocacionFuncion [punto=" + punto + ", identifi=" + identifi + ", parIzq=" + parIzq + ", parametros="
				+ parametros + ", parDer=" + parDer + ", finSentencia=" + finSentencia + "]";
	}
	

}
