package cartas;

import jugadas.Jugada;
import jugadas.JugadaPierdeTurno;

public class CartaPierdeTurno extends Carta {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Pierde turno"
     */
	public CartaPierdeTurno() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta
     */
	@Override
	protected String getTituloPorDefecto() {
		return "Pierde turno";
	}
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
	@Override
	public Jugada getJugada() {
		return new JugadaPierdeTurno(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
