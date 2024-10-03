
import java.util.List;

public class Turno {
    public static int numeroDeTurnoActual = 1;

    private int numero;
    private Celda[] celdasModificadas;
    private Carta cartaUtilizada;
    private Jugador jugadorAsignado;
    private Jugador[] jugadoresAfectados;

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
        this.celdasModificadas = (Celda[])celdasModificadas.toArray();
        this.cartaUtilizada = cartaUtilizada;
        this.jugadoresAfectados = (Jugador[])jugadoresAfectados.toArray();
    }
}
