package command;

import drawingApp.Shape;
import mvc.DrawingModel;

public class AddShapeCmd implements Command {

	private Shape shape;
	private DrawingModel model;
	private int a;
	
	
	
	public AddShapeCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	public AddShapeCmd(Shape shape,DrawingModel model, int a) {
		this.shape=shape;
		this.model=model;
		this.a=a;
	}
	public void executee() {
		model.addAtIndex(shape, a);
	}

	@Override
	public void execute() {
		model.add(shape);

	}

	@Override
	public void unexecute() {
		model.remove(shape);

	}
	@Override
	public String toString() {
		return "Added: "+shape.toString();
	}

}
