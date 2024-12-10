package exportadores;

import estructuras.Lista;
import estructuras.ListaOrdenableSimplementeEnlazada;
import estructuras.ListaSimplementeEnlazada;
import interfaz.Consola;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Tablero;
import utiles.Utiles;
import utiles.ValidacionesUtiles;


public class ExportadorDeDatosAImagen<T> {

    /**
     * Dibuja una version simplificada del tablero, donde solo se muestran las dimensiones donde hay
     * datos relevantes. Crea una imagen BMP por cada capa de profundidad relevante. Las dimensiones
     * donde ninguno de sus casilleros tiene informacion se acumulan en intervalos vacios que son
     * representados por una dimension vacia (...)
     * @param <T>
     * @param tablero no puede ser null
     * @param rutaImagen no puede ser null
     * @throws NullPointerException si tablero o rutaImagen son null
     */
    public static <T> void exportarTableroPorCapas(Tablero<T> tablero, String rutaImagen) throws NullPointerException {
        
        ValidacionesUtiles.validarNoNull(tablero, "tablero");
        ValidacionesUtiles.validarNoNull(rutaImagen, "rutaImagen");

        //Definir dimensiones de imagen y grosor de linea
        int grosorDeLinea = 10;
        int ancho = 1024;
        int alto = 1024;
        
        // Calcular el desplazamiento necesario para centrar el tablero
        int xOffset = 0;
        int yOffset = alto / 2;


        //Se crean las listas que van a contener los indices del tablero simplificado
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeFilasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeColumnasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();
        ListaOrdenableSimplementeEnlazada<Integer> indicesDeCapasADibujar = new ListaOrdenableSimplementeEnlazada<Integer>();

        //Se guardan en una lista de listas
        Lista<Lista<Integer>> indicesTablero = new ListaSimplementeEnlazada<>();
        indicesTablero.agregar(indicesDeFilasADibujar);
        indicesTablero.agregar(indicesDeColumnasADibujar);
        indicesTablero.agregar(indicesDeCapasADibujar);

        //Se agregan a las listas el primer y ultimo indice de la dimension del tablero
        Utiles.agregarOrdenadoSinRepetir(1, indicesDeFilasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getAlto(), indicesDeFilasADibujar);
        
        Utiles.agregarOrdenadoSinRepetir(1, indicesDeColumnasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getAncho(), indicesDeColumnasADibujar);
        
        Utiles.agregarOrdenadoSinRepetir(1, indicesDeCapasADibujar);
        Utiles.agregarOrdenadoSinRepetir(tablero.getProfundidad(), indicesDeCapasADibujar);

        
        //Recorre todos los casilleros con fichas y se agregan los indices de sus coordenadas
        //a la lista correspondiente
        tablero.getPosicionDeLosDatos().iniciarCursor();
        while(tablero.getPosicionDeLosDatos().avanzarCursor())
        {
            Casillero<T> casillero = tablero.getPosicionDeLosDatos().obtenerCursor().getCasillero();            
            Utiles.agregarOrdenadoSinRepetir(casillero.getX(), indicesDeColumnasADibujar);           
            Utiles.agregarOrdenadoSinRepetir(casillero.getY(), indicesDeFilasADibujar);
            Utiles.agregarOrdenadoSinRepetir(casillero.getZ(), indicesDeCapasADibujar);
        }

        //Recorre todos los casilleros bloqueados y se agregan los indices de sus coordenadas
        //a la lista correspondiente

        tablero.getCasillerosBloqueados().iniciarCursor();
        while(tablero.getCasillerosBloqueados().avanzarCursor())
        {
            Casillero<T> casillero = tablero.getCasillerosBloqueados().obtenerCursor();            
            Utiles.agregarOrdenadoSinRepetir(casillero.getX(), indicesDeColumnasADibujar);           
            Utiles.agregarOrdenadoSinRepetir(casillero.getY(), indicesDeFilasADibujar);
            Utiles.agregarOrdenadoSinRepetir(casillero.getZ(), indicesDeCapasADibujar);
        }

        //Se rellenan las listas de indices a dibujar con sus indices consecutivos
        //o intervalos vacios segun corresponda
        indicesTablero.iniciarCursor();
        while(indicesTablero.avanzarCursor())
        {
            Lista<Integer> lista = indicesTablero.obtenerCursor();
            if(lista.getTamanio() >= 2)
            {
                Utiles.rellenarExacto(lista, 1);
                agregarIntervalosVacios(lista);
            }
        }

        //Se establecen las dimensiones del casillero a partir de la cantidad de filas
        // y columnas a dibujar
        int casilleroSizeX = 3 * ancho / (indicesDeFilasADibujar.getTamanio() + indicesDeColumnasADibujar.getTamanio() + 1) / 2;
        int casilleroSizeY = 3 * alto / (indicesDeColumnasADibujar.getTamanio() + indicesDeFilasADibujar.getTamanio() +  1) / 2;

        //Constantes de tamanio de dibujo
        int TAMANIO_TEXTO_EJES = casilleroSizeX / 3,
            TAMANIO_CRUZ_FICHAS = casilleroSizeX / 5,
            TAMANIO_FIJO_ICONOS_X = 3 * ancho / 14,
            TAMANIO_FIJO_ICONOS_Y = 3 * alto / 14,
            TAMANIO_FIJO_DESCRIPCION = ancho / 28;


        //Constantes de color de dibujo
        Color COLOR_DE_FONDO = Color.darkGray,
                COLOR_DEL_TABLERO = Color.gray,
                COLOR_TEXTO_EJES = Color.white,
                COLOR_TEXTO_FILAS = Color.white,
                COLOR_TEXTO_COLUMNAS = Color.white;

        

        //Se recorren los indices a dibujar de las coordenadas x, y, z en ese orden
        indicesDeCapasADibujar.iniciarCursor();
        
        int numeroCapa = 1;
        while(indicesDeCapasADibujar.avanzarCursor())
        {
            //Crea la imagen
            BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagen.createGraphics();
        
            // Rellena el fondo de la imagen
            g.setColor(COLOR_DE_FONDO);
            g.fillRect(0, 0, ancho, alto);

            indicesDeFilasADibujar.iniciarCursor();
            int y = 1;
            Integer indiceCapa = indicesDeCapasADibujar.obtenerCursor();

            String rangoCapasIntervaloVacio = "";
            //Se obtiene el rango vacio de capa Z para representarlo en la imagen
            if(estaEnIntervaloVacio(indiceCapa) && ValidacionesUtiles.estaEstrictamenteEntre(numeroCapa, 1, indicesDeCapasADibujar.getTamanio()))
            {
                rangoCapasIntervaloVacio = (indicesDeCapasADibujar.obtener(numeroCapa - 1) + 1) + "_a_" + (indicesDeCapasADibujar.obtener(numeroCapa + 1) - 1);
            }

            //Se dibujan los nombres de las coordenadas
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
                
                //Se dibuja el indice de la fila Y, (...) si es intervalo vacio
                dibujarString((estaEnIntervaloVacio(indiceFila)) ? "..." : Integer.toString(indiceFila),
                                COLOR_TEXTO_FILAS, TAMANIO_TEXTO_EJES,
                                grosorDeLinea, g,
                                obtenerCoordenadaIsometricaX(x - 1, y, xOffset, casilleroSizeX),
                                obtenerCoordenadaIsometricaY(x - 1, y, yOffset, casilleroSizeY));

                while(indicesDeColumnasADibujar.avanzarCursor())
                {
                    int posicionIsometricaX = obtenerCoordenadaIsometricaX(x, y, xOffset, casilleroSizeX);
                    int posicionIsometricaY = obtenerCoordenadaIsometricaY(x, y, yOffset, casilleroSizeY);

                    //Se dibuja el casillero
                    dibujarRombo(grosorDeLinea, COLOR_DEL_TABLERO, g,
                                 posicionIsometricaX, posicionIsometricaY,
                                 casilleroSizeX, casilleroSizeY);
                    
                    Integer indiceColumna = indicesDeColumnasADibujar.obtenerCursor();
                    
                    //Se dibuja el indice de la columna X, (...) si es intervalo vacio
                    dibujarString((estaEnIntervaloVacio(indiceColumna)) ? "..." : Integer.toString(indiceColumna),
                                    COLOR_TEXTO_COLUMNAS, TAMANIO_TEXTO_EJES,
                                    grosorDeLinea, g,
                                    obtenerCoordenadaIsometricaX(x, 0, xOffset, casilleroSizeX),
                                    obtenerCoordenadaIsometricaY(x, 0, yOffset, casilleroSizeY));
                    
                    //Si en los indices actuales hay informacion de algun tipo se obtiene el casillero
                    if(!estaEnIntervaloVacio(indiceFila) &&
                    !estaEnIntervaloVacio(indiceColumna) &&
                    !estaEnIntervaloVacio(indiceCapa))
                    {
                        Casillero<T> casillero = tablero.getCasillero(indiceColumna,indiceFila, indiceCapa);
                        
                        //Si hay una ficha dentro del casillero, se la dibuja
                        if (casillero.estaOcupado()) {
                            Color colorCirculo = tablero.getColorDato(casillero.getDato());
                            dibujarCirculo(grosorDeLinea, g,
                                           posicionIsometricaX, posicionIsometricaY,
                                           casilleroSizeX, casilleroSizeY,
                                           colorCirculo);
                            
                            //Si la ficha esta bloqueada se le agrega un string al circulo
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
                        //Si el casillero esta bloqueado se rellena el rombo
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
            //Se dibujan los iconos y su significado para el usuario
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


            //Libera los recursos utilizados para modificar el dibujo
            g.dispose();

            //Exporta la imagen
            try {
                ImageIO.write(imagen, "bmp",
                new File(rutaImagen, "_capa_" +
               ((estaEnIntervaloVacio(indiceCapa)) ? rangoCapasIntervaloVacio : indiceCapa) +
                ".bmp"));
            } catch (IOException e) {
                Consola.imprimirMensajeConSalto("Hubo un error al intentar exportar la imagen: " + e.getMessage());
            }
            
            //Siguiente capa Z
            numeroCapa++;
        }
        
    }

    /**
     * 
     * @param <T>
     * @param lista no puede ser null, debe tener al menos dos elementos
     * @throws NullPointerException si lista es null
     * @throws IllegalArgumentException si lista tiene menos de 2 elementos
     */
    private static <T> void agregarIntervalosVacios(Lista<Integer> lista) throws NullPointerException,
                                                                        IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(lista, "lista");
        ValidacionesUtiles.validarEnteroMinimo(lista.getTamanio(), 2, "tamanioLista");
        int i = 1;
        while(i < lista.getTamanio())
        {
            if(lista.obtener(i + 1) - lista.obtener(i) > 2)
            {
                lista.agregar(null, i + 1);
                i++;
            }
            i++;
        }
    }
    
    /**
     * @param indice numero de fila, columna o capa a dibujar
     * @return Devuelve verdadero si el indice de la coordenada a dibujar es vacio

     */
    private static boolean estaEnIntervaloVacio(Integer indice)
    {
        return indice == null;
    }

    /**
     * 
     * @param coordenadaX coordenada de columna del tablero simplificado
     * @param coordenadaY coordenada de fila del tablero simplificado
     * @param xOffset offset en x de la imagen
     * @param casilleroSize tamanio del casillero del tablero
     * @return devuelve la coordenada x isometrica del tablero
     */
    public static int obtenerCoordenadaIsometricaX(int coordenadaX, int coordenadaY,int xOffset,
                                                    int casilleroSize)
    {
        return xOffset + (coordenadaX + coordenadaY) * casilleroSize / 2;
    }

    /**
     * 
     * @param coordenadaX coordenada de columna del tablero simplificado
     * @param coordenadaY coordenada de fila del tablero simplificado
     * @param yOffset offset en y de la imagen
     * @param casilleroSize tamanio del casillero del tablero
     * @return devuelve la coordenada y isometrica del tablero
     */
    public static int obtenerCoordenadaIsometricaY(int coordenadaX, int coordenadaY,int yOffset,
                                                    int casilleroSize)
    {
        return yOffset + (coordenadaX - coordenadaY) * casilleroSize / 4;
    }
    
    /**
     * Dibuja un rombo con el color y grosor de linea recibidos, en las coordenadas x,y de la
     * imagen, escalado por casilleroSizeX y casilleroSizeY
     * @param grosorDeLinea mayor o igual a 1
     * @param color no puede ser null
     * @param grafico no puede ser null
     * @param x
     * @param y
     * @param casilleroSizeX
     * @param casilleroSizeY
     * @throws NullPointerException si color o grafico son null
     * @throws IllegalArgumentException si grosorDeLinea es menor a 1
     */
    public static void dibujarRombo(int grosorDeLinea, Color color, Graphics2D grafico,
                                    int x, int y,
                                    int casilleroSizeX, int casilleroSizeY) throws NullPointerException,
                                                                            IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(color, "color");
        ValidacionesUtiles.validarNoNull(grafico, "grafico");
        ValidacionesUtiles.validarEnteroMinimo(grosorDeLinea, 1, "grosorDeLinea");
                                            
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

        /**
     * Dibuja un rombo con coloRombo y grosor de linea recibidos, y lo rellena con colorRelleno.
     * En las coordenadas x,y de la imagen, escalado por casilleroSizeX y casilleroSizeY
     * @param grosorDeLinea mayor o igual a 1
     * @param colorRombo no puede ser null
     * @param colorRelleno no puede ser null
     * @param grafico no puede ser null
     * @param x
     * @param y
     * @param casilleroSizeX
     * @param casilleroSizeY
     * @throws NullPointerException si los colores o grafico son null
     * @throws IllegalArgumentException si grosorDeLinea es menor a 1
     */
    public static void dibujarRomboConRelleno(int grosorDeLinea, Color colorRombo, Color colorRelleno,
                                              Graphics2D grafico,
                                              int x, int y,
                                              int casilleroSizeX, int casilleroSizeY)
                                              throws NullPointerException,
                                              IllegalArgumentException
    {       
        
        ValidacionesUtiles.validarNoNull(colorRombo, "colorRombo");
        ValidacionesUtiles.validarNoNull(colorRelleno, "colorRelleno");
        ValidacionesUtiles.validarNoNull(grafico, "grafico");
        ValidacionesUtiles.validarEnteroMinimo(grosorDeLinea, 1, "grosorDeLinea");

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


        /**
     * Dibuja un circulo con el color y grosor de linea recibidos, en las coordenadas x,y de la
     * imagen, escalado por casilleroSizeX y casilleroSizeY
     * @param grosorDeLinea mayor o igual a 1
     * @param grafico no puede ser null
     * @param x
     * @param y
     * @param casilleroSizeX
     * @param casilleroSizeY
     * @param color no puede ser null
     * @throws NullPointerException si color o grafico son null
     * @throws IllegalArgumentException si grosorDeLinea es menor a 1
     */
    public static void dibujarCirculo(int grosorDeLinea, Graphics2D grafico,
                                        int x, int y,
                                        int casilleroSizeX, int casilleroSizeY,
                                        Color color)
                                        throws NullPointerException,
                                              IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(color, "color");
        ValidacionesUtiles.validarNoNull(grafico, "grafico");
        ValidacionesUtiles.validarEnteroMinimo(grosorDeLinea, 1, "grosorDeLinea");

        grafico.setStroke(new BasicStroke(1)); // Restablecer el graficorosor de línea a 1
        int circleDiameterX = casilleroSizeX / 4;
        int circleDiameterY = casilleroSizeY / 4;
        int circleX = x - circleDiameterX / 2;
        int circleY = y - circleDiameterY / 2;
        grafico.setColor(color);
        grafico.fillOval(circleX, circleY, circleDiameterX, circleDiameterY);
    }

    /**
     * Dibuja un circulo con el color y grosor de linea recibidos, en las coordenadas x,y de la imagen,
     * escalado por casilleroSizeX y casilleroSizeY y dentro de este se dibuja un string
     * @param colorCirculo no puede ser null
     * @param string no puede ser null
     * @param colorString no puede ser null
     * @param tamanioFuente mayor o igual a 1
     * @param grosorDeLinea mayor o igual a 1
     * @param grafico no puede ser null
     * @param x
     * @param y
     * @param casilleroSizeX
     * @param casilleroSizeY
     * @throws NullPointerException si los colores, string o grafico son null
     * @throws IllegalArgumentException si grosorDeLinea  o tamanioFuente son menores a 1

     */
    public static void dibujarCirculoConString(Color colorCirculo, String string, Color colorString,
                                                int tamanioFuente, int grosorDeLinea,
                                                Graphics2D grafico,
                                                int x, int y,
                                                int casilleroSizeX, int casilleroSizeY)
                                                throws NullPointerException,
                                              IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(colorCirculo, "colorCirculo");
        ValidacionesUtiles.validarNoNull(colorString, "colorString");
        ValidacionesUtiles.validarNoNull(grafico, "grafico");
        ValidacionesUtiles.validarNoNull(string, "string");

        ValidacionesUtiles.validarEnteroMinimo(grosorDeLinea, 1, "grosorDeLinea");
        ValidacionesUtiles.validarEnteroMinimo(tamanioFuente, 1, "tamanioFuente");


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

    /**
     * 
     * @param string no puede ser null
     * @param color no puede ser null
     * @param tamanioFuente mayor o igual a 1
     * @param grosorDeLinea mayor o igual a 1
     * @param grafico no puede ser null
     * @param x
     * @param y
     * @throws NullPointerException si string, color o grafico son null
     * @throws IllegalArgumentException si grosorDeLinea o tamanioFuente son menores a 1
     */
    public static void dibujarString(String string, Color color, int tamanioFuente, int grosorDeLinea,
                                     Graphics2D grafico,
                                     int x, int y)
                                     throws NullPointerException,
                                              IllegalArgumentException
    {
        ValidacionesUtiles.validarNoNull(color, "color");
        ValidacionesUtiles.validarNoNull(grafico, "grafico");
        ValidacionesUtiles.validarNoNull(string, "string");

        ValidacionesUtiles.validarEnteroMinimo(grosorDeLinea, 1, "grosorDeLinea");
        ValidacionesUtiles.validarEnteroMinimo(tamanioFuente, 1, "tamanioFuente");

        grafico.setColor(color);
        grafico.setFont(new Font("Calibri", Font.PLAIN, tamanioFuente));
        grafico.setStroke(new BasicStroke(grosorDeLinea));
    
        grafico.drawString(string, x, y);
    }
}
    
    
    
    


