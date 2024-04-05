/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package listas_practice;

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
 *
 * @author hugo.suarez
 */
public class Listas_practice {

    //Listas para anotacións de tarefas
    private ArrayList<Task> jobTask = new ArrayList<>();
    private LinkedList<Task> personalTask = new LinkedList<>();

    //Definición de enumeracións que podemos precisar
    public enum Date {
        Luns, Martes, Mercores, Xoves, Venres, Sábado, Domingo
    };

    /**
     * Definición da clase que xestiona as tarefas
     *
     */
    public class Task {

        private Date day;
        private LocalTime time;
        private String description;
        private boolean check;
        //Creación de constructores

        //Constructor xenérico
        public Task(String description) {
            this(description, Date.Luns, LocalTime.of(9, 0));
        }

        //Constructor específico
        public Task(String description, Date day, LocalTime time) {
            this.description = description;
            this.day = day;
            this.time = time;
            this.check = false;
        }

        @Override
        public String toString() {
            return "Tarea{" + "dia=" + day + ", hora=" + time
                    + ", tarea=" + description + ", completada=" + check + '}';
        }

        public Date getDay() {
            return day;
        }

        public void setDay(Date day) {
            this.day = day;
        }

        public LocalTime getTime() {
            return time;
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

    }

    public static void main(String[] args) {
        try {
            (new Listas_practice()).run();
        } catch (IOException ex) {
            System.out.print(ex);
        }
    }

    private void run() throws IOException {
        System.out.printf("Tamaño inicial: %d y %d elementos\n",
                jobTask.size(), personalTask.size());

        System.out.println("Tras inicializar a lista de tarefas\n");
        initTasks();
        orderTask();
        showTasks();

        System.out.println("Tras eliminar las tareas completadas\n");
        deleteCheckTasks();
        showTasks();
        listArray();
        listFromFile();
    }

    private void initTasks() {
        //Array inicial de tarefas
        Task[] arrayTasks = {
            new Task("Revisión equipos contabilidad"),
            new Task("Cierre ejercicio")
        };
        //Añado array de tarefas como lista, á lista creada inicialmente,
        //jobtask

        jobTask.addAll(Arrays.asList(arrayTasks));

        /**
         * Añado tarefas a través do constructor Task
         */
        jobTask.add(new Task("informe económico meas"));
        jobTask.add(new Task("pago de nóminas", Date.Luns, LocalTime.of(17, 10)));
        jobTask.add(new Task("emitir facturas", Date.Martes, LocalTime.of(13, 10)));

        personalTask.add(new Task("Comprar regalo a mamá", Date.Domingo, LocalTime.of(20, 0)));
        personalTask.add(new Task("Visita al planetario con los niños"));
        personalTask.add(new Task("Planificar viaje vacaciones"));

        /**
         * Modificar tarefas a través de getters e setters*
         */
        jobTask.get(0).setDay(Date.Venres);
        jobTask.get(2).setCheck(true);
        personalTask.get(1).setDay(Date.Mercores);
        personalTask.get(1).setTime(LocalTime.of(18, 15));
        personalTask.get(2).setTime(LocalTime.NOON);

        System.out.printf("Tamaño tras agregar tres tarefas: %d e %d elementos\n", jobTask.size(), personalTask.size());

    }

    /**
     * Ordenar tarefas
     */
    private void orderTask() {
        /**
         * Primer modo--------- Uso método sort, global a todas as listas
         * Pasamos como parámetro un obxecto que implementa a interfaz
         * 'Comparator' Dicha interfaz, obligame á implementación de un
         * método-compare- Recibe as dúas tarefas e devolve un int, indicando a
         * orde relativa entre as mesmas.
         */

        jobTask.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.time.compareTo(o2.time);
            }
        });

        /**
         * Segundo modo---------------------------------- En lugar de definir o
         * método de comaparación a trvés dunha clase anónima, Usamos unha
         * expresión 'lambda', pasamos parámetros e o método 'compare'
         * dorectamente
         *
         */
        personalTask.sort((Task o1, Task o2) -> o1.time.compareTo(o2.time));

    }

    /**
     * Mostrar tarefas
     */
    private void showTasks() {
        System.out.println("Tarefas de traballo\n----------------------");
        for (Task t : jobTask) {
            System.out.println(t);
        }

        System.out.println("\nTarefas persoais\n-----------------------------");
        for (Task t : personalTask) {
            System.out.println(t);
        }

    }

    /**
     * Borrado de tarefas
     */
    private void deleteCheckTasks() {

        /**
         * Igual que no caso anterior, existe duas posibilidades para eliminar
         * cada un dos obexectos das listas. No primeiro caso, implementamos
         * interfaz Predicate, e usamoso o método test, que neste caso busca
         * aqueles elementos onde .check=true
         */
        jobTask.removeIf(new Predicate<Task>() {
            @Override
            public boolean test(Task t) {
                return t.check;
            }
        });

        /**
         * No segundo caso utilzamos unha expresión lambda como no caso do
         * método anterior para ordenar as tarefas
         */
        personalTask.removeIf((Task t) -> t.check);

    }
    
    
    /**
     * Añadir todos os elementos a un array
     */
    private void listArray(){
        //Fusionar as duas listas
        jobTask.addAll(personalTask);
        //COnvertimos a jobTask en un array
        Task[] arrayTasks=jobTask.toArray(new Task[jobTask.size()]);
        System.out.println("\nCOntido do array\n");
        for (Task t:arrayTasks){
            System.out.println(t);
        }
    }
    
    /**
     * Recuperar contido dun arquivo e procesado
     */
    
    private void listFromFile()throws IOException{
        //Lista obtida mediante servicio de arquivos
        
        List<String> counts =Files.readAllLines(Paths.get(
        System.getProperty("user.home")).
                resolve("counts.txt"));
        counts.replaceAll(new UnaryOperator<String>(){
        @Override
        public String apply (String t){
        return t.substring(t.indexOf(',')+1);
    
    }
    
    
    });
        for(String c : counts)
            System.out.println(c);
    
}
}