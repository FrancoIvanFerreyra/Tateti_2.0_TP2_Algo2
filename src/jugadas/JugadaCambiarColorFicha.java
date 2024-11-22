package jugadas;

import cartas.Carta;
import estructuras.Vector;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

/**
 *  El jugador decide que ficha de otro jugador cambiarle el color y apropiarsela
 */
public class JugadaCambiarColorFicha extends Jugada {
    /**
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
     * pos: se incializa una carta con la jugada que cambia el color de una ficha
     */
    public JugadaCambiarColorFicha(Carta carta) {
        super(carta);
    }
    /*
     * pre: recibe el estado del tateti y el turno acutual del jugador
     * pos: se cambia el color de la ficha de un oponente, seleccionado previamente, y de esta manera se realiza un intercambio de fichas, en caso de que no se 
     *      pueda realizar el intercambio se lanza un mensaje de error y retorna false, en caso de que el intercambio se cumpla retorna true  
     */
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual){
        Jugador jugadorBeneficiado;
        Jugador jugadorAfectado;
        Ficha fichaAColocar;
        try {
            jugadorBeneficiado = turnoActual.getJugador();
            Casillero<Ficha> casilleroFichaACambiarColor;

            if(jugadorBeneficiado.tieneTodasLasFichasEnElTablero(tateti.getTablero()))
            {
                Consola.imprimirMensaje("No tenes fichas disponibles para intercambiar. Todas estan en el tablero!");
                return false;
            }

            do 
            {
                try {
                    Vector<Jugador> jugadores = tateti.getJugadores().filtrar(
                        jugador -> (jugador != jugadorBeneficiado && jugador.tieneAlgunaFichaEnElTablero(tateti.getTablero()))
                    );
                    // Seleccionar jugador afectado
                    jugadorAfectado = tateti.obtenerJugadorDelUsuario(jugadores,
                                 "¿A qué jugador desea cambiarle la ficha?");

                } catch (Exception e) {
                    Consola.imprimirMensaje("No hay fichas de otros jugadores en el tablero.");
                    return false;
                }

                if (jugadorAfectado == null) {
                    return false;
                }

                try {
                    Vector<Ficha> fichas = jugadorAfectado.getFichas().filtrar(
                        ficha -> {
                            try {
                                return tateti.getTablero().contiene(ficha) && !ficha.estaBloqueado();
                            } catch (NullPointerException e) {
                                return false;
                            }
                        }
                    );

                    casilleroFichaACambiarColor = tateti.obtenerCasilleroDeFichaDelUsuario(fichas,
                                                 "Que ficha desea cambiar de color?");
                } catch (Exception e) {
                    Consola.imprimirMensaje("Todas las fichas del jugador " + jugadorAfectado + " están bloqueadas!");
                    casilleroFichaACambiarColor = null;
                }
            } while (casilleroFichaACambiarColor == null);

            fichaAColocar = new Ficha(String.valueOf(jugadorBeneficiado.getId()));

            tateti.getTablero().eliminarRelacionDatoCasillero(casilleroFichaACambiarColor.getDato());
            tateti.getTablero().eliminarRelacionDatoColor(casilleroFichaACambiarColor.getDato());
            jugadorAfectado.quitarFicha(casilleroFichaACambiarColor.getDato());

            casilleroFichaACambiarColor.setDato(fichaAColocar);
            tateti.getTablero().actualizarRelacionDatoCasillero(fichaAColocar, casilleroFichaACambiarColor);
            tateti.getTablero().actualizarRelacionDatoColor(fichaAColocar, jugadorBeneficiado.getColor());

            jugadorBeneficiado.agregarFicha(fichaAColocar);

            setJugador(jugadorBeneficiado);
            getJugadoresAfectados().agregar(jugadorAfectado);
            getCasillerosAfectados().agregar(casilleroFichaACambiarColor);
            Consola.imprimirMensaje("Se cambio correctamente el color de la ficha ubicada en " + 
                                     casilleroFichaACambiarColor.toString() + "!");      
            return true;

        } catch (Exception e) {
            Consola.imprimirMensaje("Error durante la jugada: " + e.getMessage());
            return false;
        }
    }

    /*
     * pre: se recibe el estaedo del tablero
     * pos: se busca en la lista de casilleros y jugadores afectados los atributos que intervinieron en esta jugada,se regresa su color y casillero original, 
     *      vaciando el nuevo casillero. En caso de que no esten en el listado ambos atributos o se produzca algun error se lanza un mensaje de error
     */
    @Override
    public void deshacer(Tateti tateti) {
        try {
            if (getJugadoresAfectados().estaVacia() || getCasillerosAfectados().estaVacia()) {
                Consola.imprimirMensaje("No hay información suficiente para deshacer la jugada.");
                return;
            }

            getJugadoresAfectados().iniciarCursor();
            while(getJugadoresAfectados().avanzarCursor())
            {
                Jugador jugadorAfectado = getJugadoresAfectados().obtenerCursor();
                getCasillerosAfectados().iniciarCursor();
                while(getCasillerosAfectados().avanzarCursor())
                {
                    @SuppressWarnings("unchecked")
                    Casillero<Ficha> casillero = getCasillerosAfectados().obtenerCursor();
                    Ficha fichaAEliminar = casillero.getDato();
                    tateti.getTablero().eliminarRelacionDatoCasillero(fichaAEliminar);
                    tateti.getTablero().eliminarRelacionDatoColor(fichaAEliminar);
                    getJugador().quitarFicha(fichaAEliminar);

                    Ficha fichaAReponer = new Ficha(String.valueOf(jugadorAfectado.getId()));
                    casillero.setDato(fichaAReponer);
                    tateti.getTablero().actualizarRelacionDatoCasillero(fichaAReponer, casillero);
                    tateti.getTablero().actualizarRelacionDatoColor(fichaAReponer, jugadorAfectado.getColor());
                    jugadorAfectado.agregarFicha(fichaAReponer);
                    
                    Consola.imprimirMensaje("La ficha ubicada en " + 
                                            casillero.toString() + "volvio a su color anterior!");              
                }

            }
        } catch (Exception e) {
            Consola.imprimirMensaje("Error al deshacer la jugada: " + e.getMessage());
        }
    }
}
