package interfaz;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;
import java.lang.reflect.Method;
import java.util.InputMismatchException;
import utiles.ValidacionesUtiles;
public class Consola {

    public static void imprimirMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }

   public static <T> String imprimirMenuDeOpciones(Lista<T> opciones, String titulo, boolean tieneOpcionParaVolver) throws Exception{
        String resultado = titulo + "\n";
       for (int i = 1; i <= opciones.getTamanio(); i++) 
       {
           T opcion = opciones.obtener(i);
           
           // Verificar si el tipo T tiene un método toString
           if (tieneMetodoToString(opcion)) {
               resultado += i + "- " + opcion.toString() + "\n";  // Si tiene, imprime la cadena
           }
       }
       if(tieneOpcionParaVolver)
       {
        resultado += "0- Volver atras\n";
        }
       System.out.println(resultado);
       return resultado;
   }

   public static <T> T consultarOpcionAlUsuario(Lista<T> opciones, String titulo, boolean tieneOpcionParaVolver) throws Exception
   {
        if(opciones.estaVacia())
        {
            throw new Exception("Debe existir al menos una opcion");
        }
        imprimirMenuDeOpciones(opciones, titulo, tieneOpcionParaVolver);
        int indiceElegido = obtenerNumeroEnteroEnRangoDelUsuario("Opcion seleccionada:",
                            (tieneOpcionParaVolver) ? 0 : 1,
                            opciones.getTamanio());
        return (indiceElegido == 0) ? null : opciones.obtener(indiceElegido);
   }

   public static <T> T consultarOpcionAlUsuario(Vector<T> opciones, String titulo, boolean tieneOpcionParaVolver) throws Exception
   {
        Lista<T> lista = new ListaSimplementeEnlazada<T>();
        lista.agregar(opciones);
        return consultarOpcionAlUsuario(lista, titulo, tieneOpcionParaVolver);
   }

   
   // Método auxiliar para verificar si el tipo T tiene un método toString
   private static <T> boolean tieneMetodoToString(T objeto) {
       try {
           Method metodo = objeto.getClass().getMethod("toString");
           return metodo != null;
       } catch (NoSuchMethodException e) {
           return false;  // No tiene método toString
       }
   }

    public static String obtenerStringDelUsuario(String titulo) {
        System.out.println(titulo + "\t");
        return Teclado.leerLinea();
    }

    public static int obtenerNumeroEnteroDelUsuario(String titulo)
    {
        boolean ingresoNumero = false;
        int numero = 0;
        do { 
            try {
                System.out.println(titulo + "\t");
                numero = Teclado.leerEntero();
                ingresoNumero = true;
            } catch (InputMismatchException e) {
                imprimirMensaje("Debe ingresar un numero");
                Teclado.limpiarEntrada();
            }
        } while (!ingresoNumero);
        return numero;
    }

    public static boolean obtenerConfirmacionDelUsuario(String titulo) throws Exception
    {
        Lista<String> opciones = new ListaSimplementeEnlazada<String>();
        opciones.agregar("SI");
        opciones.agregar("NO");
        return "SI".equals(consultarOpcionAlUsuario(opciones, titulo, false));
    }

    public static Boolean obtenerConfirmacionORetornoDelUsuario(String titulo) throws Exception
    {
        Lista<String> opciones = new ListaSimplementeEnlazada<String>();
        opciones.agregar("SI");
        opciones.agregar("NO");
        String resultado = consultarOpcionAlUsuario(opciones, titulo, true);
        if(resultado == null)
        {
            return null;
        }
        return "SI".equals(resultado);
    }

    public static int obtenerNumeroEnteroEnRangoDelUsuario(String titulo, int minimoValido, int maximoValido)
    {
        int numero = obtenerNumeroEnteroDelUsuario(titulo);
        while(!ValidacionesUtiles.estaEntre(numero, minimoValido, maximoValido))
        {
            imprimirMensaje("Por favor ingrese un numero entre " + minimoValido + " y " + maximoValido + "\t");
            numero = obtenerNumeroEnteroDelUsuario(titulo);
        }
        return numero;
    }

    public static int obtenerNumeroEnteroEnRangoMinimoDelUsuario(String titulo, int minimoValido)
    {
        int numero;
        do { 
            numero = obtenerNumeroEnteroDelUsuario(titulo);
            if(!ValidacionesUtiles.esMayorOIgualQue(numero, minimoValido))
            {
                imprimirMensaje("Por favor ingrese un numero mayor o igual que " + minimoValido + "\t");
            }
        } while (!ValidacionesUtiles.esMayorOIgualQue(numero, minimoValido));
        return numero;
    }

    public static int obtenerNumeroEnteroEnRangoMaximoDelUsuario(String titulo, int maximoValido)
    {
        int numero;
        do { 
            numero = obtenerNumeroEnteroDelUsuario(titulo);
            if(!ValidacionesUtiles.esMenorOIgualQue(numero, maximoValido))
            {
                imprimirMensaje("Por favor ingrese un numero menor o igual que " + maximoValido + "\t");
            }
        } while (!ValidacionesUtiles.esMenorOIgualQue(numero, maximoValido));
        return numero;
    }

    public static void limpiar() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}


