package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import tateti.Casillero;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;


public abstract class Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Carta carta = null;
	private Jugador jugador;
	private Lista<Jugador> jugadoresAfectados;
	private Lista<Casillero> casillerosAfectados;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public Jugada(Carta carta) {
		this.carta = carta;
		this.jugador = null;
		this.jugadoresAfectados = new ListaSimplementeEnlazada<>();
		this.casillerosAfectados = new ListaSimplementeEnlazada<>();
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public abstract boolean jugar(Tateti tateti, 
								Turno turnoActual) throws Exception;

	public abstract void deshacer(Tateti tateti) throws Exception;

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public Carta getCarta() {
		return this.carta;
	}
	public Jugador getJugador() {
		return this.jugador;
	}
	public Lista<Jugador> getJugadoresAfectados() {
		return this.jugadoresAfectados;
	}

	public Lista<Casillero> getCasillerosAfectados() {
		return this.casillerosAfectados;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	protected void setJugador(Jugador jugador) throws Exception
	{
		if(jugador == null)
		{
			throw new Exception("El jugador no puede ser null");
		}
		this.jugador = jugador;
	}

}
