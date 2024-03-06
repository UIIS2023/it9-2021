package command;

import drawingApp.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command {
	
	private Shape shape;
	private DrawingModel model;
	private int i;
	
	public ToFrontCmd(Shape shape, DrawingModel model) {
		this.shape = shape;
		this.model = model;
	}
	

	@Override
	public void execute() {
		i=model.getIndex(shape);
		if(i==model.getShapes().size()-1) {
			return;
		}
		model.remove(shape);
		model.addAtIndex(shape, i+1);
		shape.setSelected(shape.isSelected());
		
	}

	@Override
	public void unexecute() {
		   int currentIndex = model.getIndex(shape);
		   if (currentIndex > 0) {
		      model.remove(shape);
		      model.addAtIndex(shape, currentIndex - 1);
		   }
		}
	@Override
	public String toString() {
		return "Moved up: "+shape.toString();
	}

}
