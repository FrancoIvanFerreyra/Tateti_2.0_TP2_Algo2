package cartas;
import jugadas.Jugada;
import jugadas.JugadaTeletransportarFicha;

public class CartaTeletransportarFicha extends Carta{
    public CartaTeletransportarFicha(){}

    @Override
    protected String getTituloPorDefecto() {
        return "Teletransportar una ficha";
    }

    @Override
    public Jugada getJugada() {
        return new JugadaTeletransportarFicha(this);
    }

}
