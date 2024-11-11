package pruebas;

import org.junit.jupiter.api.Assertions;


public abstract class PruebaDeListaLineal extends PruebaDeLista{


    protected void pruebaDeListaLineal() throws Exception{
        super.pruebaDeListaGenerica();
        lista.iniciarCursor();
        for (int i = 0; i < 5; i++) {
            lista.avanzarCursor();
        }
        Assertions.assertEquals(null, lista.obtenerCursor(), "El ultimo elemento de la lista no apunta a null");
    }


}
