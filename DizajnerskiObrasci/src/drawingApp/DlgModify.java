package drawingApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;

public class DlgModify extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtHeight;
	private JTextField txtWidth;
	private JTextField txtX;
	private JTextField txtY;
	private JLabel lblHeight;
	private JLabel lblWidth;
	private JLabel lblX;
	private JLabel lblY;
	private boolean isOK;
	private JLabel lblColor1;
	private JButton btnColor;
	private JLabel lblColor2;
	private JButton btnInnerColor;
	private Color c;
	private Color colorUnutrasnjost;
	private Color colorIviceUnutrasnjiKrug;
	private Color colorUnutrasnjiKrugBoja;
	private JLabel lblColor3;
	private JButton btnInnverColor2;
	private JLabel lblColor4;
	private JButton btnBojaUnutrasnjegKruga;
	
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
		return txtHeight;
	}

	public void setTxtUnos(JTextField txtUnos) {
		this.txtHeight = txtUnos;
	}

	public JTextField getTxtUnos1() {
		return txtWidth;
	}

	public void setTxtUnos1(JTextField txtUnos1) {
		this.txtWidth = txtUnos1;
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	
	
	public JLabel getLblX() {
		return lblX;
	}

	public void setLblX(JLabel lblX) {
		this.lblX = lblX;
	}

	public JLabel getLblY() {
		return lblY;
	}

	public void setLblY(JLabel lblY) {
		this.lblY = lblY;
	}

	public JLabel getLblUnesite() {
		return lblHeight;
	}

	public void setLblUnesite(JLabel lblUnesite) {
		this.lblHeight = lblUnesite;
	}

	public JLabel getLblUnesite1() {
		return lblWidth;
	}

	public void setLblUnesite1(JLabel lblUnesite1) {
		this.lblWidth = lblUnesite1;
	}
	
	public JLabel getLblIzaberiteBojuUnutrasnjosti() {
		return lblColor2;
	}

	public void setLblIzaberiteBojuUnutrasnjosti(JLabel lblIzaberiteBojuUnutrasnjosti) {
		this.lblColor2 = lblIzaberiteBojuUnutrasnjosti;
	}

	public JButton getBtnBojaUnutrasnjosti() {
		return btnInnerColor;
	}

	public void setBtnBojaUnutrasnjosti(JButton btnBojaUnutrasnjosti) {
		this.btnInnerColor = btnBojaUnutrasnjosti;
	}

	public JLabel getLblIzaberiteBoju() {
		return lblColor1;
	}

	public void setLblIzaberiteBoju(JLabel lblIzaberiteBoju) {
		this.lblColor1 = lblIzaberiteBoju;
	}

	public JLabel getLblIzaberiteBojuIvice() {
		return lblColor3;
	}

	public void setLblIzaberiteBojuIvice(JLabel lblIzaberiteBojuIvice) {
		this.lblColor3 = lblIzaberiteBojuIvice;
	}

	public JButton getBtnBojaIviceUnutrasnjegKruga() {
		return btnInnverColor2;
	}

	public void setBtnBojaIviceUnutrasnjegKruga(JButton btnBojaIviceUnutrasnjegKruga) {
		this.btnInnverColor2 = btnBojaIviceUnutrasnjegKruga;
	}

	public JLabel getLblIzaberiteBojuUnutrasnjeg() {
		return lblColor4;
	}

	public void setLblIzaberiteBojuUnutrasnjeg(JLabel lblIzaberiteBojuUnutrasnjeg) {
		this.lblColor4 = lblIzaberiteBojuUnutrasnjeg;
	}

	public JButton getBtnBojaUnutrasnjegKruga() {
		return btnBojaUnutrasnjegKruga;
	}

	public void setBtnBojaUnutrasnjegKruga(JButton btnBojaUnutrasnjegKruga) {
		this.btnBojaUnutrasnjegKruga = btnBojaUnutrasnjegKruga;
	}
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgModify dialog = new DlgModify();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgModify() {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 271, 322);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(Color.LIGHT_GRAY);
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlCenter, BorderLayout.NORTH);
		pnlCenter.setLayout(new MigLayout("", "[68px][86px,grow][1px][1px]", "[][][20px][][][][][]"));
		{
			lblX = new JLabel("X:");
			pnlCenter.add(lblX, "cell 0 0,alignx center");
		}
		{
			txtX = new JTextField();
			txtX.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
			txtX.addKeyListener(new KeyAdapter() {
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
			pnlCenter.add(txtX, "cell 1 0,growx");
			txtX.setColumns(10);
		}
		{
			lblY = new JLabel("Y:");
			pnlCenter.add(lblY, "cell 0 1,alignx center");
		}
		{
			txtY = new JTextField();
			txtY.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
			txtY.addKeyListener(new KeyAdapter() {
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
			pnlCenter.add(txtY, "cell 1 1,growx");
			txtY.setColumns(10);
		}
		{
			lblHeight = new JLabel("Enter height:");
			pnlCenter.add(lblHeight, "cell 0 2,alignx center,aligny center");
		}
		{
			txtHeight = new JTextField();
			txtHeight.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
			txtHeight.addKeyListener(new KeyAdapter() {
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
			txtHeight.setColumns(10);
			pnlCenter.add(txtHeight, "cell 1 2,growx,aligny top");
		}
		{
			lblWidth = new JLabel("Enter width:");
			pnlCenter.add(lblWidth, "cell 0 3,alignx center,aligny center");
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
			txtWidth.setColumns(10);
			pnlCenter.add(txtWidth, "cell 1 3,growx,aligny top");
		}
		{
			lblColor1 = new JLabel("Boredr color:");
			pnlCenter.add(lblColor1, "cell 0 4,alignx center");
		}
		{
			btnColor = new JButton("Color");
			btnColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c = JColorChooser.showDialog(null, "Choose color:", Color.RED);
				}
			});
			pnlCenter.add(btnColor, "cell 1 4,growx");
		}
		{
			lblColor2 = new JLabel("Inner color:");
			pnlCenter.add(lblColor2, "cell 0 5,alignx center");
		}
		{
			btnInnerColor = new JButton("Color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorUnutrasnjost = JColorChooser.showDialog(null, "Choose inner color:", Color.RED);
				}
			});
			pnlCenter.add(btnInnerColor, "cell 1 5,growx");
		}
		{
			lblColor3 = new JLabel("Inner border color:");
			lblColor3.setVisible(false);
			pnlCenter.add(lblColor3, "cell 0 6");
		}
		{
			btnInnverColor2 = new JButton("Color");
			btnInnverColor2.setVisible(false);
			btnInnverColor2.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					colorIviceUnutrasnjiKrug = JColorChooser.showDialog(null, "Choose border color:", Color.RED);
				}
			});
			pnlCenter.add(btnInnverColor2, "cell 1 6,growx");
		}
		{
			lblColor4 = new JLabel("Inner circle color:");
			lblColor4.setVisible(false);
			pnlCenter.add(lblColor4, "cell 0 7");
		}
		{
			btnBojaUnutrasnjegKruga = new JButton("Color");
			btnBojaUnutrasnjegKruga.setVisible(false);
			btnBojaUnutrasnjegKruga.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					colorUnutrasnjiKrugBoja = JColorChooser.showDialog(null, "Choose inner circle:", Color.RED);
				}
			});
			pnlCenter.add(btnBojaUnutrasnjegKruga, "cell 1 7,growx");
		}
		{
			JPanel pnlBtn = new JPanel();
			pnlBtn.setBackground(Color.GRAY);
			pnlBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBtn, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtHeight.getText().isEmpty() || txtWidth.getText().isEmpty() || txtX.getText().isEmpty() || txtY.getText().isEmpty()) {
							isOK = false;
							setVisible(true);
							JOptionPane.showMessageDialog(null, "Empty field detected","Error", JOptionPane.WARNING_MESSAGE);
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

	public JLabel getLblColor4() {
		return lblColor4;
	}

	public void setLblColor4(JLabel lblColor4) {
		this.lblColor4 = lblColor4;
	}

}
