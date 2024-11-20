package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Tateti;
import tateti.Turno;

public class JugadaRetrocederUnaJugada extends Jugada{

    public JugadaRetrocederUnaJugada(Carta carta) {
        super(carta);
    }

    @Override
    public boolean jugar(Tateti tateti, Turno turnoActual) throws Exception {
        try
        {
            tateti.retrocederTurnos(1);
        }
        catch(Exception e)
        {
            Consola.imprimirMensaje("No hay jugadas que retroceder");
            return false;
        }
        return true;
    }

    @Override
    public void deshacer(Tateti tateti) throws Exception {
        //No se puede deshacer
    }

}
