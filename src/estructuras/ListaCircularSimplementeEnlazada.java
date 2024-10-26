package estructuras;

public class ListaCircularSimplementeEnlazada<T> extends ListaSimplementeEnlazada<T> {
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
			getNodo(this.getTamanio()).setSiguiente(this.getPrimero());
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
			getNodo(this.getTamanio()).setSiguiente(this.getPrimero());
		}
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
