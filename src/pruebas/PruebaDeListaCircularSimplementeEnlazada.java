package pruebas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import estructuras.ListaCircularSimplementeEnlazada;

public class PruebaDeListaCircularSimplementeEnlazada extends PruebaDeListaCircular{

    @BeforeEach
    @Override
    void inicializar() {
        lista = new ListaCircularSimplementeEnlazada<>();
    }

    @Test
    public void pruebaDeListaCircularSimplementeEnlazada() throws Exception{
        super.pruebaDeListaCircular();
    }


}
