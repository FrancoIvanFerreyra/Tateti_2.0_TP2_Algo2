package tateti;



import cartas.*;
import estructuras.Cola;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;
import interfaz.Consola;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import utiles.Utiles;
import utiles.ValidacionesUtiles;

public class Mazo {
    private int tamanio;
    private Cola<Carta> cartasDisponibles;
    private Lista<Carta> cartasUtilizadas;

    /**
     * Crea un mazo de cartas de tamanio maximo tamanio, donde cada carta es instanciada
     * al azar de entre todos los tipos de cartas existentes y guardada en el mazo. Tambien
     * crea una lista de cartas utilizadas que se utilizara para descartar las cartas que se
     * hayan usado
     * @param tamanio mayor o igual a 1
     * @throws IllegalArgumentException si tamanio es menor a 1
     */
    public Mazo(int tamanio) throws IllegalArgumentException
    {
        this.tamanio = tamanio;
        this.cartasDisponibles = new Cola<>();
        this.cartasUtilizadas = new ListaSimplementeEnlazada<>();
        Lista<Class<? extends Carta>> tiposDeCarta = obtenerTiposDeCarta();

        for(int i = 0; i < this.tamanio; i++)
        {
            int indiceRandom = Utiles.obtenerEnteroAleatorio(1, tiposDeCarta.getTamanio());
            try {
                Carta nuevaCarta = tiposDeCarta.obtener(indiceRandom).
                                    getDeclaredConstructor().
                                    newInstance();
                cartasDisponibles.acolar(nuevaCarta);
            } catch (IllegalAccessException | IllegalArgumentException | 
                    InstantiationException | NoSuchMethodException | 
                    NullPointerException | SecurityException | 
                    InvocationTargetException e) {
                Consola.imprimirMensajeConSalto("No se pudo crear la carta para el mazo" + e.getMessage());
            }
        }
    }

    /**
     * 
     * @return devuelve una lista con todos los tipos de cartas existentes
     */
    private Lista<Class<? extends Carta>> obtenerTiposDeCarta()
    {
        Lista<Class<? extends Carta>> tiposDeCarta = new ListaSimplementeEnlazada<>();
        
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
     * 
     * @return levanta una carta del frente del mazo de cartas disponibles
     * @throws NoSuchElementException si no hay cartas disponibles
     */
    public Carta levantarCarta() throws NoSuchElementException
    {
        if(!hayCartasDisponibles())
        {
            throw new NoSuchElementException("No hay cartas disponibles en el mazo");
        }
        return this.cartasDisponibles.desacolar();
    }

    /**
     * Retorna una carta al fondo del mazo de cartas disponibles
     * @param carta no debe ser null
     * @throws NullPointerException si carta es null
     * @throws IllegalStateException si el mazo ya tiene el maximo de cartas disponibles
     */
    public void devolverCarta(Carta carta) throws NullPointerException, IllegalStateException
    {
        ValidacionesUtiles.validarNoNull(carta, "carta");
        if(estaCompleto())
        {
            throw new IllegalStateException("El mazo ya tiene el maximo de cartas");
        }
        this.cartasDisponibles.acolar(carta);
    }

    /**
     * Arroja la carta a la lista de cartas utilizadas
     * @param carta no puede ser null
     * @throws NullPointerException si carta es null
     * @throws IllegalStateException si ya se descarto todas las cartas del mazo
     */
    public void descartarCarta(Carta carta) throws NullPointerException, IllegalStateException
    {
        ValidacionesUtiles.validarNoNull(carta, "carta");
        if(this.cartasUtilizadas.getTamanio() == this.tamanio)
        {
            throw new IllegalStateException("Ya se descartaron todas las cartas del mazo");
        }
        this.cartasUtilizadas.agregar(carta);
    }

    /**
     * Toma las cartas utilizadas, las intercambia de indices al azar y las
     * guarda en el mazo de cartas disponibles. No debe haber cartas disponibles
     * para que el mazo se pueda mezclar
     * @throws IllegalStateException si el mazo aun tiene cartas disponibles
     */
    public void mezclar() throws IllegalStateException
    {
        if(hayCartasDisponibles())
        {
            throw new IllegalStateException("No se puede mezclar si aun hay cartas disponibles");
        }
        int cantidadDeIntercambios = 100;

        for(int i = 0; i < cantidadDeIntercambios; i++)
        {
            int indiceMazo1 = Utiles.obtenerEnteroAleatorio(1,
                                                         this.cartasUtilizadas.getTamanio());
            int indiceMazo2 = 0;
            do
            {
                indiceMazo2 = Utiles.obtenerEnteroAleatorio(1,
                                                         this.cartasUtilizadas.getTamanio());
            } while(indiceMazo2 == indiceMazo1);
    
            cartasUtilizadas.intercambiar(indiceMazo1, indiceMazo2);
        }
        cartasDisponibles.acolar(cartasUtilizadas);
        cartasUtilizadas.vaciar();


    }
    /**
     * @return devuelve verdadero si hay cartas en el mazo de cartas disponibles
     */
    public boolean hayCartasDisponibles()
    {
        return this.cartasDisponibles.contarElementos() > 0;
    }

    /**
     * 
     * @return devuelve verdadero si el mazo tiene todas las cartas disponibles
     */
    public boolean estaCompleto()
    {
        return this.cartasDisponibles.contarElementos() == this.tamanio;
    }

    /**
     * 
     * @return devuelve el tamaño maximo del mazo (la cantidad total de cartas)
     */
    public int getTamanio(){
        return this.tamanio;
    }
}
