public class Ficha {
    //ATRIBUTOS -----------------------------------------------------------------------------------------------
    private Jugador jugador;
    private Estado estado;


    // CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * Crea una nueva instancia de Ficha asociada a un jugador específico.
     * pre: El jugador no debe ser nulo.
     * @param jugador El jugador que posee esta ficha.
     * pos: Se crea una Ficha asociada al jugador especificado y con un estado por defecto (NORMAL).
     * @throws Exception Si el jugador es nulo.
     */
    public Ficha(Jugador jugador) throws Exception
    {
        if(jugador == null)
        {
            throw new Exception("El jugador no puede ser nulo");
        }
        this.jugador = jugador;
        this.estado = new Estado();
    }


    // GETTERS SIMPLES -----------------------------------------------------------------------------------------
    /**
     * Obtiene el jugador asociado a esta ficha.
     * pre: -
     * @return El jugador que posee esta ficha.
     * pos: El jugador asociado a la ficha es devuelto sin modificar la ficha.
     */
    public Jugador obtenerJugador(){
        return this.jugador;
    }

    /**
     * Obtiene el estado actual de la ficha.
     * pre: -
     * @return El estado actual de la ficha.
     * pos: El estado de la ficha es devuelto sin modificar la ficha.
     */
    public Estado obtenerEstado(){
        return this.estado;
    }
}
