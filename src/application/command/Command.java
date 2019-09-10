package application.command;

import java.io.Serializable;

public interface Command extends Serializable {
	void execute();
	void unexecute();
}
