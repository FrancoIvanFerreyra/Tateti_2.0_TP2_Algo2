package tateti;

import cartas.Carta;
import estructuras.Vector;
import java.awt.Color;
import java.util.NoSuchElementException;
import utiles.ValidacionesUtiles;

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
     * Crea un nuevo Jugador con su nombre y color, y sus cantidades maximas de fichas y cartas.
     * @param nombre no puede ser null ni ser vacio
     * @param cantidadDeFichas mayor o igual que 1.
     * @param color no puede ser null.
     * @param cantidadDeCartas mayor o igual a 1.
     * @throws NullPointerException si nombre es null o color es null
	 * @throws IllegalArgumentException si nombre es vacio o si cantidadDeFichas o cantidadDeCartas
	 * 									son menores a 1
     */
	public Jugador(String nombre, int cantidadDeFichas, Color color, int cantidadDeCartas) 
																throws NullPointerException,
																IllegalArgumentException
	{
		ValidacionesUtiles.validarNoNull(nombre, "nombre");
		if(nombre.length() == 0)
		{
			throw new IllegalArgumentException("El nombre no puede estar vacio");
		}
		ValidacionesUtiles.validarNoNull(color, "color");
		ValidacionesUtiles.validarEnteroMinimo(cantidadDeFichas, 1, "cantidadDeFichas");
		ValidacionesUtiles.validarEnteroMinimo(cantidadDeCartas, 1, "cantidadDeCartas");
		
		this.nombre = nombre;
		this.id = idDeJugadorActual;
		idDeJugadorActual++;
		this.fichas = new Vector<>(cantidadDeFichas, null);
		this.color = color;
		this.cartas = new Vector<>(cantidadDeCartas, null);
	}
	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------

	
	/**
	 * @return devuelve el nombre del jugador
	 */
	@Override
	public String toString()
	{
		return this.nombre;
	}

	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/**
	 * @param tablero no puede ser null
	 * @return devuelve verdadero si el tablero tiene la cantidad maxima de fichas del jugador
	 * @throws NullPointerException si tablero es null
	 */
	public boolean tieneTodasLasFichasEnElTablero(Tablero<Ficha> tablero) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(tablero, "tablero");
		if(this.fichas.contarElementos() < this.fichas.getLongitud())
		{
			return false;
		}
		for(int i = 1; i <= this.fichas.getLongitud(); i++)
		{
			if(!tablero.contiene(this.fichas.obtener(i)))
			{
				return false;
			}
		}
		return true;
	}
	
    /**
	 * @param tablero no puede ser null
	 * @return devuelve verdadero si el tablero tiene al menos una ficha del jugador
	 * @throws NullPointerException si tablero es null
	 */
	public boolean tieneAlgunaFichaEnElTablero(Tablero<Ficha> tablero) throws NullPointerException
	{
		ValidacionesUtiles.validarNoNull(tablero, "tablero");
		if(this.fichas.contarElementos() == 0)
		{
			//Falso si el jugador no tiene fichas
			return false;
		}
		for(int i = 1; i <= this.fichas.contarElementos(); i++)
		{
			if(tablero.contiene(this.fichas.obtener(i)))
			{
				return true;
			}
		}
		return false;
	}

    /**
     * Agrega una ficha al jugador.
     * @param ficha no puede ser null.
     * @throws NullPointerException Si la ficha es null
	 * @throws IllegalArgumentException si el jugador ya tiene el máximo de fichas.
     */
	public void agregarFicha(Ficha ficha) throws NullPointerException, IllegalArgumentException
	{
		ValidacionesUtiles.validarNoNull(ficha, "ficha");
		if(this.fichas.contarElementos() >= this.fichas.getLongitud())
		{
			throw new IllegalArgumentException("Se agrego el maximo de fichas (" + this.fichas.getLongitud() + ")");
		}
		this.fichas.agregar(ficha);
	}


	/**
	 * Quita una ficha especifica al jugador
	 * @param ficha no debe ser null, y el jugador debe tenerla
	 * @throws NullPointerException si ficha es null
	 * @throws NoSuchElementException si el jugador no tiene la ficha
	 */
	public void quitarFicha(Ficha ficha) throws NullPointerException, NoSuchElementException
	{
		ValidacionesUtiles.validarNoNull(ficha, "ficha");
		if(!this.fichas.contiene(ficha))
		{
			throw new NoSuchElementException("El jugador no posee esa ficha");
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

	/**
	 * Quita la ficha del jugador en una posicion especifica
	 * @param posicion rango entre [1, contarElementos()], el jugador debe poseer al menos una ficha,
	 * 				   debe existir la ficha en la posicion 
	 * @throws IllegalArgumentException si jugador no tiene fichas
	 * @throws NoSuchElementException si posicion no esta en el rango [1, contarElementos()]
	 */
	public void quitarFicha(int posicion) throws IllegalArgumentException, NoSuchElementException
	{
		if(this.fichas.contarElementos() < 1)
		{
			throw new IllegalArgumentException("El jugador no tiene fichas");
		}
		ValidacionesUtiles.validarEnteroEnRango(posicion, 1,
												 this.fichas.contarElementos(), "posicion");
		if(this.fichas.obtener(posicion) == null)
		{
			throw new NoSuchElementException("No existe una ficha en esa posicion");
		}
		this.fichas.remover(posicion);
	}

    /**
	 * Agrega una carta al jugador
	 * @param carta no puede ser null
	 * @throws NullPointerException si carta es null
	 * @throws IllegalArgumentException si el jugador ya tiene el maximo de cartas
	 */

	public void agregarCarta(Carta carta) throws NullPointerException, IllegalArgumentException
	{
		ValidacionesUtiles.validarNoNull(carta, "carta");
		if(this.cartas.contarElementos() >= this.cartas.getLongitud())
		{
			throw new IllegalArgumentException("Se agrego el maximo de cartas (" + this.cartas.getLongitud() + ")");
		}
		this.cartas.agregar(carta);	
	}

	/**
	 * Quita una carta del jugador en una posicion especifica
	 * @param posicion debe estar en rango [1, this.cartas.contarElementos()], el jugador debe tener
	 * 				   al menos una carta, la carta debe existir en posicion
	 * @throws IllegalArgumentException si posicion no esta en rango [1, this.cartas.contarElementos()]
	 * 									o si el jugador no tiene cartas
	 * @throws NoSuchElementException si no existe la carta en posicion
	 */
	public void quitarCarta(int posicion) throws IllegalArgumentException, NoSuchElementException
	{
		if(this.cartas.contarElementos() < 1)
		{
			throw new IllegalArgumentException("El jugador no tiene cartas");
		}
		ValidacionesUtiles.validarEnteroEnRango(posicion, 1,
												 this.cartas.contarElementos(), "posicion");
		if(this.cartas.obtener(posicion) == null)
		{
			throw new NoSuchElementException("No existe una carta en esa posicion");
		}
		this.cartas.remover(posicion);
	}

	/**
	 * Quita una carta especifica del jugador
	 * @param carta no debe ser null, y el jugador debe tenerla
	 * @throws NullPointerException si carta es null
	 * @throws NoSuchElementException si el jugador no tiene la carta
	 */
	public void quitarCarta(Carta carta) throws NullPointerException, NoSuchElementException
	{
		ValidacionesUtiles.validarNoNull(carta, "carta");
		if(!this.cartas.contiene(carta))
		{
			throw new NoSuchElementException("El jugador no posee esa carta");
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
    /**
     * Obtiene una ficha del jugador en una posición específica.
     * @param indice debe estar en rango [1, this.fichas.getLongitud()].
     * @return Devuelve la ficha del jugador en la posición indicada.
     * @throws IllegalArgumentException si el índice no esta en rango [1, this.fichas.getLongitud()]
     */
	public Ficha getFicha(int indice) throws IllegalArgumentException
	{
		ValidacionesUtiles.validarEnteroEnRango(indice, 1,
												 this.fichas.getLongitud(), "indice");
		return this.fichas.obtener(indice);
	}

	/**
	 * 
	 * @return Devuelve un vector de tamanio igual a la cantidad maxima de fichas,
	 * 		   que contiene tanto las fichas del jugador como los espacios vacios.
	 */
	public Vector<Ficha> getFichas()
	{
		Vector<Ficha> resultado = new Vector<>(this.fichas.getLongitud(), null);
		for (int i = 1; i <= this.fichas.getLongitud(); i++) {
			resultado.agregar(this.fichas.obtener(i));
		}
		return resultado;
	}
	
	

	/**
	 * @return Devuelve un vector de tamanio igual a la cantidad maxima de cartas,
	 * 		   que contiene tanto las cartas del jugador como los espacios vacios.
	 * */
	public Vector<Carta> getCartas()
	{
		Vector<Carta> resultado = new Vector<>(this.cartas.getLongitud(), null);
		for (int i = 1; i <= this.cartas.getLongitud(); i++) {
			resultado.agregar(this.cartas.obtener(i));
		}
		return resultado;
	}
	

	/**
	 * @return Devuelve el nombre del jugador como una cadena de caracteres.
	 */
	public String getNombre()
	{
		return this.nombre;
	}

	/**
	 * @return Devuelve el color del jugador.
	 */
	public Color getColor()
	{
		return this.color;
	}

	/**
	 * 
	 * @return Devuelve el id del jugador
	 */
	public int getId()
	{
		return this.id;
	}
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
