package drawingApp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import drawingApp.Shape;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import drawingApp.PnlDrawing;


import javax.swing.JToggleButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.SystemColor;

public class DrawingApp extends JFrame {
	
	private JPanel contentPane;
	private PnlDrawing draw = new PnlDrawing(this);
	//kontrola dugmadi, samo 1 ukljucen
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private JToggleButton tglbtnSelect;
	private JToggleButton btnPoint, tglbtnLine, tglbtnRec, tglbtnCircle, tglbtnDonut;
	private JPanel pnlS, pnlN;
	private JButton btnDelete;
	private boolean cekiranTacka, cekiranLinija, cekiranPravougaonik, cekiranKrug, cekiranKrugSaRupom, cekiranSelektuj;
	private JButton btnModify;

	public JToggleButton getTglbtnSelektuj() {
		return tglbtnSelect;
	}

	public void setTglbtnSelektuj(JToggleButton tglbtnSelektuj) {
		this.tglbtnSelect = tglbtnSelektuj;
	}

	public JToggleButton getTglbtnTacka() {
		return btnPoint;
	}

	public void setTglbtnTacka(JToggleButton tglbtnTacka) {
		this.btnPoint = tglbtnTacka;
	}

	public JToggleButton getTglbtnLinija() {
		return tglbtnLine;
	}

	public void setTglbtnLinija(JToggleButton tglbtnLinija) {
		this.tglbtnLine = tglbtnLinija;
	}

	public JToggleButton getTglbtnPravougaonik() {
		return tglbtnRec;
	}

	public void setTglbtnPravougaonik(JToggleButton tglbtnPravougaonik) {
		this.tglbtnRec = tglbtnPravougaonik;
	}

	public JToggleButton getTglbtnKrug() {
		return tglbtnCircle;
	}

	public void setTglbtnKrug(JToggleButton tglbtnKrug) {
		this.tglbtnCircle = tglbtnKrug;
	}

	public JToggleButton getTglbtnKrugSaRupom() {
		return tglbtnDonut;
	}

	public void setTglbtnKrugSaRupom(JToggleButton tglbtnKrugSaRupom) {
		this.tglbtnDonut = tglbtnKrugSaRupom;
	}

