package listas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author Francisco Charte
 */
public class Listas {

    /**
     * Listas con anotaciones de tareas
     */
    private ArrayList<Tarea> trabajo = new ArrayList<>();
    private LinkedList<Tarea> personal = new LinkedList<>();

    /**
     * Enumeración con los días de la semana
     */
    public enum Dia {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    };

    /**
     * Clase para gestionar una tarea a realizar
     */
    public class Tarea {

        private Dia dia; /** Día en que se ha de realizar la tarea */
        private LocalTime hora; /** Hora de la tarea */
        private String tarea; /** Descripción de la tarea */
        private boolean completada; /** Indica si la tarea está completada */

        /**
         * El constructor establece un día y hora por defecto para la
         * realización de la tarea
         *
         * @param tarea Descripción de la tarea a realizar
         */
        public Tarea(String tarea) {
            this(tarea, Dia.LUNES, LocalTime.of(9, 0));
        }

        /**
         * El constructor establece los distintos parámetros de la tarea
         * asumiendo que está por completar
         *
         * @param tarea Descripción de la tarea a realizar
         * @param dia Día de la semana próxima para la tarea
         * @param hora Hora a la que se realizará la tarea
         */
        public Tarea(String tarea, Dia dia, LocalTime hora) {
            this.tarea = tarea;
            this.dia = dia;
            this.hora = hora;
            this.completada = false;
        }

        @Override
        public String toString() {
            return "Tarea{" + "dia=" + dia + ", hora=" + hora
                    + ", tarea=" + tarea + ", completada=" + completada + '}';
        }

        /* Getters y Setters para los datos de la tarea */
        public boolean isCompletada() {
            return completada;
        }

        public void setCompletada(boolean completada) {
            this.completada = completada;
        }

        public Dia getDia() {
            return dia;
        }

        public void setDia(Dia dia) {
            this.dia = dia;
        }

        public LocalTime getHora() {
            return hora;
        }

        public void setHora(LocalTime hora) {
            this.hora = hora;
        }

        public String getTarea() {
            return tarea;
        }

        public void setTarea(String tarea) {
            this.tarea = tarea;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            (new Listas()).run();
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }

    /**
     * Ejercicio de uso de listas basadas en arrays y doblemente enlazadas
     */
    private void run() throws IOException {
        System.out.printf("Tamaño inicial: %d y %d elementos\n",
                trabajo.size(), personal.size());

        System.out.println("Tras incializar las listas de tareas\n");
        inicializaTareas();
        muestraTareas();

        System.out.println("Tras ordenar las tareas por horas\n");
        ordenaTareas();
        muestraTareas();

        System.out.println("Tras eliminar las tareas completadas\n");
        eliminaCompletadas();
        muestraTareas();

        listaArray();
        listaDesdeArchivo();
    }

    /**
     * Inicializa las listas de tareas con un conjunto inicial de objetos
     */
    private void inicializaTareas() {
        Tarea[] tareas = {
            new Tarea("Revisión equipos contabilidad"),
            new Tarea("Cierre ejercicio")
        };
        
        trabajo.addAll(Arrays.asList(tareas));
        
        trabajo.add(new Tarea("Informe económico fin de mes"));
        trabajo.add(new Tarea("Reunión equipo auditor", Dia.MARTES, LocalTime.of(17, 10)));
        trabajo.add(new Tarea("Presentación nuevo plan", Dia.LUNES, LocalTime.of(11, 30)));

        personal.add(new Tarea("Comprar regalo a mamá", Dia.SABADO, LocalTime.of(20, 0)));
        personal.add(new Tarea("Visita al planetario con los niños"));
        personal.add(new Tarea("Planificar viaje vacaciones"));

        trabajo.get(0).setDia(Dia.VIERNES);
        trabajo.get(2).setCompletada(true);

        personal.get(1).setDia(Dia.MIERCOLES);
        personal.get(1).setHora(LocalTime.of(18, 30));
        personal.get(2).setHora(LocalTime.NOON);
        personal.get(0).setCompletada(true);

        System.out.printf("Tamaño tras agregar tres tareas: %d y %d elementos\n",
                trabajo.size(), personal.size());
    }

    /**
     * Elimina las tareas ya completadas
     */
    private void eliminaCompletadas() {
        trabajo.removeIf(new Predicate<Tarea>() {
            @Override
            public boolean test(Tarea t) {
                return t.completada;
            }
        });

        personal.removeIf((Tarea t) -> t.completada);
    }

    /**
     * Ordena las tareas por hora
     */
    private void ordenaTareas() {
        trabajo.sort(new Comparator<Tarea>() {
            @Override
            public int compare(Tarea o1, Tarea o2) {
                return o1.hora.compareTo(o2.hora);
            }
        });

        personal.sort((Tarea o1, Tarea o2) -> o1.hora.compareTo(o2.hora));
    }

    /**
     * Muestra las tareas alojadas en las listas
     */
    private void muestraTareas() {
        System.out.println("TAREAS DE TRABAJO\n--------------------");
        for (var t : trabajo) {
            System.out.println(t);
        }

        System.out.println("\nTAREAS PERSONALES\n--------------------");
        for (var t : personal) {
            System.out.println(t);
        }
    }
    
    private void listaArray() {
        // Fusionar las tareas en un único array de elementos
        trabajo.addAll(personal);
        Tarea[] tareas = trabajo.toArray(new Tarea[trabajo.size()]);

        System.out.println("\nContenido del array\n");
        for (var t : tareas) {
            System.out.println(t);
        }
        
    }
    
    /**
     * Recupera el contenido de un archivo en una lista y lo procesa
     * 
     * @throws IOException 
     */
    private void listaDesdeArchivo() throws IOException {
        // Listas obtenidas mediante servicios de la plataforma
        List<String> cuentas = Files.readAllLines(Paths.get(
                System.getProperty("user.home")).
                        resolve("cuentas.txt"));
        
        cuentas.replaceAll(new UnaryOperator<String>() {
            @Override
            public String apply(String t) {
                return t.substring(t.indexOf(',') + 1);
            }
        });
        
        for(String c : cuentas) 
            System.out.println(c);        
    }
}
