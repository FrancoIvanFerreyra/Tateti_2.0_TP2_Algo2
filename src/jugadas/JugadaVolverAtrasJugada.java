package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.EstadoJuego;
import tateti.Tateti;
import tateti.Turno;

public class JugadaVolverAtrasJugada extends Jugada {

    public JugadaVolverAtrasJugada(Carta carta) {
        super(carta);
    }

    @Override
    public boolean jugar(Tateti tateti, Turno turno) {
        String mensajeError = "";

        // Verifica si el historial de turnos está vacío
        if (tateti.getHistorialTurnos().contarElementos() == 0) {
            mensajeError = "No hay turnos por retroceder.";
            Consola.imprimirMensaje(mensajeError);
            return false;
        }

        try {
            tateti.getHistorialDeEstado().apilar(tateti.obtenerEstadoJuego());
            // Retrocede un turno
            tateti.retrocederTurnos(1);
            Consola.imprimirMensaje("Se ha retrocedido el turno exitosamente.");
            return true;
        } catch (Exception e) {
            // Si ocurre un error al retroceder el turno
            mensajeError = "No se pudo retroceder el turno: " + e.getMessage();
            Consola.imprimirMensaje(mensajeError);
            return false;
        }
    }

    @Override
    public void deshacer(Tateti tateti) {
        String mensajeError = "";

        // Verifica si el historial de turnos está vacío
        if (tateti.getHistorialTurnos().contarElementos() == 0) {
            mensajeError = "No hay turnos que deshacer.";
            Consola.imprimirMensaje(mensajeError);
            return;
        }

        try {
            // Restaura el último estado guardado
            EstadoJuego estadoAnterior = tateti.getHistorialDeEstado().desapilar();
            tateti.restaurarEstado(estadoAnterior);
            Consola.imprimirMensaje("Se deshizo el retroceso de turno.");
            Consola.imprimirMensaje("La jugada fue deshecha exitosamente.");
        } catch (Exception e) {
            // Si ocurre un error al deshacer la jugada
            mensajeError = "No se pudo deshacer la jugada: " + e.getMessage();
            Consola.imprimirMensaje(mensajeError);
        }
    }
}
