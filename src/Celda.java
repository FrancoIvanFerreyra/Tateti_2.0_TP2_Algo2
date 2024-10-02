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
}
