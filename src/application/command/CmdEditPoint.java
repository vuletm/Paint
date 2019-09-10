package application.command;

import geometry.Point;

public class CmdEditPoint implements Command {

	private Point oldState;//staro stanje tacke
	private Point newState;//novo stanje tacke
	private Point originalState = new Point();

	public CmdEditPoint(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}

	@Override
	public void execute() {//proveriti jesam li u pravu
		originalState = oldState.clone();//ovde koristimo PROTOTYPE obrazac da ne bi morali da idemo orgiinalState.setX = oldState.setX itd za sve nego samo kloniramo

		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setForeground(newState.getForeground());
	}

	@Override
	public void unexecute() {//zasto ovo
		oldState.setX(originalState.getX());
		oldState.setY(originalState.getY());
		oldState.setForeground(originalState.getForeground());
	}

	@Override
	public String toString() {
		return "edit:" + originalState.toString() + ",to:" + newState;
	}
	
}
