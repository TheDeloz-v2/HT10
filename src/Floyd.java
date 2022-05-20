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

public class Floyd {

    //-----PROPIEDADES-----
    private int[][] matrix;
    private int vertices;
    int[][] dist;
    //-----METODOS-----
    /**
	 * Metodo Constructor
	 * @param int[][] matrix
	 */
    public Floyd(int[][] matrix){
        this.matrix = matrix;
        System.out.print(matrix.length);
         dist= new int[vertices][vertices];
        findDistance();
    }
    
    /**
     * Implementation of Floyd Warshall Algorithm
     * Source: Geeks4Geeks https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
     */
    public void findDistance(){
        int i, j, k;

        for(i = 0; i < vertices; i++){
            for(j = 0; j < vertices; j++){
                dist[i][j] = matrix[i][j];
            }
        }
        
        for(k = 0; k < vertices; k++){
            for(i = 0; i < vertices; i++){
                for(j = 0; j < vertices; j++){
                    if (dist[i][k] + dist[k][j] < dist[i][j]){
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    /**
     * Impresion de las distancias entre vertices.
     */
    public void printDistances()
    {
        System.out.println("The following matrix shows the shortest "+
                         "distances between every pair of vertices");
        for (int i=0; i<vertices; ++i)
        {
            for (int j=0; j<vertices; ++j)
            {
                if (dist[i][j]== 999999)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j]+"   ");
            }
            System.out.println();
        }
    }
}
