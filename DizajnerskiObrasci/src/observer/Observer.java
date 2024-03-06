package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import mvc.DrawingFrame;

public class Observer implements PropertyChangeListener {

	DrawingFrame frame;

	public Observer(DrawingFrame frame) {
		this.frame = frame;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().contains("enable")) {
			enableButtons(evt.getPropertyName());
		} else if (evt.getPropertyName().contains("disable")) {
			disableButtons(evt.getPropertyName());
		}
	}

	public void enableButtons(String name) {
		if (name.contains("delete")) {
			frame.getBtnDelete().setEnabled(true);
		} else if (name.contains("modify")) {
			frame.getBtnModify().setEnabled(true);
		} else if (name.contains("undo")) {
			frame.getBtnUndo().setEnabled(true);
		} else if (name.contains("redo")) {
			frame.getBtnRedo().setEnabled(true);
		}
		else if(name.contains("to front")) {
			frame.getTglbtnToFront().setEnabled(true);
		}
		else if(name.contains("to back")) {
			frame.getTglbtnToBack().setEnabled(true);
		}
		else if(name.contains("bring front")) {
			frame.getTglbtnBringToFront().setEnabled(true);
		}
		else if(name.contains("bring back")) {
			frame.getTglbtnBringToBack().setEnabled(true);
		}
		else if(name.contains("select")) {
			frame.getTglbtnSelektuj().setEnabled(true);
		}
	}

	public void disableButtons(String name) {
		if (name.contains("delete")) {
			frame.getBtnDelete().setEnabled(false);
		} else if (name.contains("modify")) {
			frame.getBtnModify().setEnabled(false);
		} else if (name.contains("undo")) {
			frame.getBtnUndo().setEnabled(false);
		} else if (name.contains("redo")) {
			frame.getBtnRedo().setEnabled(false);
		}
		else if(name.contains("to front")) {
			frame.getTglbtnToFront().setEnabled(false);
		}
		else if(name.contains("to back")) {
			frame.getTglbtnToBack().setEnabled(false);
		}
		else if(name.contains("bring front")) {
			frame.getTglbtnBringToFront().setEnabled(false);
		}
		else if(name.contains("bring back")) {
			frame.getTglbtnBringToBack().setEnabled(false);
		}
		else if(name.contains("select")) {
			frame.getTglbtnSelektuj().setEnabled(false);
		}

	}

}
