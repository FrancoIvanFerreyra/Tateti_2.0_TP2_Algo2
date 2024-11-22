package utiles;

import java.lang.reflect.Method;
import java.nio.file.Paths;

public class ValidacionesUtiles {
    
    /**
     * 
     * @param numero
     * @param minimo
     * @return devuelve verdadero si numero es mayor que minimo
     */
    public static boolean esMayorQue(int numero, int minimo)
    {
        return numero > minimo;
    }

    /**
     * 
     * @param numero
     * @param minimo
     * @return devuelve verdadero si numero es mayor o igual que minimo
     */
    public static boolean esMayorOIgualQue(int numero, int minimo)
    {
        return numero >= minimo;
    }

    /**
     * 
     * @param numero
     * @param maximo
     * @return devuelve verdadero si numero es menor que maximo
     */
    public static boolean esMenorQue(int numero, int maximo)
    {
        return numero < maximo;
    }

    /**
     * 
     * @param numero
     * @param maximo
     * @return devuelve verdadero si numero es menor o igual que maximo
     */
    public static boolean esMenorOIgualQue(int numero, int maximo)
    {
        return numero <= maximo;
    }

    /**
     * 
     * @param numero
     * @param minimo
     * @param maximo
     * @return devuelve verdadero si numero esta dentro del rango [minimo, maximo]
     * @throws IllegalArgumentException si minimo es mayor a maximo
     */
    public static boolean estaEntre(int numero, int minimo, int maximo) throws IllegalArgumentException
    {
        if(esMayorQue(minimo, maximo))
        {
            throw new IllegalArgumentException("Minimo es mayor a maximo");
        }
        return numero >= minimo && numero <= maximo;
    }

        /**
     * 
     * @param numero
     * @param minimo
     * @param maximo
     * @return devuelve verdadero si numero esta estrictamente dentro del rango (minimo, maximo)
     * @throws IllegalArgumentException si minimo es mayor a maximo
     */
    public static boolean estaEstrictamenteEntre(int numero, int minimo, int maximo)
    {
        if(esMayorQue(minimo, maximo))
        {
            throw new IllegalArgumentException("Minimo es mayor a maximo");
        }
        return numero > minimo && numero < maximo;
    }

    /**
     * Evalua si un string es un nombre valido
     * @param nombre 
     * @return devuelve verdadero si el nombre no es null e inicia con una letra
     */
    public static boolean esNombreValido(String nombre)
    {
        return nombre != null && nombre.matches("^[a-zA-Z][a-zA-Z0-9]*$");  
    }

    /**
     * Lanza excepcion si entero es menor a minimo
     * @param entero
     * @param minimo
     * @param nombre no puede ser null
     * @throws IllegalArgumentException si entero es menor a minimo
     * @throws NullPointerException si nombre es null
     */
    public static void validarEnteroMinimo(int entero, int minimo,
                                         String nombre) throws IllegalArgumentException,
                                         NullPointerException
    {
        if(!esMayorOIgualQue(entero, minimo))
        {
            throw new IllegalArgumentException(nombre + "debe ser mayor o igual a " + minimo);
        }
    }

        /**
     * Lanza excepcion si entero es mayor a maximo
     * @param entero
     * @param minimo
     * @param nombre no puede ser null
     * @throws IllegalArgumentException si entero es mayor a maximo
     * @throws NullPointerException si nombre es null
     */
    public static void validarEnteroMaximo(int entero, int maximo,
                                     String nombre) throws IllegalArgumentException,
                                     NullPointerException
    {
        validarNoNull(nombre, "nombre");
        if(!esMenorOIgualQue(entero, maximo))
        {
            throw new IllegalArgumentException(nombre + "debe ser menor o igual a " + maximo);
        }
    }

    /**
     * Lanza excepcion si entero no esta en el rango [minimo, maximo]
     * @param entero
     * @param minimo debe ser menor o igual a maximo
     * @param maximo debe ser mayor o igual a minimo
     * @param nombre no debe ser null
     * @throws IllegalArgumentException si minimo es mayor a maximo o si entero
     *                                  no esta en el rango [minimo, maximo]
     * @throws NullPointerException si nombre es null
     */
    public static void validarEnteroEnRango(int entero, int minimo,
                                             int maximo, String nombre)
                                            throws IllegalArgumentException,
                                            NullPointerException
    {
        validarNoNull(nombre, "nombre");
        validarEnteroMinimo(entero, minimo, nombre);
        validarEnteroMaximo(entero, maximo, nombre);
    }

    /**
     * Lanza una excepcion si el objeto es null
     * @param <T>
     * @param objeto
     * @param nombre no debe ser null
     * @throws NullPointerException si objeto o nombre son null
     */
    public static <T> void validarNoNull(T objeto, String nombre) throws NullPointerException
    {
        if(objeto == null)
        {
            throw new NullPointerException(nombre + " no puede ser null");
        }
    }

    /**
     * Valida una ruta relativa
     * @param ruta no debe ser null, ni estar vacia ni tener caracteres no validos para rutas.
     *             Debe ser una ruta relativa
     * @throws NullPointerException si ruta es null
     * @throws IllegalArgumentException si ruta esta vacia o tiene caracteres no validos para rutas,
     *                                  o es una ruta absoluta
     */
    public static void validarRutaRelativa(String ruta) throws NullPointerException,
                                                            IllegalArgumentException
    {
        validarNoNull(ruta, "ruta");
        if (ruta.trim().isEmpty()) {
            throw new IllegalArgumentException("La ruta no puede estar vacía.");
        }
        if (Paths.get(ruta).isAbsolute()) {
            throw new IllegalArgumentException("La ruta debe ser relativa.");
        }
        if (!ruta.matches("^[\\w\\-. ]+$")) {
            throw new IllegalArgumentException("La ruta contiene caracteres no válidos.");
        }
    }

        /**
    * 
    * @param <T>
    * @param objeto no puede ser null
    * @return devuelve veradero si el objeto puede ejecutar el metodo toString()
    */
   public static <T> boolean tieneMetodoToString(T objeto) {
    ValidacionesUtiles.validarNoNull(objeto, "objeto");
       try {
           Method metodo = objeto.getClass().getMethod("toString");
           return metodo != null;
       } catch (NoSuchMethodException e) {
           return false;  // No tiene método toString
       }
   }
}
