public class Celda {

    private int[] posicion;
    private Ficha ficha;
    private Estado estado;

    public Celda(int posX, int posY, int posZ) throws Exception
    {
        if(posX < 0 || posY < 0 || posZ < 0)
        {
            throw new Exception("Las posiciones deben ser mayores o iguales a 0");
        }
        this.posicion = new int[]{posX, posY, posZ};
        this.ficha = null;
        this.estado = new Estado();
    }

    public Celda(int posX, int posY) throws Exception
    {
        this(posX, posY, 0);
    }

    public int[] obtenerPosicionEnElTablero()
    {
        int[] coordenadas = new int[3];
        for(int i = 0; i < this.posicion.length; i++)
        {
            coordenadas[i] = this.posicion[i];
        }
        return coordenadas;
    }

    public Ficha obtenerFicha()
    {
        return this.ficha;
    }

    public Estado obtenerEstado()
    {
        return this.estado;
    }

    public boolean estaVacia()
    {
        return this.ficha == null;
    }

    public void AsignarFicha(Ficha ficha) throws Exception
    {
        if(ficha == null)
        {
            throw new Exception("La ficha a asignar no debe ser nula");
        }
        if(obtenerEstado().obtenerTipoEstado() == TipoEstado.BLOQUEADO)
        {
            throw new Exception("La celda esta bloqueada");
        }
        if(!estaVacia())
        {
            throw new Exception("Ya existe la ficha " + obtenerFicha() + "en la celda");
        }
        this.ficha = ficha;
    }
    public Ficha RemoverFicha() throws Exception
    {
        if(obtenerEstado().obtenerTipoEstado() == TipoEstado.BLOQUEADO)
        {
            throw new Exception("La celda esta bloqueada");
        }
        if(estaVacia())
        {
            throw new Exception("La celda no posee ninguna ficha");
        }
        Ficha ficha = obtenerFicha();
        this.ficha = null;
        return ficha;
    }
}
