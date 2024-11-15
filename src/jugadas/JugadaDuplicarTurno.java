package jugadas;

import cartas.Carta;
import tateti.Tateti;
import tateti.Turno;

public class JugadaDuplicarTurno extends Jugada {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
//CONSTRUCTORES -------------------------------------------------------------------------------------------
	
	public JugadaDuplicarTurno(Carta carta) {
		super(carta);
	}
	
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	@Override
	public void jugar(Tateti tateti, 
						Turno turnoActual) throws Exception {
		turnoActual.agregarSubturno();
	}

	@Override
    public void deshacer(Tateti tateti) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
//SETTERS SIMPLES -----------------------------------------------------------------------------------------	


}