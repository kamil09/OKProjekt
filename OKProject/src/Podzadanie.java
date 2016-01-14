import java.io.Serializable;
import java.util.Random;

/**
 * Jedno z 2 operacji należących do zadania
 * @author Kamil Piotrowski
 *
 */
public class Podzadanie extends Blok implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Maszyna na której jest wykonywane zadanie
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
	
	public Podzadanie(int ma, int num, int id){
		Random generator = new Random();
		this.maszyna=ma;
		this.numerOperacji = num;
		this.czasTrwania=generator.nextInt(Main.maxZ-Main.minZ)+Main.minZ;
		this.wykonane=false;
		this.id=id;
	}

	public Podzadanie(Podzadanie op) {
		this.maszyna=op.maszyna;
		this.czasStartu=op.czasStartu;
		this.numerOperacji=op.numerOperacji;
		this.czasTrwania=op.czasTrwania;
		this.czasGotowosci=op.czasGotowosci;
		this.wykonane=op.wykonane;
		this.czasKonca=op.czasKonca;
		this.id=op.id;
	}

}