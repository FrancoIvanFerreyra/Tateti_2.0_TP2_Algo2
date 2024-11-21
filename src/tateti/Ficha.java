package tateti;

import interfaces.Bloqueable;
import utiles.ValidacionesUtiles;

public class Ficha implements Bloqueable{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
		
	private final String simbolo;
	private int bloqueosRestantes = 0;
	

//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
    /**
     * Crea una ficha con el símbolo especificado.
     * @param simbolo no puede ser null.
	 * @throws Exception si simbolo es null
     */
	public Ficha(String simbolo) throws Exception {
		ValidacionesUtiles.validarNoNull(simbolo, "simbolo");
		this.simbolo = simbolo;
	}

	
	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	/**
     * @return Una cadena con el símbolo de la ficha.
     */
	@Override
	public String toString() {
		return this.simbolo;
	}
	
	/**
     * @param ficha ficha que se quiere comparar. No debe ser null.
     * @return devuelve verdadero si ambas fichas tienen el mismo símbolo.
	 * @throws Exception si ficha es null
     */
	public boolean esElMismoSimbolo(Ficha ficha) throws Exception{
		ValidacionesUtiles.validarNoNull(ficha, "ficha");
		return getSimbolo().equals(ficha.getSimbolo());
	}
	
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * Incrementa la cantidad de bloqueos restantes de la ficha.
     * @param cantidadDeBloqueos mayor o igual que 1.
     * @throws Exception Si cantidadDeBloqueos es menor a 1.
     */
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		if(cantidadDeBloqueos < 1)
		{
			throw new IllegalArgumentException("Cantidad de bloqueos debe ser mayor o igual que 1");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

    /**
     * Reduce la cantidad de bloqueos restantes de la ficha.
     * @param cantidadDeBloqueos rango entre 1 y getCantidadDeBloqueosRestantes.
     * @throws Exception si cantidadDeBloqueos es menor a 1 o mayor a getCantidadDeBloqueosRestantes.
     */
	@Override
	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws Exception {
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeBloqueos, 1, this.bloqueosRestantes,
												 "cantidadDeBloqueos");
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

    /**
     * @return verdadero si la ficha tiene bloqueos restantes.
     */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}


//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
 	* 
 	* @return devuelve el simbolo de la ficha
 	*/
	public String getSimbolo() {
		return this.simbolo;
	}

    /**
     * @return devuelve la cantidad de bloqueos restantes de la ficha.
     */
	@Override
    public int getBloqueosRestantes() {
        return this.bloqueosRestantes;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}
