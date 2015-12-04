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
	
	public Podzadanie(int ma){
		Random generator = new Random();
		this.maszyna=ma;
		this.czasTrwania=generator.nextInt(Main.maxZ-Main.minZ)+Main.minZ;
		this.wykonane=false;
	}

}