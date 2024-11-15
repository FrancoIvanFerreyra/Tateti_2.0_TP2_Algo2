package utiles;
import java.util.List;

import estructuras.ListaEnlazada;

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
    
    public static <T> void verificarObjetoValido(T objeto) throws Exception {
        if (objeto == null) {
            throw new Exception("El objeto no puede ser nulo");
        }
    }
    
    public static <T> void verificarListaValida(ListaEnlazada<T> lista) throws Exception {
        if (lista == null) {
            throw new Exception("La lista no puede ser nula");
        }
    }


    public static boolean esMayorQue(int numero, int minimo)
    {
        return numero > minimo;
    }
    public static boolean esMayorOIgualQue(int numero, int minimo)
    {
        return numero >= minimo;
    }
    public static boolean esMenorQue(int numero, int maximo)
    {
        return numero < maximo;
    }
    public static boolean esMenorOIgualQue(int numero, int maximo)
    {
        return numero <= maximo;
    }
    public static boolean estaEntre(int numero, int minimo, int maximo)
    {
        return numero >= minimo && numero <= maximo;
    }
    public static boolean estaEstrictamenteEntre(int numero, int minimo, int maximo)
    {
        return numero > minimo && numero < maximo;
    }

    public static boolean esNombreValido(String nombre)
    {
        return nombre != null && nombre.matches("^[a-zA-Z][a-zA-Z0-9]*$");  
    }
}
