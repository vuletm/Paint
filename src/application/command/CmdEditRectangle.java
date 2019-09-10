package application.command;

import geometry.Rectangle;

public class CmdEditRectangle implements Command{

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle originalState = new Rectangle();

	public CmdEditRectangle(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = oldState.clone();

		oldState.setUpperLeft(newState.getUpperLeft());
		oldState.setHeight(newState.getHeight());
		oldState.setSide(newState.getSide());
		oldState.setForeground(newState.getForeground());
		oldState.setBackground(newState.getBackground());
	}

	@Override
	public void unexecute() {
		oldState.setUpperLeft(originalState.getUpperLeft());
		oldState.setHeight(originalState.getHeight());
		oldState.setSide(originalState.getSide());
		oldState.setForeground(originalState.getForeground());
		oldState.setBackground(originalState.getBackground());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + (Rectangle)newState;
	}
	
}
