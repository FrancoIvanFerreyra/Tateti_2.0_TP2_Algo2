package cartas;

import jugadas.Jugada;
import jugadas.JugadaPierdeTurno;

public class CartaPierdeTurno extends Carta {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public CartaPierdeTurno() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	protected String getTituloPorDefecto() {
		return "Pierde turno";
	}

	@Override
	public Jugada getJugada() {
		return new JugadaPierdeTurno(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
