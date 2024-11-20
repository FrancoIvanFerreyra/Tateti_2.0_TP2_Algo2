package tateti;

import interfaces.Bloqueable;
import java.awt.Color;

public class Ficha implements Bloqueable{
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------
	private char simbolo;
	private int bloqueosRestantes = 0;
	private Color color = null;
	
	//CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * Crea una ficha con el símbolo y el color especificados.
     * pre: color != null.
     * @param simbolo El símbolo que representa a la ficha.
     * @param color El color asociado a la ficha.
	 * @throws Exception Si el color que recibe es null
     * pos: Se crea una ficha con el símbolo y el color indicados.
     */
	public Ficha(char simbolo, Color color) throws Exception {
		if (color == null) {
            throw new Exception("El color no puede ser nulo.");
        }
		this.simbolo = simbolo;
		this.color = color;
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
		return getSimbolo() == ficha.getSimbolo();
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
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws Exception{
		if(cantidadDeBloqueos < 1)
		{
			throw new Exception("Cantidad de bloqueos debe ser mayor a 0");
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
	/**
     * Obtiene el símbolo de la ficha.
     * pre: -
     * @return El símbolo de la ficha.
     */
	public char getSimbolo() {
		return simbolo;
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

	/**
     * Obtiene el color de la ficha.
     * pre: -
     * @return El color de la ficha.
     */
	public Color getColor() {
        return color;  // Método para obtener el color de la ficha
    }

	/**
     * Establece el color de la ficha.
     * pre: color no puede ser null.
     * @param color El nuevo color de la ficha.
     * pos: El color de la ficha se actualiza al valor especificado.
     */
	public void setColor(Color color) throws Exception {
		if (color == null) {
            throw new Exception("El color no puede ser nulo.");
        }
		this.color = color;
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}
