package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import estructuras.Estado;
import estructuras.Ficha;
import estructuras.Jugador;
import estructuras.TipoEstado;


public class PruebaDeFicha {
    private Jugador jugadorPrueba;
    private Estado estadoPrueba;

    @BeforeEach
    public void crearInstanciasDePrueba() {
        try {
            jugadorPrueba = new Jugador(1);
            estadoPrueba = new Estado();
            
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }

    @Test
    public void testJugadorNoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Ficha(null);
        });
        assertEquals("El jugador no puede ser nulo", exception.getMessage());
    }


    @Test
    public void testAsignacionCorrectaDeJugador() {
        try {
            Ficha ficha = new Ficha(jugadorPrueba);
            assertEquals(jugadorPrueba, ficha.obtenerJugador());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }

    @Test
    public void testAsignacionCorrectaDeEstado() {
        try {
            Ficha ficha = new Ficha(jugadorPrueba);
            assertNotNull(ficha.obtenerEstado());
            assertEquals(estadoPrueba.obtenerTipoEstado(), ficha.obtenerEstado().obtenerTipoEstado());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerJugador() {
        try {
            Ficha ficha = new Ficha(jugadorPrueba);
            assertEquals(jugadorPrueba, ficha.obtenerJugador());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }

    @Test
    public void testObtenerEstado() {
        try {
            Ficha ficha = new Ficha(jugadorPrueba);
            assertEquals(estadoPrueba.obtenerTipoEstado(), ficha.obtenerEstado().obtenerTipoEstado());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }

    @Test
    public void testCambiarEstado() {
        try {
            Ficha ficha = new Ficha(jugadorPrueba);
            ficha.obtenerEstado().bloquear();
            assertEquals(TipoEstado.BLOQUEADO, ficha.obtenerEstado().obtenerTipoEstado());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Excepción: " + e.getMessage());
        }
    }
}