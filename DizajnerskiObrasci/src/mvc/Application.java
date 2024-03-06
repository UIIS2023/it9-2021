package mvc;

import javax.swing.JFrame;

public class Application {

	public static void main(String[] args) {
		System.out.println("U projekat su ubacene dodatne biblioteke zbog koriscenja MigLayout-a. Build Path -> Configure Build Path -> Add Library -> User Library -> 1. jgoodies-forms-1.8.0.jar  2. jgoodies-forms-1.8.0-sources.jar   3. miglayouts15-swing.jar -> apply and close");
		
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		frame.getView().setModel(model);
		DrawingController controller = new DrawingController(model, frame);
		frame.setController(controller);
		
		//frame
		frame.setSize(1500,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
