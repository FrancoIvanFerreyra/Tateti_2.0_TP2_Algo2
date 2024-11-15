package tateti;

public class Casillero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	public static int CANTIDAD_DE_VECINOS = 3;
	
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private int x = 0;
	private int y = 0;
	private T dato = null;
	private Casillero<T>[][][] vecinos;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * pre:
	 * @param x: 1 o mayor
	 * @param y: 1 o mayor
	 * @throws Exception
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
	
	public static int invertirCoordenadaDeVecino(int i) {
		return i * -1;
	}
	
//METODOS GENERALES ---------------------------------------------------------------------------------------
	
	@Override
	public String toString() {	
		return "Casillero (" + this.x + ", " + this.y + ", " + this.z+ ")";
	}
	
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public boolean estaOcupado() {
		return this.dato != null;
	}
	
	/**
	 * pre:
	 * @param dato: no puede ser vacio
	 * @return: devuelve verdadero si el dato es el mismo
	 * @throws Exception 
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
	 * pre:
	 * @param movimiento: un movimiento en 2d, no puede ser nulo
	 * @return devuelve verdadero si existe el casillero vecino en esa direccion o falso si no existe (por ejemplo en el 
	 *         borde) 
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
	 * post: remueve la ficha del casillero
	 */
	public void vaciar() {
		this.setDato(null);		
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public T getDato() {
		return this.dato;
	}

	/**
	 * pre: 
	 * @param x: -1 0 y 1, para indicar izquierda centro o derecho respectivamente
	 * @param y: -1 0 y 1, para indicar arriba centro o abajo respectivamente
	 * @return devuelve el casilero
	 */
	public Casillero<T> getCasilleroVecino(int x, int y, int z) {
		//validar rangos
		return this.vecinos[x + 1][y + 1][z + 1];
	}
	
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
	 * Devuelve una matriz con los vecinos, y el casillero actual en el centro
	 * @return
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
		
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
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
