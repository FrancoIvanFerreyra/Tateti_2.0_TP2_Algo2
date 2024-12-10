package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;
/**
 * El jugador decice que casillero del tablero quiere bloquear
 */
public class JugadaAnularCasillero extends Jugada{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
/**
    /**
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
     * pos: se incializa una carta con la jugada que bloquea un casillero
     */
public JugadaAnularCasillero(Carta carta) {
    super(carta);
}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

/*
 * pre: tateti y turno actual no pueden ser null
 * pos: incrementa los bloqueos restantes del casillero seleccionado,
 *      exactamente la cantidad de jugadores + 1, dejando al casillero
 *      anulado por una ronda completa
 */
@Override
public boolean jugar(Tateti tateti, Turno turnoActual) throws NullPointerException {
    ValidacionesUtiles.validarNoNull(tateti, "tateti");
    ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");
    Casillero<Ficha> casilleroAAnular;
    casilleroAAnular = tateti.obtenerCasilleroDirectoDelUsuario("Se necesitan los datos del casillero a anular", true);
    if(casilleroAAnular == null)
    {
        return false;
    }
    casilleroAAnular.incrementarBloqueosRestantes(tateti.getJugadores().getLongitud() + 1);
    if(!tateti.getTablero().getCasillerosBloqueados().contiene(casilleroAAnular))
    {
        tateti.getTablero().getCasillerosBloqueados().agregar(casilleroAAnular);
    }
    getCasillerosAfectados().agregar(casilleroAAnular);
    Consola.imprimirMensajeConSalto("Se anulo correctamente el " + casilleroAAnular.toString() + "!");
    
    return true;

}

/**
 * pre: tateti no puede ser null
 * pos: se libera al casillero de su estado bloqueado
 */
@Override
public void deshacer(Tateti tateti) throws NullPointerException {
    ValidacionesUtiles.validarNoNull(tateti, "tateti");
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        if(casilleroAfectado.estaBloqueado())
        {
            casilleroAfectado.reducirBloqueosRestantes(casilleroAfectado.getBloqueosRestantes());
            if (!casilleroAfectado.estaBloqueado() &&
            tateti.getTablero().getCasillerosBloqueados().contiene(casilleroAfectado))
            {
                tateti.getTablero().getCasillerosBloqueados().removerPrimeraAparicion(casilleroAfectado);
                Consola.imprimirMensajeConSalto("Se libero correctamente el " + casilleroAfectado.toString() + "!");    
            }
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
