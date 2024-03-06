package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SaveManager implements OpenAndSave {
	
	private OpenAndSave save;
	public SaveManager(OpenAndSave save) {
		this.save = save;
	}

	@Override
	public void open(Object c, Object m, Object f, File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		save.open(c,m,f,file);

	}

	@Override
	public void save(Object o, File file) {
		save.save(o, file);

	}
	public void setSave(OpenAndSave save) {
		this.save = save;
	}
}
