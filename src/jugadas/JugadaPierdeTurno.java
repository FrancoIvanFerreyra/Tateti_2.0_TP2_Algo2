package jugadas;

import cartas.Carta;
import estructuras.Vector;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

/**
 * El jugador decide a quien le hace perder el turno
 */
public class JugadaPierdeTurno extends Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public JugadaPierdeTurno(Carta carta) {
		super(carta);
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	public boolean jugar(Tateti tateti, 
						Turno turnoActual) throws Exception {
		Vector<Jugador> jugadores = tateti.getJugadores().filtrar(
			jugador -> {
				return jugador != turnoActual.getJugador();
			}
		);
		
		//Preguntar jugador
		Jugador jugadorABloquear = tateti.obtenerJugadorDelUsuario(jugadores, "A que jugador desea bloquear 1 turno?");
		if(jugadorABloquear == null)
		{
			return false;
		} 
		Turno turno = tateti.obtenerTurno(jugadorABloquear); //le pregunto al tateti el proximo turno del jugador
		turno.incrementarBloqueosRestantes(1);
		getJugadoresAfectados().agregar(jugadorABloquear);
		return true;
	}

	@Override
	public void deshacer(Tateti tateti) throws Exception {
		getJugadoresAfectados().iniciarCursor();
		while(getJugadoresAfectados().avanzarCursor())
		{
			Jugador jugadorAfectado = getJugadoresAfectados().obtenerCursor();
			Turno turno = tateti.obtenerTurno(jugadorAfectado);
			turno.reducirBloqueosRestantes(1);	
		}
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
