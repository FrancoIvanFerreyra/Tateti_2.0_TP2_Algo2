package tateti;

import cartas.Carta;
import estructuras.Cola;
import estructuras.Lista;
import estructuras.Vector;
import estructuras.Pila;
import exportadores.ExportadorDeDatosAImagen;
import jugadas.Jugada;

import utiles.Utiles;

import interfaz.Consola;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import utiles.AdministradorDeArchivos;

import utiles.ValidacionesUtiles;

public class Tateti {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private Tablero<Ficha> tablero = null;
	private Mazo mazoDeCartas = null;
	private Vector<Jugador> jugadores = null;
	private Vector<Color> coloresDeFicha = null;
	private Vector<Turno> turnos = null;
	private Pila<Turno> historialTurnos = null;
	private int cantidadDeFichasSeguidasParaGanar;
	
	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * Crea una partida de tateti en base a una configuracion de partida preexistente
	 * @param configuracion no puede ser null
	 * @throws NullPointerException si configuracion es null
	 */
	public Tateti(ConfiguracionPartida configuracion) throws NullPointerException
	{
		this(ConfiguracionPartida.validarConfiguracion(configuracion).getAnchoTablero(),
			configuracion.getAltoTablero(),
			configuracion.getProfundidadTablero(),
			configuracion.getCantidadDeFichasSeguidasParaGanar(),
			configuracion.getCantidadDeJugadores(),
			configuracion.getCantidadDeFichasPorJugador(),
			configuracion.getCantidadDeCartasPorJugador()
			);
	}

	/**
	 * Crea una partida de tateti con las reglas proporcionadas (dimensiones del tablero,
	 * cantidad de jugadores,cantidad de fichas y cartas de los jugadores, y la longitud del
	 * tateti)
	 * @param anchoTablero mayor o igual que 3
	 * @param altoTablero mayor o igual que 3
	 * @param profundidadTablero mayor o igual que 1
	 * @param cantidadDeFichasSeguidasParaGanar debe estar en el rango [3, longitudMaximaTateti],
	 * siendo longitudMaximaTateti -> max(ancho, alto, profundidad)
	 * @param cantidadJugadores debe estar en el rango [2, cantidadMaximaDeJugadoresParaElTablero],
	 * siendo cantidadMaximaDeJugadoresParaElTablero -> 
	 * (dimension * dimension * (dimensionMenor - 1)) / cantidadDeFichasSeguidasParaGanar,
	 * donde dimensionMenor es la menor de las dimensiones proporcionadas.
	 * @param cantidadDeFichasPorJugador debe estar en el rango 
	 * 			[3, cantidadMaximaDeFIchasPorJugadorEnElTablero],
	 * siendo cantidadMaximaDeFichasPorJugadorDelTablero ->
	 * (dimension * dimension * (dimensionMenor - 1)) / cantidadJugadores,
 	 * donde dimensionMenor es la menor de las dimensiones proporcionadas.
	 * @param cantidadDeCartasPorJugador mayor o igual que 2
	 * @throws IllegalArgumentException si algun argumento esta fuera de su rango
	 */
	public Tateti(int anchoTablero, int altoTablero, int profundidadTablero,
					int cantidadDeFichasSeguidasParaGanar, int cantidadJugadores,
					int cantidadDeFichasPorJugador, int cantidadDeCartasPorJugador
					) throws IllegalArgumentException {
		
		ValidacionesUtiles.validarEnteroMinimo(anchoTablero, 3, "anchoTablero");
		ValidacionesUtiles.validarEnteroMinimo(altoTablero, 3, "altoTablero");
		ValidacionesUtiles.validarEnteroMinimo(profundidadTablero, 1, "profundidadTablero");
		
		int longitudMaximaTateti = ConfiguracionPartida.obtenerLongitudMaximaDeTateti(
									anchoTablero, altoTablero, profundidadTablero);
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeFichasSeguidasParaGanar, 3,
		longitudMaximaTateti,   "cantidadDeFichasSeguidasParaGanar");
		
