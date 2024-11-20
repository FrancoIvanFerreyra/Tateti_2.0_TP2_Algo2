package tateti;

import java.awt.Color;
import utiles.ValidacionesUtiles;

public class RelacionDatoColor<T> {
	private T dato = null;
	private Color color = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/*
	pre: color no puede ser null, dato no puede ser null
	pos: crea una instancia intermedia que vincula a un dato con el color
		 que representa
	*/	
	public RelacionDatoColor(Color color, T dato) {
		this.color = color;
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
		return this.dato;
	}
	/*
	 * pre: -
	 * pos: devuelve el color
	 */
	public Color getColor() {
		return this.color;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

	/*
	 * pre: dato no púede ser null
	 * pos: actualiza el dato que representa el color
	 */
    public void setDato(T dato) throws Exception {
		ValidacionesUtiles.validarNoNull(dato, "dato");
        this.dato = dato;
    }
	/*
	 * pre: color no púede ser null
	 * pos: actualiza el color representado por el dato
	 */
    public void setColor(Color color) throws Exception{
		ValidacionesUtiles.validarNoNull(color, "color");
        this.color = color;
    }
}
