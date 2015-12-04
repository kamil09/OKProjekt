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

	public List<Zadanie> listaZadan = new ArrayList<Zadanie>();
	public List<Przerwa> listaPrzerw = new ArrayList<Przerwa>();
	//Używane przy losowaniue przerw
	public static int dlugoscInstancji=0;
	
	public Instancja(){
		for(int i=0 ; i< Main.iloscZadan ; i++) {
			listaZadan.add(new Zadanie());
			dlugoscInstancji+=listaZadan.get(i).A.czasTrwania;
		}
		for(int i=0; i< (Main.procentPrzerw*Main.iloscZadan) ; i++ )
			listaPrzerw.add(new Przerwa());
		wypiszInstanje();
	}
	
	public void wypiszInstanje(){
		System.out.println("ZADANIA:");
		for (int i=0;i< this.listaZadan.size(); i++){
			Zadanie z = listaZadan.get(i);
			System.out.println("ID: "+z.id+" Czas G: "+z.czasGotowosci+"  Operacje: "+z.A.maszyna+"/"+z.A.czasTrwania+"  |  "+z.B.maszyna+"/"+z.B.czasTrwania  );
		}
		System.out.println("Dlugość: "+ dlugoscInstancji );
		System.out.println("PRZERWY:");
		for (int i=0; i < this.listaPrzerw.size() ; i++ ){
			Przerwa p = this.listaPrzerw.get(i);
			System.out.println("ID: "+p.id+"  Czas rozpoczęcia: "+p.czasStartu+" Czas trwania: "+p.czasTrwania);
		}
		
	}
}
