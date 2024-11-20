package tateti;

import jugadas.Jugada;

public class Turno {
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Jugador jugador = null;
	private Ficha fichaUtilizada = null;
	private Movimiento movimientoAplicado = null;
	private Jugada jugadaEjecutada = null;

	private int cantidadDeSubTurnos = 0;
	private int bloqueosRestantes = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Turno(Jugador jugador) throws Exception
	{
		if(jugador == null)
		{
			throw new Exception("El jugador no puede ser null");
		}
		this.jugador = jugador;
	}
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) {
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws Exception, IllegalArgumentException{
		if(cantidadDeBloqueos < 0)
		{
			throw new Exception("La cantidad de bloqueos debe ser mayor a 0");
		}
		if(this.bloqueosRestantes - cantidadDeBloqueos < 0)
		{
			throw new IllegalArgumentException("No se pueden quitar " + cantidadDeBloqueos + "bloqueos, quedan " + this.bloqueosRestantes);
		}
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

	public void terminarTurno() {
		if (this.bloqueosRestantes > 0) {
			this.bloqueosRestantes--;
		}		
	}

	
	public void iniciarTurno() {
		this.cantidadDeSubTurnos += 1;		
	}
	
	public void agregarSubturno() {
		this.cantidadDeSubTurnos += 1;
	}
	
	public boolean haySubturnos() {
		return this.cantidadDeSubTurnos > 0;
	}

	public void limpiar()
	{
		setFichaUtilizada(null);
		setMovimientoAplicado(null);
		setJugadaEjecutada(null);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	public Jugador getJugador() {
		return jugador;
	}

	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}

	public int getCantidadDeSubTurnos() {
		return this.cantidadDeSubTurnos;
	}

	public void utilizarSubturno() {
		this.cantidadDeSubTurnos--;		
	}

	public Ficha getFichaUtilizada()
	{
		return this.fichaUtilizada;
	}

	public Movimiento getMovimientoAplicado()
	{
		return this.movimientoAplicado;
	}

	public Jugada getJugadaEjecutada()
	{
		return this.jugadaEjecutada;
	}



//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	public void setFichaUtilizada(Ficha ficha)
	{
		this.fichaUtilizada = ficha;
	}

	public void setMovimientoAplicado(Movimiento movimiento)
	{
		this.movimientoAplicado = movimiento;
	}

	public void setJugadaEjecutada(Jugada jugada)
	{
		this.jugadaEjecutada = jugada;
	}
}
