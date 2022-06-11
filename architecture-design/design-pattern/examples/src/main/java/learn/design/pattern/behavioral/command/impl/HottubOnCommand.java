package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Hottub;

/**
 * 向浴缸加水
 */
public class HottubOnCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Hottub hottub;

	public HottubOnCommand(Hottub hottub) {
		this.hottub = hottub;
	}

	@Override
	public void execute() {
		hottub.addWater();
		hottub.heatWater();
	}

	@Override
	public void undo() {
		hottub.releaseWater();
	}
}
