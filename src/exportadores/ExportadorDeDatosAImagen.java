package exportadores;

import estructuras.Lista;
import estructuras.ListaOrdenableSimplementeEnlazada;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import tateti.Casillero;
import tateti.Tablero;
import utiles.Coordenada;


public class ExportadorDeDatosAImagen<T> {

    public static <T> void exportarTableroPorCapas(Tablero<T> tablero, String basePath) throws Exception {
        int grosorDeLinea = 10;
        int ancho = 1024;
        int alto = 1024;
        
        // Calcular el desplazamiento necesario para centrar el tablero
        int xOffset = 0;
        int yOffset = 0;
    
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();
    
        // Rellenar el fondo de la imagen
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, ancho, alto);

        Lista<Integer> indicesDeFilasADibujar = obtenerIndicesConDato(Coordenada.Y, tablero);
        Lista<Integer> indicesDeColumnasADibujar = obtenerIndicesConDato(Coordenada.X, tablero);
        agregarIntervalosVacios(indicesDeFilasADibujar);
        agregarIntervalosVacios(indicesDeColumnasADibujar);

        int casilleroSizeX = 3 * ancho / (indicesDeFilasADibujar.getTamanio() + indicesDeColumnasADibujar.getTamanio() + 1) / 2;
        int casilleroSizeY = 3 * alto / (indicesDeColumnasADibujar.getTamanio() + indicesDeFilasADibujar.getTamanio() +  1) / 2;
        yOffset = (alto / 2);


        indicesDeFilasADibujar.iniciarCursor();
        indicesDeColumnasADibujar.iniciarCursor();
        
        int y = 1;
        dibujarString("X", grosorDeLinea, g, xOffset, yOffset, 2, -1, casilleroSizeX, casilleroSizeY);
        dibujarString("Y", grosorDeLinea, g, xOffset, yOffset, -1, 2, casilleroSizeX, casilleroSizeY);

