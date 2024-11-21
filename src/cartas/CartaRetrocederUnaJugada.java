package cartas;

import jugadas.Jugada;
import jugadas.JugadaRetrocederUnaJugada;

public class CartaRetrocederUnaJugada extends Carta {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Retroceder una jugada"
     */
	public CartaRetrocederUnaJugada() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Retroceder una jugada"
     */
	@Override
	protected String getTituloPorDefecto() {
		return "Retroceder una jugada";
	}
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
	@Override
	public Jugada getJugada() {
		return new JugadaRetrocederUnaJugada(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
