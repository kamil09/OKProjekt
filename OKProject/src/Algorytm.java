
import java.util.ArrayList;
import java.util.List;

public class Algorytm extends Thread{
	
	/**
	 * Populacja startowa - lista rozwiązań
	 */
	public List<Uszeregowanie> populacjaStartowa = new ArrayList<Uszeregowanie>();
	/**
	 * Populacja po rozszerzeniu
	 */
	public List<Uszeregowanie> populacjaKoncowa = new ArrayList<Uszeregowanie>();
	
	/**
	 * Konstruktor algorytmu
	 */
	public Algorytm (){	
	}
	
	
	/**
	 * Cała metoda algorytmu
	 */
	public void run(){
		final long startTime = System.currentTimeMillis();
		
		//Wygeneruj pierwszych kilka rozwiązan
		
		while(true){
			final long endTime = System.currentTimeMillis();
			//WARUNEK PRZERWANIA
			if( (endTime-startTime)/1000>Main.czas ) break;
			
			//Rozszerz do odpowiedniego rozmiaru:
			//MUTUJ
			//KRZYŻUJ
			//MUTUJ I KRZYZUJ
			//TURNIEJ
			
		}
		
		//WYBIERZ NAJLEPSZE ROZWIAZANIE I WYPISZ ROZWIAZANIE
	}
	
	

}