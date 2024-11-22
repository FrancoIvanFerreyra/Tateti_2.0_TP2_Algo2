package cartas;

import jugadas.Jugada;
import jugadas.JugadaBloquearFicha;

public class CartaBloquearFicha extends Carta{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Bloquear ficha"
     */
public CartaBloquearFicha() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Bloquear ficha"
     */
	@Override
	protected String getTituloPorDefecto() {
		return "Bloquear ficha";
	}
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
	@Override
	public Jugada getJugada() {
		return new JugadaBloquearFicha(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
