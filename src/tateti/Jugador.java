package tateti;

import java.awt.Color;
import estructuras.Vector;
import utiles.ValidacionesUtiles;
import cartas.Carta;

public class Jugador {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	private String nombre;
	private Vector<Ficha> fichas;
	private Vector<Carta> cartas;
	private Color color;
//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Jugador(String nombre, int cantidadDeFichas, Color color, int cantidadDeCartas) throws Exception
	{
		this.nombre = nombre;
		this.fichas = new Vector<Ficha>(cantidadDeFichas, null);
		this.color = color;
		this.cartas = new Vector<Carta>(cantidadDeCartas, null);
	}
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

	@Override
	public String toString()
	{
		return "" + this.nombre;
	}

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public boolean tieneTodasLasFichasEnElTablero(){
		return this.fichas.contarElementos() == this.fichas.getLongitud();
	}

	public boolean tieneAlgunaFichaEnElTablero()
	{
		return this.fichas.contarElementos() > 0;
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

	public void agregarCarta(Carta carta) throws Exception
	{
		if(carta == null)
		{
			throw new Exception("La carta no puede ser null");
		}
		if(this.cartas.contarElementos() >= this.cartas.getLongitud())
		{
			throw new IllegalStateException("Se agrego el maximo de cartas (" + this.cartas.getLongitud() + ")");
		}
		this.cartas.agregar(carta);	
	}

	public void quitarCarta(int posicion) throws Exception
	{
		if(!ValidacionesUtiles.estaEntre(posicion, 1, this.cartas.getLongitud()))
		{
			throw new Exception("La posicion debe estar entre 1 y " + this.cartas.getLongitud());
		}
		if(this.cartas.obtener(posicion) == null)
		{
			throw new Exception("No existe una carta en esa posicion");
		}
		this.cartas.remover(posicion);
	}

	public void quitarCarta(Carta carta) throws Exception
	{
		if(carta == null)
		{
			throw new Exception("La carta no puede ser null");
		}
		if(!this.cartas.contiene(carta))
		{
			throw new Exception("El jugador no posee esa carta");
		}
		int i = 1;
		while(i <= this.cartas.getLongitud())
		{
			if(this.cartas.obtener(i) == carta)
			{
				this.quitarCarta(i);
				return;
			}
			i++;
		}
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

	public Vector<Carta> getCartas() throws Exception
	{
		Vector<Carta> resultado = new Vector<Carta>(this.cartas.getLongitud(), null);
		for (int i = 1; i <= this.cartas.getLongitud(); i++) {
			resultado.agregar(this.cartas.obtener(i));
		}
		return resultado;		
	}

	public String getNombre()
	{
		return this.nombre;
	}

	public Color getColor()
	{
		return this.color;
	}
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
