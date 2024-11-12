package tateti;

import estructuras.Vector;
import utiles.ValidacionesUtiles;

public class Jugador {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	private String nombre;
	private Vector<Ficha> fichas;
//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Jugador(String nombre, int cantidadDeFichas) throws Exception
	{
		this.nombre = nombre;
		this.fichas = new Vector<Ficha>(cantidadDeFichas, null);
	}
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

	@Override
	public String toString()
	{
		return "" + this.nombre;
	}

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public boolean tieneTodasLasFichasEnElTablero(Tablero<Ficha> tablero) throws Exception{
		boolean resultado = true;
		int i = 1;
		while(resultado && i <= fichas.getLongitud())
		{
			if(fichas.obtener(i) == null)
			{
				return false;
			}
			i++;
		}
		return true;
	}

	public void agregarFicha(Ficha ficha) throws Exception
	{
		if(ficha == null)
		{
			throw new Exception("La ficha no puede ser null");
		}
		if(this.fichas.contarElementos() >= this.fichas.getLongitud())
		{
			throw new Exception("Se agrego el maximo de fichas (" + this.fichas.getLongitud() + ")");
		}
		this.fichas.agregar(ficha);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	public Ficha getFicha(int indice) throws Exception
	{
		if(!ValidacionesUtiles.estaEntre(indice, 0, this.fichas.getLongitud()))
		{
			throw new Exception("El indice de ficha debe estar entre 0 y " + this.fichas.getLongitud());
		}
		return this.fichas.obtener(indice);
	}

	public Vector<Ficha> getFichas() throws Exception
	{
		Vector<Ficha> resultado = new Vector<Ficha>(this.fichas.getLongitud(), null);
		for (int i = 1; i <= this.fichas.getLongitud(); i++) {
			resultado.agregar(this.fichas.obtener(i));
		}
		return resultado;
	}

	public String getNombre()
	{
		return this.nombre;
	}
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
