package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.PredmetController;
import model.BazaPredmet;
import model.BazaProfesor;
import model.BazaProfesorPredajePredmet;
import model.BazaProfesoriNaPredmetu;
import model.BazaStudent;
import model.Predmet;
import model.Profesor;
import model.Student;
import pomocneKlase.MyFocusListener;

/**
 * klasa koja modeluje dijalog za izmenu i prikaz prethodno unetog predmeta.
 * Nasleđuje klase JDialog i implementira interfejs ActionListener.
 * 
 * @author Andrea Sabo Cibolja
 *
 */

public class IzmenaPredmetaDialog extends JDialog implements ActionListener {

	/**
	 * serijski broj
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * instanca klase
	 */
	public static IzmenaPredmetaDialog instanceIzmenaPredmeta;

	/**
	 * Getter koji vraća profesora koji predaje na ovom predmetu.
	 * 
	 * @return ime i prezime profesora
	 */
	public JTextField getTxtProfesor() {
		return txtProfesor;
	}

	/**
	 * Postavljanje i ažuriranje poslednje dodatog profesora na ovaj predmet
	 */
	public void setTxtProfesor() {
		if ((predmet.getProfesori() != null) && (predmet.getProfesori().size() != 0)) {
			Profesor profa = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();
			txtProfesor.setText(profa.getIme() + " " + profa.getPrezime());
		}
	}

	/**
	 * tekstualno polje za izmenu šifre
	 */
	JTextField txtSifra;
	/**
	 * tekstualno polje za izmeni naziva
	 */
	JTextField txtNaziv;
	/**
	 * tekstualno polje za unos broja ESPB bodova
	 */
	JTextField txtEspb;
	/**
	 * tesktualno polje koje prikazuej poslednje dodatog profesora na ovaj predmet,
	 * tj. njegogovo ime i prezime, u ovo polje je nemoguće kucati od strane
	 * korisnika
	 */
	JTextField txtProfesor;
	/**
	 * ComboBox koji prikazuje odabrane informacije koje je moguće izmeniti.
	 * Semestar u kom se predmet izvodi
	 */
	JComboBox<String> semestarCombo;
			/**
			 * ComboBox koji prikazuje odabrane informacije koje je moguće izmeniti. Godina
			 * izvođenja predmeta.
			 */
	JComboBox<String> godinaCombo;

	/**
	 * Predmet za koji se vrši izmena.
	 */
	Predmet predmet;

	/**
	 * Konstruktor klase
	 * 
	 * @param predmet predmet za koji se vrši izmena
	 */

