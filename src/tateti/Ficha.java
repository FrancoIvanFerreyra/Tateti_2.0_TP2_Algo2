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
	/*
	 * pre: -
	 * pos: muesta el sibolo de la ficha
	 */
	@Override
	public String toString() {
		return "" + this.simbolo;
	}
	/*
	 * pre: recibe una ficha
	 * pos: retorna verdadero si el sibolo de ambas fichas son iguales, caso contrario false
	 */
	public boolean esElMismoSimbolo(Ficha ficha) {
		return getSimbolo().equals(ficha.getSimbolo());
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/*
	* pre: recibe un entero que representa la ccantidad de bloques a aumentar, debe ser mayor
	* pos: aumenta el contador de bloquoes ,de la ficha, la cantidad de veces señalada 
	*/
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		if(cantidadDeBloqueos < 1)
		{
			throw new IllegalArgumentException("Cantidad de bloqueos debe ser mayor a 0");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}
	/*
	 * pre: recibe un entero que representa la cantidad de bloques a disminuir, debe ser mayor que 0
	 * pos: disminuye el contador de bloques de la ficha
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

	/*
	 * pre: -
	 * pos: retorna true si el contador de bloqueos es mayor a 0, caso contrario retorna false
	 */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}


//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	/*
	 * pre: -
	 * pos: retorna el simbolo establecido a la ficha
	 */
	public String getSimbolo() {
		return this.simbolo;
	}

	/*
	 * pre: -
	 * pos: devuelve un enetero que representa la cantida de bloqueos restantes para poder ser liberado
	 */
	@Override
    public int getBloqueosRestantes() {
        return this.bloqueosRestantes;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}
