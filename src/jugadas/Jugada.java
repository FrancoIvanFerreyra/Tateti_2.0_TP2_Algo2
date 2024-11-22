package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import tateti.Casillero;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;

/*
 * crea la clase abstracta jugada
 */
public abstract class Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Carta carta = null;
	private Jugador jugador;
        @SuppressWarnings("FieldMayBeFinal")
	private Lista<Jugador> jugadoresAfectados;
	@SuppressWarnings("rawtypes")
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
	/**
	 * Ejecuta la jugada sobre el tateti
	 * @param tateti no puede ser null
	 * @param turnoActual no puede ser null
	 * @return devuelve verdadero si se completo la jugada
	 * @throws NullPointerException si tateti o turnoActual son null
	 */
	public abstract boolean jugar(Tateti tateti, 
								Turno turnoActual) throws NullPointerException;
	/**
	 * Deshace todas las acciones hechas por la jugada
	 * @param tateti no puede ser null
	 * @throws NullPointerException si tateti es null
	 */
	public abstract void deshacer(Tateti tateti) throws NullPointerException;

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
	@SuppressWarnings("rawtypes")
	public Lista<Casillero> getCasillerosAfectados() {
		return this.casillerosAfectados;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	/**
	 * Asigna el jugador que realiza la jugada
	 * @param jugador no puede ser null
	 * @throws NullPointerException si jugador es null
	 */
	protected void setJugador(Jugador jugador) throws NullPointerException
	{
		ValidacionesUtiles.validarNoNull(jugador, "jugador");
		this.jugador = jugador;
	}

}
