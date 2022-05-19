/**
 * @author Andres
 *
 */
public class Floyd {
    private int[][] matrix;
    private int vertices;

    public Floyd(int[][] matrix){
        this.matrix = matrix;
        System.out.print(matrix.length);
        findDistance();
    }

    
    /**
     * Implementation of Floyd Warshall Algorithm
     * Source: Geeks4Geeks https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
     */
    public void findDistance(){
        int[][] dist = new int[vertices][vertices];
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

    public void printDistances()
    {
        System.out.println("The following matrix shows the shortest "+
                         "distances between every pair of vertices");
        for (int i=0; i<vertices; ++i)
        {
            for (int j=0; j<vertices; ++j)
            {
                if (matrix[i][j]== -1)
                    System.out.print("INF ");
                else
                    System.out.print(matrix[i][j]+"   ");
            }
            System.out.println();
        }
    }
}
