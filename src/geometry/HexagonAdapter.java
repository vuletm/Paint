package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends AreaShape  {

	/*
	 * Adapter je obrazac sa kojim neku stranu klasu prilagodimo nasem projektu
	 * U ovom slucaju imamo Hexagon klasu koju smo importovali 
	 * Hexagon klasa nam ne odgovara jer ne moze da implementira Shape interfejs, pa onda ne moze da se ubaci u listu Shape-ova
	 * Moramo da napravimo HexagonAdapter koji implementira Shape, a da koristimo metode iz Hexagon-a
	 * ZNaci koristimo logiku iz importovanog Hexagona, ali je adaptiramo na nas projekt
	 *  */
	private Hexagon h;
	private int x,y,r;
	
	public HexagonAdapter(int x,int y,int r) {
		this.x=x;
		this.y=y;
		this.r=r;
		h=new Hexagon(x,y,r);
	} 
	
	public HexagonAdapter(int x,int y,int r,Color foreground,Color background) {
		this.x=x;
		this.y=y;
		this.r=r;
		h=new Hexagon(x,y,r);
		h.setBorderColor(foreground);
		h.setAreaColor(background);
		
		this.setForeground(foreground);
		this.setBackground(background);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof HexagonAdapter) {
			HexagonAdapter forwarded = (HexagonAdapter) o;
			return this.r - forwarded.getR();
		} else {
			return 0;
		}
	}


	public void setSelected(boolean selected)
	{
		super.setSelected(selected);
		this.h.setSelected(selected);
	}

	public boolean equals(Object second) {
		if (second instanceof HexagonAdapter) {
			HexagonAdapter secondHexagon = (HexagonAdapter) second;
			if (this.x == secondHexagon.getX() && this.y == secondHexagon.getY() && this.r==secondHexagon.getR() ) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	


	@Override
	public void fill(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public void draw(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public void selected(Graphics g) {
		this.h.paint(g);
	}

	@Override
	public boolean contains(int x, int y) {
		return this.h.doesContain(x, y);
	}

	@Override
	public Shape clone() {
		HexagonAdapter hexagonClone = new HexagonAdapter(this.x,this.y,this.r);
		hexagonClone.setBackground(this.getBackground());
		hexagonClone.setForeground(this.getForeground());
		return hexagonClone;
	}
	
	public String getForegroundText()
	{
		return "foreground["+this.h.getBorderColor().getRed()+"."+this.h.getBorderColor().getGreen()+"."+this.h.getBorderColor().getBlue()+"]";
	}
	
	public String getBackgroundText()
	{
		return "background["+this.h.getAreaColor().getRed()+"."+this.h.getAreaColor().getGreen()+"."+this.h.getAreaColor().getBlue()+"]";
	}
	
	@Override
	public String toString()
	{
		return "Hexagon:center["+x+","+y+"],radius("+r+"),"+this.getForegroundText()+","+getBackgroundText();
	}
	
	public int getR() {
		return this.r;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x)
	{
		h.setX(x);
	}
	
	public void setY(int y)
	{
		h.setY(y);
	}
	
	public void setR(int r)
	{
		h.setR(r);
	}
	
	public Color getForeground() {
		return h.getBorderColor();
	}

	public void setForeground(Color color) {
		this.h.setBorderColor(color);
	}
	
	public void setBackground(Color background) {
		this.h.setAreaColor(background);
	}
	
	public Color getBackground() {
		return this.h.getAreaColor();
	}
	
}
