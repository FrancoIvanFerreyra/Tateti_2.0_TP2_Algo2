package tateti;

import java.awt.Color;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import exportadores.ExportadorDeDatosAImagen;
import interfaz.Consola;

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
	 * pre: 
	 * - Los parámetros `ancho`, `alto` y `profundidad` deben ser mayores a 0.
	 * @param ancho
	 * @param alto
	 * @throws Exception si `ancho`, `alto` o `profundidad` son menores o iguales a 0.
	 * post:
	 * - Crea un tablero tridimensional con las dimensiones especificadas.
	 * - Inicializa las listas de datos, colores y casilleros.
	 * - Relaciona los casilleros vecinos según las reglas establecidas.
	 */
	public Tablero(int ancho, int alto, int profundidad) throws Exception {
		//tarea validar > 0
		this.ancho = ancho;
		this.alto = alto;
		this.profundidad = profundidad;
		this.tablero = new ListaSimplementeEnlazada<Lista<Lista<Casillero<T>>>>();
		this.posicionDeLosDatos = new ListaSimplementeEnlazada<RelacionDatoCasillero<T>>();
		this.coloresDeLosDatos = new ListaSimplementeEnlazada<RelacionDatoColor<T>>();
		this.casillerosBloqueados = new ListaSimplementeEnlazada<Casillero<T>>();

		for( int i = 1; i <= ancho; i++) {
			Lista<Lista<Casillero<T>>> fila = new ListaSimplementeEnlazada<Lista<Casillero<T>>>();
			this.tablero.agregar(fila);
			for(int j = 1; j <= alto; j++) {
				Lista<Casillero<T>> columna = new ListaSimplementeEnlazada<Casillero<T>>();
				fila.agregar(columna);
				for(int k = 1; k <= profundidad; k++)
				{
					Casillero<T> nuevoCasillero = new Casillero<T>(i, j, k);
					columna.agregar(nuevoCasillero);
					//Estoy en (i, j), tengo que asignar (i-1, j + 0), (i-1, j-1), (i, j-1)
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
	 * pre:
	 * @param casillero1: no puede ser vacio
	 * @param casillero2: no puede ser vacio
	 * @param i: rango entre -1, 0 y 1
	 * @param j: rango entre -1, 0 y 1
	 * post: relaciona los dos vecinos en sus matrices de vecinos, en el casillero1 como i y j, y en casillero2
	 *       como el opuesto
	 */
	public void relacionarCasillerosVecinos(Casillero<T> casillero1, Casillero<T> casillero2, int i, int j, int k) {
		//Validar
		casillero2.setCasilleroVecino(casillero1, i, j, k);
		casillero1.setCasilleroVecino(casillero2, Casillero.invertirCoordenadaDeVecino(i), Casillero.invertirCoordenadaDeVecino(j), Casillero.invertirCoordenadaDeVecino(k));
	}

	public boolean existeElCasillero(int i, int j, int k) throws Exception {
		if ((i < 1) ||
		    (j < 1) ||
			(k < 1)) {
			   return false;
		   }
		if ((i > this.tablero.getTamanio()) ||
			(j > this.tablero.obtener(i).getTamanio()) ||
			(k > this.tablero.obtener(i).obtener(j).getTamanio())) {
			return false;
		}
		return true;
	}

	public void agregar(int x, int y, int z,  T ficha) throws Exception {
		Casillero<T> casillero = getCasillero(x, y, z);
		casillero.setDato(ficha);
	}

	public Casillero<T> getCasillero(int x, int y, int z) throws Exception {
		//validar
		return this.tablero.obtener(x).obtener(y).obtener(z);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 * post: devuelve el dato que hay en el casillero
	 */
	public T obtener(int x, int y, int z) throws Exception {
		return getCasillero(x, y, z).getDato();
	}

	/**
	 * pre:
	 * @param dato
	 * @return: devuelve el casillero de esa ficha
	 * @throws Exception
	 */
	public Casillero<T> getCasillero(T dato) throws Exception {
		this.posicionDeLosDatos.iniciarCursor();
		while(this.posicionDeLosDatos.avanzarCursor()) {
			Casillero<T> casillero = this.posicionDeLosDatos.obtenerCursor().getCasillero();
			if (casillero.tiene(dato)) {
				return casillero;
			}
		}
		throw new Exception("No se encontro la ficha");
	}
	
	/**
	 * pre:
	 * - @param dato no debe ser nulo.
	 * post:
	 * - Devuelve el color asociado al dato especificado.
	 * - Devuelve null si el dato no tiene un color asignado.
	 * @throws Exception
	 * - si dato es nulo.
	 */
	public Color getColorDato(T dato)
	{
		this.coloresDeLosDatos.iniciarCursor();
		while(this.coloresDeLosDatos.avanzarCursor())
		{
			if(this.coloresDeLosDatos.obtenerCursor().getDato().equals(dato))
			{
				return this.coloresDeLosDatos.obtenerCursor().getColor();
			}
		}
		return null;
	}

	/**
	 * pre:
	 * - @param casillero debe existir y no ser nulo
	 * - @param casilleroVecino debe existir y no ser nulo
	 * - @param dato debe estar contenido en el casillero original.
	 * post:
	 * - Mueve el dato desde 'casillero' a 'casilleroVecino'.
	 * - Actualiza la relación del dato con el nuevo casillero.
	 * @throws Exception
	 * - si 'casillero', 'casilleroVecino' o 'dato' son nulos.
	 * - si dato no está en el casillero original.
	 */
	public void mover(Casillero<T> casillero, Casillero<T> casilleroVecino, T dato) throws Exception{
		casilleroVecino.setDato(dato);
		casillero.vaciar();
		actualizarRelacionDatoCasillero(dato, casilleroVecino);
	}

	/**
	 * pre:
	 * - @param dato no debe ser nulo.
	 * post:
	 * - Devuelve true si el dato está contenido en algún casillero del tablero.
	 * - Devuelve false en caso contrario.
	 * @throws Exception
	 * -  si 'dato' es nulo.
	 */
	public boolean contiene(T dato) throws Exception
	{
		if(dato == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
		}
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
	 * pre:
	 * - @param dato no debe ser nulo.
	 * - El dato debe estar en un casillero del tablero.
	 * post:
	 * - Devuelve una lista de movimientos posibles para el casillero que contiene el dato.
	 * - Los movimientos posibles consideran vecinos libres y no bloqueados.
	 * @throws Exception
	 * -  si 'dato' es nulo.
	 * -  si el dato no está en un casillero del tablero.
	 */
	public Lista<Movimiento> obtenerMovimientosPosibles(T dato) throws Exception
	{
		Casillero<T> casillero = this.getCasillero(dato);
		Lista<Movimiento> movimientosPosibles = new ListaSimplementeEnlazada<Movimiento>();
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
	 * pre:
	 * - @param dato no debe ser nulo.
	 * - El dato debe estar en un casillero del tablero.
	 * post:
	 * - Devuelve true si el casillero que contiene el dato tiene movimientos posibles.
	 * - Devuelve false en caso contrario.
	 * @throws Exception
	 * - si 'dato' es nulo.
	 * - si el dato no está en un casillero del tablero.
	 */
	public boolean tieneMovimientosPosibles(T dato) throws Exception
	{
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

	/**
	 * pre: El tablero debe haber sido inicializado.
	 * post:
	 * - Muestra una representación del tablero en consola.
	 * - Exporta la representación del tablero en capas como imágenes.
	 * @throws Exception si el tablero no ha sido inicializado correctamente.
	 */
	public void mostrar() throws Exception
	{
		Consola.limpiar();
		Consola.imprimirMensaje(this.toString());
		try {
			ExportadorDeDatosAImagen.exportarTableroPorCapas(this, "./src/estadosTablero/");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	public String toString(){
		String resultado = "Posiciones tablero \n";
		for(int k = getProfundidad(); k >= 1; k--)
		{
			for(int j = 1; j <= getAlto(); j++)
			{
				for(int i = 1; i <= getAncho(); i++)
				{
					String dato;
					try
					{
						dato = obtener(i, j, k).toString();
					}
					catch(Exception e)
					{
						dato = "vacio";
					}
					resultado += "(" + i + ", " + j + ", " + k + "), " + dato + "\t";
				}
				resultado += "\n";
			}
			resultado += "\n";
		}
		return resultado;
	}

	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	public int getAncho() {
		return this.ancho;
	}

	public int getAlto() {
		return this.alto;
	}

	public int getProfundidad() {
		return this.profundidad;
	}
	public Lista<RelacionDatoCasillero<T>> getPosicionDeLosDatos()
	{
		return this.posicionDeLosDatos;
	}

	public Lista<RelacionDatoColor<T>> getColoresDeLosDatos()
	{
		return this.coloresDeLosDatos;
	}

	public Lista<Casillero<T>> getCasillerosBloqueados()
	{
		return this.casillerosBloqueados;
	}


	/**
	 * pre:
	 * @param dato no debe ser nulo.
	 * @param casillero no debe ser nulo.
	 * post: Actualiza o agrega la relación entre el dato y el casillero especificado.
	 * @throws Exception si 'dato' o 'casillero' son nulos.
	 */
	public void actualizarRelacionDatoCasillero(T dato, Casillero<T> casillero) throws Exception
	{
		if(dato == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
		}
		if(casillero == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
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

	public void eliminarRelacionDatoCasillero(T dato) throws Exception
	{
		if(dato == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
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
	 * pre: 
	 * @param dato no debe ser nulo.
	 * @param color no debe ser nulo.
	 * post: Actualiza o agrega la relación entre el dato y el color especificado.
	 * @throws Exception si 'dato' o 'color' son nulos.
	 */
	public void actualizarRelacionDatoColor(T dato, Color color) throws Exception
	{
		if(dato == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
		}
		if(color == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
		}
		getColoresDeLosDatos().iniciarCursor();
		while(getColoresDeLosDatos().avanzarCursor())
		{
			RelacionDatoColor<T> relacionDatoColor = getColoresDeLosDatos().obtenerCursor();
			if(relacionDatoColor.getDato().equals(dato))
			{
				relacionDatoColor.setColor(color);
				break;
			}
		}
		this.coloresDeLosDatos.agregar(new RelacionDatoColor<>(color, dato));
	}

	public void eliminarRelacionDatoColor(T dato) throws Exception
	{
		if(dato == null)
		{
			throw new Exception("El dato a buscar no puede ser null");
		}

		getColoresDeLosDatos().iniciarCursor();
		int i = 1;
		while(getColoresDeLosDatos().avanzarCursor())
		{
			RelacionDatoColor<T> relacionDatoColor = getColoresDeLosDatos().obtenerCursor();
			if(relacionDatoColor.getDato().equals(dato))
			{
				getColoresDeLosDatos().remover(i);;
				return;
			}
			i++;
		}
		throw new Exception("No existe relacion datoColor para este dato");
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
