package cartas;

import jugadas.Jugada;
import jugadas.JugadaCambiarColorFicha;

public class CartaCambiarColorFicha extends Carta{
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "cambiar el color dfe una ficha"
     */
    public CartaCambiarColorFicha(){}
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Cambiar el color de una ficha"
     */
    @Override
    protected String getTituloPorDefecto(){
        return "Cambiar el color de una ficha";
    }
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
    @Override
    public Jugada getJugada(){
        return new JugadaCambiarColorFicha(this);
    }
}
