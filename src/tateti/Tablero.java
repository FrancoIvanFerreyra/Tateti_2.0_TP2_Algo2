package tateti;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import java.awt.Color;
import utiles.ValidacionesUtiles;

public class Tablero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Lista<RelacionDatoCasillero<T>> posicionDeLosDatos = null;
	private Lista<RelacionDatoColor<T>> coloresDeLosDatos = null;
	private Lista<Casillero<T>> casillerosBloqueados = null;
	private Lista<Lista<Lista<Casillero<T>>>> tablero = null;
	private int ancho = 0;
	private int alto = 0;
	private int profundidad = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * 
	 * Crea un tablero de dimensiones ancho x alto x profundidad
	 * contando de 1 a (dimension) inclusive. Posee ancho creciente hacia la derecha,
	 * alto creciente hacia arriba y profundidad creciente hacia adelante.
	 * Crea listas auxiliares para almacenar posiciones de los datos, colores de los datos y
	 * casilleros bloqueados
	 *
	 * @param ancho mayor o igual a 1
	 * @param alto mayor o igual a 1
	 * @param profundidad mayor o igual a 1
	 * @throws Exception si alguna dimension es menor a 1
	 */
	public Tablero(int ancho, int alto, int profundidad) throws Exception {
		ValidacionesUtiles.validarEnteroMinimo(ancho, 1, "ancho");
		ValidacionesUtiles.validarEnteroMinimo(alto, 1, "alto");
		ValidacionesUtiles.validarEnteroMinimo(profundidad, 1, "profundidad");

		this.ancho = ancho;
		this.alto = alto;
		this.profundidad = profundidad;
		this.tablero = new ListaSimplementeEnlazada<>();
		this.posicionDeLosDatos = new ListaSimplementeEnlazada<>();
		this.coloresDeLosDatos = new ListaSimplementeEnlazada<>();
		this.casillerosBloqueados = new ListaSimplementeEnlazada<>();

		for( int i = 1; i <= ancho; i++) {
			Lista<Lista<Casillero<T>>> fila = new ListaSimplementeEnlazada<>();
			this.tablero.agregar(fila);
			for(int j = 1; j <= alto; j++) {
				Lista<Casillero<T>> columna = new ListaSimplementeEnlazada<>();
				fila.agregar(columna);
				for(int k = 1; k <= profundidad; k++)
				{
					Casillero<T> nuevoCasillero = new Casillero<>(i, j, k);
					columna.agregar(nuevoCasillero);

					//Se relacionan los casilleros vecinos a medida que se van creando
					for(int l = -1; l <= 1; l++) {
						for(int m = -1; m <= 1; m++)
						{
							if (this.existeElCasillero(i - 1, j + l, k + m)) {
								relacionarCasillerosVecinos(this.getCasillero(i - 1, j + l, k + m), nuevoCasillero, -1, l, m);					
							}
						}
					}
					for(int l = -1; l <= 0; l++)
					{
						if (this.existeElCasillero(i, j + l, k-1)) {
							relacionarCasillerosVecinos(this.getCasillero(i, j  + l, k - 1), nuevoCasillero, 0, l, -1);
						}
					}
					for(int l = -1; l <= 0; l++)
					{
						if (l != 0 && this.existeElCasillero(i, j + l, k)) {
							relacionarCasillerosVecinos(this.getCasillero(i, j + l, k), nuevoCasillero, 0, l, 0);
						}
					}
				}
			}
		}
	}

	/**
	 * Relaciona los dos vecinos en sus matrices de vecinos, en el casillero1 como i, j y k,
	 * y en casillero2 como el opuesto
	 * @param casillero1: no puede ser null
	 * @param casillero2: no puede ser null
	 * @param i: rango entre -1, 0 y 1
	 * @param j: rango entre -1, 0 y 1
	 * @param k: rango entre -1, 0 y 1
	 * @throws Exception si algun casillero es null, o si i, j, o k no estan en el rango [-1, 0, 1]
	 */
	public final void relacionarCasillerosVecinos(Casillero<T> casillero1, Casillero<T> casillero2,
													 int i, int j, int k) throws Exception {
		ValidacionesUtiles.validarNoNull(casillero1, "casillero1");
		ValidacionesUtiles.validarNoNull(casillero2, "casillero2");
		ValidacionesUtiles.validarEnteroEnRango(i, -1, 1, "i");
		ValidacionesUtiles.validarEnteroEnRango(j, -1, 1, "j");
		ValidacionesUtiles.validarEnteroEnRango(k, -1, 1, "k");

		casillero2.setCasilleroVecino(casillero1, i, j, k);
		casillero1.setCasilleroVecino(casillero2, Casillero.invertirCoordenadaDeVecino(i), Casillero.invertirCoordenadaDeVecino(j), Casillero.invertirCoordenadaDeVecino(k));
	}


	/**
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @return devuelve verdadero si existe el casillero(x, y, z) actualmente en el tablero.
	 * El tamaño del tablero puede no ser el maximo establecido en el constructor
	 */
	public final boolean existeElCasillero(int i, int j, int k) {

		if(ValidacionesUtiles.esMenorQue(i, 1) ||
			ValidacionesUtiles.esMenorQue(j, 1) ||
			ValidacionesUtiles.esMenorQue(k, 1))
			{
				return false;
			}
			try {
				int anchoActual = this.tablero.getTamanio();
				int altoActual = this.tablero.obtener(i).getTamanio();
				int profundidadActual = this.tablero.obtener(i).obtener(j).getTamanio();

				if (ValidacionesUtiles.esMayorQue(i, anchoActual) ||
				ValidacionesUtiles.esMayorQue(j, altoActual) ||
				ValidacionesUtiles.esMayorQue(k, profundidadActual)) 
				{
					return false;
				}
			} catch (Exception e) {
				//Algun indice esta fuera de rango, por lo que no existe el casillero
				return false;
			}

		return true;
	}

	/**
	 * Agrega el dato al casillero (x, y, z) del tablero
	 * @param x
	 * @param y
	 * @param z
	 * @param dato no puede ser null
	 * @throws Exception si dato es null o si el casillero (x, y, z) no existe
	 */
	public void agregar(int x, int y, int z,  T dato) throws Exception {
		if(!this.existeElCasillero(x, y, z))
		{
			throw new Exception("No existe casillero en " + x + ", " + y + ", " + z);
		}
		ValidacionesUtiles.validarNoNull(dato, "dato");
		Casillero<T> casillero = getCasillero(x, y, z);
		casillero.setDato(dato);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return devuelve el casillero (x, y, z) del tablero
	 * @throws Exception si el casillero (x, y, z) no existe
	 */
	public final Casillero<T> getCasillero(int x, int y, int z) throws Exception {
		if(!existeElCasillero(x, y, z))
		{
			throw new Exception("No existe casillero en " + x + ", " + y + ", " + z);
		}
		return this.tablero.obtener(x).obtener(y).obtener(z);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return devuelve el dato que hay en el casillero
	 * @throws Exception si el casillero (x, y, z) no existe
	 */
	public T obtener(int x, int y, int z) throws Exception {
		if(!existeElCasillero(x, y, z))
		{
			throw new Exception("No existe casillero en " + x + ", " + y + ", " + z);
		}
		return getCasillero(x, y, z).getDato();
	}

	/**
	 * pre:
	 * @param dato no puede ser null
	 * @return: devuelve el casillero que contiene el dato
	 * @throws Exception si no se encontro el dato en el tablero
	 */
	public Casillero<T> getCasillero(T dato) throws Exception {
		ValidacionesUtiles.validarNoNull(dato, "dato");
		this.posicionDeLosDatos.iniciarCursor();
		while(this.posicionDeLosDatos.avanzarCursor()) {
			Casillero<T> casillero = this.posicionDeLosDatos.obtenerCursor().getCasillero();
			if (casillero.tiene(dato)) {
				return casillero;
			}
		}
		throw new Exception("No se encontro el dato");
	}

	/**
	 * @param dato no puede ser null
	 * @return: devuelve el color representado por el dato
	 * @throws Exception si el dato es null o si no se encontro el dato en el tablero
	 */
	public Color getColorDato(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		this.coloresDeLosDatos.iniciarCursor();
		while(this.coloresDeLosDatos.avanzarCursor())
		{
			if(this.coloresDeLosDatos.obtenerCursor().getDato().equals(dato))
			{
				return this.coloresDeLosDatos.obtenerCursor().getColor();
			}
		}
		throw new Exception("No se encontro el dato en el tablero");
	}
	
	/**
	 * @param casillero no puede ser null y debe tener un dato
	 * @param casilleroVecino no puede ser null y debe estar vacio
	 * @return mueve el dato contenido en casillero a casilleroVecino
	 * @throws Exception si algun casillero es null, si casillero esta vacio o
	 * 			si casilleroVecino esta ocupado
	 */
	public void mover(Casillero<T> casillero, Casillero<T> casilleroVecino) throws Exception{
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		ValidacionesUtiles.validarNoNull(casilleroVecino, "casilleroVecino");
		if(!casillero.estaOcupado())
		{
			throw new Exception("El casillero no tiene dato que mover");
		}
		if(casilleroVecino.estaOcupado())
		{
			throw new Exception("El casillero vecino esta ocupado");
		}
		T dato = casillero.getDato();
		casilleroVecino.setDato(dato);
		casillero.vaciar();
		actualizarRelacionDatoCasillero(dato, casilleroVecino);
	}

	/**
	 * 
	 * @param dato no puede ser null
	 * @return verdadero si el tablero contiene al dato en algun casillero
	 * @throws Exception si dato es null
	 */
	public boolean contiene(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		getPosicionDeLosDatos().iniciarCursor();
		while(getPosicionDeLosDatos().avanzarCursor())
		{
			RelacionDatoCasillero<T> relacionDatoCasillero = getPosicionDeLosDatos().obtenerCursor();
			if(relacionDatoCasillero.getDato().equals(dato))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param dato no puede ser null y debe estar en el tablero
	 * @return una lista de movimientos posibles del dato, desde su casillero hacia los vecinos.
	 * 		   Si no tiene movimientos devuelve una lista vacia
	 * @throws Exception si dato es null o si no esta en el tablero
	 */
	public Lista<Movimiento> obtenerMovimientosPosibles(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(!this.contiene(dato))
		{
			throw new Exception("El tablero no contiene al dato");
		}
		Casillero<T> casillero = this.getCasillero(dato);
		Lista<Movimiento> movimientosPosibles = new ListaSimplementeEnlazada<>();
		for(Movimiento movimiento : Movimiento.values())
		{
			if (casillero.existeElVecino(movimiento)
			 && !casillero.getCasilleroVecino(movimiento).estaOcupado()
			 && !casillero.getCasilleroVecino(movimiento).estaBloqueado())
			 {
				movimientosPosibles.agregar(movimiento);
			 }
		}
		return movimientosPosibles;
	}

	/**
	 * 
	 * @param dato no puede ser null y debe estar en el tablero
	 * @return verdadero si el dato tiene al menos un movimiento disponible
	 * 		   desde su casillero hacia algun vecino
	 * @throws Exception si dato es null o si no esta en el tablero
	 */
	public boolean tieneMovimientosPosibles(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(!this.contiene(dato))
		{
			throw new Exception("El tablero no contiene al dato");
		}
		Casillero<T> casillero = this.getCasillero(dato);
		for(Movimiento movimiento : Movimiento.values())
		{
			if (casillero.existeElVecino(movimiento)
			 && !casillero.getCasilleroVecino(movimiento).estaOcupado()
			 && !casillero.getCasilleroVecino(movimiento).estaBloqueado())
			 {
				return true;
			 }
		}
		return false;
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------

@Override
public String toString(){
	return "Dimensiones del tablero: \n ancho: " + this.ancho + "\n" +
	"alto: " + this.alto + "\n" + 
	"profundidad: " + this.profundidad + "\n";
}
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------


	/**
 	* 
 	* @return devuelve el ancho del tablero
 	*/
	public int getAncho() {
		return this.ancho;
	}

	/**
 	* 
 	* @return devuelve el alto del tablero
 	*/
	public int getAlto() {
		return this.alto;
	}

	/**
 	* 
 	* @return devuelve la profundidad del tablero
 	*/
	public int getProfundidad() {
		return this.profundidad;
	}

	/**
 	* 
 	* @return devuelve la lista de relaciones dato-casillero
 	*/
	public Lista<RelacionDatoCasillero<T>> getPosicionDeLosDatos()
	{
		return this.posicionDeLosDatos;
	}

	/**
 	* 
 	* @return devuelve la lista de relaciones dato-color
 	*/
	public Lista<RelacionDatoColor<T>> getColoresDeLosDatos()
	{
		return this.coloresDeLosDatos;
	}

	/**
 	* 
 	* @return devuelve la lista de casilleros bloqueados del tablero
 	*/
	public Lista<Casillero<T>> getCasillerosBloqueados()
	{
		return this.casillerosBloqueados;
	}

	/**
	 * Actualiza la relacion dato-casillero correspondiente a dato con el nuevo casillero.
	 * Si no existe la relacion para actualizar, crea una nueva
	 * @param dato no puede ser null
	 * @param casillero no puede ser null y debe contener dato
	 * @throws Exception si dato y/o casillero son null, o si casillero no contiene al dato 
	 */
	public void actualizarRelacionDatoCasillero(T dato, Casillero<T> casillero) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		ValidacionesUtiles.validarNoNull(casillero, "casillero");
		if(!casillero.tiene(dato))
		{
			throw new Exception("El casillero no contiene el dato");
		}
		getPosicionDeLosDatos().iniciarCursor();
		while(getPosicionDeLosDatos().avanzarCursor())
		{
			RelacionDatoCasillero<T> relacionDatoCasillero = getPosicionDeLosDatos().obtenerCursor();
			if(relacionDatoCasillero.getDato().equals(dato))
			{
				relacionDatoCasillero.setCasillero(casillero);
				return;
			}
		}
		this.posicionDeLosDatos.agregar(new RelacionDatoCasillero<>(casillero, dato));
	}

	/**
	 * Elimina la relacion dato-casillero correspondiente a dato
	 * @param dato no puede ser null, el tablero no debe contenerlo y debe existir una
	 * 			   relacion dato-casillero con dato igual a dato
	 * @throws Exception si dato es null, si el tablero contiene al dato, 
	 * 					o si no existe relacion dato-casillero con dato igual a dato
	 */
	public void eliminarRelacionDatoCasillero(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(this.contiene(dato))
		{
			throw new Exception("El tablero contiene al dato. Relacion no se puede eliminar");
		}

		getPosicionDeLosDatos().iniciarCursor();
		int i = 1;
		while(getPosicionDeLosDatos().avanzarCursor())
		{
			RelacionDatoCasillero<T> relacionDatoCasillero = getPosicionDeLosDatos().obtenerCursor();
			if(relacionDatoCasillero.getDato().equals(dato))
			{
				getPosicionDeLosDatos().remover(i);
				return;
			}
			i++;
		}
		throw new Exception("No existe relacion datoCasillero para este dato");
	}

	/**
	 * Actualiza la relacion dato-color correspondiente a dato con el nuevo color.
	 * Si no existe la relacion para actualizar, crea una nueva
	 * @param dato no puede ser null
	 * @param color no puede ser null
	 * @throws Exception si dato y/o casillero son null 
	 */
	public void actualizarRelacionDatoColor(T dato, Color color) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		ValidacionesUtiles.validarNoNull(color, "color");

		getColoresDeLosDatos().iniciarCursor();
		while(getColoresDeLosDatos().avanzarCursor())
		{
			RelacionDatoColor<T> relacionDatoColor = getColoresDeLosDatos().obtenerCursor();
			if(relacionDatoColor.getDato().equals(dato))
			{
				relacionDatoColor.setColor(color);
				return;
			}
		}
		this.coloresDeLosDatos.agregar(new RelacionDatoColor<>(color, dato));
	}

	/**
	 * Elimina la relacion dato-color correspondiente a dato
	 * @param dato no puede ser null, el tablero no debe contenerlo y debe existir una
	 * 			   relacion dato-color con dato igual a dato
	 * @throws Exception si dato es null, si el tablero contiene al dato, 
	 * 					o si no existe relacion dato-color con dato igual a dato
	 */
	public void eliminarRelacionDatoColor(T dato) throws Exception
	{
		ValidacionesUtiles.validarNoNull(dato, "dato");
		if(this.contiene(dato))
		{
			throw new Exception("El tablero contiene al dato. Relacion no se puede eliminar");
		}

		getColoresDeLosDatos().iniciarCursor();
		int i = 1;
		while(getColoresDeLosDatos().avanzarCursor())
		{
			RelacionDatoColor<T> relacionDatoColor = getColoresDeLosDatos().obtenerCursor();
			if(relacionDatoColor.getDato().equals(dato))
			{
				getColoresDeLosDatos().remover(i);
				return;
			}
			i++;
		}
		throw new Exception("No existe relacion datoColor para este dato");
	}

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
