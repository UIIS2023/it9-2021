package command;

import drawingApp.Point;
import drawingApp.Rectangle;

public class UpdateRectangleCmd implements Command {

	private Rectangle rectangle;
	private Rectangle newState;
	private Rectangle temp = new Rectangle(new Point(0,0),0,0);
	
	public UpdateRectangleCmd(Rectangle rectangle, Rectangle newState) {
		this.rectangle = rectangle;
		this.newState = newState;
		this.temp = (Rectangle)rectangle.clone();
	}
	
	@Override
	public void execute() {
		/*temp.getUpperLeftPoint().setX(rectangle.getUpperLeftPoint().getX());
		temp.getUpperLeftPoint().setY(rectangle.getUpperLeftPoint().getY());
		temp.setWidth(rectangle.getWidth());
		temp.setHeight(rectangle.getHeight());
		temp.setColor(rectangle.getColor());
		temp.setColorUnutrasnjost(rectangle.getColorUnutrasnjost());*/
		
		rectangle.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
		rectangle.setWidth(newState.getWidth());
		rectangle.setHeight(newState.getHeight());
		rectangle.setColor(newState.getColor());
		rectangle.setColorUnutrasnjost(newState.getColorUnutrasnjost());

	}

	@Override
	public void unexecute() {
		rectangle.getUpperLeftPoint().setX(temp.getUpperLeftPoint().getX());
		rectangle.getUpperLeftPoint().setY(temp.getUpperLeftPoint().getY());
		rectangle.setWidth(temp.getWidth());
		rectangle.setHeight(temp.getHeight());
		rectangle.setColor(temp.getColor());
		rectangle.setColorUnutrasnjost(temp.getColorUnutrasnjost());

	}
	@Override
	public String toString() {
		return "Updated: "+temp.toString()+" into "+newState.toString();
	}

}
