package tateti;



import estructuras.Cola;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;


import cartas.*;
import interfaz.Consola;
import java.util.Random;

public class Mazo {
    private int tamanio;
    private Cola<Carta> cartasDisponibles;
    private Lista<Carta> cartasUtilizadas;

    /**
     * Crea un mazo de cartas con un tamaño específico.
     * pre: tamanio > 0.
     * pos: El mazo se inicializa con cartas aleatorias disponibles.
     * @param tamanio La cantidad inicial de cartas en el mazo.
     */
    public Mazo(int tamanio) throws Exception {
        if (tamanio > 0) {
            throw new Exception("El tamanio del mazo debe ser mayor a cero"); 
        }
        this.tamanio = tamanio;
        this.cartasDisponibles = new Cola<Carta>();
        this.cartasUtilizadas = new ListaSimplementeEnlazada<Carta>();
        Lista<Class<? extends Carta>> tiposDeCarta = obtenerTiposDeCarta();
        Random random = new Random();
        for(int i = 0; i < tamanio; i++)
        {
            int indiceRandom = random.nextInt(tiposDeCarta.getTamanio()) + 1;
            try {
                Carta nuevaCarta = tiposDeCarta.obtener(indiceRandom).getDeclaredConstructor().newInstance(null);
                cartasDisponibles.acolar(nuevaCarta);
            } catch (Exception e) {
                throw new Exception("Error al crear el mazo");
            }
        }
    }

    /**
     * Obtiene los tipos de cartas disponibles para el mazo.
     * pre: -
     * pos: Devuelve una lista con las clases de cartas que se pueden usar.
     * @return Una lista de clases que extienden de Carta
     */
    private Lista<Class<? extends Carta>> obtenerTiposDeCarta()
    {
        Lista<Class<? extends Carta>> tiposDeCarta = new ListaSimplementeEnlazada<Class<? extends Carta>>();

        tiposDeCarta.agregar(CartaPierdeTurno.class);
        tiposDeCarta.agregar(CartaDuplicarTurno.class);
        tiposDeCarta.agregar(CartaBloquearFicha.class);
        tiposDeCarta.agregar(CartaAnularCasillero.class);
        tiposDeCarta.agregar(CartaRetrocederUnaJugada.class);
        tiposDeCarta.agregar(CartaTeletransportarFicha.class);
        tiposDeCarta.agregar(CartaCambiarColorFicha.class);

        return tiposDeCarta;
    }

    /**
     * Levanta una carta del mazo.
     * pre: hayCartasDisponibles().
     * pos: Devuelve una carta y la elimina de las cartas disponibles.
     * @return La carta levantada.
     * @throws Exception Si no hay cartas disponibles en el mazo.
     */
    public Carta levantarCarta() throws Exception
    {
        if(!hayCartasDisponibles())
        {
            throw new Exception("No hay cartas disponibles en el mazo");
        }
        return this.cartasDisponibles.desacolar();
    }

    /**
     * Devuelve una carta al mazo, añadiéndola a las cartas disponibles.
     * pre: carta != null.
     * pos: La carta se agrega al final de las cartas disponibles.
     * @param carta La carta que se desea devolver al mazo.
     * @throws Exception Si la carta es null.
     */
    public void devolverCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser null");
        }
        this.cartasDisponibles.acolar(carta);
    }

    /**
     * Descarta una carta, moviéndola a la lista de cartas utilizadas.
     * pre: carta != null.
     * pos: La carta se agrega a la lista de cartas utilizadas.
     * @param carta La carta que se desea descartar.
     * @throws Exception Si la carta es null.
     */
    public void descartarCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser null");
        }
        this.cartasUtilizadas.agregar(carta);
    }

    /**
     * Mezcla las cartas utilizadas y las agrega nuevamente al mazo de cartas disponibles.
     * pre: !hayCartasDisponibles().
     * pos: Las cartas utilizadas se mezclan aleatoriamente y se añaden al mazo.
     * @throws Exception Si aún hay cartas disponibles en el mazo.
     */
    public void mezclar() throws Exception
    {
        if(hayCartasDisponibles())
        {
            throw new Exception("No se puede mezclar si aun hay cartas disponibles");
        }
        int cantidadDeIntercambios = 100;
        Random random = new Random();

        for(int i = 0; i < cantidadDeIntercambios; i++)
        {
            int indiceMazo1 = random.nextInt(this.cartasUtilizadas.getTamanio()) + 1;
            int indiceMazo2 = 0;
            do
            {
                indiceMazo2 = random.nextInt(this.cartasUtilizadas.getTamanio()) + 1;
            } while(indiceMazo2 == indiceMazo1);
    
            cartasUtilizadas.intercambiar(indiceMazo1, indiceMazo2);
        }
        cartasDisponibles.acolar(cartasUtilizadas);

    /**
     * Verifica si hay cartas disponibles en el mazo.
     * pre: -
     * pos: 
     * @return true si hay cartas disponibles; false si no hay.
     */
    }
    public boolean hayCartasDisponibles()
    {
        return this.cartasDisponibles.contarElementos() > 0;
    }
}
