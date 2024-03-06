package drawingApp;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Shape {

	private Point center;
	protected int radius;
	private Color color;
	private Color colorUnutrasnjost;
	
	public Circle() {

	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, boolean selected) {
		this(center, radius);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawOval(this.getCenter().getX() - this.radius, getCenter().getY() - getRadius(), this.getRadius()*2, this.getRadius()*2);
		
		g.setColor(getColorUnutrasnjost());
		g.fillOval(this.getCenter().getX()+1 - this.radius, getCenter().getY()+1 - getRadius(), (this.getRadius()-1)*2, (this.getRadius()-1)*2);
		
		if (isSelected()) {
			g.drawRect(getCenter().getX() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() + getRadius() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() - getRadius() - 3, getCenter().getY() - 3, 6, 6);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() + getRadius() - 3, 6, 6);
			g.drawRect(getCenter().getX() - 3, getCenter().getY() - getRadius() - 3, 6, 6);
		}
	}

	
	public boolean contains(int x, int y) {
		return this.getCenter().distance(x, y) <= radius ;
	}
	
	public boolean contains(Point p) {
		return p.distance(p.getX(), p.getY()) <= radius;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Circle) {
			Circle c = (Circle) obj;
			if (this.center.equals(c.getCenter()) && this.radius == c.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public Color getColorUnutrasnjost() {
		return colorUnutrasnjost;
	}

	public void setColorUnutrasnjost(Color colorUnutrasnjost) {
		this.colorUnutrasnjost = colorUnutrasnjost;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public double area() {
		return radius * radius * Math.PI;
	}
	
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) throws Exception {
		if (radius >= 0) {
			this.radius = radius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
	}
	
	public String toString() {
		return "Circle: Center = (" + getCenter().getX() +","+getCenter().getY() +") ; Radius = " + getRadius() + " ; Border color = "+getColor()+" ; Inner color = "+getColorUnutrasnjost(); 
	}

	@Override
	protected void moveBy(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int compareTo(Object obj) {
		if(obj instanceof Circle) {
			return (int)(this.area() - ((Circle)obj).area());
		}
		return 0;
	}

	@Override
	public Shape clone() {
		Circle c = new Circle();
		/*c.getCenter().setX(this.getCenter().getX());
		c.getCenter().setY(this.getCenter().getY());*/
		c.setCenter(new Point(getCenter().getX(),getCenter().getY()));
		try {
			c.setRadius(getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.setColor(getColor());
		c.setColorUnutrasnjost(getColorUnutrasnjost());
		c.setSelected(isSelected());
		return c;
	}

	
}
