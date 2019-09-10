package application.command;

import java.util.Collections;

import application.mvc.DrawModel;
import geometry.Shape;

public class CmdToBack implements Command{

	private DrawModel model;
	private Shape s;//selektovan oblik
	private int position;//poziciju selektovanog oblika za slanje unazad
	
	public CmdToBack(DrawModel model,Shape s)
	{
		this.model=model;
		this.s=s;
	}
	
	@Override
	public void execute() {
		//slanje unazad zapravo menja poziciju tog selektovanog oblika sa onim pre njega
		this.position=model.getAll().indexOf(s);//?????
		Collections.swap(model.getAll(), position,position-1);
	}

	@Override
	public void unexecute() {
		this.position=model.getAll().indexOf(s);
		Collections.swap(model.getAll(),position,position+1);
	}
	
	public String toString()
	{
		return "toback:"+s.toString();
	}

}
