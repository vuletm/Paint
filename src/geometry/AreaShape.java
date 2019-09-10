package geometry;

import java.awt.Color;
import java.awt.Graphics;

public abstract class AreaShape extends Shape{

	private Color background;
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
	public Color getBackground() {
		return background;
	}
	
	public String getBackgroundText()
	{
		return "background["+background.getRed()+"."+background.getGreen()+"."+background.getBlue()+"]";
	}

	public abstract void fill(Graphics g);
	
}
