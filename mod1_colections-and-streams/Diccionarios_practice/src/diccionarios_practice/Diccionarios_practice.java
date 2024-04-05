/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package diccionarios_practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.IntStream;

/**
 *
 * @author hugo.suarez
 */
public class Diccionarios_practice {
    private final Map<String, String>[] paises = new Map[3];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        (new Diccionarios_practice()).run();
    }
    
    /**
     * Ejercicio de uso de diccionarios
     */
    private void run() {
        inicializaDiccionarios();
        
        muestraValores(paises);
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
    
    
    public void muestraValores(Map<String,String>[] diccionarios){
        
        for(int i=0;i<diccionarios.length;i++){
            /**
             * Obteño os valores de cada dicciionario/map e gardoos, nunha
             * colleción
             */
        Collection<String> capital=paises[i].values();
        
        

        System.out.printf("\nValores de diccionarios %d\n",i);
        /**
         * Recorro a colección pertencente a cada diccionario e saco por pantalla,
         * cada un dos valores almacenados
         */
        capital.forEach(System.out::println);
    }
    }
     /** 
     * Muestra las claves contenidas en cada uno de los diccionarios
     */
    private void muestraClaves() {
        for(int i : Arrays.asList(0, 1, 2)) {
            Set<String> pais = paises[i].keySet();

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
