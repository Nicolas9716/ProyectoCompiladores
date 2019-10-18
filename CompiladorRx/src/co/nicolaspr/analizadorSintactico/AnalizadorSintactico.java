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
			Token fun = tokenActual;
			obtenerSiguienteToken();

			if (estipoDato()) {
				Token tipoRetorno = tokenActual;
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token identificador = tokenActual;
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
						Token parIzq = tokenActual;
						obtenerSiguienteToken();

						ArrayList<Parametro> parametros = esListaDeParametro();

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
							Token parDer = tokenActual;
							obtenerSiguienteToken();

							if (tokenActual.getCategoria() == Categoria.LLAVE_IZQ) {
								Token llaveIzq = tokenActual;
								obtenerSiguienteToken();

								ArrayList<Sentencia> sentencias = esListaSentencias();

								if (tokenActual.getCategoria() == Categoria.LLAVE_DER) {
									Token llaveDer = tokenActual;
									obtenerSiguienteToken();
									return new Funcion(fun, tipoRetorno, identificador, parIzq, parametros, parDer,
											llaveIzq, sentencias, llaveDer);
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
					reportarError("Falta el nombre de la función");
				}
			} else {
				reportarError("Falta tipo de retorno");
			}

		}

		return null;
	}

	private boolean estipoDato() {
		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("vacio")
				|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("entero")
				|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getLexema().equals("decimal")
				|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("cadena")
				|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getLexema().equals("logico")) {
			return true;
		}
		return false;
	}

	/*
	 * <esListaDeParametro>::= <parametro>[<esListaDeParametro>]
	 * 
	 */
	public ArrayList<Parametro> esListaDeParametro() {

		ArrayList<Parametro> parametros = new ArrayList<Parametro>();
		Parametro p = esParametro();

		while (p != null) {
			parametros.add(p);
			p = esParametro();
		}

		return parametros;

	}

	private Parametro esParametro() {
		if (estipoDato()) {
			Token tipoDato = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();
				return new Parametro(tipoDato, identificador);
			}
		}
		return null;
	}

	public ArrayList<Sentencia> esListaSentencias() {
		ArrayList<Sentencia> sentencias = new ArrayList<Sentencia>();
		Sentencia s = esSentencia();

		while (s != null) {
			sentencias.add(s);
			s = esSentencia();
		}

		return sentencias;

	}

	private Sentencia esSentencia() {
		Condicion c = esCondicion();
		if (c != null) {
			return new Sentencia(c);
		}

		DeclaracionDeVariable d = esDeclaracionDeVariable();
		if (d != null) {
			return new Sentencia(d);
		}

		AsignacionDeVariable a = esAsignacion();
		if (a != null) {
			return new Sentencia(a);
		}

		Impresion b = esImpresion();
		if (b != null) {
			return new Sentencia(b);
		}

		Retorno e = esRetorno();
		if (e != null) {
			return new Sentencia(e);
		}

		Leer f = esLectura();
		if (f != null) {
			return new Sentencia(f);
		}

		Ciclo ciclo = esCiclo();
		if (ciclo != null) {
			return new Sentencia(ciclo);
		}

		InvocacionFuncion i = esInvocacion();
		if (i != null) {
			return new Sentencia(i);
		}

		return null;
	}

	private InvocacionFuncion esInvocacion() {
		// TODO Auto-generated method stub
		return null;
	}

	private Leer esLectura() {
		// TODO Auto-generated method stub
		return null;
	}

	private Ciclo esCiclo() {
		// TODO Auto-generated method stub
		return null;
	}

	private DeclaracionDeVariable esDeclaracionDeVariable() {
		// TODO Auto-generated method stub
		return null;
	}

	private Impresion esImpresion() {
		// TODO Auto-generated method stub
		return null;
	}

	private Retorno esRetorno() {
		// TODO Auto-generated method stub
		return null;
	}

	private Condicion esCondicion() {
		// TODO Auto-generated method stub
		return null;
	}

	private AsignacionDeVariable esAsignacion() {
		// TODO Auto-generated method stub
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
