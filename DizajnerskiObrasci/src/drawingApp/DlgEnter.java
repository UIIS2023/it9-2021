package drawingApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

public class DlgEnter extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtWidth;
	private JTextField txtUnos1;
	private JLabel lblWidth;
	private JLabel Height;
	private boolean isOK;
	private JLabel lblColor1;
	private JButton btnColorBC;
	private Color c;
	private Color colorUnutrasnjost;
	private Color colorIviceUnutrasnjiKrug;
	private Color colorUnutrasnjiKrugBoja;
	private JLabel lblInnerColor;
	

	private JButton btnColorIC;
	private JLabel lblColor3;
	private JButton btnColorICB;
	
	
	
	public Color getColorIviceUnutrasnjiKrug() {
		return colorIviceUnutrasnjiKrug;
	}

	public void setColorIviceUnutrasnjiKrug(Color colorIviceUnutrasnjiKrug) {
		this.colorIviceUnutrasnjiKrug = colorIviceUnutrasnjiKrug;
	}

	public Color getColorUnutrasnjiKrugBoja() {
		return colorUnutrasnjiKrugBoja;
	}

	public void setColorUnutrasnjiKrugBoja(Color colorUnutrasnjiKrugBoja) {
		this.colorUnutrasnjiKrugBoja = colorUnutrasnjiKrugBoja;
	}

	public Color getColorUnutrasnjost() {
		return colorUnutrasnjost;
	}

	public void setColorUnutrasnjost(Color colorUnutrasnjost) {
		this.colorUnutrasnjost = colorUnutrasnjost;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}
	
	public boolean isOK() {
		return isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}

	public JTextField getTxtUnos() {
		return txtWidth;
	}

	public void setTxtUnos(JTextField txtUnos) {
		this.txtWidth = txtUnos;
	}

	public JTextField getTxtUnos1() {
		return txtUnos1;
	}

	public void setTxtUnos1(JTextField txtUnos1) {
		this.txtUnos1 = txtUnos1;
	}

	public JLabel getLblUnesite() {
		return lblWidth;
	}

	public void setLblUnesite(JLabel lblUnesite) {
		this.lblWidth = lblUnesite;
	}

	public JLabel getLblUnesite1() {
		return Height;
	}

	public void setLblUnesite1(JLabel lblUnesite1) {
		this.Height = lblUnesite1;
	}
	


	public JButton getBtnBojaIviceUnutrasnji() {
		return btnColorICB;
	}

	public void setBtnBojaIviceUnutrasnji(JButton btnBojaIviceUnutrasnji) {
		this.btnColorICB = btnBojaIviceUnutrasnji;
	}

	
	
	public JLabel getLblIzaberiteBoju() {
		return lblColor1;
	}

	public void setLblIzaberiteBoju(JLabel lblIzaberiteBoju) {
		this.lblColor1 = lblIzaberiteBoju;
	}

	public JLabel getLblIzaberiteBojuUnutrasnjosti() {
		return lblInnerColor;
	}

	public void setLblIzaberiteBojuUnutrasnjosti(JLabel lblIzaberiteBojuUnutrasnjosti) {
		this.lblInnerColor = lblIzaberiteBojuUnutrasnjosti;
	}

	public JLabel getLblIzaberiteBojuIvice() {
		return lblColor3;
	}

	public void setLblIzaberiteBojuIvice(JLabel lblIzaberiteBojuIvice) {
		this.lblColor3 = lblIzaberiteBojuIvice;
	}
	public JLabel getLblInnerColor() {
		return lblInnerColor;
	}

	public void setLblInnerColor(JLabel lblInnerColor) {
		this.lblInnerColor = lblInnerColor;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgEnter dialog = new DlgEnter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgEnter() {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 335, 264);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(Color.LIGHT_GRAY);
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new MigLayout("", "[][grow]", "[][20][25][][25][25]"));
		{
			lblWidth = new JLabel("Width:");
			pnlCenter.add(lblWidth, "cell 0 0,alignx left");
		}
		{
			txtWidth = new JTextField();
			txtWidth.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
			txtWidth.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (!((c >= '0') && (c <= '9') ||
							(c == KeyEvent.VK_BACK_SPACE) ||
							(c == KeyEvent.VK_DELETE))) {
						e.consume();
					}
				}
			});
			pnlCenter.add(txtWidth, "cell 1 0,growx");
			txtWidth.setColumns(10);
		}
		{
			{
				Height = new JLabel("Height:");
				pnlCenter.add(Height, "cell 0 1,alignx left");
			}
		}
		txtUnos1 = new JTextField();
		txtUnos1.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
		txtUnos1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!((c >= '0') && (c <= '9') ||
						(c == KeyEvent.VK_BACK_SPACE) ||
						(c == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}
		});
		pnlCenter.add(txtUnos1, "cell 1 1,growx");
		txtUnos1.setColumns(10);
		{
			lblColor1 = new JLabel("Border color:");
			pnlCenter.add(lblColor1, "cell 0 2,alignx left");
		}
		{
			btnColorBC = new JButton("Color");
			btnColorBC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					c = JColorChooser.showDialog(null, "Color:", Color.RED);
				}
			});
			pnlCenter.add(btnColorBC, "cell 1 2,growx");
		}
		{
			lblInnerColor = new JLabel("Inner Color:");
			pnlCenter.add(lblInnerColor, "cell 0 3,alignx left");
		}
		{
			btnColorIC = new JButton("Color");
			btnColorIC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorUnutrasnjost = JColorChooser.showDialog(null, "Inner color:", Color.RED);
				}
			});
			pnlCenter.add(btnColorIC, "cell 1 3,growx");
		}
		{
			lblColor3 = new JLabel("Inner circle border color:");
			lblColor3.setVisible(false);
			pnlCenter.add(lblColor3, "cell 0 4");
		}
		{
			btnColorICB = new JButton("Color");
			btnColorICB.setVisible(false);
			btnColorICB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorIviceUnutrasnjiKrug = JColorChooser.showDialog(null, "Inner circle boreder:", Color.RED);
				}
			});
			pnlCenter.add(btnColorICB, "cell 1 4,growx");
		}
		{
			JPanel pnlBtn = new JPanel();
			pnlBtn.setBackground(Color.GRAY);
			pnlBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBtn, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (txtWidth.getText().isEmpty() || txtUnos1.getText().isEmpty()) {
							isOK = false;
							setVisible(true);
							JOptionPane.showMessageDialog(null, "Empty fields detected!","Error", JOptionPane.WARNING_MESSAGE);
						} else {
							isOK = true;
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				pnlBtn.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				pnlBtn.add(cancelButton);
			}
		}
	}

	public JButton getBtnColorIC() {
		return btnColorIC;
	}

	public void setBtnColorIC(JButton btnColorIC) {
		this.btnColorIC = btnColorIC;
	}

	public JButton getBtnColorBC() {
		return btnColorBC;
	}

	public void setBtnColorBC(JButton btnColorBC) {
		this.btnColorBC = btnColorBC;
	}

}
