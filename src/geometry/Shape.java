package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import application.observer.Observer;
import application.observer.Subject;

@SuppressWarnings("serial")
public abstract class Shape implements Subject, Comparable, Serializable {
	
	/*
	 * Shape implementira Subject zato sto zelimo da obavestimo Observer (Frame) da kad se Shape promeni, tj selektuje
	 * da se updatuju dugmici u frame-u
	 */
	
	
	protected boolean selected;//boolean promenljiva koju proveravamo u svakoj draw() metodi u oblicima, ako je true onda selektuje oblik
	private Color foreground;
	
	private Observer observer;//svaki Subject treba da ima listu observera, u ovom slucaju samo 1 observer, nas Observer je Frame 

	public Shape() {

	}
	
	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color color) {
		this.foreground= color;
	}
	
	public boolean isSelected() {
		return selected;
	}

	//Svaki put kad se neki Shape selektuje, javi observeru da uradi metodu update(), tj da enabluje dugmice
	public void setSelected(boolean selected) {
		this.selected = selected;
		notifyObservers();
	}
	
	public String getForegroundText()
	{
		return "foreground["+foreground.getRed()+"."+foreground.getGreen()+"."+foreground.getBlue()+"]";
	}
	
	public void setObserver(Observer observer)
	{
		this.observer=observer;
	}

	public void removeObserver(Observer observer)
	{
		this.observer=null;
	}

	public void notifyObservers()
	{
		observer.update();
	}

	public abstract void draw(Graphics g);
	public abstract void selected(Graphics g);
	public abstract boolean contains(int x, int y);
	public abstract Shape clone();
}
