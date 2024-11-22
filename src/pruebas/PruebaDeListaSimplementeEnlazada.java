package pruebas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import estructuras.ListaSimplementeEnlazada;

public class PruebaDeListaSimplementeEnlazada extends PruebaDeListaLineal{

    @BeforeEach
    @Override
    void inicializar() {
        lista = new ListaSimplementeEnlazada<>();
    }

    @Test
    public void pruebaDeListaSimplementeEnlazada() throws Exception{
        super.pruebaDeListaLineal();
    }


}
