package model;

import java.util.ArrayList;
import java.util.List;


public class BazaPredmet {

	//singlton
		// sablon koji nam omogucava da imamo jednu instancu necega, mi zelimo da imamo jednu instancu profesora, jer ne zelimo da neko drugi sa vise strana to menja 
		private static BazaPredmet instance =null;
		
		public static BazaPredmet getInstance() {
			if(instance==null)
				instance=new BazaPredmet();
			return instance;
			
		}
		
		private long  brojac=0;
		
		private List<Predmet> predmeti;
		private List<String> kolone;

		
		
		
		private BazaPredmet()
		{
		
			this.kolone = new ArrayList<String>();
			initPredmet();
			this.kolone.add("SIFRA PREDMETA");
			this.kolone.add("NAZIV");
			this.kolone.add("ESPB");
			this.kolone.add("GODINA IZVODJENJA");
			this.kolone.add("SEMESTAR IZVODJENJA");
		}

		private void initPredmet() {
			
			this.predmeti = new ArrayList<Predmet>();
		     Semestar s=Semestar.LETNJI;
			predmeti.add(new Predmet("EE123","Verovatnoca",9,6,s));
		}
		

		public long getBrojac() {
			return brojac;
		}


		public void setBrojac(long brojac) {
			this.brojac = brojac;
		}


		public List<Predmet> getPredmeti() {
			return predmeti;
		}


		public void setPredmet(List<Predmet> predmeti) {
			this.predmeti = predmeti;
		}


		public List<String> getKolone() {
			return kolone;
		}

		public int getColumnCount() {
			return 5;
		}
		public void setKolone(List<String> kolone) {
			this.kolone = kolone;
		}


		public static void setInstance(BazaPredmet instance) {
			BazaPredmet.instance = instance;
		}
		public String getColumnName(int index)
		{
			return this.kolone.get(index);
		}
		
		public Predmet getRow(int  row)
		{
			return this.predmeti.get(row);
		}
		
		public String getValueAt(int row, int column) {
			    Predmet predmeti = this.predmeti.get(row); //dobavi mi ceo red
				switch (column) {
				case 0:
					return predmeti.getSifraPredmeta();
				case 1:
					return predmeti.getNazivPredmeta();
				case 2:
					return String.valueOf(predmeti.getEspb());
				case 3:
					return String.valueOf(predmeti.getGodinaStudija());
				case 4:
					String sem = new String();
					if(predmeti.getSemestar() == Semestar.LETNJI) sem="LETNJI"; else sem= "ZIMSKI";
					return sem;
				default:
					return null;
				}
			}

}