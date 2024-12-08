package interfaz;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;
import java.util.InputMismatchException;
import utiles.ValidacionesUtiles;
public class Consola {

    /**
     * Imprime el mensaje en la terminal y luego salta de linea
     * @param mensaje
     */
    public static void imprimirMensajeConSalto(String mensaje)
    {
        System.out.println(mensaje);
    }

        /**
     * Imprime el mensaje en la terminal
     * @param mensaje
     */
    public static void imprimirMensaje(String mensaje)
    {
        System.out.print(mensaje);
    }

    /**
     * Imprime en la terminal un menu de opciones, que consta de un titulo y opciones numeradas
     * de 1 a n siendo n el largo de la lista de opciones. Solo se imprimiran los elementos T 
     * de la lista que posean el metodo toString(). Si tieneOpcionParaVolver es verdadero, imprime
     * una linea extra con indice 0 para volver al menu anterior
     * @param <T>
     * @param opciones no puede ser null, no debe estar vacio
     * @param titulo no debe ser null
     * @param tieneOpcionParaVolver determina si el usuario tendra o no una opcion para
     *                              volver al menu anterior
     * @throws NullPointerException si opciones o titulo son null
     * @throws IllegalArgumentException si opciones esta vacio
     */
   public static <T> void imprimirMenuDeOpciones(Lista<T> opciones, String titulo,
                                                 boolean tieneOpcionParaVolver)
                                                 throws NullPointerException,
                                                    IllegalArgumentException{
       ValidacionesUtiles.validarNoNull(opciones, "opciones");
       ValidacionesUtiles.validarNoNull(titulo, "titulo");
       if(opciones.estaVacia())
       {
            throw new IllegalArgumentException("Opciones no puede estar vacio");
       }
        String resultado = titulo + "\n";
       for (int i = 1; i <= opciones.getTamanio(); i++) 
       {
           T opcion = opciones.obtener(i);
           
           // Verificar si el tipo T tiene un método toString
           if (ValidacionesUtiles.tieneMetodoToString(opcion)) {
               resultado += i + "- " + opcion.toString() + "\n";  // Si tiene, imprime la cadena
           }
       }
       if(tieneOpcionParaVolver)
       {
        resultado += "0- Volver al menu anterior\n";
        }
       imprimirMensajeConSalto(resultado);
   }

    /**
     * Llama a imprimirMenuDeOpciones con sus parametros y obtiene el indice elegido por el usuario
     * @param <T>
     * @param opciones no puede ser null, no debe estar vacio
     * @param titulo no debe ser null
     * @param tieneOpcionParaVolver determina si el usuario tendra o no una opcion para
     *                              volver al menu anterior
     * @return devuelve el elemento T elegido por el usuario. Devuelve null si tieneOpcionParaVolver es
     *          verdadero y el usuario elige la opcion de volver al menu anterior
     * @throws NullPointerException si opciones o titulo son null
     * @throws IllegalArgumentException si opciones esta vacio
     * */
   public static <T> T consultarOpcionAlUsuario(Lista<T> opciones, String titulo,
                                             boolean tieneOpcionParaVolver)
                                              throws NullPointerException,
                                              IllegalArgumentException
   {
        ValidacionesUtiles.validarNoNull(opciones, "opciones");
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        if(opciones.estaVacia())
        {
            throw new IllegalArgumentException("Opciones no puede estar vacio");
        }
        imprimirMenuDeOpciones(opciones, titulo, tieneOpcionParaVolver);
        int indiceElegido = obtenerNumeroEnteroEnRangoDelUsuario("Opcion seleccionada:",
                            (tieneOpcionParaVolver) ? 0 : 1,
                            opciones.getTamanio());
        return (indiceElegido == 0) ? null : opciones.obtener(indiceElegido);
   }

    /**
     * Transforma el vector de opciones a una lista de opciones y llama a consultarOpcionAlUsuario
     * con ella
     * @param <T>
     * @param opciones no puede ser null, no debe estar vacio
     * @param titulo no debe ser null
     * @param tieneOpcionParaVolver determina si el usuario tendra o no una opcion para
     *                              volver al menu anterior
     * @return devuelve el elemento T elegido por el usuario. Devuelve null si tieneOpcionParaVolver es
     *          verdadero y el usuario elige la opcion de volver al menu anterior
     * @throws NullPointerException si opciones o titulo son null
     * @throws IllegalArgumentException si opciones esta vacio
     * */
   public static <T> T consultarOpcionAlUsuario(Vector<T> opciones, String titulo,
                                                 boolean tieneOpcionParaVolver)
                                                  throws NullPointerException,
                                                  IllegalArgumentException
   {
        ValidacionesUtiles.validarNoNull(opciones, "opciones");
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        if(opciones.contarElementos() == 0)
        {
            throw new IllegalArgumentException("Opciones no puede estar vacio");
        }
        Lista<T> lista = new ListaSimplementeEnlazada<>();
        lista.agregar(opciones);
        return consultarOpcionAlUsuario(lista, titulo, tieneOpcionParaVolver);
   }

   /**
    * 
    * @param titulo no debe ser null
    * @return devuelve una cadena con la linea escrita por teclado por el usuario
    * @throws NullPointerException si titulo es null
    */
    public static String obtenerStringDelUsuario(String titulo) throws NullPointerException {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        System.out.println(titulo + "\t");
        return Teclado.leerLinea();
    }

