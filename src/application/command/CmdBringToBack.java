package application.command;

import application.mvc.DrawModel;
import geometry.Shape;

public class CmdBringToBack implements Command {

	private DrawModel model;
	private Shape s;//selektovan oblik
	private int position;//cuvamo originalnu poziciju selektovanog oblika da bi posle mogli da ga vratimo gde je bio
	
	public CmdBringToBack(DrawModel model,Shape s) {
		this.model=model;
		this.s=s;
		this.position=model.getAll().indexOf(s);
	}
	
	@Override
	public void execute() {
		//brise iz modela i dodaje ga na pocetak, da bi bio na dnu
		model.remove(s);
		model.getAll().add(0, s);
		
	}

	@Override
	public void unexecute() {
		//brise iz modela tj sa dna, i vraca ga na onu poziciju gde je originalno bio
		model.remove(s);
		model.getAll().add(position, s);
	}
	
	public String toString()
	{
		return "bringtoback:"+s.toString();
	}

}
