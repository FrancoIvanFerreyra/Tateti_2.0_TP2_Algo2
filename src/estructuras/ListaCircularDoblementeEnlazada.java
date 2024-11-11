package estructuras;

public class ListaCircularDoblementeEnlazada<T> extends ListaDoblementeEnlazada<T> {
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
	public void agregar(T elemento, int posicion) throws Exception {
		super.agregar(elemento, posicion);
		if(posicion == 1 || posicion == this.getTamanio())
		{
            vincularNodosExtremos();
		}
	}
	
	/*
	 * pre : posición pertenece al intervalo: [1, contarElementos()]
	 * post: remueve de la Lista el elemento en la posición indicada.
	 */
	@Override
	public void remover(int posicion) throws Exception {
		super.remover(posicion);
		if(posicion == 1 || posicion == this.getTamanio())
		{
			vincularNodosExtremos();
		}
	}

    private void vincularNodosExtremos() throws Exception
    {
        if(this.getTamanio() == 0)
        {
            throw new Exception("La lista circular esta vacia");
        }
        ((NodoDoblementeEnlazado<T>)this.getPrimero()).setAnterior((NodoDoblementeEnlazado<T>)getNodo(this.getTamanio()));
        ((NodoDoblementeEnlazado<T>)getNodo(this.getTamanio())).setSiguiente((NodoDoblementeEnlazado<T>)this.getPrimero());
    }
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
