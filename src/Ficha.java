public class Ficha {

    private Jugador jugador;
    private Estado estado;

    public Ficha(Jugador jugador) throws Exception
    {
        if(jugador == null)
        {
            throw new Exception("El jugador no puede ser nulo");
        }
        this.jugador = jugador;
        this.estado = new Estado();
    }

    public Jugador obtenerJugador(){
        return this.jugador;
    }

    public Estado obtenerEstado(){
        return this.estado;
    }
}
