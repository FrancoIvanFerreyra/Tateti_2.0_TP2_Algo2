package cartas;

import jugadas.Jugada;
import jugadas.JugadaBloquearFicha;

public class CartaBloquearFicha extends Carta{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
public CartaBloquearFicha() {}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	protected String getTituloPorDefecto() {
		return "Bloquear ficha";
	}

	@Override
	public Jugada getJugada() {
		return new JugadaBloquearFicha(this);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
