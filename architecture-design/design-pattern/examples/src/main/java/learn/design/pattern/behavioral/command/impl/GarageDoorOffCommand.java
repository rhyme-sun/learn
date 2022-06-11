package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.GarageDoor;

/**
 * 关车库门
 */
public class GarageDoorOffCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private GarageDoor garageDoor;

	public GarageDoorOffCommand(GarageDoor garageDoor) {
		this.garageDoor = garageDoor;
	}

	@Override
	public void execute() {
		garageDoor.down();
	}

	@Override
	public void undo() {
		garageDoor.up();
	}
}
