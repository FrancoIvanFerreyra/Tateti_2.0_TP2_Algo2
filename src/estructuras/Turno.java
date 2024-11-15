
import java.util.List;

public class Turno {
    public static int numeroDeTurnoActual = 1;

    private int numero;
    private Vector<Celda> celdasModificadas;
    private Carta cartaUtilizada;
    private Jugador jugadorAsignado;
    private Vector<Jugador> jugadoresAfectados;

    public Turno(Jugador jugadorAsignado, List<Celda> celdasModificadas, Carta cartaUtilizada, List<Jugador> jugadoresAfectados) throws Exception
    {
        if(jugadorAsignado == null)
        {
            throw new Exception("El jugador no puede ser nulo");
        }
        if(celdasModificadas == null)
        {
            throw new Exception("La lista de celdas modificadas no puede ser nula");
        }

        if(jugadoresAfectados == null)
        {
            throw new Exception("La lista de jugadores afectados no puede ser nula");
        }

        if(cartaUtilizada == null
            && !jugadoresAfectados.isEmpty())
        {
            throw new Exception("No se pueden afectar a jugadores sin una carta");
        }
        this.numero = numeroDeTurnoActual;
        numeroDeTurnoActual++;
        this.jugadorAsignado = jugadorAsignado;
        this.celdasModificadas = new Vector<>(celdasModificadas);
        this.cartaUtilizada = cartaUtilizada;
        this.jugadoresAfectados = new Vector<>(jugadoresAfectados);
    }
    
    public int obtenerNumero()
    {
        return this.numero;
    }
    public Celda[] obtenerCeldasModificadas()
    {
        return this.celdasModificadas.obtenerElementos();
    }

    public boolean existenCeldasModificadas()
    {
        return ValidacionesUtiles.esMayorQue(this.celdasModificadas.obtenerElementos().length, 0);
    }

    public Carta obtenerCartaUtilizada()
    {
        return this.cartaUtilizada;
    }
    public Jugador obtenerJugadorAsignado()
    {
        return this.jugadorAsignado;
    }
    public Jugador[] obtenerJugadoresAfectados()
    {
        return this.jugadoresAfectados.obtenerElementos();
    }
}
