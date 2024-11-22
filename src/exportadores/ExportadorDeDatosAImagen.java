package exportadores;

import estructuras.Lista;
import estructuras.ListaOrdenableSimplementeEnlazada;
import estructuras.ListaSimplementeEnlazada;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Tablero;
import utiles.Utiles;
import utiles.ValidacionesUtiles;


public class ExportadorDeDatosAImagen<T> {


        
    
        public static <T> void exportarTableroPorCapas(Tablero<T> tablero, String basePath) throws Exception {
            ValidacionesUtiles.validarNoNulo(tablero, "El tablero no puede ser null");
            ValidacionesUtiles.validarNoNulo(basePath, "La ruta base no puede ser null");

            // Constantes
            final int ANCHO_IMAGEN = 1024;
            final int ALTO_IMAGEN = 1024;
            final int GROSOR_LINEA = 10;
            final Color COLOR_FONDO = Color.darkGray;
            final Color COLOR_TABLERO = Color.gray;
            final Color COLOR_TEXTO = Color.white;
            final Color COLOR_CASILLERO_BLOQUEADO = Color.red;

    
            // Preparar listas para filas, columnas y capas a dibujar
            ListaOrdenableSimplementeEnlazada<Integer> filas = new ListaOrdenableSimplementeEnlazada<>();
            ListaOrdenableSimplementeEnlazada<Integer> columnas = new ListaOrdenableSimplementeEnlazada<>();
            ListaOrdenableSimplementeEnlazada<Integer> capas = new ListaOrdenableSimplementeEnlazada<>();
    
            inicializarCoordenadas(tablero, filas, columnas, capas);
    
            // Rellenar intervalos vacíos
            rellenarIntervalos(filas);
            rellenarIntervalos(columnas);
            rellenarIntervalos(capas);
    
            // Dibujar imágenes por capa
            capas.iniciarCursor();
            int numeroCapa = 1;
    
            while (capas.avanzarCursor()) {
                Integer capaActual = capas.obtenerCursor();
                String rangoIntervalo = obtenerRangoIntervalo(capaActual, capas, numeroCapa);
    
                BufferedImage imagen = crearImagenBase();
                Graphics2D g = imagen.createGraphics();
                dibujarTablero(g, filas, columnas, capaActual, tablero, rangoIntervalo);
    
                // Guardar la imagen
                String nombreArchivo = (rangoIntervalo != null) 
                    ? "capa_" + rangoIntervalo + ".bmp" 
                    : "capa_" + capaActual + ".bmp";
                ImageIO.write(imagen, "bmp", new File(basePath, nombreArchivo));
    
                numeroCapa++;
            }
        }
    
        private static <T> void inicializarCoordenadas(Tablero<T> tablero,
                                                       Lista<Integer> filas,
                                                       Lista<Integer> columnas,
                                                       Lista<Integer> capas) {
            // Agregar bordes iniciales
            Utiles.agregarOrdenadoSinRepetir(1, filas);
            Utiles.agregarOrdenadoSinRepetir(tablero.getAlto(), filas);
            Utiles.agregarOrdenadoSinRepetir(1, columnas);
            Utiles.agregarOrdenadoSinRepetir(tablero.getAncho(), columnas);
            Utiles.agregarOrdenadoSinRepetir(1, capas);
            Utiles.agregarOrdenadoSinRepetir(tablero.getProfundidad(), capas);
    
            // Agregar coordenadas de datos y casilleros bloqueados
            agregarCoordenadasDesdeCasilleros(tablero.getPosicionDeLosDatos(), filas, columnas, capas);
            agregarCoordenadasDesdeCasilleros(tablero.getCasillerosBloqueados(), filas, columnas, capas);
        }
    
        private static <T> void agregarCoordenadasDesdeCasilleros(Lista<Casillero<T>> casilleros,
                                                                  Lista<Integer> filas,
                                                                  Lista<Integer> columnas,
                                                                  Lista<Integer> capas) {
            casilleros.iniciarCursor();
            while (casilleros.avanzarCursor()) {
                Casillero<T> casillero = casilleros.obtenerCursor();
                Utiles.agregarOrdenadoSinRepetir(casillero.getX(), columnas);
                Utiles.agregarOrdenadoSinRepetir(casillero.getY(), filas);
                Utiles.agregarOrdenadoSinRepetir(casillero.getZ(), capas);
            }
        }
    
        private static void rellenarIntervalos(Lista<Integer> coordenadas) {
            int i = 1;
            while (i < coordenadas.getTamanio()) {
                if (coordenadas.obtener(i + 1) - coordenadas.obtener(i) > 1) {
                    coordenadas.agregar(null, i + 1);
                    i++;
                }
                i++;
            }
        }
    
        private static String obtenerRangoIntervalo(Integer capaActual, 
                                                    Lista<Integer> capas, 
                                                    int indice) {
            if (capaActual == null && ValidacionesUtiles.estaEstrictamenteEntre(indice, 1, capas.getTamanio())) {
                return (capas.obtener(indice - 1) + 1) + "_a_" + (capas.obtener(indice + 1) - 1);
            }
            return null;
        }
    
        private static BufferedImage crearImagenBase() {
            BufferedImage imagen = new BufferedImage(ANCHO_IMAGEN, ALTO_IMAGEN, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagen.createGraphics();
            g.setColor(COLOR_FONDO);
            g.fillRect(0, 0, ANCHO_IMAGEN, ALTO_IMAGEN);
            return imagen;
        }
    
        private static <T> void dibujarTablero(Graphics2D g, 
                                               Lista<Integer> filas, 
                                               Lista<Integer> columnas, 
                                               Integer capa, 
                                               Tablero<T> tablero, 
                                               String rangoIntervalo) {
            // Aquí agregarías el resto de la lógica para dibujar el tablero y los casilleros.
            // Este método centraliza toda la lógica de dibujo.
        }
    }
    
