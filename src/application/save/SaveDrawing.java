package application.save;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import application.mvc.DrawModel;

public class SaveDrawing implements Save {

	private DrawModel model;
	
	public SaveDrawing(DrawModel model) {
		this.model=model;
	}
	
	@Override
	public void save() {
		JFileChooser saveDialog = new JFileChooser(); 
		saveDialog.setDialogTitle("Choose save destination");
		if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { //ako klikne Save dugme uspesno
			
			File choosenFile = new File(saveDialog.getSelectedFile().getAbsolutePath());//ovde smestamo putanju koju je ukucao npr Documents/drawing 

			if (choosenFile.exists()) { 
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fos;//sluzi za upis u fajl,bilo teksta ili objekata
				
				ObjectOutputStream oos;//sluzi za upisivanje objekata u fajl
				try { 
					fos = new FileOutputStream(choosenFile);
					oos = new ObjectOutputStream(fos);

					oos.writeObject(model.getAll());//zapisujemo model,tj listu svih oblika u fajl u formatu objekta
					oos.close();
					fos.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
