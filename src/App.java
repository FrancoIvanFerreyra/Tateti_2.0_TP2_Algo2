import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import interfaz.Consola;
import tateti.ConfiguracionPartida;
import tateti.Tateti;

public class App {
    public static void main(String[] args) throws Exception {
        int anchoTablero, altoTablero, profundidadTablero;
        int cantidadJugadores, cantidadCartasPorJugador, cantidadDeFichasPorJugador;
        int cantidadFichasSeguidasParaGanar;

        Lista<ConfiguracionPartida> opcionesTateti = new ListaSimplementeEnlazada<>();
        opcionesTateti.agregar(new ConfiguracionPartida(
            "Clasico",
             3, 3, 1,
             3, 
             2,
              3,
               3));

        opcionesTateti.agregar(new ConfiguracionPartida(
                "Cubo 3x3x3",
                 3, 3, 3,
                 3, 
                 2,
                  3,
                   3));
        opcionesTateti.agregar(new ConfiguracionPartida(
                    "Cubo 5x5x5",
                     5, 5, 5,
                     4, 
                     5,
                      4,
                       4));
        opcionesTateti.agregar(new ConfiguracionPartida(
                        "Cubo 10x10x10",
                         10, 10, 10,
                         5, 
                         6,
                          6,
                           6));
        opcionesTateti.agregar(new ConfiguracionPartida(
                            "Crear nueva configuracion",
                             3, 3, 3,
                             3, 
                             3,
                              3,
                               3));
        boolean usuarioEligioConfiguracion = false;
        Tateti tateti = null;
        while(!usuarioEligioConfiguracion)
        {
            Consola.imprimirMensaje("Tateti 2.0 - Hecho por Todos Desarrollamos Algo");
            ConfiguracionPartida opcion = Consola.consultarOpcionAlUsuario(opcionesTateti,
             "Selecciona una configuracion de partida:", false);
    
            if(opcion.getTitulo().equals("Crear nueva configuracion"))
            {
                anchoTablero = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                    "De cuantos casilleros debe ser el ancho del tablero? (Numero mayor o igual a 3) ",
                3);
                altoTablero = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                    "De cuantos casilleros debe ser el alto del tablero? (Numero mayor o igual a 3) ",
                3);
                profundidadTablero = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                    "De cuantos casilleros debe ser la profundidad del tablero? (Numero mayor o igual a 3) ",
                3);

                int longitudMaximaTateti = ConfiguracionPartida.obtenerLongitudMaximaDeTateti(
                    anchoTablero, altoTablero, profundidadTablero);

                cantidadFichasSeguidasParaGanar = Consola.obtenerNumeroEnteroEnRangoDelUsuario(
                        "Cuantos fichas seguidas para ganar? (Numero entre 3 y " +
                         longitudMaximaTateti + ") ",
                         3,
                          longitudMaximaTateti);

                int cantidadMaximaDeJugadoresParaElTablero = ConfiguracionPartida.
                          obtenerCantidadMaximaDeJugadoresParaElTablero(
                anchoTablero, altoTablero, profundidadTablero, cantidadFichasSeguidasParaGanar);

                cantidadJugadores = Consola.obtenerNumeroEnteroEnRangoDelUsuario(
                    "Cuantos jugadores? (Numero entre 2 y " + 
                    cantidadMaximaDeJugadoresParaElTablero + ") ",2,
                    cantidadMaximaDeJugadoresParaElTablero);

                int cantidadMaximaDeFichasPorJugadorEnElTablero = ConfiguracionPartida.
                    obtenerCantidadMaximaDeFichasPorJugadorEnElTablero(
                    anchoTablero, altoTablero, profundidadTablero,
                    cantidadFichasSeguidasParaGanar, cantidadJugadores);
                
                cantidadDeFichasPorJugador = Consola.obtenerNumeroEnteroEnRangoDelUsuario(
                        "Cuantos fichas por jugador? (Numero entre " + 
                        cantidadFichasSeguidasParaGanar + " y " +
                        cantidadMaximaDeFichasPorJugadorEnElTablero + ") ",
                         cantidadFichasSeguidasParaGanar, 
                        cantidadMaximaDeFichasPorJugadorEnElTablero);
        
                cantidadCartasPorJugador = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                        "Cuantos cartas por jugador? (Numero mayor o igual a 1) ",1);

                String resumenOpcionesIngresadas = "Dimensiones del tablero: (" +
                                                anchoTablero + ", " +
                                                altoTablero + ", " + 
                                                profundidadTablero + ")\n" +
                 "Cantidad de jugadores: " + cantidadJugadores + "\n" + 
                 "Cantidad de fichas por jugador: " + cantidadDeFichasPorJugador + "\n" + 
                 "Cantidad de cartas por jugador: " + cantidadCartasPorJugador + "\n" + 
                 "Longitud del tateti: " + cantidadFichasSeguidasParaGanar + "\n";

                Consola.imprimirMensaje(resumenOpcionesIngresadas);
                if(Consola.obtenerConfirmacionDelUsuario("Confirmar configuracion?", false))
                {
                    tateti = new Tateti(anchoTablero, altoTablero, profundidadTablero,
                    cantidadFichasSeguidasParaGanar,
                    cantidadJugadores,
                    cantidadDeFichasPorJugador, cantidadCartasPorJugador
                     );
                    usuarioEligioConfiguracion = true;
                }
            }
            else
            {
                Consola.imprimirMensaje(opcion.getDescripcion());
                if(Consola.obtenerConfirmacionDelUsuario("Confirmar configuracion?", false))
                {
                    tateti = new Tateti(opcion);
                    usuarioEligioConfiguracion = true;
                }
            }
        }
        if(tateti == null)
        {
            throw new Exception("No se pudo iniciar la partida correctamente");
        }
        tateti.jugar();
    }
}
