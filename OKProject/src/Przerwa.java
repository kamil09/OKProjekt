import java.util.Random;

public class Przerwa extends Blok{

	/**
	 * ID
	 */
	public final int id;
	
	public static int idGen=-1;
	
	public Przerwa(){
		Random gen = new Random();
		this.id=++Przerwa.idGen;
		this.czasTrwania=gen.nextInt(Main.maxP-Main.minP)+Main.minP;
		Instancja.dlugoscInstancji+=this.czasTrwania;
		this.czasStartu=gen.nextInt(Instancja.dlugoscInstancji);
		
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
		this.czasKonca=this.czasStartu+this.czasTrwania;
	}
}