    /**
     * 
     * @param titulo no debe ser null
     * @return devuelve un numero entero ingresado por teclado por el usuario
     * @throws NullPointerException si titulo es null
     */
    public static int obtenerNumeroEnteroDelUsuario(String titulo) throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        boolean ingresoNumero = false;
        int numero = 0;
        do { 
            try {
                System.out.println(titulo + "\t");
                numero = Teclado.leerEntero();
                ingresoNumero = true;
            } catch (InputMismatchException e) {
                imprimirMensajeConSalto("Debe ingresar un numero");
                Teclado.limpiarEntrada();
            }
        } while (!ingresoNumero);
        return numero;
    }

    /**
     * 
     * @param titulo no debe ser null
     * @param tieneOpcionParaVolver determina si el usuario tiene o no la opcion de volver al 
     *          menu anterior
     * @return devuelve verdadero si el usuario elige la opcion SI de un menu de opciones con
     *         SI y NO, donde titulo es la pregunta
     * @throws NullPointerException si titulo es null
     */
    public static Boolean obtenerConfirmacionDelUsuario(String titulo,
                                     boolean tieneOpcionParaVolver) throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        Lista<String> opciones = new ListaSimplementeEnlazada<>();
        opciones.agregar("SI");
        opciones.agregar("NO");
        String resultado = consultarOpcionAlUsuario(opciones, titulo, tieneOpcionParaVolver);
        return (resultado == null) ? null : "SI".equals(resultado);
    }


    /**
     * 
     * @param titulo no debe ser null
     * @return devuelve verddadero si el usuario elige la opcion SI de un menu de opciones con
     *         SI y NO, donde titulo es la pregunta. Devuelve null si el usuario eligio volver
     *         al menu anterior
     * @throws NullPointerException si titulo es null
     */
    public static Boolean obtenerConfirmacionORetornoDelUsuario(String titulo) throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        Lista<String> opciones = new ListaSimplementeEnlazada<>();
        opciones.agregar("SI");
        opciones.agregar("NO");
        String resultado = consultarOpcionAlUsuario(opciones, titulo, true);
        if(resultado == null)
        {
            return null;
        }
        return "SI".equals(resultado);
    }

    /**
     * 
     * @param titulo no debe ser null
     * @param minimoValido debe ser menor o igual a maximoValido
     * @param maximoValido debe ser mayor o igual a minimoValido
     * @return devuelve el numero entero escrito por teclado por el usuario dentro del
     *          rango [minimoValido, maximoValido]
     * @throws NullPointerException si titulo es null
     * @throws IllegalArgumentException si minimoValido es mayor a maximoValido
     */
    public static int obtenerNumeroEnteroEnRangoDelUsuario(String titulo, int minimoValido,
                                                         int maximoValido)
                                                         throws NullPointerException,
                                                         IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        if(!ValidacionesUtiles.esMenorOIgualQue(minimoValido, minimoValido))
        {
            throw new IllegalArgumentException("El minimo es mayor al maximo");
        }
        int numero = obtenerNumeroEnteroDelUsuario(titulo);
        while(!ValidacionesUtiles.estaEntre(numero, minimoValido, maximoValido))
        {
            imprimirMensajeConSalto("Por favor ingrese un numero entre " + minimoValido + " y " + maximoValido + "\t");
            numero = obtenerNumeroEnteroDelUsuario(titulo);
        }
        return numero;
    }

    /**
     * 
     * @param titulo no debe ser null
     * @param minimoValido
     * @return devuelve un entero escrito por teclado por el usuario que sea mayor o igual a
     *         minimoValido
     * @throws NullPointerException si titulo es null
     */
    public static int obtenerNumeroEnteroEnRangoMinimoDelUsuario(String titulo, int minimoValido)
                                                                throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        int numero;
        do { 
            numero = obtenerNumeroEnteroDelUsuario(titulo);
            if(!ValidacionesUtiles.esMayorOIgualQue(numero, minimoValido))
            {
                imprimirMensajeConSalto("Por favor ingrese un numero mayor o igual que " + minimoValido + "\t");
            }
        } while (!ValidacionesUtiles.esMayorOIgualQue(numero, minimoValido));
        return numero;
    }

    /**
     * 
     * @param titulo no debe ser null
     * @param maximoValido
     * @return devuelve un entero escrito por teclado por el usuario que sea menor o igual a
     *         maximoValido
     * @throws NullPointerException si titulo es null
     */

    public static int obtenerNumeroEnteroEnRangoMaximoDelUsuario(String titulo, int maximoValido)
                                                            throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        int numero;
        do { 
            numero = obtenerNumeroEnteroDelUsuario(titulo);
            if(!ValidacionesUtiles.esMenorOIgualQue(numero, maximoValido))
            {
                imprimirMensajeConSalto("Por favor ingrese un numero menor o igual que " + maximoValido + "\t");
            }
        } while (!ValidacionesUtiles.esMenorOIgualQue(numero, maximoValido));
        return numero;
    }

    /**
     * Limpia la terminal
     */
    public static void limpiar() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}


