package utiles;

import estructuras.Lista;
import estructuras.ListaOrdenableSimplementeEnlazada;
import estructuras.ListaSimplementeEnlazada;
import estructuras.Vector;
import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Random;
import tateti.Movimiento;

public class Utiles {

    /**
     * 
     * @param movimiento movimiento en 3D, no debe ser null
     * @return devuelve el movimiento opuesto a movimiento en el espacio 3D
     * @throws NullPointerException si movimiento es null
     * @throws NoSuchElementException si no se encontro el movimiento opuesto a movimiento
     */
    public static Movimiento movimientoOpuesto(Movimiento movimiento) throws NullPointerException,
                                                                    NoSuchElementException
    {
        ValidacionesUtiles.validarNoNull(movimiento, "movimiento");
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
            default -> throw new NoSuchElementException(
                    "No se encontro el movimiento opuesto a movimiento");
        }
        return resultado;
    }

    /**
     * 
     * @return devuelve una lista con todos los movimientos en 3D que no son opuestos entre
     *         si
     */
    public static Lista<Movimiento> obtenerMovimientosAChequear(){
        Lista<Movimiento> movimientosAChequear = new ListaSimplementeEnlazada<>();
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

    /**
     * 
     * @return devuelve un color aleatorio
     */
    public static Color generarColorAleatorio() {
        Random random = new Random();
        int rojo = random.nextInt(256);  // Valor entre 0 y 255
        int verde = random.nextInt(256); 
        int azul = random.nextInt(256); 
        
        return new Color(rojo, verde, azul);
    }

    /**
     * 
     * @param color no debe ser null
     * @return devuelve verdadero si el color es considerado "oscuro" a partir
     * de la formula de luminancia relativa
     * @throws NullPointerException si color es null
     */
    public static boolean esColorOscuro(Color color) throws NullPointerException{
        ValidacionesUtiles.validarNoNull(color, "color");
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

    /**
     * 
     * @param nuevoColor no debe ser null
     * @param coloresGenerados no debe ser null
     * @return devuelve verdadero si nuevoColor es lo suficientemente distinto a todos los
     *         colores en coloresGenerados, bajo un umbral de distincion establecido
     * @throws NullPointerException si color o coloresGenerados son null
     */
    public static boolean esColorDistinto(Color nuevoColor, Vector<Color> coloresGenerados)
                                        throws NullPointerException {
        ValidacionesUtiles.validarNoNull(nuevoColor, "nuevoColor");
        ValidacionesUtiles.validarNoNull(coloresGenerados, "coloresGenerados");

        int UMBRAL_DISTINCION = 100;
        for (int i = 1; i <= coloresGenerados.getLongitud(); i++) {
            Color colorGenerado;
            colorGenerado = coloresGenerados.obtener(i);

            if (colorGenerado != null &&
                calcularDistanciaColor(nuevoColor, colorGenerado) < UMBRAL_DISTINCION) {
                return false; // El color es demasiado similar a uno existente
            }
        }
        return true; // El color es suficientemente distinto
    }

    /**
     * 
     * @param color1 no debe ser null
     * @param color2 no debe ser null
     * @return devuelve la distancia euclidiana entre color1 y color2
     * @throws NullPointerException si color1 o color2 son null
     */
    private static double calcularDistanciaColor(Color color1, Color color2) 
                                                    throws NullPointerException{
        ValidacionesUtiles.validarNoNull(color1, "color1");
        ValidacionesUtiles.validarNoNull(color2, "color2");

        int deltaRojo = color1.getRed() - color2.getRed();
        int deltaVerde = color1.getGreen() - color2.getGreen();
        int deltaAzul = color1.getBlue() - color2.getBlue();
        return Math.sqrt(deltaRojo * deltaRojo + deltaVerde * deltaVerde + deltaAzul * deltaAzul);
    }
    

    /**
     * Inserta un numero ordenado y sin repetir en una lista ordenada de forma ascendente
     * @param numero
     * @param lista no debe ser null y debe estar ordenada de forma ascendente
     * @return devuelve el indice de insercion de numero en la lista ordenable. Si la lista ya tiene al
     *         numero, devuelve -1
     * @throws NullPointerException si lista es null
     * @throws IllegalArgumentException si la lista no esta ordenada de forma ascendente
     */
    public static int agregarOrdenadoSinRepetir(int numero,
                                             ListaOrdenableSimplementeEnlazada<Integer> lista)
                                              throws NullPointerException,
                                              IllegalArgumentException
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
        if(!lista.estaOrdenada())
        {
            throw new IllegalArgumentException("La lista no esta ordenada de forma ascendente");
        }
        return lista.insertarOrdenado(numero);
    }

    /**
     * Inserta a una lista elementos n en las posiciones a, si la diferencia entre
     * los elementos posicionados en a - 1 y a es igual a rango + 1, es decir, 
     * lista(a - 1) == n - ((rango + 1) / 2) y lista(a) == n + ((rango + 1) / 2)
     * o viceversa
     * @param lista no puede ser null y debe poseer al menos 2 elementos
     * @param rango mayor o igual a 1
     * @throws NullPointerException si lista es null
     * @throws IllegalArgumentException si lista tiene menos de dos elementos
     *                                  o rango es menor a 1
     */
    public static void rellenarExacto(Lista<Integer> lista, int rango) 
                                        throws NullPointerException,
                                        IllegalArgumentException {
        ValidacionesUtiles.validarNoNull(lista, "lista");
        ValidacionesUtiles.validarEnteroMinimo(lista.getTamanio(), 2, "tamanioLista");
        ValidacionesUtiles.validarEnteroMinimo(rango, 1, "rango");

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

    /**
     * 
     * @param minimo debe ser menor o igual al maximo
     * @param maximo debe ser mayor o igual al minimo
     * @return devuelve un entero aleatorio dentro del rango [minimo, maximo]
     * @throws IllegalArgumentException si minimo es mayor a maximo
     */
    public static int obtenerEnteroAleatorio(int minimo, int maximo) throws IllegalArgumentException
    {
        if(!ValidacionesUtiles.esMenorOIgualQue(minimo, maximo))
        {
            throw new IllegalArgumentException("Minimo es mayor que el maximo");
        }
        Random random = new Random();
        return random.nextInt(maximo - minimo) + minimo;
    }

    /**
     * Detiene la ejecucion del programa la cantidad de milisegundos recibida
     * @param milisegundos mayor a 1
     * @throws IllegalArgumentException si milisegundos es menor a 1
     */
    public static void pausarEjecucion(int milisegundos) throws IllegalArgumentException{
        ValidacionesUtiles.validarEnteroMinimo(milisegundos, 1, "milisegundos");
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción del hilo
            System.out.println("Se interrumpió la espera.");
        }
    }
}
