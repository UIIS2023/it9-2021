package command;

import drawingApp.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private int i;
	
	public BringToBackCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}

	@Override
	public void execute() {
		
		i = model.getIndex(shape);
		model.remove(shape);
		model.addAtIndex(shape, 0);
		
	}

	@Override
	public void unexecute() {
		
		model.remove(shape);
		model.addAtIndex(shape, i);
		
	}
	@Override
	public String toString() {
		return "Brought to back: "+shape.toString();
	}

}
