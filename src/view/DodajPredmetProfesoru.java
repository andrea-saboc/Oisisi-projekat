package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import model.BazaNepolozeni;
import model.BazaPredmet;
import model.BazaProfesorPredajePredmet;
import model.Ocena;
import model.Predmet;
import model.Profesor;

/**
 * Klasa služi za modelovanje dijaloga za dodavanje predmeta profesoru.
 * Prikazuje se lista svih predmeta koje profesor može da predaje. Iz te liste
 * se bira koji se dodaje tom profesoru. Omogućena je i višestruka selekcija.
 * Nasleđuje klasu JDialog i implementira interfejs ItemListener. Nakon uspešno
 * odrađene akcije tabela predmeta profesora se ažurira.
 * 
 * @author Aleksandra Mirković
 *
 */

public class DodajPredmetProfesoru extends JDialog implements ItemListener {

	/**
	 * serijski broj
	 */
	private static final long serialVersionUID = -2615893345062305472L;
	/**
	 * 
	 */
	/**
	 * Profesor kome se dodaje predmet
	 */

	Profesor profesor;

	/**
	 * Konstruktor klase.
	 * 
	 * @param p predstavlja profesora, kome želimo da dodamo predmet.
	 */

	@SuppressWarnings("deprecation")
	public DodajPredmetProfesoru(Profesor p) {
		super();
		setTitle("Dodaj predmet");

		setModal(true);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setResizable(false);
		setSize(400, 400);

		this.profesor = p;
		JScrollPane scrollPane = new JScrollPane(); // pravimo scrollPane da bi mogli da skrolujemo ako budemo imali
													// vise redova
		add(scrollPane, BorderLayout.CENTER);

		JPanel panCenter = new JPanel();
		BoxLayout boxCenter = new BoxLayout(panCenter, BoxLayout.Y_AXIS);
		panCenter.setLayout(boxCenter);

		JPanel nemaNistaZaDodati = new JPanel();
		JLabel nemaPredmeta = new JLabel("Nema predmeta!");
		nemaNistaZaDodati.add(nemaPredmeta);

		List list = new List();
		list.setMultipleSelections(true);

		int brojMogucihPredmetaZaDodavanje = 0;

		for (Predmet p1 : BazaPredmet.getInstance().getPredmeti()) {

			boolean dodaj = true;
			for (Predmet p2 : p.getPredmeti()) {
				if ((p1.getSifraPredmeta().equals(p2.getSifraPredmeta()))) {
					dodaj = false;
				}

			}

			if (dodaj) {
				String moguceDodati = p1.getSifraPredmeta() + " " + "-" + " " + p1.getNazivPredmeta();
				list.add(moguceDodati);
				++brojMogucihPredmetaZaDodavanje;
			}
		}

		if (brojMogucihPredmetaZaDodavanje > 0) {
			panCenter.add(list);
		} else {
			panCenter.add(nemaNistaZaDodati);
		}

		JButton btnOk = new JButton("Potvrdi");
		list.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				repaint();
				if (list.getSelectedItems().length > 0) {
					btnOk.setEnabled(true);
				} else {
					btnOk.setEnabled(false);
				}
			}
		});
		btnOk.setEnabled(false);
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] dodatiPredmete = list.getSelectedItems();
				if (dodatiPredmete.length == 0) {
					JOptionPane.showMessageDialog(null, "Niste selektovali predmet za dodavanje!", "Upozorenje!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnOk.setPreferredSize(new Dimension(100, 25));
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] dodatiPredmete = list.getSelectedItems();
				for (int i = 0; i < list.getSelectedItems().length; i++) {
					String[] splited = dodatiPredmete[i].split("-");
					String sifraPredmeta = splited[0].trim();
					for (int j = 0; j < BazaPredmet.getInstance().getPredmeti().size(); j++) {
						System.out.println(j);
						System.out.println(BazaPredmet.getInstance().getPredmeti().get(j));
						if (BazaPredmet.getInstance().getPredmeti().get(j).getSifraPredmeta().equals(sifraPredmeta)) {

							BazaProfesorPredajePredmet.getInstance()
									.dodajPredmet(BazaPredmet.getInstance().getPredmeti().get(j));
							ProfesorPredmetJTabel.getInstance().azurirajPrikaz();
							BazaPredmet.getInstance().getPredmeti().get(j).getProfesori().add(p);
							// dodajemo i tom predmetu na spisak studenta
						}

					}

				}

				setVisible(false);

			}

		});

		JButton btnCancel = new JButton("ODUSTANI");

		btnCancel.setPreferredSize(new Dimension(100, 25));
		btnCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

		JPanel panBottom = new JPanel();
		panBottom.add(Box.createGlue());
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(btnOk);
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(btnCancel);
		panBottom.add(Box.createHorizontalStrut(10));
		panBottom.add(Box.createVerticalStrut(40));

		JPanel panNorth = new JPanel();

		JLabel n = new JLabel("Predmeti: ");
		panNorth.add(n);

		JPanel panWest = new JPanel();
		JPanel panEast = new JPanel();
		JPanel panTop = new JPanel();
		Dimension d = new Dimension(1400, 1000);
		panWest.setSize(d);
		panEast.setSize(d);
		panTop.setSize(d);

		add(panCenter, BorderLayout.CENTER);
		add(panBottom, BorderLayout.SOUTH);
		add(panWest, BorderLayout.WEST);
		add(panEast, BorderLayout.EAST);
		add(panTop, BorderLayout.BEFORE_FIRST_LINE);
		add(panNorth, BorderLayout.NORTH);

		setLocationRelativeTo(IzmenaProfesorDialog.instanceIzmenaProfesor);

	}

	/**
	 * Metoda koja omogućava da se nakon selekcije određenog predmeta, njegovo polje
	 * oboji u plavo.
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

}
