package pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cartas.CartaCambiarColorFicha;

import java.awt.Color;

import tateti.Ficha;
import tateti.Jugador;
import cartas.Carta;
import cartas.CartaBloquearFicha;


class TestJugador {
    private Jugador jugador_1;
    private Jugador jugador_2;
    private Ficha ficha_rojo;
    private Ficha ficha_verde;

    private CartaBloquearFicha carta1;
    private CartaCambiarColorFicha carta2;


    @BeforeEach
    void setUp() throws Exception {
        jugador_1 = new Jugador("Juan", 3, Color.RED, 2);
        jugador_2 = new Jugador("Carlos", 3, Color.RED, 2);
        ficha_rojo = new Ficha("Rojo");
        ficha_verde = new Ficha("Verde");
        carta1 = new CartaBloquearFicha();
        carta2 = new CartaCambiarColorFicha();
    }

    @Test
    void testAgregarFicha() throws Exception {
        jugador_1.agregarFicha(ficha_rojo);
        Assertions.assertTrue(jugador_1.getFichas().contiene(ficha_rojo));

        jugador_2.agregarFicha(ficha_verde);
        Assertions.assertTrue(jugador_2.getFichas().contiene(ficha_verde));
    }

    @Test
    void testAgregarFichaNull() {
        Assertions.assertThrows(Exception.class, () -> jugador_1.agregarFicha(null));
    }

    @Test
    void testAgregarFichaExceso() throws Exception {
        for (int i = 0; i < 3; i++) {
            jugador_1.agregarFicha(ficha_rojo);
        }
        Assertions.assertThrows(Exception.class, () -> jugador_1.agregarFicha(ficha_rojo));
    }


    @Test
    void testQuitarFichaNull() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            jugador_1.quitarFicha(null);
        });
        Assertions.assertEquals("La Ficha no puede ser null", exception.getMessage());
    }
    
    @Test
    void testQuitarFichaCorrectamente() throws Exception {
        jugador_1.agregarFicha(ficha_rojo);
        jugador_1.quitarFicha(ficha_rojo);
    }
    
    @Test
    void testTieneAlgunaFichaEnElTablero() throws Exception {
        jugador_1.agregarFicha(ficha_rojo);
        Assertions.assertTrue(jugador_1.tieneAlgunaFichaEnElTablero());
    }

    @Test
    void testQuitarFichaNoPosee() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            jugador_1.quitarFicha(ficha_verde); // no posee esa ficha
        });
        
        Assertions.assertEquals("El jugador no posee esa Ficha", exception.getMessage());
    }

    @Test
    void testQuitarFichaPosicionNoExistente() throws Exception {
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            jugador_2.quitarFicha(3);
        });
        
        Assertions.assertEquals("No existe una ficha en esa posicion", exception.getMessage());
    }

    @Test
    void testQuitarFichaPosicionFueraDeRango() throws Exception {     
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            jugador_2.quitarFicha(0);
        });

        Assertions.assertEquals("La posicion debe estar entre 1 y 3", exception.getMessage());
        
        exception = Assertions.assertThrows(Exception.class, () -> {
            jugador_2.quitarFicha(4);
        });
        
        Assertions.assertEquals("La posicion debe estar entre 1 y 3", exception.getMessage());
    }

    @Test
    void testQuitarFichaPorPosicionCorrectamente() throws Exception {     
        jugador_2.agregarFicha(ficha_verde);
        jugador_2.agregarFicha(ficha_verde);
        jugador_2.agregarFicha(ficha_verde);

        Assertions.assertEquals(3, jugador_2.getFichas().contarElementos()); // verifica que el jugador tiene 3 fichas
        jugador_2.quitarFicha(1); // quitar ficha en posición 1
        Assertions.assertEquals(2, jugador_2.getFichas().contarElementos());
        jugador_2.quitarFicha(2);
        Assertions.assertEquals(1, jugador_2.getFichas().contarElementos());
    }

    @Test
    void testTieneTodasLasFichasEnElTablero() throws Exception {
        Assertions.assertTrue(jugador_1.tieneTodasLasFichasEnElTablero());
    }
    
    @Test
    void testAgregarCarta() throws Exception {
        jugador_1.agregarCarta(carta1);
        Assertions.assertTrue(jugador_1.getCartas().contiene(carta1));
    }
    @Test
    void testQuitarCarta() throws Exception {
        jugador_1.agregarCarta(carta2);
        jugador_1.quitarCarta(carta2);
        Assertions.assertFalse(jugador_1.getCartas().contiene(carta2));
    }

    @Test
    void testQuitarCartaNull() {
        Assertions.assertThrows(Exception.class, () -> jugador_1.quitarCarta((Carta) null));
    }

    @Test
    void testGetNombre() {
        Assertions.assertEquals("Juan", jugador_1.getNombre());
    }

    @Test
    void testGetColor() {
        Assertions.assertEquals(Color.RED, jugador_1.getColor());
    }

    @Test
    void testGetFicha() throws Exception {
        jugador_1.agregarFicha(ficha_rojo);
        Assertions.assertEquals(ficha_rojo, jugador_1.getFicha(1));
    }

    @Test
    void testGetFichaIndiceInvalido() {
        Assertions.assertThrows(Exception.class, () -> jugador_1.getFicha(10));
    }
}
