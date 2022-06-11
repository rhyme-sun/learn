package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;

/**
 * 宏命令
 * 一次执行多个命令
 */
class MacroCommand implements Command {

	Command[] commands;

	public MacroCommand(Command[] commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {
		for (Command command : commands) {
			command.execute();
		}
	}

	@Override
	public void undo() {

	}
}