    private static <T> void agregarIntervalosVacios(Lista<Integer> elementos) throws Exception
    {
        int i = 1;
        while(i < elementos.getTamanio())
        {
            if(elementos.obtener(i + 1) - elementos.obtener(i) > 2)
            {
                elementos.agregar(null, i + 1);
                i++;
            }
            i++;
        }
    }
    
    private static boolean estaEnIntervaloVacio(Integer indice)
    {
        return indice == null;
    }

    public static int obtenerCoordenadaIsometricaX(int coordenadaX, int coordenadaY,int xOffset,
                                                    int casilleroSize)
    {
        return xOffset + (coordenadaX + coordenadaY) * casilleroSize / 2;
    }

    public static int obtenerCoordenadaIsometricaY(int coordenadaX, int coordenadaY,int yOffset,
                                                    int casilleroSize)
    {
        return yOffset + (coordenadaX - coordenadaY) * casilleroSize / 4;
    }
                
    public static void dibujarRombo(int grosorDeLinea, Color color, Graphics2D grafico,
                                    int x, int y,
                                    int casilleroSizeX, int casilleroSizeY)
    {
                                            
        // Crear las coordenadas del rombo (casillero en forma de rombo)
        int[] xPoints = {
            x, 
            x + casilleroSizeX / 2, 
            x, 
            x - casilleroSizeX / 2
        };
        int[] yPoints = {
            y - casilleroSizeY / 4, 
            y, 
            y + casilleroSizeY / 4, 
            y
        };
                                            
        // Dibujar el borde del rombo
        grafico.setColor(color);
        grafico.setStroke(new BasicStroke(grosorDeLinea));
        grafico.drawPolygon(xPoints, yPoints, 4);
    }

    public static void dibujarRomboConRelleno(int grosorDeLinea, Color colorRombo, Color colorRelleno,
                                              Graphics2D grafico,
                                              int x, int y,
                                              int casilleroSizeX, int casilleroSizeY)
    {                                            
        // Crear las coordenadas del rombo (casillero en forma de rombo)
        int[] xPoints = {
            x, 
            x + casilleroSizeX / 2, 
            x, 
            x - casilleroSizeX / 2
        };
        int[] yPoints = {
            y - casilleroSizeY / 4, 
            y, 
            y + casilleroSizeY / 4, 
            y
        };

        grafico.setColor(colorRelleno);
        grafico.fillPolygon(xPoints, yPoints, 4);
                                            
        // Dibujar el borde del rombo
        grafico.setColor(colorRombo);
        grafico.setStroke(new BasicStroke(grosorDeLinea));
        grafico.drawPolygon(xPoints, yPoints, 4);
    }

    public static void dibujarCirculo(int grosorDeLinea, Graphics2D grafico,
                                        int x, int y,
                                        int casilleroSizeX, int casilleroSizeY,
                                        Color color)
    {
        grafico.setStroke(new BasicStroke(1)); // Restablecer el graficorosor de línea a 1
        int circleDiameterX = casilleroSizeX / 4;
        int circleDiameterY = casilleroSizeY / 4;
        int circleX = x - circleDiameterX / 2;
        int circleY = y - circleDiameterY / 2;
        grafico.setColor(color);
        grafico.fillOval(circleX, circleY, circleDiameterX, circleDiameterY);
    }

    public static void dibujarCirculoConString(Color colorCirculo, String string, Color colorString,
                                                int tamanioFuente, int grosorDeLinea,
                                                Graphics2D grafico,
                                                int x, int y,
                                                int casilleroSizeX, int casilleroSizeY)
    {
        grafico.setStroke(new BasicStroke(1)); // Restablecer el graficorosor de línea a 1
        int circleDiameterX = casilleroSizeX / 4;
        int circleDiameterY = casilleroSizeY / 4;
        int circleX = x - circleDiameterX / 2;
        int circleY = y - circleDiameterY / 2;
        grafico.setColor(colorCirculo);
        grafico.fillOval(circleX, circleY, circleDiameterX, circleDiameterY);

        grafico.setColor(colorString);
        grafico.setFont(new Font("Calibri", Font.PLAIN, tamanioFuente));
        grafico.setStroke(new BasicStroke(grosorDeLinea));

        // Calcular el tamaño del texto
        FontMetrics metrics = grafico.getFontMetrics();
        int anchoTexto = metrics.stringWidth(string); // Ancho del texto
        int altoTexto = metrics.getAscent();      // Altura del texto (desde la línea base hacia arriba)

        // Ajustar la posición para centrar el texto
        int xCentrado = x - anchoTexto / 2;  // Centrar horizontalmente
        int yCentrado = y + altoTexto / 4; // Centrar verticalmente (compensa la línea base)

        // Dibujar el texto ajustado
        grafico.drawString(string, xCentrado, yCentrado);
    }

    public static void dibujarString(String string, Color color, int tamanioFuente, int grosorDeLinea,
                                     Graphics2D grafico,
                                     int x, int y)
    {
        grafico.setColor(color);
        grafico.setFont(new Font("Calibri", Font.PLAIN, tamanioFuente));
        grafico.setStroke(new BasicStroke(grosorDeLinea));
    
        grafico.drawString(string, x, y);
    }
}
    
    
    
    


