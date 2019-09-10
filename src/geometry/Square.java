package geometry;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Square extends AreaShape  {

	protected Point upperLeft;
	private int side;

	public Square() {

	}

	public Square(Point upperLeft, int side) {
		this.upperLeft = upperLeft;
		this.side = side;
	}
	
	public Square(Point upperLeft, int side, Color foreground, Color background) {
		this.upperLeft = upperLeft;
		this.side= side;
		super.setForeground(foreground);
		super.setBackground(background);
	}
	
	public Line diagonal() {
		Point upperRight= new Point(this.upperLeft.getX() + this.side, upperLeft.getY());
		Point lowerLeft = new Point(upperLeft.getX(), this.upperLeft.getY() + this.side);

		Line diagonal = new Line(upperRight, lowerLeft);

		return diagonal;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Square) {
			Square forwarded = (Square) o;
			return this.side - forwarded.side;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		if (this.upperLeft.getX() <= x && x <= this.upperLeft.getX() + this.side&& this.upperLeft.getY() <= y
				&& y <= this.upperLeft.getY() + this.side) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void selected(Graphics g) {
		upperLeft.selected(g);
		this.diagonal().getStart().selected(g);
		this.diagonal().getEnd().selected(g);
		Point lowerRight = new Point(upperLeft.getX() + side, upperLeft.getY() + side);
		lowerRight.selected(g);
		Line l1 = new Line(upperLeft, this.diagonal().getStart());
		l1.lineCenter().selected(g);
		Line l2 = new Line(this.diagonal().getEnd(), lowerRight);
		l2.lineCenter().selected(g);
		Line l3 = new Line(upperLeft, this.diagonal().getEnd());
		l3.lineCenter().selected(g);
		Line l4 = new Line(this.diagonal().getStart(), lowerRight);
		l4.lineCenter().selected(g);
	}
	
	public boolean equals(Object second) {
		if (second instanceof Square) {
			Square secondSquare= (Square) second;
			if (this.upperLeft.equals(secondSquare.getUpperLeft()) && this.getSide()==secondSquare.getSide())
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
		fill(g);
		g.setColor(this.getForeground());
		g.drawRect(this.upperLeft.getX(), this.upperLeft.getY(), this.side, this.side);

		if (this.isSelected()) {
			this.selected(g);
		}
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(upperLeft.getX(), upperLeft.getY(), this.side, this.side);
	}
	
	@Override
	public String toString() {
		return "Square:start"+upperLeft.getXYText()+",side("+side+"),"+getForegroundText()+","+getBackgroundText();
	}
	
	@Override
	public Shape clone() {
		Square squareClone = new Square();
		squareClone.setUpperLeft(new Point(this.getUpperLeft().getX(),this.getUpperLeft().getY()));
		squareClone.setSide(this.getSide());
		squareClone.setForeground(this.getForeground());
		squareClone.setBackground(this.getBackground());
		return squareClone;
	}
	
	public Point getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
}
