package command;

import drawingApp.Circle;
import drawingApp.Point;

public class UpdateCircleCmd implements Command {
	
	private Circle circle;
	private Circle newState;
	private Circle temp = new Circle();

	public UpdateCircleCmd(Circle circle, Circle newState) {
		this.circle = circle;
		this.newState = newState;
		this.temp = (Circle)circle.clone();
	}
	
	
	@Override
	public void execute() {
		/*temp.getCenter().setX(circle.getCenter().getX());
		temp.getCenter().setY(circle.getCenter().getY());
		temp.setColor(circle.getColor());
		temp.setColorUnutrasnjost(circle.getColorUnutrasnjost());
		try {
			temp.setRadius(circle.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		circle.getCenter().setX(newState.getCenter().getX());
		circle.getCenter().setY(newState.getCenter().getY());
		circle.setColor(newState.getColor());
		circle.setColorUnutrasnjost(newState.getColorUnutrasnjost());
		try {
			circle.setRadius(newState.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void unexecute() {
		circle.getCenter().setX(temp.getCenter().getX());
		circle.getCenter().setY(temp.getCenter().getY());
		circle.setColor(temp.getColor());
		circle.setColorUnutrasnjost(temp.getColorUnutrasnjost());
		try {
			circle.setRadius(temp.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public String toString() {
		return "Updated: "+temp.toString()+" into "+newState.toString();
	}

}
