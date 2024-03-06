package command;

import drawingApp.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private int a;
	
	
	
	public RemoveShapeCmd(Shape shape, DrawingModel model,int a) {
		this.shape = shape;
		this.model = model;
		this.a=a;
	}
	@Override
	public void execute() {
		model.remove(shape);
		

	}

	@Override
	public void unexecute() {
		model.addAtIndex(shape,a);
		
	}
	@Override
	public String toString() {
		return "Removed: "+shape.toString();
	}

}
