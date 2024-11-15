package estructuras;

public class ListaEnlazada<T> extends Lista<T> {

    // agregar un elemento al final de la lista.
    @Override
    public void agregar(T elemento, int posicion) throws Exception {
        Nodo<T> nuevoNodo = new Nodo<T>(elemento);
        if (posicion == 1) {
            nuevoNodo.setSiguiente(this.getPrimero());
            this.setPrimero(nuevoNodo);
        } else {
            Nodo<T> nodoAnterior = this.getNodo(posicion - 1);
            nuevoNodo.setSiguiente(nodoAnterior.getSiguiente());
            nodoAnterior.setSiguiente(nuevoNodo);
        }
        this.aumentarTamanio();
    }

    // borrar un elemento en una posición específica.
    @Override
    public void remover(int posicion) throws Exception {
        if (posicion == 1) {
            this.setPrimero(this.getPrimero().getSiguiente());
        } else {
            Nodo<T> nodoAnterior = this.getNodo(posicion - 1);
            nodoAnterior.setSiguiente(nodoAnterior.getSiguiente().getSiguiente());
        }
        this.disminuirTamanio();
    }

	// verifica si un objeto está en la lista.
	public boolean contiene(T objeto) throws Exception {
		this.iniciarCursor();

		while (this.avanzarCursor()) {
			if (this.obtenerCursor().equals(objeto)) {
				return true;
			}
		}

		return false;
	}

	// buscar un objeto y devolver su posición
	public int obtenerPosicion(T objeto) throws Exception {
		iniciarCursor();
		int posicion = 1;
		while (avanzarCursor()) {
			if (this.obtenerCursor().equals(objeto)) {
				return posicion;
			}
			posicion++;
		}
		return -1;
	}
}
