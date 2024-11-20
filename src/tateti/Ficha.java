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
	
	@Override
	public String toString() {
		return "" + this.simbolo;
	}
	
	public boolean esElMismoSimbolo(Ficha ficha) {
		return getSimbolo().equals(ficha.getSimbolo());
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		if(cantidadDeBloqueos < 1)
		{
			throw new IllegalArgumentException("Cantidad de bloqueos debe ser mayor a 0");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

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

	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}


//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public String getSimbolo() {
		return this.simbolo;
	}

	@Override
    public int getBloqueosRestantes() {
        return this.bloqueosRestantes;
    }

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	




	
}
