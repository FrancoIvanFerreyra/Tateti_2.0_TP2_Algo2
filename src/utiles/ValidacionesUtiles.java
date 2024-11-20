package utiles;
import estructuras.ListaEnlazada;

public class ValidacionesUtiles {
    
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

    public static void validarEnteroMinimo(int entero, int minimo, String nombre) throws Exception
    {
        if(!esMayorOIgualQue(entero, minimo))
        {
            throw new Exception(nombre + "debe ser mayor o igual a " + minimo);
        }
    }

    public static void validarEnteroMaximo(int entero, int maximo, String nombre) throws Exception
    {
        if(!esMenorOIgualQue(entero, maximo))
        {
            throw new Exception(nombre + "debe ser menor o igual a " + maximo);
        }
    }

    public static void validarEnteroEnRango(int entero, int minimo, int maximo, String nombre) throws Exception
    {
        validarEnteroMinimo(entero, minimo, nombre);
        validarEnteroMaximo(entero, maximo, nombre);
    }

    public static <T> void validarNoNull(T objeto, String nombre) throws Exception
    {
        if(objeto == null)
        {
            throw new Exception(nombre + "no puede ser null");
        }
    }
}
