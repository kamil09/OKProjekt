import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Instancja jest zbiorem zadań oraz przerw
 * @author Kamil Piotrowski
 *
 */
public class Instancja implements Serializable{
	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Lista zadań
	 */
	public List<Zadanie> listaZadan = new ArrayList<Zadanie>();
	/**
	 * Lista przerw
	 */
	public List<Przerwa> listaPrzerw = new ArrayList<Przerwa>();
	//Używane przy losowaniu przerw
	public static int dlugoscInstancji=0;
	
	public Instancja(){
		for(int i=0 ; i< Main.iloscZadan ; i++) {
			this.listaZadan.add(new Zadanie());
			dlugoscInstancji+=listaZadan.get(i).op1.czasTrwania;
		}
		//System.out.println("tworzenie przerw");
		for(int i=0; i< (Main.procentPrzerw*Main.iloscZadan)+2 ; i++ )
			this.listaPrzerw.add(new Przerwa(listaPrzerw));
		//System.out.println("koniec tworzenia przerw");
		Collections.sort(listaPrzerw);
		Collections.sort(listaZadan);
		
		//wypiszInstanje();
	}
	
	public Instancja(Instancja inst){
		for(Przerwa p : inst.listaPrzerw) this.listaPrzerw.add( new Przerwa(p) );
		for(Zadanie z : inst.listaZadan) this.listaZadan.add( new Zadanie(z) );	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * METODA TESTOWA NIE UŻYWANA W ALGORYTMIE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public void wypiszInstanje(){
		System.out.println("ZADANIA:");
		for (int i=0;i< listaZadan.size(); i++){
			Zadanie z = listaZadan.get(i);
			System.out.println("ID: "+z.id+" Czas G: "+z.op1.czasGotowosci+"  Operacje: "+z.op1.maszyna+"/"+z.op1.czasTrwania+"  |  "+z.op2.maszyna+"/"+z.op2.czasTrwania  );
		}
		System.out.println("Dlugość: "+ dlugoscInstancji );
		System.out.println("PRZERWY:");
		for (int i=0; i < listaPrzerw.size() ; i++ ){
			Przerwa p = listaPrzerw.get(i);
			System.out.println("ID: "+p.id+"  Czas rozpoczęcia: "+p.czasStartu+"   Czas konca:  "+ p.czasKonca+ " Czas trwania: "+p.czasTrwania);
		}
		
	}
}
