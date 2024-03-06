package command;

import drawingApp.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private int i;
	
	public BringToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		i = model.getIndex(shape);
		model.remove(shape);
		model.addAtIndex(shape, model.getShapes().size());

	}

	@Override
	public void unexecute() {
		
		model.remove(shape);
		model.addAtIndex(shape, i);
	}
	@Override
	public String toString() {
		return "Brought to front: "+shape.toString();
	}

}
