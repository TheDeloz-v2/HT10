import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

/**
 * Class Floyd
 * 
 * Clase para la implementacion del algoritmo de Floyd Warshall.
 * 
 * @version 1.0, 18/05/2022
 * 
 * @author
 *         Andres E. Montoya - 21552
 *         Diego E. Lemus - 21469
 *         Fernanda Esquivel - 21542
 *         Francisco J. Castillo - 21562
 */

public class Rutas {

    // -----PROPIEDADES-----
    private Scanner scan;
    private String fileName;
    Floyd floyd;

    private ArrayList<String> ciudades;
    private ArrayList<String> trayect;
    private HashMap<String, String> abrev_city;
    private HashMap<String, String> city_abrev;
    private Vector<Integer> ruta;
    private int size;
    private final int inf = 999999;

    private String[][] direction;
    private int[][] distance;
    private int[][] distanceFloyd;

    private String start;
    private String end;

    // -----METODOS-----
    /**
     * Metodo Constructor
     */
    public Rutas(String fileName) {
        this.fileName = fileName;
        scan = new Scanner(System.in);

        ciudades = new ArrayList<String>();
        trayect = new ArrayList<String>();
        abrev_city = new HashMap<String, String>();
        city_abrev = new HashMap<String, String>();

        readCitiesFromFile();
        // Inicializador de matrices
        createDirectionMatrix();
        createDistMatrix();

        // generador de mapa de abreviaciones
        createAbbreviations();

        // FLOYD
        floyd = new Floyd(distance);
        distanceFloyd = floyd.getMatrix();
        floyd.inicializar();
        floyd.floydRutas();

    }

    /**
     * Metodo de ejecucion
     */
    public void execute() {
        // MATRIZ DE DISTANCIA
        sep();
        prnt("                Matriz de distancia   ");
        sep();
        printMatrix(distance);

        // matriz de direccion
        sep();
        prnt("                Matriz de direccion   ");
        sep();
        printMatrix(direction);

        sep();
        prnt("                Matriz de Floyd   ");
        sep();
        printMatrix(distanceFloyd);
        sep();
        // MOSTRANDO AL USUARIO
        prnt("Bienvenido al Centro de Respuesta al COVID 19");
        prnt("Coordinemos la logistica de distribucion...");
        sep();

        boolean end = false;
        String input = "";
        while (!end) {
            boolean goodInput = false;
            getRoute();
            // generar rutas
            if (ciudades.contains(this.start) && ciudades.contains(this.end)) {
                ruta = floyd.rutas(ciudades.indexOf(start), ciudades.indexOf(this.end));
                getPath();
            }
            sep();
            prnt("*CENTRO DEL GRAFO*");// CENTRO DEL GRAFO
            sep();

            option3();

            goodInput = false;
            while (!goodInput) {
                prnt("Desea verificar otra ruta? S/N");
                input = scan.nextLine().toUpperCase();
                if (input.equals("S")) {
                    goodInput = true;
                }
                if (input.equals("N")) {
                    goodInput = true;
                    end = true;
                }
            }
            sep();
        }
        scan.close();
        prnt("Exitos en la distribucion. Adios.");
        sep();
    }

