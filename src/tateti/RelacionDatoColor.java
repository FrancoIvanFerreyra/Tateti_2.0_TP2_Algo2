package tateti;

import java.awt.Color;
import utiles.ValidacionesUtiles;

public class RelacionDatoColor<T> {
	private T dato = null;
	private Color color = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * Crea una relacion dato-color
	 * @param color no puede ser null
	 * @param dato no puede ser null
	 * @throws Exception si dato o color son null
	 */	
	public RelacionDatoColor(Color color, T dato) throws Exception{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		ValidacionesUtiles.validarNoNull(color, "color");
		this.color = color;
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
		return this.dato;
	}
	/**
	 * 
	 * @return devuelve el color de la relacion
	 */
	public Color getColor() {
		return this.color;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/**
	 * Actualiza el dato de la relacion
	 * @param dato no puede ser null
	 * @throws Exception si el dato es null
	 */
    public void setDato(T dato) throws Exception {
		ValidacionesUtiles.validarNoNull(dato, "dato");
        this.dato = dato;
    }
	/**
	 * Actualiza el color de la relacion
	 * @param color no puede ser null
	 * @throws Exception si color es null
	 */
    public void setColor(Color color) throws Exception{
		ValidacionesUtiles.validarNoNull(color, "color");
        this.color = color;
    }
}
