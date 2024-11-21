package jugadas;

import cartas.Carta;
import estructuras.Vector;
import interfaz.Consola;
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
	/**
	 * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
	 * pos: se incializa una carta con la jugada que hace perder un turno a otro jugador
	 */
	public JugadaPierdeTurno(Carta carta) {
		super(carta);
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/**
	 * pre: recibe el estado del tateti y el turno acutual del jugador 
	 * pos: se realiza la seleccion de un jugador, en caso de ser nulo retornamos false, en caso contrario se incrementa el contador de bloqueos al proximo turno,
	 * 		si todo el proceso se realiza con exito se retorna true
	 */
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
		Consola.imprimirMensaje("Se bloqueo correctamente 1 turno a " + jugadorABloquear.toString() + "!");
		return true;
	}
	/* 
	 * pre: recibe el estado del tateti 
	 * pos: se busca al jugador perjudicado en la lista de jugadores afectados, si se lo encuentra se reduce su contador de bloques y mostramos un mensaje
	 */
	@Override
	public void deshacer(Tateti tateti) throws Exception {
		getJugadoresAfectados().iniciarCursor();
		while(getJugadoresAfectados().avanzarCursor())
		{
			Jugador jugadorAfectado = getJugadoresAfectados().obtenerCursor();
			Turno turno = tateti.obtenerTurno(jugadorAfectado);
			try {
				turno.reducirBloqueosRestantes(1);
			} catch (IllegalArgumentException e) {
				Consola.imprimirMensaje(jugadorAfectado.getNombre() +
										"ya cumplio con su bloqueo, imposible deshacer");
				return;
			}
			Consola.imprimirMensaje("Se quito correctamente 1 bloqueo a " + 
									jugadorAfectado.getNombre());		
		}
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
