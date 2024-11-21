package estructuras;

import java.util.NoSuchElementException;
import utiles.ValidacionesUtiles;

public abstract class Lista<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private Nodo<T> primero = null;
	private int tamanio = 0;
	private Nodo<T> cursor = null;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: crea una lista vacia
	 */
	public Lista() {
		this.primero = null;
		this.tamanio = 0;
		this.cursor = null;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return this.getClass().getName() + ", tamaño: " + this.getTamanio() + ", primer nodo: " + this.getPrimero().getDato();
	}
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	/**
	 * pre:
	 * pos: indica si la Lista tiene algún elemento.
	 */
	public boolean estaVacia() {
		return (this.tamanio == 0);
	}

	/**
	 * pre: -
	 * pos: agrega el elemento al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
	public void agregar(T elemento) {
		this.agregar(elemento, this.getTamanio() + 1);
	}

	/**
	 * pre: posición pertenece al intervalo: [1, contarElementos() + 1]
	 * pos: agrega el elemento en la posición indicada.
	 */
	public abstract void agregar(T elemento, int posicion) throws IllegalArgumentException;

	/**
	 * pre: -
	 * pos: agrega cada elemento no nulo del vector al final de la Lista, en la posición:
	 *       contarElementos() + 1.
	 */
	public void agregar(Vector<T> vector) throws NullPointerException
	{
		ValidacionesUtiles.validarNoNull(vector, "vector");

		for(int i = 1; i <= vector.getLongitud(); i++)
		{
			if(vector.obtener(i) != null)
			{
				agregar(vector.obtener(i));
			}
		}
	};

	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: remueve de la Lista el elemento en la posición indicada.
	 */
	public abstract void remover(int posicion) throws IllegalArgumentException;

		/*
	 * pre : elemento no es null y lista contiene a elemento
	 * post: remueve de la Lista la primera aparicion del elemento en la lista
	 * (en el indice mas bajo).
	 */
	public abstract void removerPrimeraAparicion(T elemento) throws NoSuchElementException;

	/**
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * pos: devuelve el dato de la posicion
	 */
	public T obtener(int posicion) throws IllegalArgumentException {
		validarPosicion(posicion);
		return this.getNodo(posicion).getDato();
	}

	/**
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * pos: cambia el elemento de la posicion
	 */
	public void cambiar(T elemento, int posicion) throws IllegalArgumentException {
		validarPosicion(posicion);
		this.getNodo(posicion).setDato(elemento);
	}

	public abstract void intercambiar(int posicion1, int posicion2) throws IllegalArgumentException;

	// verifica si un objeto está en la lista.
	public boolean contiene(T objeto) throws NullPointerException {
		ValidacionesUtiles.validarNoNull(objeto, "null");
		this.iniciarCursor();
	
		while (this.avanzarCursor()) {
			if (this.obtenerCursor().equals(objeto)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * post: deja el cursor de la Lista preparado para hacer un nuevo
	 *       recorrido sobre sus elementos.
	 */
	public void iniciarCursor() {
		this.cursor = null;
	}

	/*
	 * pre : se ha iniciado un recorrido (invocando el método
	 *       iniciarCursor()) y desde entonces no se han agregado o
	 *       removido elementos de la Lista.
	 * post: mueve el cursor y lo posiciona en el siguiente elemento
	 *       del recorrido.
	 *       El valor de retorno indica si el cursor quedó posicionado
	 *       sobre un elemento o no (en caso de que la Lista esté vacía o
	 *       no existan más elementos por recorrer.)
	 */
	public boolean avanzarCursor() {
		if (this.cursor == null) {
			this.cursor = this.primero;
		} else {
			this.cursor = this.cursor.getSiguiente();
		}

		/* pudo avanzar si el cursor ahora apunta a un nodo */
		return (this.cursor != null);
	}

	/*
	 * pre : el cursor está posicionado sobre un elemento de la Lista,
	 *       (fue invocado el método avanzarCursor() y devolvió true)
	 * post: devuelve el elemento en la posición del cursor.
	 *
	 */
	public T obtenerCursor() {
		T elemento = null;
		if (this.cursor != null) {
			elemento = this.cursor.getDato();
		}
		return elemento;
	}

	protected void aumentarTamanio() {
		this.tamanio++;
	}
	
	protected void disminuirTamanio() {
		this.tamanio--;
	}
	
	//GETTERS SIMPLES -----------------------------------------------------------------------------------------

	/*
	 * post: devuelve la cantidad de elementos que tiene la Lista.
	 */
	public int getTamanio() {
		return this.tamanio;
	}

	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: devuelve el nodo en la posición indicada.
	 */
	protected Nodo<T> getNodo(int posicion) throws IllegalArgumentException {
		validarPosicion(posicion);
		Nodo<T> actual = this.primero;
		for(int i = 1; i < posicion; i++) {
			actual = actual.getSiguiente();
		}
		return actual;
	}

	protected void validarPosicion(int posicion) throws IllegalArgumentException {
		if ((posicion < 1) ||
				(posicion > this.tamanio + 1)) {
			throw new IllegalArgumentException("La posicion debe estar entre 1 y tamaño + 1");
		}
	}

	protected Nodo<T> getPrimero() {
		return this.primero;
	}
	
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	protected void setPrimero(Nodo<T> primero) {
		this.primero = primero;
	}
}
