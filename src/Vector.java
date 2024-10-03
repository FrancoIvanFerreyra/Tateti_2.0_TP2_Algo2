
import java.util.List;

public class Vector<T> {
    T[] elementos = null;

    public Vector(List<T> elementos) throws Exception
    {
        if(elementos == null)
        {
            throw new Exception("La lista de elementos no puede ser nulo");
        }
        this.elementos = (T[])elementos.toArray();
    }    

    public T[] obtenerElementos()
    {
        T[] copia = (T[]) new Object[this.elementos.length];
        for (int i = 0; i < this.elementos.length; i++) {
            copia[i] = this.elementos[i];
        }
        return copia;
    }
}
