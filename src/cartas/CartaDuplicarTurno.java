package cartas;

import jugadas.Jugada;
import jugadas.JugadaDuplicarTurno;

public class CartaDuplicarTurno extends Carta{

    public CartaDuplicarTurno()
    {

    }

    @Override
    protected String getTituloPorDefecto() {
        return "Duplicar turno";
    }

    @Override
    public Jugada getJugada() {
        return new JugadaDuplicarTurno(this);
    }

}
