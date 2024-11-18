package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;

public class JugadaCambiarColorFicha extends Jugada {
    private Jugador jugadorBeneficiado;
    private Jugador jugadorAfectado;
    private Ficha fichaApropiada;
    private Ficha fichaNuevaAfectado;
    private Ficha fichaRemovidaBeneficiado;

    public JugadaCambiarColorFicha(Carta carta) {
        super(carta);
    }

    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual){
        try {
            jugadorBeneficiado = turnoActual.getJugador();
            Ficha fichaACambiarColor;

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
                    fichaACambiarColor = Consola.consultarOpcionAlUsuario(jugadorAfectado.getFichas().filtrar(
                        ficha -> {
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

            // Validar fichas del beneficiado
            if (jugadorBeneficiado.getFichas().getLongitud() > 0) {
                fichaRemovidaBeneficiado = jugadorBeneficiado.getFichas().obtener(0);
            } else {
                Consola.imprimirMensaje("El jugador beneficiado no tiene fichas disponibles para remover.");
                return false;
            }

            // Crear una nueva ficha para el jugador afectado
            fichaNuevaAfectado = new Ficha(
                jugadorAfectado.getFicha(0).getSimbolo(),
                jugadorAfectado.getFicha(0).getColor()
            );

            // Cambiar color y actualizar vectores
            fichaACambiarColor.setColor(jugadorBeneficiado.getColor());
            jugadorAfectado.getFichas().remover(jugadorAfectado.getFichas().obtenerPosicion(fichaACambiarColor));
            jugadorAfectado.agregarFicha(fichaNuevaAfectado);
            jugadorBeneficiado.getFichas().remover(0);
            jugadorBeneficiado.getFichas().agregar(fichaACambiarColor);

            fichaApropiada = fichaACambiarColor;
            return true;

        } catch (Exception e) {
            Consola.imprimirMensaje("Error durante la jugada: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void deshacer(Tateti tateti) {
        try {
            if (fichaApropiada == null || fichaRemovidaBeneficiado == null || fichaNuevaAfectado == null) {
                Consola.imprimirMensaje("No hay información suficiente para deshacer la jugada.");
                return;
            }

            // Restaurar el color original de la ficha apropiada
            fichaApropiada.setColor(jugadorAfectado.getColor());

            // Quitar la ficha del jugador beneficiado
            jugadorBeneficiado.getFichas().remover(jugadorBeneficiado.getFichas().obtenerPosicion(fichaApropiada));

            // Devolver la ficha al jugador afectado
            jugadorAfectado.getFichas().remover(jugadorAfectado.getFichas().obtenerPosicion(fichaNuevaAfectado));
            jugadorAfectado.getFichas().agregar(fichaApropiada);

            // Restaurar la ficha removida al jugador beneficiado
            jugadorBeneficiado.getFichas().agregar(fichaRemovidaBeneficiado);

            Consola.imprimirMensaje("La jugada fue deshecha exitosamente.");
        } catch (Exception e) {
            Consola.imprimirMensaje("Error al deshacer la jugada: " + e.getMessage());
        }
    }
}
