package pruebas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import estructuras.ListaCircularDoblementeEnlazada;

public class PruebaDeListaCircularDoblementeEnlazada extends PruebaDeListaCircular{

    @BeforeEach
    @Override
    void inicializar() {
        lista = new ListaCircularDoblementeEnlazada<>();
    }

    @Test
    public void pruebaDeListaCircularDoblementeEnlazada() throws Exception{
        super.pruebaDeListaCircular();
    }


}