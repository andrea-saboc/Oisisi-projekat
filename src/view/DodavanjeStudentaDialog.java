package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.StudentController;
import model.BazaStudent;
import model.Student;
import pomocneKlase.MyFocusListener;

/**
 * Klasa koja modeluje dijalog za dodavanje novog studenta u bazu studenata.
 * Ukoliko referent u tabu Studenti odabere neku od opcija za dodavanje novog
 * entiteta(dugme ToolBar-a, stavka menija, odgovarajući akcelerator ili
 * mnemonik) otvara se dijalog koji je modalan i centriran u odnosu na glavni
 * prozor. Prikazuje se forma za unos informacija o studentu, kao i dva dugmeta
 * za potvrdu i odustanak. Pritisak na dugme "Potvrdi" je moguće samo ako su sva
 * polja validno popunjena. Nakon dodavanja novog studenta u sistem, prikaz
 * tabele sa studentima se osvežava i sadržaj se ažurira.
 * 
 * Nasleđuje klasu JDialog i implementira interfejs ActionListener.
 * 
 * @author Andrea Sabo Cibolja
 *
 */

public class DodavanjeStudentaDialog extends JDialog implements ActionListener {

	/**
	 * serijski broj
	 */
	private static final long serialVersionUID = 5282385078335739861L;

	/**
	 * predstavlja roditeljski dijalog koji je pozvao ovaj dijalog
	 */
	private static GlavniProzor parent;
	/**
	 * tekstualno polje za unos imena
	 */
	JTextField txtIme;
	/**
	 * tekstualno polje za unos prezimena
	 */
	JTextField txtPrezime;
	/**
	 * tekstualno polje za unos datuma rođenja
	 */
	JTextField txtDatumRodjenja;
	/**
	 * tekstualno polje za unos adrese
	 */
	JTextField txtAdresa;
	/**
	 * tekstualno polje za unos broja telefona
	 */
	JTextField txtTel;
	/**
	 * tekstualno polje za unos elektronske pošte
	 */
	JTextField txtEmail;
	/**
	 * tekstualno polje za unos broja indeksa
	 */
	JTextField txtIndeks;
	/**
	 * tekstualno polje za unos godine upisa
	 */
	JTextField txtGodinaUpisa;
	/**
	 * ComboBox za odabir odgovarajuće vrednosti za trenutnu godinu studija
	 */
	JComboBox<String> trenutnaGodinaCombo;
	/**
	 * ComboBox za odabir načina finansiranja
	 */
	JComboBox<String> finansCombo;

	/**
	 * Konstruktor klase
	 */

