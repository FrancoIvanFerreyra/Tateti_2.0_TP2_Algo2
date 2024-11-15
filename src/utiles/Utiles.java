package utiles;

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
        }
        return resultado;
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

}
