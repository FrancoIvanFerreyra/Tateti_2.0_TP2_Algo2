package cartas;
import jugadas.Jugada;
import jugadas.JugadaTeletransportarFicha;

public class CartaTeletransportarFicha extends Carta{
    /**
     * pre: -
     * pos: se crea una carta con un id unico y con el titulo "Teletransportar una ficha"
     */
    public CartaTeletransportarFicha(){}
    /**
     * pre: -
     * pos: Retorna el título por defecto de la carta "Teletransportar una ficha"
     */
    @Override
    protected String getTituloPorDefecto() {
        return "Teletransportar una ficha";
    }
	/**
     * pre: -
     * pos: retonra la jugada de la carta
     */
    @Override
    public Jugada getJugada() {
        return new JugadaTeletransportarFicha(this);
    }

}
