package tateti;

public class Ficha {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
		
	private char simbolo;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public Ficha(char simbolo) {
		//validar
		this.simbolo = simbolo;
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
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public char getSimbolo() {
		return simbolo;
	}
	
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
}
