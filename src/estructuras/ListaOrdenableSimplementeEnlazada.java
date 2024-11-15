package estructuras;

import java.util.Comparator;

public class ListaOrdenableSimplementeEnlazada<T extends Comparable<T>> extends ListaSimplementeEnlazada<T>{

		    /**
     * Verifica si la lista está ordenada en orden ascendente.
     */
    public boolean estaOrdenada() throws Exception {
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

    public int insertarOrdenado(T elemento) throws Exception
	{
		if(!this.estaOrdenada())
		{
			throw new Exception("La lista no esta ordenada");
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

    public void ordenar(Comparator<T> comparador) throws Exception {
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
