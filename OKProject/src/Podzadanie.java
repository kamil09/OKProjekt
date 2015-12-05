import java.util.Random;

/**
 * Jedno z 2 operacji należących do zadania
 * @author Kamil Piotrowski
 *
 */
public class Podzadanie extends Blok{
	
	/**
	 * Maszyna na której jes wykonywane zadanie
	 */
	public final int maszyna;
	/**
	 * Czy zadanie zostało wykonane
	 */
	public boolean wykonane=false;
	/**
	 * Drugie podzadanie
	 * Aby łatwiej było sprawdzać stan drugiej operacji
	 */
	public Podzadanie brat=null;
	/**
	 * Numer operacji 1 lub 2;
	 */
	public int numerOperacji; 
	/**
	 * Czas gotowości dla acalego zadania
	 */
	public int czasGotowosci=0;
	
	public Podzadanie(int ma, int num){
		Random generator = new Random();
		this.maszyna=ma;
		this.numerOperacji = num;
		this.czasTrwania=generator.nextInt(Main.maxZ-Main.minZ)+Main.minZ;
		this.wykonane=false;
	}

	public Podzadanie(Podzadanie op) {
		this.maszyna=op.maszyna;
		this.numerOperacji=op.numerOperacji;
		this.czasTrwania=op.czasTrwania;
		this.czasGotowosci=op.czasGotowosci;
		this.wykonane=false;
	}

}