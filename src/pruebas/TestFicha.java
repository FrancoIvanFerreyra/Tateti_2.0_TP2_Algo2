package pruebas;

import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tateti.Ficha;

public class TestFicha {
    private Ficha ficha;

    @BeforeEach
    public void setUp() {
        try {
            ficha = new Ficha('X', Color.RED);
        } catch (Exception e) {
            System.out.println("Error al crear la ficha: " + e.getMessage());
        }
    }

    @Test
    public void testConstructorFicha() {
        Assertions.assertEquals('X', ficha.getSimbolo(), "El símbolo de la ficha debe ser X.");
    }

    @Test
    public void testEsElMismoSimbolo() {
        try {
            Ficha otraFicha = new Ficha('X', Color.RED);
            Assertions.assertTrue(ficha.esElMismoSimbolo(otraFicha), "Las fichas deberían tener el mismo símbolo.");
        } catch (Exception e) {
            System.out.println("Error al crear las fichas: " + e.getMessage());
        }
    }

    @Test
    public void testGetColor() {
        Assertions.assertTrue(ficha.getColor() == Color.RED);
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("X", ficha.toString(), "El método toString debería devolver el símbolo de la ficha como cadena.");
    }

    @Test
    public void testIncrementarBloqueosRestantes() throws Exception {
        ficha.incrementarBloqueosRestantes(3);
        Assertions.assertEquals(3, ficha.getBloqueosRestantes(), "El número de bloqueos restantes debería ser 3.");

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ficha.incrementarBloqueosRestantes(-1);
        });
        Assertions.assertEquals("Cantidad de bloqueos debe ser mayor a 0", exception.getMessage());
    }

    @Test
    public void testReducirBloqueosRestantes() throws Exception {
        ficha.incrementarBloqueosRestantes(5);
        ficha.reducirBloqueosRestantes(2);
        Assertions.assertEquals(3, ficha.getBloqueosRestantes(), "Los bloqueos restantes deberían ser 3 después de reducir 2.");

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            ficha.reducirBloqueosRestantes(0);
        });
        Assertions.assertEquals("La cantidad de bloqueos debe ser mayor a 0", exception.getMessage());

        exception = Assertions.assertThrows(Exception.class, () -> {
            ficha.reducirBloqueosRestantes(10);
        });
        Assertions.assertEquals("No se pueden quitar 10bloqueos, quedan 3", exception.getMessage());
    }

    @Test
    public void testEstaBloqueado() throws Exception {
        Assertions.assertFalse(ficha.estaBloqueado(), "La ficha no debería estar bloqueada inicialmente.");

        ficha.incrementarBloqueosRestantes(2);
        Assertions.assertTrue(ficha.estaBloqueado(), "La ficha debería estar bloqueada después de incrementar bloqueos.");

        ficha.reducirBloqueosRestantes(2);
        Assertions.assertFalse(ficha.estaBloqueado(), "La ficha no debería estar bloqueada después de reducir los bloqueos.");
    }

}