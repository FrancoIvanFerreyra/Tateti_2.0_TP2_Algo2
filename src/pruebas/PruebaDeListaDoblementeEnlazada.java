package pruebas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import estructuras.ListaDoblementeEnlazada;

public class PruebaDeListaDoblementeEnlazada extends PruebaDeListaLineal{

    @BeforeEach
    @Override
    void inicializar() {
        lista = new ListaDoblementeEnlazada<>();
    }

    @Test
    public void pruebaDeListaDoblementeEnlazada() throws Exception{
        super.pruebaDeListaLineal();
    }


}
