

public class Estado{
    private TipoEstado tipo;

    public Estado(TipoEstado tipo) throws Exception
    {
        if(tipo == null)
        {
            throw new Exception("El tipo de estado no debe ser nulo");
        }
        this.tipo = tipo;
    }

    public Estado() throws Exception
    {
        this(TipoEstado.NORMAL);
    }

    public TipoEstado obtenerTipoEstado()
    {
        return this.tipo;
    }

    public void bloquear()
    {
        this.tipo = TipoEstado.BLOQUEADO;
    }

    public void desbloquear()
    {
        this.tipo = TipoEstado.NORMAL;
    }
}
