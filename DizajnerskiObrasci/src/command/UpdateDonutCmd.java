package command;

import drawingApp.Donut;
import drawingApp.Point;

public class UpdateDonutCmd implements Command {
	
	private Donut donut;
	private Donut newState;
	private Donut temp = new Donut();
	
	public UpdateDonutCmd(Donut donut, Donut newState) {
		this.donut = donut;
		this.newState = newState;
		this.temp = (Donut)donut.clone();
	}

	@Override
	public void execute() {
		
		/*temp.getCenter().setX(donut.getCenter().getX());
		temp.getCenter().setY(donut.getCenter().getY());
		temp.setColor(donut.getColor());
		temp.setColorIviceMali(donut.getColorIviceMali());
		//temp.setColorUnutrasnjost(donut.getColorUnutrasnjost()); ne treba jer vec imam boje za veliki i mali krug
		temp.setColorUnutrasnjostVeliki(donut.getColorUnutrasnjostVeliki());
		temp.setColorUnutrasnostMali(donut.getColorUnutrasnostMali());
		try {
			temp.setRadius(donut.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp.setInnerRadius(donut.getInnerRadius());*/
		
		donut.getCenter().setX(newState.getCenter().getX());
		donut.getCenter().setY(newState.getCenter().getY());
		donut.setColor(newState.getColor());
		donut.setColorIviceMali(newState.getColorIviceMali());
		donut.setColorUnutrasnjostVeliki(newState.getColorUnutrasnjostVeliki());
		donut.setColorUnutrasnostMali(newState.getColorUnutrasnostMali());
		donut.setInnerRadius(newState.getInnerRadius());
		try {
			donut.setRadius(newState.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

	@Override
	public void unexecute() {
		
		donut.getCenter().setX(temp.getCenter().getX());
		donut.getCenter().setY(temp.getCenter().getY());
		donut.setColor(temp.getColor());
		donut.setColorIviceMali(temp.getColorIviceMali());
		donut.setColorUnutrasnjostVeliki(temp.getColorUnutrasnjostVeliki());
		donut.setColorUnutrasnostMali(temp.getColorUnutrasnostMali());
		donut.setInnerRadius(temp.getInnerRadius());
		try {
			donut.setRadius(temp.getRadius());
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
