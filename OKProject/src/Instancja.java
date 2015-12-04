import java.io.Serializable;
import java.util.ArrayList;
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
	public static List<Zadanie> listaZadan = new ArrayList<Zadanie>();
	/**
	 * Lista przerw
	 */
	public static List<Przerwa> listaPrzerw = new ArrayList<Przerwa>();
	//Używane przy losowaniu przerw
	public static int dlugoscInstancji=0;
	
	public Instancja(){
		for(int i=0 ; i< Main.iloscZadan ; i++) {
			listaZadan.add(new Zadanie());
			dlugoscInstancji+=listaZadan.get(i).op1.czasTrwania;
		}
		for(int i=0; i< (Main.procentPrzerw*Main.iloscZadan) ; i++ )
			listaPrzerw.add(new Przerwa());
		wypiszInstanje();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * METODA TESTOWA NIE UŻYWANA W ALGORYTMIE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public void wypiszInstanje(){
		System.out.println("ZADANIA:");
		for (int i=0;i< listaZadan.size(); i++){
			Zadanie z = listaZadan.get(i);
			System.out.println("ID: "+z.id+" Czas G: "+z.czasGotowosci+"  Operacje: "+z.op1.maszyna+"/"+z.op1.czasTrwania+"  |  "+z.op2.maszyna+"/"+z.op2.czasTrwania  );
		}
		System.out.println("Dlugość: "+ dlugoscInstancji );
		System.out.println("PRZERWY:");
		for (int i=0; i < listaPrzerw.size() ; i++ ){
			Przerwa p = listaPrzerw.get(i);
			System.out.println("ID: "+p.id+"  Czas rozpoczęcia: "+p.czasStartu+"   Czas konca:  "+ p.czasKonca+ " Czas trwania: "+p.czasTrwania);
		}
		
	}
}
