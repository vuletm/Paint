package geometry;

import java.awt.Color;
import java.awt.Graphics;

import application.observer.Observer;


@SuppressWarnings("serial")
public class Circle extends AreaShape {
	
	private Point center;
	private int radius;
	public Circle() {

	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}
	
	public Circle(Point center, int radius, Color foreground, Color background) {
		this(center, radius);
		super.setForeground(foreground);
		super.setBackground(background);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Circle) {
			Circle forwarded = (Circle) o;
			return this.radius - forwarded.radius;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		Point click = new Point(x, y);
		if (this.center.distance(click) <= this.radius) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void selected(Graphics g) {
		center.selected(g);
		Point left= new Point(center.getX() - radius, center.getY());
		Point right = new Point(center.getX() + radius, center.getY());
		Point up = new Point(center.getX(), center.getY() - radius);
		Point down = new Point(center.getX(), center.getY() + radius);

		left.selected(g);
		right.selected(g);
		up.selected(g);
		down.selected(g);
	}
	
	public boolean equals(Object second) {
		if (second instanceof Circle) {
			Circle secondCircle= (Circle) second;
			if (this.center.equals(secondCircle.getCenter()) && this.radius==secondCircle.getRadius())
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
		g.drawOval(this.center.getX() - this.radius, this.center.getY() - this.radius, 2 * this.radius,
				2 * this.radius);

		if (this.isSelected()) {
			this.selected(g);
		}
	}

	@Override
	public void fill(Graphics g) {
		g.setColor(this.getBackground());
		g.fillOval(this.center.getX() - this.radius, this.center.getY() - this.radius, 2 * this.radius,
				2 * this.radius);
	}
	
	@Override
	public String toString() {
		return "Circle:center"+center.getXYText()+",radius("+radius+"),"+getForegroundText()+","+getBackgroundText();
	}
	
	@Override
	public Circle clone() {
		Circle circleClone = new Circle();
		circleClone.setCenter(new Point(this.getCenter().getX(),this.getCenter().getY()));
		circleClone.setRadius(this.getRadius());
		circleClone.setBackground(this.getBackground());
		circleClone.setForeground(this.getForeground());
		return circleClone;
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
}