	public static void main(String[] args) {
		DrawingApp frame = new DrawingApp();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public DrawingApp() {
		setTitle("Drawing | Marko Karapandzic IT9-2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 550, 455);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		draw.setForeground(Color.BLACK);
		draw.setBackground(new Color(191, 205, 219));

		contentPane.add(draw);

		pnlS = new JPanel();
		pnlS.setBackground(Color.GRAY);
		pnlS.setForeground(Color.RED);
		contentPane.add(pnlS, BorderLayout.SOUTH);

		tglbtnSelect = new JToggleButton("Select");
		tglbtnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranSelektuj == false) {
					cekiranSelektuj = true;
					cekiranTacka = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranLinija = false;
				} else {
					buttonGroup.clearSelection();
					cekiranSelektuj = false;
				}
			}
		});
		pnlS.add(tglbtnSelect);
		buttonGroup.add(tglbtnSelect);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				brisi();
			}
		});

		btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modifikuj();
			}
		});
		pnlS.add(btnModify);
		pnlS.add(btnDelete);

		pnlN = new JPanel();
		pnlN.setBackground(Color.GRAY);
		pnlN.setForeground(Color.RED);
		contentPane.add(pnlN, BorderLayout.NORTH);

		btnPoint = new JToggleButton("Point");
		btnPoint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranTacka == false) {
					cekiranTacka = true;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranTacka = false;
				}
			}
		});
		buttonGroup.add(btnPoint);
		pnlN.add(btnPoint);
		
		//***************************************************************

		tglbtnLine = new JToggleButton("Line");
		tglbtnLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranLinija == false) {
					cekiranLinija = true;
					cekiranTacka = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranLinija = false;
				}
			}
		});
		buttonGroup.add(tglbtnLine);
		pnlN.add(tglbtnLine);

		tglbtnRec = new JToggleButton("Rectangle");
		tglbtnRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranPravougaonik == false) {
					cekiranPravougaonik = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranKrug = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranPravougaonik = false;
				}
			}
		});
		buttonGroup.add(tglbtnRec);
		pnlN.add(tglbtnRec);

		tglbtnCircle = new JToggleButton("Circle");
		tglbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranKrug == false) {
					cekiranKrug = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrugSaRupom = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranKrug = false;
				}
			}
		});
		
		buttonGroup.add(tglbtnCircle);
		pnlN.add(tglbtnCircle);

		tglbtnDonut = new JToggleButton("Donut");
		tglbtnDonut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cekiranKrugSaRupom == false) {
					cekiranKrugSaRupom = true;
					cekiranTacka = false;
					cekiranLinija = false;
					cekiranPravougaonik = false;
					cekiranKrug = false;
					cekiranSelektuj = false;
				} else {
					buttonGroup.clearSelection();
					cekiranKrugSaRupom = false;
				}
			}
		});
		buttonGroup.add(tglbtnDonut);
		pnlN.add(tglbtnDonut);

	}

	protected void brisi() {
		Shape brisi = (Shape) draw.getSelected();
		if (brisi != null) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm",
					JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				draw.getShapes().remove(brisi);
				draw.setSelected(null);
			} 
		} else {
			JOptionPane.showMessageDialog(null, "Shape must be selected!","Error", JOptionPane.WARNING_MESSAGE);
		}
		draw.repaint();
	}

	protected void modifikuj() {
		int korX = 0, korY = 0, x1 = 0, y1 = 0;
		Shape selected = (Shape) draw.getSelected();
		if (selected != null) {
			
			if (selected instanceof Point) {
				Point p = (Point) selected;
				
				DlgPointMod izmena = new DlgPointMod();
				
				izmena.getTxtX().setText("" + p.getX());
				izmena.getTxtY().setText("" + p.getY());
				izmena.pack();
				izmena.setVisible(true);
				if (izmena.isOK()) {
					korX = Integer.parseInt(izmena.getTxtX().getText());
					korY = Integer.parseInt(izmena.getTxtY().getText());
					p = new Point(korX, korY);
					p.setColor(izmena.getC());
					draw.getShapes().set(draw.getShapes().indexOf(selected), p);
				}
				
				
			} else if (selected instanceof Line) {
				Line l = (Line) selected;
				DlgModify izmena = new DlgModify();
				izmena.setTitle("Line");
				izmena.getLblX().setText("Coordinate X of start point:");
				izmena.getLblY().setText("Coordinate Y of start point:");
				izmena.getLblUnesite().setText("Coordinate X of end poitn:");
				izmena.getLblUnesite1().setText("Coordinate Y of end point:");
				izmena.getTxtX().setText("" + l.getStartPoint().getX());
				izmena.getTxtY().setText("" + l.getStartPoint().getY());
				izmena.getTxtUnos().setText("" + l.getEndPoint().getX());
				izmena.getTxtUnos1().setText("" + l.getEndPoint().getY());
				izmena.getLblIzaberiteBojuUnutrasnjosti().setVisible(false);
				izmena.getBtnBojaUnutrasnjosti().setVisible(false);
				izmena.pack();
				izmena.setVisible(true);
				
				if (izmena.isOK()) {
					korX = Integer.parseInt(izmena.getTxtX().getText());
					korY = Integer.parseInt(izmena.getTxtY().getText());
					x1 = Integer.parseInt(izmena.getTxtUnos().getText());
					y1 = Integer.parseInt(izmena.getTxtUnos1().getText());
					l = new Line(new Point(korX, korY), new Point(x1, y1));
					l.setColor(izmena.getC());
					draw.getShapes().set(draw.getShapes().indexOf(selected), l);
				}
			} else if (selected instanceof Rectangle) {
				Rectangle r = (Rectangle) selected;
				DlgModify izmena = new DlgModify();
				izmena.setTitle("Rectangle");
				izmena.getTxtX().setText("" + r.getUpperLeftPoint().getX());
				izmena.getTxtY().setText("" + r.getUpperLeftPoint().getY());
				izmena.getTxtUnos().setText("" + r.getHeight());
				izmena.getTxtUnos1().setText("" + r.getWidth());
				izmena.pack();
				izmena.setVisible(true);
				if (izmena.isOK()) {
					korX = Integer.parseInt(izmena.getTxtX().getText());
					korY = Integer.parseInt(izmena.getTxtY().getText());
					x1 = Integer.parseInt(izmena.getTxtUnos().getText());
					y1 = Integer.parseInt(izmena.getTxtUnos1().getText());
					r = new Rectangle(new Point(korX, korY), x1, y1);
					r.setColor(izmena.getC());
					r.setColorUnutrasnjost(izmena.getColorUnutrasnjost());
					draw.getShapes().set(draw.getShapes().indexOf(selected), r);
				}
			} else if (selected instanceof Donut) {
				Donut d = (Donut) selected;
				DlgModify izmena = new DlgModify();
				izmena.getLblUnesite().setText("Enter circle radius:");
				izmena.getLblUnesite1().setText("Enter inner circle radius:");
				izmena.setTitle("Donut");
				izmena.getLblIzaberiteBoju().setText("Border color:");
				izmena.getLblIzaberiteBojuUnutrasnjosti().setText("Circle color:");
				izmena.getLblIzaberiteBojuIvice().setVisible(true);
				izmena.getLblIzaberiteBojuUnutrasnjeg().setVisible(true);
				izmena.getBtnBojaIviceUnutrasnjegKruga().setVisible(true);
				izmena.getBtnBojaUnutrasnjegKruga().setVisible(true);
				izmena.getTxtX().setText("" + d.getCenter().getX());
				izmena.getTxtY().setText("" + d.getCenter().getY());
				izmena.getTxtUnos().setText("" + d.getRadius());
				izmena.getTxtUnos1().setText("" + d.getInnerRadius());
				izmena.pack();
				izmena.setVisible(true);
				if (izmena.isOK()) {
					korX = Integer.parseInt(izmena.getTxtX().getText());
					korY = Integer.parseInt(izmena.getTxtY().getText());
					x1 = Integer.parseInt(izmena.getTxtUnos().getText());
					y1 = Integer.parseInt(izmena.getTxtUnos1().getText());
					d = new Donut(new Point(korX, korY), x1, y1);
					d.setColor(izmena.getC());
					d.setColorIviceMali(izmena.getColorIviceUnutrasnjiKrug());
					d.setColorUnutrasnostMali(izmena.getColorUnutrasnjiKrugBoja());
					d.setColorUnutrasnjostVeliki(izmena.getColorUnutrasnjost());
					draw.getShapes().set(draw.getShapes().indexOf(selected), d);
				}
			} else if (selected instanceof Circle) {
				Circle c = (Circle) selected;
				DlgCircleMod izmena = new DlgCircleMod();
				izmena.getTxtX().setText("" + c.getCenter().getX());
				izmena.getTxtY().setText("" + c.getCenter().getY());
				izmena.getTxtPoluprecnik().setText("" + c.getRadius());
				izmena.pack();
				izmena.setVisible(true);
				if (izmena.isOK()) {
					korX = Integer.parseInt(izmena.getTxtX().getText());
					korY = Integer.parseInt(izmena.getTxtY().getText());
					x1 = Integer.parseInt(izmena.getTxtPoluprecnik().getText());
					c = new Circle(new Point(korX, korY), x1);
					c.setColor(izmena.getC());
					c.setColorUnutrasnjost(izmena.getColorUnutrasnjost());
					draw.getShapes().set(draw.getShapes().indexOf(selected), c);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Shape must be selected!","Error", JOptionPane.WARNING_MESSAGE);
		}
		draw.repaint();
		draw.setSelected(null);
		buttonGroup.clearSelection();
	}
}
