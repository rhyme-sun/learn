package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;
import learn.design.pattern.behavioral.command.receiver.Thermostat;

/**
 * 打开空调
 */
public class ThermostatOnCommand implements Command {

	/**
	 * 命令接受者，即接受命令，执行对应操作
	 */
	private Thermostat thermostat;

	public ThermostatOnCommand(Thermostat thermostat) {
		this.thermostat = thermostat;
	}

	@Override
	public void execute() {
		thermostat.on();
		thermostat.heatUp();
		thermostat.coolDown();
	}

	@Override
	public void undo() {
		thermostat.heatUp();
		thermostat.coolDown();
		thermostat.off();
	}
}
