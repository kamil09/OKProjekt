
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
			this.turniej();
		}
		
		//WYBIERZ NAJLEPSZE ROZWIAZANIE I WYPISZ ROZWIAZANIE
	}
	
	/**
	 * Algorytm turnieju
	 */
	public void turniej(){
		this.populacjaStartowa.clear();
		if(!this.populacjaKoncowa.isEmpty()){
			int size = (int) Math.ceil(Main.populacjaEwolucji/Main.populacjaStartowa);
			for(int first=0; first< Main.populacjaEwolucji; first+=size ){
				int last=first+size-1;
				int najlepszy=-1;
				int najlepszyVal=-1;
				
				if(last>=Main.populacjaEwolucji) last=Main.populacjaEwolucji-1;
				najlepszy=first;
				najlepszyVal=this.populacjaKoncowa.get(first).sumaCzasow;
				
				for(int k=first+1; k<=last ; k++ ){
					if(this.populacjaKoncowa.get(k).sumaCzasow < najlepszyVal ){
						najlepszy=k;
						najlepszyVal=this.populacjaKoncowa.get(k).sumaCzasow;
					}
				}
				this.populacjaKoncowa.add(this.populacjaKoncowa.get(najlepszy));
			}
		}
		this.populacjaKoncowa.clear();
	}
	
	

}