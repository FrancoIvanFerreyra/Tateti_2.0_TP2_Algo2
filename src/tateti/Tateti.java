package tateti;

import cartas.Carta;
import cartas.CartaAnularCasillero;
import cartas.CartaBloquearFicha;
import estructuras.Cola;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;
import estructuras.Pila;
import exportadores.ExportadorDeDatosAImagen;
//import exportadores.ExportadorDeDatosAImagen;
import jugadas.Jugada;
import utiles.Utiles;

import interfaz.Consola;
import java.awt.Color;
import java.io.File;

import javax.swing.tree.ExpandVetoException;
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
	private int cantidadDeFichasPorJugador;
	private Pila<EstadoJuego> hitorialEstado = null;
	
	//demas cosas, jugadores, cartas, etc

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Tateti() throws Exception {
		this(5, 5,5,  2, 3, 5);
	}

	public Tateti(int anchoTablero, int altoTablero, int profundidadTablero, int cantidadJugadores,
					int cantidadDeFichasPorJugador, int cantidadDeCartasPorJugador) throws Exception {
		this.tablero = new Tablero<Ficha>(anchoTablero, altoTablero, profundidadTablero);
		this.jugadores = new Vector<Jugador>(cantidadJugadores, null);
		this.coloresDeFicha = new Vector<Color>(cantidadJugadores, Color.black);
		this.mazoDeCartas = new Mazo((cantidadDeCartasPorJugador + 2) * cantidadJugadores);
		this.hitorialEstado = new Pila<>();
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
			this.jugadores.agregar(new Jugador(nombreDelJugador, cantidadDeFichasPorJugador, colorDelJugador, cantidadDeCartasPorJugador));
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

		Lista<Movimiento> movimientosAChequear = Utiles.obtenerMovimientosAChequear();
		movimientosAChequear.iniciarCursor();

		while(movimientosAChequear.avanzarCursor())
		{
			Movimiento movimiento = movimientosAChequear.obtenerCursor();                                                                //casillero de la derecha
			int cantidadDeFichasSeguidas = contarFichasSeguidas(casillero, movimiento, casillero.getDato()) + 
			contarFichasSeguidas(casillero, Utiles.movimientoOpuesto(movimiento), casillero.getDato());
			if(cantidadDeFichasSeguidas - 1 >= cantidadFichas)
			{
				//hay ganador
				return true;
			}
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
		int numeroDeTurno = 1;
		while(!existeGanador)
		{
			//while x turno
			//Levantar la carta

			
			turnoActual = filaDeTurnos.desacolar();
			Consola.imprimirMensaje("Turno de jugar para " + turnoActual.getJugador().getNombre());
			turnoActual.limpiar();
			Casillero<Ficha> casilleroDestino = null;

			if (!turnoActual.estaBloqueado()) {
				turnoActual.iniciarTurno();
				
				int dado = 3;
				Consola.imprimirMensaje("-----------------------------CARTAS-------------------------------");
				for(int i = 0; i < dado; i++)
				{
					Carta nuevaCarta;
					try{
						Consola.imprimirMensaje("Levantando del mazo la carta " + (i + 1) + " de " + dado + "...");
						nuevaCarta = mazoDeCartas.levantarCarta();
					}
					catch(Exception e)
					{
						try
						{
							Consola.imprimirMensaje("No hay cartas que levantar. Mezclando las ya usadas...");
							mazoDeCartas.mezclar();
						}
						catch(Exception ex)
						{
							Consola.imprimirMensaje("No hay cartas que mezclar. Todas estan en juego!");
							break;
						}
						Consola.imprimirMensaje("Cartas mezcladas!");
						Consola.imprimirMensaje("Levantando del mazo la carta " + (i + 1) + " de " + dado + "...");
						nuevaCarta = mazoDeCartas.levantarCarta();
					}
					try {
						Consola.imprimirMensaje("Guardando la carta...");
						turnoActual.getJugador().agregarCarta(nuevaCarta);
					} catch (IllegalStateException e) {
						Consola.imprimirMensaje("No podes tener mas cartas. Devolviendo la carta levantada al mazo...");
						mazoDeCartas.devolverCarta(nuevaCarta);
						continue;
					}
					Consola.imprimirMensaje("Se guardo la carta " + nuevaCarta.getTitulo());
					Consola.imprimirMensaje("------------------------------------------------------------------");

				}

				while (turnoActual.haySubturnos()) {
					turnoActual.utilizarSubturno();
					if (!turnoActual.getJugador().tieneTodasLasFichasEnElTablero()) {
						casilleroDestino = jugadaInicial(this.tablero, turnoActual);
					} else {
						casilleroDestino = mover(this.tablero, turnoActual);
					}

					boolean cartaJugada = false;
					while(!cartaJugada)
					{
						if(Consola.obtenerConfirmacionDelUsuario("Desea jugar una carta?"))
						{
							//Si juega una carta
							while(!cartaJugada)
							{
	
								Carta cartaActual = Consola.consultarOpcionAlUsuario(
									turnoActual.getJugador().getCartas().filtrar(
										carta ->
										{
											return carta != null;
										}
									), "Seleccione una carta para jugar", true); //preguntar la carta del jugador
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
							}						
						}
						else
						{
							cartaJugada = true;
						}
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
			exportarEstadoDelTablero(tablero, "./src/estadosTablero/", numeroDeTurno);
			numeroDeTurno++;
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
		int x = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada x de la nueva ficha (entre 1 y " + tablero.getAncho() + "):", 1, tablero.getAncho()); //pregunta la posicion
		int y = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada y de la nueva ficha: (entre 1 y " + tablero.getAlto() + "):", 1, tablero.getAlto());
		int z = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada z de la nueva ficha: (entre 1 y " + tablero.getProfundidad() + "):", 1, tablero.getProfundidad());

		Casillero<Ficha> casillero = tablero.getCasillero(x, y, z);
		while (casillero == null || casillero.estaOcupado() || casillero.estaBloqueado()) {
			if(casillero.estaBloqueado())
			{
				Consola.imprimirMensaje("El casillero (" + x + ", " + y + ", " + z + ") esta bloqueado!");
			}
			if(casillero.estaOcupado())
			{
				Consola.imprimirMensaje("El casillero (" + x + ", " + y + ", " + z + ") ya esta ocupado por otra ficha!");
			}
			x = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada x de la nueva ficha (entre 1 y " + tablero.getAncho() + "):", 1, tablero.getAncho()); //pregunta la posicion
			y = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada y de la nueva ficha: (entre 1 y " + tablero.getAlto() + "):", 1, tablero.getAlto());
			z = Consola.obtenerNumeroEnteroEnRangoDelUsuario("Ingrese coordenada z de la nueva ficha: (entre 1 y " + tablero.getProfundidad() + "):", 1, tablero.getProfundidad());
			
			if(tablero.existeElCasillero(x, y, z))
			{
				casillero = tablero.getCasillero(x, y, z);
			}
			else
			{
				casillero = null;
			}
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
		Ficha fichaAMover = null;
		Movimiento movimiento = null;
		while(movimiento == null)
		{
			try {
				fichaAMover = Consola.consultarOpcionAlUsuario(jugador.getFichas().filtrar(ficha -> 
				{
					try 
					{
						return !ficha.estaBloqueado() &&
						tablero.tieneMovimientosPosibles(ficha);
					} 
					catch (Exception e) 
					{
						return false;
					}
				}),
	"Seleccione una ficha para mover", false); //Preguntar la ficha al usuario
	
			} catch (Exception e) {
				Consola.imprimirMensaje("No puede mover ninguna de sus fichas");
				return getTablero().getCasillero(1,1,1);
			}
			movimiento = Consola.consultarOpcionAlUsuario(tablero.obtenerMovimientosPosibles(fichaAMover),
									 "A que direccion desea mover la ficha?", true); //Pregunta al usuario
		}

		return mover(tablero, turnoActual, fichaAMover, movimiento);
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

	public <T> void exportarEstadoDelTablero(Tablero<T> tablero, String directorioBase, int numeroDeTurno) throws Exception
	{
		File directorio = AdministradorDeArchivos.crearDirectorio("./", directorioBase);
		if(numeroDeTurno == 1)
		{
			AdministradorDeArchivos.vaciarDirectorio(directorio);
		}
		File directorioTurno = AdministradorDeArchivos.crearDirectorio(directorio, "turno" + numeroDeTurno);
        ExportadorDeDatosAImagen.exportarTableroPorCapas(tablero, directorioTurno.getPath());
	}
	public EstadoJuego obtenerEstadoJuego(){
		return new EstadoJuego(getTablero(), getJugadores(), getTurnos());
	}
	public void restaurarEstado(EstadoJuego estado) {
        this.tablero = estado.getTablero();
        this.jugadores = estado.getJugadores();
        this.turnos = estado.getTurno();
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
	public Vector<Turno> getTurnos(){
		return this.turnos;
	}
	public Pila<EstadoJuego> getHistorialDeEstado(){
		return this.hitorialEstado;
	}
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}