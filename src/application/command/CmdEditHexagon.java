package application.command;

import geometry.HexagonAdapter;

public class CmdEditHexagon implements Command {

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter originalState = new HexagonAdapter(0,0,0);

	public CmdEditHexagon(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {
		originalState = (HexagonAdapter) oldState.clone();

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setR(newState.getR());
		oldState.setForeground(newState.getForeground());
		oldState.setBackground(newState.getBackground());
	}

	@Override
	public void unexecute() {
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setR(originalState.getR());
		oldState.setForeground(originalState.getForeground());
		oldState.setBackground(originalState.getBackground());
	}
	
	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}
	
}
