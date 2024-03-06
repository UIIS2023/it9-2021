package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mvc.DrawingModel;

public class SavePainting implements OpenAndSave {
	
	
	

	@Override
	public void open(Object c, Object m, Object f, File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Object o, File file) {  //KAD SE KLIKNE NA SAVE FILE, IZVRSAVA SE OVA METODA I METODA SAVE U KLASI SAVELOG
		DrawingModel model = (DrawingModel)o;
		ObjectOutputStream stream = null;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(file));
			stream.writeObject(model.getShapes());
			stream.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}

}
