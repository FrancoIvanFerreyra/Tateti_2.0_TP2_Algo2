package utiles;

import java.io.File;
import java.io.IOException;

public class AdministradorDeArchivos {

    public static File crearDirectorio(String rutaPadre, String nombreDirectorio)
    {
        // Crear la carpeta base para almacenar los turnos
        File directorio = new File(rutaPadre, nombreDirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        return directorio;
    }

    public static File crearDirectorio(File archivoPadre, String nombreDirectorio)
    {
        // Crear la carpeta base para almacenar los turnos
        File directorio = new File(archivoPadre, nombreDirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        return directorio;
    }

    public static void vaciarDirectorio(File directorio) throws IOException {
    if (!directorio.exists()) {
        return; // Si el directorio no existe, no hay nada que eliminar
    }
    for (File archivo : directorio.listFiles()) {
        if (archivo.isDirectory()) {
            vaciarDirectorio(archivo); // Eliminar contenido del subdirectorio
        }
        if (!archivo.delete()) {
            throw new IOException("No se pudo eliminar el archivo o directorio: " + archivo.getAbsolutePath());
        }
    }
}

}
