package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;

/*
 * El jugador retrocede una jugada 
 */
public class JugadaRetrocederUnaJugada extends Jugada{
    /*
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada
     * pos: se forma la carta que contiene la juegada retroceder una jugada
     */
    public JugadaRetrocederUnaJugada(Carta carta) {
        super(carta);
    }

    /*
     * pre: recibe el estado del tateti y el turno acutual del jugador 
     * pos: llamama a la funcion de retrocederTurnos para realizar la acción de volver atras una jugada, en caso de que no haya un turno
     *      por retroceder retonrnamos false y mostramos un mensaje de error, en caso contrario retornamos true y un mensaje
     */
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) throws NullPointerException {
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");

        try
        {
            tateti.retrocederTurnos(1);
            Consola.imprimirMensajeConSalto("Se retrocedio correctamente una jugada!");
        }
        catch(IllegalArgumentException e)
        {
            Consola.imprimirMensajeConSalto("No hay jugadas que retroceder");
            return false;
        }
        return true;
    }

    /*
     * pre: recibe el estado del tateti
     * pos: en este caos no se puede deshacer la jugada de retroceder una jugada debido que traeria problemas de juego, se muestra
     *      un mensaje que informa al usuario de la situación
     */
    @Override
    public void deshacer(Tateti tateti) throws NullPointerException {
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        //No se puede deshacer
        Consola.imprimirMensajeConSalto("No se puede deshacer el retroceso de jugadas");
    }

}
