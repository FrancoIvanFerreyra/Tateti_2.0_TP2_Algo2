package tateti;

import estructuras.Vector;
;

public class EstadoJuego {
    private Tablero<Ficha> tablero;
    private Vector<Jugador> jugadores;
    private Vector<Turno> turnos;

    public EstadoJuego(Tablero<Ficha> tablero, Vector<Jugador> jugadores, Vector<Turno> turnos) {
        this.tablero = tablero;
        this.jugadores = jugadores;
        this.turnos = turnos;
    }

    public Tablero<Ficha> getTablero() {
        return this.tablero;
    }

    public Vector<Jugador> getJugadores() {
        return jugadores;
    }

    public Vector<Turno> getTurno() {
        return turnos;
    }
}
