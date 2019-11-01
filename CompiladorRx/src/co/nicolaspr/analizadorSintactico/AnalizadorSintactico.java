package co.nicolaspr.analizadorSintactico;

import java.util.ArrayList;

import co.nicolaspr.analizadorLexico.Categoria;
import co.nicolaspr.analizadorLexico.Token;

/**
 * Aqui esta todo lo relacionado en cuanto al analizador sintactico, desde sus
 * BNF programados, hasta su forma de hacer el analisis
 * 
 * @author Darwin Bonilla, Nicolas Rios y Santiago Vargas
 * @version 1.0.0
 */
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
	 * Hacer backtracking, ya que no corresponde el término
	 */
	public void hacerBTToken(int posicionToken) {
		posicionActual = posicionToken;
		if (posicionToken < listaTokens.size()) {
			tokenActual = listaTokens.get(posicionToken);
		} else {
			tokenActual = new Token("", Categoria.ERROR, 0, 0);
		}
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
	 * <Funcion>::=fun<esTipoDato>identificador"("[<esListaParametro>]")""{"[<esListaDeSentencia>]"}"
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

	/**
	 * Metodo que me dice si el token es un tipo de dato
	 * 
	 * @return true de ser un tipo de dato, false de no ser un tipo de dato
	 */
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
	 * <esListaDeParametro>::= <parametro>[","<esListaDeParametro>]
	 * 
	 */
	public ArrayList<Parametro> esListaDeParametro() {

		ArrayList<Parametro> parametros = new ArrayList<Parametro>();
		Parametro p = esParametro();

		while (p != null) {
			parametros.add(p);

			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				p = esParametro();
			} else {
				p = null;
			}
		}

		return parametros;

	}

	/**
	 * <esParametro>::= <esTipoDato> identificador
	 * 
	 * @return
	 */
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

	/**
	 * <esListaSentencia>::= <esSentencia> [<esListaSentencia>]
	 * 
	 * @return
	 */
	public ArrayList<Sentencia> esListaSentencias() {
		ArrayList<Sentencia> sentencias = new ArrayList<Sentencia>();
		Sentencia s = esSentencia();

		while (s != null) {
			sentencias.add(s);
			s = esSentencia();
		}

		return sentencias;

	}

	/**
	 * <esSentencia>::=
	 * <esCondicion>|<esDeclaracionDeVariable>|<esAsignacion>|<esImpresion>|<esRetorno>|
	 * <esLectura>|<esCiclo>|<esInvocacion>
	 * 
	 * @return
	 */
	private Sentencia esSentencia() {
		Condicion c = esCondicion();
		if (c != null) {
			return c;
		}

		DeclaracionDeVariable d = esDeclaracionDeVariable();
		if (d != null) {
			return d;
		}

		AsignacionDeVariable a = esAsignacion();
		if (a != null) {
			return a;
		}

		Impresion b = esImpresion();
		if (b != null) {
			return b;
		}

		Retorno e = esRetorno();
		if (e != null) {
			return e;
		}

		Leer f = esLectura();
		if (f != null) {
			return f;
		}

		Ciclo ciclo = esCiclo();
		if (ciclo != null) {
			return ciclo;
		}

		InvocacionFuncion i = esInvocacion();
		if (i != null) {
			return i;
		}

		return null;
	}

	/**
	 * <esInvocacion>::= "." identificador "(" [<esListaArgumentos>] ")" ";"
	 * 
	 * @return
	 */
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

					ArrayList<Argumento> argumentos = null;

					if (tokenActual.getCategoria() != Categoria.PARENTESIS_DER) {
						argumentos = esListaArgumentos();
					}

					if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
						Token parDer = tokenActual;
						obtenerSiguienteToken();

						if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
							Token finSentencia = tokenActual;
							obtenerSiguienteToken();
							return new InvocacionFuncion(inv, id, parIzq, argumentos, parDer, finSentencia);
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

	/**
	 * <ListaArgumentos> ::= <Argumento>[","<ListaArgumentos>]
	 * 
	 * @return
	 */
	public ArrayList<Argumento> esListaArgumentos() {

		ArrayList<Argumento> argumentos = new ArrayList<>();

		Argumento a = esArgumento();

		while (a != null) {
			argumentos.add(a);

			if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
				obtenerSiguienteToken();
				a = esArgumento();
			} else {
				a = null;
			}

		}

		return argumentos;
	}

	/**
	 * <Argumento> ::= <Expresion>
	 * 
	 * @return
	 */
	public Argumento esArgumento() {
		Expresion expresion = esExpresion();

		if (expresion != null) {

			return new Argumento(expresion);
		}

		return null;
	}

	/**
	 * <esLectura> ::= leer identificador ";"
	 * 
	 * @return
	 */
	private Leer esLectura() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("leer")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token id = tokenActual;
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
					Token finSentencia = tokenActual;
					obtenerSiguienteToken();
					return new Leer(palabraReservada, id, finSentencia);
				} else {
					reportarError("Falta el final de sentencia en el leer");
				}

			} else {
				reportarError("Falta el identificador de leer");
			}

		}

		return null;
	}
	
	/**
	 * <esLecturaInversa> ::= leerInv identificador ";"
	 * 
	 * @return
	 */
	private LeerInv esLecturaInversa() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("leerInv")) {
			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
				Token id = tokenActual;
				obtenerSiguienteToken();

				if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
					Token finSentencia = tokenActual;
					obtenerSiguienteToken();
					return new LeerInv(palabraReservada, id, finSentencia);
				} else {
					reportarError("Falta el final de sentencia en el leer");
				}

			} else {
				reportarError("Falta el identificador de leer");
			}

		}

		return null;
	}
	
	

	/**
	 * <esCliclo> ::= mientras "(" <esExpresionLogica> ")" "{" [<esListaSentencias>]
	 * "}"
	 * 
	 * @return
	 */
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

	/**
	 * <esDeclaracionDeVariable>::= <esTopoDato> identificador ";"
	 * 
	 * @return
	 */
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

	/**
	 * <esImpresion>::= imprimir "(" [<esExpresion>] ")" ";"
	 * 
	 * @return
	 */
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

	/**
	 * <esExpresion>::=
	 * <esExpresionAritmetica>|<esExpresionRelacional>|<esExpresionCadena>||<esExpresionLogica>
	 * 
	 * @return
	 */
	private Expresion esExpresion() {
		int posTokenAux = posicionActual;

		ExpresionAritmetica expAritmetica = esExpresionAritmetica();

		if (tokenActual.getCategoria() != Categoria.OPERADOR_RELACIONAL) {

			if (expAritmetica != null) {

				return expAritmetica;
			}
		} else {

			hacerBTToken(posTokenAux);
		}

		ExpresionRelacional expRelacional = esExpresionRelacional();

		if (tokenActual.getCategoria() != Categoria.OPERADOR_LOGICO) {

			if (expRelacional != null) {
				return expRelacional;
			}
		} else {

			hacerBTToken(posTokenAux);
		}

		ExpresionCadena expCadena = esExpresionCadena();
		if (expCadena != null) {
			return expCadena;
		}

		ExpresionLogica expLogica = esExpresionLogica();
		if (expLogica != null) {
			return expLogica;
		}

		return null;

	}

	/**
	 * <esExpresionCadena> ::= <cadena> | <cadena> "+" <expresion>
	 * 
	 * @return
	 */
	private ExpresionCadena esExpresionCadena() {

		if (tokenActual.getCategoria() == Categoria.CADENA_CARACTERES) {
			Token cadena = tokenActual;
			obtenerSiguienteToken();
			if (tokenActual.getCategoria() != Categoria.OPERADOR_ARITMETICO && !tokenActual.getLexema().equals("+")) {
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

	/**
	 * <esExpresionRelacional> ::= "(" <esExpresionAritmetica> <operadorRelacional>
	 * <esExpresionAritmetica> | <esExpresionAritmetica> <operadorRelacional>
	 * <esExpresionAritmetica>| falso |verdadero
	 * 
	 * @return
	 */
	private ExpresionRelacional esExpresionRelacional() {

		if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
			obtenerSiguienteToken();

			Token operador = null;

			ExpresionAritmetica ea = esExpresionAritmetica();

			if (ea != null) {
				if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {

					operador = tokenActual;

					obtenerSiguienteToken();
					ExpresionAritmetica ea1 = esExpresionAritmetica();
					if (ea1 != null) {

						if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {

							obtenerSiguienteToken();

							return new ExpresionRelacional(ea, operador, ea1);
						} else {
							reportarError("falta parentesis derecho");
						}

					} else {
						reportarError("falta la expresion aritmetica");
					}

				} else {
					reportarError("Falta operador relacional");
				}
			} else {

				if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getLexema().equals("verdadero")
						|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
								&& tokenActual.getLexema().equals("falso")) {

					return new ExpresionRelacional(tokenActual);

				}

			}

		} else {
			Token operador = null;

			ExpresionAritmetica ea = esExpresionAritmetica();

			if (ea != null) {
				if (tokenActual.getCategoria() == Categoria.OPERADOR_RELACIONAL) {

					operador = tokenActual;

					obtenerSiguienteToken();
					ExpresionAritmetica ea1 = esExpresionAritmetica();
					if (ea1 != null) {
						return new ExpresionRelacional(ea, operador, ea1);
					} else {
						reportarError("falta la expresion aritmetica");
					}

				} else {
					reportarError("Falta operador relacional");
				}
			} else {

				if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
						&& tokenActual.getLexema().equals("verdadero")
						|| tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA
								&& tokenActual.getLexema().equals("falso")) {

					return new ExpresionRelacional(tokenActual);

				}

			}
		}

		return null;
	}

	/**
	 * <ExpreAritmetica>::="(" <ExpreAritmetica> ")" [<EAAX>] | <ValorNumerico>
	 * [<EAAX>]
	 * 
	 * @return
	 */
	private ExpresionAritmetica esExpresionAritmetica() {

		if (tokenActual.getCategoria() == Categoria.PARENTESIS_IZQ) {
			obtenerSiguienteToken();
			ExpresionAritmetica eA = esExpresionAritmetica();

			if (eA != null) {
				if (tokenActual.getCategoria() == Categoria.PARENTESIS_DER) {
					obtenerSiguienteToken();
					ExpresionAritmeticaAuxiliar eAux = esExpresionAritmeticaAuxiliar();
					return new ExpresionAritmetica(eA, eAux);
				}
			}
		}

		ValorNumerico vl = esValorNumerico();
		if (vl != null) {
			System.out.println("qqqq");
			obtenerSiguienteToken();
			ExpresionAritmeticaAuxiliar eAux = esExpresionAritmeticaAuxiliar();
			return new ExpresionAritmetica(vl, eAux);
		}

		return null;
	}

	/**
	 * <ValorNumerico>::=[<signo>] entero | [<signo>] real | identificar
	 */
	private ValorNumerico esValorNumerico() {

		Token Signo = null;

		if (tokenActual.getLexema().equals("+") || tokenActual.getLexema().equals("-")) {

			Signo = tokenActual;
			obtenerSiguienteToken();

			if (tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.REAL) {
				Token Valor = tokenActual;
				return new ValorNumerico(Signo, Valor);
			} else {
				reportarError("falta el valor numerico");
			}

		} else {

			if (tokenActual.getCategoria() == Categoria.ENTERO || tokenActual.getCategoria() == Categoria.REAL) {
				Token Valor = tokenActual;
				return new ValorNumerico(Signo, Valor);
			} else {
				if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
					Token Valor = tokenActual;
					return new ValorNumerico(Signo, Valor);
				}
			}
		}
		return null;

	}

	/**
	 * <expresionAritmeticaAuxiliar>::=operadorAritmetico<ExpresionAritmetica>[<expresionAritmeticaAuxiliar>]
	 */
	private ExpresionAritmeticaAuxiliar esExpresionAritmeticaAuxiliar() {

		if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
			Token operador = tokenActual;
			obtenerSiguienteToken();
			ExpresionAritmetica eA = esExpresionAritmetica();
			if (eA != null) {
				ExpresionAritmeticaAuxiliar eAux = esExpresionAritmeticaAuxiliar();
				return new ExpresionAritmeticaAuxiliar(operador, eA, eAux);
			} else {
				reportarError("Falta un termino de la expresion aritmetica");

			}
		}

		return null;
	}

	/**
	 * <esRetorno>::= retornar <es Expresion> ";"
	 * 
	 * @return
	 */
	private Retorno esRetorno() {

		if (tokenActual.getCategoria() == Categoria.PALABRA_RESERVADA && tokenActual.getLexema().equals("retornar")) {

			Token palabraReservada = tokenActual;
			obtenerSiguienteToken();
			Expresion expresion = esExpresion();
			if (expresion != null) {
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

	/**
	 * <esCondicion>::= si "(" <esExoresuinLogica> ")" "{" [<esListaSentencias>] "}"
	 * 
	 * @return
	 */
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

	/**
	 * <esExpresionLogica>::= "!" <esExpresionRelacional> | <esExpresionRelacional>
	 * <OperadorLogico> <esExpresionRelacional>
	 * 
	 * @return
	 */
	private ExpresionLogica esExpresionLogica() {

		if (tokenActual.getCategoria() == Categoria.OPERADOR_LOGICO && tokenActual.getLexema().equals("!")) {

			Token operador = tokenActual;
			obtenerSiguienteToken();

			ExpresionRelacional er = esExpresionRelacional();

			if (er != null) {

				return new ExpresionLogica(operador, er);

			} else {
				reportarError("falta expresion relacional");
			}
		} else {

			ExpresionRelacional er = esExpresionRelacional();

			if (er != null) {

				if (tokenActual.getCategoria() == Categoria.OPERADOR_LOGICO && !tokenActual.getLexema().equals("!")) {

					Token operador = tokenActual;

					obtenerSiguienteToken();

					ExpresionRelacional er1 = esExpresionRelacional();

					if (er1 != null) {

						return new ExpresionLogica(er, er1, operador);

					} else {
						reportarError("falta la expresion relacional");
					}

				} else {
					reportarError("falta el operador logico");
				}
			}
		}

		return null;
	}

	/**
	 * <esAdignacion>::= identificador <operadorAsignacion> <esTermino> ";"
	 * 
	 * @return
	 */
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

	/**
	 * <esTermino>::= numeroEntero|identificador|cadenaCaracteres
	 * 
	 * @return
	 */
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

	/**
	 * Metodo para agregar un error a la lista de errores sintacticos
	 * 
	 * @param mensaje
	 */
	public void reportarError(String mensaje) {
		listaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
	}

	/**
	 * Mrtodo que me deja acceder a los datos de la unidad de compílacion
	 * 
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

	/**
	 * @return the listaTokens
	 */
	public ArrayList<Token> getListaTokens() {
		return listaTokens;
	}

	/**
	 * @param listaTokens the listaTokens to set
	 */
	public void setListaTokens(ArrayList<Token> listaTokens) {
		this.listaTokens = listaTokens;
	}

	/**
	 * @return the tokenActual
	 */
	public Token getTokenActual() {
		return tokenActual;
	}

	/**
	 * @param tokenActual the tokenActual to set
	 */
	public void setTokenActual(Token tokenActual) {
		this.tokenActual = tokenActual;
	}

	/**
	 * @return the posicionActual
	 */
	public int getPosicionActual() {
		return posicionActual;
	}

	/**
	 * @param posicionActual the posicionActual to set
	 */
	public void setPosicionActual(int posicionActual) {
		this.posicionActual = posicionActual;
	}

	/**
	 * @return the listaErrores
	 */
	public ArrayList<ErrorSintactico> getListaErrores() {
		return listaErrores;
	}

	/**
	 * @param listaErrores the listaErrores to set
	 */
	public void setListaErrores(ArrayList<ErrorSintactico> listaErrores) {
		this.listaErrores = listaErrores;
	}

}
