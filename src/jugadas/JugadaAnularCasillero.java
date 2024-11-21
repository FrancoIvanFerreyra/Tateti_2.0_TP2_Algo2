package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Tateti;
import tateti.Turno;
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
 * pre: recibe el estado del tateti y el turno acutual del jugador
 * pos: se bloquea el casillero seleccionado mediante el ingreso de coordenas, el bloqueo se realiza mediante el incremento del contado de bloqueos del mismo, en 
 *      caso de que se cumpla se agrega al listado de casillero afectados y retorna true, caso contrario retorna false
 */
@Override
public boolean jugar(Tateti tateti, Turno turnoActual) throws Exception {
    Casillero<Ficha> casilleroAAnular;
    casilleroAAnular = tateti.obtenerCasilleroDirectoDelUsuario("Se necesitan los datos del casillero a anular");
    if(casilleroAAnular == null)
    {
        return false;
    }
    casilleroAAnular.incrementarBloqueosRestantes(1);
    tateti.getTablero().getCasillerosBloqueados().agregar(casilleroAAnular);
    getCasillerosAfectados().agregar(casilleroAAnular);
    Consola.imprimirMensaje("Se anulo correctamente el " + casilleroAAnular.toString() + "!");
    
    return true;

}

/**
 * pre: recibe el estado del tateti
 * pos: se disminuye el contador de bloques del casillero que fue afectado, se lo obtiene del listado de casillero afectados
 */
@Override
public void deshacer(Tateti tateti) throws Exception {
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        if(casilleroAfectado.estaBloqueado())
        {
            casilleroAfectado.reducirBloqueosRestantes(1);
            try {
                tateti.getTablero().getCasillerosBloqueados().removerPrimeraAparicion(casilleroAfectado);
                Consola.imprimirMensaje("Se libero correctamente el " + casilleroAfectado.toString() + "!");
            } catch (Exception e) {
                Consola.imprimirMensaje("No se pudo deshacer la jugada correctamente" + e.getMessage());
            }
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
