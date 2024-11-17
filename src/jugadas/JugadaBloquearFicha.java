package jugadas;

import cartas.Carta;
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
    Jugador jugadorAAfectar = null;
    Ficha fichaAAnular = null;
    do
    {
        try {
            jugadorAAfectar = Consola.consultarOpcionAlUsuario(tateti.getJugadores().filtrar(
                jugador ->
                {
                    return jugador != turnoActual.getJugador() &&
                            jugador.tieneAlgunaFichaEnElTablero();
                }
            ), "A que jugador desea bloquear una ficha?", true);
        } catch (Exception e) {
            Consola.imprimirMensaje("No hay fichas de otros jugadores en el tablero");
            return false;
        }
        if(jugadorAAfectar == null)
        {
            return false;
        }
        try {
            fichaAAnular = Consola.consultarOpcionAlUsuario(jugadorAAfectar.getFichas().filtrar(
                ficha -> {
                    try {
                        return tateti.getTablero().contiene(ficha) && !ficha.estaBloqueado();
                    } catch (Exception e) {
                        return false;
                    }
                }
            ), "Que ficha desea bloquear?", true);
            
        } catch (Exception e) {
            Consola.imprimirMensaje("Todas las fichas del jugador " + jugadorAAfectar.toString() + "estan bloqueadas!");
        }
    } while(fichaAAnular == null);



    fichaAAnular.incrementarBloqueosRestantes(1);
    getCasillerosAfectados().agregar(tateti.getTablero().getCasillero(fichaAAnular));
    return true;

}

@Override
public void deshacer(Tateti tateti) throws Exception {
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        Casillero casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        Ficha ficha = (Ficha)casilleroAfectado.getDato();
        if(ficha.estaBloqueado())
        {
            ficha.reducirBloqueosRestantes(1);
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
