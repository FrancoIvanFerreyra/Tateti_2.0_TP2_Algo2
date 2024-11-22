package pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Pila;
public class PruebaDePila {

    @Test
    public void pruebaDePila() throws Exception {

        //Creo la pila
        Pila<String> pila = new Pila();

        //Pruebo si se creo vacia
        Assertions.assertTrue(pila.estaVacia(), "La pila no se inicializo vacia");


        //Pruebo agregar un dato nulo
        Assertions.assertThrows(Exception.class, () -> {
            String string = null;
            pila.apilar(string);
        }, "La pila no devolvio excepcion al recibir un dato nulo");

        //Pruebo apilar un string y obtenerlo como el tope
        pila.apilar("Hola");
        Assertions.assertEquals("Hola", pila.obtener(), "El string 'Hola' no esta en el tope de la pila");


        //Pruebo apilar una lista nula
        Assertions.assertThrows(Exception.class, () -> {
            Lista<String> lista = null;
            pila.apilar(lista);
        }, "La pila no devolvio excepcion al recibir una lista nula");


        //Apilo una lista de string y pruebo si el tope es el ultimo elemento de la lista
        Lista<String> lista = new ListaSimplementeEnlazada<>();
        lista.agregar("mundo");
        lista.agregar("como");
        lista.agregar("estan");
        pila.apilar(lista);
        Assertions.assertEquals("estan", pila.obtener(), "El string 'estan' no esta en el tope de la pila");
        
        //Pruebo desapilar y actualizar el tope
        pila.desapilar();
        Assertions.assertEquals("como", pila.obtener(), "El string 'como' no esta en el tope de la pila luego de desapilar 'estan'");
        
        //Pruebo el conteo de elementos
        Assertions.assertEquals(3, pila.contarElementos(), "La pila no tiene 3 elementos como se esperaba");
    }
}
