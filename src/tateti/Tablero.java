package tateti;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import interfaz.Consola;

public class Tablero<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Lista<RelacionDatoCasillero<T>> posicionDeLosDatos = null;
	private Lista<Lista<Casillero<T>>> tablero = null;
	private int ancho = 0;
	private int alto = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param ancho
	 * @param alto
	 * @throws Exception 
	 * post: crea un tablero de ancho 'ancho' contando de 1 a ancho inclusive
	 */
	public Tablero(int ancho, int alto) throws Exception {
		//tarea validar > 0
		this.ancho = ancho;
		this.alto = alto;
		this.tablero = new ListaSimplementeEnlazada<Lista<Casillero<T>>>();
		this.posicionDeLosDatos = new ListaSimplementeEnlazada<RelacionDatoCasillero<T>>();

		for( int i = 1; i <= ancho; i++) {
			Lista<Casillero<T>> fila = new ListaSimplementeEnlazada<Casillero<T>>();
			this.tablero.agregar(fila);
			for(int j = 1; j <= alto; j++) {
				Casillero<T> nuevoCasillero = new Casillero<T>(i, j);
				fila.agregar(nuevoCasillero);
				
				//Estoy en (i, j), tengo que asignar (i-1, j + 0), (i-1, j-1), (i, j-1)
				for(int k = -1; k <= 1; k++) {
					if (this.existeElCasillero(i - 1, j + k)) {
						relacionarCasillerosVecinos(this.getCasillero(i - 1, j + k), nuevoCasillero, -1, k);					
					}
				}
				if (this.existeElCasillero(i, j - 1)) {
					relacionarCasillerosVecinos(this.getCasillero(i, j - 1), nuevoCasillero, 0, -1);
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
	public void relacionarCasillerosVecinos(Casillero<T> casillero1, Casillero<T> casillero2, int i, int j) {
		//Validar
		casillero2.setCasilleroVecino(casillero1, i, j);
		casillero1.setCasilleroVecino(casillero2, Casillero.invertirCoordenadaDeVecino(i), Casillero.invertirCoordenadaDeVecino(j));
	}

	public boolean existeElCasillero(int i, int j) throws Exception {
		if ((i < 1) ||
		    (j < 1)) {
			   return false;
		   }
		if ((i > this.tablero.getTamanio()) ||
			(j > this.tablero.obtener(i).getTamanio())) {
			return false;
		}
		return true;
	}

	public void agregar(int x, int y, T ficha) throws Exception {
		Casillero<T> casillero = getCasillero(x, y);
		casillero.setDato(ficha);
	}

	public Casillero<T> getCasillero(int x, int y) throws Exception {
		//validar
		return this.tablero.obtener(x).obtener(y);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @throws Exception
	 * post: devuelve el dato que hay en el casillero
	 */
	public T obtener(int x, int y) throws Exception {
		return getCasillero(x, y).getDato();
	}

	/**
	 * pre:
	 * @param dato
	 * @return: devuelve el casillero de esa ficha
	 * @throws Exception
	 */
	public Casillero<T> getCasillero(T dato) throws Exception {
		this.tablero.iniciarCursor();
		while(this.tablero.avanzarCursor()) {
			Lista<Casillero<T>> columna = this.tablero.obtenerCursor();
			columna.iniciarCursor();
			while (columna.avanzarCursor()) {
				Casillero<T> casillero = columna.obtenerCursor();
				if (casillero.tiene(dato)) {
					return casillero;
				}
			}
		}
		throw new Exception("No se encontro la ficha");
	}
	
	public void mover(Casillero<T> casillero, Casillero<T> casilleroVecino, T dato) throws Exception{
		casilleroVecino.setDato(dato);
		casillero.vaciar();
		actualizarRelacionDatoCasillero(dato, casilleroVecino);
	}

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

	public Lista<Movimiento> obtenerMovimientosPosibles(T dato) throws Exception
	{
		Casillero<T> casillero = this.getCasillero(dato);
		Lista<Movimiento> movimientosPosibles = new ListaSimplementeEnlazada<Movimiento>();
		for(Movimiento movimiento : Movimiento.values())
		{
			if (casillero.existeElVecino(movimiento)
			 && !casillero.getCasilleroVecino(movimiento).estaOcupado())
			 {
				movimientosPosibles.agregar(movimiento);
			 }
		}
		return movimientosPosibles;
	}

	public boolean tieneMovimientosPosibles(T dato) throws Exception
	{
		Casillero<T> casillero = this.getCasillero(dato);
		for(Movimiento movimiento : Movimiento.values())
		{
			if (casillero.existeElVecino(movimiento)
			 && !casillero.getCasilleroVecino(movimiento).estaOcupado())
			 {
				return true;
			 }
		}
		return false;
	}

	public void mostrar()
	{
		Consola.limpiar();
		Consola.imprimirMensaje(this.toString());
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------


public String toString(){
	String resultado = "Posiciones tablero \n";
	for(int j = getAlto(); j >= 1; j--)
	{
		for(int i = 1; i <= getAncho(); i++)
		{
			String dato;
			try
			{
				dato = obtener(i, j).toString();
			}
			catch(Exception e)
			{
				dato = "vacio";
			}
			resultado += "(" + i + ", " + j + "), " + dato + "\t";
		}
		resultado += "\n";
	}
	return resultado;
}
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
//GETTERS SIMPLES -----------------------------------------------------------------------------------------


	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}
	public Lista<RelacionDatoCasillero<T>> getPosicionDeLosDatos()
	{
		return this.posicionDeLosDatos;
	}

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
				break;
			}
		}
		this.posicionDeLosDatos.agregar(new RelacionDatoCasillero<>(casillero, dato));
	}

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
