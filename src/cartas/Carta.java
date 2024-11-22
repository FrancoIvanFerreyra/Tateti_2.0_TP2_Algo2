package cartas;

import java.util.Objects;
import jugadas.Jugada;

public abstract class Carta {
    // ATRIBUTOS DE CLASE --------------------------------------------------------------------------------------
    private static Long IdActual = 1L;

    // ATRIBUTOS -----------------------------------------------------------------------------------------------
    private Long id = null; // 1 2 3 4 5 6 7 8 9 .... 9223372036854775807
    private String titulo = null;

    // CONSTRUCTORES -------------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Se inicializa la carta con un título por defecto y un ID único
     */
    protected Carta() {
        this.titulo = getTituloPorDefecto();
        this.id = Carta.getIdActual();
    }

    /**
     * pre: `titulo` no debe ser nulo.
     * pos: Se inicializa la instancia con el título especificado y un ID único.
     */
    protected Carta(String titulo) {
        this.titulo = titulo;
        this.id = Carta.getIdActual();
    }

    // METODOS DE CLASE ----------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Devuelve un ID único y aumenta el valor estático `IdActual`.
     */
    private static Long getIdActual() {
        return Carta.IdActual++;
    }

    // METODOS GENERALES ---------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna una cadena en el formato "título (ID)".
     */
    @Override
    public String toString() {
        return this.getTitulo() + " (" + this.id + ")";
    }

    /**
     * pre: -
     * pos: Retorna un valor hash consistente para la carta.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * pre: -
     * pos: Retorna `true` si el objeto comparado es una instancia de `Carta` con el mismo ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Carta other = (Carta) obj;
        return Objects.equals(id, other.id);
    }

    // METODOS DE COMPORTAMIENTO -------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: Retorna el String del titulo por defecto de la carta
     */
    protected abstract String getTituloPorDefecto();

    /**
     * pre: -
     * pos: retorna la jugada asociada a la carta
     */
    public abstract Jugada getJugada();

    // GETTERS SIMPLES -----------------------------------------------------------------------------------------
    /**
     * pre: -
     * pos: retonra el titlo de la carta
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * pre: -
     * pos: retorna el id de la carta
     */
    public Long getId() {
        return this.id;
    }

    // SETTERS SIMPLES -----------------------------------------------------------------------------------------
}
