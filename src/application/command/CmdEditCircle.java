package application.command;

import geometry.Circle;

public class CmdEditCircle implements Command{

	private Circle oldState;
	private Circle newState;
	private Circle originalState = new Circle();

	public CmdEditCircle(Circle oldState, Circle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setCenter(newState.getCenter());
		oldState.setRadius(newState.getRadius());
		oldState.setForeground(newState.getForeground());
		oldState.setBackground(newState.getBackground());
	}

	@Override
	public void unexecute() {
		oldState.setCenter(originalState.getCenter());
		oldState.setRadius(originalState.getRadius());
		oldState.setForeground(originalState.getForeground());
		oldState.setBackground(originalState.getBackground());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}

}
