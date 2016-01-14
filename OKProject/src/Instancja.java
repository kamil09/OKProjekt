import java.io.PrintWriter;
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
		System.out.println("**** "+Main.numerWczytanejInstancji+" ****");
		System.out.println(listaZadan.size());
		for (int i=0;i< listaZadan.size(); i++){
			Zadanie z = listaZadan.get(i);
			System.out.println(z.op1.czasTrwania+"; "+z.op2.czasTrwania+"; "+ (z.op1.maszyna+1) +"; "+(z.op2.maszyna+1)+"; "+ z.op1.czasGotowosci);
		}
		System.out.println(listaPrzerw.size());
		for (int i=0; i < listaPrzerw.size() ; i++ ){
			Przerwa p = listaPrzerw.get(i);
			System.out.println(p.id+"; 1; "+p.czasTrwania+"; "+p.czasStartu);
		}
	}
	public void wypiszInstanje(PrintWriter file){
		file.println("**** "+Main.numerWczytanejInstancji+" ****");
		file.println(listaZadan.size());
		for (int i=0;i< listaZadan.size(); i++){
			Zadanie z = listaZadan.get(i);
			file.println(z.op1.czasTrwania+"; "+z.op2.czasTrwania+"; "+ (z.op1.maszyna+1) +"; "+(z.op2.maszyna+1)+"; "+ z.op1.czasGotowosci);
		}
		file.println(listaPrzerw.size());
		for (int i=0; i < listaPrzerw.size() ; i++ ){
			Przerwa p = listaPrzerw.get(i);
			file.println(p.id+"; 1; "+p.czasTrwania+"; "+p.czasStartu);
		}
	}
	
}
