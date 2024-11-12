package estructuras;

public class Estado{
    //ATRIBUTOS -----------------------------------------------------------------------------------------------
    private TipoEstado tipo;


    // CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * Crea una instancia de Estado con un tipo específico de estado.
     * pre: El tipo de estado debe ser válido (no nulo).
     * @param tipo El tipo de estado inicial.
     * pos: Se crea un Estado con el tipo especificado.
     * @throws Exception Si el tipo de estado es nulo.
     */

    public Estado(TipoEstado tipo) throws Exception
    {
        if(tipo == null)
        {
            throw new Exception("El tipo de estado no debe ser nulo");
        }
        this.tipo = tipo;
    }

    /**
     * Crea una instancia de Estado con el tipo de estado por defecto (NORMAL).
     * pre: -
     * @throws Exception Si ocurre un error al asignar el tipo de estado.
     * pos: Se crea un Estado con el tipo NORMAL.
     */
    public Estado() throws Exception {
        this(TipoEstado.NORMAL);
    }


    // METODOS GENERALES ---------------------------------------------------------------------------------------
    /**
     * Obtiene el tipo de estado actual.
     * pre: -
     * @return El tipo de estado actual.
     * pos: El estado no cambia y se retorna el tipo de estado actual.
     */
    public TipoEstado obtenerTipoEstado() {
        return this.tipo;
    }


    // METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * Cambia el tipo de estado a BLOQUEADO.
     * pre: -
     * pos: El tipo de estado se cambia a BLOQUEADO.
     */
    public void bloquear() {
        this.tipo = TipoEstado.BLOQUEADO;
    }

    /**
     * Cambia el tipo de estado a NORMAL.
     * pre: -
     * pos: El tipo de estado se cambia a NORMAL.
     */
    public void desbloquear() {
        this.tipo = TipoEstado.NORMAL;
    }
}
