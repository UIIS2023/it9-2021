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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dialog.ModalityType;

public class DlgCircleEnter extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtRadius;

	private boolean isOK;
	JLabel lblInnerColor;
	JLabel lblColorB;

	
	JButton btnColorIN;
	JButton btnColorBC;
	
	private Color c;
	private Color colorUnutrasnjost;
	
	public JLabel getLblInnerColor() {
		return lblInnerColor;
	}

	public void setLblInnerColor(JLabel lblInnerColor) {
		this.lblInnerColor = lblInnerColor;
	}

	public JButton getBtnColorBC() {
		return btnColorBC;
	}

	public void setBtnColorBC(JButton btnColorBC) {
		this.btnColorBC = btnColorBC;
	}
	public JLabel getLblColorB() {
		return lblColorB;
	}

	public void setLblColorB(JLabel lblColorB) {
		this.lblColorB = lblColorB;
	}

	public JButton getBtnColorIN() {
		return btnColorIN;
	}

	public void setBtnColorIN(JButton btnColorIN) {
		this.btnColorIN = btnColorIN;
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
	
	public JTextField getTxtPoluprecnik() {
		return txtRadius;
	}

	public void setTxtPoluprecnik(JTextField txtPoluprecnik) {
		this.txtRadius = txtPoluprecnik;
	}

	public boolean isOK() {
		return isOK;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircleEnter dialog = new DlgCircleEnter();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircleEnter() {
		setResizable(false);
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Circle");
		setBounds(100, 100, 202, 193);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(Color.LIGHT_GRAY);
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new MigLayout("", "[][grow]", "[][25][25]"));
		{
			JLabel lblRadius = new JLabel("Radius:");
			pnlCenter.add(lblRadius, "cell 0 0,alignx left");
		}
		{
			txtRadius = new JTextField();
			txtRadius.setTransferHandler(null); //da bismo zabranili pastovanje u textfield, da ne bi korisnik kopirao neki string
			txtRadius.addKeyListener(new KeyAdapter() {
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
			pnlCenter.add(txtRadius, "cell 1 0,growx");
			txtRadius.setColumns(10);
		}
		{
			 lblColorB = new JLabel("Border color:");
			pnlCenter.add(lblColorB, "cell 0 1");
		}
		{
			 btnColorBC = new JButton("Color");
			btnColorBC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					c = JColorChooser.showDialog(null, "Color", Color.RED);
				}
			});
			pnlCenter.add(btnColorBC, "cell 1 1,growx");
		}
		{
			 lblInnerColor = new JLabel("Inner color:");
			pnlCenter.add(lblInnerColor, "cell 0 2");
		}
		{
			 btnColorIN = new JButton("Color");
			btnColorIN.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					colorUnutrasnjost = JColorChooser.showDialog(null, "Color", Color.RED);
				}
			});
			pnlCenter.add(btnColorIN, "cell 1 2,growx");
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
						if (txtRadius.getText().isEmpty()) {
							isOK = false;
							setVisible(true);
							JOptionPane.showMessageDialog(null, "Enter radius!","Error", JOptionPane.WARNING_MESSAGE);
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
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				pnlBtn.add(cancelButton);
			}
		}
	}

}
