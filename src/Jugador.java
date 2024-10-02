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

    public int obtenerTurnosAPerder()
    {
        return this.turnosAPerder;
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

    public void agregarTurnosAPerder(int cantidad) throws Exception
    {
        if(cantidad <= 0)
        {
            throw new Exception("La cantidad de turnos a perder debe ser mayor a 0");
        }
        if(ValidacionesUtiles.esMayorQue(obtenerTurnosAPerder() + cantidad, 3))
        {
            throw new Exception("El jugador " + obtenerNumero() + " no puede perder mas turnos. El maximo es 3");
        }
        this.turnosAPerder += cantidad;
    }

    public void quitarTurnosAPerder(int cantidad) throws Exception
    {
        if(cantidad <= 0)
        {
            throw new Exception("La cantidad de turnos a perder debe ser mayor a 0");
        }
        if(ValidacionesUtiles.esMenorQue(obtenerTurnosAPerder() - cantidad, 0))
        {
            throw new Exception("El jugador " + obtenerNumero() + " solo puede recuperar hasta " + obtenerTurnosAPerder() + "turnos");
        }
        this.turnosAPerder -= cantidad;
    }
}
