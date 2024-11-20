package jugadas;

import cartas.Carta;
import estructuras.Vector;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

public class JugadaBloquearFicha extends Jugada{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
public JugadaBloquearFicha(Carta carta) {
    super(carta);
}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

@Override
public boolean jugar(Tateti tateti, Turno turnoActual) throws Exception {
    Jugador jugadorAAfectar;
    Casillero<Ficha> casilleroFichaABloquear = null;
    do
    {
        try {
            Vector<Jugador> jugadores = tateti.getJugadores().filtrar(
                jugador ->
                {
                    return jugador != turnoActual.getJugador() &&
                            jugador.tieneAlgunaFichaEnElTablero();
                }
            );
            jugadorAAfectar = tateti.obtenerJugadorDelUsuario(jugadores,
                    "A que jugador desea bloquear una ficha?");
        } catch (Exception e) {
            Consola.imprimirMensaje("No hay fichas de otros jugadores en el tablero");
            return false;
        }
        if(jugadorAAfectar == null)
        {
            return false;
        }
        Vector<Ficha> fichas = jugadorAAfectar.getFichas().filtrar(
            ficha -> {
                try {
                    return tateti.getTablero().contiene(ficha) && !ficha.estaBloqueado();
                } catch (Exception e) {
                    return false;
                }
            }
        );
        try {
            casilleroFichaABloquear = tateti.obtenerCasilleroDeFichaDelUsuario(fichas,
                             "Que ficha desea bloquear?");
            
        } catch (Exception e) {
            Consola.imprimirMensaje("Todas las fichas del jugador " + jugadorAAfectar.getNombre() + " estan bloqueadas!");
            casilleroFichaABloquear = null;
        }
    } while(casilleroFichaABloquear == null);



    casilleroFichaABloquear.getDato().incrementarBloqueosRestantes(1);
    getCasillerosAfectados().agregar(casilleroFichaABloquear);
    Consola.imprimirMensaje("Se bloqueo correctamente la ficha ubicada en " +
                             casilleroFichaABloquear.toString() + "!");
    return true;

}

@Override
public void deshacer(Tateti tateti) throws Exception {
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        Ficha ficha = casilleroAfectado.getDato();
        if(ficha.estaBloqueado())
        {
            ficha.reducirBloqueosRestantes(1);
            Consola.imprimirMensaje("Se quito 1 bloqueo correctamente a la ficha ubicada en " +
                                    casilleroAfectado.toString() + "!");
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
