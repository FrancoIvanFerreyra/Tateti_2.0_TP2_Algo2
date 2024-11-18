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
    
        int grosorDeLinea = 10;
        int ancho = 1024;
        int alto = 1024;
        
        // Calcular el desplazamiento necesario para centrar el tablero
        int xOffset = 0;
        int yOffset = 0;


        ListaOrdenableSimplementeEnlazada<Integer> indicesDeFilasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeColumnasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeCapasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();

        Lista<Lista<Integer>> indicesTablero = new ListaSimplementeEnlazada<>();
        indicesTablero.agregar(indicesDeFilasADibujar);
        indicesTablero.agregar(indicesDeColumnasADibujar);
        indicesTablero.agregar(indicesDeCapasADibujar);


        Utiles.agregarOrdenadoSinRepetir(1, indicesDeFilasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getAlto(), indicesDeFilasADibujar);
        
        Utiles.agregarOrdenadoSinRepetir(1, indicesDeColumnasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getAncho(), indicesDeColumnasADibujar);
        
        Utiles.agregarOrdenadoSinRepetir(1, indicesDeCapasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getProfundidad(), indicesDeCapasADibujar);

        

        tablero.getPosicionDeLosDatos().iniciarCursor();
        while(tablero.getPosicionDeLosDatos().avanzarCursor())
        {
            Casillero<T> casillero = tablero.getPosicionDeLosDatos().obtenerCursor().getCasillero();            
            Utiles.agregarOrdenadoSinRepetir(casillero.getX(), indicesDeColumnasADibujar);           
            Utiles.agregarOrdenadoSinRepetir(casillero.getY(), indicesDeFilasADibujar);
            Utiles.agregarOrdenadoSinRepetir(casillero.getZ(), indicesDeCapasADibujar);
        }

        tablero.getCasillerosBloqueados().iniciarCursor();
        while(tablero.getCasillerosBloqueados().avanzarCursor())
        {
            Casillero<T> casillero = tablero.getCasillerosBloqueados().obtenerCursor();            
            Utiles.agregarOrdenadoSinRepetir(casillero.getX(), indicesDeColumnasADibujar);           
            Utiles.agregarOrdenadoSinRepetir(casillero.getY(), indicesDeFilasADibujar);
            Utiles.agregarOrdenadoSinRepetir(casillero.getZ(), indicesDeCapasADibujar);
        }


        indicesTablero.iniciarCursor();
        while(indicesTablero.avanzarCursor())
        {
            Lista<Integer> lista = indicesTablero.obtenerCursor();
            Utiles.rellenarExacto(lista, 1);
            agregarIntervalosVacios(lista);
        }

        int casilleroSizeX = 3 * ancho / (indicesDeFilasADibujar.getTamanio() + indicesDeColumnasADibujar.getTamanio() + 1) / 2;
        int casilleroSizeY = 3 * alto / (indicesDeColumnasADibujar.getTamanio() + indicesDeFilasADibujar.getTamanio() +  1) / 2;
        yOffset = (alto / 2);


        int TAMANIO_TEXTO_EJES = casilleroSizeX / 3;
        int TAMANIO_CRUZ_FICHAS = casilleroSizeX / 5;
        int TAMANIO_FIJO_ICONOS_X = 3 * ancho / 14;
        int TAMANIO_FIJO_ICONOS_Y = 3 * alto / 14;
        int TAMANIO_FIJO_DESCRIPCION = ancho / 28;



        Color COLOR_DE_FONDO = Color.darkGray,
                COLOR_DEL_TABLERO = Color.gray,
                COLOR_TEXTO_EJES = Color.white,
                COLOR_TEXTO_FILAS = Color.white,
                COLOR_TEXTO_COLUMNAS = Color.white;

        indicesDeCapasADibujar.iniciarCursor();
        
        int numeroCapa = 1;
        while(indicesDeCapasADibujar.avanzarCursor())
        {
            BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagen.createGraphics();
        
            // Rellenar el fondo de la imagen
            g.setColor(COLOR_DE_FONDO);
            g.fillRect(0, 0, ancho, alto);

            indicesDeFilasADibujar.iniciarCursor();
            int y = 1;
            Integer indiceCapa = indicesDeCapasADibujar.obtenerCursor();

            String rangoCapasIntervaloVacio = "";
            if(estaEnIntervaloVacio(indiceCapa) && ValidacionesUtiles.estaEstrictamenteEntre(numeroCapa, 1, indicesDeCapasADibujar.getTamanio()))
            {
                rangoCapasIntervaloVacio = (indicesDeCapasADibujar.obtener(numeroCapa - 1) + 1) + "_a_" + (indicesDeCapasADibujar.obtener(numeroCapa + 1) - 1);
            }

            dibujarString("X", COLOR_TEXTO_EJES, TAMANIO_TEXTO_EJES, grosorDeLinea, g,
                         obtenerCoordenadaIsometricaX(2, -1, xOffset, casilleroSizeX),
                         obtenerCoordenadaIsometricaY(2, -1, yOffset, casilleroSizeY));
            
            dibujarString("Y", COLOR_TEXTO_EJES, TAMANIO_TEXTO_EJES, grosorDeLinea, g,
                         obtenerCoordenadaIsometricaX(-1, 2, xOffset, casilleroSizeX),
                         obtenerCoordenadaIsometricaY(-1, 2, yOffset, casilleroSizeY));   
            
            dibujarString("Z = " + 
                    ((estaEnIntervaloVacio(indiceCapa)) ? rangoCapasIntervaloVacio : indiceCapa), 
                    COLOR_TEXTO_EJES, TAMANIO_TEXTO_EJES,
                    grosorDeLinea, g,
                    obtenerCoordenadaIsometricaX(indicesDeColumnasADibujar.getTamanio() - 1,
                                                 indicesDeFilasADibujar.getTamanio() + 1,
                                                 xOffset, casilleroSizeX),
                    obtenerCoordenadaIsometricaY(indicesDeColumnasADibujar.getTamanio() - 1,
                                                 indicesDeFilasADibujar.getTamanio() + 1,
                                                 yOffset, casilleroSizeY));    

            while(indicesDeFilasADibujar.avanzarCursor())
            {
                indicesDeColumnasADibujar.iniciarCursor();
                int x = 1;
                Integer indiceFila = indicesDeFilasADibujar.obtenerCursor();
                dibujarString((estaEnIntervaloVacio(indiceFila)) ? "..." : Integer.toString(indiceFila),
                                COLOR_TEXTO_FILAS, TAMANIO_TEXTO_EJES,
                                grosorDeLinea, g,
                                obtenerCoordenadaIsometricaX(x - 1, y, xOffset, casilleroSizeX),
                                obtenerCoordenadaIsometricaY(x - 1, y, yOffset, casilleroSizeY));

                while(indicesDeColumnasADibujar.avanzarCursor())
                {
                    int posicionIsometricaX = obtenerCoordenadaIsometricaX(x, y, xOffset, casilleroSizeX);
                    int posicionIsometricaY = obtenerCoordenadaIsometricaY(x, y, yOffset, casilleroSizeY);

                    dibujarRombo(grosorDeLinea, COLOR_DEL_TABLERO, g,
                                 posicionIsometricaX, posicionIsometricaY,
                                 casilleroSizeX, casilleroSizeY);
                    
                    Integer indiceColumna = indicesDeColumnasADibujar.obtenerCursor();
                    dibujarString((estaEnIntervaloVacio(indiceColumna)) ? "..." : Integer.toString(indiceColumna),
                                    COLOR_TEXTO_COLUMNAS, TAMANIO_TEXTO_EJES,
                                    grosorDeLinea, g,
                                    obtenerCoordenadaIsometricaX(x, 0, xOffset, casilleroSizeX),
                                    obtenerCoordenadaIsometricaY(x, 0, yOffset, casilleroSizeY));
                    
                    
                    if(!estaEnIntervaloVacio(indiceFila) &&
                    !estaEnIntervaloVacio(indiceColumna) &&
                    !estaEnIntervaloVacio(indiceCapa))
                    {
                        Casillero<T> casillero = tablero.getCasillero(indiceColumna,indiceFila, indiceCapa);
                        if (casillero.estaOcupado()) {
                            Color colorCirculo = tablero.getColorDato(casillero.getDato());
                            dibujarCirculo(grosorDeLinea, g,
                                           posicionIsometricaX, posicionIsometricaY,
                                           casilleroSizeX, casilleroSizeY,
                                           colorCirculo);
     
                            if(((Ficha)casillero.getDato()).estaBloqueado())
                            {
                                Color colorCruz = (Utiles.esColorOscuro(
                                                    tablero.getColorDato(casillero.getDato())))
                                                    ? Color.white : Color.black;
                                dibujarCirculoConString(colorCirculo, "B", colorCruz,
                                                        TAMANIO_CRUZ_FICHAS, grosorDeLinea, g,
                                                        posicionIsometricaX, posicionIsometricaY,
                                                        casilleroSizeX, casilleroSizeY);
                            }
                        }
                        if(casillero.estaBloqueado())
                        {
                            dibujarRomboConRelleno(grosorDeLinea, Color.gray, Color.red , g,
                                                   posicionIsometricaX, posicionIsometricaY,
                                                   casilleroSizeX, casilleroSizeY);
                        }
                    }
                    
                    x++;
                }
                
                y++;
            }
            dibujarRomboConRelleno(grosorDeLinea, COLOR_DEL_TABLERO,
                                   Color.red , g,
                                   (1 * ancho / 10), 8 * alto / 10,
                                   TAMANIO_FIJO_ICONOS_X / 2, TAMANIO_FIJO_ICONOS_Y / 2);
            dibujarString("Casillero bloqueado", COLOR_TEXTO_EJES,
                                 TAMANIO_FIJO_DESCRIPCION, grosorDeLinea, g,
                                (2 * ancho / 10), 8 * alto / 10);
            dibujarCirculoConString(Color.black, "B", Color.white,
                                TAMANIO_FIJO_DESCRIPCION, grosorDeLinea, g,
                                (1 * ancho / 10), 9 * alto / 10,
                                TAMANIO_FIJO_ICONOS_X, TAMANIO_FIJO_ICONOS_Y);
            dibujarString("Ficha bloqueada", COLOR_TEXTO_EJES,
                                TAMANIO_FIJO_DESCRIPCION, grosorDeLinea, g,
                               (2 * ancho / 10), 9 * alto / 10);



            g.dispose();
            ImageIO.write(imagen, "bmp",
                         new File(basePath, "_capa_" +
                        ((estaEnIntervaloVacio(indiceCapa)) ? rangoCapasIntervaloVacio : indiceCapa) +
                         ".bmp"));
            numeroCapa++;
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
    
    
    
    


