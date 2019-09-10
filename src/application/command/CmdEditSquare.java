package application.command;

import geometry.Square;

public class CmdEditSquare implements Command {
	
	private Square oldState;
	private Square newState;
	private Square originalState = new Square();

	public CmdEditSquare(Square oldState, Square newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = (Square) oldState.clone();

		oldState.setUpperLeft(newState.getUpperLeft());
		oldState.setSide(newState.getSide());
		oldState.setForeground(newState.getForeground());
		oldState.setBackground(newState.getBackground());
	}

	@Override
	public void unexecute() {
		oldState.setUpperLeft(originalState.getUpperLeft());
		oldState.setSide(originalState.getSide());
		oldState.setForeground(originalState.getForeground());
		oldState.setBackground(originalState.getBackground());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}
}
