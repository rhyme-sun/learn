package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.CellingFan;

/**
 * 吊扇高速指令
 */
public class CellingLowCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private CellingFan cellingFan;

	private int prevSpeed;

	public CellingLowCommand(CellingFan cellingFan) {
		this.cellingFan = cellingFan;
	}

	@Override
	public void execute() {
		prevSpeed = cellingFan.getSpeed();
		cellingFan.low();
	}

	@Override
	public void undo() {
		if (prevSpeed == 3) {
			cellingFan.high();
		} else if (prevSpeed == 2) {
			cellingFan.medium();
		}else if (prevSpeed == 1) {
			cellingFan.low();
		} else {
			cellingFan.off();
		}
	}
}
