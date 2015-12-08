import java.io.Serializable;

/**
 * Klasa po której dziedziczy podzadanie i przerwa
 * @author no-one
 *
 */
public class Blok implements Comparable<Blok>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Czas startu zadania
	 */
	public int czasStartu=-1;
	/**
	 * Czas konca zadania
	 */
	public int czasKonca=-1;
	/**
	 * Czas trwania zadania
	 */
	public int czasTrwania;
	/**
	 * id
	 */
	public int id;
	
	Blok(){
	}
	
	Blok(int c, int k, int t ){
		this.czasStartu=c;
		this.czasKonca=k;
		this.czasStartu=t;
	}

	@Override
	public int compareTo(Blok o) {
		return this.czasStartu-o.czasStartu;
	}
	
}
