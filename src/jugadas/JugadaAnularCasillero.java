package jugadas;

import cartas.Carta;
import interfaz.Consola;
import tateti.Casillero;
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
    int x, y, z;
    Casillero casilleroAAnular;
    String mensajeError = "";
    do 
    { 
        Consola.imprimirMensaje(mensajeError);
        mensajeError = "";
        x = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada X del casillero a anular:");
        y = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada Y del casillero a anular:");
        z = Consola.obtenerNumeroEnteroDelUsuario("Ingrese coordenada Z del casillero a anular:");
        
        if(tateti.getTablero().existeElCasillero(x, y, z))
        {
            casilleroAAnular = tateti.getTablero().getCasillero(x, y, z);
            if(casilleroAAnular.estaOcupado())
            {
                mensajeError += "Casillero ocupado!\t";
            }
            if(casilleroAAnular.estaBloqueado())
            {
                mensajeError += "Casillero bloqueado!\t";
            }
        }
        else
        {
            casilleroAAnular = null;
        }


    } while (casilleroAAnular == null ||
            casilleroAAnular.estaOcupado() || 
            casilleroAAnular.estaBloqueado());

    casilleroAAnular.incrementarBloqueosRestantes(1);
    tateti.getTablero().getCasillerosBloqueados().agregar(casilleroAAnular);
    getCasillerosAfectados().agregar(casilleroAAnular);
    
    return true;

}

@Override
public void deshacer(Tateti tateti) throws Exception {
    getCasillerosAfectados().iniciarCursor();
    while(getCasillerosAfectados().avanzarCursor())
    {
        Casillero casilleroAfectado = getCasillerosAfectados().obtenerCursor();
        if(casilleroAfectado.estaBloqueado())
        {
            casilleroAfectado.reducirBloqueosRestantes(1);
            tateti.getTablero().getCasillerosBloqueados().iniciarCursor();
            int i = 1;
            while(tateti.getTablero().getCasillerosBloqueados().avanzarCursor())
            {
                if(tateti.getTablero().getCasillerosBloqueados().obtenerCursor().equals(casilleroAfectado))
                {
                    tateti.getTablero().getCasillerosBloqueados().remover(i);
                    break;
                }
                i++;
            }

        }
    }
}

//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
}