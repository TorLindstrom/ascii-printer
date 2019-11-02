import java.io.*;
import java.util.*;
import javax.swing.*;

public class asciiPrinter{
	public static void main(String [] arg)throws IOException{
		
		Scanner in1 = new Scanner(System.in);
			
		//användar loop
		while (true){
				
			String val;
				
			//Frågar om användaren vill skriva till fil, eller ut till kommandofönstret från fil
			System.out.println("Vill du skriva ny fil, eller skriva ut en existerande fil?\nSkriv ny/ut, eller avbryt");
				
			//låter användaren skriva om
			while (true){
				
				val = in1.next().toLowerCase();
				
				//validerar indata
				if (!val.equals("ny")&&!val.equals("ut")&&!val.equals("avbryt")){
					System.out.println("Snälla skriv \"ny\" eller \"ut\"");
				}
				else if (val.equals("avbryt")){
					System.out.println("Hejdå");
					System.exit(0);
				}
				else
					break;
					
			}
				
			//om ny fil
			if (val.equals("ny")){
					
				PrintWriter out;
					
				while (true){
					try{
						//fråga om vilken fil
						System.out.print("Namnge filen tack, bara själva namnet: ");
						System.out.flush();
						//tar emot namnet
						String fil = in1.next();
							
						fil+=".txt";
							
						//utström
						out = new PrintWriter(new BufferedWriter(new FileWriter(fil)));
						break;
					}
					catch (FileNotFoundException e1){
						System.out.println("Snälla välj ett tillåtet filnamn");
					}
				}
					
				//fält med objekt av alla klasser
				fisk[] katalog = {new utrymme(), new liten(), new val(), new blub(), new haj(), new rund()};
					
				System.out.println();
					
				for (int i=0;i<katalog.length;i++){
					katalog[i].rita();
				}
					
				System.out.println("Skriv: namnet på en utav fiskarna, 'utrymme', 'rad', 'klar', eller 'avsluta'");
					
				//klar flagga
				boolean klar=false;
					
				//fil loop
				while (!klar){
						
					//skapar ett helt band att sedan printa till fil
					String[] preRender = {" "," "," "," "," "};
					boolean attSkriva=false;
						
					//rad loop
					while (true){
							
						System.out.print("Input:");
						System.out.flush();
						
						//tar input
						String action = in1.next().toLowerCase();
							
						if (action.equals("avsluta"))	
							System.exit(0);
							
						if (action.equals("klar")){
							klar=true;
							break;
						}
							
						if (action.equals("rad")){
							out.println("");
							break;
						}
							
						//flagga som antar att input inte är valid
						boolean valid=false;
						
						//kollar om input är en fisk
						for (int i=0;i<katalog.length;i++){
								
							if (katalog[i].getNamn().toLowerCase().equals(action)){
									
								//om ett elements namn stämmer överens med input så är den valid
								valid=true;
									
								if (katalog[i].getNamn().toLowerCase().equals("utrymme")){
										
									while (true){
										try{
											System.out.print("Hur stort utrymme?:");
											System.out.flush();
											katalog[i].sättLängd(Integer.parseInt(in1.next()));
											break;
										}
										catch (NumberFormatException e2){
											System.out.println("Snälla skriv ett heltal");
										}
											
									}
								}
									
								katalog[i].addera(preRender);
								attSkriva=true;
								break;
							}
						}
							
						//kollar om inputen är valid
						if (valid==false){
							System.out.println("Snälla skriv namnet på en utav fiskarna, eller ett tekniskt kommando");
						}
						
					} //end rad loop
						
					if (!klar)
						System.out.println("Skriver raden till fil");
						
					if (attSkriva){
						//skriver ut raden
						for (String ritaUt:preRender){
							out.println(ritaUt);
						}
					}
						
				} //end fil loop
					
				//stänger utström
				System.out.println("Stänger utsrömen");
				out.close();
					
			} //end if fil
				
			//om skriv ut
			if (val.equals("ut")){
					
				while (true){
						
					System.out.println("Välj en fil");
						
					//väljer fil
					JFileChooser väljare = new JFileChooser(System.getProperty("user.dir"));
						
					int resultat = väljare.showOpenDialog(null);
						
					if (resultat!=JFileChooser.APPROVE_OPTION){
						System.out.println("Ingen fil valdes, avbryter programmet");
						System.exit(0);
					}
						
					String vald = väljare.getSelectedFile().getAbsolutePath();
						
					if (vald.substring(vald.lastIndexOf('.')).equals(".txt")){
							
						//skapar inström
						BufferedReader in = new BufferedReader(new FileReader(vald));
							
						//printar filen till kommandofönster
						while (true){
								
							String rad = in.readLine();
								
							if (rad==null)
								break;
								
							System.out.println(rad);
								
						}
						//bryter fil väljar loopen
						break;
					}
						
					//om inte en fisk fil så ber den att välja en ny fil
					else {
						System.out.println("Snälla välj en 'txt' fil");
					}
						
				} //end fil väljar loop
					
			}//end ut if
		
		} //end användar loop
	} //end main
} //end class





