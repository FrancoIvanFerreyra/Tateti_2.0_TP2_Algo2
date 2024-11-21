package estructuras;

import java.util.function.Predicate;
import utiles.ValidacionesUtiles;

public class Vector<T extends Object> {
    //ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
    //ATRIBUTOS -----------------------------------------------------------------------------------------------
    
        private T[] datos = null;
        private T datoInicial;
    
    //CONSTRUCTORES -------------------------------------------------------------------------------------------
        
        /**
         * pre: 
         * @param longitud: entero mayor a 0, determina la cantiadad de elementos del vector
         * @param datoInicial: valor inicial para las posiciones del vector
         * @throws IllegalArgumentException: da error si la longitud es invalida
         * post: inicializa el vector de longitud de largo y todos los valores inicializados
         */
        public Vector(int longitud, T datoInicial) throws IllegalArgumentException {
            if (longitud < 1) {
                throw new IllegalArgumentException("La longitud debe ser mayor o igual a 1");
            }
            this.datos = crearVector(longitud);
            this.datoInicial = datoInicial;
            for(int i = 0; i < this.getLongitud(); i++){
                this.datos[i] = datoInicial;
            }
        }
        
    //METODOS DE CLASE ----------------------------------------------------------------------------------------
    //METODOS GENERALES ---------------------------------------------------------------------------------------
    //METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
        
        /**
         * pre:
         * @param posicion: valor entre 1 y el largo del vector (no redimensiona)
         * @param dato: -
         * @throws IllegalArgumentException: da error si la posicion no esta en rango
         * post: guarda la el dato en la posicion dada 
         */
        public void agregar(int posicion, T dato) throws IllegalArgumentException {
            validarPosicion(posicion);
            this.datos[posicion - 1] = dato;
        }
    
        /**
         * pre: -
         * @param posicion: valor entre 1 y el largo del vector
         * @return devuelve el valor en esa posicion
         * @throws IllegalArgumentException: da error si la posicion no esta en rango
         */
        public T obtener(int posicion) throws IllegalArgumentException {
            validarPosicion(posicion);
            return this.datos[posicion - 1];
        }
    
        /**
         * pre: -
         * @param posicion: valor entre 1 y el largo del vector
         * @throws IllegalArgumentException: da error si la posicion no esta en rango
         * post: remueve el valor en la posicion y deja el valor inicial
         */
        public void remover(int posicion) throws IllegalArgumentException {
            validarPosicion(posicion);
            this.datos[posicion - 1] = this.datoInicial;
        }

        /**
         * pre: 
         * @param dato: valor a guardar
         * @return devuelve la posicion en que se guardo
         * post: guarda el dato en la siguiente posicion vacia
         */
        @SuppressWarnings("ManualArrayToCollectionCopy")
        public int agregar(T dato){
            for(int i = 0; i < this.getLongitud(); i++) {
                if (this.datos[i] == this.datoInicial) {
                    this.datos[i] = dato;
                    return i + 1;
                }
            }		
            T[] temp = crearVector(this.getLongitud() * 2);
            for(int i = 0; i < this.getLongitud(); i++) {
                temp[i] = this.datos[i];
            }
            int posicion = this.getLongitud(); 
            this.datos = temp;
            this.datos[posicion] = dato;
            for(int i = posicion +1; i < this.getLongitud(); i++) {
                this.datos[i] = this.datoInicial;	
            }
            return posicion + 1;
        }
        
        /**
         * pre: -
         * @param posicion: valor entre 1 y el largo del vector
         * @throws IllegalArgumentException: da error si la posicion no esta en rango
         * post: valida la posicion que este en rango
         */
        private void validarPosicion(int posicion) throws IllegalArgumentException {
            if ((posicion < 1) ||
                (posicion > this.getLongitud())) {
                throw new IllegalArgumentException("La " + posicion + " no esta en el rango 1 y " + this.getLongitud() + " inclusive");
            }
        }
    
        /**
         * pre: 
         * @param longitud: mayor o igual a 1
         * @return devuelve un vector del tipo y longitud deseado
         * @throws IllegalArgumentException 
         */
        
        @SuppressWarnings("unchecked")
        private T[] crearVector(int longitud) throws IllegalArgumentException {
            if (longitud <= 0) {
                throw new IllegalArgumentException("La longitud debe ser mayor o igual a 1");
            }
            return (T[]) new Object[longitud];
        }

        public Vector<T> filtrar(Predicate<T> condicion) throws NullPointerException{
		
            ValidacionesUtiles.validarNoNull(condicion, "condicion");

            Vector<T> elementosFiltrados = new Vector<>(this.getLongitud(), null); 

            for (int i = 1; i <= this.getLongitud(); i++)
            {
                T elemento = this.obtener(i);
                if(condicion.test(elemento))
                {
                    elementosFiltrados.agregar(elemento);
                }
            }
            return elementosFiltrados;
	}

    /**
     * 
     * @param objeto no puede ser null
     * @return devuelve verdadero si el objeto esta en el vector
     * @throws NullPointerException si objeto es null
     */
	public boolean contiene(T objeto) throws NullPointerException {
        ValidacionesUtiles.validarNoNull(objeto, "objeto");
		int i = 1;
	
		while (i <= this.getLongitud()) {

			if (this.obtener(i) != null &&
                this.obtener(i).equals(objeto)) {
				return true;
			}
            i++;
		}

		return false;
	}

    /**
     * 
     * @param objeto no puede ser null
     * @return devuelve la posicion donde se encuentra el objeto o -1 si no
     *         esta en el vector
     * @throws NullPointerException si objeto es null
     */
    public int obtenerPosicion(T objeto) throws NullPointerException{
        ValidacionesUtiles.validarNoNull(objeto, "objeto");
        int i = 0;
        while(i <= this.getLongitud()){
            if(this.obtener(i)!= null && this.obtener(i).equals(objeto)){
                return i;
            }
            i++;
        }
        i = -1;
        return i;
    }

            
    //GETTERS SIMPLES -----------------------------------------------------------------------------------------
        
    /**
     * 
     * @return devuelve la longitud del vector
     */
    public final int getLongitud() {
        return this.datos.length;
    }
    
    /**
     * 
     * @return devuelve la cantidad de elementos no nulos del vector
     */
    public int contarElementos()
    {
        int cantidadDeElementos = 0;
        for(int i = 1; i <= this.getLongitud(); i++)
        {
            T elemento = this.obtener(i);
            if(elemento != null)
            {
                cantidadDeElementos++;
            }
        }
        return cantidadDeElementos;
    }
        
    
    //SETTERS SIMPLES -----------------------------------------------------------------------------------------
}