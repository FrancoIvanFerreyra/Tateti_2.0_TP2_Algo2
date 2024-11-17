package tateti;



import estructuras.Cola;
import estructuras.Lista;
import estructuras.ListaSimplementeEnlazada;


import cartas.*;
import java.util.Random;

public class Mazo {
    private int tamanio;
    private Cola<Carta> cartasDisponibles;
    private Lista<Carta> cartasUtilizadas;

    public Mazo(int tamanio)
    {
        this.tamanio = tamanio;
        this.cartasDisponibles = new Cola<Carta>();
        this.cartasUtilizadas = new ListaSimplementeEnlazada<Carta>();
        Lista<Class<? extends Carta>> tiposDeCarta = obtenerTiposDeCarta();
        Random random = new Random();
        for(int i = 0; i < tamanio; i++)
        {
            int indiceRandom = random.nextInt(tiposDeCarta.getTamanio() - 1) + 1;
            try {
                Carta nuevaCarta = tiposDeCarta.obtener(indiceRandom).getDeclaredConstructor().newInstance(null);
                cartasDisponibles.acolar(nuevaCarta);
            } catch (Exception e) {
            }
        }
    }
    private Lista<Class<? extends Carta>> obtenerTiposDeCarta()
    {
        Lista<Class<? extends Carta>> tiposDeCarta = new ListaSimplementeEnlazada<Class<? extends Carta>>();
        try
        {
            tiposDeCarta.agregar(CartaPierdeTurno.class);
            tiposDeCarta.agregar(CartaDuplicarTurno.class);
            tiposDeCarta.agregar(CartaBloquearFicha.class);
            tiposDeCarta.agregar(CartaAnularCasillero.class);
        }
        catch(Exception e)
        {

        }

        return tiposDeCarta;
    }

    public Carta levantarCarta() throws Exception
    {
        if(cartasDisponibles.contarElementos() <= 0)
        {
            throw new Exception("No hay cartas disponibles en el mazo");
        }
        return this.cartasDisponibles.desacolar();
    }

    public void devolverCarta(Carta carta) throws Exception
    {
        if(carta == null)
        {
            throw new Exception("La carta no puede ser null");
        }
        this.cartasDisponibles.acolar(carta);
    }

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
            int indiceMazo1 = random.nextInt(this.tamanio - 1) + 1;
            int indiceMazo2 = 0;
            do
            {
                indiceMazo2 = random.nextInt(this.tamanio - 1) + 1;
            } while(indiceMazo2 == indiceMazo1);
    
            cartasUtilizadas.intercambiar(indiceMazo1, indiceMazo2);
        }
        cartasDisponibles.acolar(cartasUtilizadas);


    }
    public boolean hayCartasDisponibles()
    {
        return this.cartasDisponibles.contarElementos() > 0;
    }
}
