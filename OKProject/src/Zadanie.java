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
	 * operacja op1 zawsze przed operacją op2
	 */
	/**
	 * Czas gotowości dla acalego zadania
	 */
	public int czasGotowosci=0;
	
	public Podzadanie op1;
	
	public Podzadanie op2;
	
	public int id;
	
	
	public Zadanie(){
		Random gen = new Random();
		int i = gen.nextInt(10);
		i=i%2;
		this.op1=new Podzadanie(i);
		this.op2=new Podzadanie(1-i);
		this.op1.brat=op2;
		this.op2.brat=op1;
		this.id=(++Zadanie.IDgen);
		this.czasGotowosci=gen.nextInt(Main.maxG);
	}
}
