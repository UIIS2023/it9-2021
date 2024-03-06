package drawingApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalityType;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;

public class DlgCircleMod extends JDialog {

	private final JPanel pnlCenter = new JPanel();
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private boolean isOK;

	private Color c;
	private Color colorUnutrasnjost;
	
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

	public JTextField getTxtPoluprecnik() {
		return txtRadius;
	}

	public void setTxtPoluprecnik(JTextField txtPoluprecnik) {
		this.txtRadius = txtPoluprecnik;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircleMod dialog = new DlgCircleMod();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircleMod() {
		setResizable(false);
		setModal(true);
		setTitle("Circle");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 247, 233);
		getContentPane().setLayout(new BorderLayout());
		pnlCenter.setBackground(Color.LIGHT_GRAY);
		pnlCenter.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlCenter, BorderLayout.CENTER);
		pnlCenter.setLayout(new MigLayout("", "[68px][235px]", "[20px][20px][20px][25][25]"));
		
		JLabel label = new JLabel("X:");
		pnlCenter.add(label, "cell 0 0,alignx center,aligny center");
		
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
		txtX.setColumns(10);
		pnlCenter.add(txtX, "cell 1 0,growx,aligny top");
		
		JLabel label_1 = new JLabel("Y:");
		pnlCenter.add(label_1, "cell 0 1,alignx center,aligny center");
		
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
		txtY.setColumns(10);
		pnlCenter.add(txtY, "cell 1 1,growx,aligny top");
		
		JLabel lblR = new JLabel("Enter radius:");
		pnlCenter.add(lblR, "cell 0 2,alignx left,aligny center");
		
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
		txtRadius.setColumns(10);
		pnlCenter.add(txtRadius, "cell 1 2,growx,aligny top");
		
		JLabel lblColor1 = new JLabel("Chose color:");
		pnlCenter.add(lblColor1, "cell 0 3");
		
		JButton btnColor = new JButton("Color");
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = JColorChooser.showDialog(null, "Color:", Color.RED);
			}
		});
		pnlCenter.add(btnColor, "cell 1 3,growx");
		
		JLabel lblColor2 = new JLabel("Chose inner color:");
		pnlCenter.add(lblColor2, "cell 0 4");
		
		JButton btnColor2 = new JButton("Color");
		btnColor2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorUnutrasnjost = JColorChooser.showDialog(null, "Inner color:", Color.RED);
			}
		});
		pnlCenter.add(btnColor2, "cell 1 4,growx");
		{
			JPanel pnlBtn = new JPanel();
			pnlBtn.setBackground(Color.GRAY);
			pnlBtn.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(pnlBtn, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (txtRadius.getText().isEmpty() || txtX.getText().isEmpty() || txtY.getText().isEmpty()) {
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
}
