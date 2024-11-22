package tateti;

import interfaces.Bloqueable;
import java.util.NoSuchElementException;
import utiles.ValidacionesUtiles;

public class Casillero<T> implements Bloqueable{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	public static int CANTIDAD_DE_VECINOS = 3;
	
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private int x = 0;
	private int y = 0;
	private int z = 0;
	private T dato = null;
	private Casillero<T>[][][] vecinos;
	private int bloqueosRestantes = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * Crea un casillero vacio con coordenadas x, y, z
	 * @param x: 1 o mayor
	 * @param y: 1 o mayor
	 * @param z: 1 o mayor
	 * @throws IllegalArgumentException si alguna coordenada es menor a 1
	 */
	@SuppressWarnings("unchecked")
	public Casillero(int x, int y, int z) throws IllegalArgumentException {
		ValidacionesUtiles.validarEnteroMinimo(x, 1, "x");
		ValidacionesUtiles.validarEnteroMinimo(y, 1, "y");
		ValidacionesUtiles.validarEnteroMinimo(z, 1, "z");

		this.x = x;
		this.y = y;
		this.z = z;
		this.vecinos = new Casillero[CANTIDAD_DE_VECINOS][CANTIDAD_DE_VECINOS][CANTIDAD_DE_VECINOS];
		for(int i = 0; i < this.vecinos.length; i++) {
			for(int j= 0; j < this.vecinos.length; j++) {
				for(int k = 0; k < this.vecinos.length; k++)
				{
					this.vecinos[i][j][k] = null;
				}
			}
		}
		this.vecinos[1][1][1] = this; 
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param i
	 * @return Devuelve la coordenada de vecino opuesta
	 */
	public static int invertirCoordenadaDeVecino(int i) {
		return i * -1;
	}
	
//METODOS GENERALES ---------------------------------------------------------------------------------------
	
	/**
	 * @return Devuelve una cadena con las coordenadas del casillero
	 */
	@Override
	public String toString() {	
		return "Casillero (" + this.x + ", " + this.y + ", " + this.z+ ")";
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	/**
	 * 
	 * @return devuelve verdadero si el casillero tiene un dato
	 */
	public boolean estaOcupado() {
		return this.dato != null;
	}
	
	/**
	 * pre:
	 * @param dato: no puede ser null
	 * @return: devuelve verdadero si el dato es el mismo
	 * @throws NullPointerException si dato es null 
	 */
	public boolean tiene(T dato) throws NullPointerException {
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(this.dato == null)
		{
			return false;
		}
		return this.dato.equals(dato);
	}
	
	/**
	 * pre:
	 * @param movimiento: un movimiento en 3D, no puede ser null
	 * @return devuelve verdadero si existe el casillero vecino en esa direccion o falso si no existe (por ejemplo en el 
	 *         borde)
	 * @throws NullPointerException si movimiento es null 
	 */
	public boolean existeElVecino(Movimiento movimiento) {
		ValidacionesUtiles.validarNoNull(movimiento, "movimiento");
		switch (movimiento) {
			case ADELANTE:
				return this.vecinos[1][2][1] != null;
			
			case ATRAS:
				return this.vecinos[1][0][1] != null;
			
			case IZQUIERDA: 
				return this.vecinos[0][1][1] != null;
			
			case DERECHA:
				return this.vecinos[2][1][1] != null;
			
			case IZQUIERDA_ADELANTE:
				return this.vecinos[0][2][1] != null;
			
			case IZQUIERDA_ATRAS:
				return this.vecinos[0][0][1] != null;
			
			case DERECHA_ADELANTE:
				return this.vecinos[2][2][1] != null;
			
			case DERECHA_ATRAS:
				return this.vecinos[2][0][1] != null;
			
			case ARRIBA: 
				return this.vecinos[1][1][2] != null;
			
			case ABAJO:
				return this.vecinos[1][1][0] != null;
			
			case ADELANTE_ARRIBA:
				return this.vecinos[1][2][2] != null;
			
			case ADELANTE_ABAJO:
				return this.vecinos[1][2][0] != null;
			
			case ATRAS_ARRIBA:
				return this.vecinos[1][0][2] != null;
			
			case ATRAS_ABAJO:
				return this.vecinos[1][0][0] != null;
			
			case IZQUIERDA_ARRIBA:
				return this.vecinos[0][0][2] != null;
			
			case IZQUIERDA_ABAJO:
				return this.vecinos[0][1][0] != null;
			
			case DERECHA_ARRIBA:
				return this.vecinos[2][1][2] != null;
			
			case DERECHA_ABAJO:
				return this.vecinos[2][1][0] != null;
			
			case IZQUIERDA_ADELANTE_ARRIBA:
				return this.vecinos[0][2][2] != null;
			
			case IZQUIERDA_ADELANTE_ABAJO:
				return this.vecinos[0][2][0] != null;
			
			case DERECHA_ADELANTE_ARRIBA:
				return this.vecinos[2][2][2] != null;
			
			case DERECHA_ADELANTE_ABAJO:
				return this.vecinos[2][2][0] != null;
			
			case IZQUIERDA_ATRAS_ARRIBA:
				return this.vecinos[0][0][2] != null;
			
			case IZQUIERDA_ATRAS_ABAJO:
				return this.vecinos[0][0][0] != null;
			
			case DERECHA_ATRAS_ARRIBA:
				return this.vecinos[2][0][2] != null;
			
			case DERECHA_ATRAS_ABAJO:
				return this.vecinos[2][0][0] != null;
			
			default:
				break;		
		}
		return false;
	}
	
	/**
	 * pre:
	 * post: remueve la ficha del casillero
	 */
	public void vaciar() {
		this.setDato(null);		
	}


	/**
	 * Incrementa los bloqueos restantes del casillero
	 * @param cantidadDeBloqueos: mayor o igual a 1
	 * @throws IllegalArgumentException si cantidadDeBloqueos es menor a 1
	 */
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		ValidacionesUtiles.validarEnteroMinimo(cantidadDeBloqueos, 1, "cantidadDeBloqueos");
		this.bloqueosRestantes += cantidadDeBloqueos;
	}


	/**
	 * Reduce los bloqueos restantes del casillero. El casillero debe tener bloqueos restantes
	 * @param cantidadDeBloqueos: debe estar dentro del tango [1, this.cantidadDeBloqueosRestantes]
	 * @throws IllegalArgumentException si cantidadDeBloqueos es menor a 1 o mayor a this.bloqueosRestantes
	 */
	@Override
	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException {
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeBloqueos, 1,
												 this.bloqueosRestantes, "cantidadDeBloqueos");
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

	/**
	 * @return devuelve verdadero si el casillero tiene bloqueos restantes
	 */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @return devuelve la coordenada x del casillero
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return devuelve la coordenada y del casillero
	 */
	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return devuelve la coordenada z del casillero
	 */
	public int getZ() {
		return z;
	}

	/**
	 * 
	 * @return devuelve el dato del casillero
	 */
	public T getDato() {
		return this.dato;
	}

	/**
	 * pre: 
	 * @param x: -1 0 y 1, para indicar izquierda, centro o derecha respectivamente
	 * @param y: -1 0 y 1, para indicar arriba, centro o abajo respectivamente
	 * @param z: -1 0 y 1, para indicar adelante, centro o atras respectivamente
	 * @return devuelve el casillero vecino
	 * @throws IllegalArgumentException si alguna coordenada esta fuera del rango [-1, 1]
	 */
	public Casillero<T> getCasilleroVecino(int x, int y, int z) throws IllegalArgumentException{
		ValidacionesUtiles.validarEnteroEnRango(x, -1, 1, "x");
		ValidacionesUtiles.validarEnteroEnRango(y, -1, 1, "y");
		ValidacionesUtiles.validarEnteroEnRango(z, -1, 1, "z");

		return this.vecinos[x + 1][y + 1][z + 1];
	}
	

	/**
	 * 
	 * @param movimiento movimiento en 3D, no puede ser null
	 * @return devuelve el casillero vecino ubicado donde indique el movimiento
	 * @throws NullPointerException si movimiento es null
	 * @throws NoSuchElementException si no se encontro la posicion vecina
	 */
	public Casillero<T> getCasilleroVecino(Movimiento movimiento) throws NullPointerException,
																	NoSuchElementException {
		ValidacionesUtiles.validarNoNull(movimiento, "movimiento");
		switch (movimiento) {
			case ADELANTE:
				return this.vecinos[1][2][1];
			
			case ATRAS:
				return this.vecinos[1][0][1];
			
			case IZQUIERDA: 
				return this.vecinos[0][1][1];
			
			case DERECHA:
				return this.vecinos[2][1][1];
			
			case IZQUIERDA_ADELANTE:
				return this.vecinos[0][2][1];
			
			case IZQUIERDA_ATRAS:
				return this.vecinos[0][0][1];
			
			case DERECHA_ADELANTE:
				return this.vecinos[2][2][1];
			
			case DERECHA_ATRAS:
				return this.vecinos[2][0][1];
			
			case ARRIBA: 
				return this.vecinos[1][1][2];
			
			case ABAJO:
				return this.vecinos[1][1][0];
			
			case ADELANTE_ARRIBA:
				return this.vecinos[1][2][2];
			
			case ADELANTE_ABAJO:
				return this.vecinos[1][2][0];
			
			case ATRAS_ARRIBA:
				return this.vecinos[1][0][2];
			
			case ATRAS_ABAJO:
				return this.vecinos[1][0][0];
			
			case IZQUIERDA_ARRIBA:
				return this.vecinos[0][0][2];
			
			case IZQUIERDA_ABAJO:
				return this.vecinos[0][1][0];
			
			case DERECHA_ARRIBA:
				return this.vecinos[2][1][2];
			
			case DERECHA_ABAJO:
				return this.vecinos[2][1][0];
			
			case IZQUIERDA_ADELANTE_ARRIBA:
				return this.vecinos[0][2][2];
			
			case IZQUIERDA_ADELANTE_ABAJO:
				return this.vecinos[0][2][0];
			
			case DERECHA_ADELANTE_ARRIBA:
				return this.vecinos[2][2][2];
			
			case DERECHA_ADELANTE_ABAJO:
				return this.vecinos[2][2][0];
			
			case IZQUIERDA_ATRAS_ARRIBA:
				return this.vecinos[0][0][2];
			
			case IZQUIERDA_ATRAS_ABAJO:
				return this.vecinos[0][0][0];
			
			case DERECHA_ATRAS_ARRIBA:
				return this.vecinos[2][0][2];
			
			case DERECHA_ATRAS_ABAJO:
				return this.vecinos[2][0][0];
			
			default:
				break;		
		}
		throw new NoSuchElementException("No se encontro la posicion vecina");
	}
	
	/**
	 * 
	 * @return Devuelve una matriz con los vecinos, y el casillero actual en el centro
	 */
	@SuppressWarnings("unchecked")
	public Casillero<T>[][][] getCasillerosVecinos() {
		Casillero<T>[][][] matriz = new Casillero[CANTIDAD_DE_VECINOS][CANTIDAD_DE_VECINOS][CANTIDAD_DE_VECINOS];
		for (int i = 0; i < CANTIDAD_DE_VECINOS; i++) {
			for (int j = 0; j < CANTIDAD_DE_VECINOS; j++) 
			{
				for(int k = 0; k < CANTIDAD_DE_VECINOS; k++)
				{
					matriz[i][j][k] = this.vecinos[i][j][k];
				}
			}
		}
		return matriz;
	}

	/**
	 * @return deveuelve la cantidad de bloqueos restantes del casillero
	 */
	public int getBloqueosRestantes()
	{
		return this.bloqueosRestantes;
	}
		
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/**
	 * 
	 * Asigna el dato dentro del casillero
	 */
	public void setDato(T dato) {
		this.dato = dato;		
	}
	
	/**
	 * pre:
	 * @param casillero: no puede ser null
	 * @param i: -1 0 y 1, para indicar izquierda, centro o derecha respectivamente
	 * @param j: -1 0 y 1, para indicar arriba, centro o abajo respectivamente
	 * @param k: -1 0 y 1, para indicar adelante, centro o atras respectivamente
	 * @return devuelve el casillero vecino
	 * @throws NullPointerException si casillero es null
	 * @throws IllegalArgumentException si alguna coordenada esta fuera del rango [-1, 1]
	 */
	public void setCasilleroVecino(Casillero<T> casillero, int i, int j, int k) throws NullPointerException,
																				IllegalArgumentException{
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		ValidacionesUtiles.validarEnteroEnRango(i, -1, 1, "i");
		ValidacionesUtiles.validarEnteroEnRango(j, -1, 1, "j");
		ValidacionesUtiles.validarEnteroEnRango(k, -1, 1, "k");

		this.vecinos[i+1][j+1][k+1] = casillero;
	}
}
