package tateti;

import cartas.Carta;
import estructuras.Cola;
import estructuras.Vector;
import estructuras.Pila;
import exportadores.ExportadorDeDatosAImagen;
import jugadas.Jugada;
import utiles.Utiles;

import interfaz.Consola;
import java.awt.Color;
import javax.swing.tree.ExpandVetoException;

import utiles.ValidacionesUtiles;

public class Tateti {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private Tablero<Ficha> tablero = null;
	private Vector<Jugador> jugadores = null;
	private Vector<Color> coloresDeFicha = null;
	private Vector<Turno> turnos = null;
	private Pila<Turno> historialTurnos = null;
	private int cantidadDeFichasPorJugador;
	
	//demas cosas, jugadores, cartas, etc

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Tateti() throws Exception {
		this(3, 3, 2, 3);
	}

	public Tateti(int anchoTablero, int altoTablero, int cantidadJugadores,
					 int cantidadDeFichasPorJugador) throws Exception {
		this.tablero = new Tablero<Ficha>(anchoTablero, altoTablero);
		this.jugadores = new Vector<Jugador>(cantidadJugadores, null);
		this.coloresDeFicha = new Vector<Color>(cantidadJugadores, Color.black);
		for(int i = 1; i <= this.jugadores.getLongitud(); i++)
		{
			String titulo = "Jugador " + i + ", por favor ingrese su nombre:";
			String nombreDelJugador = Consola.obtenerStringDelUsuario(titulo);
			while(!ValidacionesUtiles.esNombreValido(nombreDelJugador))
			{
				nombreDelJugador = Consola.obtenerStringDelUsuario("Nombre inválido!\n" + titulo);
			}
			Color colorDelJugador = Utiles.generarColorAleatorio();
			while(coloresDeFicha.contiene(colorDelJugador))
			{
				colorDelJugador = Utiles.generarColorAleatorio();
			}
			this.jugadores.agregar(new Jugador(nombreDelJugador, cantidadDeFichasPorJugador, colorDelJugador));
		}
		this.turnos = new Vector<Turno>(this.jugadores.getLongitud(), null);
		for(int i = 0; i < this.turnos.getLongitud(); i++)
		{
			System.out.println("Turno agregado");
			this.turnos.agregar(new Turno(this.jugadores.obtener(i + 1)));
		}
		this.historialTurnos = new Pila<Turno>();
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------


	public Turno obtenerTurno(Jugador jugador) throws Exception
	{
		if(jugador == null)
		{
			throw new Exception("El jugador no puede ser null");
		}
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
		return null;
	}
	public boolean existeGanador(Casillero<Ficha> casillero) throws Exception {
		//validar
		int cantidadFichas = 3; //longitud del tateti


		//suponemos direccion horizontal                           // casillero de la izquierda                                                                //casillero de la derecha
		int cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, Movimiento.ARRIBA, casillero.getDato()) + 
										contarFichasSeguidas(casillero, Movimiento.ABAJO, casillero.getDato());
		if(cantidadDeFichasSeguidas - 1 >= cantidadFichas) {
			//hay ganador
			return true;
		}

		cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, Movimiento.IZQUIERDA, casillero.getDato()) + 
									contarFichasSeguidas(casillero, Movimiento.DERECHA, casillero.getDato());
		if(cantidadDeFichasSeguidas - 1 >= cantidadFichas) {
			//hay ganador
			return true;
		}

		cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, Movimiento.IZQUIERDA_ARRIBA, casillero.getDato()) + 
									contarFichasSeguidas(casillero, Movimiento.DERECHA_ABAJO, casillero.getDato());
		if(cantidadDeFichasSeguidas - 1 >= cantidadFichas) {
			//hay ganador
			return true;
		}
		cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, Movimiento.IZQUIERDA_ABAJO, casillero.getDato()) + 
									contarFichasSeguidas(casillero, Movimiento.DERECHA_ARRIBA, casillero.getDato());
		if(cantidadDeFichasSeguidas - 1 >= cantidadFichas) {
			//	hay ganador
			return true;
		}
		return false;
	}

	public int contarFichasSeguidas(Casillero<Ficha> casillero, Movimiento movimiento, Ficha ficha) throws Exception {
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

	
	public void jugar() throws Exception {

		Cola<Turno> filaDeTurnos = new Cola<Turno>();
		filaDeTurnos.acolar(turnos);
		Turno turnoActual = null;
		boolean existeGanador = false;
		while(!existeGanador)
		{
			tablero.mostrar();
			//while x turno
			//Levantar la carta
			
			turnoActual = filaDeTurnos.desacolar();
			Consola.imprimirMensaje("Turno de jugar para " + turnoActual.getJugador().getNombre());
			turnoActual.limpiar();
			Casillero<Ficha> casilleroDestino = null;

			if (!turnoActual.estaBloqueado()) {
				turnoActual.iniciarTurno();
				while (turnoActual.haySubturnos()) {
					turnoActual.utilizarSubturno();
					if (!turnoActual.getJugador().tieneTodasLasFichasEnElTablero(this.tablero)) {
						casilleroDestino = jugadaInicial(this.tablero, turnoActual);
					} else {
						casilleroDestino = mover(this.tablero, turnoActual);
					}
					
					//Si juega una carta
					Carta cartaActual = null; //preguntar la carta del jugador
					if (cartaActual != null) {
						Jugada jugada = cartaActual.getJugada();	
						jugada.jugar(this, turnoActual);
						turnoActual.setJugadaEjecutada(jugada);
					}				
				}
				Consola.imprimirMensaje("Finalizo tu turno!");
				turnoActual.terminarTurno();
				historialTurnos.apilar(turnoActual);
				existeGanador = existeGanador(casilleroDestino);
			}
			else
			{
				Consola.imprimirMensaje("Estas bloqueado por este turno :(");
				Consola.imprimirMensaje("Finalizo tu turno!");
				turnoActual.terminarTurno();
			}
			filaDeTurnos.acolar(turnoActual);
		}
		if(turnoActual != null)
		{
			Consola.imprimirMensaje("Hay ganador! Felicidades " + turnoActual.getJugador().getNombre() + " :)");
		}

	}
	
	
	public Casillero<Ficha> jugadaInicial(Tablero<Ficha> tablero, Turno turnoActual) throws Exception {
		Jugador jugador = turnoActual.getJugador();
		Ficha ficha = new Ficha(jugador.getNombre().charAt(0));
		tablero.actualizarRelacionDatoColor(ficha, jugador.getColor()); //crea
		int x = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada x de la nueva ficha:"); //pregunta la posicion
		int y = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada y de la nueva ficha:");
		Casillero<Ficha> casillero = tablero.getCasillero(x, y);
		while (casillero.estaOcupado()) {
			Consola.imprimirMensaje("El casillero (" + x + ", " + y + ") ya esta ocupado por otra ficha!");
			x = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada x de la nueva ficha:"); //pregunta la posicion
			y = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada y de la nueva ficha:");
			casillero = tablero.getCasillero(x, y);
		}
		casillero.setDato(ficha);
		jugador.agregarFicha(ficha);
		tablero.actualizarRelacionDatoCasillero(ficha, casillero);
		turnoActual.setFichaUtilizada(ficha);
		return casillero;
	}

	public Casillero<Ficha> mover(Tablero<Ficha> tablero, 
									Turno turnoActual) throws Exception {
		Jugador jugador = turnoActual.getJugador();
		Ficha ficha = null;
		ficha = Consola.consultarOpcionAlUsuario(jugador.getFichas().filtrar(f -> 
											{
												try 
												{
													return tablero.tieneMovimientosPosibles(f);
												} 
												catch (Exception e) 
												{
													return false;
												}
											}),
			"Seleccione una ficha para mover"); //Preguntar la ficha al usuario
		


		Movimiento movimiento = Consola.consultarOpcionAlUsuario(
								tablero.obtenerMovimientosPosibles(ficha),
								 "A que direccion desea mover la ficha?"); //Pregunta al usuario
		return mover(tablero, turnoActual, ficha, movimiento);
	}

	public Casillero<Ficha> mover(Tablero<Ficha> tablero, Turno turnoActual, 
									Ficha ficha, Movimiento movimiento) throws Exception {

		Casillero<Ficha> casillero = tablero.getCasillero(ficha);
		if (!casillero.existeElVecino(movimiento)) {
		throw new Exception("No existe el movimiento en esa direccion");
		}
		if (casillero.getCasilleroVecino(movimiento).estaOcupado()) {
		throw new Exception("No existe el movimiento en esa direccion");
		}
		tablero.mover(casillero, casillero.getCasilleroVecino(movimiento), ficha);

		turnoActual.setFichaUtilizada(ficha);
		turnoActual.setMovimientoAplicado(movimiento);

		return casillero.getCasilleroVecino(movimiento);
	}

	public void retrocederTurnos(int cantidadDeTurnos) throws Exception
	{
		if(cantidadDeTurnos <= 0)
		{
			throw new Exception("La cantidad de turnos a retroceder debe ser mayor a 0");
		}

		if(getHistorialTurnos().contarElementos() < cantidadDeTurnos)
		{
			throw new Exception("No se pueden retroceder " + cantidadDeTurnos + 
									", el maximo es " + getHistorialTurnos().contarElementos());
		}
		while(cantidadDeTurnos > 0)
		{
			Turno turno = getHistorialTurnos().desapilar();
			if(turno.getJugadaEjecutada() != null)
			{
				turno.getJugadaEjecutada().deshacer(this);
			}

			if(turno.getMovimientoAplicado() == null)
			{
				//Ficha debe volver al jugador
			}
			else
			{
				mover(getTablero(), turno, turno.getFichaUtilizada(),
						Utiles.movimientoOpuesto(turno.getMovimientoAplicado()));
			}

			cantidadDeTurnos--;
		}
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public Tablero<Ficha> getTablero() {
		return tablero;
	}

	public Vector<Jugador> getJugadores() {
		return jugadores;
	}

	public Pila<Turno> getHistorialTurnos()
	{
		return this.historialTurnos;
	}
	
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}