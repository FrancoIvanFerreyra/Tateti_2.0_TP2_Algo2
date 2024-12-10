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
 * El jugador decide que ficha del tablero quiere mover a un casillero en especifico 
 */
public class JugadaTeletransportarFicha extends Jugada{

    /**
     * pre: recibe la carta que se quiere utilizar como base para almancenar la jugada 
     * pos: se incializa una carta con la jugada que teletransporta una ficha
     */
    public JugadaTeletransportarFicha(Carta carta){
        super(carta);
    }
    /*
     * pre: recibe el estado del tateti y el turno acutual del jugador 
     * pos: mueve una ficha desde un casillero a otro, se valida que la ficha a mover exista y el casillero destino sea vallido
     *       en caso de que se produzca el cambio se retorna true, caso contrario false.
     */
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) throws NullPointerException{
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        ValidacionesUtiles.validarNoNull(turnoActual, "turnoActual");
        
        Jugador jugadorActual;
        Casillero<Ficha> casilleroDestino = null;
        Casillero<Ficha> casilleroOrigen = null;
        Ficha fichaATeletransportar;

        jugadorActual = turnoActual.getJugador();
        Vector<Ficha> fichas = jugadorActual.getFichas().filtrar(
            ficha -> {
                        return ficha != null &&
                        tateti.getTablero().contiene(ficha) &&
                        !ficha.estaBloqueado();
                }
            );
            Lista<Casillero<Ficha>> casillerosFichas = tateti.getTablero().getCasilleros(fichas);

        while(casilleroDestino == null)
        {
            try {
                casilleroOrigen = Consola.consultarOpcionAlUsuario(casillerosFichas,
                "Que ficha desea teletransportar?",
                 true);

            } catch (IllegalArgumentException e) {
                Consola.imprimirMensajeConSalto("No tenes fichas en el tablero o todas estan bloqueadas!");
                return false;
            }
            if(casilleroOrigen == null)
            {
                return false;
            }
            casilleroDestino = tateti.obtenerCasilleroDirectoDelUsuario(
                            "A que casillero desea teletransportar su ficha?",
                             true);
        }

        if(casilleroOrigen == null)
        {
            return false;
        }
        fichaATeletransportar = casilleroOrigen.getDato();

        casilleroDestino.setDato(fichaATeletransportar);
        casilleroOrigen.vaciar();
        tateti.getTablero().actualizarRelacionDatoCasillero(fichaATeletransportar, casilleroDestino);

        setJugador(jugadorActual);
        getCasillerosAfectados().agregar(casilleroDestino);
        getCasillerosAfectados().agregar(casilleroOrigen);
        Consola.imprimirMensajeConSalto("Se teletransporto correctamente la ficha ubicada en " + 
                                casilleroOrigen.toString() + " a " +
                                casilleroDestino.toString() + "!");                      
        return true;

    }
    /**
     * pre: recieb el estado del tateti 
     * pos: la ficha transportada vuelve a su casillero de origen y vacia el casillero donde se encotraba, se valida que el casillero de origen siga disponible
     */
    @Override
    public void deshacer(Tateti tateti) throws NullPointerException,
                                         IllegalArgumentException {
        ValidacionesUtiles.validarNoNull(tateti, "tateti");
        if(this.getJugador() == null || getCasillerosAfectados().getTamanio() < 2)
        {
            throw new IllegalArgumentException("No hay informacion suficiente para deshacer la jugada");
        }
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroDestino = getCasillerosAfectados().obtener(1);
        
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroOrigen = getCasillerosAfectados().obtener(2);

        casilleroOrigen.setDato(casilleroDestino.getDato());
        casilleroDestino.vaciar();
        tateti.getTablero().actualizarRelacionDatoCasillero(casilleroOrigen.getDato(), casilleroOrigen);

        Consola.imprimirMensajeConSalto("La ficha teletransportada a " + 
                                casilleroDestino.toString() + "volvio a ubicarse en " + 
                                casilleroOrigen.toString() + "!");  
    }
}
