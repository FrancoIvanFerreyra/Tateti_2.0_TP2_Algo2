package cartas;

import jugadas.Jugada;
import jugadas.JugadaAnularCasillero;

public class CartaAnularCasillero extends Carta{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
public CartaAnularCasillero() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	protected String getTituloPorDefecto() {
		return "Anular casillero";
	}

	@Override
	public Jugada getJugada() {
		return new JugadaAnularCasillero(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}

