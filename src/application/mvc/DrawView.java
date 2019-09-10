package application.mvc;

import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class DrawView extends JPanel {

	/*
	 * DrawView exenteduje JPanel, znaci ima sve sto ima i obican JPanel samo smo mu dodali dodatnu funkcionalnost
	 * da se na bilo kakvu promenu (resize,klik na panel i slicno) iscrtava cela lista oblika
	 */
	private DrawModel model;
	
	public void setModel(DrawModel model) {
		this.model=model;
	}
	
	
	//paint metoda je ugradjena u JavaSwing, poziva se kad god se komponenta bilo kako izmeni (refreshuje,resizuje,klikne na nju i slicno)
	@Override
	public void paint(Graphics g) {
		super.paint(g);//rekli smo joj da odradi sve sto inace odradi
		if (model != null) {//dodali smo funkcionalnost da prodje kroz ceo model i ispocetka iscrtava sve oblike
			Iterator<Shape> it = model.getAll().iterator();
			while (it.hasNext()) {
				it.next().draw(g);
			}
		}
		repaint();
	}
	
	public DrawModel getModel()
	{
		return model;
	}
}
