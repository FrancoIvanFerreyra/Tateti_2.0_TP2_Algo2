package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import tateti.Casillero;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

/*
 * crea la clase abstracta jugada
 */
public abstract class Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Carta carta = null;
	private Jugador jugador;
	private Lista<Jugador> jugadoresAfectados;
	private Lista<Casillero> casillerosAfectados;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	/*
	 * pre: recibe la carta que es la base de la jugada
	 * pos: retorna la jugada que cuenta con los atributos, jugador, lista de jugadores afectados y lista de casilleros afectados
	 */
	public Jugada(Carta carta) {
		this.carta = carta;
		this.jugador = null;
		this.jugadoresAfectados = new ListaSimplementeEnlazada<>();
		this.casillerosAfectados = new ListaSimplementeEnlazada<>();
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/*
	 * pre: recibe el estado del tateti y el turno acutual del jugador 
	 * pos: forma el metodo abtracto  jugar que retorna true o false
	 */
	public abstract boolean jugar(Tateti tateti, 
								Turno turnoActual) throws Exception;
	/**
	 * pre: recibe el estado del tateti
	 * pos: forma el metodo abtracto para deshacer lo hecho por la jugada
	 */
	public abstract void deshacer(Tateti tateti) throws Exception;

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	/*
	 * pre:-
	 * pos: retorna la carta asociada a la jugada
	 */
	public Carta getCarta() {
		return this.carta;
	}
	/*
	 * pre: -
	 * pos: retorna el jugador que realiza la jugada
	 */
	public Jugador getJugador() {
		return this.jugador;
	}
	/*
	 * pre: -
	 * pos: retonra la lista de jugadores que fueron afectados por la jugada
	 */
	public Lista<Jugador> getJugadoresAfectados() {
		return this.jugadoresAfectados;
	}
	/*
	 * pre: = 
	 * pos: retorna la lista de casillero afectados por la jugada
	 */
	public Lista<Casillero> getCasillerosAfectados() {
		return this.casillerosAfectados;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	/*
	 * pre: recibe el jugador que realiza la jugada
	 * pos: estabelce al jugador que realiza la jugada
	 */
	protected void setJugador(Jugador jugador) throws Exception
	{
		if(jugador == null)
		{
			throw new Exception("El jugador no puede ser null");
		}
		this.jugador = jugador;
	}

}
