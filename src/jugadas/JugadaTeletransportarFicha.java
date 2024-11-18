package jugadas;

import cartas.Carta;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;
import interfaz.Consola;
import tateti.Ficha;
import tateti.Casillero;


public class JugadaTeletransportarFicha extends Jugada{
    private Jugador jugadorActual =null;
    private Casillero casilleroDestino = null;
    private Casillero casilleroOrigen = null;
    private Ficha fichaATeletransportar = null;
    private int x,y,z;
    public JugadaTeletransportarFicha(Carta carta){
        super(carta);
    }
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) {
        try {
            jugadorActual = turnoActual.getJugador();
    
            // Seleccionar ficha a teletransportar
            do {
                try {
                    fichaATeletransportar = Consola.consultarOpcionAlUsuario(
                        jugadorActual.getFichas().filtrar(ficha -> {
                            try {
                                return tateti.getTablero().contiene(ficha) && !ficha.estaBloqueado();
                            } catch (Exception e) {
                                return false;
                            }
                        }), "¿Qué ficha desea teletransportar?", true);
                    casilleroOrigen = tateti.getTablero().getCasillero(fichaATeletransportar);
                } catch (Exception e) {
                    Consola.imprimirMensaje("Todas las fichas del jugador están bloqueadas o no están en el tablero.");
                    fichaATeletransportar = null;
                }
            } while (fichaATeletransportar == null);
    
            // Seleccionar casillero destino
            do {
                x = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada X del casillero destino:");
                y = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada Y del casillero destino:");
                z = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada Z del casillero destino:");
    
                if (!tateti.getTablero().existeElCasillero(x, y, z)) {
                    Consola.imprimirMensaje("El casillero no existe. Intente nuevamente.");
                    casilleroDestino = null;
                } else {
                    casilleroDestino = tateti.getTablero().getCasillero(x, y, z);
                    if (casilleroDestino.estaOcupado()) {
                        Consola.imprimirMensaje("El casillero está ocupado. Intente nuevamente.");
                    } else if (casilleroDestino.estaBloqueado()) {
                        Consola.imprimirMensaje("El casillero está bloqueado. Intente nuevamente.");
                    }
                }
            } while (casilleroDestino == null || casilleroDestino.estaOcupado() || casilleroDestino.estaBloqueado());
    
            // Realizar la teletransportación
            casilleroOrigen.vaciar();
            casilleroDestino.setDato(fichaATeletransportar);
    
            return true;
        } catch (Exception e) {
            Consola.imprimirMensaje("Error durante la jugada: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public void deshacer(Tateti tateti) {
        try {
            casilleroDestino.vaciar();
            casilleroOrigen.setDato(fichaATeletransportar);
        } catch (Exception e) {
            Consola.imprimirMensaje("Error al deshacer la jugada: " + e.getMessage());
        }
    }
}
