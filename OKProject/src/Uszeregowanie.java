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
		this.wypiszUserzegowanie();
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
						
						//JESLI OPERACJA JEST GOTOWA I ZADANIE PIERWSZE ZOSTAŁO JUŻ WYKONANE
						if( (p.czasGotowosci<=maszyna.get(i).czasKonca) && (p.brat.czasKonca<=maszyna.get(i).czasKonca) ){
							p.czasStartu=maszyna.get(i).czasKonca;
							p.czasKonca=p.czasStartu+p.czasTrwania;
							maszyna.add(i+1, p);
							break;
						}
						//JESLI OPERACJA JEST GOTOWA, ALE ZADANIE PIERWSZE SIĘ NIE WYKONAŁO
						if( (p.czasGotowosci<=maszyna.get(i).czasKonca) && (p.brat.czasKonca>maszyna.get(i).czasKonca) ){
							miejsce=maszyna.get(i+1).czasStartu-p.brat.czasKonca;
							//SPRAWDZAMY CZY OPERACJA 1 SKOŃCZY SIĘ W TYM PRZEDZIALE I SIĘ ZMIEŚCIMY
							if(miejsce>=p.czasTrwania){
								p.czasStartu=p.czasGotowosci;
								p.czasKonca=p.czasStartu+p.czasTrwania;
								maszyna.add(i+1,p);
								break;
							}
						}
						//MOZNA BY JESZCZE ROZWAŻYĆ ŻE NIE JEST GOTOWA ALE OP1 SIĘ WYKONAŁA LUB NIE WYKONAŁA
					}
				}
				//JESTEŚMY NA KOŃCU, OPERACJA 1 NA PEWNO SIĘ WYKONAŁA!
				else{
					if( p.czasGotowosci<=maszyna.get(i).czasKonca ) p.czasStartu=maszyna.get(i).czasKonca;
					else p.czasStartu=p.czasGotowosci;
					p.czasKonca=p.czasStartu+p.czasTrwania;
					maszyna.add(p);
					break;
				}
				
			}
			
		}
		p.wykonane=true;
	}
	
	void wypiszUserzegowanie(){
		System.out.print("M1: ");
		for(int i =0 ;i< this.maszyna_1.size()-1 ; i++ ){
			System.out.print(this.maszyna_1.get(i).czasTrwania+" ; ");
			if(this.maszyna_1.get(i+1).czasStartu!=this.maszyna_1.get(i).czasKonca)
				System.out.print("bez"+(this.maszyna_1.get(i+1).czasStartu-this.maszyna_1.get(i).czasKonca)+" ; ");
			
		}
		System.out.print(this.maszyna_1.get(this.maszyna_1.size()-1).czasTrwania);
		System.out.println(" ");
		System.out.print("M2: ");
		for(int i =0 ;i< this.maszyna_2.size()-1 ; i++ ){
			System.out.print(this.maszyna_2.get(i).czasTrwania+" ; ");
			if(this.maszyna_2.get(i+1).czasStartu!=this.maszyna_2.get(i).czasKonca)
				System.out.print("bez"+(this.maszyna_2.get(i+1).czasStartu-this.maszyna_2.get(i).czasKonca)+" ; ");

		}
		System.out.print(this.maszyna_2.get(this.maszyna_2.size()-1).czasTrwania);
		System.out.println(" ");
	}
	
}
