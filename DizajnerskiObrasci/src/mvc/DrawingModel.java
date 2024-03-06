package mvc;

import java.util.ArrayList;

import drawingApp.Shape;

public class DrawingModel {
	
	public ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public void add(Shape s) {
		shapes.add(s);
	}
	
	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public Shape get (int index) {
		return shapes.get(index);
	}
	
	public ArrayList<Shape> getShapes(){
		return shapes;
	}
	public void addAtIndex(Shape shape, int i) {
		shapes.add(i,shape);
	}
	public int getIndex(Shape shape) {
		return shapes.indexOf(shape);
	}
}
