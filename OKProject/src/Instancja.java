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
	
	public Instancja(){
		for(int i=0 ; i< Main.iloscZadan ; i++){
			listaZadan.add(new Zadanie());
			
		}
		
		wypiszInstanje();
	}
	
	public void wypiszInstanje(){
		System.out.println("ZADANIA:");
		for (int i=0;i< Main.iloscZadan; i++){
			Zadanie z = listaZadan.get(i);
			System.out.println("ID: "+z.id+" Czas G: "+z.czasGotowosci+"  Operacje: "+z.A.maszyna+"/"+z.A.czasTrwania+"  |  "+z.B.maszyna+"/"+z.B.czasTrwania  );
		}
		System.out.println("PRZERWY:");
		
		
	}
}
