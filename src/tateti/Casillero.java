package tateti;

import interfaces.Bloqueable;


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
	 * pre: recibe las tres coordenas necesarias para poder formar un casillero
	 * @param x: 1 o mayor
	 * @param y: 1 o mayor
	 * @throws Exception
	 * pos: retorna un casillero con sus tres coordenas(x,y,z) y crea una matriz con los vecinos 
	 */
	@SuppressWarnings("unchecked")
	public Casillero(int x, int y, int z) throws Exception {
		if (x < 1) {
			throw new Exception("X debe ser mayor a 0");
		}
		if (y < 1) {
			throw new Exception("Y debe ser mayor a 0");
		}

		if (z < 1) {
			throw new Exception("Z debe ser mayor a 0");
		}
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
		this.vecinos[1][1][1] = this; //definirlo
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
	/*
	 * pre: recibe uan cordenada 
	 * pos: retorna la inversa de esa coordenada
	 */
	public static int invertirCoordenadaDeVecino(int i) {
		return i * -1;
	}
	
//METODOS GENERALES ---------------------------------------------------------------------------------------
	/**
	 * pre: -
	 * pos: retorna un mensaje con las coordenas de un casillero uno al lado del otro
	 */
	@Override
	public String toString() {	
		return "Casillero (" + this.x + ", " + this.y + ", " + this.z+ ")";
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	/*
	 * pre: -
	 * pos: retorna verdadero si el casillero esta ocupado en caso contrario retorna false
	 */
	public boolean estaOcupado() {
		return this.dato != null;
	}
	
	/**
	 * pre: recibe un dato para poder analizar
	 * pos: se retorna true en caso de que el dato ingresado sea el mismo que esta almacenado en el casillero, caso contrario false
	 */
	public boolean tiene(T dato) throws Exception {
		if (dato == null) {
			throw new Exception("El dato no puede ser vacio");
		}
		if(this.dato == null)
		{
			return false;
		}
		return this.dato.equals(dato);
	}
	
	/**
	 * pre: recibie un movimiento 2d, que no puede ser nulo  
	 * pos: devuelve verdadero si existe el casillero vecino en esa direccion o falso si no existe (por ejemplo en el borde) 
	 */
	public boolean existeElVecino(Movimiento movimiento) {
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
	 * post: remueve el objeto almacenado en el casillero
	 */
	public void vaciar() {
		this.setDato(null);		
	}

	/*
	 * pre: recibe un entero que representa la cantidad de bloqueos a incrementar 
	 * pos: el contador de bloqueos del casillero se incrementa la cantidad ingresado, en caso de que sea un valor invalido se lanza un error
	 */
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws Exception{
		if(cantidadDeBloqueos < 1)
		{
			throw new Exception("Cantidad de bloqueos debe ser mayor a 0");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

	/*
	 * pre: recibe un entero que representa la cantidad de bloqueos a disminuir
	 * pos: el contador de bloqueos del casillero disminuye la cantidad ingresa, en caso de que no se pueda disminuir o la cantidad ingresada es mayor a la existente
	 * 		se lanza un error 
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

	/*
	 * pre: -
	 * pos: retorna verdadero si el contado de bloqueos es mayor a 0, en caso false
	 */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	/*
	 * pre:-
	 * pos: retorna la coordenad x del casillero
	 */
	public int getX() {
		return x;
	}
	/*
	 * pre:-
	 * pos: retorna la coordenad y del casillero
	 */
	public int getY() {
		return y;
	}
	/*
	 * pre:-
	 * pos: retorna la coordenad z del casillero
	 */
	public int getZ() {
		return z;
	}
		/*
	 * pre:-
	 * pos: retorna el dato que se alamcena en el casillero
	 */
	public T getDato() {
		return this.dato;
	}

	/**
	 * pre: 
	 * @param x: -1 0 y 1, para indicar izquierda centro o derecho respectivamente
	 * @param y: -1 0 y 1, para indicar arriba centro o abajo respectivamente
	 * @return devuelve el casilero vecino 
	 */
	public Casillero<T> getCasilleroVecino(int x, int y, int z) {
		//validar rangos
		return this.vecinos[x + 1][y + 1][z + 1];
	}
	/*
	 * pre: recibe el tipo de movimiento realizado
	 * pos: devuelve los casilleros vecinos a partir del movimeinto ingresado
	 */
	public Casillero<T> getCasilleroVecino(Movimiento movimiento) throws Exception {
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
		throw new Exception("No se encontro la posicion vecina");
	}
	
	/**
	 * pre: -
	 * pos: Devuelve una matriz con los vecinos, y el casillero actual en el centro
	 */
	@SuppressWarnings("unchecked")
	public Casillero<T>[][][] getCasillerosVecinos() {
		Casillero<T>[][][] matriz = new Casillero[3][3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) 
			{
				for(int k = 0; k < 3; k++)
				{
					matriz[i][j][k] = this.vecinos[i][j][k];
				}
			}
		}
		return matriz;
	}
	/*
	 * pre: -
	 * pos: retonra un entero que representa la cantidad de bloqueos restantes del casillero
	 */
	public int getBloqueosRestantes()
	{
		return this.bloqueosRestantes;
	}
		
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	/*
	 * pre: recibe un dato
	 * pos: se coloca en el casillero el dato ingresado
	 */
	public void setDato(T dato) {
		this.dato = dato;		
	}
	
	/**
	 * pre: 
	 * @param x: -1 0 y 1, para indicar izquierda centro o derecho respectivamente
	 * @param y: -1 0 y 1, para indicar arriba centro o abajo respectivamente
	 * @return devuelve el casilero
	 */
	public void setCasilleroVecino(Casillero<T> casillero, int i, int j, int k) {
		//validar
		this.vecinos[i+1][j+1][k+1] = casillero;
	}
}
