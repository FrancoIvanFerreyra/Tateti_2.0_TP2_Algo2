package pruebas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import estructuras.Vector;

public class PruebaDeVector{
    
    @Test
    public void pruebaDeVector() throws Exception {
        //Pruebo el constructor del vector con largo negativo
        Assertions.assertThrows(Exception.class, () -> {
            new Vector(-1, null);
        }, "El constructor no devolvió error");

        //Creo el vector
        Vector<String> vector = new Vector(7, "vacio");

        //Pruebo agregar un dato al vector en un indice inexistente
        Assertions.assertThrows(Exception.class, () -> {
            vector.agregar(25, "IndiceMayor");
        }, "El vector acepto un indice mayor a su tamaño");

        //Pruebo agregar un dato nulo al vector
        Assertions.assertDoesNotThrow(() -> {
            vector.agregar(1, null);
        }, "El vector no acepto un dato nulo");
        Assertions.assertEquals(2, vector.agregar("¡"), "El string '¡' no se pudo agregar al indice vacio 1");

        //Cargo datos al vector
        vector.agregar("Hola");
        vector.agregar("Mundo");
        vector.agregar("Como");
        vector.agregar("Estan");

        //Pruebo obtener el string 'Mundo' en el indice 4
        Assertions.assertEquals("Mundo", vector.obtener(4), "El string 'Mundo' no esta en el indice 4 del vector");
        
        //Pruebo obtener el dato por defecto en un indice al que no le cargue datos
        Assertions.assertEquals("vacio", vector.obtener(7), "El string por defecto 'vacio' no esta en el indice 7 del vector");
        
        //Pruebo obtener un dato de un indice inexistente
        Assertions.assertThrows(Exception.class, () -> {
            vector.obtener(15);
        }, "El vector devolvio un dato en un indice mayor a su tamaño");

        //Pruebo quitar un dato de un indice inexistente
        Assertions.assertThrows(Exception.class, () -> {
            vector.remover(20);
        }, "El vector removio un dato en un indice mayor a su tamaño");

        //Pruebo quitar un dato de un indice existente
        Assertions.assertDoesNotThrow(() -> {
            vector.remover(3);
        }, "El vector lanzo excepcion al intentar eliminar el tercer elemento");

        //Pruebo si se cargo un dato en el primer indice sin dato (3)
        Assertions.assertEquals(3, vector.agregar("Chau"), "El vector no guardo el string 'Chau' en el supuesto indice vacio 3");
                
        
        //Pruebo que el vector mantenga su tamaño al llenarlo de datos
        vector.agregar("hoy");
        Assertions.assertEquals(7, vector.getLongitud(), "El vector aumento de tamaño inesperadamente");
        
        //Pruebo que el vector haya aumentado su tamaño para albergar mas datos
        vector.agregar("?");
        Assertions.assertEquals(14, vector.getLongitud(), "El vector no aumento de tamaño como se esperaba");
    }



}
