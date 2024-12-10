package jugadas;

import cartas.Carta;
import estructuras.Lista;
import estructuras.Vector;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;
import utiles.ValidacionesUtiles;

/**
 *  El jugador decide que ficha de otro jugador cambiarle el color y apropiarsela
 */
public class JugadaCambiarColorFicha extends Jugada {
    /**
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
     * pos: se incializa una carta con la jugada que cambia el color de una ficha
     */
    public JugadaCambiarColorFicha(Carta carta) {
        super(carta);
    }
    /*
     * pre: recibe el estado del tateti y el turno acutual del jugador
     * pos: se cambia el color de la ficha de un oponente, seleccionado previamente, y de esta manera se realiza un intercambio de fichas, en caso de que no se 
     *      pueda realizar el intercambio se lanza un mensaje de error y retorna false, en caso de que el intercambio se cumpla retorna true  
     */
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) throws NullPointerException{
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");

        Jugador jugadorBeneficiado;
        Jugador jugadorAfectado;
        Ficha fichaAColocar;
        jugadorBeneficiado = turnoActual.getJugador();
        Casillero<Ficha> casilleroFichaACambiarColor;
        
        if(jugadorBeneficiado.tieneTodasLasFichasEnElTablero(tateti.getTablero()))
        {
            Consola.imprimirMensajeConSalto("No tenes fichas disponibles para intercambiar." +
            " Todas estan en el tablero!");
            return false;
        }
        
        do 
        {
            Vector<Jugador> jugadores = tateti.getJugadores().filtrar(
            jugador -> (jugador != null &&
            jugador != jugadorBeneficiado &&
            jugador.tieneAlgunaFichaEnElTablero(tateti.getTablero())
            )
            );
            try {
                // Seleccionar jugador afectado
                jugadorAfectado = Consola.consultarOpcionAlUsuario(jugadores,
                "¿A qué jugador desea cambiarle la ficha?", true);
                
            } catch (IllegalArgumentException e) {
                Consola.imprimirMensajeConSalto("No hay fichas de otros jugadores en el tablero.");
                return false;
            }
            
            if (jugadorAfectado == null) {
                return false;
            }
            
            Vector<Ficha> fichas = jugadorAfectado.getFichas().filtrar(
            ficha -> {
                return ficha != null &&
                tateti.getTablero().contiene(ficha) &&
                !ficha.estaBloqueado();
            }
            );
            Lista<Casillero<Ficha>> casillerosFicha = tateti.getTablero().getCasilleros(fichas);
            try {
                casilleroFichaACambiarColor = Consola.consultarOpcionAlUsuario(casillerosFicha,
                "Que ficha desea cambiar de color?",
                true);
            } catch (IllegalArgumentException e) {
                Consola.imprimirMensajeConSalto("Todas las fichas del jugador " + jugadorAfectado + 
                " están bloqueadas!");
                casilleroFichaACambiarColor = null;
            }
        } while (casilleroFichaACambiarColor == null);
        
        fichaAColocar = new Ficha(String.valueOf(jugadorBeneficiado.getId()));
        Ficha fichaACambiarDeColor = casilleroFichaACambiarColor.getDato();
        casilleroFichaACambiarColor.vaciar();
        tateti.getTablero().eliminarRelacionDatoCasillero(fichaACambiarDeColor);
        tateti.getTablero().eliminarRelacionDatoColor(fichaACambiarDeColor);
        jugadorAfectado.quitarFicha(fichaACambiarDeColor);
        
        casilleroFichaACambiarColor.setDato(fichaAColocar);
        tateti.getTablero().actualizarRelacionDatoCasillero(fichaAColocar, casilleroFichaACambiarColor);
        tateti.getTablero().actualizarRelacionDatoColor(fichaAColocar, jugadorBeneficiado.getColor());
        
        jugadorBeneficiado.agregarFicha(fichaAColocar);
        
        setJugador(jugadorBeneficiado);
        getJugadoresAfectados().agregar(jugadorAfectado);
        getCasillerosAfectados().agregar(casilleroFichaACambiarColor);
        Consola.imprimirMensajeConSalto("Se cambio correctamente el color de la ficha ubicada en " + 
        casilleroFichaACambiarColor.toString() + "!");      
        return true;
    }

    /*
     * pre: se recibe el estaedo del tablero
     * pos: se busca en la lista de casilleros y jugadores afectados los atributos que intervinieron en esta jugada,se regresa su color y casillero original, 
     *      vaciando el nuevo casillero. En caso de que no esten en el listado ambos atributos o se produzca algun error se lanza un mensaje de error
     */
    @Override
    public void deshacer(Tateti tateti) throws NullPointerException{
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        if (getJugadoresAfectados().estaVacia() || getCasillerosAfectados().estaVacia()) {
            Consola.imprimirMensajeConSalto("No hay información suficiente para deshacer la jugada.");
            return;
        }
        
        getJugadoresAfectados().iniciarCursor();
        while(getJugadoresAfectados().avanzarCursor())
        {
            Jugador jugadorAfectado = getJugadoresAfectados().obtenerCursor();
            getCasillerosAfectados().iniciarCursor();
            while(getCasillerosAfectados().avanzarCursor())
            {
                @SuppressWarnings("unchecked")
                Casillero<Ficha> casillero = getCasillerosAfectados().obtenerCursor();
                Ficha fichaAEliminar = casillero.getDato();
                casillero.vaciar();
                tateti.getTablero().eliminarRelacionDatoCasillero(fichaAEliminar);
                tateti.getTablero().eliminarRelacionDatoColor(fichaAEliminar);
                getJugador().quitarFicha(fichaAEliminar);
                
                Ficha fichaAReponer = new Ficha(String.valueOf(jugadorAfectado.getId()));
                casillero.setDato(fichaAReponer);
                tateti.getTablero().actualizarRelacionDatoCasillero(fichaAReponer, casillero);
                tateti.getTablero().actualizarRelacionDatoColor(fichaAReponer, jugadorAfectado.getColor());
                jugadorAfectado.agregarFicha(fichaAReponer);
                
                Consola.imprimirMensajeConSalto("La ficha ubicada en " + 
                casillero.toString() + "volvio a su color anterior!");              
            }
            
        }
    }
}
