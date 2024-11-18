package cartas;

import jugadas.Jugada;
import jugadas.JugadaCambiarColorFicha;

public class CartaCambiarColorFicha extends Carta{
    public CartaCambiarColorFicha(){}
    
    @Override
    protected String getTituloPorDefecto(){
        return "Cambiar el color de una ficha";
    }

    @Override
    public Jugada getJugada(){
        return new JugadaCambiarColorFicha(this);
    }
}
