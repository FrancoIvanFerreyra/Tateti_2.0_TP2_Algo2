package pruebas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cartas.CartaBloquearFicha;

import java.awt.Color;

import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tablero;

import org.junit.jupiter.api.Assertions;



class PruebaDeJugador {
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

        jugador.agregarFicha(ficha_x);
        Assertions.assertTrue(jugador.getFichas().contiene(ficha_x), "El jugador no posee la ficha como se esperaba");
    }

    @Test
    void testAgregarFichaNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            jugador.agregarFicha(null);
        }, "El jugador no devolvio NullPointerException al agregarle una ficha null");
    }

    @Test
    void testAgregarFichaExceso() throws Exception {

        for (int i = 0; i < 3; i++) {
            jugador.agregarFicha(ficha_o);
        }
        Assertions.assertThrows(IllegalArgumentException.class, () -> jugador.agregarFicha(ficha_o));
    }



    @Test
    void testTieneAlgunaFichaEnElTablero() {
        Tablero<Ficha> tablero = new Tablero<>(3,3,3);
        Assertions.assertFalse(jugador.tieneAlgunaFichaEnElTablero(tablero),
                             "El jugador tiene alguna ficha en el tablero a pesar de no tener fichas");
        Casillero<Ficha> casillero = tablero.getCasillero(1, 1, 1);
        casillero.setDato(ficha_o);
        tablero.actualizarRelacionDatoCasillero(ficha_o, casillero);
        jugador.agregarFicha(ficha_o);
        Assertions.assertTrue(jugador.tieneAlgunaFichaEnElTablero(tablero),
                                "El jugador no posee fichas en el tablero a pesar de haber colocado" + 
                                 " una ficha en el casillero (1, 1, 1)");
    }

    @Test
    void testTieneTodasLasFichasEnElTablero() {
        Tablero<Ficha> tablero = new Tablero<>(3,3,3);
        Assertions.assertFalse(jugador.tieneTodasLasFichasEnElTablero(tablero),
                             "El jugador tiene todas las fichas en el tablero a pesar de no tener fichas");
        Casillero<Ficha> casillero;
        casillero = tablero.getCasillero(1, 1, 1);
        casillero.setDato(ficha_o);
        tablero.actualizarRelacionDatoCasillero(ficha_o, casillero);
        jugador.agregarFicha(ficha_o);
        Assertions.assertEquals(1, jugador.getFichas().contarElementos(), "El jugador no tiene 1 ficha");
        casillero = tablero.getCasillero(2, 1, 1);
        casillero.setDato(ficha_o);
        tablero.actualizarRelacionDatoCasillero(ficha_o, casillero);
        jugador.agregarFicha(ficha_o);
        Assertions.assertEquals(2, jugador.getFichas().contarElementos(), "El jugador no tiene 1 ficha");
        casillero = tablero.getCasillero(3, 1, 1);
        casillero.setDato(ficha_o);
        tablero.actualizarRelacionDatoCasillero(ficha_o, casillero);
        jugador.agregarFicha(ficha_o);
        Assertions.assertEquals(3, jugador.getFichas().contarElementos(), "El jugador no tiene 1 ficha");



        Assertions.assertTrue(jugador.tieneTodasLasFichasEnElTablero(tablero),
        "El jugador no tiene todas las fichas en el tablero a pesar de colocar el maximo de fichas");

    }

    @Test
    void testQuitarCarta() {
        jugador.agregarCarta(carta1);
        jugador.quitarCarta(carta1);
        Assertions.assertFalse(jugador.getCartas().contiene(carta1),
         "El jugador aun posee la carta1 a pesar de habersela quitado previamente");
    }

    @Test
    void testQuitarCartaPosicionInvalida() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> jugador.quitarCarta(10)); // Assuming only 2 cartas are allowed
    }

    @Test
    void testQuitarCartaNull() {
        Assertions.assertThrows(NullPointerException.class, () -> jugador.quitarCarta( null));
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
        Assertions.assertEquals(ficha_x, jugador.getFicha(1));
    }

    @Test
    void testGetFichaIndiceInvalido() {
        Assertions.assertThrows(IllegalArgumentException.class,
         () -> jugador.getFicha(10),
        "El jugador no lanzo excepcion al intentar obtener una ficha que no existe");
    }
}
