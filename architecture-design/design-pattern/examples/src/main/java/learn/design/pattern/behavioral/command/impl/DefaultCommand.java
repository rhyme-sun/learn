package learn.design.pattern.behavioral.command.impl;

import learn.design.pattern.behavioral.command.Command;

/**
 * 空对象
 * 出厂指令，什么都不做
 */
public class DefaultCommand implements Command {

	@Override
	public void execute() {}

	@Override
	public void undo() {}
}
