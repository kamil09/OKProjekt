import java.util.Random;

/**
 * Zadanie
 * @author Kamil Piotrowski
 *
 */
public class Zadanie implements Comparable<Zadanie>{
	
	public static int IDgen=-1;
	/**
	 * każde zadanie składa się z 2 części.
	 * każda z nich jest wykonywana na innej maszynie, nie mogą wykonywać się jednocześnie
	 * operacja op1 zawsze przed operacją op2
	 */
	
	public Podzadanie op1;
	
	public Podzadanie op2;
	
	public int id;
	
	
	public Zadanie(){
		Random gen = new Random();
		int i = gen.nextInt(10);
		i=i%2;
		this.op1=new Podzadanie(i,1);
		this.op2=new Podzadanie(1-i,2);
		this.op1.brat=this.op2;
		this.op2.brat=this.op1;
		this.id=(++Zadanie.IDgen);
		
		this.op1.czasGotowosci=gen.nextInt(Main.maxG);
		this.op2.czasGotowosci=this.op1.czasGotowosci;
	}
	
	public Zadanie(Zadanie z) {
		this.id=z.id;
		this.op1 = new Podzadanie (z.op1);
		this.op2 = new Podzadanie (z.op2);
		this.op1.brat=this.op2;
		this.op2.brat=this.op1;
	}

	@Override
	public int compareTo(Zadanie o) {
		return this.op1.czasGotowosci-o.op1.czasGotowosci;
	}
}
