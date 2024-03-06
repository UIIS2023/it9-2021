package command;

import adapter.HexagonAdapter;
import drawingApp.Point;

public class UpdateHexagonCmd implements Command {
	
	private HexagonAdapter h;
	private HexagonAdapter newState;
	private HexagonAdapter temp = new HexagonAdapter(new Point(0,0,false), 1);
	
	public UpdateHexagonCmd (HexagonAdapter hexagonAdapter, HexagonAdapter newState) {
		this.h = hexagonAdapter;
		this.newState = newState;
		this.temp = (HexagonAdapter)h.clone();
	}

	@Override
	public void execute() {
		/*temp.setX(h.getX());
		temp.setY(h.getY());
		try {
			temp.setR(h.getR());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp.setColor(h.getColor());
		temp.setInnerColor(h.getInnerColor());*/
		
		h.setX(newState.getX());
		h.setY(newState.getY());
		try {
			h.setR(newState.getR());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setColor(newState.getColor());
		h.setInnerColor(newState.getInnerColor());

	}

	@Override
	public void unexecute() {
		h.setX(temp.getX());
		h.setY(temp.getY());
		try {
			h.setR(temp.getR());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		h.setColor(temp.getColor());
		h.setInnerColor(temp.getInnerColor());

	}
	@Override
	public String toString() {
		return "Updated: "+temp.toString()+" into "+newState.toString();
	}

}
