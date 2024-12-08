import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import interfaz.Consola;
import interfaz.Teclado;
import tateti.ConfiguracionPartida;
import tateti.Tateti;
import utiles.Utiles;

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
        do {
            boolean usuarioEligioConfiguracion = false;
            Tateti tateti = null;

            while(!usuarioEligioConfiguracion)
            {
                Consola.imprimirMensajeConSalto("Tateti 2.0 - Hecho por Todos Desarrollamos Algo");
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
                        "De cuantos casilleros debe ser la profundidad del tablero? (Numero mayor o igual a 1) ",
                    1);
    
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
    
                    ConfiguracionPartida nuevaConfiguracion = new 
                                    ConfiguracionPartida(
                                "Nueva configuracion",
                                        anchoTablero, altoTablero,
                                        profundidadTablero,
                                        cantidadFichasSeguidasParaGanar,
                                        cantidadJugadores,
                                        cantidadDeFichasPorJugador,
                                        cantidadCartasPorJugador);
    
                    Consola.imprimirMensajeConSalto(nuevaConfiguracion.getDescripcion());
                    if(Consola.obtenerConfirmacionDelUsuario("Confirmar configuracion?", false))
                    {
                        tateti = new Tateti(nuevaConfiguracion);
                        opcionesTateti.agregar(nuevaConfiguracion);
                        usuarioEligioConfiguracion = true;
                    }
                }
                else
                {
                    Consola.imprimirMensajeConSalto(opcion.getDescripcion());
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
        while(Consola.obtenerConfirmacionDelUsuario("Desea jugar otra partida?", false));
        Consola.imprimirMensaje("Gracias por jugar Tateti 2.0! Saliendo...");
        Teclado.finalizar();
        Utiles.pausarEjecucion(1500);


    }
}
