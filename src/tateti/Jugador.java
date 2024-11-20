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
    /**
     * Constructor de la clase Jugador.
     * pre: nombre no puede ser null, cantidadDeFichas debe ser > 0, color no puede ser null, cantidadDeCartas > 0.
     * @param nombre Nombre del jugador.
     * @param cantidadDeFichas Número de fichas asignadas al jugador.
     * @param color Color asociado al jugador.
     * @param cantidadDeCartas Número de cartas asignadas al jugador.
     * @throws Exception Si alguno de los parámetros no cumple con las condiciones preestablecidas.
     * pos: Se crea un jugador con las fichas, cartas y color indicados.
     */
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
	/**
     * Verifica si el jugador tiene todas sus fichas en el tablero.
     * pre: -
     * @return true si todas las fichas están en el tablero, false de lo contrario.
     */
	public boolean tieneTodasLasFichasEnElTablero(){
		return this.fichas.contarElementos() == this.fichas.getLongitud();
	}
	
    /**
     * Verifica si el jugador tiene al menos una ficha en el tablero.
     * pre: -
     * @return true si hay al menos una ficha en el tablero, false de lo contrario.
     */
	public boolean tieneAlgunaFichaEnElTablero()
	{
		return this.fichas.contarElementos() > 0;
	}

    /**
     * Agrega una ficha al jugador.
     * pre: ficha != null, el jugador no tiene todas las fichas asignadas.
     * @param ficha La ficha a agregar.
     * @throws Exception Si la ficha es nula o si ya se alcanzó el máximo de fichas.
     * pos: La ficha se agrega a la lista de fichas del jugador.
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
		ficha.setColor(this.color);
		this.fichas.agregar(ficha);
	}

    /**
     * Agrega una carta al jugador.
     * pre: carta != null, el jugador no tiene todas las cartas asignadas.
     * @param carta La carta a agregar.
     * @throws Exception Si la carta es nula o si ya se alcanzó el máximo de cartas.
     * pos: La carta se agrega a la lista de cartas del jugador.
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

	/**
     * Quita una carta en una posición específica.
     * pre: 1 <= posicion <= longitud de cartas, la posición contiene una carta.
     * @param posicion La posición de la carta a eliminar.
     * @throws Exception Si la posición es inválida o no contiene carta.
     * pos: La carta es removida de la posición indicada.
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

	/**
     * Quita una carta específica.
     * pre: carta != null, el jugador tiene la carta indicada.
     * @param carta La carta a eliminar.
     * @throws Exception Si la carta es nula o no pertenece al jugador.
     * pos: La carta se elimina de la lista de cartas del jugador.
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
    /**
     * Obtiene una ficha en una posición específica.
     * pre: 0 <= indice < longitud de fichas.
     * @param indice Índice de la ficha a obtener.
     * @return La ficha en la posición indicada.
     * @throws Exception Si el índice es inválido.
     */
	public Ficha getFicha(int indice) throws Exception
	{
		if(!ValidacionesUtiles.estaEntre(indice, 0, this.fichas.getLongitud()))
		{
			throw new Exception("El indice de ficha debe estar entre 0 y " + this.fichas.getLongitud());
		}
		return this.fichas.obtener(indice);
	}

	/**
	 * Obtiene todas las fichas asociadas al jugador.
	 * pre: -
	 * @return Un vector con las fichas del jugador. 
	 *         El vector contiene todas las posiciones inicializadas, incluidas las vacías.
	 * @throws Exception Si ocurre algún error al acceder a las fichas.
	 */
	public Vector<Ficha> getFichas() throws Exception
	{
		Vector<Ficha> resultado = new Vector<Ficha>(this.fichas.getLongitud(), null);
		for (int i = 1; i <= this.fichas.getLongitud(); i++) {
			resultado.agregar(this.fichas.obtener(i));
		}
		return resultado;
	}

	/**
	 * Obtiene todas las cartas asociadas al jugador.
	 * pre: -
	 * @return Un vector con las cartas del jugador. 
	 *         El vector contiene todas las posiciones inicializadas, incluidas las vacías.
	 * @throws Exception Si ocurre algún error al acceder a las cartas.
	 */
	public Vector<Carta> getCartas() throws Exception
	{
		Vector<Carta> resultado = new Vector<Carta>(this.cartas.getLongitud(), null);
		for (int i = 1; i <= this.cartas.getLongitud(); i++) {
			resultado.agregar(this.cartas.obtener(i));
		}
		return resultado;		
	}

	/**
	 * Obtiene el nombre del jugador.
	 * pre: -
	 * @return El nombre del jugador como una cadena de caracteres.
	 */
	public String getNombre()
	{
		return this.nombre;
	}

	/**
	 * Obtiene el color asociado al jugador.
	 * pre: -
	 * @return El color del jugador.
	 */
	public Color getColor()
	{
		return this.color;
	}
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
