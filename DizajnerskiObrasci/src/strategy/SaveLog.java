package strategy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;

import mvc.DrawingFrame;

public class SaveLog implements OpenAndSave {

	@Override
	public void open(Object c, Object m, Object f, File file) {
		
	}

	@Override
	public void save(Object o, File file) {    //KAD SE KLIKNE NA SAVE FILE, IZVRSAVA SE OVA METODA I METODA SAVE U KLASI SAVEPAINTING
		DrawingFrame frame = (DrawingFrame)o;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			DefaultListModel<String> dlm = frame.getDlm();
			for (int i = 0; i < frame.getList().size(); i++) {
				bw.write(dlm.getElementAt(i));
				bw.newLine();
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
	}
		try {
			bw.close();
		}catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
