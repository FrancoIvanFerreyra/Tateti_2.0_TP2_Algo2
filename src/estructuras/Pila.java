package estructuras;

import utiles.ValidacionesUtiles;

public class Pila<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private Nodo<T> tope = null;
	private int tamanio = 0;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la pila vacia para su uso
	 */
	public Pila() {
		this.tope = null;
		this.tamanio = 0;
	}

	//METODOS DE CLASE ----------------------------------------------------------------------------------------
	//METODOS GENERALES ---------------------------------------------------------------------------------------
	//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

	/*
	 * post: indica si la cola tiene algún elemento.
	 */
	public boolean estaVacia() {
		return (this.tamanio == 0);
	}

	/**
	 * Agrega el elemento a la pila
	 * @param elemento no es null
	 * @throws NullPointerException si elemento es null
	 */
	public void apilar(T elemento) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(elemento, "elemento");
		Nodo<T>nuevo = new Nodo<>(elemento);
		nuevo.setSiguiente(this.tope);
		this.tope = nuevo;
		this.tamanio++;
	}

	/**
	 * Agrega cada elemento de la lista a la pila
	 * @param lista no es null
	 * @throws NullPointerException si lista es null
	 */
	public void apilar(Lista<T> lista) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(lista, "lista");
		lista.iniciarCursor();
		int i = 0;
		while (lista.avanzarCursor() && i < lista.getTamanio()) {
			this.apilar(lista.obtenerCursor());
			i++;
		}
	}

	/*
	 * pre :
	 * post: devuelve el elemento en el tope de la pila y achica la pila en 1.
	 */
	public T desapilar() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.tope.getDato();
			this.tope = this.tope.getSiguiente();
			this.tamanio--;
		}
		return elemento;
	}

	/**
	 * pre: -
	 * post: devuelve el elemento en el tope de la pila (solo lectura)
	 */
	public T obtener() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.tope.getDato();
		}
		return elemento;
	}

	/*
	 * post: devuelve la cantidad de elementos que tiene la cola.
	 */
	public int contarElementos() {
		return this.tamanio;
	}

	//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}