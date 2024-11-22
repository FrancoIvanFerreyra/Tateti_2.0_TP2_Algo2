package tateti;

import java.awt.Color;
import estructuras.Vector;
import utiles.ValidacionesUtiles;
import cartas.Carta;

public class Jugador {

//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------

	private static int idDeJugadorActual = 1;

//ATRIBUTOS -----------------------------------------------------------------------------------------------
	private String nombre;
	private int id;
	private Vector<Ficha> fichas;
	private Vector<Carta> cartas;
	private Color color;
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	/**
	 * pre: recibe un string para el nombre, un entero para la cantidad de fichas del jugador, un color para las fichas y un entero para la cantidad de fichas
	 * pos: retorna un jugador con un nombre, un id unico, un vector con una cierta cantidad de fichas, otro con cierta cantidad de cartas y un colo unico
	 */
	public Jugador(String nombre, int cantidadDeFichas, Color color, int cantidadDeCartas) throws Exception
	{
		this.nombre = nombre;
		this.id = idDeJugadorActual;
		idDeJugadorActual++;
		this.fichas = new Vector<Ficha>(cantidadDeFichas, null);
		this.color = color;
		this.cartas = new Vector<Carta>(cantidadDeCartas, null);
	}
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
	/*
	 * pre: -
	 * pos: muestra el nombre del jugador
	 */
	@Override
	public String toString()
	{
		return "" + this.nombre;
	}

//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/*
	 * pre: -
	 * pos: devuelve true si el jugador pose todos sus fichas posicionadas en el tablero, se verifica gracias al vector de fichas
	 */
	public boolean tieneTodasLasFichasEnElTablero(){
		return this.fichas.contarElementos() == this.fichas.getLongitud();
	}

	/*
	 * pre: -
	 * pos: retorna true si hay alguna ficha posicionada en el tablero, se verifica gracias al vector de fichas
	 */
	public boolean tieneAlgunaFichaEnElTablero()
	{
		return this.fichas.contarElementos() > 0;
	}

	/*
	 * pre: recibe un objeto ficha que se desea agregar al vector de fichas, no puede estar vacio
	 * pos: se ingresa una ficha al vector de fichas del jugador
	 */
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
	/*
	 * pre: recibe un objeto ficha para ser removido del vector de fichas, no puede estar vacio
	 * pos: recorre el vector en busca de la ficha, si la contine la remueve del vector, en caso contrario se muestra un mensaje
	 */
	public void quitarFicha(Ficha ficha) throws Exception
	{
		if(ficha == null)
		{
			throw new Exception("La Ficha no puede ser null");
		}
		if(!this.fichas.contiene(ficha))
		{
			throw new Exception("El jugador no posee esa Ficha");
		}
		int i = 1;
		while(i <= this.fichas.getLongitud())
		{
			if(this.fichas.obtener(i) == ficha)
			{
				this.quitarFicha(i);
				return;
			}
			i++;
		}
	}
	/*
	 * pre: recibe un entero que representa la poscion de una ficha en el vector de fichas, tine que estar en el rango de posicion
	 * pos: se recorre el vector hasta la posicion ingresada, en caso de que exista y tenga una ficha se la remueve
	 */
	public void quitarFicha(int posicion) throws Exception
	{
		if(!ValidacionesUtiles.estaEntre(posicion, 1, this.fichas.getLongitud()))
		{
			throw new Exception("La posicion debe estar entre 1 y " + this.fichas.getLongitud());
		}
		if(this.fichas.obtener(posicion) == null)
		{
			throw new Exception("No existe una ficha en esa posicion");
		}
		this.fichas.remover(posicion);
	}

	/*
	 * pre: recibe un objeto carta que no puede estar vacia y el vector de cartas no este lleno
	 * pos: se agrega al vector la carta ingresado, en caso de que cumpla las condiciones 
	 */
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
	/*
	 * pre: se ingresa un entero que representa la posicion de una carta, debe estar en rango(1 y longitud del vector)
	 * pos: se verifica que en el vector de cartas exista un objeto en esa posicion, en caso de existir se remueve el objeto, caso contrario
	 * 		se muestra un mensaje
	 */
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
	/*
	 * pre: recibe un objeto carta, que no pued eestar vacio 
	 * pos: se verfica que el vector posee la carta ingrsada, si existe se recorre el vector y se la remueve
	 */
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
	/*
	 * pre: recibe un entero que representa una posicion en el vector de fichas, debe estar en rango (0, longitud del vector)
	 * pos: retorna el objeto que se encuentra en la posicion ingresada 
	 */
	public Ficha getFicha(int indice) throws Exception
	{
		if(!ValidacionesUtiles.estaEntre(indice, 0, this.fichas.getLongitud()))
		{
			throw new Exception("El indice de ficha debe estar entre 0 y " + this.fichas.getLongitud());
		}
		return this.fichas.obtener(indice);
	}
	/*
	 * pre: -
	 * pos: retorna un vecto que contiene todas las fichas del jugador
	 */
	public Vector<Ficha> getFichas() throws Exception
	{
		Vector<Ficha> resultado = new Vector<Ficha>(this.fichas.getLongitud(), null);
		for (int i = 1; i <= this.fichas.getLongitud(); i++) {
			resultado.agregar(this.fichas.obtener(i));
		}
		return resultado;
	}
	/*
	 * pre: -
	 * pos: retorna un vector de cartas que posee el jugador
	 */
	public Vector<Carta> getCartas() throws Exception
	{
		Vector<Carta> resultado = new Vector<Carta>(this.cartas.getLongitud(), null);
		for (int i = 1; i <= this.cartas.getLongitud(); i++) {
			resultado.agregar(this.cartas.obtener(i));
		}
		return resultado;		
	}
	/*
	 * pre: -
	 * pos: retorna el nombre del jugador
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	/*
	 * pre: -
	 * pos: retorna el color del jugador
	 */
	public Color getColor()
	{
		return this.color;
	}
	/*
	 * pre: -
	 * pos: retorna el id unico del jugador actual
	 */
	public int getId()
	{
		return this.id;
	}
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
