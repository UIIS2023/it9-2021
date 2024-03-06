package command;

import java.util.Iterator;

import drawingApp.Shape;
import mvc.DrawingController;

public class SelectedShapeCmd implements Command {
	private DrawingController controller;
	private Shape shape;


	public SelectedShapeCmd(DrawingController controller, Shape shape) {
		this.controller = controller;
		this.shape = shape;
	}

	@Override
	public void execute() {
		this.controller.getSelectedList().add(this.shape);
		this.shape.setSelected(true);

	}

	@Override
	public void unexecute() {
				this.shape.setSelected(false);
				this.controller.getSelectedList().remove(this.shape);
		}
	@Override
	public String toString() {
		if(shape.isSelected()) {
			
			return "Selected: "+shape.toString();
		}
		else {
			return "Deselect: "+shape.toString();
		}
	}
	}


