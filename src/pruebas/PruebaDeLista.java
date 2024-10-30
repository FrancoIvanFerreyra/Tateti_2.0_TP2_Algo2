package pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import estructuras.Lista;


public abstract class PruebaDeLista{

    protected Lista<String> lista;

    @BeforeEach
    abstract void inicializar();

    protected void pruebaDeListaGenerica() throws Exception {
        Assertions.assertTrue(lista.estaVacia(), "La lista recien creada no esta vacia");
        Assertions.assertThrows(Exception.class, () -> {
            lista.agregar("Hola", 2);
        }, "La lista no devolvio excepcion al intentar agregar un dato en un indice inexistente");
        Assertions.assertThrows(Exception.class, () -> {
            lista.remover(2);
        }, "La lista no devolvio excepcion al intentar remover un dato en un indice inexistente");

        lista.iniciarCursor();
        Assertions.assertTrue(!lista.avanzarCursor(), "La lista no devolvio excepcion al intentar avanzar el cursor sin tener elementos");
        
        lista.agregar("Hola");
        lista.agregar("mundo");
        lista.agregar("como");
        lista.agregar("estan");

        Assertions.assertEquals(4, lista.getTamanio(),
        "La lista no posee 4 elementos como se esperaba");

        Assertions.assertEquals("como", lista.obtener(3),
         "La lista devolvio un dato diferente al esperado en la posicion 3");
        Assertions.assertThrows(Exception.class, () -> {
            lista.cambiar("universo", -1);
        }, "La lista no devolvio excepcion al intentar cambiar un dato en un indice inexistente");
        lista.avanzarCursor();
        lista.avanzarCursor();
        Assertions.assertEquals("mundo", lista.obtenerCursor(),
        "La lista devolvio un cursor diferente al esperado ('mundo')");
    }
}