//superklass
abstract class fisk{
	
	//sätts av konstruktörerna för varje subklass
	protected String namn;
	protected int längd;
	protected String[][] ascii;
	
	//metod för att skriva ut till prerender
	protected void addera(String[] preRender){
		
		for (int i=0;i<ascii.length;i++){
			
			preRender[i]+=ascii[i][0];
			
		}
		//skapar en filler för de rader som inte används av det aktuella objektet
		String tom="";
		for (int i=0;i<längd;i++){
			tom+=" ";
		}
		//fyller ut
		for (int i=4;i>=ascii.length;i--){
			preRender[i]+=tom;
		}
		
	}
	
	//metod för att skriva ut bara denna fisk
	protected void rita(){
		
		System.out.println(namn);
		
		for (int i=0;i<ascii.length;i++){
			System.out.println(ascii[i][0]);
		} 
		System.out.println();
		
	}
	
	protected String getNamn(){
		return namn;
	}
	
	//används bara av utrymmes klassen, då referensen i main är utav klassen fisk så detta krävs för att det ska funka
	protected void sättLängd(int tal){
		längd=tal;
	}
	
}

//utrymme för filler
class utrymme extends fisk{
	
	utrymme(){
		namn = "utrymme";
		ascii = asciiTemp;
	}
	
	@Override
	public void rita(){
		System.out.println("Blankt utrymme\n");
	}
		
	String[][] asciiTemp = new String[0][0];
	
}

//liten
class liten extends fisk{
	
	//konstruktor som sätter 'fisk' variblerna till 'liten' värden
	liten(){
		namn = "Liten";
		längd = 4;
		ascii = asciiTemp;
	}
	//flerdimensionellt fält för ascii
	private String[][] asciiTemp ={{"  _ "},
								   {"><_>"}};
}

//val
class val extends fisk{
	
	val(){
		namn = "Val";
		längd = 8;
		ascii = asciiTemp;
	}
	//flerdimensionellt fält för ascii
	private String[][] asciiTemp ={{" __v_   "},
							   	   {"(____\\/{"}};
}

//blub
class blub extends fisk{
	
	blub(){
		namn = "Blub";
		längd = 19;
		ascii = asciiTemp;
	}
	
	private String[][] asciiTemp ={{"               O  o"},
								   {"          _\\_   o  "},
								   {">('>   \\\\/  o\\ .   "},
								   {"       //\\___=     "},
								   {"          ''       "}};
	
}

//haj!
class haj extends fisk{
	
	haj(){
		namn = "Haj";
		längd = 13;
		ascii = asciiTemp;
	}
	
	private String[][] asciiTemp ={{"      .      "},
								   {"\\_____)\\_____"},
								   {"/--v____ __`<"},
								   {"        )/   "},
								   {"        '    "}};
	
}

//rund
class rund extends fisk{
	
	rund(){
		namn = "Rund";
		längd = 5;
		ascii = asciiTemp;
	}
	
	private String[][] asciiTemp ={{" ,-, "},
								   {"('_)<"},
								   {" `-` "}};
	
}