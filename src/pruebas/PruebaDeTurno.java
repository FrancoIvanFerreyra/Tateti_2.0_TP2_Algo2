package pruebas;

import jugadas.JugadaPierdeTurno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tateti.Turno;
import tateti.Jugador;
import tateti.Movimiento;
import tateti.Ficha;
import jugadas.Jugada;
public class PruebaDeTurno {

    @Test
    public void pruebaDeTurno() throws Exception {

        //Pruebo crear un turno con jugador nulo
        Assertions.assertThrows(Exception.class, () ->{
            Turno turno = new Turno(null);
        }, "El turno no devolvio excepcion al intentar crearlo con un jugador nulo");
        
        //Creo el turno
        Turno turno = new Turno(new Jugador("Tomas", 3));

        //Pruebo si se creo sin subturnos
        Assertions.assertTrue(!turno.haySubturnos(), "El turno se inicializo con subturnos");
        
        //Pruebo si se creo desbloqueado
        Assertions.assertTrue(!turno.estaBloqueado(), "El turno se inicializo bloqueado");
        
        //Pruebo si se creo con los datos inicializados
        Assertions.assertEquals(null, turno.getFichaUtilizada(), "El turno se inicializo con ficha distinta a null");
        Assertions.assertEquals(null, turno.getMovimientoAplicado(), "El turno se inicializo con direccion distinta a null");
        Assertions.assertEquals(null, turno.getJugadaEjecutada(), "El turno se inicializo con jugada distinta a null");
    
        //Pruebo si se bloquea al añadirle bloqueos
        turno.incrementarBloqueosRestantes(1);
        Assertions.assertTrue(turno.estaBloqueado(), "El turno no esta bloqueado como se esperaba");

        
        //Pruebo si el turno se desbloquea al jugarse
        turno.iniciarTurno();
        turno.terminarTurno();
        Assertions.assertTrue(!turno.estaBloqueado(), "El turno no libero el bloqueo como se esperaba");

        //Pruebo quitar bloqueos negativos
        Assertions.assertThrows(Exception.class, () ->{
            turno.reducirBloqueosRestantes(-2);
        }, "El turno no devolvio excepcion al intentar reducir un numero negativo de bloqueos");
        
        //Pruebo quitar mas bloqueos de los que tiene
        Assertions.assertThrows(Exception.class, () ->{
            turno.reducirBloqueosRestantes(2);
        }, "El turno no devolvio excepcion al intentar reducir mas bloqueos de los que posee");

        //Pruebo si se los datos del turno se asignan correctamente
        Ficha fichaUtilizada = new Ficha('X');
        turno.setFichaUtilizada(fichaUtilizada);
        Assertions.assertEquals(fichaUtilizada, turno.getFichaUtilizada(), "La ficha utilizada no se guardo correctamente");

        Movimiento movimientoAplicado = Movimiento.ARRIBA;
        turno.setMovimientoAplicado(movimientoAplicado);
        Assertions.assertEquals(movimientoAplicado, turno.getMovimientoAplicado(), "La direccion aplicada no se guardo correctamente");

        Jugada jugadaEjecutada = new JugadaPierdeTurno(null);
        turno.setJugadaEjecutada(jugadaEjecutada);
        Assertions.assertEquals(jugadaEjecutada, turno.getJugadaEjecutada(), "La jugada ejecutada no se guardo correctamente");

        
        //Pruebo si los datos del turno se limpian correctamente
        turno.limpiar();
        Assertions.assertEquals(null, turno.getFichaUtilizada(), "El turno no limpio la ficha");
        Assertions.assertEquals(null, turno.getMovimientoAplicado(), "El turno no limpio la direccion");
        Assertions.assertEquals(null, turno.getJugadaEjecutada(), "El turno no limpio la jugada");

    }
}