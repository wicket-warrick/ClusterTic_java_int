package streamslambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Francisco Charte
 */
public class StreamsLambda {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        (new StreamsLambda())
                .runLambdas()
                .runStreamBasico()
                .runGeneraStream()
                .runContarVocales();
    }

    /**
     * Ejercicios de definición y uso de expresiones lambda
     */
    @FunctionalInterface
    interface Diferencia<T> {

        T calcula(T dato1, T dato2);
    }

    private StreamsLambda runLambdas() {
        // Clase que implementa la interfaz Diferencia para enteros
        class DifEnteros implements Diferencia<Integer> {

            @Override
            public Integer calcula(Integer dato1, Integer dato2) {
                return dato1 - dato2;
            }
        }

        // Una instancia de la clase anterior para calcular diferencias
        DifEnteros dif1 = new DifEnteros();
        System.out.println(dif1.calcula(5, 2));

        // Instancia de objeto que implementa Diferencia sin definir clase
        Diferencia<Integer> dif2 = new Diferencia<Integer>() {
            @Override
            public Integer calcula(Integer dato1, Integer dato2) {
                return dato1 - dato2;
            }
        };
        System.out.println(dif2.calcula(5, 2));

        // Definición de objetos mediante expresiones lambda
        Diferencia<Integer> dint = (d1, d2) -> d1 - d2;
        Diferencia<String> dstr = (s1, s2) -> s1.replace(s2, "");

        System.out.println(dint.calcula(5, 2));
        System.out.println(dstr.calcula("la luz atravesó la ventana", "la "));

        // Implementación de interfaces funcionales estándar - java.util.function.Consumer
        Consumer<Integer> muestraCaracter = c -> System.out.printf("%c - ", c);
        Consumer<Integer> muestraCodigo = c -> System.out.printf("%d\n", c);
        Consumer<Integer> muestraAmbos = muestraCaracter.andThen(muestraCodigo);
        Stream.of(34, 21, 43, 15, 78, 54).forEach(muestraAmbos);

        return this;
    }

    private StreamsLambda runStreamBasico() {
        // Filtrado 
        IntStream rndStream = new Random().ints(100, 1, 50);
        rndStream.
                filter(n -> n >= 45).
                forEach(System.out::println);

        // Transformación
        (new Random()).ints(100, 1, 50).
                map(x -> x * x).
                filter(x -> x < 50).
                forEach(System.out::println);

        "Hola Streams en Java\n".chars().
                map(c -> Character.toUpperCase(c)).
                forEach(c -> System.out.printf("%c", c));

        // Ordenación
        Stream.of("Lunes", "Martes", "Miércoles", "Jueves",
                "Viernes", "Sábado", "Domingo").
                sorted((x, y) -> x.length() - y.length()).
                forEach(System.out::println);

        // Reduce
        Double suma = Stream.of(1, 5, 2, 3, 4, 3, 5).
                map(Math::sqrt).
                reduce(0.0, (x, y) -> x + y);

        // Collect
        List<String> nbin = Stream.of(1, 5, 2, 3, 4, 3, 5).
                map(x -> x * x).
                filter(x -> x < 50).
                map(x -> Integer.toBinaryString(x)).
                limit(5).
                distinct().
                collect(Collectors.toList());

        return this;
    }

    private StreamsLambda runGeneraStream() throws IOException {
        // Generación de stream desde una cadena convertida en array 
        Arrays.stream("lun,mar,mie,jue,vie,sab,dom".split(",")).
                forEach(System.out::println);

        Arrays.stream("lun,mar,mie,jue,vie,sab,dom".split(",")).
                map(s -> s.chars().summaryStatistics().toString()).
                forEach(System.out::println);

        // Generación de stream finito 
        IntStream.rangeClosed(1, 52).
                mapToObj(semana -> "Semana " + semana).
                limit(5).
                forEach(System.out::println);
        IntStream.rangeClosed(1, 52).count();

        // Generación de streams infinitos
        IntStream.iterate(1, x -> x + 1).
                filter(x -> x % 3 == 0).
                limit(1000).
                sum();

        Stream.generate(() -> "a")
                .limit(30)
                .collect(Collectors.joining());
        
        Stream.generate(() -> System.nanoTime())
                .limit(10)
                .map(l -> String.format("%,d", l))
                .forEach(System.out::println);

        // Cálculos sobre streams numéricos
        Random rnd = new Random();
        double media = DoubleStream.generate(
                () -> rnd.nextGaussian())
                .limit(1000)
                .sum() / 1000;
        System.out.printf("La media de 1000 aleatorios: %f\n", media);

        // Streams devueltos por servicios del sistema
        Files.find(Paths.get("C:/FCharte"), 2,
                (ruta, attr) -> ruta.toString().endsWith(".bib")).
                sorted().
                forEach(ruta -> {
                    try {
                        System.out.printf("%s\t%d bytes\n",
                                ruta.getFileName(), Files.size(ruta));
                    } catch (IOException ex) {

                    }
                });

        return this;
    }
    
    private void runContarVocales() {
        Set<Character> vocales = new HashSet<>();
        vocales.addAll(Arrays.asList('a','e','i','o','u'));
        vocales.addAll(Arrays.asList('A','E','I','O','U'));
        vocales.addAll(Arrays.asList('á','é','í','ó','ú'));
        
        String frase = 
                "Un conjunto puede almacenar elementos no repetidos " +
                "de cualquier tipo por referencia y, con sus métodos " +
                "es posible comprobar la pertenencia de un elemento " +
                "al conjunto, así como iterar por el mismo.";
        
        System.out.printf("\n\nHay %d caracteres en el texto, %d de ellos son vocales\n",
                frase.length(),
                frase.chars()
                        .mapToObj(c -> (char )c)
                        .filter(c -> vocales.contains(c))
                        .count());
    }
}
