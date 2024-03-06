package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Zadatak1Vezbe9 extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroupColor = new ButtonGroup();
	private JLabel lblCrvena;
	private JLabel lblPlava;
	private JLabel lblZuta;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private DlgAddModifyColor dlgColor;
	private JList<String> listColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Zadatak1Vezbe9 frame = new Zadatak1Vezbe9();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Zadatak1Vezbe9() {
		setTitle("Zadatak 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel pnlCentar = new JPanel();
		contentPane.add(pnlCentar, BorderLayout.CENTER);
		GridBagLayout gbl_pnlCentar = new GridBagLayout();
		gbl_pnlCentar.columnWidths = new int[] { 85, 0, 291, 0 };
		gbl_pnlCentar.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gbl_pnlCentar.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlCentar.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		pnlCentar.setLayout(gbl_pnlCentar);

		JToggleButton tglbtnCrvena = new JToggleButton("Crvena");
		tglbtnCrvena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblZuta.setForeground(Color.black);
				lblPlava.setForeground(Color.black);
				dlm.addElement(lblCrvena.getText());
				lblCrvena.setForeground(Color.GREEN);
			}
		});
		buttonGroupColor.add(tglbtnCrvena);
		GridBagConstraints gbc_tglbtnPlava = new GridBagConstraints();
		gbc_tglbtnPlava.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnPlava.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPlava.gridx = 0;
		gbc_tglbtnPlava.gridy = 0;
		pnlCentar.add(tglbtnCrvena, gbc_tglbtnPlava);

		lblCrvena = new JLabel("Crvena");
		GridBagConstraints gbc_lblCrvena = new GridBagConstraints();
		gbc_lblCrvena.insets = new Insets(0, 0, 5, 5);
		gbc_lblCrvena.gridx = 1;
		gbc_lblCrvena.gridy = 0;
		pnlCentar.add(lblCrvena, gbc_lblCrvena);

		JToggleButton tglbtnPlava = new JToggleButton("Plava");
		tglbtnPlava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblZuta.setForeground(Color.black);
				lblCrvena.setForeground(Color.black);
				lblPlava.setForeground(Color.green);
				dlm.addElement(lblPlava.getText());
			}
		});
		buttonGroupColor.add(tglbtnPlava);
		GridBagConstraints gbc_tglbtnPlava1 = new GridBagConstraints();
		gbc_tglbtnPlava1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnPlava1.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnPlava1.gridx = 0;
		gbc_tglbtnPlava1.gridy = 1;
		pnlCentar.add(tglbtnPlava, gbc_tglbtnPlava1);

		lblPlava = new JLabel("Plava");
		GridBagConstraints gbc_lblPlava = new GridBagConstraints();
		gbc_lblPlava.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlava.gridx = 1;
		gbc_lblPlava.gridy = 1;
		pnlCentar.add(lblPlava, gbc_lblPlava);

		JToggleButton tglbtnZuta = new JToggleButton("Zuta");
		tglbtnZuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblCrvena.setForeground(Color.black);
				lblPlava.setForeground(Color.black);
				lblZuta.setForeground(Color.GREEN);
				dlm.addElement(lblZuta.getText());
			}
		});

		JButton btnChangeColor = new JButton("Izmeni boju");
		btnChangeColor.setEnabled(false);
		btnChangeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dlgColor = new DlgAddModifyColor();
				String[] colors = dlm.get(listColor.getSelectedIndex()).split(" ");
				dlgColor.getTxtRed().setText(colors[0]);
				dlgColor.getTxtGreen().setText(colors[1]);
				dlgColor.getTxtBlue().setText(colors[2]);
				dlgColor.setVisible(true);
				
				if (dlgColor.isOk()) {
					//kod oblika je modifikacija vrednosti a ne brisanje i dodavanje u listu
					int index=listColor.getSelectedIndex();
					dlm.remove(index);
					String colorString = dlgColor.getTxtRed().getText() + " " + dlgColor.getTxtGreen().getText() + " "
							+ dlgColor.getTxtBlue().getText();				
					dlm.add(index,colorString);

					pnlCentar.setBackground(new Color(Integer.parseInt(dlgColor.getTxtRed().getText()),
							Integer.parseInt(dlgColor.getTxtGreen().getText()),
							Integer.parseInt(dlgColor.getTxtBlue().getText())));
				}
			}
		});
		GridBagConstraints gbc_btnChangeColor = new GridBagConstraints();
		gbc_btnChangeColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnChangeColor.gridx = 2;
		gbc_btnChangeColor.gridy = 1;
		pnlCentar.add(btnChangeColor, gbc_btnChangeColor);
		buttonGroupColor.add(tglbtnZuta);
		GridBagConstraints gbc_tglbtnZuta = new GridBagConstraints();
		gbc_tglbtnZuta.fill = GridBagConstraints.HORIZONTAL;
		gbc_tglbtnZuta.insets = new Insets(0, 0, 5, 5);
		gbc_tglbtnZuta.gridx = 0;
		gbc_tglbtnZuta.gridy = 2;
		pnlCentar.add(tglbtnZuta, gbc_tglbtnZuta);

		lblZuta = new JLabel("Zuta");
		GridBagConstraints gbc_lblZuta = new GridBagConstraints();
		gbc_lblZuta.insets = new Insets(0, 0, 5, 5);
		gbc_lblZuta.gridx = 1;
		gbc_lblZuta.gridy = 2;
		pnlCentar.add(lblZuta, gbc_lblZuta);

		JButton btnAddColor = new JButton("Dodaj boju");
		btnAddColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dlgColor = new DlgAddModifyColor();
				dlgColor.setVisible(true);

				if (dlgColor.isOk()) {
					String colorString = dlgColor.getTxtRed().getText() + " " + dlgColor.getTxtGreen().getText() + " "
							+ dlgColor.getTxtBlue().getText();
					dlm.addElement(colorString);

					pnlCentar.setBackground(new Color(Integer.parseInt(dlgColor.getTxtRed().getText()),
							Integer.parseInt(dlgColor.getTxtGreen().getText()),
							Integer.parseInt(dlgColor.getTxtBlue().getText())));
				}
			}
		});
		GridBagConstraints gbc_btnAddColor = new GridBagConstraints();
		gbc_btnAddColor.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddColor.gridx = 2;
		gbc_btnAddColor.gridy = 2;
		pnlCentar.add(btnAddColor, gbc_btnAddColor);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 3;
		pnlCentar.add(scrollPane, gbc_scrollPane);

		listColor = new JList<String>();
		listColor.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnChangeColor.setEnabled(true);
			}
		});
		scrollPane.setViewportView(listColor);
		listColor.setModel(dlm);

		JLabel lblNaslov = new JLabel("Zadatak 1");
		lblNaslov.setForeground(Color.BLUE);
		lblNaslov.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNaslov.setBackground(Color.GREEN);
		lblNaslov.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblNaslov, BorderLayout.NORTH);

		JButton btnKlik = new JButton("Klikni me");
		btnKlik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Antistres dugme :)", "Poruka", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(btnKlik, BorderLayout.SOUTH);
	}

}