package interfaces;


public interface Bloqueable {
    void incrementarBloqueosRestantes(int cantidadDeBloqueos) throws Exception;

    void reducirBloqueosRestantes(int cantidadDeBloqueos) throws Exception;

    boolean estaBloqueado();

    int getBloqueosRestantes();
}

