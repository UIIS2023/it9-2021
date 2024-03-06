package drawingApp;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {
	public Drawing() {
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Drawing frame");
		frame.setSize(800, 600);
		JPanel drawing = new Drawing();
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
	}

	public void paint(Graphics g) {
		/*
		 * Point p = new Point(10,15); p.draw(g); g.setColor(Color.red); Line line = new
		 * Line(new Point(20,30), new Point(40,50)); line.draw(g);
		 * g.setColor(Color.black); Donut donut = new Donut(new Point(60,70), 50, 40,
		 * true); donut.draw(g);
		 */

		// Vezbe 8
		Point p = new Point(50, 50);

		Line l1 = new Line(new Point(100, 100), new Point(200, 200));

		Circle c1 = new Circle(new Point(500, 100), 80);

		Donut d1 = new Donut(new Point(800, 100), 50, 25, false);

		Rectangle k1 = new Rectangle(new Point(500, 500), 50, 50);

		// Zadatak 1.
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(p);
		shapes.add(l1);
		shapes.add(c1);
		shapes.add(d1);
		shapes.add(k1);
		Iterator<Shape> it = shapes.iterator();
		while (it.hasNext()) {
			// it.next() samo 1 uzima istu vrednost
			Shape sh = it.next();
			sh.moveBy(10, 0);
			System.out.println(sh);
		}
		// Collections.sort(shapes);

		// Zadatak 2.
		shapes.get(3).draw(g);

		shapes.get(shapes.size() - 1).draw(g);

		shapes.remove(1);
		// pomera se lista
		shapes.get(1).draw(g);
		
		shapes.get(3).draw(g);

		shapes.add(3, l1);
		
		//Exception
		try {
			c1.setRadius(-10);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		it = shapes.iterator();
		while (it.hasNext()) {
			Shape sh = it.next();
			sh.moveBy(10, 0);
			sh.setSelected(true);
			sh.draw(g);
		}

	}
}