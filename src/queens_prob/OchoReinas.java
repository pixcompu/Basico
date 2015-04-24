package queens_prob;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/** * @author nessy* */
public class OchoReinas {
	static int cont=0;//para contar el número de soluciones del método resolver
	//PSEUDOCODIGO
	//	Disponibles(ocup). //recibe un vector o una lista con 
	//		d=[0,1,2,3,4,5,6,7]
	//		k= tamaño(ocup)
	//		Para cada c en ocup:
	//			Eliminar c de d
	//			Eliminar (c+k) de d
	//			Eliminar (c-k) de d
	//			K - -
	//		devolver d
	/**Este método sirve para determinar las columnas en las que
	 * se puede colocar la siguiente reina, habiéndose puesto ya algunas
	 * reinas cuyas columnas se dan
	 * @param ocup Columnas que ocupan las reinas previas
	 * @return Columnas disponibles sin comerse	 */
	public static List<Integer> disponibles(List<Integer>ocup){
		//creamos la lista de disponibles que inicialmente tiene todas las columnas
		//		d=[0,1,2,3,4,5,6,7]
		List<Integer> d= new LinkedList<>();//podría haber utilizado un ArrayList
		for(int i=0;i<8;i++){
			d.add(new Integer(i));
		}
		//averiguamos cuántas reinas hay ya colocadas y lo guardamos en la variable k
		//		k= tamaño(ocup)
		int k=ocup.size();
		/*recorremos la lista de columnas ocupadas para ir tachando los lugares que 
		 * no nos vale. Podriamos hacerlo con un for, pero para listas mejor el Iterator*/
		//Para cada c en ocup:
		//			Eliminar c de d
		//			Eliminar (c+k) de d
		//			Eliminar (c-k) de d
		//			K - -
		for (Iterator<Integer> j=ocup.iterator();j.hasNext();k--){
			Integer c=j.next();
			//trabajamos con objetos que contienen números enteros por eso uso el remove
			d.remove(c);
			d.remove(new Integer(c.intValue()+k));
			d.remove(new Integer(c.intValue()-k));
		}
		//finalmente devolvemos lo que nos queda
		//devolver d
		return d;
	}
	//	resolver(ocup):
	//		si tamaño(ocup)<8:
	//		d=disponibles(ocup)
	//		para cada c en d:
	//				resolver(ocup+c)
	//			en caso contrario:
	//				mostrar solución
	/** Encuentra todas las soluciones para el problema de las 8 reinas
	 * @param ocup Lista con las ocupaciones previas, siendo su valor inicial una lista vacía
	 * resolver(ocup):
	 * si tamaño(ocup)<8:
	 * d=disponibles(ocup)
	 * para cada c en d:
	 *		resolver(ocup+c)
	 *	en caso contrario:
	 *		mostrar solución
	 */
	public static void resolver(List<Integer> ocup){
		// si tenemos ya 8 reinas ubicadas, es que encontramos una solución, si no, hay que iterar recursivamente
		if (ocup.size()<8){
			// d=disponibles(ocup)
			List<Integer> d = disponibles(ocup);
			// para cada c en d:
			for (Iterator<Integer> c=d.iterator();c.hasNext();){
				//resolver(ocup+c) Aquí tengo que crear una lista provisional
				List<Integer> provisional = new LinkedList<>(ocup);
				provisional.add(c.next());
				resolver(provisional);
			}
		}else{
			//mostramos la solución por pantalla como una linea
			String solucion="";
			for (Iterator<Integer> j=ocup.iterator();j.hasNext();){
				solucion += j.next().intValue();
			}
			cont++;
			System.out.println("Solucion " + cont+" "+solucion + " ");
		}
	}

	public static void main(String[] args) {
		//1 OPCION con el método disponibles
		/* Creo un ejemplo para ver si la funcion disponibles funciona bien*/
		/* Creamos la lista de prueba*/
		// 2 OPCION con el algoritmo resolver
		LinkedList<Integer> b = new LinkedList<>();
		System.out.println("La solucón con el método resolver es: ");
		resolver(b);
	}
}

