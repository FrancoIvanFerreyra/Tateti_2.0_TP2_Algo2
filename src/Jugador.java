
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
        this.estado = new Estado();
        this.turnosAPerder = 0;
    }

    public int obtenerNumero()
    {
        return this.numero;
    }

    public List<Carta> obtenerCartas()
    {
        List<Carta> cartas = new ArrayList<Carta>();
        for(Carta carta : this.cartas){
            cartas.add(carta);
        }
        return cartas;
    }

    public int obtenerCantidadCartas()
    {
        return this.cartas.size();
    }

    public Carta obtenerCartaActiva()
    {
        return this.cartaActiva;
    }

    public Estado obtenerEstado()
    {
        return this.estado;
    }

    public boolean tieneTurnosAPerder()
    {
        return this.turnosAPerder > 0;
    }
    
    public void agregarCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser nula");
        }
        this.cartas.add(carta);
    }

    public void quitarCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser nula");
        }
        if(obtenerCantidadCartas() == 0)
        {
            throw new Exception("El jugador no tiene cartas");
        }
        if(!ValidacionesUtiles.estaEnLaLista(carta, obtenerCartas()))
        {
            throw new Exception("El jugador no tiene esa carta");
        }
        this.cartas.remove(carta);
    }

    public void asignarCartaActiva(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser nula");
        }
        this.cartaActiva = carta;
    }

    public void limpiarCartaActiva()
    {
        this.cartaActiva = null;
    }
}
