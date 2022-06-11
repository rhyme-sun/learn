package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.GarageDoor;

/**
 * 开车库门
 */
public class GarageDoorOnCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private GarageDoor garageDoor;

	public GarageDoorOnCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.up();
	}

	@Override
	public void undo() {
		garageDoor.down();
	}
}
