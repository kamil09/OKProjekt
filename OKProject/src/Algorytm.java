
public class Algorytm extends Thread{
	
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
		
		//WYPISZ ROZWIAZANIE
	}
	
	

}