	public IzmenaPredmetaDialog(Predmet predmet) {
		super();
		setTitle("Izmena predmeta");
		setSize(400, 400);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setModal(true);
		instanceIzmenaPredmeta = this;

		this.predmet = predmet;

		JPanel panCenter = new JPanel();
		BoxLayout boxCenter = new BoxLayout(panCenter, BoxLayout.Y_AXIS);
		panCenter.setLayout(boxCenter);
		MyFocusListener focusListener = new MyFocusListener();

		JButton btnOk = new JButton("POTVRDI");
		JButton btnCancel = new JButton("ODUSTANI");

		// dimenzije labela i tekstualnih komponenti
		Dimension dim = new Dimension(150, 20);

		// sifra
		JPanel panSifra = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSifra = new JLabel("Sifra predmeta*:");
		lblSifra.setPreferredSize(dim);

		txtSifra = new JTextField(predmet.getSifraPredmeta());
		txtSifra.setPreferredSize(dim);
		txtSifra.setName("txtSifra");
		txtSifra.addFocusListener(focusListener);
		txtSifra.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});

		panSifra.add(lblSifra);
		panSifra.add(txtSifra);

		// profesor
		JPanel panProfesor = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblProfesor = new JLabel("Profesor*");
		lblProfesor.setPreferredSize(dim);
		txtProfesor = new JTextField();
		txtProfesor.setPreferredSize(new Dimension(100, 20));
		txtProfesor.setEditable(false);

		if ((predmet.getProfesori() != null) && (predmet.getProfesori().size() != 0)) {
			System.out.println("Broj prof" + predmet.getProfesori().size());
			Profesor profa = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();
			txtProfesor.setText(profa.getIme() + " " + profa.getPrezime());
		}

		panProfesor.add(lblProfesor);
		panProfesor.add(txtProfesor);

		JButton plus = new JButton("+");
		JButton btnminus = new JButton("-");
		btnminus.setMargin(new Insets(0, 0, 0, 0));
		plus.setMargin(new Insets(0, 0, 0, 0));
		btnminus.setToolTipText("Uklanjanje profesora sa predmeta");
		btnminus.setPreferredSize(new Dimension(20, 20));

		panProfesor.add(btnminus);
		btnminus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Profesor pr = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();
				if (pr == null) {
					btnminus.setEnabled(false);
					JOptionPane.showMessageDialog(instanceIzmenaPredmeta, "Ne možete da ukolnite profesora!",
							"Upozorenje", JOptionPane.WARNING_MESSAGE);
				}
				System.out.println(pr.toString());// selektovali smo red u tabeli

				Object[] options = { "Da", "Ne" };

				int code = JOptionPane.showOptionDialog(instanceIzmenaPredmeta, "Da li ste sigurni?", "Ukloni predmet",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				if (code == JOptionPane.YES_OPTION) {
					System.out.println(pr.toString());
					System.out.println(predmet.toString());
					for (int i = 0; i < BazaProfesor.getInstance().getbroj_profesora(); i++) {
						if (BazaProfesor.getInstance().getProfesori().get(i).getBrLicneKarte()
								.equals(pr.getBrLicneKarte())) {
							BazaProfesoriNaPredmetu.getInstance().getProfesori().remove(pr);
							ProfesoriNaPredmetuJTable.getInstance().azurirajPrikaz();
							BazaProfesor.getInstance().izbrisiPredmet1(BazaProfesor.getInstance().getProfesori().get(i),
									predmet.getSifraPredmeta());

						}
					}

					Profesor p1 = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();
					if (p1 == null) {
						plus.setEnabled(true);
						txtProfesor.setText(" ");
					} else {
						txtProfesor.setText(p1.getIme() + " " + p1.getPrezime());
					}

				}

			}
		});
		plus.setText("+");
		ImageIcon ii = new ImageIcon("Slike//plus.png");

		// plus.setIcon();
		plus.setToolTipText("Dodaj profesora na predmet");
		plus.setPreferredSize(new Dimension(20, 20));
		if ((predmet.getProfesori() != null) && (predmet.getProfesori().size() != 0)) {
			plus.setEnabled(false);
		}
		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DodavanjeProfesoraPredmetu dialog = new DodavanjeProfesoraPredmetu(predmet, instanceIzmenaPredmeta);
				dialog.setVisible(true);
				if ((predmet.getProfesori() != null) && (predmet.getProfesori().size() != 0)) {
					Profesor profa = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();

					txtProfesor.setText(profa.getIme() + " " + profa.getPrezime());
				}
			}
		});

		panProfesor.add(plus);
		panProfesor.add(btnminus);

		// naziv
		JPanel panNaziv = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNaziv = new JLabel("Naziv*:");
		lblNaziv.setPreferredSize(dim);

		txtNaziv = new JTextField(predmet.getNazivPredmeta());
		txtNaziv.setPreferredSize(dim);
		txtNaziv.setName("txtNaziv");
		txtNaziv.addFocusListener(focusListener);
		txtNaziv.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});

		panNaziv.add(lblNaziv);
		panNaziv.add(txtNaziv);

		// Espb bodova
		JPanel panEspb = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblEspb = new JLabel("ESPB*:");
		lblEspb.setPreferredSize(dim);

		int brEspb = predmet.getEspb();
		String espbs = "" + brEspb;

		txtEspb = new JTextField(espbs);
		txtEspb.setPreferredSize(dim);
		txtEspb.setName("txtEspb");
		txtEspb.addFocusListener(focusListener);
		txtEspb.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		panEspb.add(lblEspb);
		panEspb.add(txtEspb);

		// godina na kojoj se slusa predmet
		JPanel panGodina = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblGodina = new JLabel("Godina*:");
		lblGodina.setPreferredSize(dim);

		String godina[] = { "1", "2", "3", "4", "5", "6" };
		godinaCombo = new JComboBox<String>(godina);
		godinaCombo.setPreferredSize(dim);
		godinaCombo.setSelectedIndex(predmet.getGodinaStudija() - 1);
		System.out.println(predmet.getGodinaStudija());
		godinaCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}
		});
		panGodina.add(lblGodina);
		panGodina.add(godinaCombo);

		// Semestar
		JPanel panSemestar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSemestar = new JLabel("Semestar*:");
		lblSemestar.setPreferredSize(dim);
		String semestar[] = { "ZIMSKI", "LETNJI" };
		semestarCombo = new JComboBox<String>(semestar);
		semestarCombo.setPreferredSize(dim);
		if (predmet.getSemestarChar() == 'L') {
			semestarCombo.setSelectedIndex(1);
		} else {
			semestarCombo.setSelectedIndex(0);
		}
		semestarCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}
		});
		panSemestar.add(lblSemestar);
		panSemestar.add(semestarCombo);

		panCenter.add(panSifra);
		panCenter.add(panNaziv);
		panCenter.add(panGodina);
		panCenter.add(panSemestar);
		panCenter.add(panEspb);
		panCenter.add(panProfesor);

		panCenter.add(Box.createVerticalStrut(25));
		add(panCenter, BorderLayout.CENTER);

		JPanel panBottom = new JPanel();
		BoxLayout box = new BoxLayout(panBottom, BoxLayout.X_AXIS);
		panBottom.setLayout(box);

		btnOk.setPreferredSize(new Dimension(150, 25));
		btnOk.addActionListener(this);
		btnOk.setEnabled(true);

		btnCancel.setPreferredSize(new Dimension(150, 25));
		btnCancel.addActionListener(this);

		panBottom.add(Box.createGlue());
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(btnOk);
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(btnCancel);
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(Box.createVerticalStrut(40));

		add(panBottom, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(GlavniProzor.getInstance()); // da bi dialog bio centriran neophodno je pozvati metodu
															// setLocationRelativeTo(parent frame) posle pozivanja
															// metode pack
		provera(); // pozivamo proveru pre ikakvog unosa kako bismo mogli da izmenimo na vec
					// unesene parametre sto je validno

	}

	/**
	 * Metoda koja kupi uneti tekst, poziva se nakon potvrde promene podataka o
	 * predmetu.
	 * 
	 * @return niz stringova koji predstavljaju unete podatke
	 */
	public String[] pokupiTekst() {
		String tekst[] = new String[5];
		for (int i = 0; i < tekst.length; i++) {
			tekst[i] = new String();
		}
		tekst[0] = txtSifra.getText().toString();
		tekst[1] = txtNaziv.getText().toString();
		tekst[2] = godinaCombo.getSelectedItem().toString();
		tekst[3] = txtEspb.getText().toString();
		tekst[4] = semestarCombo.getSelectedItem().toString();
		return tekst;

	}

	/**
	 * Metoda iz interfejsa ActionListener. Omogućuje odgovarajuću reakciju sistema
	 * nakon unetih izmenjenih podataka o predmetu.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tekst[] = pokupiTekst();
		boolean izmeni = true; // flag koji nam pokazuje da li je dozvoljeno da izmenimo predmet sa unetim
								// parametrima

		// metoda getActionCommand(), vraca string koji je ispisan na kliknutom
		// JButton-u
		if (e.getActionCommand().equals("ODUSTANI")) {
			dispose();
		} else {
			if (BazaPredmet.getInstance().getPredmeti().size() == 0) {
				izmeni = true;
			} else {
				if (!predmet.getSifraPredmeta().equals(tekst[0])) { // samo ako smo menjali sifruproveravamo da li neki
																	// od predmeta ima tu sifru
					for (Predmet p : BazaPredmet.getInstance().getPredmeti()) {
						if (p.getSifraPredmeta().equals(tekst[0])) {
							izmeni = false;
							JOptionPane.showMessageDialog(null, "Predmet sa unetom sifrom vec postoji!", "Upozorenje",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			if (izmeni) {
				BazaPredmet.getInstance().izmeniPredmet(predmet.getSifraPredmeta(), tekst[0], tekst[1],
						Integer.parseInt(tekst[2]), Integer.parseInt(tekst[3]), tekst[4].charAt(0));
				setVisible(false);
			}
		}

	}

	/**
	 * Metoda koja proverava svako od tekstualnih polja u koje korisnik kuca, da li
	 * je sve napisano u odgovarajućem šablonu.
	 * 
	 * @return boolean vrednost pokazuje da li su podaci ispravno uneti
	 */

	protected boolean provera() {
		String tekst[] = pokupiTekst();
		Color correct = new Color(208, 240, 192);
		Color incorrect = new Color(255, 132, 132);
		boolean ok = true;
		boolean ok1 = true;
		if (tekst[0].length() != 0) {
			if (!Pattern.matches("[a-zA-Z0-9]*", tekst[0])) {
				txtSifra.setBackground(incorrect);
				txtSifra.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtSifra.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[1].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[a-zA-Z0-9 ]*", tekst[1])) {
				txtNaziv.setBackground(incorrect);
				txtNaziv.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtNaziv.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[3].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[0-9]{1,2}", tekst[3])) {
				txtEspb.setBackground(incorrect);
				txtEspb.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtEspb.setBackground(correct);
		} else {
			ok = false;
		}

		return ok;
	}

	/**
	 * Metoda koja pregleda listu profesora na ovom predmetu i postavlja polje
	 * txtProfesor da prikazuje ime i prezime poslednje unetog profesora.
	 */
	public void azurirajPoslednjegProfesora() {
		if ((predmet.getProfesori() != null) && (predmet.getProfesori().size() != 0)) {
			Profesor profa = BazaProfesoriNaPredmetu.getInstance().getPoslednjiProfesor();
			txtProfesor.setText(profa.getIme() + " " + profa.getPrezime());
		}

	}
}
