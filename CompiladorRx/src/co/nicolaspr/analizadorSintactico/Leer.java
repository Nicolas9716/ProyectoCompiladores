package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;

public class Leer {
	private Token palabraReservada;
	private Token id;
	private Token tipoDato;
	private Token finSentencia;
	public Leer(Token palabraReservada, Token id, Token tipoDato, Token finSentencia) {
		super();
		this.palabraReservada = palabraReservada;
		this.id = id;
		this.tipoDato = tipoDato;
		this.finSentencia = finSentencia;
	}
	@Override
	public String toString() {
		return "Leer [palabraReservada=" + palabraReservada + ", id=" + id + ", tipoDato=" + tipoDato
				+ ", finSentencia=" + finSentencia + "]";
	}
	

}