    /**
     * Saves the name and amount of cities in the given file
     */
    private void readCitiesFromFile() {
        trayect = new ArrayList<>();
        try {
            FileReader r = new FileReader(fileName);
            BufferedReader br = new BufferedReader(r);
            String line;
            while ((line = br.readLine()) != null) {
                trayect.add(line);
                String[] datos;
                datos = line.split(" ");
                String c1 = datos[0];
                String c2 = datos[1];
                if (!ciudades.contains(c1)) {
                    ciudades.add(c1);
                }
                if (!ciudades.contains(c2)) {
                    ciudades.add(c2);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Ha ocurrido una excepcion de tipo IO: " + e);
        }

        size = ciudades.size(); // SETS SIZE VARIABLE FOR ENVIRNOMENT
        System.out.println(ciudades);
        direction = new String[size][size];
        distance = new int[size][size];
    }

    /**
     * Metodo para la creacion de direccion de la ruta
     */
    private void createDirectionMatrix() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y) {
                    direction[x][y] = null;
                } else {
                    direction[x][y] = ciudades.get(x).substring(0, 1);
                }
            }
        }
    }

    /**
     * Metodo para la creacion de distancia de la ruta
     */
    private void createDistMatrix() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y) {
                    distance[x][y] = 0;
                } else {
                    distance[x][y] = inf;
                }
            }
            for (String line : trayect) {
                String[] data = line.split(" ");
                int posY = ciudades.indexOf(data[0]);
                int posX = ciudades.indexOf(data[1]);
                distance[posX][posY] = Integer.parseInt(data[2]);
                distance[posY][posX] = Integer.parseInt(data[2]);
            }
        }
    }

    /**
     * Metodo para la creacion de abreviaturas
     */
    private void createAbbreviations() {
        for (int i = 0; i < ciudades.size(); i++) {
            abrev_city.put(ciudades.get(i).substring(0, 1), ciudades.get(i));
            city_abrev.put(ciudades.get(i), ciudades.get(i).substring(0, 1));
        }
    }

    /**
     * Metodo para la impresion de la matriz
     */
    private void printMatrix(int[][] matrix) {
        for (int y = 0; y < ciudades.size() + 1; y++) {
            for (int x = 0; x < ciudades.size() + 1; x++) {
                if (y == 0) {
                    if (x == 0) {
                        System.out.print("\t");
                    } else {
                        System.out.print(city_abrev.get(ciudades.get(x - 1)) + "\t");
                    }
                } else {
                    if (x == 0) {
                        System.out.print(city_abrev.get(ciudades.get(y - 1)) + "\t");
                    } else {
                        int d = matrix[x - 1][y - 1];
                        if (d == inf) {
                            System.out.print("INF\t");
                        } else {
                            System.out.print(d + "\t");
                        }
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Metodo para la impresion de la matriz
     * 
     * @param String[][] matrix
     */
    private void printMatrix(String[][] matrix) {
        for (int y = 0; y < ciudades.size() + 1; y++) {
            for (int x = 0; x < ciudades.size() + 1; x++) {
                if (y == 0) {
                    if (x == 0) {
                        System.out.print("\t");
                    } else {
                        System.out.print(city_abrev.get(ciudades.get(x - 1)) + "\t");
                    }
                } else {
                    if (x == 0) {
                        System.out.print(city_abrev.get(ciudades.get(y - 1)) + "\t");
                    } else {
                        String d = matrix[x - 1][y - 1];
                        if (d == null) {
                            System.out.print("-\t");
                        } else {
                            System.out.print(d + "\t");
                        }
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    /**
     * Metodo para la impresion de texto
     */
    private void prnt(String text) {
        System.out.println(text);
    }

    /**
     * Metodo para la impresion de separador
     */
    private void sep() {
        prnt("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
     * Metodo para la obtencion de la ruta
     */
    public boolean getRoute() {
        prnt("Ingrese la ciudad de origen:");
        start = scan.nextLine();
        prnt("Ingrese la ciudad destino:");
        end = scan.nextLine();
        sep();
        // TO CHECK IF CITIES EXIST
        if (!(ciudades.contains(start) && ciudades.contains(end))) {
            prnt("NO HAY UNA RUTA EXISTENTE PARA: " + start + " -> " + end);

            return false;
        } else {
            prnt("PREPARANDO RUTA: " + start + " -> " + end);
            // MOSTRAR EL VLAOR DE LA DISTANCIA MÃ�S CORTA
            // MOSTRAR LAS CIUDADES POR LAS QUE PASA
            int posY = ciudades.indexOf(start);
            int posX = ciudades.indexOf(end);
            prnt("La distancia entre esas ciudades es de: " + distanceFloyd[posX][posY] + "km");

            return true;
        }

    }

    private void getPath() {
        int n = ruta.size();
        prnt("La más corta a seguir es:");
        for (int i = 0; i < n - 1; i++) {
            System.out.print(ciudades.get(ruta.get(i)) + "->");
        }
        System.out.print(ciudades.get(ruta.get(n - 1)) + "\n");
    }

    private void option3() {
        boolean goodInput = false;
        while (!goodInput) {
            prnt("Desea modificar las rutas? S/N");
            String input = scan.nextLine().toUpperCase();
            if (input.equals("S")) {
                prnt("Que desea hacer?");
                prnt("1 | Interrupcion de trafico");
                prnt("2 | Establecer conexion");

                String userInput = scan.nextLine();
                boolean goodSecondInput = false;
                int option2 = -1;
                while (!goodSecondInput) {
                    try {
                        option2 = Integer.parseInt(userInput);
                        sep();
                        if (option2 == 1) {
                            prnt("REPORTANDO INTERRUPCION");
                            prnt("Ingrese la ciudad de origen:");
                            start = scan.nextLine();
                            prnt("Ingrese la ciudad destino:");
                            end = scan.nextLine();
                            deleteRoute(start, end);
                            goodSecondInput = true;
                        } else if (option2 == 2) {
                            prnt("ESTABLECIENDO CONEXION:");
                            prnt("Ingrese la ciudad de origen:");
                            start = scan.nextLine();
                            prnt("Ingrese la ciudad destino:");
                            end = scan.nextLine();

                            prnt("Ingrese la distancia entre ciudades: (En kilometros, solo el numero)");
                            int dist = inf;
                            String distInput = scan.nextLine();
                            boolean goodInput2 = false;
                            while (!goodInput2) {
                                try {
                                    dist = Integer.parseInt(distInput);
                                    goodInput2 = true;
                                } catch (Exception e) {
                                    prnt("Ingrese una opcion valida");
                                }
                            }

                            createRoute(start, end, dist);

                            sep();
                            goodSecondInput = true;
                        }
                    } catch (Exception e) {
                        prnt("Ingrese una opcion valida");
                    }
                }
                sep();
                goodInput = true;
            } else if (input.equals("N")) {
                goodInput = true;
            }
        }
    }

    public void createRoute(String start2, String end2, int distance) {
        String newRoute = start + " " + end + " " + distance;
        trayect.add(newRoute);
        rewriteFile();
    }

    public void deleteRoute(String start, String end) {
        if (ciudades.contains(start) && ciudades.contains(end)) {
            int posY = ciudades.indexOf(start);
            int posX = ciudades.indexOf(end);
            int dist = distance[posX][posY];
            if (dist != inf) {
                String search = start + " " + end + " " + dist;
                trayect.remove(search);
                rewriteFile();
            }
        } else {
            prnt("Esta ruta no existe. No se pueden reportar interrupcion de trafico.");
        }
    }

    public boolean routeExists(String start, String end) {
        if (ciudades.contains(start) && ciudades.contains(end)) {
            int posY = ciudades.indexOf(start);
            int posX = ciudades.indexOf(end);
            if (distance[posX][posY] != inf)
                return true;
        }
        return false;
    }

    private void rewriteFile() {
        try {
            FileWriter fw = new FileWriter(fileName, false);

            for (String line : trayect) {
                fw.write(line + "\n");
            }

            fw.close();

            readCitiesFromFile();
            createDirectionMatrix();
            createDistMatrix();
            createAbbreviations();
            floyd = new Floyd(distance);
            floyd.inicializar();
            floyd.floydRutas();
            distanceFloyd = floyd.getMatrix();

        } catch (Exception e) {
            prnt("Error IO");
        }
    }
}
