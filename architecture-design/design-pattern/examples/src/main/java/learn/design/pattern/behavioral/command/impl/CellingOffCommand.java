package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.CellingFan;

/**
 * 吊扇高速指令
 */
public class CellingOffCommand implements Command {


	private CellingFan cellingFan;

	private int prevSpeed;

	public CellingOffCommand(CellingFan cellingFan) {
		this.cellingFan = cellingFan;
	}

	@Override
	public void execute() {
		prevSpeed = cellingFan.getSpeed();
		cellingFan.off();
	}

	@Override
	public void undo() {
		if (prevSpeed == 3) {
			cellingFan.high();
		} else if (prevSpeed == 2) {
			cellingFan.medium();
		} else if (prevSpeed == 1) {
			cellingFan.low();
		} else {
			cellingFan.off();
		}
	}
}
