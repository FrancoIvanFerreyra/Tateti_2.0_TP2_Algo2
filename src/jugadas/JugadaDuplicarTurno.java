package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;

/*
 * permiede a un jugador duplicar su turno
 */
public class JugadaDuplicarTurno extends Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
     * pos: se incializa una carta con la jugada que duplica turno
     */
	public JugadaDuplicarTurno(Carta carta) {
		super(carta);
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/*
	 * pre: recibe el estado del tateti y el turno acutual del jugador 
	 * pos: se le suma un subturno al jugador actual, que le permite jugar de nuevo, si se cumple retornamos true
	 */
	@Override
	public boolean jugar(Tateti tateti, 
						Turno turnoActual) throws NullPointerException {
		ValidacionesUtiles.validarNoNull(tateti, "tateti");
        ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");

		turnoActual.agregarSubturno();
		Consola.imprimirMensaje("Turno duplicado. Podes jugar nuevamente!");
		return true;
	}
	/*
	 * pre: recibe el estao del tateti
	 * pos: en este caso no se puede deshacer la jugada duplicar turnos
	 */
	@Override
    public void deshacer(Tateti tateti) throws NullPointerException {
		ValidacionesUtiles.validarNoNull(tateti, "tateti");
		
		//No se puede deshacer
		Consola.imprimirMensaje("No se puede deshacer la duplicacion del turno");

    }
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	


}