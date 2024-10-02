
import java.util.ArrayList;
import java.util.List;

public class Turno {
    public static int numeroDeTurnoActual = 1;

    private int numero;
    private List<Celda> celdasModificadas;
    private Carta cartaUtilizada;
    private Jugador jugadorAsignado, jugadorAfectado;

    public Turno(Jugador jugadorAsignado, Carta cartaUtilizada, Jugador jugadorAfectado) throws Exception
    {
        if(jugadorAsignado == null)
        {
            throw new Exception("El jugador no puede ser nulo");
        }
        if(cartaUtilizada != null && jugadorAfectado == null)
        {
            throw new Exception("El jugador afectado por la carta no puede ser nulo");
        }
        this.jugadorAsignado = jugadorAsignado;
        this.numero = numeroDeTurnoActual;
        numeroDeTurnoActual++;
        this.celdasModificadas = new ArrayList<Celda>();
        this.cartaUtilizada = cartaUtilizada;
        this.jugadorAfectado = jugadorAfectado;
    }

    public Turno(Jugador jugador) throws Exception
    {
        this(jugador, null, null);
    }
}
