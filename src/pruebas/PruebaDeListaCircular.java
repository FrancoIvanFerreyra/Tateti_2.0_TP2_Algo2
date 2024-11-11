package pruebas;

import org.junit.jupiter.api.Assertions;


public abstract class PruebaDeListaCircular extends PruebaDeLista{

    protected void pruebaDeListaCircular() throws Exception{
        super.pruebaDeListaGenerica();
        lista.iniciarCursor();
        for (int i = 0; i < 5; i++) {
            lista.avanzarCursor();
        }
        Assertions.assertEquals(lista.obtener(1), lista.obtenerCursor(), "El ultimo elemento de la lista no apunta al primero");
    }


}