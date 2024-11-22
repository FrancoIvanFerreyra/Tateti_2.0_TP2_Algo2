package utiles;

import java.io.File;
import java.io.IOException;

public class AdministradorDeArchivos {

        /**
     * Crea un directorio con el nombre especificado dentro de la ruta padre.
     * Si el directorio ya existe, simplemente lo retorna.
     * @param rutaPadre no puede ser null
     * @param nombreDirectorio no puede ser null
     * @return el objeto File que representa el directorio creado
     * @throws NullPointerException si rutaPadre o nombreDirectorio son null
     * @throws SecurityException si no se tienen permisos para crear el directorio
     * @throws IllegalStateException si no se pudo crear el directorio por otras razones
     */
    public static File crearDirectorio(String rutaPadre, String nombreDirectorio)
                                            throws NullPointerException,
                                            SecurityException,
                                            IllegalStateException{
        ValidacionesUtiles.validarNoNull(rutaPadre, "rutaPadre");
        ValidacionesUtiles.validarNoNull(nombreDirectorio, "nombreDirectorio");

        File directorio = new File(rutaPadre, nombreDirectorio);

        if (!directorio.exists()) {
            boolean creado = directorio.mkdirs();
            if (!creado) {
                throw new IllegalStateException("No se pudo crear el directorio: " + directorio.getPath());
            }
        }
    
    return directorio;
}


    /**
     * Crea un directorio con el nombre especificado dentro de un archivo padre.
     * Si el directorio ya existe, simplemente lo retorna.
     * @param archivoPadre no puede ser null, debe ser un directorio válido
     * @param nombreDirectorio no puede ser null
     * @return el objeto File que representa el directorio creado
     * @throws NullPointerException si archivoPadre o nombreDirectorio son null
     * @throws IllegalArgumentException si archivoPadre no es un directorio válido
     * @throws SecurityException si no se tienen permisos para crear el directorio
     * @throws IllegalStateException si no se pudo crear el directorio por otras razones
     */
    public static File crearDirectorio(File archivoPadre, String nombreDirectorio)
                                    throws NullPointerException,
                                    IllegalArgumentException,
                                    SecurityException,
                                    IllegalStateException {
        ValidacionesUtiles.validarNoNull(archivoPadre, "archivoPadre");
        ValidacionesUtiles.validarNoNull(nombreDirectorio, "nombreDirectorio");


        if (!archivoPadre.isDirectory()) {
            throw new IllegalArgumentException("archivoPadre debe ser un directorio válido: " + archivoPadre.getPath());
        }

        File directorio = new File(archivoPadre, nombreDirectorio);

        if (!directorio.exists()) {
            boolean creado = directorio.mkdirs();
            if (!creado) {
                throw new IllegalStateException("No se pudo crear el directorio: " + directorio.getPath());
            }
        }

        return directorio;
    }


    /**
     * Vacía el contenido de un directorio, eliminando todos los archivos y subdirectorios en 
     * su interior.
     * @param directorio no puede ser null, debe ser un directorio existente
     * @throws NullPointerException si directorio es null
     * @throws IllegalArgumentException si directorio no es un directorio válido
     * @throws IOException si no se pudo eliminar algún archivo o subdirectorio
     * @throws SecurityException si no se tienen permisos para acceder o modificar los
     *                           archivos/directorios
     */
    public static void vaciarDirectorio(File directorio) throws NullPointerException,
                                                        IllegalArgumentException,
                                                        IOException,
                                                        SecurityException {
        ValidacionesUtiles.validarNoNull(directorio, "directorio");

        if (!directorio.exists()) {
            return; // No hay nada que eliminar si no existe
        }

        if (!directorio.isDirectory()) {
            throw new IllegalArgumentException("El objeto especificado no es un directorio: " + directorio.getAbsolutePath());
        }

        File[] archivos = directorio.listFiles();
        if (archivos == null) {
            throw new IOException("No se pudo listar el contenido del directorio: " + directorio.getAbsolutePath());
        }

        for (File archivo : archivos) {
            if (archivo.isDirectory()) {
                vaciarDirectorio(archivo); // Eliminar contenido recursivamente
            }
            if (!archivo.delete()) {
                throw new IOException("No se pudo eliminar el archivo o directorio: " + archivo.getAbsolutePath());
            }
        }
    }

}