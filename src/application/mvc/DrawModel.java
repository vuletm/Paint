package application.mvc;

import java.io.Serializable;
import java.util.ArrayList;

import geometry.Shape;

public class DrawModel implements Serializable{

	private ArrayList<Shape> shapes= new ArrayList<Shape>();
	
	public ArrayList<Shape> getAll() {
		return shapes;
	}

	public Shape get(int i) {
		return shapes.get(i);
	}

	public void add(Shape s) {
		shapes.add(s);
	}

	public void remove(Shape s) {
		shapes.remove(s);
	}
	
	public void setList(ArrayList<Shape>shapes)
	{
		this.shapes=shapes;
	}
}
