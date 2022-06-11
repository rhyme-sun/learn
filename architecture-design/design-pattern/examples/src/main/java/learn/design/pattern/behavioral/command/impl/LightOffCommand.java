package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Light;

/**
 * 关灯
 */
public class LightOffCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Light light;

	public LightOffCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.off();
	}

	@Override
	public void undo() {
		light.on();
	}
}
