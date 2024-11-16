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
	private Lista<Lista<Lista<Casillero<T>>>> tablero = null;
	private int ancho = 0;
	private int alto = 0;
	private int profundidad = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @param ancho
	 * @param alto
	 * @throws Exception 
	 * post: crea un tablero de ancho 'ancho' contando de 1 a ancho inclusive
	 */
	public Tablero(int ancho, int alto, int profundidad) throws Exception {
		//tarea validar > 0
		this.ancho = ancho;
		this.alto = alto;
		this.profundidad = profundidad;
		this.tablero = new ListaSimplementeEnlazada<Lista<Lista<Casillero<T>>>>();
		this.posicionDeLosDatos = new ListaSimplementeEnlazada<RelacionDatoCasillero<T>>();
		this.coloresDeLosDatos = new ListaSimplementeEnlazada<RelacionDatoColor<T>>();

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
						if (this.existeElCasillero(i, j + l, -1)) {
							relacionarCasillerosVecinos(this.getCasillero(i, j  + l, -1), nuevoCasillero, 0, l, -1);
						}
					}
					for(int l = -1; l <= 0; l++)
					{
						if (l != 0 && this.existeElCasillero(i, j + l, 0)) {
							relacionarCasillerosVecinos(this.getCasillero(i, j + l, 0), nuevoCasillero, 0, l, 0);
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

//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
