package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.Vector;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;

/**
 * El jugador decide que ficha del trablero quiere mover a un caisllero en especifico 
 */
public class JugadaBloquearFicha extends Jugada{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
/**
 * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
 * pos: se incializa una carta con la jugada que bloquea una ficha a otro jugador
 */
public JugadaBloquearFicha(Carta carta) {
    super(carta);
}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

/*
 * pre: recibe el estado del tateti y el turno acutual del jugador 
 * pos: bloquea una ficha de un jugador oponente, mediante un contador de bloqueos propio de la ficha, lo que le impide realizar cualquier moviemiento. Si el
 *      bloqueo se raliza con exito se retorna true, en caso contrario false (si el jugador no tiene fichas en el tablero o ya esta bloqueada)
 */
@Override
public boolean jugar(Tateti tateti, Turno turnoActual) throws NullPointerException {
    ValidacionesUtiles.validarNoNull(tateti, "tateti");
    ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");
    Jugador jugadorAAfectar;
    Casillero<Ficha> casilleroFichaABloquear = null;
    do
    {
        try {
            Vector<Jugador> jugadores = tateti.getJugadores().filtrar(
                jugador ->
                {
                    return jugador != null &&
                            jugador != turnoActual.getJugador() &&
                            jugador.tieneAlgunaFichaEnElTablero(tateti.getTablero());
                }
            );
            jugadorAAfectar = Consola.consultarOpcionAlUsuario(jugadores,
                        "A que jugador desea bloquear una ficha?", true);
        } catch (IllegalArgumentException e) {
            Consola.imprimirMensajeConSalto("No hay fichas de otros jugadores en el tablero");
            return false;
        }
        if(jugadorAAfectar == null)
        {
            return false;
        }
        Vector<Ficha> fichas = jugadorAAfectar.getFichas().filtrar(
            ficha -> {
                return ficha != null &&
                        tateti.getTablero().contiene(ficha) &&
                        !ficha.estaBloqueado();
            }
        );
        Lista<Casillero<Ficha>> casillerosFichas = tateti.getTablero().getCasilleros(fichas);
        try {
            casilleroFichaABloquear = Consola.consultarOpcionAlUsuario(casillerosFichas,
                             "Que ficha desea bloquear?", true);
            
        } catch (IllegalArgumentException e) {
            Consola.imprimirMensajeConSalto("Todas las fichas del jugador " + jugadorAAfectar.getNombre() +
                                     " estan bloqueadas!");
            casilleroFichaABloquear = null;
        }
    } while(casilleroFichaABloquear == null);


    // aumenta la cantidad de bloquoes de esa ficha segun la cantidad de participantes 
    casilleroFichaABloquear.getDato().incrementarBloqueosRestantes(tateti.getJugadores().getLongitud()
                                                                                                 + 1);
    getCasillerosAfectados().agregar(casilleroFichaABloquear);
    Consola.imprimirMensajeConSalto("Se bloqueo correctamente la ficha ubicada en " +
                             casilleroFichaABloquear.toString() + "!");
    return true;

}

/**
 * pre: reciebe el estado del tablero
 * pos: se libera a la ficha bloqueada de su estado bloqueado
 */
@Override
public void deshacer(Tateti tateti) throws NullPointerException {
    ValidacionesUtiles.validarNoNull(tateti, "tateti");
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        Ficha ficha = casilleroAfectado.getDato();
        if(ficha.estaBloqueado())
        {
            ficha.reducirBloqueosRestantes(ficha.getBloqueosRestantes());
            Consola.imprimirMensajeConSalto("Se libero correctamente a la ficha ubicada en " +
                                    casilleroAfectado.toString() + "!");
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
