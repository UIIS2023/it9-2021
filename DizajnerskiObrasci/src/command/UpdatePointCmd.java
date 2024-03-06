package command;

import drawingApp.Point;

public class UpdatePointCmd implements Command {

	private Point point;
	private Point newState;
	private Point temp = new Point(0,0,false);
	
	public UpdatePointCmd(Point point, Point newState) {
		this.point = point;
		this.newState = newState;
		this.temp = (Point)point.clone();
	}
	
	
	@Override
	public void execute() {
		/*temp.setX(point.getX());
		temp.setY(point.getY());
		temp.setColor(point.getColor());
		*/
		point.setX(newState.getX());
		point.setY(newState.getY());
		point.setColor(newState.getColor());
		
	}

	@Override
	public void unexecute() {
		point.setX(temp.getX());
		point.setY(temp.getY());
		point.setColor(temp.getColor());
	}
	@Override
	public String toString() {
		return "Updated: "+temp.toString()+" into "+newState.toString();
	}
}
