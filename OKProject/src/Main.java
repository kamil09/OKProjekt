import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
/**
 * Problem 3
	Job shop, liczba maszyn m=2, operacje typu non-preemptive, 
	dla pierwszej maszyny od k=2 do n/2 okresów przestoju o losowym czasie rozpoczęcia i trwania, 
	różne i losowe czasy gotowości dla wszystkich zadań
	minimalizacja sumy czasów zakończenia wszystkich operacji, liczba zadań n.
 * @author Kamil Piotrowski ; Michał Lewiński
 *
 */


public class Main extends Thread{

	/**
	 * Tryb działania programu
	 * 1- normalny
	 * 0- wygenerowanie instancji i jej zapisanie, wymagana nazwa pliku
	 */
	public static int tryb=1;
	
	
//*********************USTAWIENIA ALGORYTMU************************************8
	/**
	 * Czas po którym zatrzymujemy program w sekundach
	 */
	public static double czas=8;
	/**
	 * Ilość iteracji w programie
	 */
	public static int iteracje=0;
	/**
	 * Startowa populacja np 50
	 */
	public static int populacjaStartowa=50;
	/**
	 * Populacja do której rozszerzamy, przynajmniej 3,4 razy większa od startowej
	 */
	public static int populacjaEwolucji=300;
	/**
	 * Procent mutacji
	 */
	public static double iloscMutacji=0.33;
	/**
	 * Procent Krzyzowania
	 */
	public static double iloscKrzyzowania=0.33;
	/**
	 * PRZYKŁAD:
	 * 40% MUTACJA , 45% KRZYZOWANIE => 15% MUTACJA i KRZYZOWANIE  
	 */
	/**
	 * Początkowa siła mutacji
	 * Siła mutacj decyduje o ilości pojedynczych mutacji na uszeregowaniu np sila=0.2 , N=50 => najmocniejsza mutacja to 10 pojedynczych 
	 * Mutacja działa na zasadzie przesunięcia w lewo, od jej siły zależy także wielkość przesunięcia.
	 * silaMutacji/=1.5 co 1 sek.
	 */
	public static double silaMutacji=0.5;
	
//*********************USTAWIENIA GENERATORA INSTANCJI**************************************
	/**
	 * Ilość zadań do wygenerowania w instacji
	 */
	public static int iloscZadan=50;
	/**
	 * Ilosć przerw do wygenerowania instancji jako procent ilości zadan <1
	 * +2 (jesli procentPrzerw == 0 ) to mamy 2 przerwy
	 */
	public static double procentPrzerw=0.2;
	/**
	 * Minimalna długość zadania
	 */
	public static int minZ=1;
	/**
	 * Maksymalna dłyugosć zadania
	 */
	public static int maxZ=50;
	/**
	 * Minimalna długosć przerwy
	 */
	public static int minP=1;
	/**
	 * maksymalna długość przerwy
	 */
	public static int maxP=50;
	/**
	 * maksymalny czas po którym zadanie uzyskuje stan gotowości (może się wykonywać)
	 */
	public static int maxG=100;
	
	//*****************************INNE PARAMETRY***************************************
	
	/**
	 * Wygenerowana lub wczytana instancja problrmu czyli zapis wszystkich zadań, i przerw
	 */
	public static Instancja instancja;
	/**
	 * Najlepsze rozwiązanie z wylosowanych wieprwszych uszeregowań (przed całym algorytmem)
	 */
	public static int pierwszeRozwiazanie=-1;
	/**
	 * Nagłówek w pliku wynikowym
	 */
	public static String numerWczytanejInstancji="1";
	/**
	 * Plik z zapisaną instancją
	 */
	public static String serialFileName="out.txt";
	/**
	 * Czy wygenerować instancja
	 * Domyślnie true, jeśli podano plik zmieniamy na false
	 */
	public static boolean wygenerowacInstancje=true; 
	/**
	 * Czy sprawdzamy poprawność uszeregowań
	 */
	public static boolean weryfikacja=false;
	/**
	 * 0 - wypisuje tylko wynik
	 * 1 - wypisuje całe rozwiązanie
	 */
	public static int wyjscie=0;
	/**
	 * Czy zapisać do pliku, nieużywane  w tesowaniu
	 * Tylko w celu pokazowym.
	 */
	public static int zapisDoPliki=1;
	
	/**
	 * MAIN
	 * @param args parametry programu
	 * @throws IOException Wyjątek
	 * @throws FileNotFoundException Wyjątek brak pliku
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		wczytajWartosci(args);
		if(tryb==1){
			if (wygenerowacInstancje) instancja=new Instancja();
			else{
				ObjectInputStream we = new ObjectInputStream(new FileInputStream(serialFileName));
				instancja = (Instancja)we.readObject();
				we.close();
				//String[] par = Main.serialFileName.split("[.]");
				//if( par[0] != null )
				//	Main.numerWczytanejInstancji=par[0];
			}
			
			Algorytm algorytm=new Algorytm();
			//System.out.println("START");
			algorytm.run();
			if(zapisDoPliki == 1){
				PrintWriter instOut = new PrintWriter("przyklad.in");
				instancja.wypiszInstanje(instOut);
				instOut.close();
			}
		}
		else{
			instancja=new Instancja();
			ObjectOutputStream wy = new ObjectOutputStream(new FileOutputStream(serialFileName));
			wy.writeObject(instancja);
			instancja.wypiszInstanje();
			wy.close();
		}
		
	}
	
	/**
	 * Ustawia odpowiednie wartości z argumentóww
	 * @param args ardumenty podane do programu
	 */
	public static void wczytajWartosci(String[] args){
		for(int i=0; i < args.length-1 ; i++){
			switch (args[i]){
				case "-TR0" :
					serialFileName=args[i+1];
					tryb=0;
					//System.out.println(serialFileName);
					i++;
					break;
				case "-TR1" :
					break;
				case "-PS" :
					populacjaStartowa=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-PE" :
					populacjaEwolucji=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-IM" :
					iloscMutacji=Double.parseDouble(args[i+1]);
					i++;
					break;
				case "-IK" :
					iloscKrzyzowania=Double.parseDouble(args[i+1]);
					i++;
					break;
				case "-SM" :
					silaMutacji=Double.parseDouble(args[i+1]);
					i++;
					break;
				case "-T" :
					czas=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-SF" :
					serialFileName=args[i+1];
					wygenerowacInstancje=false;
					i++;
					break;
				case "-NI" :
					numerWczytanejInstancji=args[i+1];
					i++;
					break;
				case "-IZ" :
					iloscZadan=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-PP" :
					procentPrzerw=Double.parseDouble(args[i+1]);
					i++;
					break;
				case "-minZ" :
					minZ=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-maxZ" :
					maxZ=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-minP" :
					minP=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-maxP" :
					maxP=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-maxG" :
					maxG=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-IT"	:
					iteracje=Integer.parseInt(args[i+1]);
					i++;
					break;
				case "-ALL":
					wyjscie=1;
					break;
			}	
		}
	}
}
