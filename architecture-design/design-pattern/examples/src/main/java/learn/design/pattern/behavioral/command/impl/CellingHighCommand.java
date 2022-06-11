package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.CellingFan;

/**
 * 吊扇加速指令
 */
public class CellingHighCommand implements Command {

	private CellingFan cellingFan;

	private int prevSpeed;

	public CellingHighCommand(CellingFan cellingFan) {
		this.cellingFan = cellingFan;
	}

	@Override
	public void execute() {
		prevSpeed = cellingFan.getSpeed();
		cellingFan.high();
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
