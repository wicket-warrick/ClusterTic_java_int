package otrascolecciones;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

/**
 * @author Francisco Charte
 */
public class OtrasColecciones {
    private final Deque<Integer> turnos = new LinkedList<>();
    private final Set<Character> vocales = new HashSet<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        (new OtrasColecciones())
                .runPilaCola()
                .runConjunto();
    }
    
    /**
     * Ejercicios de uso de una Deque en forma de cola y de pila
     */
    private OtrasColecciones runPilaCola() {
        int[] numeros = (new Random()).ints(15, 1, 50).toArray();
        
        System.out.println("Secuencia de números original");
        for(var n : numeros)
            System.out.print(n + ", ");
        
        // Uso como pila
        for(var n : numeros)
            turnos.push(n); // También se podría usar turnos.addFirst(n);
        
        System.out.println("\n\nOrden en que se extraen de la pila");
        while(!turnos.isEmpty())
            System.out.print(turnos.pop() + ", ");  // También se podría usar turnos.pollFirst()
        
        // Uso como cola
        for(var n : numeros)
            turnos.offer(n);
        
        System.out.println("\n\nOrden en que se extraen de la cola");
        while(!turnos.isEmpty())
            System.out.print(turnos.poll() + ", ");
        
        return this;
    }
    
    /**
     * Ejercicio que muestra el uso de un conjunto
     */
    private OtrasColecciones runConjunto() {
        // Conjunto con las vocales
        vocales.addAll(Arrays.asList('a','e','i','o','u'));
        vocales.addAll(Arrays.asList('A','E','I','O','U'));
        vocales.addAll(Arrays.asList('á','é','í','ó','ú'));
        
        String frase = 
                "Un conjunto puede almacenar elementos no repetidos " +
                "de cualquier tipo por referencia y, con sus métodos " +
                "es posible comprobar la pertenencia de un elemento " +
                "al conjunto, así como iterar por el mismo.";
        
        int numVocales = 0;
        for(var i = 0; i < frase.length(); i++ )
            if(vocales.contains(frase.charAt(i)))
                numVocales++;
        
        System.out.printf("\n\nHay %d caracteres en el texto, %d de ellos son vocales\n",
                frase.length(), numVocales);      
        
        return this;
    }
}
