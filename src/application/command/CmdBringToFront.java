package application.command;

import application.mvc.DrawModel;
import geometry.Shape;

public class CmdBringToFront implements Command {

	private DrawModel model;
	private Shape s;
	private int position;
	
	public CmdBringToFront(DrawModel model,Shape s) {
		this.model=model;
		this.s=s;
		this.position=model.getAll().indexOf(s);
	}
	
	@Override
	public void execute() {
		model.remove(s);
		model.add(s);//brise i dodaje na kraj pomocu add()
	}

	@Override
	public void unexecute() {
		model.remove(s);
		model.getAll().add(position, s);//brise i vraca gde je bio originalno
	}
	
	public String toString()
	{
		return "bringtofront:"+s.toString();
	}

}
