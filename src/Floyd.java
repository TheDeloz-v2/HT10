import java.util.Vector;

import javax.imageio.event.IIOReadWarningListener;

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
    int[][] dis;
    int[][] next;
    private final int inf = 999999;
    //-----METODOS-----
    /**
	 * Metodo Constructor
	 * @param int[][] matrix
	 */
    public Floyd(int[][] matrix){
        this.matrix = matrix;
        vertices = matrix.length;
        dist= new int[vertices][vertices];
        dis = new int[vertices][vertices];
        next = new int [vertices][vertices];
        findDistance();
    }
    
    /**
     * Implementation of Floyd Warshall Algorithm
     * Source: Geeks4Geeks https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
     */
    private void findDistance(){
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
    
    /* --------------------------------------- Area para las rutas----------------------------------- */
    public void inicializar () {
    	for(int i = 0; i < vertices; i++){
            for(int j = 0; j < vertices; j++){
                dis[i][j] = matrix[i][j];
                if(matrix[i][j]==inf) {
                	next[i][j]=-1;
                }else {
                	next[i][j]=j;
                }
            }
        }
    }
    public void floydRutas() {
    	for(int k=0;k<vertices;k++) {
    		for(int i=0;i<vertices;i++) {
    			for(int j=0;j<vertices;j++) {
    				if(dis[i][k]==inf || dis[k][j]==inf) {
    					continue;
    				}
    				if (dis[i][j]>dis[i][k]+dis[k][j]) {
    					dis[i][j]=dis[i][k]+dis[k][j];
    					next[i][j]=next[i][k];
    				}
    			}
    		}
    	}
    }
    public Vector<Integer> rutas(int a, int b){
    	if(next[a][b]==-1) {
    		return null;
    	}
    	Vector<Integer> ruta = new Vector<Integer>();
    	ruta.add(a);
    	while(a!=b) {
    		a=next[a][b];
    		ruta.add(a);
    	}
    	return ruta;
    }

    /**
     * Getter de la matriz de distancias
     */
    public int[][] getMatrix()
    {
        return dist;
    }
}
