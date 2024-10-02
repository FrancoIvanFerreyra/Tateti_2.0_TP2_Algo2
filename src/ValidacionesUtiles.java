import java.util.List;

public class ValidacionesUtiles {

    public static <T> boolean estaEnLaLista(T objeto, List<?> lista) throws Exception
    {
        if(objeto == null)
        {
            throw new Exception("El objeto no puede ser nulo");
        }
        if(lista == null)
        {
            throw new Exception("La lista no puede ser nula");
        }
        if(lista.isEmpty())
        {
            return false;
        }
        else if(lista.get(0).getClass().isInstance(objeto))
        {
            throw new Exception("La lista no es del tipo de dato del objeto");
        }
        return lista.contains(objeto);
    }
}
