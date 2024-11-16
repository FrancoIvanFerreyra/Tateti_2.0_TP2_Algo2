package utiles;

import static org.junit.Assert.fail;


import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import java.awt.Color;
import java.util.Random;
import tateti.Movimiento;

public class Utiles {

    public static Movimiento movimientoOpuesto(Movimiento movimiento)
    {
        Movimiento resultado = null;
        switch(movimiento)
        {
            case Movimiento.ARRIBA -> resultado = Movimiento.ABAJO;
            case Movimiento.ABAJO -> resultado = Movimiento.ARRIBA;
            case Movimiento.IZQUIERDA -> resultado = Movimiento.DERECHA;
            case Movimiento.DERECHA -> resultado = Movimiento.IZQUIERDA;
            case Movimiento.IZQUIERDA_ABAJO -> resultado = Movimiento.DERECHA_ARRIBA;
            case Movimiento.IZQUIERDA_ARRIBA -> resultado = Movimiento.DERECHA_ABAJO;
            case Movimiento.DERECHA_ABAJO -> resultado = Movimiento.IZQUIERDA_ARRIBA;
            case Movimiento.DERECHA_ARRIBA -> resultado = Movimiento.IZQUIERDA_ABAJO;
            case Movimiento.ADELANTE -> resultado = Movimiento.ATRAS;
            case Movimiento.ATRAS -> resultado = Movimiento.ADELANTE;
            case Movimiento.ADELANTE_ARRIBA -> resultado = Movimiento.ATRAS_ABAJO;
            case Movimiento.ADELANTE_ABAJO -> resultado = Movimiento.ATRAS_ARRIBA;
            case Movimiento.ATRAS_ARRIBA -> resultado = Movimiento.ADELANTE_ABAJO;
            case Movimiento.ATRAS_ABAJO -> resultado = Movimiento.ADELANTE_ARRIBA;
            case Movimiento.IZQUIERDA_ADELANTE -> resultado = Movimiento.DERECHA_ATRAS;
            case Movimiento.DERECHA_ADELANTE -> resultado = Movimiento.IZQUIERDA_ATRAS;
            case Movimiento.IZQUIERDA_ATRAS -> resultado = Movimiento.DERECHA_ADELANTE;
            case Movimiento.DERECHA_ATRAS -> resultado = Movimiento.IZQUIERDA_ADELANTE;
            case Movimiento.IZQUIERDA_ADELANTE_ARRIBA -> resultado = Movimiento.DERECHA_ATRAS_ABAJO;
            case Movimiento.IZQUIERDA_ADELANTE_ABAJO -> resultado = Movimiento.DERECHA_ATRAS_ARRIBA;
            case Movimiento.DERECHA_ADELANTE_ARRIBA -> resultado = Movimiento.IZQUIERDA_ATRAS_ABAJO;
            case Movimiento.DERECHA_ADELANTE_ABAJO -> resultado = Movimiento.IZQUIERDA_ATRAS_ARRIBA;
            case Movimiento.IZQUIERDA_ATRAS_ARRIBA -> resultado = Movimiento.DERECHA_ADELANTE_ABAJO;
            case Movimiento.IZQUIERDA_ATRAS_ABAJO -> resultado = Movimiento.DERECHA_ADELANTE_ARRIBA;
            case Movimiento.DERECHA_ATRAS_ARRIBA -> resultado = Movimiento.IZQUIERDA_ADELANTE_ABAJO;
            case Movimiento.DERECHA_ATRAS_ABAJO -> resultado = Movimiento.IZQUIERDA_ADELANTE_ARRIBA;
            
        }
        return resultado;
    }

    public static Lista<Movimiento> obtenerMovimientosAChequear() throws Exception{
        Lista<Movimiento> movimientosAChequear = new ListaSimplementeEnlazada<Movimiento>();
		movimientosAChequear.agregar(Movimiento.ADELANTE);
		movimientosAChequear.agregar(Movimiento.IZQUIERDA);
		movimientosAChequear.agregar(Movimiento.IZQUIERDA_ADELANTE);
		movimientosAChequear.agregar(Movimiento.DERECHA_ADELANTE);
		movimientosAChequear.agregar(Movimiento.ARRIBA);
		movimientosAChequear.agregar(Movimiento.IZQUIERDA_ARRIBA);
		movimientosAChequear.agregar(Movimiento.DERECHA_ARRIBA);
		movimientosAChequear.agregar(Movimiento.ADELANTE_ARRIBA);
		movimientosAChequear.agregar(Movimiento.ATRAS_ARRIBA);
		movimientosAChequear.agregar(Movimiento.IZQUIERDA_ADELANTE_ARRIBA);
		movimientosAChequear.agregar(Movimiento.DERECHA_ADELANTE_ARRIBA);
		movimientosAChequear.agregar(Movimiento.IZQUIERDA_ATRAS_ARRIBA);
		movimientosAChequear.agregar(Movimiento.DERECHA_ATRAS_ARRIBA);

        return movimientosAChequear;

    }

    public static String generarStringAleatorio(int longitud) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(longitud);
        
        for (int i = 0; i < longitud; i++) {
            char letraAleatoria = (char) ('a' + random.nextInt(26)); // 'a' es el primer carácter en el rango
            sb.append(letraAleatoria);
        }
        
        return sb.toString();
    }

    public static Color generarColorAleatorio() {
        Random random = new Random();
        int rojo = random.nextInt(256);  // Valor entre 0 y 255
        int verde = random.nextInt(256); 
        int azul = random.nextInt(256); 
        
        return new Color(rojo, verde, azul);
    }

}
