package interfaz;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;

import java.lang.reflect.Method;
public class Consola {

    public static void imprimirMensaje(String mensaje)
    {
        System.out.println(mensaje);
    }

   public static <T> String imprimirMenuDeOpciones(Lista<T> opciones, String titulo) throws Exception{
        String resultado = titulo + "\n";
       for (int i = 1; i <= opciones.getTamanio(); i++) 
       {
           T opcion = opciones.obtener(i);
           
           // Verificar si el tipo T tiene un método toString
           if (tieneMetodoToString(opcion)) {
               resultado += i + "- " + opcion.toString() + "\n";  // Si tiene, imprime la cadena
           }
       }
       System.out.println(resultado);
       return resultado;
   }

   public static <T> T consultarOpcionAlUsuario(Lista<T> opciones, String titulo) throws Exception
   {
        imprimirMenuDeOpciones(opciones, titulo);
        int indiceElegido = obtenerNumeroEnteroDelUsuario("Opcion seleccionada:");
        return opciones.obtener(indiceElegido);
   }

   public static <T> T consultarOpcionAlUsuario(Vector<T> opciones, String titulo) throws Exception
   {
        Lista<T> lista = new ListaSimplementeEnlazada<T>();
        lista.agregar(opciones);
        return consultarOpcionAlUsuario(lista, titulo);
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
        System.out.println(titulo + "\t");
        return Teclado.leerEntero();
    }

    public static void limpiar() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}


