package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Hottub;

/**
 * 放水
 */
public class HottubOffCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Hottub hottub;

	public HottubOffCommand(Hottub hottub) {
		this.hottub = hottub;
	}

	@Override
	public void execute() {
		hottub.releaseWater();
	}

	@Override
	public void undo() {
		hottub.addWater();
	}
}
