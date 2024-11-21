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
             2, 
             3,
              3,
               3));

        opcionesTateti.agregar(new ConfiguracionPartida(
                "Cubo 3x3x3",
                 3, 3, 3,
                 2, 
                 4,
                  3,
                   3));
        opcionesTateti.agregar(new ConfiguracionPartida(
                    "Cubo 5x5x5",
                     5, 5, 5,
                     3, 
                     5,
                      4,
                       4));
        opcionesTateti.agregar(new ConfiguracionPartida(
                        "Cubo 10x10x10",
                         10, 10, 10,
                         5, 
                         8,
                          6,
                           6));
        opcionesTateti.agregar(new ConfiguracionPartida(
                            "Crear nueva configuracion",
                             3, 3, 3,
                             2, 
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
    
            if(opcion.getTitulo() == "Crear nueva configuracion")
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
                cantidadJugadores = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                    "Cuantos jugadores? (Numero mayor o igual a 2) ",1);
                
                cantidadDeFichasPorJugador = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                        "Cuantos fichas por jugador? (Numero mayor o igual a 3) ",3);
        
                cantidadCartasPorJugador = Consola.obtenerNumeroEnteroEnRangoMinimoDelUsuario(
                        "Cuantos cartas por jugador? (Numero mayor o igual a 1) ",1);
                cantidadFichasSeguidasParaGanar = Consola.obtenerNumeroEnteroEnRangoDelUsuario(
                        "Cuantos fichas seguidas para ganar? (Numero entre 3 y " +
                         cantidadDeFichasPorJugador + ") ",
                         3,
                          cantidadDeFichasPorJugador);
        
                tateti = new Tateti(anchoTablero, altoTablero, profundidadTablero, cantidadJugadores,
                                            cantidadDeFichasPorJugador, cantidadCartasPorJugador,
                                             cantidadFichasSeguidasParaGanar);
                usuarioEligioConfiguracion = true;
        
            }
            else
            {
                Consola.imprimirMensaje(opcion.getDescripcion());
                if(Consola.obtenerConfirmacionDelUsuario("Confirmar configuracion?"))
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
