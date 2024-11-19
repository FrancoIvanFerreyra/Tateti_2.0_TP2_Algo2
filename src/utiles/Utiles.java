package utiles;

import estructuras.Lista;
import estructuras.ListaOrdenableSimplementeEnlazada;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;

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

    public static boolean esColorOscuro(Color color) {
        // Convertir los componentes RGB a escala [0, 1]
        double r = color.getRed() / 255.0;
        double g = color.getGreen() / 255.0;
        double b = color.getBlue() / 255.0;
    
        // Ajustar los valores RGB según la fórmula de luminancia relativa
        r = (r <= 0.03928) ? r / 12.92 : Math.pow((r + 0.055) / 1.055, 2.4);
        g = (g <= 0.03928) ? g / 12.92 : Math.pow((g + 0.055) / 1.055, 2.4);
        b = (b <= 0.03928) ? b / 12.92 : Math.pow((b + 0.055) / 1.055, 2.4);
    
        // Calcular la luminancia relativa
        double luminancia = 0.2126 * r + 0.7152 * g + 0.0722 * b;
    
        // Determinar si es oscuro
        return luminancia < 0.5; // Umbral común para considerar un color "oscuro"
    }

    public static boolean esColorDistinto(Color nuevoColor, Vector<Color> coloresGenerados) {
        int UMBRAL_DISTINCION = 500;
        for (int i = 1; i <= coloresGenerados.getLongitud(); i++) {
            Color colorGenerado;
            try {
                colorGenerado = coloresGenerados.obtener(i);
            } catch (Exception e) {
                continue;
            }
            if (calcularDistanciaColor(nuevoColor, colorGenerado) < UMBRAL_DISTINCION) {
                return false; // El color es demasiado similar a uno existente
            }
        }
        return true; // El color es suficientemente distinto
    }

    private static double calcularDistanciaColor(Color c1, Color c2) {
        int deltaRojo = c1.getRed() - c2.getRed();
        int deltaVerde = c1.getGreen() - c2.getGreen();
        int deltaAzul = c1.getBlue() - c2.getBlue();
        return Math.sqrt(deltaRojo * deltaRojo + deltaVerde * deltaVerde + deltaAzul * deltaAzul);
    }
    

    public static int agregarOrdenadoSinRepetir(int numero, ListaOrdenableSimplementeEnlazada<Integer> lista) throws Exception
    {
        if(lista.contiene(numero))
        {
            return -1;
        }
        if(lista.estaVacia())
        {
            lista.agregar(numero);
            return 1;
        }
        return lista.insertarOrdenado(numero);
    }

    public static void rellenarExacto(Lista<Integer> lista, int rango) throws Exception {
        if (lista == null || lista.getTamanio() < 2 || rango < 1) {
            throw new IllegalArgumentException("La lista debe contener al menos 2 elementos y el rango debe ser mayor a 0.");
        }
    
        int indice = 2; 
        while (indice <= lista.getTamanio()) {
            int elementoInferior = (lista.obtener(indice) - lista.obtener(indice - 1) > 0) ? lista.obtener(indice - 1) : lista.obtener(indice);
            int elementoSuperior = (lista.obtener(indice) - lista.obtener(indice - 1) > 0) ? lista.obtener(indice) : lista.obtener(indice - 1);
    
            if (elementoSuperior - elementoInferior == rango + 1) {
                lista.agregar(elementoInferior + rango, indice); 
                indice++; 
            }
            indice++;
        }
    }
    
    


}
