import java.util.Random;

public class Przerwa {

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
	public int czasTrwania=0;
	/**
	 * ID
	 */
	public int id=0;
	
	public static int idGen=-1;
	
	public Przerwa(){
		Random gen = new Random();
		this.id=++Przerwa.idGen;
		this.czasTrwania=gen.nextInt(Main.maxP-Main.minP)+Main.minP;
		this.czasStartu=(int) (gen.nextInt(Instancja.dlugoscInstancji)*Main.procentPrzerw);
		
		while(true){
			boolean OK=true;
			for(Przerwa pr : Instancja.listaPrzerw ){
				if( (this.czasStartu>=pr.czasStartu) && (this.czasStartu<=pr.czasStartu+pr.czasTrwania) ) OK=false;
				if( (this.czasStartu+this.czasTrwania<=pr.czasStartu+pr.czasTrwania) && (this.czasStartu+this.czasTrwania)>=(pr.czasStartu) ) OK=false;
				if( (pr.czasStartu>=this.czasStartu) && (pr.czasStartu<=this.czasStartu+this.czasTrwania) ) OK=false;	
			}
			if(OK) break;
			else{
				this.czasStartu+=this.czasTrwania/2;
			}
		}
		
		
	}
}
