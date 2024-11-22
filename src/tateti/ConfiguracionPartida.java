package tateti;

import utiles.ValidacionesUtiles;

public class ConfiguracionPartida {

    private final String titulo;
    private final String descripcion;
    private final int anchoTablero, altoTablero, profundidadTablero;
    private final int cantidadDeJugadores, cantidadDeCartasPorJugador, cantidadDeFichasPorJugador;
    private final int cantidadFichasSeguidasParaGanar;

    
    /**
     * Crea una instancia ConfiguracionPartida que almacena los datos de la partida deseada
     * @param titulo no puede ser null ni estar vacio
     * @param anchoTablero mayor o igual a 3
     * @param altoTablero mayor o igual a 3
     * @param profundidadTablero mayor o igual a 1
     * @param cantidadFichasSeguidasParaGanar debe estar en el rango [3, obtenerLongitudMaximaDeTateti]
     * @param cantidadDeJugadores debe estar en el rango [2, obtenerCantidadMaximaDeJugadoresParaElTablero]
     * @param cantidadDeFichasPorJugador debe estar en el rango 
     *        [cantidadFichasSeguidasParaGanar, obtenerCantidadMaximaDeFichasPorJugadorEnElTablero]
     * @param cantidadDeCartasPorJugador mayor o igual a 1
     * @throws IllegalArgumentException si algun parametro esta fuera de rango o si el titulo esta vacio
     * @throws NullPointerException si titulo es null
     */
    public ConfiguracionPartida(String titulo, int anchoTablero, int altoTablero,
                                 int profundidadTablero,
                                 int cantidadFichasSeguidasParaGanar,
                                int cantidadDeJugadores,
                                int cantidadDeFichasPorJugador, int cantidadDeCartasPorJugador
                                ) throws IllegalArgumentException, NullPointerException
    {
		ValidacionesUtiles.validarEnteroMinimo(anchoTablero, 3, "anchoTablero");
		ValidacionesUtiles.validarEnteroMinimo(altoTablero, 3, "altoTablero");
		ValidacionesUtiles.validarEnteroMinimo(profundidadTablero, 1, "profundidadTablero");
		
        int longitudMaximaTateti = obtenerLongitudMaximaDeTateti(anchoTablero, altoTablero,
                                                                         profundidadTablero);
		ValidacionesUtiles.validarEnteroEnRango(cantidadFichasSeguidasParaGanar, 3,
		                longitudMaximaTateti,   "cantidadFichasSeguidasParaGanar");

		int cantidadMaximaDeJugadoresParaElTablero = obtenerCantidadMaximaDeJugadoresParaElTablero(
                    anchoTablero, altoTablero, profundidadTablero, cantidadFichasSeguidasParaGanar);				
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeJugadores, 2,
                                                cantidadMaximaDeJugadoresParaElTablero,
												 "cantidadJugadores");
		
		int cantidadMaximaDeFichasPorJugadorEnElTablero = obtenerCantidadMaximaDeFichasPorJugadorEnElTablero(
            anchoTablero, altoTablero, profundidadTablero,
            cantidadFichasSeguidasParaGanar, cantidadDeJugadores);

		ValidacionesUtiles.validarEnteroEnRango(cantidadDeFichasPorJugador,
                                                 cantidadFichasSeguidasParaGanar,
												cantidadMaximaDeFichasPorJugadorEnElTablero,
												"cantidadDeFichasPorJugador");

		ValidacionesUtiles.validarEnteroMinimo(cantidadDeCartasPorJugador, 1,
		"cantidadCartasPorJugador");

