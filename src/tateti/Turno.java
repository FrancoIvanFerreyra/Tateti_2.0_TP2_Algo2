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
	/**
	 * Crea un nuevo turno para un jugador.
	 * pre: jugador != null.
	 * @param jugador El jugador que inicia el turno no puede ser null
	 * @throws Exception Si el jugador es null.
	 */
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
	/**
	 * Incrementa el numero de bloqueos restantes en el turno.
	 * pre: cantidadDeBloqueos >= 0.
	 * pos: La cantidad de bloqueos restantes se incrementa en la cantidad indicada.
	 * @param cantidadDeBloqueos La cantidad de bloqueos a añadir.
	 * @throws Exception Si la cantidad de bloqueos es menor a cero
	 * 
	 */
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws Exception {
		if(cantidadDeBloqueos < 0) {
			throw new Exception("La cantidad de bloqueos debe ser mayor a 0");
		}
		this.bloqueosRestantes += cantidadDeBloqueos;
	}

	/**
	 * Reduce el numero de bloqueos restantes en el turno.
	 * pre: cantidadDeBloqueos > 0 y cantidadDeBloqueos <= bloqueosRestantes.
	 * pos: La cantidad de bloqueos restantes se reduce en la cantidad indicada.
	 * @param cantidadDeBloqueos La cantidad de bloqueos a quitar.
	 * @throws Exception Si la cantidad de bloqueos es menor o igual a cero, o si intentas reducir más bloqueos de los disponibles.
	 */
	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws Exception{
		if(cantidadDeBloqueos < 0)
		{
			throw new Exception("La cantidad de bloqueos debe ser mayor a 0");
		}
		if(this.bloqueosRestantes - cantidadDeBloqueos < 0)
		{
			throw new Exception("No se pueden quitar " + cantidadDeBloqueos + "bloqueos, quedan " + this.bloqueosRestantes);
		}
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

	/**
	 * Finaliza el turno actual reduciendo la cantidad de bloqueos restantes si hay bloqueos disponibles.
	 * pre: bloqueosRestantes >= 0.
	 * pos: Si hay bloqueos restantes, se reduce su cantidad en 1.
	 */
	public void terminarTurno() {
		if (this.bloqueosRestantes > 0) {
			this.bloqueosRestantes--;
		}		
	}

	/**
	 * Inicia un nuevo turno incrementando la cantidad de subturnos.
	 * pre: -
	 * pos: La cantidad de subturnos se incrementa en 1.
	 */	
	public void iniciarTurno() {
		this.cantidadDeSubTurnos += 1;		
	}

	/**
	 * Agrega un subturno al turno actual.
	 * pre: -
	 * pos: La cantidad de subturnos se incrementa en 1.
	 */	
	public void agregarSubturno() {
		this.cantidadDeSubTurnos += 1;
	}

	/**
	 * Verifica si hay subturnos disponibles.
	 * pre: -
	 * pos: Devuelve true si cantidadDeSubTurnos > 0, de lo contrario devuelve false.
	 * @return true si hay subturnos disponibles; false en caso contrario.
	 */	
	public boolean haySubturnos() {
		return this.cantidadDeSubTurnos > 0;
	}

	/**
	 * Limpia el estado actual del turno, eliminando datos de ficha, movimiento y jugada.
	 * pre: -
	 * pos: fichaUtilizada, movimientoAplicado y jugadaEjecutada quedan en null.
	 */
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
