package application.save;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import application.mvc.DrawModel;

public class SaveLog implements Save{
	
	private DefaultListModel<String>dlm;
	
	public SaveLog(DefaultListModel<String>dlm) {
		this.dlm=dlm;
	}

	@Override
	public void save() {
		JFileChooser saveDialog = new JFileChooser();
		saveDialog.setDialogTitle("Choose save destination");
		if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File chosenFile = new File(saveDialog.getSelectedFile().getAbsolutePath() + ".txt");

			if (chosenFile.exists()) {
				JOptionPane.showMessageDialog(null, "Filename already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					PrintWriter pw = new PrintWriter(chosenFile);//koristi PrintWriter koji je slican kao System.out.println samo sto upisuje u fajl a ne u konzolu
					
					for (int i = 0; i < dlm.size(); i++) {//prodjes kroz ceo dlm,tj kroz ceo log i liniju po liniju upisujes u fajl
						pw.println(dlm.get(i));
					}
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
