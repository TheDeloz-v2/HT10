import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 */

/**
 * @author Andres
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> ciudades = new ArrayList<String>();
		ArrayList<String> trayect = new ArrayList<String>();
		HashMap<String, String> abrev_city = new HashMap<String, String>();
		HashMap<String, String> city_abrev = new HashMap<String, String>();
		try {
			FileReader r = new FileReader("guategrafo.txt");
			BufferedReader br = new BufferedReader(r);
			String line;
			while((line=br.readLine())!=null){
				trayect.add(line);
				String [] datos;
				datos = line.split(" ");
				String c1 =datos[0];
				String c2 = datos[1];
				if(!ciudades.contains(c1)) {
					ciudades.add(c1);
				}
				if(!ciudades.contains(c2)) {
					ciudades.add(c2);
				}
				
			}
		}catch(IOException e) {
			System.out.println("Ha ocurrido una excepcion de tipo IO: "+e);
		}
		String[][] direction = new String[ciudades.size()][ciudades.size()];
		int[][] distance = new int[ciudades.size()][ciudades.size()];
		//Inicializador de matriz de direcciones
		for(int x=0;x<ciudades.size();x++) {
			for(int y=0;y<ciudades.size();y++) {
				if(x==y) {
					direction[x][y]=null;
				}else {
					direction[x][y]=ciudades.get(x).substring(0,1);
				}
			}
		}
		//Inicializador de matriz de distancia
		for(int x=0;x<ciudades.size();x++) {
			for(int y=0;y<ciudades.size();y++) {
				if(x==y) {
					distance[x][y]=0;
				}else {
					for(int i=0;i<trayect.size();i++) {
						String [] datos;
						datos = trayect.get(x).split(" ");
						String c = datos[0]+" "+datos[1];
						String d = datos[2];
						if(c.contains(ciudades.get(x))&&c.contains(ciudades.get(y))) {
							distance[x][y]=Integer.parseInt(d);
						}else {
							distance[x][y]=-1;
						}
					}
				}
			}
		}
		//generador de mapa de abreviaciones
		for(int i=0;i<ciudades.size();i++) {
			abrev_city.put(ciudades.get(i).substring(0,1),ciudades.get(i));
			city_abrev.put(ciudades.get(i),ciudades.get(i).substring(0,1));
		}
		
		
		//imprimir matrices
		//matriz de distancia
		for(int y=0;y<ciudades.size()+1;y++) {
			for(int x=0;x<ciudades.size()+1;x++) {
				if(y==0) {
					if(x==0) {
						System.out.print("\t");
					}else {
						System.out.print(city_abrev.get(ciudades.get(x-1))+"\t");
					}
				}
				else {
					if(x==0) {
						System.out.print(city_abrev.get(ciudades.get(y-1))+"\t");
					}else {
						System.out.print(distance[x-1][y-1]+"\t");
					}
				}
			}
			System.out.print("\n");
		}
		
		System.out.print("\n");
		//matriz de direccion
		for(int y=0;y<ciudades.size()+1;y++) {
			for(int x=0;x<ciudades.size()+1;x++) {
				if(y==0) {
					if(x==0) {
						System.out.print("\t");
					}else {
						System.out.print(city_abrev.get(ciudades.get(x-1))+"\t");
					}
				}
				else {
					if(x==0) {
						System.out.print(city_abrev.get(ciudades.get(y-1))+"\t");
					}else {
						System.out.print(direction[x-1][y-1]+"\t");
					}
				}
			}
			System.out.print("\n");
		}
	}

}