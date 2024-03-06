package observer;

import java.beans.PropertyChangeSupport;

import mvc.DrawingController;

public class ObserverManager {

	private PropertyChangeSupport propertyChangeSupport;
	private DrawingController controller;
	
	public ObserverManager(DrawingController controller,PropertyChangeSupport propertyChangeSupport) {
		this.controller = controller;
		this.propertyChangeSupport = propertyChangeSupport;
	}
	
	public void delete() {
		propertyChangeSupport.firePropertyChange("disable delete", true, false);
		propertyChangeSupport.firePropertyChange("disable modify", true, false);
		propertyChangeSupport.firePropertyChange("disable bring front", true, false);
		propertyChangeSupport.firePropertyChange("disable bring back", true, false);
		propertyChangeSupport.firePropertyChange("disable to front", true, false);
		propertyChangeSupport.firePropertyChange("disable to back", true, false);
	}
	public void select() {
		propertyChangeSupport.firePropertyChange("disable modify", true, false);
		propertyChangeSupport.firePropertyChange("disable to back", true, false);
		propertyChangeSupport.firePropertyChange("disable to front", true, false);
		propertyChangeSupport.firePropertyChange("disable bring back", true, false);
		propertyChangeSupport.firePropertyChange("disable bring front", true, false);
	}
	public void deselect() {
		propertyChangeSupport.firePropertyChange("disable to back", true, false);
		propertyChangeSupport.firePropertyChange("disable to front", true, false);
		propertyChangeSupport.firePropertyChange("disable bring back", true, false);
		propertyChangeSupport.firePropertyChange("disable bring front", true, false);
		propertyChangeSupport.firePropertyChange("disable delete", true, false);
		propertyChangeSupport.firePropertyChange("disable modify", true, false);
	}
	public void notEmpty() {
		propertyChangeSupport.firePropertyChange("enable delete", true, false);
	}
	public void undoIsNotEmpty() {
		propertyChangeSupport.firePropertyChange("enable undo", true, false);
	}
	public void undoIsEmpty() {
		propertyChangeSupport.firePropertyChange("disable undo", true, false);
	}
	public void sizeIsOne() {
		propertyChangeSupport.firePropertyChange("enable modify", true, false);
		propertyChangeSupport.firePropertyChange("enable to back", true, false);
		propertyChangeSupport.firePropertyChange("enable to front", true, false);
		propertyChangeSupport.firePropertyChange("enable bring back", true, false);
		propertyChangeSupport.firePropertyChange("enable bring front", true, false);
	}
	public void delmod() {
		propertyChangeSupport.firePropertyChange("enable delete", true, false);
		propertyChangeSupport.firePropertyChange("enable modify", true, false);
	}
	public void redoIsEmpty() {
		propertyChangeSupport.firePropertyChange("disable redo", true, false);
	}
	public void oneShape() {
		propertyChangeSupport.firePropertyChange("disable to front", true, false);
		propertyChangeSupport.firePropertyChange("disable bring front", true, false);
		propertyChangeSupport.firePropertyChange("disable to back", true, false);
		propertyChangeSupport.firePropertyChange("disable bring back", true, false);
	}
	public void inBeetween() {
		propertyChangeSupport.firePropertyChange("enable bring front", true, false);
		propertyChangeSupport.firePropertyChange("enable bring back", true, false);
		propertyChangeSupport.firePropertyChange("enable to front", true, false);
		propertyChangeSupport.firePropertyChange("enable to back", true, false);
	}
	public void lowestSelected() {
		propertyChangeSupport.firePropertyChange("enable to front", true, false);
		propertyChangeSupport.firePropertyChange("enable bring front", true, false);
	}
	public void highestSelected() {
		propertyChangeSupport.firePropertyChange("enable bring back", true, false);
		propertyChangeSupport.firePropertyChange("enable to back", true, false);
	}
	public void firstSelected() {
		propertyChangeSupport.firePropertyChange("disable to back", true, false);
		propertyChangeSupport.firePropertyChange("disable bring back", true, false);
	}
	public void lastSelected() {
		propertyChangeSupport.firePropertyChange("disable to front", true, false);
		propertyChangeSupport.firePropertyChange("disable bring front", true, false);
	}
	public void movedOnTop() {
		propertyChangeSupport.firePropertyChange("enable bring back", true, false);
		propertyChangeSupport.firePropertyChange("enable to back", true, false);
	}
	public void movedToBottom() {
		propertyChangeSupport.firePropertyChange("enable bring front", true, false);
		propertyChangeSupport.firePropertyChange("enable to front", true, false);
	}
	public void movedUp() {
		propertyChangeSupport.firePropertyChange("enable to back", true, false);
	}
	public void movedDown() {
		propertyChangeSupport.firePropertyChange("enable to front", true, false);
	}
	public void redoIsOnOne() {
		propertyChangeSupport.firePropertyChange("disable redo", true, false);
	}
	public void undoClicked() {
		propertyChangeSupport.firePropertyChange("enable redo", true, false);
	}
	public void enableSelect() {
		propertyChangeSupport.firePropertyChange("enable select",true,false);
	}
	public void disableSelect() {
		propertyChangeSupport.firePropertyChange("disable select",true,false);
	}
}
