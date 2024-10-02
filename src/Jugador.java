
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private int numero;
    private List<Carta> cartas;
    private Carta cartaActiva;
    private Estado estado;
    private int turnosAPerder;

    public Jugador(int numero) throws Exception
    {
        if(numero <= 0)
        {
            throw new Exception("El numero debe ser mayor a 0");
        }
        this.numero = numero;
        this.cartas = new ArrayList<Carta>();
        this.cartaActiva = null;
        this.estado = Estado.NORMAL;
        this.turnosAPerder = 0;
    }
}