        while(indicesDeFilasADibujar.avanzarCursor())
        {
            indicesDeColumnasADibujar.iniciarCursor();
            int x = 1;
            int indiceFila = indicesDeFilasADibujar.obtenerCursor();
            dibujarString((indiceFila == 0) ? "..." : Integer.toString(indiceFila), grosorDeLinea, g, xOffset, yOffset, x - 1,y, casilleroSizeX, casilleroSizeY);
            while(indicesDeColumnasADibujar.avanzarCursor())
            {
                dibujarRombo(grosorDeLinea, g, xOffset, yOffset, x, y, casilleroSizeX, casilleroSizeY);
                
                int indiceColumna = indicesDeColumnasADibujar.obtenerCursor();
                dibujarString((indiceColumna == 0) ? "..." : Integer.toString(indiceColumna), grosorDeLinea, g, xOffset, yOffset, x, 0, casilleroSizeX, casilleroSizeY);
                if(!estaEnIntervaloVacio(indiceFila, indiceColumna))
                {
                    Casillero<T> casillero = tablero.getCasillero(indiceColumna,indiceFila);
                    if (casillero.estaOcupado()) {
                        Color colorCirculo = tablero.getColorDato(casillero.getDato());
                        dibujarCirculo(grosorDeLinea, g, xOffset, yOffset, x, y, casilleroSizeX, casilleroSizeY, colorCirculo);
                    }
                }
                x++;
            }
            y++;
        }
        g.dispose();
        ImageIO.write(imagen, "bmp", new File(basePath + "_capa_1" + ".bmp"));
    }
                                    
    private static <T> Lista<Integer> obtenerIndicesConDato(Coordenada coordenada, Tablero<T> tablero) throws Exception
    {
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeCapa = new ListaOrdenableSimplementeEnlazada<Integer>();
        int limiteSuperior = 0;
        indicesDeCapa.agregar(1);
        switch(coordenada)
        {
            case Coordenada.X:
                limiteSuperior = tablero.getAncho();
                break;
            case Coordenada.Y:
                limiteSuperior = tablero.getAlto();
            break;
            case Coordenada.Z:
                break;
            default:
                break;
        }
        if(limiteSuperior > 1)
        {
            indicesDeCapa.agregar(limiteSuperior);
            if(limiteSuperior - 1 == 2)
            {
                indicesDeCapa.agregar(2, 2);
            }
        }

        tablero.getPosicionDeLosDatos().iniciarCursor();
        while(tablero.getPosicionDeLosDatos().avanzarCursor())
        {
            Casillero<T> casillero = tablero.getPosicionDeLosDatos().obtenerCursor().getCasillero();
            int indiceDeCapa = 0;
            switch(coordenada)
            {
                case Coordenada.X -> indiceDeCapa = casillero.getX();
                case Coordenada.Y -> indiceDeCapa = casillero.getY();
                case Coordenada.Z -> {
                }
                default -> {
                }
            }
            if(!indicesDeCapa.contiene(indiceDeCapa))
            {
                if(indicesDeCapa.estaVacia())
                {
                    indicesDeCapa.agregar(indiceDeCapa);
                    continue;
                }
                int index = indicesDeCapa.insertarOrdenado(indiceDeCapa);
                if( index - 1 >= 1 && indiceDeCapa - indicesDeCapa.obtener(index - 1) == 2)
                {
                    indicesDeCapa.agregar(indiceDeCapa - 1, index);
                    index++;
                }
                
                if(index + 1 <= indicesDeCapa.getTamanio() && (indicesDeCapa.obtener(index + 1)  - indiceDeCapa == 2))
                {
                    indicesDeCapa.agregar(indiceDeCapa + 1, index + 1);
                    index++;
                }
            }
        }
        return indicesDeCapa;
    }
    
    private static <T> Lista<Integer> agregarIntervalosVacios(Lista<Integer> elementos) throws Exception
    {
        int i = 1;
        while(i < elementos.getTamanio())
        {
            if(elementos.obtener(i + 1) - elementos.obtener(i) > 2)
            {
                elementos.agregar(0, i + 1);
                i++;
            }
            i++;
        }
        return elementos;
    }
    
    private static boolean estaEnIntervaloVacio(int indiceFila, int indiceColumna)
    {
        return indiceFila == 0 || indiceColumna == 0;
    }

    public static int obtenerCoordenadaIsometricaX(int coordenadaX, int coordenadaY,int xOffset, int casilleroSize)
    {
        return xOffset + (coordenadaX + coordenadaY) * casilleroSize / 2;
    }

    public static int obtenerCoordenadaIsometricaY(int coordenadaX, int coordenadaY,int yOffset, int casilleroSize)
    {
        return yOffset + (coordenadaX - coordenadaY) * casilleroSize / 4;
    }
                
    public static void dibujarRombo(int grosorDeLinea, Graphics2D grafico, int xOffset, int yOffset, int x, int y, int casilleroSizeX, int casilleroSizeY)
    {
        // Transformación isométrica para las coordenadas X e y
        int xPos = obtenerCoordenadaIsometricaX(x, y, xOffset, casilleroSizeX);
        int yPos = obtenerCoordenadaIsometricaY(x, y, yOffset, casilleroSizeY);       
                                            
        // Crear las coordenadas del rombo (casillero en forma de rombo)
        int[] xPoints = {
            xPos, 
            xPos + casilleroSizeX / 2, 
            xPos, 
            xPos - casilleroSizeX / 2
        };
        int[] yPoints = {
            yPos - casilleroSizeY / 4, 
            yPos, 
            yPos + casilleroSizeY / 4, 
            yPos
        };
                                            
        // Dibujar el borde del rombo
        grafico.setColor(Color.gray);
        grafico.setStroke(new BasicStroke(grosorDeLinea));
        grafico.drawPolygon(xPoints, yPoints, 4);
    }

    public static void dibujarCirculo(int grosorDeLinea, Graphics2D grafico, int xOffset, int yOffset, int x, int y, int casilleroSizeX, int casilleroSizeY, Color color)
    {
        // Transformación isométrica para las coordenadas X e y
        int xPos = obtenerCoordenadaIsometricaX(x, y, xOffset, casilleroSizeX);
        int yPos = obtenerCoordenadaIsometricaY(x, y, yOffset, casilleroSizeY);       

        // Dibujar el círculo solo si el casillero está ocupado
        grafico.setStroke(new BasicStroke(1)); // Restablecer el graficorosor de línea a 1
        int circleDiameterX = casilleroSizeX / 4;
        int circleDiameterY = casilleroSizeY / 4;
        int circleX = xPos - circleDiameterX / 2;
        int circleY = yPos - circleDiameterY / 2;
        grafico.setColor(color);
        grafico.fillOval(circleX, circleY, circleDiameterX, circleDiameterY);
    }

    public static void dibujarString(String dato, int grosorDeLinea, Graphics2D grafico, int xOffset, int yOffset, int x, int y, int casilleroSizeX, int casilleroSizeY)
    {
        grafico.setColor(Color.white);
        grafico.setFont(new Font("Calibri", Font.PLAIN, casilleroSizeX / 3));
        grafico.setStroke(new BasicStroke(grosorDeLinea));
        
        // Transformación isométrica para las coordenadas X e y
        int xPos = obtenerCoordenadaIsometricaX(x, y, xOffset, casilleroSizeX);
        int yPos = obtenerCoordenadaIsometricaY(x, y, yOffset, casilleroSizeY);       
    
        grafico.drawString(dato, xPos, yPos);
    }
}
    
    
    
    


