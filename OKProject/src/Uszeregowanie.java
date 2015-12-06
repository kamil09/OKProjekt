import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Uszeregowanie {

	/**
	 * Suma czasów uszeregowania zadań
	 * Wielkość którą staramy się zoptymalizować
	 */
	public int sumaCzasow=-1;
	
	public List<Blok> maszyna_1 = new ArrayList<Blok>();
	public List<Blok> maszyna_2 = new ArrayList<Blok>();

	/**
	 * Kostruktor tworzy pierwsze losowe rozwiązanie
	 * @param inst
	 */
	public Uszeregowanie(Instancja inst){
		this.zbudujUszeregowanie( inst );
	}
	
	/**
	 * Domyślny konstruktor
	 */
	public Uszeregowanie(){}
	
	
	void zbudujUszeregowanie(Instancja inst){
		this.maszyna_1.add(new Blok(0,0,0));
		this.maszyna_2.add(new Blok(0,0,0));

		Random gen = new Random();
		List<Podzadanie> maszyna_1_TEMP = new ArrayList<Podzadanie>();
		List<Podzadanie> maszyna_2_TEMP = new ArrayList<Podzadanie>();
		Instancja i = inst;
		//DODANIE PRZERW DO ROZWIAZANIA
		for(Przerwa p : i.listaPrzerw) maszyna_1.add(p);
		for(Zadanie z : i.listaZadan ){
			if(z.op1.maszyna == 0) maszyna_1_TEMP.add(z.op1);
			if(z.op1.maszyna == 1) maszyna_2_TEMP.add(z.op1);
		}
		
		while( (!maszyna_1_TEMP.isEmpty()) || (!maszyna_2_TEMP.isEmpty()) ){
			if(!maszyna_1_TEMP.isEmpty()){
				int wylosowany = gen.nextInt(maszyna_1_TEMP.size());
				Podzadanie p = maszyna_1_TEMP.get(wylosowany);
				umiescNaLiscie(this.maszyna_1, p);
				if( p.brat.wykonane == false ) {
					maszyna_2_TEMP.add(p.brat);
				}
				maszyna_1_TEMP.remove(p);
			}
			if(!maszyna_2_TEMP.isEmpty()){
				int wylosowany = gen.nextInt(maszyna_2_TEMP.size());
				Podzadanie p = maszyna_2_TEMP.get(wylosowany);
				umiescNaLiscie(this.maszyna_2, p);
				if(p.brat.wykonane == false ){
					maszyna_1_TEMP.add(p.brat);
				}
				maszyna_2_TEMP.remove(p);
			}
		}
		//for(Blok b : maszyna_1) System.out.println(b.czasStartu);
		//System.out.println(iloscoperacji);
		this.ewaluacjaMaszyn();
		//this.wypiszUserzegowanie();
		//this.wypiszBledneUszeregowanieOperacji(inst);
		//System.out.println("BLAD!!!!!!!!!!!!!");
		//this.wypiszBledneUszeregowanieZadan(maszyna_1);
		//this.wypiszBledneUszeregowanieZadan(maszyna_2);
		//System.out.println("Maksymalny czas trwania uszeregowania: " + ewaluacjaMaszyn());
		//System.out.println("KONIEC BLEDU!!!!!!!!");
	}
	
	void umiescNaLiscie(List<Blok> maszyna, Podzadanie p){
		//GDY JESTEŚMY OPERACJA NR 1
		if(p.numerOperacji==1){
			for(int i=0; i<maszyna.size() ;i++){
				//JESTEŚMY W ŚRODKU USZEREGOWANIA
				if(i<(maszyna.size()-1) ){
					//JEŚLI SIĘ MIEŚCIMY
					int miejsce=maszyna.get(i+1).czasStartu-maszyna.get(i).czasKonca;
					if(miejsce>=p.czasTrwania){
						//JESLI OPERACJA JEST GOTOWA
						if(p.czasGotowosci<=maszyna.get(i).czasKonca ){
							p.czasStartu=maszyna.get(i).czasKonca;
							p.czasKonca=p.czasStartu+p.czasTrwania;
							maszyna.add(i+1, p);
							break;
						}
						//JESLI NIE, SPRAWDZAMY CZY BĘDZIE GOTOWA W TYM PRZEDZIALE I CZY SIĘ NADAL ZMIEŚCI
						else{
							miejsce=maszyna.get(i+1).czasStartu-p.czasGotowosci;
							if(miejsce>=p.czasTrwania){
								p.czasStartu=p.czasGotowosci;
								p.czasKonca=p.czasStartu+p.czasTrwania;
								maszyna.add(i+1,p);
								break;
							}
						}	
					}
				}
				//WSTAWIAMY ZADANIE NA SAM KONIEC
				else{
					if( p.czasGotowosci<=maszyna.get(i).czasKonca ) p.czasStartu=maszyna.get(i).czasKonca;
					else p.czasStartu=p.czasGotowosci;
					p.czasKonca=p.czasStartu+p.czasTrwania;
					maszyna.add(p);
					break;
				}
			}
			
		}
		//GDY JESTESMY OPERACJA NR 2 MUSIMY UWZGLĘDNIĆ DODATKOWO KIEDY OPERACJA NR 1 się wykonała
		else{
			for(int i=0; i<maszyna.size() ;i++){
				if(i<(maszyna.size()-1) ){
					//JEŚLI SIĘ MIEŚCIMY
					int miejsce=maszyna.get(i+1).czasStartu-maszyna.get(i).czasKonca;
					if(miejsce>=p.czasTrwania){
						//JESLI ZADANIE PIERWSZE ZOSTAŁO JUŻ WYKONANE
						if( p.brat.czasKonca<=maszyna.get(i).czasKonca ){
							p.czasStartu=maszyna.get(i).czasKonca;
							p.czasKonca=p.czasStartu+p.czasTrwania;
							maszyna.add(i+1, p);
							break;
						}
						
					}
				}
				//JESTEŚMY NA KOŃCU
				else{
					if(p.brat.czasKonca<=maszyna.get(i).czasKonca) p.czasStartu=maszyna.get(i).czasKonca;
					else p.czasStartu=p.brat.czasKonca;
					p.czasKonca=p.czasStartu+p.czasTrwania;
					maszyna.add(p);
					break;
				}
				
			}
			
		}
		p.wykonane=true;
	}
	
	
	void wypiszUserzegowanie(){
		int idle_1=0; int idle_1_SUM=0;
		int idle_2=0; int idle_2_SUM=0;
		int maint_1=0; int maint_1_SUM=0;
		int maint_2=0; int maint_2_SUM=0;
		
		/**************************************************************
		 * DOPISAĆ WYPISYWANIE NAZWY I CZASU PIERWSZEGO 
		 */
		System.out.println(this.sumaCzasow+","+";");
		
		System.out.print("M1: ");
		for(int i =0 ;i< this.maszyna_1.size()-1 ; i++ ){
			if(maszyna_1.get(i) instanceof Przerwa){
				wypiszPrzerwe((Przerwa)this.maszyna_1.get(i));
				maint_1++;
				maint_1_SUM+=this.maszyna_1.get(i).czasTrwania;
			}
			if(maszyna_1.get(i) instanceof Podzadanie)
				wypiszOperacje( (Podzadanie)this.maszyna_1.get(i) );
			
			if(this.maszyna_1.get(i+1).czasStartu!=this.maszyna_1.get(i).czasKonca){
				idle_1++;
				idle_1_SUM+=this.maszyna_1.get(i+1).czasStartu-this.maszyna_1.get(i).czasKonca;
				System.out.print("idle"+idle_1+"_"+"M1,"+this.maszyna_1.get(i).czasKonca+","+(this.maszyna_1.get(i+1).czasStartu-this.maszyna_1.get(i).czasKonca)+";\t");
			}
			
		}
		System.out.print(this.maszyna_1.get(this.maszyna_1.size()-1).czasTrwania);
		if(this.maszyna_1.get(this.maszyna_1.size()-1) instanceof Podzadanie)
			wypiszOperacje((Podzadanie)this.maszyna_1.get(this.maszyna_1.size()-1));
		System.out.println(" ");
		
		System.out.print("M2: ");
		for(int i =0 ;i< this.maszyna_2.size()-1 ; i++ ){
			if(maszyna_2.get(i) instanceof Przerwa){
				wypiszPrzerwe((Przerwa)this.maszyna_2.get(i));
				maint_2++;
				maint_2_SUM+=this.maszyna_2.get(i).czasTrwania;
			}
			if(maszyna_2.get(i) instanceof Podzadanie)
				wypiszOperacje((Podzadanie)this.maszyna_2.get(i));
			if(this.maszyna_2.get(i+1).czasStartu!=this.maszyna_2.get(i).czasKonca){
				idle_2++;
				idle_2_SUM+=this.maszyna_2.get(i+1).czasStartu-this.maszyna_2.get(i).czasKonca;
				System.out.print("idle"+idle_2+"_"+"M2,"+this.maszyna_2.get(i).czasKonca+","+(this.maszyna_2.get(i+1).czasStartu-this.maszyna_2.get(i).czasKonca)+";\t");
			}
		}
		if(this.maszyna_2.get(this.maszyna_2.size()-1) instanceof Podzadanie)
			wypiszOperacje((Podzadanie)this.maszyna_2.get(this.maszyna_2.size()-1));
		
		System.out.println(" ");
		//WYPISYWANIE PRZERW KONSERWUJĄCYCH
		System.out.println(maint_1+","+maint_1_SUM+"; ");
		System.out.println(maint_2+","+maint_2_SUM+"; ");
		//WYPISYWANIE PRZERW IDLE
		System.out.println(idle_1+","+idle_1_SUM+"; ");
		System.out.println(idle_2+","+idle_2_SUM+"; ");
		System.out.println("");
	}
	
	void wypiszPrzerwe(Przerwa p){
		System.out.print("maint" 
				+p.id+"_M1,"
				+p.czasStartu+","
				+p.czasTrwania+";\t");
	}
	void wypiszOperacje(Podzadanie p){
		System.out.print("o"+p.numerOperacji+"_" 
				+p.id+","
				+p.czasStartu+","
				+p.czasTrwania+";\t");
	}
	
	int czasTrwaniaUszeregowania(List<Blok> maszyna){
		int sumaCzasowKoncowych=0;
		for (int i = 0; i < maszyna.size(); i++) {
			if(maszyna.get(i) instanceof Podzadanie){
				sumaCzasowKoncowych+=maszyna.get(i).czasKonca;
			}
		}
		return sumaCzasowKoncowych;
	}
	int ewaluacjaMaszyn(){
		int czasMaszyny1 = czasTrwaniaUszeregowania(maszyna_1);
		int czasMaszyny2 = czasTrwaniaUszeregowania(maszyna_2);
		this.sumaCzasow=Math.max(czasMaszyny1,czasMaszyny2);
		return this.sumaCzasow;
	}
	
	
	//METODY TESTUJĄCE POPRAWNOŚĆ USZEREGOWANIA - OP1 PRZED OP2 Oraz czasy rozpoczęcia / zakończenia
	void wypiszBledneUszeregowanieOperacji(Instancja inst){
		List<Zadanie> listaZadanDoSprawdzenia = inst.listaZadan;
		for (int i = 0; i < listaZadanDoSprawdzenia.size() ; i++) {
			if (listaZadanDoSprawdzenia.get(i).op2.czasStartu < listaZadanDoSprawdzenia.get(i).op1.czasKonca) {
				wypiszOperacje(listaZadanDoSprawdzenia.get(i).op1);
				System.out.printf(" ");
				wypiszOperacje(listaZadanDoSprawdzenia.get(i).op2);
				System.out.println("");
			}
		}
	}
	void wypiszBledneUszeregowanieZadan(List<Blok> maszyna){
		if(maszyna.isEmpty()){
			System.out.println("maszyna jest pusta");
		}
		for (int i = 0; i < maszyna.size()-1; i++) {
			if(maszyna.get(i).czasKonca>maszyna.get(i+1).czasStartu){
				if (maszyna.get(i) instanceof Przerwa){
					wypiszPrzerwe((Przerwa) maszyna.get(i));
				}
				else if(maszyna.get(i) instanceof Podzadanie){
					wypiszOperacje((Podzadanie) maszyna.get(i));
				}
				if (maszyna.get(i+1) instanceof Przerwa){
					wypiszPrzerwe((Przerwa) maszyna.get(i+1));
				}
				else if(maszyna.get(i+1) instanceof Podzadanie){
					wypiszOperacje((Podzadanie) maszyna.get(i+1));
				}
				System.out.println("");
			}
		}
	}
}
