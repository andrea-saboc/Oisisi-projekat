 package model;

 import java.time.LocalDate;
import java.util.*;


public class BazaProfesor {

	//singlton
	// sablon koji nam omogucava da imamo jednu instancu necega, mi zelimo da imamo jednu instancu profesora, jer ne zelimo da neko drugi sa vise strana to menja 
	private static BazaProfesor instance =null;
	
	public static BazaProfesor getInstance() {
		if(instance==null)
			instance=new BazaProfesor();
		return instance;
		
	}
	
	private long  broj_profesora=0;
	
	private List<Profesor> profesori;
	private List<String> kolone;
	
	
	private BazaProfesor()
	{
	
		this.kolone = new ArrayList<String>();
		initProfesor();
		this.kolone.add("IME");
		this.kolone.add("PREZIME");
		this.kolone.add("TITULA");
		this.kolone.add("ZVANJE");
	}

	private void initProfesor() {
		this.profesori = new ArrayList<Profesor>();
		profesori.add(new Profesor("Petar","Petrovic",LocalDate.of(1999, 5, 25),"+38169877633","Tolstojeva 10","petarpetrovic@gmail.com","Radnicka 17","199928277745","Prof. dr","Redovni profesor",null));
		getBroj_Profesora();
		profesori.add(new Profesor("Nikola","Nikolic",LocalDate.of(1980, 4, 17),"+38169667633","Tolstojeva 1","nikolanikolic@gmail.com","Radnicka 17","1980777166111","MSc","Saradnik u nastavi",null));
		getBroj_Profesora();
		
		
	}
	public long getBroj_Profesora() {
		return broj_profesora++;
	}


	public void setBroj_Profesora(long broj_profesora) {
		this.broj_profesora = broj_profesora;
	}


	public List<Profesor> getProfesori() {
		return profesori;
	}


	public void setProfesori(List<Profesor> profesori) {
		this.profesori = profesori;
	}


	public List<String> getKolone() {
		return kolone;
	}

	public int getColumnCount() {
		return 4;
	}
	public void setKolone(List<String> kolone) {
		this.kolone = kolone;
	}


	public static void setInstance(BazaProfesor instance) {
		BazaProfesor.instance = instance;
	}
	public String getColumnName(int index)
	{
		return this.kolone.get(index);
	}
	
	public Profesor getRow(int  row)
	{
		return this.profesori.get(row);
	}
	
	public String getValueAt(int row, int column) {
		    Profesor profesori = this.profesori.get(row); //dobavi mi ceo red
			switch (column) {
			case 0:
				return profesori.getIme();
			case 1:
				return profesori.getPrezime();
			case 2:
				return profesori.getTitula();
			case 3:
				return profesori.getZvanjeProfesora();
			default:
				return null;
			}
		}
	public void dodajProfesora(String ime, String prezime, LocalDate datumRodjena, String brojTelefona, String adresaStanovanja,
			String email, String adresaKancelarije, String brLicneKarte, String titula, String zvanjeProfesora) {
		
		this.profesori.add(new Profesor(ime,prezime,datumRodjena,brojTelefona,adresaStanovanja,email,adresaKancelarije,brLicneKarte,titula,zvanjeProfesora,null));
		getBroj_Profesora();
	}
}