		int cantidadMaximaDeJugadoresParaElTablero = ConfiguracionPartida.
							obtenerCantidadMaximaDeJugadoresParaElTablero(
		anchoTablero, altoTablero, profundidadTablero, cantidadDeFichasSeguidasParaGanar);

		ValidacionesUtiles.validarEnteroEnRango(cantidadJugadores, 2,
										cantidadMaximaDeJugadoresParaElTablero,
												"cantidadJugadores");
		
		int cantidadMaximaDeFichasPorJugadorEnElTablero = ConfiguracionPartida.
							obtenerCantidadMaximaDeFichasPorJugadorEnElTablero(
							anchoTablero, altoTablero, profundidadTablero,
							cantidadDeFichasSeguidasParaGanar, cantidadJugadores);
		
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeFichasPorJugador, 3,
										cantidadMaximaDeFichasPorJugadorEnElTablero,
												"cantidadDeFichasPorJugador");
		
		ValidacionesUtiles.validarEnteroMinimo(cantidadDeCartasPorJugador, 1,
		"cantidadCartasPorJugador");
				
		this.tablero = new Tablero<>(anchoTablero, altoTablero, profundidadTablero);
		this.jugadores = new Vector<>(cantidadJugadores, null);
		this.coloresDeFicha = new Vector<>(cantidadJugadores, Color.black);
		this.mazoDeCartas = new Mazo((cantidadDeCartasPorJugador + 2) * cantidadJugadores);
		this.cantidadDeFichasSeguidasParaGanar = cantidadDeFichasSeguidasParaGanar;
		for(int i = 1; i <= this.jugadores.getLongitud(); i++)
		{
			String titulo = "Jugador " + i + ", por favor ingrese su nombre:";
			String nombreDelJugador = Consola.obtenerStringDelUsuario(titulo);
			while(!ValidacionesUtiles.esNombreValido(nombreDelJugador))
			{
				nombreDelJugador = Consola.obtenerStringDelUsuario("Nombre inválido!\n" + titulo);
			}
			Color colorDelJugador = Utiles.generarColorAleatorio();
			while(!Utiles.esColorDistinto(colorDelJugador, this.coloresDeFicha))
			{
				colorDelJugador = Utiles.generarColorAleatorio();
			}
			this.coloresDeFicha.agregar(colorDelJugador);
			this.jugadores.agregar(new Jugador(nombreDelJugador, cantidadDeFichasPorJugador,
												 colorDelJugador, cantidadDeCartasPorJugador));
		}
		this.turnos = new Vector<>(this.jugadores.getLongitud(), null);
		for(int i = 0; i < this.turnos.getLongitud(); i++)
		{
			this.turnos.agregar(new Turno(this.jugadores.obtener(i + 1)));
		}
		this.historialTurnos = new Pila<>();
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------


	/**
	 * 
	 * @param jugador no puede ser null
	 * @return devuelve el turno de la partida que pertenece al jugador
	 * @throws NullPointerException si jugador es null
	 * @throws NoSuchElementException si no existe turno asociado al jugador
	 */
	public Turno obtenerTurno(Jugador jugador) throws NullPointerException, NoSuchElementException
	{
		ValidacionesUtiles.validarNoNull(jugador,"jugador");
		boolean encontrado = false;
		int i = 1;
		while(!encontrado && i <= turnos.getLongitud())
		{
			Turno turno = turnos.obtener(i);
			if(turno.getJugador() == jugador)
			{
				return turno; 
			}
			i++;
		}
		throw new NoSuchElementException("No existe el turno de este jugador");
	}

	/**
	 * 
	 * @param casillero no puede ser null y debe contener una ficha
	 * @return devuelve verdadero si se completo la cantidad de fichas seguidas
	 * 		para ganar, a partir de casillero, y en cualquier direccion
	 * @throws NullPointerException si casillero es null
	 * @throws IllegalArgumentException si casillero no tiene una ficha
	 */
	public boolean existeGanador(Casillero<Ficha> casillero) throws NullPointerException,
															IllegalArgumentException {
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		if(!casillero.estaOcupado())
		{
			throw new IllegalArgumentException("El casillero esta vacio");
		}
		Lista<Movimiento> movimientosAChequear = Utiles.obtenerMovimientosAChequear();
		movimientosAChequear.iniciarCursor();

		while(movimientosAChequear.avanzarCursor())
		{
			Movimiento movimiento = movimientosAChequear.obtenerCursor();                                                                //casillero de la derecha
			int cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, movimiento, casillero.getDato()) + 
			contarFichasSeguidas(casillero, Utiles.movimientoOpuesto(movimiento), casillero.getDato());
			if(cantidadDeFichasSeguidas - 1 >= this.cantidadDeFichasSeguidasParaGanar)
			{
				//hay ganador
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param casillero
	 * @param movimiento no puede ser null
	 * @param ficha no puede ser null
	 * @return devuelve la cantidad de fichas seguidas del mismo simbolo que hay desde casillero
	 * 		   hacia movimiento
	 * @throws NullPointerException si movimiento o ficha son null
	 */
	public int contarFichasSeguidas(Casillero<Ficha> casillero, Movimiento movimiento, Ficha ficha)
															throws NullPointerException {
		
		ValidacionesUtiles.validarNoNull(movimiento, "movimiento");
		ValidacionesUtiles.validarNoNull(ficha, "ficha");

		if(casillero == null) {
			return 0;
		}
		if(!casillero.estaOcupado())
		{
			return 0;
		}
		if(!casillero.getDato().esElMismoSimbolo(ficha)) {
			return 0;
		}
		return 1 + contarFichasSeguidas(casillero.getCasilleroVecino(movimiento), movimiento, ficha);
	}

	
	/**
	 * Inicia la partida de tateti. Finaliza cuando hay un ganador
	 * @throws Exception
	 */
	public void jugar() {

		Cola<Turno> filaDeTurnos = new Cola<>();
		filaDeTurnos.acolar(turnos);
		Turno turnoActual = null;
		boolean existeGanador = false;
		int numeroDeTurno = 1;
		while(!existeGanador)
		{
			turnoActual = filaDeTurnos.desacolar();
			Consola.imprimirMensaje("Turno de jugar para " + turnoActual.getJugador().getNombre());
			Utiles.pausarEjecucion(2000);
			turnoActual.limpiar();
			Casillero<Ficha> casilleroDestino = null;

			if (!turnoActual.estaBloqueado()) {
				turnoActual.iniciarTurno();
				
				Consola.imprimirMensaje("Tirando el dado...");
				Utiles.pausarEjecucion(2000);
				int cantidadDeCartasASacar = tirarDado();
				Consola.imprimirMensaje("Salio el numero " + cantidadDeCartasASacar + "!");
				Utiles.pausarEjecucion(1500);
				Consola.imprimirMensaje("-----------------------------CARTAS-------------------------------");
				for(int i = 0; i < cantidadDeCartasASacar; i++)
				{
					Carta nuevaCarta;
					try{
						Consola.imprimirMensaje("Levantando del mazo la carta " + (i + 1) + " de " + cantidadDeCartasASacar + "...");
						Utiles.pausarEjecucion(1500);
						nuevaCarta = mazoDeCartas.levantarCarta();
					}
					catch(Exception e)
					{
						try
						{
							Consola.imprimirMensaje("No hay cartas que levantar. Mezclando las ya usadas...");
							Utiles.pausarEjecucion(1500);
							mazoDeCartas.mezclar();
						}
						catch(Exception ex)
						{
							Consola.imprimirMensaje("No hay cartas que mezclar. Todas estan en juego!");
							Utiles.pausarEjecucion(1500);
							break;
						}
						Consola.imprimirMensaje("Cartas mezcladas!");
						Utiles.pausarEjecucion(1500);
						Consola.imprimirMensaje("Levantando del mazo la carta " + (i + 1) + " de " + cantidadDeCartasASacar + "...");
						Utiles.pausarEjecucion(1500);
						nuevaCarta = mazoDeCartas.levantarCarta();
					}
					try {
						Consola.imprimirMensaje("Guardando la carta...");
						Utiles.pausarEjecucion(1500);
						turnoActual.getJugador().agregarCarta(nuevaCarta);
					} catch (IllegalArgumentException e) {
						Consola.imprimirMensaje("No podes tener mas cartas. Devolviendo la carta levantada al mazo...");
						Utiles.pausarEjecucion(1500);
						mazoDeCartas.devolverCarta(nuevaCarta);
						Consola.imprimirMensaje("------------------------------------------------------------------");
						continue;
					}
					Consola.imprimirMensaje("Se guardo la carta " + nuevaCarta.getTitulo());
					Utiles.pausarEjecucion(1500);
					Consola.imprimirMensaje("------------------------------------------------------------------");

				}

				while (turnoActual.haySubturnos()) {
					turnoActual.utilizarSubturno();
					if (!turnoActual.getJugador().tieneTodasLasFichasEnElTablero(this.getTablero())) {
						casilleroDestino = jugadaInicial(this.tablero, turnoActual);
					} else {
						casilleroDestino = mover(this.tablero, turnoActual);
					}
					
					Utiles.pausarEjecucion(1500);

					boolean cartaJugada = false;
					while(!cartaJugada)
					{
						if(Consola.obtenerConfirmacionDelUsuario("Desea jugar una carta?", false))
						{
							//Si juega una carta
							while(!cartaJugada)
							{
								Vector<Carta> cartas = turnoActual.getJugador().getCartas().filtrar(
									carta ->
									{
										return carta != null;
									}
								);
								Carta cartaActual = Consola.consultarOpcionAlUsuario(
									cartas,
							"Seleccione una carta para jugar",
				true); //preguntar la carta del jugador
								if(cartaActual == null)
								{
									break;
								}

								Jugada jugada = cartaActual.getJugada();
								if(!jugada.jugar(this, turnoActual))
								{
									continue;
								}	
								turnoActual.setJugadaEjecutada(jugada);
								cartaJugada = true;	
								turnoActual.getJugador().quitarCarta(cartaActual);
								mazoDeCartas.descartarCarta(cartaActual);
								Utiles.pausarEjecucion(2000);		
							}						
						}
						else
						{
							cartaJugada = true;
						}
					}
					

				}
				Consola.imprimirMensaje("Finalizo tu turno!");
				Utiles.pausarEjecucion(2000);
				turnoActual.terminarTurno();
				reducirBloqueos();
				historialTurnos.apilar(turnoActual);
				existeGanador = existeGanador(casilleroDestino);
			}
			else
			{
				Consola.imprimirMensaje("Estas bloqueado por este turno :(");
				Utiles.pausarEjecucion(1500);
				Consola.imprimirMensaje("Finalizo tu turno!");
				Utiles.pausarEjecucion(2000);
				turnoActual.terminarTurno();
				reducirBloqueos();
			}
			
			filaDeTurnos.acolar(turnoActual);
			exportarEstadoDelTablero(tablero, "./src/estadosTablero/", numeroDeTurno);
			numeroDeTurno++;
		}
		if(turnoActual != null)
		{
			Consola.imprimirMensaje("Hay ganador! Felicidades " + turnoActual.getJugador().getNombre() + " :)");
		}

	}

	/**
	 * Tira un dado de 6 caras numeradas del 1 al 6
	 * @return devuelve el numero obtenido del dado
	 */
	public int tirarDado()
	{
		return Utiles.obtenerEnteroAleatorio(1, 6);
	}
	
	
	/**
	 * Crea una ficha para el jugador, y la coloca en el casillero donde este desee
	 * @param tablero no debe ser null
	 * @param turnoActual no debe ser null
	 * @return devuelve el casillero donde el jugador coloco su ficha
	 * @throws NullPointerException si tablero o turnoActual son null
	 */
	public Casillero<Ficha> jugadaInicial(Tablero<Ficha> tablero, Turno turnoActual) 
																throws NullPointerException {
		Jugador jugador = turnoActual.getJugador();
		Ficha ficha = new Ficha(String.valueOf(jugador.getId()));
		tablero.actualizarRelacionDatoColor(ficha, jugador.getColor());
		Casillero<Ficha> casillero = obtenerCasilleroDirectoDelUsuario(
									"Se necesitan los datos del casillero destino de la ficha",
									false);
		casillero.setDato(ficha);
		jugador.agregarFicha(ficha);
		tablero.actualizarRelacionDatoCasillero(ficha, casillero);
		turnoActual.setFichaUtilizada(ficha);
		Consola.imprimirMensaje("Ficha colocada correctamente en " + casillero.toString() + "!");
		return casillero;
	}

	/**
	 * Consulta al usuario la ficha que quiere mover y en que direccion, para luego mover la ficha
	 * @param tablero no debe ser null
	 * @param turnoActual no debe ser null
	 * @return devuelve el casillero hacia donde el jugador movio su ficha
	 * @throws NullPointerException si tablero o turnoActual son null
	 */
	public Casillero<Ficha> mover(Tablero<Ficha> tablero, 
									Turno turnoActual) throws NullPointerException {
		Jugador jugador = turnoActual.getJugador();
		Ficha fichaAMover = null;
		Movimiento movimiento = null;
		while(movimiento == null)
		{
			Vector<Ficha> fichas = jugador.getFichas().filtrar(ficha -> 
			{
				return ficha != null &&
					  !ficha.estaBloqueado() &&
					  tablero.contiene(ficha) &&
					  tablero.tieneMovimientosPosibles(ficha);
			});
			Lista<Casillero<Ficha>> casillerosDeFichas = getTablero().getCasilleros(fichas);
			try {
				Casillero<Ficha> casilleroFichaAMover = Consola.consultarOpcionAlUsuario(
					casillerosDeFichas, "Seleccione una ficha para mover", false);
				fichaAMover = casilleroFichaAMover.getDato();
			} catch (IllegalArgumentException e) {
				Consola.imprimirMensaje("No puede mover ninguna de sus fichas");
				return getTablero().getCasillero(1,1,1);
			}
			movimiento = Consola.consultarOpcionAlUsuario(tablero.obtenerMovimientosPosibles(fichaAMover),
									 "A que direccion desea mover la ficha?", true);
		}

		return mover(tablero, turnoActual, fichaAMover, movimiento);
	}

	/**
	 * Mueve la ficha hacia el casillero ubicado en direccion de movimiento.
	 * Dicho casillero debe existir y no debe estar ni ocupado ni bloqueado
	 * @param tablero no debe ser null
	 * @param turnoActual no debe ser null
	 * @param ficha no debe ser null
	 * @param movimiento no debe ser null
	 * @return devuelve el casillero destino de la ficha
	 * @throws NullPointerException si algun parametro es null
	 * @throws NoSuchElementException si el casillero destino no existe
	 * @throws IllegalArgumentException si el casillero esta ocupado o bloqueado
	 */
	public Casillero<Ficha> mover(Tablero<Ficha> tablero, Turno turnoActual, 
									Ficha ficha, Movimiento movimiento) 
									throws NullPointerException,
									NoSuchElementException,
									IllegalArgumentException {

		Casillero<Ficha> casillero = tablero.getCasillero(ficha);
		if (!casillero.existeElVecino(movimiento)) {
		throw new NoSuchElementException("No existe el casillero en ese movimiento");
		}
		if (casillero.getCasilleroVecino(movimiento).estaOcupado()) {
		throw new IllegalArgumentException("El casillero destino esta ocupado");
		}
		if (casillero.getCasilleroVecino(movimiento).estaBloqueado()) {
			throw new IllegalArgumentException("El casillero destino esta bloqueado");
			}
		tablero.mover(casillero, casillero.getCasilleroVecino(movimiento));

		turnoActual.setFichaUtilizada(ficha);
		turnoActual.setMovimientoAplicado(movimiento);

		Consola.imprimirMensaje("Ficha movida correctamente a " +
								 casillero.getCasilleroVecino(movimiento).toString() + "!");

		return casillero.getCasilleroVecino(movimiento);
	}

	/**
	 * Quita cantidadDeTurnos turnos de la pila historialTurnos, y deshace todas las 
	 * acciones ejecutadas en cada uno
	 * @param cantidadDeTurnos debe estar en el rango [1, getHistorialTurnos.contarElementos()]
	 * @throws IllegalArgumentException si cantidadDeTurnos esta fuera de rango
	 */
	public void retrocederTurnos(int cantidadDeTurnos) throws IllegalArgumentException
	{
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeTurnos, 1,
								 this.historialTurnos.contarElementos(), "cantidadDeTurnos");
		while(cantidadDeTurnos > 0)
		{
			Turno turno = getHistorialTurnos().desapilar();
			if(turno.getJugadaEjecutada() != null)
			{
				turno.getJugadaEjecutada().deshacer(this);
			}

			if(turno.getMovimientoAplicado() == null)
			{
				turno.getJugador().quitarFicha(turno.getFichaUtilizada());
				Casillero<Ficha> casillero = getTablero().getCasillero(turno.getFichaUtilizada());
				casillero.vaciar();
				getTablero().eliminarRelacionDatoCasillero(turno.getFichaUtilizada());
				getTablero().eliminarRelacionDatoColor(turno.getFichaUtilizada());
			}
			else
			{
				mover(getTablero(), turno, turno.getFichaUtilizada(),
						Utiles.movimientoOpuesto(turno.getMovimientoAplicado()));
			}

			cantidadDeTurnos--;
		}
	}

	/**
	 * Exporta el estado actual del tablero a un conjunto de imagenes BMP, donde en cada imagen
	 * se representa una capa 2D del tablero simplificado a filas y columnas con datos relevantes,
	 * una imagen por cada capa de profundidad relevante. En caso de ser el primer turno del juego,
	 * limpia el directorio base para representar la nueva partida
	 * @param <T>
	 * @param tablero no puede ser null
	 * @param directorioBase no puede ser null, debe ser una ruta relativa valida
	 * @param numeroDeTurno mayor o igual a 1
	 * @throws NullPointerException si tablero o directorioBase son null
	 * @throws IllegalArgumentException si directorioBase no es una ruta relativa valida
	 */
	public <T> void exportarEstadoDelTablero(Tablero<T> tablero, String directorioBase,
											 int numeroDeTurno)
											  throws NullPointerException,
											  IllegalArgumentException
	{
		ValidacionesUtiles.validarNoNull(tablero, "tablero");
		ValidacionesUtiles.validarRutaRelativa(directorioBase);
		ValidacionesUtiles.validarEnteroMinimo(numeroDeTurno, 1, "numeroDeTurno");

		File directorio = AdministradorDeArchivos.crearDirectorio("./", directorioBase);
		if(numeroDeTurno == 1)
		{
			try {
				AdministradorDeArchivos.vaciarDirectorio(directorio);
			} catch (IOException e) {
				Consola.imprimirMensaje("Error: No se pudo vaciar el directorio de estados del tablero");
			}
		}
		File directorioTurno = AdministradorDeArchivos.crearDirectorio(directorio, "turno" + numeroDeTurno);
        ExportadorDeDatosAImagen.exportarTableroPorCapas(tablero, directorioTurno.getPath());
	}

	/**
	 * 
	 * @param mensaje no puede ser null
	 * @param tieneOpcionParaVolver indica si el usuario puede o no volver al menu anterior
	 * @return Devuelve el casillero del tablero a partir de las coordenadas ingresadas por
	 * 		   el usuario. Devuelve null si tieneOpcionParaVolver es verdadero y el usuario
	 * 		   eligio volver al menu anterior
	 * @throws NullPointerException si mensaje es null
	 */
	public Casillero<Ficha> obtenerCasilleroDirectoDelUsuario(String mensaje,
														 boolean tieneOpcionParaVolver) 
														throws NullPointerException
	{
		ValidacionesUtiles.validarNoNull(mensaje, "mensaje");
		Consola.imprimirMensaje(mensaje);
		Casillero<Ficha> casillero = null;
		int x, y, z;
		do
		{
			x = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada x de la nueva ficha (entre 1 y " + tablero.getAncho() + "):", 1, tablero.getAncho()); //pregunta la posicion
			y = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada y de la nueva ficha: (entre 1 y " + tablero.getAlto() + "):", 1, tablero.getAlto());
			z = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada z de la nueva ficha: (entre 1 y " + tablero.getProfundidad() + "):", 1, tablero.getProfundidad());
			
			Consola.imprimirMensaje("Casillero elegido es (" + x + ", " + y + ", " + z + ")");
			Boolean usuarioConfirmoIngreso = Consola.obtenerConfirmacionDelUsuario(
												"Confirmar casillero?", tieneOpcionParaVolver);		
			
			if(usuarioConfirmoIngreso == null)
			{
				return null;
			}
			if(usuarioConfirmoIngreso)
			{
				if(tablero.existeElCasillero(x, y, z))
				{
					casillero = tablero.getCasillero(x, y, z);
					if(casillero.estaBloqueado())
					{
						Consola.imprimirMensaje("El casillero (" + x + ", " + y + ", " + z + ") esta bloqueado!");
					}
					if(casillero.estaOcupado())
					{
						Consola.imprimirMensaje("El casillero (" + x + ", " + y + ", " + z + ") ya esta ocupado por otra ficha!");
					}
				}
				else
				{
					Consola.imprimirMensaje("El casillero (" + x + ", " + y + ", " + z + ") no existe!");
					casillero = null;
				}
			}

		} while (casillero == null || casillero.estaOcupado() || casillero.estaBloqueado());
		return casillero;
	}
	/**
	 * 
	 */
	public void reducirBloqueos() {
		Lista<Casillero<Ficha>> casillerosBloqueados = this.tablero.getCasillerosBloqueados();
		
		// Verificar si no hay casilleros bloqueados
		if (casillerosBloqueados.getTamanio() == 0) {
			Consola.imprimirMensaje("No hay casilleros para desbloquear.");
			return;  // Salir del método si no hay casilleros bloqueados
		}
	
		try {
			// Iniciar el cursor para recorrer los casilleros bloqueados
			casillerosBloqueados.iniciarCursor();
			
			boolean bloqueoReducido = false;  // Para verificar si se redujeron bloqueos
			
			// Recorrer todos los casilleros bloqueados
			while (casillerosBloqueados.avanzarCursor()) {
				Casillero<Ficha> casilleroBloqueado = casillerosBloqueados.obtenerCursor();
	
				// Verificar si la ficha está bloqueada antes de reducir los bloqueos
				if (casilleroBloqueado.estaBloqueado()) {
					casilleroBloqueado.reducirBloqueosRestantes(1);
					bloqueoReducido = true;
					Consola.imprimirMensaje("Se redujo un bloqueo en la ficha ubicada en " +
							casilleroBloqueado.toString());
				}
			}
	
			// Mensaje de éxito si se redujeron bloqueos
			if (bloqueoReducido) {
				Consola.imprimirMensaje("Los bloqueos se redujeron exitosamente.");
			} else {
				Consola.imprimirMensaje("No se encontraron fichas bloqueadas para reducir.");
			}
	
		} catch (Exception e) {
			// Capturar y manejar excepciones específicas si es necesario
			Consola.imprimirMensaje("No se pudo reducir los bloqueos. Error: " + e.getMessage());
		}
	}

	
	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @return devuelve el tablero del tateti
	 */
	public Tablero<Ficha> getTablero() {
		return tablero;
	}

	/**
	 * 
	 * @return devuelve los jugadores del tateti
	 */
	public Vector<Jugador> getJugadores() {
		return jugadores;
	}

	/**
	 * 
	 * @return devuelve el historial de turnos del tateti
	 */
	public Pila<Turno> getHistorialTurnos()
	{
		return this.historialTurnos;
	}
	/**
	 * 
	 * @return devuelve los turnos de los jugadores del tateti
	 */
	public Vector<Turno> getTurnos(){
		return this.turnos;
	}
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}