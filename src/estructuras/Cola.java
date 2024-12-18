package estructuras;

import utiles.ValidacionesUtiles;

public class Cola<T> {
	//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
	//ATRIBUTOS -----------------------------------------------------------------------------------------------

	private Nodo<T> frente = null;

	private Nodo<T> ultimo = null;

	private int tamanio = 0;

	//CONSTRUCTORES -------------------------------------------------------------------------------------------

	/**
	 * pre:
	 * post: inicializa la cola vacia para su uso
	 */
	public Cola() {
		this.frente = null;
		this.ultimo = null;
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
	 * Agrega el elemento a la cola
	 * @param elemento no debe ser null
	 * @throws NullPointerException si elemento es null
	 */
	public void acolar(T elemento) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(elemento, "elemento");

		Nodo<T> nuevo = new Nodo<>(elemento);
		if (!this.estaVacia()) {
			this.ultimo.setSiguiente(nuevo);
			this.ultimo = nuevo;
		} else {
			this.frente = nuevo;
			this.ultimo = nuevo;
		}
		this.tamanio++;
	}

	/**
	 * Agrega a la cola cada elemento de la lista
	 * @param lista no debe ser null
	 * @throws NullPointerException si lista es null
	 */
	public void acolar(Lista<T> lista) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(lista, "lista");
		lista.iniciarCursor();
		int i = 0;
		while (lista.avanzarCursor() && i < lista.getTamanio()) {
			this.acolar(lista.obtenerCursor());
			i++;
		}
	}

	/**
	 * Agrega a la cola los elementos del vector
	 * @param vector no debe ser null
	 * @throws NullPointerException si vector es null
	 */
	public void acolar(Vector<T> vector) throws NullPointerException{
		ValidacionesUtiles.validarNoNull(vector, "vector");
		for(int i = 1; i <= vector.getLongitud(); i++)
		{
			if(vector.obtener(i) != null)
			{
				acolar(vector.obtener(i));
			}
		}
	}

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola quitandolo.
	 */
	public T desacolar() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.frente.getDato();
			this.frente = this.frente.getSiguiente();
			this.tamanio--;
			if (estaVacia()) {
				this.ultimo = null;
			}
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

	/*
	 * pre :
	 * post: devuelve el elemento en el frente de la cola. Solo lectura
	 */
	public T obtener() {
		T elemento = null;
		if (!this.estaVacia()) {
			elemento = this.frente.getDato();
		}
		return elemento;
	}

	//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}