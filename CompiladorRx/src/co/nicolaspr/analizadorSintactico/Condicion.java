package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import co.nicolaspr.analizadorLexico.Token;

public class Condicion {
	private Token palabraReservada,parIzq;
	private ExpresionLogica expresionLogica;
	private Token parDer,llaIzq;
	private ArrayList<Sentencia>sentencias;
	private Token llaDer;
	public Condicion(Token palabraReservada, Token parIzq, ExpresionLogica expresionLogica, Token parDer, Token llaIzq,
			ArrayList<Sentencia> sentencias, Token llaDer) {
		super();
		this.palabraReservada = palabraReservada;
		this.parIzq = parIzq;
		this.expresionLogica = expresionLogica;
		this.parDer = parDer;
		this.llaIzq = llaIzq;
		this.sentencias = sentencias;
		this.llaDer = llaDer;
	}
	@Override
	public String toString() {
		return "Condicion [palabraReservada=" + palabraReservada + ", parIzq=" + parIzq + ", expresionLogica="
				+ expresionLogica + ", parDer=" + parDer + ", llaIzq=" + llaIzq + ", sentencias=" + sentencias
				+ ", llaDer=" + llaDer + "]";
	}
	

}
