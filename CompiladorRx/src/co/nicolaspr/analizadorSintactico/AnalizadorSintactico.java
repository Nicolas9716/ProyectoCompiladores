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
		if (tokenActual.getCategoria() == Categoria.PUNTO) {
			Token inv = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token id = tokenActual;
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
					Token parIzq = tokenActual;
					obtenerSiguienteToken();

					ArrayList<Argumento> parametros = esListaArgumentosRecursivo();

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						Token parDer = tokenActual;
						obtenerSiguienteToken();

						if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
							Token finSentencia = tokenActual;
							obtenerSiguienteToken();
							return new InvocacionFuncion(inv, id, parIzq, parametros, parDer, finSentencia);
						} else {
							reportarError("Falta el finsentencia en la funcion");
						}
					} else {
						reportarError("Falta parentisis derecho en la funcion");
					}
				} else {
					reportarError("Falta parentisis izquierdo en la funcion");
				}
			} else {
				reportarError("Falta el identificador de la funcion a invocar");
			}
		}
		return null;
	}

	private ArrayList<Argumento> esListaArgumentosRecursivo() {
		ArrayList<Argumento> a = null;
		Expresion e = esExpresion();
		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR && e.toString().length() < 3) {
			a = new ArrayList<Argumento>();
			a.add(new Argumento(tokenActual));
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				a.addAll(esListaArgumentosRecursivo());
			}
			return a;
		}
		if (e != null) {
			a = new ArrayList<Argumento>();
			a.add(new Argumento(e));
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				a.addAll(esListaArgumentosRecursivo());
			}
			return a;
		}

		return null;
	}

	private Leer esLectura() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("leer")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token id = tokenActual;
				obtenerSiguienteToken();

				if (estipoDato()) {
					Token tipoDato = tokenActual;
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
						Token finSentencia = tokenActual;
						obtenerSiguienteToken();
						return new Leer(palabraReservada, id, tipoDato, finSentencia);
					} else {
						reportarError("Falta el final de sentencia en el leer");
					}
				} else {
					reportarError("Falta el tipo de dato de leer");
				}
			} else {
				reportarError("Falta el identificador de leer");
			}

		}

		return null;
	}

	private Ciclo esCiclo() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("mientras")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
				Token parIzq = tokenActual;
				obtenerSiguienteToken();

				ExpresionLogica expLog = esExpresionLogica();

				if (expLog != null) {
					obtenerSiguienteToken();

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
								return new Ciclo(palabraReservada, parIzq, expLog, parDer, llaveIzq, sentencias,
										llaveDer);
							} else {
								reportarError("Falta llave derecha en el ciclo");
							}

						} else {
							reportarError("Falta llave izquierda en en el ciclo");
						}
					} else {
						reportarError("Falta parentesis derecho en el ciclo");
					}
				} else {
					reportarError("Falta expresion logica en ciclo");
				}
			} else {
				reportarError("Falta parentesis izquierdo en el ciclo");
			}

		}

		return null;
	}

	private DeclaracionDeVariable esDeclaracionDeVariable() {
		if (estipoDato()) {
			Token tipoDato = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token identificador = tokenActual;
				obtenerSiguienteToken();
				if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
					Token finSentencia = tokenActual;
					obtenerSiguienteToken();
					return new DeclaracionDeVariable(tipoDato, identificador, finSentencia);
				} else {
					reportarError("Falta el fin de sentencia en la declaracion de variable");
				}

			} else {
				reportarError("Falta el identificador de la declaracion de variable");
			}
		}
		return null;

	}

	private Impresion esImpresion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("imprimir")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
				Token parIzq = tokenActual;
				obtenerSiguienteToken();

				Expresion exp = esExpresion();

				if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
					Token parDer = tokenActual;
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
						Token finSentencia = tokenActual;
						obtenerSiguienteToken();
						return new Impresion(palabraReservada, parIzq, exp, parDer, finSentencia);
					} else {

						reportarError("Falta el final de sentencia en la impresion");
					}

				} else {
					reportarError("Falta parentesis derecho en la impresion");
				}

			} else {

				reportarError("Falta parentesis izquierdo en la impresion");
			}

		}

		return null;

	}

	private Expresion esExpresion() {
		ExpresionCadena expresionCadena = esExpresionCadena();
		if (expresionCadena != null) {
			Expresion e = new Expresion(expresionCadena);
			return e;
		}

		ExpresionAritmetica expAritmetica = esExpresionAritmetica();
		if (expAritmetica != null) {
			Expresion e = new Expresion(expAritmetica);
			return e;
		}

		ExpresionRelacional expresionRelacional = esExpresionRelacional();
		if (expresionRelacional != null) {
			Expresion e = new Expresion(expresionRelacional);
			return e;
		}

		ExpresionLogica expresionLogica = esExpresionLogica();
		if (expresionLogica != null) {
			Expresion e = new Expresion(expresionLogica);
			return e;
		}

		return null;

	}

	private ExpresionCadena esExpresionCadena() {
		
		if (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
			Token cadena = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria()!=Categoria.OPERADOR_ARITMETICO&&!tokenActual.getLexema().equals("+")) {
				return new ExpresionCadena(cadena);
			} else {
				Token mas = tokenActual;
				obtenerSiguienteToken();
				Expresion ex = esExpresion();
				if (ex != null) {
					return new ExpresionCadena(cadena, mas, ex);
				} else {
					reportarError("Falta la expresion");
				}
			}
		}

		return null;
	}

	private ExpresionRelacional esExpresionRelacional() {
		// TODO Auto-generated method stub
		return null;
	}

	private ExpresionAritmetica esExpresionAritmetica() {
		// TODO Auto-generated method stub
		return null;
	}

	private Retorno esRetorno() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("retornar")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();
			Expresion expresion = esExpresion();
			if (expresion != null) {
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
					Token finSentencia = tokenActual;
					obtenerSiguienteToken();
					return new Retorno(palabraReservada, expresion, finSentencia);
				} else {
					reportarError("Falta el final de sentencia en el retorno");
				}
			} else {
				reportarError("Falta la expresion en el retorno");
			}
		}

		return null;
	}

	private Condicion esCondicion() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("si")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
				Token parIzq = tokenActual;
				obtenerSiguienteToken();

				ExpresionLogica expLog = esExpresionLogica();

				if (expLog != null) {
					obtenerSiguienteToken();

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
								Condicion c = new Condicion(palabraReservada, parIzq, expLog, parDer, llaveIzq,
										sentencias, llaveDer);
								return c;

							} else {
								reportarError("Falta llave derecha en la condicion");
							}
						} else {
							reportarError("Falta llave izquierda en la condicion");
						}
					} else {
						reportarError("Falta parentesis derecho en la condicion");
					}
				} else {
					reportarError("Falta expresion logica en la condicion");
				}
			} else {
				reportarError("Falta parentesis izquierdo en la condicion");
			}
		}

		return null;
	}

	private ExpresionLogica esExpresionLogica() {
		// TODO Auto-generated method stub
		return null;
	}

	private AsignacionDeVariable esAsignacion() {

		if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
			Token id = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.OPERADOR_ASIGNACION) {
				Token opAsignacion = tokenActual;
				obtenerSiguienteToken();
				Termino t = esTermino();
				if (t != null) {
					obtenerSiguienteToken();

					if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
						Token finSentencia = tokenActual;
						obtenerSiguienteToken();
						return new AsignacionDeVariable(id, opAsignacion, t, finSentencia);
					} else {
						reportarError("Falta fin de sentencia en la asginacion");
					}
				} else {
					reportarError("Falta el termino que se asigna en la asginacion");
				}
			} else {
				reportarError("Falta operador de asignacion en la asginacion");
			}
		}

		return null;
	}

	private Termino esTermino() {
		if (tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.IDENTIFICADOR
				|| tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
			return new Termino(tokenActual);
		}

		return null;
	}

	/**
	 * Metodo que me ayuda a recorrer el codigo fuente de una forma ordenada
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
