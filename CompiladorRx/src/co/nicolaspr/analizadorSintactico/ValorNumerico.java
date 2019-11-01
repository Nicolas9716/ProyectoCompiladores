package co.nicolaspr.analizadorSintactico;

import co.nicolaspr.analizadorLexico.Token;
/**
 * En esta clase podemos crear un numero, negativo o positivo
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
public class ValorNumerico {
	
	private Token signo,Tipo;

	public ValorNumerico(Token signo, Token tipo) {
		super();
		this.signo = signo;
		Tipo = tipo;
	}

	/**
	 * @return the signo
	 */
	public Token getSigno() {
		return signo;
	}

	/**
	 * @param signo the signo to set
	 */
	public void setSigno(Token signo) {
		this.signo = signo;
	}

	/**
	 * @return the tipo
	 */
	public Token getTipo() {
		return Tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(Token tipo) {
		Tipo = tipo;
	}
	
	

}
