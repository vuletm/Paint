package application.mvc;

import java.awt.Frame;

public class Main {

	public static void main(String[] args) {
		
		/*
		 * Aplikacija je napravljena preko MVC obrasca (Model,Controller,View)
		 	U ovoj klasi smo odradili povezaivanje svih klasa MVC-a
		 */
		
		//Model su podaci koji se prikazuju, znaci lista Shape-ova
		DrawModel model = new DrawModel();
		
		//Frame je izgled programa, korisnicki interfejs
		DrawFrame frame = new DrawFrame();
		frame.getView().setModel(model);//??????????

		//Controller obavlja interakciju sa korisnikom
		DrawController controller = new DrawController(model,frame);
		
		frame.setController(controller);
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("Vulo project");
		frame.setLocationRelativeTo(null);
	}

}
