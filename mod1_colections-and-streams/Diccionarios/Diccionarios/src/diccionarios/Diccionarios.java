package diccionarios;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 * @author Francisco Charte
 */
public class Diccionarios {
    /** Tres diccionarios con distintas implementaciones */
    private final Map<String, String>[] paises = new Map[3];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        (new Diccionarios()).run();
    }
    
    /**
     * Ejercicio de uso de diccionarios
     */
    private void run() {
        inicializaDiccionarios();
        
        muestraValores();
        muestraClaves();
        muestraEntradas();       
    }
    
    /**
     * Inicializa los tres diccionarios con el mismo conjunto de entradas
     */
    private void inicializaDiccionarios() {
        paises[0] = new HashMap<>();
        paises[1] = new LinkedHashMap<>();
        paises[2] = new TreeMap<>();
     
        paises[0].put("Portugal", "Lisboa");
        paises[0].put("Francia", "Paris");
        paises[0].put("España", "Madrid");
        paises[0].put("Albania", "Tirana");
        paises[0].put("Italia", "Roma");

        paises[1].putAll(paises[0]);
        paises[2].putAll(paises[0]);        
    }

    /**
     * Muestra los valores contenidos en cada uno de los diccionarios
     */
    private void muestraValores() {
        for(var i = 0; i <= 2; i++)  {   
            var capital = paises[i].values();

            System.out.printf("\nValores del diccionario %d\n", i);
            capital.forEach(System.out::println);
        }        
    }
    
    /** 
     * Muestra las claves contenidas en cada uno de los diccionarios
     */
    private void muestraClaves() {
        for(var i : Arrays.asList(0, 1, 2)) {
            var pais = paises[i].keySet();

            System.out.printf("\nClaves del diccionario %d\n", i);
            pais.forEach(System.out::println);
        }        
    }
    
    /**
     * Muestras las entradas de cada uno de los diccionarios
     */
    private void muestraEntradas() {
        for(var i : IntStream.rangeClosed(0, 2).toArray()) {
            System.out.printf("\nEntradas del diccionario %d\n", i);
            paises[i].forEach((x, y) -> 
                    System.out.format("La capital de %s es %s\n", x, y));
        }
        
    }
}
