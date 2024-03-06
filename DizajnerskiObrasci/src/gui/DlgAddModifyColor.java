package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgAddModifyColor extends JDialog {

	public final JPanel contentPanel = new JPanel();
	private JTextField txtRed;
	private JTextField txtGreen;
	private JTextField txtBlue;
	private boolean ok = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgAddModifyColor dialog = new DlgAddModifyColor();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgAddModifyColor() {
		setTitle("Dodaj boju");
		setBounds(100, 100, 450, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{74, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{26, 26, 23, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblCrvenaBoja = new JLabel("Crvena boja");
			GridBagConstraints gbc_lblCrvenaBoja = new GridBagConstraints();
			gbc_lblCrvenaBoja.anchor = GridBagConstraints.EAST;
			gbc_lblCrvenaBoja.insets = new Insets(0, 0, 5, 5);
			gbc_lblCrvenaBoja.gridx = 0;
			gbc_lblCrvenaBoja.gridy = 0;
			contentPanel.add(lblCrvenaBoja, gbc_lblCrvenaBoja);
		}
		{
			txtRed = new JTextField();
			GridBagConstraints gbc_txtRed = new GridBagConstraints();
			gbc_txtRed.insets = new Insets(0, 0, 5, 0);
			gbc_txtRed.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRed.gridx = 1;
			gbc_txtRed.gridy = 0;
			contentPanel.add(txtRed, gbc_txtRed);
			txtRed.setColumns(10);
		}
		{
			JLabel lblZelenaBoja = new JLabel("Zelena boja");
			GridBagConstraints gbc_lblZelenaBoja = new GridBagConstraints();
			gbc_lblZelenaBoja.anchor = GridBagConstraints.EAST;
			gbc_lblZelenaBoja.insets = new Insets(0, 0, 5, 5);
			gbc_lblZelenaBoja.gridx = 0;
			gbc_lblZelenaBoja.gridy = 1;
			contentPanel.add(lblZelenaBoja, gbc_lblZelenaBoja);
		}
		{
			txtGreen = new JTextField();
			GridBagConstraints gbc_txtGreen = new GridBagConstraints();
			gbc_txtGreen.insets = new Insets(0, 0, 5, 0);
			gbc_txtGreen.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtGreen.gridx = 1;
			gbc_txtGreen.gridy = 1;
			contentPanel.add(txtGreen, gbc_txtGreen);
			txtGreen.setColumns(10);
		}
		{
			JLabel lblPlavaBoja = new JLabel("Plava boja");
			GridBagConstraints gbc_lblPlavaBoja = new GridBagConstraints();
			gbc_lblPlavaBoja.insets = new Insets(0, 0, 0, 5);
			gbc_lblPlavaBoja.anchor = GridBagConstraints.EAST;
			gbc_lblPlavaBoja.gridx = 0;
			gbc_lblPlavaBoja.gridy = 2;
			contentPanel.add(lblPlavaBoja, gbc_lblPlavaBoja);
		}
		{
			txtBlue = new JTextField();
			GridBagConstraints gbc_txtBlue = new GridBagConstraints();
			gbc_txtBlue.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtBlue.gridx = 1;
			gbc_txtBlue.gridy = 2;
			contentPanel.add(txtBlue, gbc_txtBlue);
			txtBlue.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//ovo je primer
						if(Integer.parseInt(txtRed.getText())>0 & Integer.parseInt(txtRed.getText())<256) {
							ok = true;
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Greska!", "Poruka", JOptionPane.ERROR_MESSAGE);
						}


					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTxtRed() {
		return txtRed;
	}

	public void setTxtRed(JTextField txtRed) {
		this.txtRed = txtRed;
	}

	public JTextField getTxtGreen() {
		return txtGreen;
	}

	public void setTxtGreen(JTextField txtGreen) {
		this.txtGreen = txtGreen;
	}

	public JTextField getTxtBlue() {
		return txtBlue;
	}

	public void setTxtBlue(JTextField txtBlue) {
		this.txtBlue = txtBlue;
	}

	public boolean isOk() {
		return ok;
	}
	
	
	
	

}