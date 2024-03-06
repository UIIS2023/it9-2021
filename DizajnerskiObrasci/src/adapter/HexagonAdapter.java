package adapter;
import java.awt.Graphics;
import java.awt.Color;

import drawingApp.Point;
import drawingApp.Shape;
import hexagon.Hexagon;

public class HexagonAdapter extends Shape {
	
	private Hexagon hexagon;
	
	public HexagonAdapter(Hexagon hexagon) {
		this.hexagon = hexagon;
	}
	public HexagonAdapter(Point startPoint,int r) {
		this.hexagon = new Hexagon (startPoint.getX(),startPoint.getY(),r);
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof HexagonAdapter) {
			return hexagon.getR()-((HexagonAdapter)o).getR();
		}
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof HexagonAdapter) {
			Hexagon h = ((HexagonAdapter) obj).hexagon;
			return hexagon.getX() == h.getX() && hexagon.getY() == h.getY() && hexagon.getR() == h.getR();
		}
		return false;
	}
	
	

	@Override
	public void draw(Graphics g) {
		hexagon.paint(g);
		hexagon.setSelected(isSelected());
		
	}
	
	@Override
	public String toString() {
		return "Hexagon: Center = (" + hexagon.getX() + ","+hexagon.getY()+") ; Radius = "+hexagon.getR()+" ; Border color = "+hexagon.getBorderColor()+" ; Inner color = "+hexagon.getAreaColor();
	}

	@Override
	protected void moveBy(int i, int j) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}
	
	@Override
	public void setSelected(boolean selected) {
		hexagon.setSelected(selected);
		super.setSelected(selected);
	}
	
	public Color getColor() {
		return hexagon.getBorderColor();
	}
	
	public void setColor(Color color) {
		hexagon.setBorderColor(color);
		super.setColor(color);
	}
	public Color getInnerColor() {
		return hexagon.getAreaColor();
	}
	public void setInnerColor(Color color) {
		hexagon.setAreaColor(color);
		super.setColor(color);
	}
	public int getR() {
		return hexagon.getR();
	}
	public void setR(int radius)  throws Exception {
		if (radius >= 0) {
			hexagon.setR(radius);
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
	}
	public int getX() {
		return hexagon.getX();
	}
	public void setX(int x) {
		hexagon.setX(x);
	}
	public int getY() {
		return hexagon.getY();
	}
	public void setY(int y) {
		hexagon.setY(y);
	}
	@Override
	public Shape clone() {
		HexagonAdapter h = new HexagonAdapter(new Point(getX(),getY()),getR());
		h.setColor(getColor());
		h.setInnerColor(getInnerColor());
		h.setSelected(isSelected());
		return h;
	}
	
}