        this.titulo = titulo;
        this.anchoTablero = anchoTablero;
        this.altoTablero = altoTablero;
        this.profundidadTablero = profundidadTablero;
        this.cantidadDeJugadores = cantidadDeJugadores;
        this.cantidadDeFichasPorJugador = cantidadDeFichasPorJugador;
        this.cantidadDeCartasPorJugador = cantidadDeCartasPorJugador;
        this.cantidadFichasSeguidasParaGanar = cantidadFichasSeguidasParaGanar;
        this.descripcion = this.titulo + "\n" + 
        "Dimensiones del tablero: (" + this.anchoTablero + ", " +
                                        this.altoTablero + ", " + 
                                        this.profundidadTablero + ")\n" +
         "Cantidad de jugadores: " + this.cantidadDeJugadores + "\n" + 
         "Cantidad de fichas por jugador: " + this.cantidadDeFichasPorJugador + "\n" + 
         "Cantidad de cartas por jugador: " + this.cantidadDeCartasPorJugador + "\n" + 
         "Longitud del tateti: " + this.cantidadFichasSeguidasParaGanar + "\n";
    }

    public static ConfiguracionPartida validarConfiguracion(ConfiguracionPartida configuracion)
                                                             throws NullPointerException
    {
        ValidacionesUtiles.validarNoNull(configuracion, "configuracion");
        return configuracion;
    }

    @Override
    public String toString() {
        return getTitulo();
    }

    /**
     * 
     * @param ancho mayor o igual a 3
     * @param alto mayor o igual a 3
     * @param profundidad mayor o igual a 1
     * @return devuelve la cantidad de fichas seguidas maxima para ganar que se puede
     * configurar en un tablero con dimensiones ancho x alto x profundidad
     * @throws IllegalArgumentException si algun parametro esta fuera de rango
     */
    public static int obtenerLongitudMaximaDeTateti(int ancho, int alto,
                                                     int profundidad) throws IllegalArgumentException
    {
        ValidacionesUtiles.validarEnteroMinimo(ancho, 3, "ancho");
		ValidacionesUtiles.validarEnteroMinimo(alto, 3, "alto");
		ValidacionesUtiles.validarEnteroMinimo(profundidad, 1, "profundidad");

        return Math.max(Math.max(ancho, alto), profundidad);
    }

    /**
     * 
     * @param ancho mayor o igual a 3
     * @param alto mayor o igual a 3
     * @param profundidad mayor o igual a 1
     * @param longitudTateti debe estar en el rango [3, obtenerLongitudMaximaDeTateti]
     * @return devuelve la cantidad maxima de jugadores que pueden jugar en un tablero
     *         de ancho x alto x profundidad, con condicion de victoria igual a longitudTateti
     * @throws IllegalArgumentException si algun parametro esta fuera de rango
     */
    public static int obtenerCantidadMaximaDeJugadoresParaElTablero(int ancho, int alto,
                                                    int profundidad,
                                                     int longitudTateti) throws IllegalArgumentException
    {
        ValidacionesUtiles.validarEnteroMinimo(ancho, 3, "ancho");
		ValidacionesUtiles.validarEnteroMinimo(alto, 3, "alto");
		ValidacionesUtiles.validarEnteroMinimo(profundidad, 1, "profundidad");
        ValidacionesUtiles.validarEnteroEnRango(longitudTateti, 3,
        obtenerLongitudMaximaDeTateti(ancho, alto, profundidad), "longitudTateti");

        
        int dimensionMenor = Math.min(Math.min(ancho, alto), (profundidad > 1) ? profundidad : 3);
        int cantidadDeCasilleros = ancho * alto  * profundidad;
        
        //Dejo una capa de casilleros libre para permitir movimientos, luego divido el resto
        //por la condicion de victoria para que todos los jugadores tengan las fichas necesarias
        //para ganar
        return ((cantidadDeCasilleros / dimensionMenor) * (dimensionMenor - 1)) / longitudTateti;

    }


    /**
     * 
     * @param ancho mayor o igual a 3
     * @param alto mayor o igual a 3
     * @param profundidad mayor o igual a 1
     * @param longitudTateti debe estar en el rango [3, obtenerLongitudMaximaDeTateti]
     * @param cantidadJugadores debe estar en el rango [2, obtenerCantidadMaximaDeJugadoresParaElTablero]
     * @return devuelve la cantidad maxima de fichas que cada jugador (de 1 a cantidadJugadores) 
     *         puede tener al mismo tiempo en un tablero de ancho x alto x profundidad,
     *         con condicion de victoria longitudTateti
     * @throws IllegalArgumentException si algun parametro esta fuera de rango
     */
    public static int obtenerCantidadMaximaDeFichasPorJugadorEnElTablero(int ancho, int alto,
                                    int profundidad, int longitudTateti,
                                    int cantidadJugadores) throws IllegalArgumentException
    {
        ValidacionesUtiles.validarEnteroMinimo(ancho, 3, "ancho");
		ValidacionesUtiles.validarEnteroMinimo(alto, 3, "alto");
		ValidacionesUtiles.validarEnteroMinimo(profundidad, 1, "profundidad");

        ValidacionesUtiles.validarEnteroEnRango(longitudTateti, 3,
        obtenerLongitudMaximaDeTateti(ancho, alto, profundidad), "longitudTateti");
        
        ValidacionesUtiles.validarEnteroEnRango(cantidadJugadores, 2,
                                                obtenerCantidadMaximaDeJugadoresParaElTablero(
                                                    ancho, alto, profundidad, longitudTateti),
                                        "longitudTateti");
        
                                        int dimensionMenor = Math.min(Math.min(ancho, alto), (profundidad > 1) ? profundidad : 3);
        int cantidadDeCasilleros = ancho * alto  * profundidad;
        
        //Dejo una capa de casilleros libre para permitir movimientos, luego divido el resto
        //por la cantidad de jugadores para obtener el maximo de fichas por jugador
        return ((cantidadDeCasilleros / dimensionMenor) * (dimensionMenor - 1)) / cantidadJugadores;
    }

    

    /**
     * 
     * @return devuelve el titulo de la configuracion
     */
    public String getTitulo()
    {
        return this.titulo;
    }

    /**
     * 
     * @return devuelve la descripcion de todas las opciones de la configuracion
     */
    public String getDescripcion()
    {
        return this.descripcion;
    }

    /**
     * 
     * @return devuelve el ancho configurado del tablero
     */
    public int getAnchoTablero()
    {
        return this.anchoTablero;
    }

    /**
     * 
     * @return devuelve el alto configurado del tablero
     */
    public int getAltoTablero()
    {
        return this.altoTablero;
    }

    /**
     * 
     * @return devuelve la profundidad configurada del tablero
     */
    public int getProfundidadTablero()
    {
        return this.profundidadTablero;
    }

    /**
     * 
     * @return devuelve la cantidad de fichas por jugador configurada del tateti
     */
    public int getCantidadDeFichasPorJugador()
    {
        return this.cantidadDeFichasPorJugador;
    }

    /**
     * 
     * @return devuelve la cantidad de cartas por jugador configurada del tateti
     */
    public int getCantidadDeCartasPorJugador()
    {
        return this.cantidadDeCartasPorJugador;
    }

    /**
     * 
     * @return devuelve la cantidad de fichas seguidas para ganar configurada del tateti
     */
    public int getCantidadDeFichasSeguidasParaGanar()
    {
        return this.cantidadFichasSeguidasParaGanar;
    }

    /**
     * 
     * @return devuelve la cantidad de jugadores configurada del tateti
     */
    public int getCantidadDeJugadores()
    {
        return this.cantidadDeJugadores;
    }

}
