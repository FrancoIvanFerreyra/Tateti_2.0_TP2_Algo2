package pruebas;

import interfaz.Consola;
import java.awt.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import tateti.Ficha;

public class PruebaDeFicha {
    private Ficha ficha;

    @BeforeEach
    public void crearInstanciasDePrueba() {
        try
        {
            ficha = new Ficha("1");            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testFichaConSimboloNulo() {
        Assertions.assertThrows(Exception.class, () -> {
            new Ficha(null);
        }, "La ficha no lanzo excepcion al crearla con un simbolo null");
    }

    @Test
    public void testCompararFichasMismoSimbolo() {
        Ficha ficha2 = null;
        try
        {
            ficha2 = new Ficha("1");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Assertions.assertTrue(ficha.esElMismoSimbolo(ficha2), "Las fichas del mismo simbolo no se reconocen como iguales");
    }

    @Test
    public void testIncrementarBloqueoFicha()
    {
        try
        {
            ficha.incrementarBloqueosRestantes(1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Assertions.assertTrue(ficha.estaBloqueado(), "La ficha no se bloqueo al agregarle 1 bloqueo");
    }

    @Test
    public void testIncrementarConNegativoBloqueoFicha()
    {
        Assertions.assertThrows(IllegalArgumentException.class,

            () -> {ficha.incrementarBloqueosRestantes(-1);},
            "La ficha no lanzo excepcion al intentar agregarle bloqueos negativos"); 
    }
}