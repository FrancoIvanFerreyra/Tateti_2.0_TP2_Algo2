package cartas;

import jugadas.Jugada;
import jugadas.JugadaAnularCasillero;

public class CartaAnularCasillero extends Carta{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Anular casillero"
     */
public CartaAnularCasillero() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Anular casillero"
     */
	@Override
	protected String getTituloPorDefecto() {
		return "Anular casillero";
	}
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
	@Override
	public Jugada getJugada() {
		return new JugadaAnularCasillero(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}

