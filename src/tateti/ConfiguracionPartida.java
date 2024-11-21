package tateti;

import utiles.ValidacionesUtiles;

public class ConfiguracionPartida {

    private String titulo;
    private String descripcion;
    private int anchoTablero, altoTablero, profundidadTablero;
    private int cantidadDeJugadores, cantidadDeCartasPorJugador, cantidadDeFichasPorJugador;
    private int cantidadFichasSeguidasParaGanar;

    public ConfiguracionPartida(String titulo, int anchoTablero, int altoTablero,
                                 int profundidadTablero,
                                int cantidadDeJugadores, int cantidadDeCartasPorJugador,
                                int cantidadDeFichasPorJugador,
                                int cantidadFichasSeguidasParaGanar) throws Exception
    {
        ValidacionesUtiles.validarNoNull(titulo, "titulo");
        ValidacionesUtiles.validarEnteroMinimo(anchoTablero, 3, "anchoTablero");
        ValidacionesUtiles.validarEnteroMinimo(altoTablero, 3, "altoTablero");
        ValidacionesUtiles.validarEnteroMinimo(profundidadTablero, 1, "profundidadTablero");

        int cantidadDeCasilleros = anchoTablero * altoTablero  * profundidadTablero;
        ValidacionesUtiles.validarEnteroMinimo(cantidadDeJugadores, 2, "cantidadJugadores");
        
        int cantidadMaximaDeFichasEnElTablero =  (2 * cantidadDeCasilleros / 3) / cantidadDeJugadores;
        ValidacionesUtiles.validarEnteroEnRango(cantidadDeFichasPorJugador, 3,
                                                 cantidadMaximaDeFichasEnElTablero,
                                                  "cantidadDeFichasPorJugador");
        ValidacionesUtiles.validarEnteroMinimo(cantidadDeCartasPorJugador, 1,
                                                 "cantidadCartasPorJugador");

        int longitudMaximaDelTablero = Math.max(Math.max(anchoTablero, altoTablero), profundidadTablero);
        int cantidadMaximaDeFichasSeguidasPosibles = Math.min(cantidadDeFichasPorJugador, longitudMaximaDelTablero);
        
        ValidacionesUtiles.validarEnteroEnRango(cantidadFichasSeguidasParaGanar, 3,
                                                 cantidadMaximaDeFichasSeguidasPosibles,
                                                  "cantidadFichasSeguidasParaGanar");
        this.titulo = titulo;
        this.anchoTablero = anchoTablero;
        this.altoTablero = altoTablero;
        this.profundidadTablero = profundidadTablero;
        this.cantidadDeJugadores = cantidadDeJugadores;
        this.cantidadDeFichasPorJugador = cantidadDeFichasPorJugador;
        this.cantidadDeCartasPorJugador = cantidadDeCartasPorJugador;
        this.cantidadFichasSeguidasParaGanar = cantidadFichasSeguidasParaGanar;
        this.descripcion = this.titulo + "\n" + 
        "Dimensiones del tablero: ()" + this.anchoTablero + ", " +
                                        this.altoTablero + ", " + 
                                        this.profundidadTablero + ")\n" +
         "Cantidad de jugadores: " + this.cantidadDeJugadores + "\n" + 
         "Cantidad de fichas por jugador: " + this.cantidadDeFichasPorJugador + "\n" + 
         "Cantidad de cartas por jugador: " + this.cantidadDeCartasPorJugador + "\n" + 
         "Longitud del tateti: " + this.cantidadFichasSeguidasParaGanar + "\n";
    }

    @Override
    public String toString() {
        return getTitulo();
    }

    

    public String getTitulo()
    {
        return this.titulo;
    }

    public String getDescripcion()
    {
        return this.descripcion;
    }
    public int getAnchoTablero()
    {
        return this.anchoTablero;
    }
    public int getAltoTablero()
    {
        return this.altoTablero;
    }
    public int getProfundidadTablero()
    {
        return this.profundidadTablero;
    }
    public int getCantidadDeFichasPorJugador()
    {
        return this.cantidadDeFichasPorJugador;
    }
    public int getCantidadDeCartasPorJugador()
    {
        return this.cantidadDeCartasPorJugador;
    }
    public int getCantidadDeFichasSeguidasParaGanar()
    {
        return this.cantidadFichasSeguidasParaGanar;
    }
    public int getCantidadDeJugadores()
    {
        return this.cantidadDeJugadores;
    }

}
