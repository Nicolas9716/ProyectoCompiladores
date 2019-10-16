package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import co.nicolaspr.analizadorLexico.Categoria;
import co.nicolaspr.analizadorLexico.Token;

public class AnalizadorSintactico {
	private ArrayList<Token> listaTokens;
	private Token tokenActual;
	private int posicionActual;
	private ArrayList<ErrorSintactico> listaErrores;
	private UnidadDeCompilacion unidadDeCompilacion;

	public AnalizadorSintactico(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
		this.tokenActual = listaTokens.get(posicionActual);
		this.listaErrores = new ArrayList<ErrorSintactico>();

	}

	/**
	 * Metodo para iniciar el analisis sintactico, verifica si la unidad de
	 * compilacion es correcta
	 */
	public void analizar() {
		this.unidadDeCompilacion = esUnidadDeCompilacion();
	}

	/**
	 * <unidadDeCompilacion> ::= <listaDeFunciones>
	 * 
	 * La unidad de compilacion es la clase sobre la que se va a programar
	 * 
	 * @return null si no hay funciones o retorna una nueva unidad de compilacion
	 *         sobre la cual se programo
	 * 
	 */
	public UnidadDeCompilacion esUnidadDeCompilacion() {

		ArrayList<Funcion> f = esListaDeFunciones();

		if (f != null) {
			return new UnidadDeCompilacion(f);
		}

		return null;

	}

	/**
	 * <listaDeFunciones> ::= <funcion> [<listaDeFunciones>]
	 * 
	 * Metodo para veriicar que hay una o varias funciones en la unidad de
	 * compilacion
	 * 
	 * @return
	 */
	public ArrayList<Funcion> esListaDeFunciones() {

		ArrayList<Funcion> funciones = new ArrayList<Funcion>();
		Funcion f = esFuncion();

		while (f != null) {
			funciones.add(f);
			f = esFuncion();
		}

		return funciones;
	}

	/**
	 * <Funcion>::=fun<tipoRetorno>identificador"("[<listaParametro>]")""{"[<listaDeSentencia>]"}"
	 * 
	 * @return
	 */
	public Funcion esFuncion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("fun")) {
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA) {
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token nombre = tokenActual;
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
						obtenerSiguienteToken();

						ArrayList<Parametro> parametros = esListaDeParametro();

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
							obtenerSiguienteToken();
							Token retorno = null;
							if (tokenActual.getCategoria() == Categoria.LLAVE_IZQ) {
								obtenerSiguienteToken();
								retorno = esTipoRetorno();
								obtenerSiguienteToken();
								
								if(retorno == null) {
									reportarError("Debe especificar el tipo de retorno");
								}

								if (tokenActual.getCategoria() == Categoria.LLAVE_DER) {
									ArrayList<Sentencia> sentencias = new ArrayList<Sentencia>();
									return new Funcion(nombre, parametros, retorno, sentencias);
								} else {
									reportarError("Falta llave derechaa");
								}

							} else {
								reportarError("Falta llave izquierda");
							}

						} else {
							reportarError("Falta parentesis derecho");
						}

					} else {
						reportarError("Falta parentesis izquierdo");
					}
				} else {
					reportarError("Debe escribir un nombre para la funcion");
				}
			} else {
				reportarError("Falt tipo de retorno");
			}

		}

		return null;
	}

	public ArrayList<Parametro> esListaDeParametro() {

		return null;
	}

	public Token esTipoRetorno() {

		return null;
	}

	/**
	 * Metopdo que me ayuda a recorrer el codigo fuente de una forma ordenada
	 */
	public void obtenerSiguienteToken() {

		posicionActual++;
		if (posicionActual < listaTokens.size()) {
			tokenActual = listaTokens.get(posicionActual);
		}
	}

	public void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * @return the unidadDeCompilacion
	 */
	public UnidadDeCompilacion getUnidadDeCompilacion() {
		return unidadDeCompilacion;
	}

	/**
	 * @param unidadDeCompilacion the unidadDeCompilacion to set
	 */
	public void setUnidadDeCompilacion(UnidadDeCompilacion unidadDeCompilacion) {
		this.unidadDeCompilacion = unidadDeCompilacion;
	}
	
	

}
