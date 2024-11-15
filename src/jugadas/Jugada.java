package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;


public abstract class Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Carta carta = null;
	private Jugador jugador;
	private Lista<Jugador> jugadoresAfectados;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public Jugada(Carta carta) {
		this.carta = carta;
		this.jugador = null;
		this.jugadoresAfectados = new ListaSimplementeEnlazada<>();
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public abstract void jugar(Tateti tateti, 
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
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
