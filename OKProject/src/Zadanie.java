import java.util.Random;

/**
 * Zadanie
 * @author Kamil Piotrowski
 *
 */
public class Zadanie {
	
	public static int IDgen=-1;
	/**
	 * każde zadanie składa się z 2 części.
	 * każda z nich jest wykonywana na innej maszynie, nie mogą wykonywać się jednocześnie
	 * operacja A zawsze przed operacją B
	 */
	/**
	 * Czas gotowości dla acalego zadania
	 */
	public int czasGotowosci=0;
	
	public Podzadanie A;
	
	public Podzadanie B;
	
	public int id;
	
	
	public Zadanie(){
		Random gen = new Random();
		int i = gen.nextInt(10);
		i=i%2;
		this.A=new Podzadanie(i);
		this.B=new Podzadanie(1-i);
		this.A.brat=B;
		this.B.brat=A;
		this.id=(++Zadanie.IDgen);
		this.czasGotowosci=gen.nextInt(Main.maxG);
	}
}
