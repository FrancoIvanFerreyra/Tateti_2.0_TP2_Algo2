package tateti;

import utiles.ValidacionesUtiles;

public class RelacionDatoCasillero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private T dato = null;
	private Casillero<T> casillero = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	/**
	 * Crea una relacion dato-casillero, con el dato y el casillero que lo contiene
	 * @param casillero no puede ser null y debe contener al dato
	 * @param dato no puede ser null
	 * @throws NullPointerException si casillero o dato son null
	 * @throws IllegalArgumentException si el casillero no contiene al dato
	 */	
	public RelacionDatoCasillero(Casillero<T> casillero, T dato) throws NullPointerException,
																		IllegalArgumentException{
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(!casillero.tiene(dato))
		{
			throw new IllegalArgumentException("El casillero no contiene al dato. No se pueden relacionar");
		}
		this.casillero = casillero;
		this.dato = dato;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/**
	 * 
	 * @return devuelve el dato de la relacion
	 */
	public T getDato() {
		return dato;
	}

	/**
	 * 
	 * @return devuelve el casillero de la relacion
	 */
	public Casillero<T> getCasillero() {
		return casillero;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/**
	 * Actualiza el dato de la relacion
	 * @param dato no puede ser null y el casillero de la relacion debe contenerlo
	 * @throws NullPointerException si dato es null
	 * @throws IllegalArgumentException si el casillero de la relacion no contiene al dato
	 */
	public void setDato(T dato) throws NullPointerException, IllegalArgumentException{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(!this.casillero.tiene(dato))
		{
			throw new IllegalArgumentException("El casillero de esta relacion no contiene al nuevo dato");
		}
		this.dato = dato;
	}

	/**
	 * Actualiza el casillero de la relacion
	 * @param casillero no puede ser null y debe contener al dato de la relacion
	 * @throws NullPointerException si casillero es null
	 * @throws IllegalArgumentException si no contiene al dato de la relacion
	 */
	public void setCasillero(Casillero<T> casillero) throws NullPointerException,
															IllegalArgumentException{
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		if(!casillero.tiene(this.dato))
		{
			throw new IllegalArgumentException("El nuevo casillero no contiene el dato de la relacion");
		}
		this.casillero = casillero;
	}
}
