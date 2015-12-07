
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		Random gen = new Random();
		
		//Wygeneruj populacje startową
		for(int i=0;i<Main.populacjaStartowa;i++) {
			Instancja instCp = new Instancja( Main.instancja );
			this.populacjaStartowa.add(new Uszeregowanie(instCp) );
		}
		for(Uszeregowanie u : this.populacjaStartowa) u.ewaluacjaMaszyn();
		Main.pierwszeRozwiazanie=wybierzNajlepsze().sumaCzasow;
		wybierzNajlepsze().wypiszUserzegowanie();
		int czasZminyMutacji=1;
		while(true){
			final long endTime = System.currentTimeMillis();
			//WARUNEK PRZERWANIA
			if( (endTime-startTime)/1000>Main.czas ) break;
			if( (endTime-startTime)/1000> czasZminyMutacji ){
				czasZminyMutacji++;
				Main.silaMutacji/=1.5;
			}
			
			this.populacjaKoncowa.addAll(this.populacjaStartowa);
			//Rozszerz do odpowiedniego rozmiaru:
			for(int i=0; i< (Main.populacjaEwolucji-Main.populacjaStartowa)*Main.iloscMutacji; i++){
				//MUTACJE
				Uszeregowanie noU = new Uszeregowanie(this.populacjaStartowa.get( gen.nextInt(this.populacjaStartowa.size()) ) );
				noU.pelnaMutacja();
				this.populacjaKoncowa.add(noU);
			}
			for(int i=0; i<(Main.populacjaEwolucji-Main.populacjaStartowa)*Main.iloscKrzyzowania; i++){
				//KRZYŻOWANIE
				Uszeregowanie noU = new Uszeregowanie(this.populacjaStartowa.get( gen.nextInt(this.populacjaStartowa.size()) ) );
				noU.krzyzowanie(this.populacjaStartowa.get(gen.nextInt(this.populacjaStartowa.size())) );
				this.populacjaKoncowa.add(noU);			}
			int size=Main.populacjaEwolucji-this.populacjaKoncowa.size();
			for(int i=0; i < size ; i++){
				//MUTACJA I KRZYZOWANIE
				Uszeregowanie noU = new Uszeregowanie(this.populacjaStartowa.get( gen.nextInt(this.populacjaStartowa.size()) ) );
				noU.pelnaMutacja();
				noU.krzyzowanie(this.populacjaStartowa.get(gen.nextInt(this.populacjaStartowa.size())) );
				this.populacjaKoncowa.add(noU);
			}
			//WYZNACZENIE WARTOŚCI OPTYMALIZOWANEJ
			for(Uszeregowanie u : this.populacjaKoncowa) u.ewaluacjaMaszyn();
			//ROZPOCZECIE TURNIEJU
			this.turniej();
			
		}
		
		//WYBIERZ NAJLEPSZE ROZWIAZANIE I WYPISZ ROZWIAZANIE
		wybierzNajlepsze().wypiszUserzegowanie();
		
		
		/**
		 * TEST KOPIOWANIA USZEREGOWANIA WYPADŁ OK
		 */
//		wybierzNajlepsze().pelnaMutacja();
//		Uszeregowanie testU = new Uszeregowanie(wybierzNajlepsze());
//		testU.ewaluacjaMaszyn();
//		testU.wypiszBledneUszeregowanieOperacji(testU.instancjaUszeregowania);
//		testU.wypiszBledneUszeregowanieZadan(testU.maszyna_1);
//		testU.wypiszBledneUszeregowanieZadan(testU.maszyna_2);
//		testU.wypiszUserzegowanie();
	}
	
	/**
	 * Algorytm turnieju
	 */
	public void turniej(){
		this.populacjaStartowa.clear();
		if(!this.populacjaKoncowa.isEmpty()){
			int size = (int) Math.ceil(this.populacjaKoncowa.size()/Main.populacjaStartowa);
			for(int first=0; first< this.populacjaKoncowa.size(); first+=size ){
				int last=first+size-1;
				int najlepszy=-1;
				int najlepszyVal=-1;
				
				if(last>=this.populacjaKoncowa.size()) last=this.populacjaKoncowa.size()-1;
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