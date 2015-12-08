import java.util.List;
import java.util.Random;

public class Przerwa extends Blok{
	
	public static int idGen=-1;
	
	public Przerwa(List<Przerwa> listaPrzerw){
		Random gen = new Random();
		this.id=++Przerwa.idGen;
		this.czasTrwania=gen.nextInt(Main.maxP-Main.minP)+Main.minP;
		Instancja.dlugoscInstancji+=this.czasTrwania;
		this.czasStartu=gen.nextInt(Instancja.dlugoscInstancji);
		
		while(true){
			boolean OK=true;
			for(Przerwa pr : listaPrzerw ){
				if( (this.czasStartu>=pr.czasStartu) && (this.czasStartu<=pr.czasStartu+pr.czasTrwania) ) OK=false;
				if( (this.czasStartu+this.czasTrwania<=pr.czasStartu+pr.czasTrwania) && (this.czasStartu+this.czasTrwania)>=(pr.czasStartu) ) OK=false;
				if( (pr.czasStartu>=this.czasStartu) && (pr.czasStartu<=this.czasStartu+this.czasTrwania) ) OK=false;	
			}
			if(OK) break;
			else{
//				this.czasStartu+=(this.czasTrwania/2)+1;
				this.czasStartu+=this.czasTrwania+1;
			}
		}
		this.czasKonca=this.czasStartu+this.czasTrwania;
	}

	public Przerwa(Przerwa p) {
		this.id=p.id;
		this.czasStartu=p.czasStartu;
		this.czasKonca=p.czasKonca;
		this.czasTrwania=p.czasTrwania;
	}
}
