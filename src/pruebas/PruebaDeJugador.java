package pruebas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cartas.Carta;
import cartas.CartaBloquearFicha;

import java.awt.Color;

import tateti.Ficha;
import tateti.Jugador;

import org.junit.jupiter.api.Assertions;



class JugadorTest {
    private Jugador jugador;
    private Ficha ficha_x;
    private Ficha ficha_o;
    private CartaBloquearFicha carta1;

    @BeforeEach
    void setUp() throws Exception {
        jugador = new Jugador("Jugador1", 3, Color.RED, 2);
        ficha_x = new Ficha("X");
        ficha_o = new Ficha("O");
        carta1 = new CartaBloquearFicha();
    }

    @Test
    void testConstructor() throws Exception {
        Assertions.assertNotNull(jugador);
        Assertions.assertEquals("Jugador1", jugador.getNombre());
        Assertions.assertEquals(Color.RED, jugador.getColor());
        Assertions.assertEquals(3, jugador.getFichas().getLongitud());
        Assertions.assertEquals(2, jugador.getCartas().getLongitud());
    }

    @Test
    void testAgregarFicha() {
        if(ficha_x != null && jugador.getFichas().contarElementos() == jugador.getFichas().getLongitud())
        {
            jugador.agregarFicha(ficha_x);
            Assertions.assertTrue(jugador.getFichas().contiene(ficha_x), "El jugador no posee la ficha como se esperaba");
        }
    }

    @Test
    void testAgregarFichaNull() {
        Assertions.assertThrows(Exception.class, () -> {
            jugador.agregarFicha(null);
        });
    }

    @Test
    void testAgregarFichaExceso() throws Exception {

        for (int i = 0; i < 3; i++) {
            jugador.agregarFicha(ficha_o);
        }
        Assertions.assertThrows(Exception.class, () -> jugador.agregarFicha(ficha_o));
    }

    @Test
    void testTieneTodasLasFichasEnElTablero() {
        Assertions.assertTrue(jugador.tieneTodasLasFichasEnElTablero());
    }

    @Test
    void testTieneAlgunaFichaEnElTablero() {
        Assertions.assertTrue(jugador.tieneAlgunaFichaEnElTablero());
    }

    @Test
    void testQuitarCarta() throws Exception {
        jugador.agregarCarta(carta1);
        jugador.quitarCarta(carta1);
        Assertions.assertFalse(jugador.getCartas().contiene(carta1));
    }

    @Test
    void testQuitarCartaPosicionInvalida() {
        Assertions.assertThrows(Exception.class, () -> jugador.quitarCarta(10)); // Assuming only 2 cartas are allowed
    }

    @Test
    void testQuitarCartaNull() {
        Assertions.assertThrows(Exception.class, () -> jugador.quitarCarta((Carta) null));
    }

    @Test
    void testGetNombre() {
        Assertions.assertEquals("Jugador1", jugador.getNombre());
    }

    @Test
    void testGetColor() {
        Assertions.assertEquals(Color.RED, jugador.getColor());
    }

    @Test
    void testGetFicha() throws Exception {
        jugador.agregarFicha(ficha_x);
        Assertions.assertEquals(ficha_x, jugador.getFicha(0));
    }

    @Test
    void testGetFichaIndiceInvalido() {
        Assertions.assertThrows(Exception.class, () -> jugador.getFicha(10));
    }
}
