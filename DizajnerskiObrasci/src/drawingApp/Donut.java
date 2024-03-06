package drawingApp;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	private Color color;
	private Color colorUnutrasnjostVeliki;
	private Color colorUnutrasnostMali;
	private Color colorIviceMali;

	public Donut() {

	}

	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setComposite(AlphaComposite.SrcOver.derive(1.0f));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Ellipse2D outerCircle = new Ellipse2D.Double(getCenter().getX() - getRadius(), getCenter().getY() - getRadius(),
				2 * getRadius(), 2 * getRadius());
		Ellipse2D innerCircle = new Ellipse2D.Double(getCenter().getX() - innerRadius, getCenter().getY() - innerRadius,
				2 * innerRadius, 2 * innerRadius);

		Area circle = new Area(outerCircle);
		

		circle.subtract(new Area(innerCircle));
		g2d.setColor(getColorUnutrasnjostVeliki());
		g2d.fill(circle);
		g2d.setColor(getColor());
		g2d.draw(circle);
		

		
		

		//g2d.dispose();

		if (isSelected()) {
			//g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - getRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + getRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + innerRadius - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - innerRadius - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + innerRadius - 2, 4, 4);
			//g.setColor(Color.black);
		}
	}

	public boolean contains(int x, int y) {
		double dFromCenter = this.getCenter().distance(x, y);
		return dFromCenter > innerRadius && super.contains(x, y);
	}

	public boolean contains(Point p) {
		double dFromCenter = this.getCenter().distance(p.getX(), p.getY());
		return dFromCenter > innerRadius && super.contains(p.getX(), p.getY());
	}

	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut d = (Donut) obj;
			if (this.getCenter().equals(d.getCenter()) && this.getRadius() == d.getRadius()
					&& this.innerRadius == d.getInnerRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Shape clone() {
		Donut d = new Donut();
		d.setCenter(new Point(getCenter().getX(), getCenter().getY()));
		try {
			d.setRadius(getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.setInnerRadius(getInnerRadius());
		d.setColor(getColor());
		d.setColorIviceMali(getColorIviceMali());
		d.setColorUnutrasnjost(getColorUnutrasnjost());
		d.setColorUnutrasnjostVeliki(getColorUnutrasnjostVeliki());
		d.setColorUnutrasnostMali(getColorUnutrasnostMali());
		d.setSelected(isSelected());
		return d;
	}

	public Color getColorIviceMali() {
		return colorIviceMali;
	}

	public void setColorIviceMali(Color colorIviceMali) {
		this.colorIviceMali = colorIviceMali;
	}

	public Color getColorUnutrasnjostVeliki() {
		return colorUnutrasnjostVeliki;
	}

	public void setColorUnutrasnjostVeliki(Color colorUnutrasnjostVeliki) {
		this.colorUnutrasnjostVeliki = colorUnutrasnjostVeliki;
	}

	public Color getColorUnutrasnostMali() {
		return colorUnutrasnostMali;
	}

	public void setColorUnutrasnostMali(Color colorUnutrasnostMali) {
		this.colorUnutrasnostMali = colorUnutrasnostMali;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getInnerRadius() {
		return innerRadius;
	}

	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}

	@Override
	public String toString() {
		return "Donut: Center = (" + getCenter().getX() + "," + getCenter().getY() + ") ; Radius = " + getRadius()
				+ " ; Inner radius = " + getInnerRadius() + " ; Border color = " + getColor() + " ; Inner color = "
				+ getColorUnutrasnjostVeliki();

	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Donut) {
			return (int) (this.area() - ((Donut) obj).area());
		}
		return 0;
	}
}
