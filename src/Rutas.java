import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Floyd
 * 
 * Clase para la implementacion del algoritmo de Floyd Warshall.
 * 
 * @version 1.0, 18/05/2022
 * 
 * @author 
 * Andres E. Montoya - 21552
 * Diego E. Lemus - 21469
 * Fernanda Esquivel - 21542
 * Francisco J. Castillo - 21562
 */

public class Rutas {

    //-----PROPIEDADES-----
    private ArrayList<String> ciudades;
    private ArrayList<String> trayect;
    private HashMap<String, String> abrev_city;
    private HashMap<String, String> city_abrev;
    private int size;
    private final int inf = 999999;

    private String[][] direction;
    private int[][] distance;

    private String start;
    private String end;

    //-----METODOS-----
    /**
	 * Metodo Constructor
	 */
    public Rutas() {
        ciudades = new ArrayList<String>();
        trayect = new ArrayList<String>();
        abrev_city = new HashMap<String, String>();
        city_abrev = new HashMap<String, String>();
    }

    /**
	 * Metodo de ejecucion
	 */
    public void execute() {
        readCitiesFromFile();
        // Inicializador de matrices
        createDirectionMatrix();
        createDistMatrix();

        // generador de mapa de abreviaciones
        createAbbreviations();
        
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

        Floyd floyd = new Floyd(distance);
        sep();
        prnt("                 Matriz de Floyd   ");
        sep();
        printMatrix(floyd.getMatrix());
        sep();

        // MOSTRANDO AL USUARIO
        prnt("Bienvenido al Centro de Respuesta al COVID 19");
        prnt("Coordinemos la logistica de distribucion...");
        sep();

        // TRABAJO CON LAS RUTAS

        getRoute(); // OPCION 1
        // OPCION 2?
        modifyGraphs(); // OPCION 3

        prnt("Exitos en la distribucion. Adios.");
        sep();

    }

    /**
     * Saves the name and amount of cities in the given file
     */
    private void readCitiesFromFile() {
        try {
            FileReader r = new FileReader("guategrafo.txt");
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
    private void createDistMatrix(){
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == y) {
                    distance[x][y] = 0;
                } else {
                    distance[x][y] = inf;
                }
            }
            for (String line : trayect) {
                String [] data = line.split(" ");
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
    private void createAbbreviations(){
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
                        if(d == inf){
                            System.out.print("INF\t");
                        }else{
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
                        if(d == null){
                            System.out.print("-\t");
                        }else{
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
    private void prnt(String text){
        System.out.println(text);
    }

    /**
	 * Metodo para la impresion de separador
	 */
    private void sep(){
        prnt("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    /**
	 * Metodo para la obtencion de la ruta
	 */
    private void getRoute(){
        Scanner scan = new Scanner(System.in);
        prnt("Ingrese la ciudad de origen:");
        start = scan.nextLine();
        prnt("Ingrese la ciudad destino:");
        end = scan.nextLine();
        sep();
        // TO CHECK IF CITIES EXIST
        if(!(ciudades.contains(start) && ciudades.contains(end))){
            prnt("NO HAY UNA RUTA EXISTENTE PARA: " + start + " -> " + end);
        }
        else{
            prnt("PREPARANDO RUTA: " + start + " -> " + end);
            // MOSTRAR EL VLAOR DE LA DISTANCIA MÃ�S CORTA
            // MOSTRAR LAS CIUDADES POR LAS QUE PASA
            int posY = ciudades.indexOf(start);
            int posX = ciudades.indexOf(end);
            prnt("La distancia entre esas ciudades es de: " + distance[posX][posY] + "km");
            sep();
            scan.close();
        } 
        
    }

    /**
	 * Metodo para la modificaciob del grafo
	 */
    private void modifyGraphs(){


        sep();
    }
}
