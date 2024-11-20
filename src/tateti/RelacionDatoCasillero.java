package tateti;

import utiles.ValidacionesUtiles;

public class RelacionDatoCasillero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private T dato = null;
	private Casillero<T> casillero = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	/*
	pre: casillero no puede ser null, dato no puede ser null
	pos: crea una instancia intermedia que vincula a un dato con el casillero
			que lo contiene
	*/	
	public RelacionDatoCasillero(Casillero<T> casillero, T dato) {
		this.casillero = casillero;
		this.dato = dato;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/*
	 * pre: -
	 * pos: devuelve el dato
	 */
	public T getDato() {
		return dato;
	}

		/*
	 * pre: -
	 * pos: devuelve el casillero
	 */
	public Casillero<T> getCasillero() {
		return casillero;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/*
	 * pre: dato no púede ser null
	 * pos: actualiza el dato que contiene el casillero
	 */
	public void setDato(T dato) throws Exception{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		this.dato = dato;
	}

		/*
	 * pre: casillero no puede ser null
	 * pos: actualiza el casillero donde se encuentra el dato
	 */
	public void setCasillero(Casillero<T> casillero) throws Exception{
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		this.casillero = casillero;
	}
}
