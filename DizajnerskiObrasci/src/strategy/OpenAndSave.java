package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface OpenAndSave {
	
	public void open(Object c,Object m,Object f,File file) throws FileNotFoundException, IOException, ClassNotFoundException;
	public void save(Object o,File file);

}
