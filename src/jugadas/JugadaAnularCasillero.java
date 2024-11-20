package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Casillero;
import tateti.Ficha;
import tateti.Tateti;
import tateti.Turno;

public class JugadaAnularCasillero extends Jugada{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
public JugadaAnularCasillero(Carta carta) {
    super(carta);
}

//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------

@Override
public boolean jugar(Tateti tateti, Turno turnoActual) throws Exception {
    Casillero<Ficha> casilleroAAnular;
    casilleroAAnular = tateti.obtenerCasilleroDirectoDelUsuario("Se necesitan los datos del casillero a anular");
    if(casilleroAAnular == null)
    {
        return false;
    }
    casilleroAAnular.incrementarBloqueosRestantes(1);
    tateti.getTablero().getCasillerosBloqueados().agregar(casilleroAAnular);
    getCasillerosAfectados().agregar(casilleroAAnular);
    Consola.imprimirMensaje("Se anulo correctamente el " + casilleroAAnular.toString() + "!");
    
    return true;

}

@Override
public void deshacer(Tateti tateti) throws Exception {
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        @SuppressWarnings("unchecked")
        Casillero<Ficha> casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        if(casilleroAfectado.estaBloqueado())
        {
            casilleroAfectado.reducirBloqueosRestantes(1);
            try {
                tateti.getTablero().getCasillerosBloqueados().removerPrimeraAparicion(casilleroAfectado);
                Consola.imprimirMensaje("Se libero correctamente el " + casilleroAfectado.toString() + "!");
            } catch (Exception e) {
                Consola.imprimirMensaje("No se pudo deshacer la jugada correctamente" + e.getMessage());
            }
        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}
