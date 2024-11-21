package pruebas;


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
    public void testConstructorFicha() {
        Assertions.assertEquals("1", ficha.getSimbolo(), "El símbolo de la ficha debe ser 1.");
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
        
        Ficha otraFicha = null;
        try {
            otraFicha = new Ficha("O");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(otraFicha != null)
        {
            Assertions.assertFalse(ficha.esElMismoSimbolo(otraFicha), "Las fichas no deberían tener el mismo símbolo.");
        }

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

        ficha.incrementarBloqueosRestantes(3);
        Assertions.assertEquals(4, ficha.getBloqueosRestantes(), "El número de bloqueos restantes debería ser 4.");

    }

    @Test
    public void testIncrementarConNegativoBloqueoFicha()
    {
        Assertions.assertThrows(IllegalArgumentException.class,

            () -> {ficha.incrementarBloqueosRestantes(-1);},
            "La ficha no lanzo excepcion al intentar agregarle bloqueos negativos"); 
    }


    @Test
    public void testToString() {
        Assertions.assertEquals("1", ficha.toString(), "El método toString debería devolver el símbolo de la ficha como cadena.");
    }


    @Test
    public void testReducirBloqueosRestantes() throws Exception {
        ficha.incrementarBloqueosRestantes(5);
        ficha.reducirBloqueosRestantes(2);
        Assertions.assertEquals(3, ficha.getBloqueosRestantes(), "Los bloqueos restantes deberían ser 3 después de reducir 2.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ficha.reducirBloqueosRestantes(10);
        }, "La ficha no lanzo excepcion al intentar reducirle mas bloqueos de los que posee");
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