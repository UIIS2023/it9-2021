package drawingApp;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	private int x;
	private int y;
	private Color color;
	
	public Point() {
		
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y=y;
	}
	
	public Point(int x, int y, boolean selected) {
		this(x, y);
		setSelected(selected);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		g.drawLine(this.x-2, this.y, this.x+2, y);
		g.drawLine(x, this.y-2, x, this.y+2);
		
		if (isSelected()) {
			//g.setColor(Color.BLUE);
			g.drawRect(this.x-3, this.y-3, 6, 6);
		}
	}

	public void setSelected(Graphics g) {
		g.drawRect(this.x-3, this.y-3, 6, 6);
	}
	
	public boolean contains(int x, int y) {
		return this.distance(x, y) <=3;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (this.x == p.getX() &&
					this.y == p.getY()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public double distance(int x2, int y2) {
		double dx = this.x - x2;
		double dy = this.y - y2;
		double d = Math.sqrt(dx*dx + dy*dy);
		return d;
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "Point: ("+getX() + ","+getY()+") ; Color = "+getColor() ;
	}

	@Override
	protected void moveBy(int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int compareTo(Object obj) {
		if(obj instanceof Point) {
			double d1 = this.distance(0, 0);
			double d2 = ((Point)obj).distance(0, 0);
			return (int)(d1-d2);
		}
		else {
			return 0;
		}
	}

	@Override
	public Shape clone() {
		Point p = new Point();
		p.setX(getX());
		p.setY(getY());
		p.setColor(getColor());
		p.setSelected(isSelected());
		return p;
	}

}
