package drawingApp;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

public class PnlDrawing extends JPanel {

	private DrawingApp frame;
	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	
	private Point startPoint;
	private Shape selected;

	public PnlDrawing(DrawingApp frame) {
		this.frame = frame;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				misKlik(arg0);
			}
		});

	}

	protected void misKlik(MouseEvent e) {
		Shape oblik = null;
		int x1 = 0;
		int y1 = 0;
		
		//select
		if (frame.getTglbtnSelektuj().isSelected()) {
			selected = null;
			Iterator<Shape> i = shapes.iterator();
			while (i.hasNext()) {
				Shape shape = i.next();
				shape.setSelected(false);
				if (shape.contains(e.getX(), e.getY())) {
					selected = shape;
				}
			}
			if (selected != null) {
				selected.setSelected(true);
			}
			
			//tacka
			
		} else if (frame.getTglbtnTacka().isSelected()) {
			DlgPointLine unos = new DlgPointLine();
			unos.setTitle("Point");
			unos.pack();
			unos.setVisible(true);
			if (unos.isOK()) {
				Point p = new Point(e.getX(), e.getY(), false);
				p.setColor(unos.getC());
				oblik = p;
			}
			
			//linija
			
		} else if (frame.getTglbtnLinija().isSelected()) {
			DlgPointLine unos = new DlgPointLine();
			if (startPoint == null) {
				startPoint = new Point(e.getX(), e.getY());
			} else {
				Point endPoint = new Point(e.getX(), e.getY());
				unos.setTitle("Line");
				unos.pack();
				unos.setVisible(true);
				if (unos.isOK()) {
					Line l = new Line(startPoint, endPoint, false);
					l.setColor(unos.getC());
					oblik = l;
				}
				startPoint = null;
			}
			
			//pravougaonik
			
		} else if (frame.getTglbtnPravougaonik().isSelected()) {
			DlgEnter unos = new DlgEnter();
			unos.pack();
			unos.setTitle("Rectangle");
			unos.setVisible(true);
			if (unos.isOK()) {
				x1 = Integer.parseInt(unos.getTxtUnos().getText());
				y1 = Integer.parseInt(unos.getTxtUnos1().getText());
			}
			Rectangle r = new Rectangle(new Point(e.getX(), e.getY()), y1, x1, false);
			r.setColor(unos.getC());
			r.setColorUnutrasnjost(unos.getColorUnutrasnjost());
			try {
				oblik = r;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		//krug entner
			
		} else if (frame.getTglbtnKrug().isSelected()) {
			DlgCircleEnter krug = new DlgCircleEnter();
			krug.pack();
			krug.setVisible(true);
			if (krug.isOK()) {
				x1 = Integer.parseInt(krug.getTxtPoluprecnik().getText());
			}
			Circle c = new Circle(new Point(e.getX(), e.getY()), x1, false);
			c.setColor(krug.getC());
			c.setColorUnutrasnjost(krug.getColorUnutrasnjost());
			try {
				oblik = c;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			//donut
			
		} else if (frame.getTglbtnKrugSaRupom().isSelected()) {
			DlgEnter unos = new DlgEnter();
			unos.getLblUnesite().setText("Circle radius:");
			unos.getLblUnesite1().setText("Inner circle radius:");
			unos.setTitle("Donut");
			unos.getLblIzaberiteBoju().setText("Border color:");
			unos.getLblIzaberiteBojuUnutrasnjosti().setText("Circle color:");
		
			unos.getLblIzaberiteBojuIvice().setVisible(true);
			unos.getBtnBojaIviceUnutrasnji().setVisible(true);
			
			unos.pack();
			unos.setVisible(true);
			if (unos.isOK()) {
				x1 = Integer.parseInt(unos.getTxtUnos().getText());
				y1 = Integer.parseInt(unos.getTxtUnos1().getText());
			}
			Donut d = new Donut(new Point(e.getX(), e.getY()), x1, y1, false);
			d.setColor(unos.getC());
			d.setColorIviceMali(unos.getColorIviceUnutrasnjiKrug());
			d.setColorUnutrasnostMali(unos.getColorUnutrasnjiKrugBoja());
			d.setColorUnutrasnjostVeliki(unos.getColorUnutrasnjost());
			try {
				oblik = d;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if (oblik != null) {
			shapes.add(oblik);
		}
		repaint();
	}
	
//***************************************************************************************
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> i = shapes.iterator();
		while (i.hasNext()) {
			i.next().draw(g);
		}
	}

	public Shape getSelected() {
		return selected;
	}

	public void setSelected(Shape selected) {
		this.selected = selected;
	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}
}
