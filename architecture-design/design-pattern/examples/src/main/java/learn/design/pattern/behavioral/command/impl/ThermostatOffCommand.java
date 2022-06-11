package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Thermostat;

/**
 * 关闭空调
 */
public class ThermostatOffCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Thermostat thermostat;

	public ThermostatOffCommand(Thermostat thermostat) {
		this.thermostat = thermostat;
	}

	@Override
	public void execute() {
		thermostat.off();
	}

	@Override
	public void undo() {
		thermostat.on();
	}
}
