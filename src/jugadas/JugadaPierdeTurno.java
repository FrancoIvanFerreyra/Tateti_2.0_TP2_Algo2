package jugadas;

import cartas.Carta;
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
	public void jugar(Tateti tateti, 
						Turno turnoActual) throws Exception {
		//Preguntar jugador
		Jugador jugador = null; //del tateti tateti.getJugadores(); //pregunto alguno
		Turno turno = null; //le pregunto al tateti el proximo turno del jugador
		turno.incrementarBloqueosRestantes(1);
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
