package tateti;

import interfaces.Bloqueable;
import jugadas.Jugada;
import utiles.ValidacionesUtiles;

public class Turno implements Bloqueable{
//ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
//ATRIBUTOS -----------------------------------------------------------------------------------------------
	
	private Jugador jugador = null;
	private Ficha fichaUtilizada = null;
	private Movimiento movimientoAplicado = null;
	private Jugada jugadaEjecutada = null;

	private int cantidadDeSubTurnos = 0;
	private int bloqueosRestantes = 0;
	
//CONSTRUCTORES -------------------------------------------------------------------------------------------

	public Turno(Jugador jugador) throws NullPointerException
	{
		ValidacionesUtiles.validarNoNull(jugador, "jugador");
		this.jugador = jugador;
	}
//METODOS DE CLASE ----------------------------------------------------------------------------------------
//METODOS GENERALES ---------------------------------------------------------------------------------------
//METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
	
	/**
	 * Incrementa los bloqueos restantes del casillero
	 * @param cantidadDeBloqueos: mayor o igual a 1
	 * @throws IllegalArgumentException si cantidadDeBloqueos es menor a 1
	 */
	@Override
	public void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException{
		ValidacionesUtiles.validarEnteroMinimo(cantidadDeBloqueos, 1, "cantidadDeBloqueos");
		this.bloqueosRestantes += cantidadDeBloqueos;
	}


	/**
	 * Reduce los bloqueos restantes del casillero. El casillero debe tener bloqueos restantes
	 * @param cantidadDeBloqueos: debe estar dentro del tango [1, this.cantidadDeBloqueosRestantes]
	 * @throws IllegalArgumentException si cantidadDeBloqueos es menor a 1 o mayor a this.bloqueosRestantes
	 */
	@Override
	public void reducirBloqueosRestantes(int cantidadDeBloqueos) throws IllegalArgumentException {
		ValidacionesUtiles.validarEnteroEnRango(cantidadDeBloqueos, 1,
												 this.bloqueosRestantes, "cantidadDeBloqueos");
		this.bloqueosRestantes -= cantidadDeBloqueos;
	}

	/**
	 * @return devuelve verdadero si el casillero tiene bloqueos restantes
	 */
	@Override
	public boolean estaBloqueado() {
		return this.bloqueosRestantes > 0;
	}

	/**
	 * Finaliza el turno. Si tiene bloqueos restantes, reduce 1
	 */
	public void terminarTurno() {
		if (this.estaBloqueado()) {
			this.bloqueosRestantes--;
		}		
	}
	
	/**
	 * Inicia el turno
	 */
	public void iniciarTurno() {
		this.cantidadDeSubTurnos += 1;		
	}
	
	/**
	 * Agrega un subturno al turno
	 */
	public void agregarSubturno() {
		this.cantidadDeSubTurnos += 1;
	}
	
	/**
	 * 
	 * @return devuelve verdadero si el turno tiene subturnos disponibles
	 */
	public boolean haySubturnos() {
		return this.cantidadDeSubTurnos > 0;
	}


	/**
	 * limpia los datos del turno
	 */
	public void limpiar()
	{
		setFichaUtilizada(null);
		setMovimientoAplicado(null);
		setJugadaEjecutada(null);
	}
	
//GETTERS SIMPLES -----------------------------------------------------------------------------------------
	
	/**
	 * 
	 * @return devuelve el jugador del turno
	 */
	public Jugador getJugador() {
		return jugador;
	}

	/**
	 * 
	 * @return devuelve la cantidad de subturnos del turno
	 */
	public int getCantidadDeSubTurnos() {
		return this.cantidadDeSubTurnos;
	}

	/**
	 * 
	 * reduce un subturno del total si tiene alguno
	 */
	public void utilizarSubturno() {
		if(this.haySubturnos())
		this.cantidadDeSubTurnos--;		
	}

	/**
	 * 
	 * @return devuelve la ficha utilizada en este turno
	 */
	public Ficha getFichaUtilizada()
	{
		return this.fichaUtilizada;
	}

	/**
	 * 
	 * @return devuelve el movimiento aplicado a la ficha de este turno
	 */
	public Movimiento getMovimientoAplicado()
	{
		return this.movimientoAplicado;
	}

	/**
	 * 
	 * @return devuelve la jugada ejecutada en este turno
	 */
	public Jugada getJugadaEjecutada()
	{
		return this.jugadaEjecutada;
	}

	/**
	 * 
	 * @return devuelve la cantidad de bloqueos restantes del turno
	 */
	@Override
	public int getBloqueosRestantes()
	{
		return this.bloqueosRestantes;
	}



//SETTERS SIMPLES -----------------------------------------------------------------------------------------	
	
	/**
	 * Asigna la ficha utilizada del turno
	 * @param ficha
	 */
	public void setFichaUtilizada(Ficha ficha)
	{
		this.fichaUtilizada = ficha;
	}

	/**
	 * Asigna el movimiento aplicado del turno
	 * @param movimiento
	 */
	public void setMovimientoAplicado(Movimiento movimiento)
	{
		this.movimientoAplicado = movimiento;
	}

	/**
	 * Asigna la jugada ejecutada del turno
	 * @param jugada
	 */
	public void setJugadaEjecutada(Jugada jugada)
	{
		this.jugadaEjecutada = jugada;
	}
}
