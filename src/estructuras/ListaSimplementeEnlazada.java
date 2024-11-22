package estructuras;

import java.util.NoSuchElementException;

public class ListaSimplementeEnlazada<T> extends Lista<T> {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	/**
	 * pre: posición pertenece al intervalo: [1, contarElementos() + 1]
	 * pos: agrega el elemento en la posición indicada.
	 */
	@Override
	public void agregar(T elemento, int posicion) throws IllegalArgumentException {
		validarPosicion(posicion);
		Nodo<T> nuevo = new Nodo<>(elemento);
		if (posicion == 1) {
			nuevo.setSiguiente( this.getPrimero());
			this.setPrimero(nuevo);
		} else {
			Nodo<T> anterior = this.getNodo(posicion -1);
			nuevo.setSiguiente(anterior.getSiguiente());
			anterior.setSiguiente(nuevo);
		}
		this.aumentarTamanio();
	}
	
	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: remueve de la Lista el elemento en la posición indicada.
	 */
	@Override
	public void remover(int posicion) throws IllegalArgumentException {
		validarPosicion(posicion);
		this.iniciarCursor();
		Nodo<T> removido = null;
		if (posicion == 1) {
			removido = this.getPrimero();
			this.setPrimero(removido.getSiguiente());
		} else {
			Nodo<T> anterior = this.getNodo(posicion - 1);
			removido = anterior.getSiguiente();
			anterior.setSiguiente(removido.getSiguiente());
		}
		this.disminuirTamanio();
	}

	@Override
	public void removerPrimeraAparicion(T elemento) throws  NoSuchElementException
	{
		if(!this.contiene(elemento))
		{
			throw new NoSuchElementException("La lista no contiene este elemento");
		}
		int i = 1;
		this.iniciarCursor();
		boolean encontrado = false;
		while(!encontrado && this.avanzarCursor())
		{
			if(this.obtenerCursor().equals(elemento))
			{
				this.remover(i);
				encontrado = true;
			}
			i++;
		}
	}

	@Override
	public void intercambiar(int posicion1, int posicion2) throws IllegalArgumentException
	{
		validarPosicion(posicion1);
		validarPosicion(posicion2);
		if(posicion1 == posicion2)
		{
			throw new IllegalArgumentException("No es necesario intercambiar posiciones iguales");
		}

		T auxiliar = this.obtener(posicion1);
		this.cambiar(this.obtener(posicion2), posicion1);
		this.cambiar(auxiliar, posicion2);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	

}
