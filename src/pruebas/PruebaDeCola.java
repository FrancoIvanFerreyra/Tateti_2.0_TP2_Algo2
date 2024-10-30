package pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Cola;
public class PruebaDeCola {

    @Test
    public void pruebaDeCola() throws Exception {

        //Creo la cola
        Cola<String> cola = new Cola();

        //Pruebo si se creo vacia
        Assertions.assertTrue(cola.estaVacia(), "La cola no se inicializo vacia");


        //Pruebo agregar un dato nulo
        Assertions.assertThrows(Exception.class, () -> {
            String string = null;
            cola.acolar(string);
        }, "La cola no devolvio excepcion al recibir un dato nulo");

        //Pruebo acolar un string y obtenerlo como el frente
        cola.acolar("Hola");
        Assertions.assertEquals("Hola", cola.obtener(), "El string 'Hola' no esta en el frente de la cola");


        //Pruebo acolar una lista nula
        Assertions.assertThrows(Exception.class, () -> {
            Lista<String> lista = null;
            cola.acolar(lista);
        }, "La cola no devolvio excepcion al recibir una lista nula");


        //Agrego a la cola una lista de strings y pruebo si el frente no cambio
        Lista<String> lista = new ListaSimplementeEnlazada<>();
        lista.agregar("mundo");
        lista.agregar("como");
        lista.agregar("estan");
        cola.acolar(lista);
        Assertions.assertEquals("Hola", cola.obtener(), "El string 'Hola' no esta en el frente de la cola");
        
        //Pruebo desacolar y actualizar el frente
        cola.desacolar();
        Assertions.assertEquals("mundo", cola.obtener(), "El string 'mundo' no esta en el frente de la cola luego de desacolar 'Hola'");
        
        //Pruebo el conteo de elementos
        Assertions.assertEquals(3, cola.contarElementos(), "La cola no tiene 3 elementos como se esperaba");
    }
}