package estructuras;

import java.util.Comparator;
import utiles.ValidacionesUtiles;

public class ListaOrdenableSimplementeEnlazada<T extends Comparable<T>> extends ListaSimplementeEnlazada<T>{

	/**
     * 
     * @return devuelve verdadero si todos los elementos de la lista estan ordenados de forma ascendente
     */
    public boolean estaOrdenada() {
        if (this.estaVacia() || this.getTamanio() == 1) {
            return true; // Una lista vacía o de un solo elemento está ordenada
        }

        Nodo<T> actual = this.getPrimero();
        Nodo<T> siguiente = actual.getSiguiente();

        // Recorre cada par de elementos
        while (siguiente != null) {
            if (actual.getDato().compareTo(siguiente.getDato()) > 0) {
                return false; // No está en orden ascendente
            }
            actual = siguiente;
            siguiente = siguiente.getSiguiente();
        }

        return true; // Está en orden ascendente
    }

    /**
     * Inserta un elemento en una lista ordenada de forma ascendente de manera que
     * persista el orden. La lista debe estar previamente ordenada
     * @param elemento no puede ser null
     * @return devuelve el indice de la insercion
     * @throws NullPointerException si elemento es null
     * @throws IllegalStateException si la lista no esta ordenada de forma ascendente
     */
    public int insertarOrdenado(T elemento) throws NullPointerException, IllegalStateException
	{
        ValidacionesUtiles.validarNoNull(elemento, "elemento");
		if(!this.estaOrdenada())
		{
			throw new IllegalStateException("La lista no esta ordenada");
		}
		this.iniciarCursor();
		int index = 1;
		while(this.avanzarCursor() && this.obtenerCursor().compareTo(elemento) <= 0)
		{
			index++;
		}
		if(index > this.getTamanio())
		{
			this.agregar(elemento);
		}
		else
		{
			this.agregar(elemento, index);
		}
		return index;
	}

    /**
     * Ordena la lista de forma ascendente utilizando el algoritmo de burbujeo
     * @param comparador no puede ser null
     * @throws NullPointerException si comparador es null
     * @throws ClassCastException si comparador no es compatible con los elementos de la lista
     */
    public void ordenar(Comparator<T> comparador) throws NullPointerException, ClassCastException {
        ValidacionesUtiles.validarNoNull(comparador, "comparador");
        
        if (this.estaVacia() || this.getTamanio() == 1) {
            return; // Lista ya está ordenada si tiene 0 o 1 elementos
        }
        
        boolean huboIntercambio;
        do {
            huboIntercambio = false;
            Nodo<T> actual = this.getPrimero();
            Nodo<T> siguiente = actual.getSiguiente();
            
            for (int i = 1; i < this.getTamanio(); i++) {
                if (comparador.compare(actual.getDato(), siguiente.getDato()) > 0) {
                    // Intercambiar datos
                    T temp = actual.getDato();
                    actual.setDato(siguiente.getDato());
                    siguiente.setDato(temp);
                    huboIntercambio = true;
                }
                actual = siguiente;
                siguiente = siguiente.getSiguiente();
            }
        } while (huboIntercambio);
    }
}
