package tateti;

import java.awt.Color;

public class RelacionDatoColor<T> {
	private T dato = null;
	private Color color = null;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public RelacionDatoColor() {}
	
	public RelacionDatoColor(Color color, T dato) {
		this.color = color;
		this.dato = dato;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	public T getDato() {
		return this.dato;
	}
	public Color getColor() {
		return this.color;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

    public void setDato(T dato) {
        this.dato = dato;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
