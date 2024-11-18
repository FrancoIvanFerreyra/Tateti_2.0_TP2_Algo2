package cartas;

import jugadas.Jugada;
import jugadas.JugadaVolverAtrasJugada;

public class CartaVolverAtrasJugada extends Carta {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public CartaVolverAtrasJugada() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	protected String getTituloPorDefecto() {
		return "Volver Atras Jugada";
	}

	@Override
	public Jugada getJugada() {
		return new JugadaVolverAtrasJugada(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
