package cartas;

import jugadas.Jugada;
import jugadas.JugadaRetrocederUnaJugada;

public class CartaRetrocederUnaJugada extends Carta {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public CartaRetrocederUnaJugada() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	protected String getTituloPorDefecto() {
		return "Retroceder una jugada";
	}

	@Override
	public Jugada getJugada() {
		return new JugadaRetrocederUnaJugada(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
