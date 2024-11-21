package tateti;

import interfaces.Bloqueable;
import utiles.ValidacionesUtiles;

public class Ficha implements Bloqueable{
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

	//ATRIBUTOS -----------------------------------------------------------------------------------------------
	private String simbolo;
	private int bloqueosRestantes = 0;
	
	//CONSTRUCTORES -------------------------------------------------------------------------------------------
	/*
	 * pre: simbolo no debe ser null
	 * post: crea una ficha con su simbolo
	 */
	public Ficha(String simbolo) throws Exception {
		ValidacionesUtiles.validarNoNull(simbolo, "simbolo");
		this.simbolo = simbolo;
	}
	
	//METODOS DE CLASE ----------------------------------------------------------------------------------------

	//METODOS GENERALES ---------------------------------------------------------------------------------------
	/**
     * Convierte el símbolo de la ficha en una cadena.
     * pre: -
     * @return Una cadena con el símbolo de la ficha.
     * pos: El símbolo de la ficha es devuelto como una cadena sin modificar el estado de la ficha.
     */
	@Override
	public String toString() {
		return "" + this.simbolo;
	}
	
	/**
     * Comprueba si el símbolo de otra ficha es igual al de esta ficha.
     * pre: ficha != null.
     * @param ficha La ficha con la que se quiere comparar.
     * @return true si ambas fichas tienen el mismo símbolo, false en caso contrario.
     */
	public boolean esElMismoSimbolo(Ficha ficha) {
		return getSimbolo().equals(ficha.getSimbolo());
	}
	
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * Incrementa la cantidad de bloqueos restantes de la ficha.
     * pre: cantidadDeBloqueos > 0.
     * @param cantidadDeBloqueos La cantidad de bloqueos a agregar.
     * @throws Exception Si la cantidad de bloqueos no es válida.
     * pos: La cantidad de bloqueos restantes de la ficha aumenta en cantidadDeBloqueos.
     */
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		if(cantidadDeBloqueos < 1)
		{
			throw new IllegalArgumentException("Cantidad de bloqueos debe ser mayor a 0");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

    /**
     * Reduce la cantidad de bloqueos restantes de la ficha.
     * pre: cantidadDeBloqueos > 0 && bloqueosRestantes >= cantidadDeBloqueos.
     * @param cantidadDeBloqueos La cantidad de bloqueos a quitar.
     * @throws Exception Si la cantidad de bloqueos no es válida o supera los bloqueos restantes.
     * pos: La cantidad de bloqueos restantes de la ficha disminuye en cantidadDeBloqueos.
     */
	@Override
	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws Exception {
		if(cantidadDeBloqueos <= 0)
		{
			throw new Exception("La cantidad de bloqueos debe ser mayor a 0");
		}
		if(this.bloqueosRestantes - cantidadDeBloqueos < 0)
		{
			throw new Exception("No se pueden quitar " + cantidadDeBloqueos + "bloqueos, quedan " + this.bloqueosRestantes);
		}
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

    /**
     * Verifica si la ficha está bloqueada.
     * pre: -
     * @return true si la ficha tiene bloqueos restantes, false en caso contrario.
     */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}


	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	public String getSimbolo() {
		return this.simbolo;
	}

    /**
     * Obtiene la cantidad de bloqueos restantes de la ficha.
     * pre: -
     * @return La cantidad de bloqueos restantes.
     */
	@Override
    public int getBloqueosRestantes() {
        return this.bloqueosRestantes;
    }
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
