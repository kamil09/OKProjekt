/**
 * Klasa po której dziedziczy podzadanie i przerwa
 * @author no-one
 *
 */
public class Blok {

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
	
	Blok(){
	}
	
	Blok(int c, int k, int t ){
		this.czasStartu=c;
		this.czasKonca=k;
		this.czasStartu=t;
	}
	
}
