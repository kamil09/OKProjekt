
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
		
		//Wygeneruj populacje startową
		for(int i=0;i<Main.populacjaStartowa;i++) {
			Instancja instCp = new Instancja( Main.instancja );
			this.populacjaStartowa.add(new Uszeregowanie(instCp) );
		}
		for(Uszeregowanie u : this.populacjaStartowa) u.ewaluacjaMaszyn();
		wybierzNajlepsze().wypiszUserzegowanie();
		
		while(true){
			final long endTime = System.currentTimeMillis();
			//WARUNEK PRZERWANIA
			if( (endTime-startTime)/1000>Main.czas ) break;
			this.populacjaKoncowa.addAll(this.populacjaStartowa);
			
			//*****************TEST TURNIEJ*******************************
				for(int i=0 ; i< Main.populacjaEwolucji- this.populacjaStartowa.size(); i++)
					this.populacjaKoncowa.add(new Uszeregowanie (new Instancja( Main.instancja )) );
			//********************WYWALIĆ PÓXNIEJ**************************
			
			//Rozszerz do odpowiedniego rozmiaru:
			for(int i=0; i< Main.iloscZadan*Main.iloscMutacji; i++){
				//MUTACJE
			}
			for(int i=0; i< Main.iloscZadan*Main.iloscKrzyzowania; i++){
				//KRZYŻOWANIE
			}
			for(int i=0; i< Main.populacjaEwolucji-this.populacjaKoncowa.size() ; i++){
				//MUTACJA I KRZYZOWANIE
			}
			//WYZNACZENIE WARTOŚCI OPTYMALIZOWANEJ
			for(Uszeregowanie u : this.populacjaKoncowa) u.ewaluacjaMaszyn();
			//ROZPOCZECIE TURNIEJU
			this.turniej();
			
		}
		
		//WYBIERZ NAJLEPSZE ROZWIAZANIE I WYPISZ ROZWIAZANIE
		wybierzNajlepsze().wypiszUserzegowanie();
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
				this.populacjaStartowa.add(this.populacjaKoncowa.get(najlepszy));
			}
		}
		this.populacjaKoncowa.clear();
	}
	
	/**
	 * Algorytm wybierania najoptymalniejszego rozwiązania z polulacji Startowej (po TURNIEJU)
	 */
	Uszeregowanie wybierzNajlepsze(){
		int index=0;
		int maxVal=0;
		maxVal=this.populacjaStartowa.get(0).sumaCzasow;
		for(int i=1; i< this.populacjaStartowa.size() ;i++){
			if(this.populacjaStartowa.get(i).sumaCzasow<maxVal ){
				index=i;
				maxVal=this.populacjaStartowa.get(i).sumaCzasow;
			}
		}
		return this.populacjaStartowa.get(index);
	}
	
	
	

}