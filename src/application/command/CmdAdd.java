package application.command;

import java.io.Serializable;

import application.mvc.DrawModel;
import geometry.Shape;

public class CmdAdd implements Command{

	private Shape s;
	private DrawModel model;
	
	public CmdAdd(Shape s,DrawModel model) {//prima oblik koji treba da doda, i model u koji da doda
		this.s=s;
		this.model=model;
	}
	
	@Override
	public void execute() {
		this.model.add(s);
	}

	@Override
	public void unexecute() {
		this.model.remove(s);
	}

	@Override
	public String toString()
	{
		return "add:"+s.toString();
	}
	
}
