import java.util.ArrayList;
import java.util.Collections;
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
	public Instancja instancjaUszeregowania;

	/**
	 * Kostruktor tworzy pierwsze losowe rozwiązanie
	 * @param inst
	 */
	public Uszeregowanie(Instancja inst){
		this.zbudujUszeregowanie( inst );
		this.instancjaUszeregowania=inst;
	}
	
	/**
	 * Konstruktor kopiujący uszeregowanie
	 */
	public Uszeregowanie(Uszeregowanie u){
		//Kopia instancji
		this.instancjaUszeregowania = new Instancja( u.instancjaUszeregowania );
		this.maszyna_1.add(new Blok(0,0,0));
		this.maszyna_2.add(new Blok(0,0,0));
		for(Przerwa p : this.instancjaUszeregowania.listaPrzerw ) this.maszyna_1.add(p);
		for(Zadanie z : this.instancjaUszeregowania.listaZadan ){
			if(z.op1.maszyna == 0){
				this.maszyna_1.add(z.op1);
				this.maszyna_2.add(z.op2);
			}
			else{
				this.maszyna_1.add(z.op2);
				this.maszyna_2.add(z.op1);
			}
		}
		Collections.sort(this.maszyna_1);
		Collections.sort(this.maszyna_2);
		
	}
	
	
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
		this.wypiszBledneUszeregowanieOperacji(inst);
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
	
	/**
	 * Dokonuje pełnej mutacji czyli kilku pojedyńczych mutacji
	 */
	public void pelnaMutacja() {
		for(int i=0; i<Math.ceil(Main.silaMutacji*Main.iloscZadan) ; i++ ){
			this.mutuj(this.maszyna_1);
			this.mutuj(this.maszyna_2);
		}
	}
	/**
	 * Dokonuje pojedyńczej mutacji na rozwiązaniu
	 * PRZESUWAMY OPERACJĘ O -5 w LEWO, A PÓŹNIEJ NA TEJ PODSTAWIE ODBUDOWUJEMY DRUGĄ MASZYNE
	 */
	void mutuj(List<Blok> maszyna){
		int PRZESUNIECIE=(int) (-1*(Math.ceil(Main.silaMutacji*Main.iloscZadan/2)+1));
		Random gen = new Random();
		int wylosowany_1=-1;
		//MUTACJA PIERWSZEJ MASZYNY
		while(true){
			wylosowany_1 = gen.nextInt(maszyna.size());
			if(maszyna.get(wylosowany_1) instanceof Podzadanie ) break;
		}
		Podzadanie p_1 = (Podzadanie) maszyna.get(wylosowany_1);
		//PRZESUNIECIE ZADANIA
		maszyna.remove(p_1);
		wylosowany_1+=PRZESUNIECIE;
		if(wylosowany_1 > maszyna.size()-1 ) wylosowany_1=wylosowany_1-PRZESUNIECIE;
		if(wylosowany_1 < 1 ) wylosowany_1=1;
		maszyna.add(wylosowany_1, p_1);
		
		p_1.czasStartu = maszyna.get(wylosowany_1-1).czasKonca;
		if(p_1.czasStartu<p_1.czasGotowosci) p_1.czasStartu=p_1.czasGotowosci;
		if( (p_1.numerOperacji==2) && (p_1.brat.czasKonca>p_1.czasStartu ) ) p_1.czasStartu = p_1.brat.czasKonca;
		p_1.czasKonca = p_1.czasStartu+p_1.czasTrwania;
		
		//System.out.println(wylosowany_1);
		for(int i=wylosowany_1 ; i < maszyna.size()-1 ; ++i ){		
			if( maszyna.get(i) instanceof Przerwa) continue;
			if( (maszyna.get(i).czasStartu >= maszyna.get(i-1).czasKonca) 
			&&(!(maszyna.get(i+1) instanceof Przerwa ) )) continue;
			
			Blok thisZ=maszyna.get(i);
			Blok prevZ=maszyna.get(i-1);
			if( maszyna.get(i+1) instanceof Przerwa ){
				Blok przerwa = maszyna.get(i+1);
				int diff = prevZ.czasKonca-thisZ.czasStartu;
				thisZ.czasStartu+=diff;
				if(thisZ.czasStartu < ((Podzadanie)thisZ).czasGotowosci ) thisZ.czasStartu=((Podzadanie)thisZ).czasGotowosci;
				thisZ.czasKonca=thisZ.czasStartu+thisZ.czasTrwania;
				
				
				if(thisZ.czasKonca>przerwa.czasStartu){
					//maszyna_1.remove(i);
					//maszyna_1.add(i+1, thisZ);
					thisZ.czasStartu=przerwa.czasKonca;
					if(thisZ.czasStartu < ((Podzadanie)thisZ).czasGotowosci ) thisZ.czasStartu=((Podzadanie)thisZ).czasGotowosci;
					thisZ.czasKonca=thisZ.czasStartu+thisZ.czasTrwania;
					//System.out.println("SWAP"+przerwa.czasKonca);
					Collections.swap(maszyna, i, i+1);
					i-=2; if(i==-1) i=0;
				}
				
			}
			else{
				int diff = prevZ.czasKonca-thisZ.czasStartu;
				if(diff<0) continue;
				thisZ.czasStartu+=diff;
				thisZ.czasKonca=thisZ.czasStartu+thisZ.czasTrwania;
			}
		}
		int lastIndex = maszyna.size()-1;
		int diff = maszyna.get(lastIndex-1).czasKonca-maszyna.get(lastIndex).czasStartu;
		if(diff>0){
			maszyna.get(lastIndex).czasStartu+=diff;
			maszyna.get(lastIndex).czasKonca=maszyna.get(lastIndex).czasStartu+maszyna.get(lastIndex).czasTrwania;
		}
		
		//ODBUDOWA DRUGICH OPERACJI
		this.zamienDrugieOperacje();
	
		//System.out.println("END");
		
		//System.out.println("Start");
		//System.out.println(":out");
		this.wypiszBledneUszeregowanieOperacji(this.instancjaUszeregowania);
		this.uzupelnijPrzerwy(this.maszyna_1);
		this.uzupelnijPrzerwy(this.maszyna_2);
		this.wypiszBledneUszeregowanieZadan(this.maszyna_1);
		this.wypiszBledneUszeregowanieZadan(maszyna_2);
		//System.out.println("END");
	}
	/**
	 * Bardzo ważna metoda uzywana przy mutacji
	 * Naprawia operacje drugie, które znalazły się przed pierwszymi na drodze mutacji
	 */
	public void zamienDrugieOperacje(){
		Instancja i = this.instancjaUszeregowania;
		for(Zadanie z : i.listaZadan){
			if(z.op1.czasKonca>z.op2.czasStartu){
				if(z.op2.maszyna==0){
					int index=this.maszyna_1.indexOf(z.op2);
					maszyna_1.remove(index);
					maszyna_1.add(z.op2);
					index=this.maszyna_1.indexOf(z.op2);
					z.op2.czasStartu=maszyna_1.get(index-1).czasKonca;
				}
				if(z.op2.maszyna==1){
					int index=this.maszyna_2.indexOf(z.op2);
					maszyna_2.remove(index);
					maszyna_2.add(z.op2);
					index=this.maszyna_2.indexOf(z.op2);
					z.op2.czasStartu=maszyna_2.get(index-1).czasKonca;
				}
				if(z.op1.czasKonca > z.op2.czasStartu) z.op2.czasStartu=z.op1.czasKonca;
				z.op2.czasKonca=z.op2.czasStartu+z.op2.czasTrwania;
			}
		}
	}
	
	/**
	 * Krzyzowanie zworzy JEDENO nowe uszeregowanie. Można by tworzyć 2, ale lepiej jest wywołać krzyżowanie 2 razy, poniewać złożonosć ta sama, a mniej kodu.
	 * @param u uszeregowanie z którym krzyzujemy
	 */
	void krzyzowanie(Uszeregowanie u ){
		
		
		
		this.wypiszBledneUszeregowanieOperacji(this.instancjaUszeregowania);
		this.wypiszBledneUszeregowanieZadan(this.maszyna_1);
		this.wypiszBledneUszeregowanieZadan(maszyna_2);
	}
	
	/**
	 * Metoda ta przegląda maszynę i sprawdza czy któreś zadanie nie może być zrobione chwile wcześniej (występuje przerwa w której maszyna nic nie robi)
	 * @param maszyna
	 */
	void uzupelnijPrzerwy(List<Blok> maszyna){
		for(int i =1 ; i<maszyna.size() ; i++){
			if(maszyna.get(i) instanceof Podzadanie ){
				Podzadanie thisZ = (Podzadanie) maszyna.get(i);
				Blok prevZ = maszyna.get(i-1);
				int diff=thisZ.czasStartu-prevZ.czasKonca;
				if(diff>0){
					//JEŻELI PIERWSZA OPERACJA
					if( thisZ.numerOperacji==1) {
						if (thisZ.czasGotowosci<=thisZ.czasStartu-diff) thisZ.czasStartu-=diff;
						else{
							diff=thisZ.czasStartu-thisZ.czasGotowosci;
							if(diff>0) thisZ.czasStartu-=diff;
						}
						thisZ.czasKonca=thisZ.czasStartu+thisZ.czasTrwania;
					}
					//JEŻELI DRUGA OPERACJA
					else{
						if( thisZ.brat.czasKonca<=thisZ.czasStartu-diff) thisZ.czasStartu-=diff;
						else{
							diff=thisZ.czasStartu-thisZ.brat.czasKonca;
							if(diff>0) thisZ.czasStartu-=diff;
						}
						thisZ.czasKonca=thisZ.czasStartu+thisZ.czasTrwania;
					}
				}
			}
		}
	}
	
	
	void wypiszUserzegowanie(){
		int idle_1=0; int idle_1_SUM=0;
		int idle_2=0; int idle_2_SUM=0;
		int maint_1=0; int maint_1_SUM=0;
		int maint_2=0; int maint_2_SUM=0;
		
		/**************************************************************
		 * DOPISAĆ WYPISYWANIE NAZWY I CZASU PIERWSZEGO 
		 */
		System.out.println("**** "+Main.numerWczytanejInstancji+" ****");
		System.out.println(this.sumaCzasow+","+Main.pierwszeRozwiazanie+";");
		
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
		//System.out.print(this.maszyna_1.get(this.maszyna_1.size()-1).czasTrwania);
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
			if (listaZadanDoSprawdzenia.get(i).op1.czasStartu < listaZadanDoSprawdzenia.get(i).op1.czasGotowosci){
				wypiszOperacje(listaZadanDoSprawdzenia.get(i).op1);
				System.out.println(" ");
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
