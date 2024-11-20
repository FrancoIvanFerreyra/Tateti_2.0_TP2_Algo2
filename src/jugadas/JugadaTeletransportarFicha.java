package jugadas;

import cartas.Carta;
import estructuras.Vector;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Jugador;
import tateti.Tateti;
import tateti.Turno;


public class JugadaTeletransportarFicha extends Jugada{

    public JugadaTeletransportarFicha(Carta carta){
        super(carta);
    }
    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) throws Exception{
        Jugador jugadorActual =null;
        Casillero<Ficha> casilleroDestino = null;
        Casillero<Ficha> casilleroOrigen = null;
        Ficha fichaATeletransportar;
        
        while(casilleroDestino == null)
        {
            jugadorActual = turnoActual.getJugador();
            Vector<Ficha> fichas = jugadorActual.getFichas().filtrar(
                ficha -> {
                        try {
                            return tateti.getTablero().contiene(ficha) &&
                                    !ficha.estaBloqueado();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                ); 
            casilleroOrigen = tateti.obtenerCasilleroDeFichaDelUsuario(fichas, "Que ficha desea teletransportar?");
            if(casilleroOrigen == null)
            {
                return false;
            }
            casilleroDestino = tateti.obtenerCasilleroDirectoDelUsuario("A que casillero desea teletransportar su ficha?");
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
        return true;

    }
    
    @Override
    public void deshacer(Tateti tateti) throws Exception {
        if(this.getJugador() == null || getCasillerosAfectados().getTamanio() < 2)
        {
            throw new Exception("No hay informacion suficiente para deshacer la jugada");
        }
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroDestino = getCasillerosAfectados().obtener(1);
        
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroOrigen = getCasillerosAfectados().obtener(2);

        casilleroOrigen.setDato(casilleroDestino.getDato());
        casilleroDestino.vaciar();
        tateti.getTablero().actualizarRelacionDatoCasillero(casilleroOrigen.getDato(), casilleroOrigen);
    }
}
