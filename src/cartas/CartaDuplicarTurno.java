package cartas;

import jugadas.Jugada;
import jugadas.JugadaDuplicarTurno;

public class CartaDuplicarTurno extends Carta{
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Duplicar turno"
     */
    public CartaDuplicarTurno(){}
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Duplicar turno"
     */
    @Override
    protected String getTituloPorDefecto() {
        return "Duplicar turno";
    }
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
    @Override
    public Jugada getJugada() {
        return new JugadaDuplicarTurno(this);
    }

}
