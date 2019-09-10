package geometry;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Line extends Shape  {
	
	private Point start, end;
	
	public Line() {
		
	}

	public Line(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	
	public Line(Point start, Point end, Color foreground) {
		this.start = start;
		this.end = end;
		super.setForeground(foreground);
	}
	
	public double length() {
		return this.start.distance(this.end);
	}

	public Point lineCenter() {
		return new Point((this.start.getX() + this.end.getX()) / 2,
				(this.start.getY() + this.end.getY()) / 2);
	}
	
	public int compareTo(Object second) {
		if (second instanceof Line) {
			Line secondLine = (Line) second;
			return (int) (this.length() - secondLine.length());
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		Point click = new Point(x, y);
		double distanceStart= this.start.distance(click);
		double distanceEnd = this.end.distance(click);

		if (distanceStart+ distanceEnd<= this.length() + 0.05) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void selected(Graphics g) {
		start.selected(g);
		this.lineCenter().selected(g);
		end.selected(g);
	}
	
	public boolean equals(Object second) {
		if (second instanceof Line) {
			Line secondLine= (Line) second;
			if (this.start.equals(secondLine.getStart()) && this.end.equals(secondLine.getEnd()))
				return true;
			else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.getForeground());
		g.drawLine(this.start.getX(), this.start.getY(), this.end.getX(), this.end.getY());

		if (this.isSelected()) {
			this.selected(g);
		}
	}
	
	@Override
	public String toString() {
		return "Line:start"+start.getXYText()+",end"+end.getXYText()+","+getForegroundText();
	}
	
	@Override
	public Line clone() {
		Line lineClone= new Line();
		lineClone.setStart(
				new Point(this.getStart().getX(), this.getStart().getY()));
		lineClone.setEnd(new Point(this.getEnd().getX(), this.getEnd().getY()));
		lineClone.setForeground(this.getForeground());
		return lineClone;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}
}
