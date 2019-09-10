package application.command;

import geometry.Line;

public class CmdEditLine implements Command {

	private Line oldState;
	private Line newState;
	private Line originalState = new Line();

	public CmdEditLine(Line oldState, Line newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();
		
		oldState.setStart(newState.getStart());
		oldState.setEnd(newState.getEnd());
		oldState.setForeground(newState.getForeground());
		
	}

	@Override
	public void unexecute() {
		oldState.setStart(originalState.getStart());
		oldState.setEnd(originalState.getEnd());
		oldState.setForeground(originalState.getForeground());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}
	
}
