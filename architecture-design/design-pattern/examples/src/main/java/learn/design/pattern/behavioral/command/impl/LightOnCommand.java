package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Light;

/**
 * 开灯指令
 */
public class LightOnCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Light light;

	public LightOnCommand(Light light) {
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}

	@Override
	public void undo() {
		light.off();
	}
}
