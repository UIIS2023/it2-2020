package strategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class StrategyText implements Strategy {

	private List<String> logs;
	
	public StrategyText(List<String> logs) {
		this.logs = logs;
	}
	
	@Override
	public void save() {
		JFileChooser jFileChooser = new JFileChooser(new File("D:\\"));
		jFileChooser.setDialogTitle("Save file");
		
		int result = jFileChooser.showSaveDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			String path = jFileChooser.getSelectedFile().getAbsolutePath() + ".txt";
			try {
				FileWriter fw = new FileWriter(path);
				for(String s: logs) {
					fw.write(s + System.lineSeparator());
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Saving failed.", "Failed!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			
		}
	}

}
