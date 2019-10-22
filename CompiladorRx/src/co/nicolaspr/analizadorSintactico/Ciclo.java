package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import co.nicolaspr.analizadorLexico.Token;

public class Ciclo {
	private Token palabraReservada, parIzq;
	private ExpresionLogica expresionLogica;
	private Token parDer, llaveIzq;
	private ArrayList<Sentencia> sentencias;
	private Token llaveDer;

	public Ciclo(Token palabraReservada, Token parIzq, ExpresionLogica expresionLogica, Token parDer, Token llaveIzq,
			ArrayList<Sentencia> sentencias, Token llaveDer) {
		super();
		this.palabraReservada = palabraReservada;
		this.parIzq = parIzq;
		this.expresionLogica = expresionLogica;
		this.parDer = parDer;
		this.llaveIzq = llaveIzq;
		this.sentencias = sentencias;
		this.llaveDer = llaveDer;
	}

	@Override
	public String toString() {
		return "Ciclo [palabraReservada=" + palabraReservada + ", parIzq=" + parIzq + ", expresionLogica="
				+ expresionLogica + ", parDer=" + parDer + ", llaveIzq=" + llaveIzq + ", sentencias=" + sentencias
				+ ", llaveDer=" + llaveDer + "]";
	}

}
