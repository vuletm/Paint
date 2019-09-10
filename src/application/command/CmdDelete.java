package application.command;

import java.util.ArrayList;

import application.mvc.DrawModel;
import geometry.Shape;

public class CmdDelete implements Command {

	private DrawModel model;
	private ArrayList<Shape> shapes;//prima listu za brisanje, zato sto moze obirsati vise od jednog
	
	public CmdDelete(ArrayList<Shape> shapes,DrawModel model) {
		this.model=model;
		this.shapes=shapes;
	}
	
	@Override
	public void execute() {
		for(Shape s : shapes)//prolazimo kroz listu selektovanih oblika za brsianje i brisemo ih
		{
			model.remove(s);
		}
	}

	@Override
	public void unexecute() {
		for(Shape s: shapes)
		{
			model.add(s);
		}
	}
	
	public String toString()
	{
		//osnova kouj treba da vrati je 'delete:' i posle na to dodajemo toString svih oblika koje obrisemo
		String text="delete:";
		
		for(Shape s : shapes)
		{
			text=text+s.toString()+":";//???
		}
		return text;
	}

}