	DodavanjeStudentaDialog() {
		super();
		setTitle("Dodavanje studenta");
		setSize(400, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);

		JPanel panCenter = new JPanel();
		BoxLayout boxCenter = new BoxLayout(panCenter, BoxLayout.Y_AXIS);
		panCenter.setLayout(boxCenter);
		MyFocusListener focusListener = new MyFocusListener();

		JButton btnOk = new JButton("POTVRDI");
		JButton btnCancel = new JButton("ODUSTANI");

		// dimenzije labela i tekstualnih komponenti
		Dimension dim = new Dimension(150, 20);

		// prezime
		JPanel panPrezime = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPrezime = new JLabel("Prezime*:");
		lblPrezime.setPreferredSize(dim);

		txtPrezime = new JTextField();
		txtPrezime.setPreferredSize(dim);
		txtPrezime.setName("txtPrezime");
		txtPrezime.addFocusListener(focusListener);
		txtPrezime.addKeyListener(new KeyListener() {

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

		panPrezime.add(lblPrezime);
		panPrezime.add(txtPrezime);

		// ime
		JPanel panIme = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblIme = new JLabel("Ime*:");
		lblIme.setPreferredSize(dim);

		txtIme = new JTextField();
		txtIme.setPreferredSize(dim);
		txtIme.setName("txtIme");
		txtIme.addFocusListener(focusListener);
		txtIme.addKeyListener(new KeyListener() {

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

		panIme.add(lblIme);
		panIme.add(txtIme);

		// datum rodjenja
		JPanel panDatumRodjenja = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDatumRodjenja = new JLabel("Datum rođenja*:");
		lblDatumRodjenja.setPreferredSize(dim);

		txtDatumRodjenja = new JTextField();
		txtDatumRodjenja.setPreferredSize(dim);
		txtDatumRodjenja.setName("txtDatumRodjenja");
		txtDatumRodjenja.setToolTipText("Neophodan format: dd/mm/gggg");
		txtDatumRodjenja.addFocusListener(focusListener);
		txtDatumRodjenja.addKeyListener(new KeyListener() {

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

		panDatumRodjenja.add(lblDatumRodjenja);
		panDatumRodjenja.add(txtDatumRodjenja);

		// adresa stanovanja
		JPanel panAdresa = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblAdresa = new JLabel("Adresa*:");
		lblAdresa.setPreferredSize(dim);

		txtAdresa = new JTextField();
		txtAdresa.setPreferredSize(dim);
		txtAdresa.setName("txtAdresa");
		txtAdresa.addFocusListener(focusListener);
		txtAdresa.addKeyListener(new KeyListener() {

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

		panAdresa.add(lblAdresa);
		panAdresa.add(txtAdresa);

		// broj telefona
		JPanel panTel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTel = new JLabel("Broj telefona*:");
		lblTel.setPreferredSize(dim);

		txtTel = new JTextField();
		txtTel.setPreferredSize(dim);
		txtTel.setName("txtTel");
		txtTel.addFocusListener(focusListener);
		txtTel.addKeyListener(new KeyListener() {

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

		panTel.add(lblTel);
		panTel.add(txtTel);

		// E-mail adresa
		JPanel panEmail = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblEmail = new JLabel("E-mail adresa*:");
		lblEmail.setPreferredSize(dim);

		txtEmail = new JTextField();
		txtEmail.setPreferredSize(dim);
		txtEmail.setName("txtEmail");
		txtEmail.addFocusListener(focusListener);
		txtEmail.addKeyListener(new KeyListener() {

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

		panEmail.add(lblEmail);
		panEmail.add(txtEmail);

		// Broj indeksa
		JPanel panIndeks = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblIndeks = new JLabel("Broj indeksa*:");
		lblIndeks.setPreferredSize(dim);

		txtIndeks = new JTextField();
		txtIndeks.setPreferredSize(dim);
		txtIndeks.setName("txtIndeks");
		txtIndeks.addFocusListener(focusListener);
		txtIndeks.addKeyListener(new KeyListener() {

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

		panIndeks.add(lblIndeks);
		panIndeks.add(txtIndeks);

		// Godina upisa
		JPanel panGodinaUpisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblGodinaUpisa = new JLabel("Godina upisa*:");
		lblGodinaUpisa.setPreferredSize(dim);

		txtGodinaUpisa = new JTextField();
		txtGodinaUpisa.setPreferredSize(dim);
		txtGodinaUpisa.setName("txtGodinaUpisa");
		txtGodinaUpisa.addFocusListener(focusListener);
		txtGodinaUpisa.addKeyListener(new KeyListener() {

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

		panGodinaUpisa.add(lblGodinaUpisa);
		panGodinaUpisa.add(txtGodinaUpisa);

		// Trenutna godina studija
		JPanel panTrenutnaGodina = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTrenutnaGodina = new JLabel("Trenutna godina studija*:");
		String godina[] = { "1", "2", "3", "4" };
		trenutnaGodinaCombo = new JComboBox<String>(godina);
		lblTrenutnaGodina.setPreferredSize(dim);
		trenutnaGodinaCombo.setPreferredSize(dim);
		trenutnaGodinaCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (provera()) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}
		});
		panTrenutnaGodina.add(lblTrenutnaGodina);
		panTrenutnaGodina.add(trenutnaGodinaCombo);

		// Nacin finansiranja
		JPanel panFinans = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblFinans = new JLabel("Način finansiranja*:");
		String finansiranje[] = { "Budžet", "Samofinansiranje" };
		finansCombo = new JComboBox<String>(finansiranje);
		lblFinans.setPreferredSize(dim);
		finansCombo.setPreferredSize(dim);
		panFinans.add(lblFinans);
		panFinans.add(finansCombo);

		panCenter.add(panIme);
		panCenter.add(panPrezime);
		panCenter.add(panDatumRodjenja);
		panCenter.add(panAdresa);
		panCenter.add(panTel);
		panCenter.add(panEmail);
		panCenter.add(panIndeks);
		panCenter.add(panGodinaUpisa);
		panCenter.add(panTrenutnaGodina);
		panCenter.add(panFinans);
		panCenter.add(Box.createVerticalStrut(25));
		add(panCenter, BorderLayout.CENTER);

		JPanel panBottom = new JPanel();
		BoxLayout box = new BoxLayout(panBottom, BoxLayout.X_AXIS);
		panBottom.setLayout(box);

		btnOk.setPreferredSize(new Dimension(150, 25));
		btnOk.addActionListener(this);
		btnOk.setEnabled(false);

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
		setModal(true);
		pack();
		setLocationRelativeTo(GlavniProzor.getInstance()); // da bi dialog bio centriran neophodno je pozvati metodu
															// setLocationRelativeTo(parent frame) posle pozivanja
															// metode pack

	}

	/**
	 * Metoda koja kupi unete podatke
	 * 
	 * @return povratna vrednost je niz unetih podataka konvertovan u String
	 */

	public String[] pokupiTekst() {
		String tekst[] = new String[10];
		for (int i = 0; i < tekst.length; i++) {
			tekst[i] = new String();
		}
		tekst[0] = txtIme.getText().toString();
		tekst[1] = txtPrezime.getText().toString();
		tekst[2] = txtDatumRodjenja.getText().toString();
		tekst[3] = txtAdresa.getText().toString();
		tekst[4] = txtTel.getText().toString();
		tekst[5] = txtEmail.getText().toString();
		tekst[6] = txtIndeks.getText().toString();
		tekst[7] = txtGodinaUpisa.getText().toString();
		tekst[8] = trenutnaGodinaCombo.getSelectedItem().toString();
		tekst[9] = finansCombo.getSelectedItem().toString();

		return tekst;

	}

	/**
	 * Metoda iz interfejsa ActionListener. Omogućuje odgovarajući odgovor od
	 * sistema nakon klika na ,,ODUSTANI" ili ,,POTVRDI". Omogućuje kreiranje novog
	 * studenta na osnovu unetih podataka.
	 */

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String tekst[] = pokupiTekst();
		boolean dodaj = true; // flag koji nam pokazuje da li je dozvoljeno dodati studenta sa tim parametrima

		// metoda getActionCommand(), vraca string koji je ispisan na kliknutom
		// JButton-u
		if (e.getActionCommand().equals("ODUSTANI")) {
			dispose();
		} else {
			if (BazaStudent.getInstance().getStudenti().size() == 0) {
				dodaj = true;
			} else {
				for (Student s : BazaStudent.getInstance().getStudenti()) {
					if (s.getBrIndeksa().equals(tekst[6])) {
						dodaj = false;
						JOptionPane.showMessageDialog(null, "Student sa unetim brojem indeksa već postoji!",
								"Upozorenje", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			if (dodaj) {
				/*
				 * REFERENCIRAN KOD ZA PARSIRANJE DATUMA >
				 * https://mkyong.com/java8/java-8-how-to-convert-string-to-localdate/
				 */
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
				BazaStudent.getInstance().dodajStudenta(tekst[1], tekst[0], LocalDate.parse(tekst[2], formatter),
						tekst[3], tekst[4], tekst[5], tekst[6], Integer.parseInt(tekst[7]), Integer.parseInt(tekst[8]),
						tekst[9].charAt(0));
				StudentController.getInstance().dodajStudenta();
				setVisible(false);
			}
		}

	}

	/**
	 * Metoda koja omogućava proveru da li su sva polja ispravno popunjena. U
	 * slučaju ispravno popunjenog tekstualnog polja polje se boji u zeleno, a u
	 * slučaju neispravnog u crveno.
	 * 
	 * @return predstavlja da li su sva polja ispravno popunjena, povratna vrednost
	 *         je TRUE ako jesu
	 */

	protected boolean provera() {
		String tekst[] = pokupiTekst();
		Color correct = new Color(208, 240, 192);
		Color incorrect = new Color(255, 132, 132);
		boolean ok = true;
		boolean ok1 = true;
		if (tekst[0].length() != 0) {
			if (!Pattern.matches("[a-zA-ZćĆčČšŠđĐžŽ ]*", tekst[0])) {
				txtIme.setBackground(incorrect);
				txtIme.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtIme.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[1].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[a-zA-ZćĆčČšŠđĐžŽ ]*", tekst[1])) {
				txtPrezime.setBackground(incorrect);
				txtPrezime.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtPrezime.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[2].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.][0-9]{4}", tekst[2])) {
				txtDatumRodjenja.setBackground(incorrect);
				txtDatumRodjenja.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtDatumRodjenja.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[3].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[a-zA-ZćĆčČšŠđĐžŽ 0-9,]*", tekst[3])) {
				txtAdresa.setBackground(incorrect);
				txtAdresa.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtAdresa.setBackground(correct);

		} else {
			ok = false;
		}
		if (tekst[4].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("^[0-9/-]*", tekst[4])) {
				txtTel.setBackground(incorrect);
				txtTel.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtTel.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[5].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("^(.+)@(.+)", tekst[5])) {
				txtEmail.setBackground(incorrect);
				txtEmail.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtEmail.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[6].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[a-z]{2,3}-[0-9]{1,3}-[0-9]{4}", tekst[6])
					&& !Pattern.matches("[a-zA-ZćĆčČšŠđĐžŽ ]*", tekst[6])) {
				txtIndeks.setBackground(incorrect);
				txtIndeks.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtIndeks.setBackground(correct);
		} else {
			ok = false;
		}
		if (tekst[7].length() != 0) {
			ok1 = true;
			if (!Pattern.matches("[0-9]{4}", tekst[7])) {
				txtGodinaUpisa.setBackground(incorrect);
				txtGodinaUpisa.setForeground(Color.black);
				ok1 = false;
				ok = false;
			}
			if (ok1)
				txtGodinaUpisa.setBackground(correct);
		} else {
			ok = false;
		}

		return ok;
	}

}