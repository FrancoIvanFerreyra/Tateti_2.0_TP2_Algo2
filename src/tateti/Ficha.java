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
	
	public Ficha(char simbolo, Color color) {
		//validar
		this.simbolo = simbolo;
		this.color = color;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return "" + this.simbolo;
	}
	
	public boolean esElMismoSimbolo(Ficha ficha) {
		return getSimbolo() == ficha.getSimbolo();
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws Exception{
		if(cantidadDeBloqueos < 1)
		{
			throw new Exception("Cantidad de bloqueos debe ser mayor a 0");
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
	
	public char getSimbolo() {
		return simbolo;
	}

	@Override
    public int getBloqueosRestantes() {
        return this.bloqueosRestantes;
    }
	public Color getColor() {
        return color;  // Método para obtener el color de la ficha
    }
	public void setColor(Color color){
		this.color = color;
	}
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	




	
}
