package strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import geometry.Shape;

public class StrategySerialized implements Strategy {

	private List<Shape> shapes;
	
	public StrategySerialized(List<Shape> shapes) {
		this.shapes = shapes;
	}
	
	@Override
	public void save() {
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Save file");
		
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath();
			try {
				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
				os.writeObject(shapes);	
				os.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Not found", "Error", JOptionPane.WARNING_MESSAGE);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "File not saved", "Error", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

}
