package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

public class JugadaCambiarColorFicha extends Jugada {
    private Jugador jugadorBeneficiado;
    private Jugador jugadorAfectado;
    private Ficha fichaAColocar;

    public JugadaCambiarColorFicha(Carta carta) {
        super(carta);
    }

    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual){
        try {
            jugadorBeneficiado = turnoActual.getJugador();
            Ficha fichaACambiarColor;

            if(jugadorBeneficiado.tieneTodasLasFichasEnElTablero())
            {
                Consola.imprimirMensaje("No tenes fichas disponibles para intercambiar. Todas estan en el tablero!");
                return false;
            }

            do {
                try {
                    // Seleccionar jugador afectado
                    jugadorAfectado = Consola.consultarOpcionAlUsuario(tateti.getJugadores().filtrar(
                        jugador -> (jugador != jugadorBeneficiado && jugador.tieneAlgunaFichaEnElTablero())
                    ), "¿A qué jugador desea cambiarle la ficha?", true);

                } catch (Exception e) {
                    Consola.imprimirMensaje("No hay fichas de otros jugadores en el tablero.");
                    return false;
                }

                if (jugadorAfectado == null) {
                    return false;
                }

                try {
                    // Seleccionar ficha a cambiar
                    fichaACambiarColor = Consola.consultarOpcionAlUsuario(jugadorAfectado.getFichas().filtrar( ficha -> {
                            try{
                                return tateti.getTablero().contiene(ficha) && !ficha.estaBloqueado();
                            } catch (Exception e){
                                return false;
                            }
                        }
                        ),"¿Qué ficha desea apropiarse?",true);
                } catch (Exception e) {
                    Consola.imprimirMensaje("Todas las fichas del jugador " + jugadorAfectado + " están bloqueadas.");
                    fichaACambiarColor = null;
                }
            } while (fichaACambiarColor == null);

            fichaAColocar = new Ficha(jugadorBeneficiado.getNombre().charAt(0));

            Casillero<Ficha> casillero = tateti.getTablero().getCasillero(fichaACambiarColor);
            tateti.getTablero().eliminarRelacionDatoCasillero(fichaACambiarColor);
            tateti.getTablero().eliminarRelacionDatoColor(fichaACambiarColor);
            jugadorAfectado.quitarFicha(fichaACambiarColor);

            casillero.setDato(fichaAColocar);
            tateti.getTablero().actualizarRelacionDatoCasillero(fichaAColocar, casillero);
            tateti.getTablero().actualizarRelacionDatoColor(fichaAColocar, jugadorBeneficiado.getColor());

            jugadorBeneficiado.agregarFicha(fichaAColocar);

            setJugador(jugadorBeneficiado);
            getJugadoresAfectados().agregar(jugadorAfectado);
            getCasillerosAfectados().agregar(casillero);
            return true;

        } catch (Exception e) {
            Consola.imprimirMensaje("Error durante la jugada: " + e.getMessage());
            return false;
        }
    }

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

                    Ficha fichaAReponer = new Ficha(jugadorAfectado.getNombre().charAt(0));
                    casillero.setDato(fichaAReponer);
                    tateti.getTablero().actualizarRelacionDatoCasillero(fichaAReponer, casillero);
                    tateti.getTablero().actualizarRelacionDatoColor(fichaAReponer, jugadorAfectado.getColor());
                    jugadorAfectado.agregarFicha(fichaAReponer);    
                }

            }

            Consola.imprimirMensaje("La jugada fue deshecha exitosamente.");
        } catch (Exception e) {
            Consola.imprimirMensaje("Error al deshacer la jugada: " + e.getMessage());
        }
    }
}
