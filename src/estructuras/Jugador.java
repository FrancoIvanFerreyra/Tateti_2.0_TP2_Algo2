package estructuras;

import static org.junit.jupiter.api.Assertions.fail;

import cartas.Carta;
import utiles.ValidacionesUtiles;


public class Jugador {
    //ATRIBUTOS -----------------------------------------------------------------------------------------------
    private int numero;
    private ListaEnlazada<Carta> cartas;
    private Carta cartaActiva;
    private Estado estado;
    private int turnosAPerder;

    
    // CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * Crea una instancia de Jugador con un número específico.
     * pre: El número debe ser mayor a 0.
     * @param numero Número de identificación del jugador.
     * pos: Se crea un jugador con el número especificado, sin cartas y con el estado inicial NORMAL.
     * @throws Exception Si el número es menor o igual a 0.
     */
    public Jugador(int numero) throws Exception
    {
        if(numero <= 0)
        {
            throw new Exception("El numero debe ser mayor a 0");
        }
        this.numero = numero;
        this.cartas = new ListaEnlazada<Carta>();
        this.cartaActiva = null;
        this.estado = new Estado();
        this.turnosAPerder = 0;
    }


    // GETTERS SIMPLES -----------------------------------------------------------------------------------------
    /**
     * Obtiene el número de identificación del jugador.
     * pre: -
     * @return El número del jugador.
     * pos: El número del jugador es devuelto sin modificar el jugador.
     */   
    public int obtenerNumero()
    {
        return this.numero;
    }

    /**
     * Obtiene una copia de la lista de cartas del jugador.
     * pre: -
     * @return Una lista de cartas del jugador.
     * pos: Se retorna una copia de la lista de cartas sin modificar la original.
     */
    public ListaEnlazada<Carta> obtenerCartas()
    {
        ListaEnlazada<Carta> listaCartascopia = new ListaEnlazada<Carta>();

        // Iteramos sobre la lista original y agregamos cada carta a la nueva lista
        for (int i = 1; i <= cartas.getTamanio(); i++) {
            try {
                Carta carta = cartas.obtener(i); // Obtener la carta en la posición i
                listaCartascopia.agregar(carta); // Agregar la carta a la nueva lista
            } catch (Exception e) {
                e.printStackTrace();
                fail("Excepción: " + e.getMessage());
            }
        }
    
        return listaCartascopia;
    }

    /**
     * Obtiene la cantidad de cartas que posee el jugador.
     * pre: -
     * @return Cantidad de cartas del jugador.
     * pos: La cantidad de cartas es devuelta sin modificar el jugador.
     */
    public int obtenerCantidadCartas()
    {
        return this.cartas.getTamanio();
    }

    /**
     * Obtiene la carta activa del jugador.
     * pre: -
     * @return La carta activa del jugador o null si no tiene carta activa.
     * pos: La carta activa es devuelta sin modificar el jugador.
     */
    public Carta obtenerCartaActiva()
    {
        return this.cartaActiva;
    }

    /**
     * Obtiene el estado actual del jugador.
     * pre: -
     * @return El estado del jugador.
     * pos: El estado es devuelto sin modificar el jugador.
     */
    public Estado obtenerEstado()
    {
        return this.estado;
    }

    /**
     * Obtiene la cantidad de turnos que el jugador debe perder.
     * pre: -
     * @return La cantidad de turnos a perder.
     * pos: La cantidad de turnos a perder es devuelta sin modificar el jugador.
     */
    public int obtenerTurnosAPerder()
    {
        return this.turnosAPerder;
    }
    

    // MÉTODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * Agrega una carta al jugador.
     * pre: La carta no debe ser nula.
     * @param carta Carta a agregar.
     * pos: La carta se agrega a la lista de cartas del jugador.
     * @throws Exception Si la carta es nula.
     */
    public void agregarCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser nula");
        }
        this.cartas.agregar(carta);
    }

    /**
     * Quita una carta del jugador.
     * pre: La carta no debe ser nula y el jugador debe poseer la carta.
     * @param carta Carta a quitar.
     * pos: La carta es removida de la lista de cartas del jugador.
     * @throws Exception Si la carta es nula, el jugador no tiene cartas, o no tiene la carta especificada.
     */
    public void quitarCarta(Carta carta) throws Exception
    {
        ValidacionesUtiles.verificarObjetoValido(carta);

        if(obtenerCantidadCartas() == 0)
        {
            throw new Exception("El jugador no tiene cartas");
        }
        if(!ValidacionesUtiles.estaEnLaListaEnlazada(carta, obtenerCartas()))
        {
            throw new Exception("El jugador no tiene esa carta");
        }
        this.cartas.remover(numero);
    }
      

    /**
     * Asigna una carta como la carta activa del jugador.
     * pre: La carta no debe ser nula.
     * @param carta Carta a asignar como activa.
     * pos: La carta activa del jugador se establece con la carta dada.
     * @throws Exception Si la carta es nula.
     */
    public void asignarCartaActiva(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser nula");
        }
        this.cartaActiva = carta;
    }

    /**
     * Limpia la carta activa del jugador, estableciéndola en null.
     * pre: -
     * pos: La carta activa se establece en null.
     */
    public void limpiarCartaActiva()
    {
        this.cartaActiva = null;
    }

    /**
     * Aumenta la cantidad de turnos que el jugador debe perder.
     * pre: La cantidad debe ser mayor a 0 y el total de turnos a perder después de la adición no debe exceder 3.
     * @param cantidad Cantidad de turnos a aumentar.
     * pos: La cantidad de turnos a perder se incrementa en la cantidad dada.
     * @throws Exception Si la cantidad es menor o igual a 0 o el total de turnos a perder supera el límite de 3.
     */
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

    /**
     * Disminuye la cantidad de turnos que el jugador debe perder.
     * pre: La cantidad debe ser mayor a 0 y no debe hacer que los turnos a perder sean negativos.
     * @param cantidad Cantidad de turnos a reducir.
     * pos: La cantidad de turnos a perder se reduce en la cantidad dada.
     * @throws Exception Si la cantidad es menor o igual a 0 o si la resta hace que los turnos a perder sean negativos.
     */
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
